<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/base">
    <layout:put block="body">
        <jsp:useBean id="l" scope="request" type="mg.itu.ticketing.request.LoginRequest"/>
    
        <div class="container-fluid min-vh-100 d-flex align-items-center justify-content-center bg-light py-5">
            <div class="card shadow-lg" style="max-width: 400px; width: 100%;">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <h1 class="h3 mb-2 fw-bold">Bienvenue</h1>
                        <p class="text-muted">Connectez-vous à votre compte</p>
                    </div>

                    <%@ include file="/views/partials/error.jsp" %>
                    <form action="<c:url value="/connexion"/>" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Adresse email</label>
                            <input id="email"
                                   type="email"
                                   class="form-control"
                                   name="l.email"
                                   value="${l.email}"
                                   required>
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label">Mot de passe</label>
                            <input id="password"
                                   type="password"
                                   class="form-control"
                                   name="l.password"
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
                </div>
            </div>
        </div>
    </layout:put>
</layout:extends>
