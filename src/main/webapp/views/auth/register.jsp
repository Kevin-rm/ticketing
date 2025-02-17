<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/base">
    <layout:put block="title">Inscription - Ticketing</layout:put>

    <layout:put block="styles">
        <style>
            .register-container {
                min-height: 100vh;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f8f9fa;
            }

            .register-form {
                background: white;
                padding: 2rem;
                border-radius: 10px;
                box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 400px;
            }

            .register-title {
                text-align: center;
                margin-bottom: 1.5rem;
                color: #333;
            }
        </style>
    </layout:put>

    <layout:put block="body">
        <div class="register-container">
            <div class="register-form">
                <h2 class="register-title">Inscription</h2>
                <form action="<c:url value="/register"/>" method="POST">
                    <div class="mb-3">
                        <label for="name" class="form-label">Nom complet</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Mot de passe</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="password_confirmation" class="form-label">Confirmer le mot de passe</label>
                        <input type="password" class="form-control" id="password_confirmation"
                               name="password_confirmation" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">S'inscrire</button>
                    <div class="text-center mt-3">
                        <span>Déjà inscrit ?
                            <a href="<c:url value="/connexion"/>" class="text-decoration-none">Se connecter</a>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
