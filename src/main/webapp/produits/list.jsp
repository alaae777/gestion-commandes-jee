
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr"><head><meta charset="UTF-8"><title>Produits</title></head>
<body>
<h1>Gestion des produits</h1>
<a href="${pageContext.request.contextPath}/index.jsp">Accueil</a>

<h2>Ajouter un produit</h2>
<form method="post" action="${pageContext.request.contextPath}/produits">
    <label>Nom : <input name="nom" required></label><br><br>
    <label>Description : <input name="description"></label><br><br>
    <label>Prix : <input type="number" name="prix" min="0.01" step="0.01" required></label><br><br>
    <label>Stock : <input type="number" name="stock" min="0" required></label><br><br>
    <button type="submit">Ajouter</button>
</form>

<h2>Liste des produits</h2>
<table border="1" cellpadding="8">
<tr><th>ID</th><th>Nom</th><th>Description</th><th>Prix</th><th>Stock</th><th>Action</th></tr>
<c:forEach var="p" items="${produits}">
<tr>
    <td><c:out value="${p.id}"/></td>
    <td><c:out value="${p.nom}"/></td>
    <td><c:out value="${p.description}"/></td>
    <td><c:out value="${p.prix}"/></td>
    <td><c:out value="${p.stock}"/></td>
    <td><a href="${pageContext.request.contextPath}/produits?action=delete&id=${p.id}"
       onclick="return confirm('Supprimer ce produit ?')">Supprimer</a></td>
</tr>
</c:forEach>
</table>
</body></html>
