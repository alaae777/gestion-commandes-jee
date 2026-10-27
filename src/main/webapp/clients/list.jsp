
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Liste des clients</title></head>
<body>
<h1>Liste des clients</h1>
<a href="${pageContext.request.contextPath}/index.jsp">Accueil</a> |
<a href="${pageContext.request.contextPath}/clients?action=new">Ajouter un client</a>

<table border="1" cellpadding="8">
    <tr>
        <th>ID</th><th>Nom</th><th>Prénom</th><th>Email</th><th>Téléphone</th><th>Actions</th>
    </tr>
    <c:forEach var="c" items="${clients}">
        <tr>
            <td><c:out value="${c.idClient}"/></td>
            <td><c:out value="${c.nom}"/></td>
            <td><c:out value="${c.prenom}"/></td>
            <td><c:out value="${c.email}"/></td>
            <td><c:out value="${c.telephone}"/></td>
            <td>
                <a href="${pageContext.request.contextPath}/clients?action=edit&id=${c.idClient}">Modifier</a>
                |
                <a href="${pageContext.request.contextPath}/clients?action=delete&id=${c.idClient}"
                   onclick="return confirm('Supprimer ce client ?')">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
