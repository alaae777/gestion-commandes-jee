
package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.service.ClientService;
import ma.fstt.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientService {

    @Override
    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();

        String sql = "SELECT * FROM Client";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("idClient"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone")
                );

                clients.add(client);
            }
        }

        return clients;
    }

    @Override
    public Client findById(int id) throws SQLException {
        String sql = "SELECT * FROM Client WHERE idClient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("idClient"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("telephone")
                    );
                }
            }
        }

        return null;
    }

    @Override
    public void create(Client client) throws SQLException {
        String sql = """
            INSERT INTO Client (nom, prenom, email, telephone)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getTelephone());

            ps.executeUpdate();
        }
    }

    @Override
    public void update(Client client) throws SQLException {
        String sql = """
            UPDATE Client
            SET nom = ?, prenom = ?, email = ?, telephone = ?
            WHERE idClient = ?
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getEmail());
            ps.setString(4, client.getTelephone());
            ps.setInt(5, client.getIdClient());

            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Client WHERE idClient = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
