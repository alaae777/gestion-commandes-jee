
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr"><head><meta charset="UTF-8"><title>Commandes</title></head>
<body>
<h1>Gestion des commandes</h1>
<a href="${pageContext.request.contextPath}/index.jsp">Accueil</a>

<h2>Créer une commande</h2>
<c:choose>
<c:when test="${empty clients}">
    <p>Ajoute d'abord un client.</p>
</c:when>
<c:when test="${empty produits}">
    <p>Ajoute d'abord un produit.</p>
</c:when>
<c:otherwise>
<form method="post" action="${pageContext.request.contextPath}/commandes">
    <label>Client :
        <select name="idClient" required>
            <c:forEach var="c" items="${clients}">
                <option value="${c.id}"><c:out value="${c.nom}"/> <c:out value="${c.prenom}"/></option>
            </c:forEach>
        </select>
    </label><br><br>
    <label>Produit :
        <select name="idProduit" required>
            <c:forEach var="p" items="${produits}">
                <option value="${p.id}">
                    <c:out value="${p.nom}"/> — <c:out value="${p.prix}"/> DH
                    (stock : <c:out value="${p.stock}"/>)
                </option>
            </c:forEach>
        </select>
    </label><br><br>
    <label>Quantité :
        <input type="number" name="quantite" min="1" required>
    </label><br><br>
    <button type="submit">Créer la commande</button>
</form>
</c:otherwise>
</c:choose>

<h2>Commandes enregistrées</h2>
<table border="1" cellpadding="8">
<tr><th>Numéro</th><th>Date</th><th>Client</th></tr>
<c:forEach var="c" items="${commandes}">
<tr>
    <td><c:out value="${c.id}"/></td>
    <td><c:out value="${c.date}"/></td>
    <td><c:out value="${c.nom}"/> <c:out value="${c.prenom}"/></td>
</tr>
</c:forEach>
</table>
</body></html>
