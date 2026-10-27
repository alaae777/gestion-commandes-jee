
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Formulaire client</title></head>
<body>
<h1>Formulaire client</h1>

<form method="post" action="${pageContext.request.contextPath}/clients">
    <input type="hidden" name="idClient" value="<c:out value='${client.idClient}'/>">

    <label>Nom :</label>
    <input type="text" name="nom" required value="<c:out value='${client.nom}'/>">
    <br><br>

    <label>Prénom :</label>
    <input type="text" name="prenom" required value="<c:out value='${client.prenom}'/>">
    <br><br>

    <label>Email :</label>
    <input type="email" name="email" value="<c:out value='${client.email}'/>">
    <br><br>

    <label>Téléphone :</label>
    <input type="text" name="telephone" value="<c:out value='${client.telephone}'/>">
    <br><br>

    <button type="submit">Enregistrer</button>
</form>


<p><a href="${pageContext.request.contextPath}/clients">Retour à la liste</a></p>
</body>
</html>
