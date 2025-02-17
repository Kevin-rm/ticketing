<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
        <layout:block name="title">Ticketing</layout:block>
    </title>
    <link rel="stylesheet" href="<c:url value="/public/assets/css/bootstrap.min.css"/>">
    <layout:block name="styles"/>
</head>
<body>
    <layout:block name="body"/>

    <script src="<c:url value="/public/assets/js/bootstrap.bundle.min.js"/>"></script>
    <layout:block name="scripts"/>
</body>
</html>
