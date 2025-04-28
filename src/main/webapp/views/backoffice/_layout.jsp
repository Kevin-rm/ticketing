<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="auth" uri="http://www.matsd.mg/javaframework/tags/auth-helpers" %>

<layout:extends file="/views/base">
    <layout:put block="body">
        <div class="d-flex flex-column min-vh-100">
            <nav class="navbar navbar-expand-lg bg-dark" data-bs-theme="dark">
                <div class="container-fluid">
                    <a class="navbar-brand" href="<c:url value="/backoffice/vols"/>">Ticketing</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Vols
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a class="dropdown-item" href="<c:url value="/backoffice/vols"/>">Liste</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value="/backoffice/vols/creer"/>">Création</a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Promotions
                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a class="dropdown-item" href="<c:url value="/backoffice/promotions"/>">Liste</a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item"
                                           href="<c:url value="/backoffice/promotions/creer"/>">Création</a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<c:url value="/backoffice/parametres"/>">Paramètres</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ms-auto">
                            <%@ include file="/views/partials/user-menu.jsp" %>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="container-fluid flex-grow-1 py-4">
                <%@ include file="/views/partials/alert-messages.jsp" %>

                <layout:block name="content"/>
            </div>
        </div>
    </layout:put>
</layout:extends>
