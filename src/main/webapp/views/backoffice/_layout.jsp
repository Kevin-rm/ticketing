<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="base">
    <layout:put block="body">
        <div class="d-flex flex-column min-vh-100">
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                <div class="container-fluid">
                    <a class="navbar-brand" href="<c:url value="/admin/dashboard"/>">Ticketing</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link ${pageContext.request.requestURI.contains('/flights') ? 'active' : ''}" href="<c:url value="/admin/flights"/>">Vols</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${pageContext.request.requestURI.contains('/planes') ? 'active' : ''}" href="<c:url value="/admin/planes"/>">Avions</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${pageContext.request.requestURI.contains('/cities') ? 'active' : ''}" href="<c:url value="/admin/cities"/>">Villes</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${pageContext.request.requestURI.contains('/users') ? 'active' : ''}" href="<c:url value="/admin/users"/>">Utilisateurs</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link ${pageContext.request.requestURI.contains('/settings') ? 'active' : ''}" href="<c:url value="/admin/settings"/>">Paramètres</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:out value="${sessionScope.user.firstname} ${sessionScope.user.lastname}"/>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="<c:url value="/admin/profile"/>">Mon profil</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="<c:url value="/deconnexion"/>">Déconnexion</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="container-fluid flex-grow-1 py-4">
                <c:if test="${not empty success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                            ${success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                            ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <layout:block name="content"/>
            </div>

            <footer class="footer mt-auto py-3 bg-light">
                <div class="container-fluid">
                    <span class="text-muted">Système de Réservation © 2025</span>
                </div>
            </footer>
        </div>
    </layout:put>
</layout:extends>
