<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="auth" uri="http://www.matsd.mg/javaframework/tags/auth-helpers" %>

<layout:extends file="/views/base">
    <layout:put block="body">
        <div class="d-flex flex-column min-vh-100">
            <nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="<c:url value="/vols"/>">Ticketing</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/"/>">Accueil</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/vols"/>">Vols</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/mes-reservations"/>">Mes réservations</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ms-auto">
                            <c:choose>
                                <c:when test="${auth:isUserAuthenticated()}">
                                    <%@ include file="/views/partials/user-menu.jsp" %>
                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item">
                                        <a class="nav-link" href="<c:url value="/connexion"/>">Se connecter</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="container-fluid flex-grow-1 py-4">
                <%@ include file="/views/partials/alert-messages.jsp" %>

                <layout:block name="content"/>
            </div>
            
            <footer class="bg-light py-3 mt-auto">
                <div class="container text-center">
                    <p class="mb-0">© 2025 Ticketing - Système de réservation de billets</p>
                </div>
            </footer>
        </div>
    </layout:put>
</layout:extends>
