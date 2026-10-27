
package ma.fstt.web;

import ma.fstt.util.DatabaseConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/commandes")
public class CommandeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (Connection cn = DatabaseConnection.getConnection()) {
            List<Map<String, Object>> clients = new ArrayList<>();
            List<Map<String, Object>> produits = new ArrayList<>();
            List<Map<String, Object>> commandes = new ArrayList<>();

            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT idClient, nom, prenom FROM Client ORDER BY nom");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> c = new HashMap<>();
                    c.put("id", rs.getInt("idClient"));
                    c.put("nom", rs.getString("nom"));
                    c.put("prenom", rs.getString("prenom"));
                    clients.add(c);
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT idProduit, nom, prix, stock FROM Produit ORDER BY nom");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> p = new HashMap<>();
                    p.put("id", rs.getInt("idProduit"));
                    p.put("nom", rs.getString("nom"));
                    p.put("prix", rs.getBigDecimal("prix"));
                    p.put("stock", rs.getInt("stock"));
                    produits.add(p);
                }
            }

            try (PreparedStatement ps = cn.prepareStatement(
                    "SELECT c.idCommande, c.dateCommande, cl.nom, cl.prenom " +
                            "FROM Commande c JOIN Client cl ON c.idClient=cl.idClient " +
                            "ORDER BY c.idCommande DESC");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> c = new HashMap<>();
                    c.put("id", rs.getInt("idCommande"));
                    c.put("date", rs.getTimestamp("dateCommande"));
                    c.put("nom", rs.getString("nom"));
                    c.put("prenom", rs.getString("prenom"));
                    commandes.add(c);
                }
            }

            req.setAttribute("clients", clients);
            req.setAttribute("produits", produits);
            req.setAttribute("commandes", commandes);
            req.getRequestDispatcher("/commandes/list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des commandes", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int idClient, idProduit, quantite;
        try {
            idClient = Integer.parseInt(req.getParameter("idClient"));
            idProduit = Integer.parseInt(req.getParameter("idProduit"));
            quantite = Integer.parseInt(req.getParameter("quantite"));
            if (quantite <= 0) throw new NumberFormatException("Quantité invalide");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Données de commande invalides.");
            return;
        }

        try (Connection cn = DatabaseConnection.getConnection()) {
            cn.setAutoCommit(false);
            try {
                BigDecimalHolder holder = new BigDecimalHolder();
                try (PreparedStatement ps = cn.prepareStatement(
                        "SELECT prix, stock FROM Produit WHERE idProduit=? FOR UPDATE")) {
                    ps.setInt(1, idProduit);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Produit introuvable");
                        holder.prix = rs.getBigDecimal("prix");
                        if (rs.getInt("stock") < quantite)
                            throw new SQLException("Stock insuffisant");
                    }
                }

                try (PreparedStatement ps = cn.prepareStatement(
                        "INSERT INTO Commande(idClient) VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, idClient);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("ID commande absent");
                        int idCommande = keys.getInt(1);

                        try (PreparedStatement ligne = cn.prepareStatement(
                                "INSERT INTO LigneDeCommande(idCommande,idProduit,quantite,prixUnitaire) " +
                                        "VALUES(?,?,?,?)")) {
                            ligne.setInt(1, idCommande);
                            ligne.setInt(2, idProduit);
                            ligne.setInt(3, quantite);
                            ligne.setBigDecimal(4, holder.prix);
                            ligne.executeUpdate();
                        }
                    }
                }

                try (PreparedStatement ps = cn.prepareStatement(
                        "UPDATE Produit SET stock=stock-? WHERE idProduit=?")) {
                    ps.setInt(1, quantite);
                    ps.setInt(2, idProduit);
                    ps.executeUpdate();
                }

                cn.commit();
            } catch (SQLException e) {
                cn.rollback();
                throw e;
            } finally {
                cn.setAutoCommit(true);
            }

            resp.sendRedirect(req.getContextPath() + "/commandes");

        } catch (SQLException e) {
            throw new ServletException("Impossible d'enregistrer la commande", e);
        }
    }

    private static class BigDecimalHolder {
        java.math.BigDecimal prix;
    }
}
