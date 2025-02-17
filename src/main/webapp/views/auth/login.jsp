<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/base">
    <layout:put block="title" type="REPLACE">Connexion - Ticketing</layout:put>

    <layout:put block="styles">
        <style>
            .login-container {
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f8f9fa;
            }

            .login-form {
                background: white;
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            .login-title {
                text-align: center;
                margin-bottom: 1.5rem;
                color: #333;
            }
        </style>
    </layout:put>

    <layout:put block="body">
        <div class="login-container">
            <div class="login-form">
                <h2 class="login-title">Connexion</h2>
                <form action="<c:url value="/login"/>" method="POST">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Mot de passe</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Se connecter</button>
                    <div class="text-center mt-3">
                        <span>Pas encore de compte ?
                            <a href="<c:url value="/inscription"/>" class="text-decoration-none">S'inscrire</a>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
