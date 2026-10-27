
package ma.fstt.web;

import ma.fstt.util.DatabaseConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try (Connection cn = DatabaseConnection.getConnection()) {

            if ("delete".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));

                try (PreparedStatement ps = cn.prepareStatement(
                        "DELETE FROM Produit WHERE idProduit = ?")) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                }

                resp.sendRedirect(req.getContextPath() + "/produits");
                return;
            }

            List<Map<String, Object>> produits = new ArrayList<>();

            String sql = "SELECT idProduit, nom, description, prix, stock " +
                    "FROM Produit ORDER BY nom";

            try (PreparedStatement ps = cn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Map<String, Object> p = new HashMap<>();
                    p.put("id", rs.getInt("idProduit"));
                    p.put("nom", rs.getString("nom"));
                    p.put("description", rs.getString("description"));
                    p.put("prix", rs.getBigDecimal("prix"));
                    p.put("stock", rs.getInt("stock"));
                    produits.add(p);
                }
            }

            req.setAttribute("produits", produits);
            req.getRequestDispatcher("/produits/list.jsp")
                    .forward(req, resp);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(
                    "Erreur lors du chargement des produits.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String nom = req.getParameter("nom");
        String description = req.getParameter("description");

        try {
            java.math.BigDecimal prix =
                    new java.math.BigDecimal(req.getParameter("prix"));
            int stock = Integer.parseInt(req.getParameter("stock"));

            if (nom == null || nom.trim().isEmpty()
                    || prix.signum() <= 0 || stock < 0) {
                resp.sendError(400, "Données du produit invalides.");
                return;
            }

            try (Connection cn = DatabaseConnection.getConnection();
                 PreparedStatement ps = cn.prepareStatement(
                         "INSERT INTO Produit (nom, description, prix, stock) " +
                                 "VALUES (?, ?, ?, ?)")) {

                ps.setString(1, nom.trim());
                ps.setString(2, description);
                ps.setBigDecimal(3, prix);
                ps.setInt(4, stock);
                ps.executeUpdate();
            }

            resp.sendRedirect(req.getContextPath() + "/produits");

        } catch (NumberFormatException e) {
            resp.sendError(400, "Le prix et le stock doivent être numériques.");
        } catch (SQLException e) {
            throw new ServletException(
                    "Erreur lors de l'ajout du produit.", e);
        }
    }
}
