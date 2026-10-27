
package ma.fstt.service;

import ma.fstt.entities.Client;
import java.sql.SQLException;
import java.util.List;

public interface ClientService {
    List<Client> findAll() throws SQLException;

    Client findById(int id) throws SQLException;

    void create(Client client) throws SQLException;

    void update(Client client) throws SQLException;

    void delete(int id) throws SQLException;
}
