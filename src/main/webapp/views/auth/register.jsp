<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="helper" uri="http://www.matsd.mg/javaframework/tags/jsp-helpers" %>

<layout:extends file="_layout">
    <layout:put block="form-title">
        <h3 class="card-title">Inscription</h3>
        <h6 class="card-subtitle text-body-secondary mb-4">Créez votre compte</h6>
    </layout:put>

    <layout:put block="form-content">
        <jsp:useBean id="r" scope="request" type="mg.itu.ticketing.request.RegistrationRequest"/>
        <form action="<c:url value="/inscription"/>" method="post">
            <c:set var="firstnameErrors" value="${helper:fieldErrors('r.firstname')}"/>
            <div class="mb-3">
                <label for="firstname" class="form-label">Prénom</label>
                <input id="firstname"
                       type="text"
                       class="form-control ${not empty firstnameErrors ? "is-invalid" : ""}"
                       name="r.firstname"
                       value="${r.firstname}"
                       placeholder="Votre prénom"
                       required>
                <c:if test="${not empty firstnameErrors}">
                    <div class="invalid-feedback">
                        <c:out value="${firstnameErrors[0].message}"/>
                    </div>
                </c:if>
            </div>

            <c:set var="lastnameErrors" value="${helper:fieldErrors('r.lastname')}"/>
            <div class="mb-3">
                <label for="lastname" class="form-label">Nom</label>
                <input id="lastname"
                       type="text"
                       class="form-control ${not empty lastnameErrors ? "is-invalid" : ""}"
                       name="r.lastname"
                       value="${r.lastName}"
                       placeholder="Votre nom"
                       required>
                <c:if test="${not empty lastnameErrors}">
                    <div class="invalid-feedback">
                        <c:out value="${lastnameErrors[0].message}"/>
                    </div>
                </c:if>
            </div>

            <c:set var="emailErrors" value="${helper:fieldErrors('r.email')}"/>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input id="email"
                       type="email"
                       class="form-control ${not empty emailErrors ? "is-invalid" : ""}"
                       name="r.email"
                       value="${r.email}"
                       placeholder="Votre adresse e-mail"
                       required>
                <c:if test="${not empty emailErrors}">
                    <div class="invalid-feedback">
                        <c:out value="${emailErrors[0].message}"/>
                    </div>
                </c:if>
            </div>

            <c:set var="passwordErrors" value="${helper:fieldErrors('r.password')}"/>
            <div class="mb-3">
                <label for="password" class="form-label">Mot de passe</label>
                <input id="password"
                       type="password"
                       class="form-control ${not empty passwordErrors ? "is-invalid" : ""}"
                       name="r.password"
                       placeholder="Votre mot de passe"
                       required>
                <c:if test="${not empty passwordErrors}">
                    <div class="invalid-feedback">
                        <c:out value="${passwordErrors[0].message}"/>
                    </div>
                </c:if>
            </div>

            <button type="submit" class="btn btn-primary w-100 mb-3">
                S'inscrire
            </button>
            <div class="text-center">
                <span class="text-muted">Vous avez déjà un compte ?</span>
                <a href="<c:url value="/connexion"/>" class="text-decoration-none">
                    Connectez-vous
                </a>
            </div>
        </form>
    </layout:put>
</layout:extends>
