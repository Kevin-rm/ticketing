<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/base">
    <layout:put block="body">
        <div class="container-fluid min-vh-100 d-flex align-items-center justify-content-center bg-light py-5">
            <div class="card shadow-lg" style="max-width: 400px; width: 100%;">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <h1 class="h3 mb-2 fw-bold">Bienvenue</h1>
                        <p class="text-muted">Connectez-vous à votre compte</p>
                    </div>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-exclamation-triangle-fill me-2" viewBox="0 0 16 16">
                                <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                            </svg>
                            <div>${error}</div>
                        </div>
                    </c:if>

                    <form action="<c:url value="/connexion"/>" method="post">
                        <div class="mb-3">
                            <label for="email" class="form-label">Adresse email</label>
                            <input type="email" class="form-control" id="email" name="email" 
                                   value="${l.email}" required>
                        </div>

                        <div class="mb-4">
                            <label for="password" class="form-label">Mot de passe</label>
                            <input type="password" class="form-control" id="password" 
                                   name="password" required>
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
