<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="_layout">
    <layout:put block="form-title">
        <h3 class="card-title">Bienvenue</h3>
        <h6 class="card-subtitle text-body-secondary mb-4">Connectez-vous à votre compte</h6>
    </layout:put>

    <layout:put block="form-content">
        <jsp:useBean id="l" scope="request" type="mg.itu.ticketing.request.LoginRequest"/>
        <form action="<c:url value="/connexion"/>" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input id="email"
                       type="email"
                       class="form-control"
                       name="l.email"
                       value="${l.email}"
                       placeholder="Votre adresse e-mail"
                       required>
            </div>
            <div class="mb-4">
                <label for="password" class="form-label">Mot de passe</label>
                <input id="password"
                       type="password"
                       class="form-control"
                       name="l.password"
                       placeholder="Votre mot de passe"
                       required>
            </div>
            <button type="submit" class="btn btn-primary w-100 mb-3">
                Se connecter
            </button>
            <div class="text-center">
                <span class="text-muted">Vous n'avez pas de compte ?</span>
                <a href="<c:url value="/inscription"/>" class="text-decoration-none">
                    Inscrivez-vous
                </a>
            </div>
        </form>
    </layout:put>
</layout:extends>
