
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des commandes</title>
</head>
<body>
    <h1>Application de gestion des commandes</h1>
    <p>Bienvenue dans notre application web JEE.</p>

    <h2>Menu principal</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/clients">Gestion des clients</a></li>
        <li><a href="${pageContext.request.contextPath}/produits">Gestion des produits</a></li>
        <li><a href="${pageContext.request.contextPath}/commandes">Gestion des commandes</a></li>
    </ul>
</body>
</html>
