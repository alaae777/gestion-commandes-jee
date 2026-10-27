
package ma.fstt.web;

import ma.fstt.dao.ClientDAOImpl;
import ma.fstt.entities.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {

    private final ClientDAOImpl dao = new ClientDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.delete(id);

                response.sendRedirect(request.getContextPath() + "/clients");
                return;
            }

            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));

                request.setAttribute("client", dao.findById(id));
                request.getRequestDispatcher("/clients/form.jsp")
                        .forward(request, response);
                return;
            }

            List<Client> clients = dao.findAll();
            request.setAttribute("clients", clients);

            request.getRequestDispatcher("/clients/list.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Erreur lors de la gestion des clients.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("idClient");

        Client client = new Client();
        client.setNom(request.getParameter("nom"));
        client.setPrenom(request.getParameter("prenom"));
        client.setEmail(request.getParameter("email"));
        client.setTelephone(request.getParameter("telephone"));

        try {
            if (id == null || id.isBlank()) {
                dao.create(client);
            } else {
                client.setIdClient(Integer.parseInt(id));
                dao.update(client);
            }

            response.sendRedirect(request.getContextPath() + "/clients");

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Erreur lors de l'enregistrement du client.", e);
        }
    }
}
