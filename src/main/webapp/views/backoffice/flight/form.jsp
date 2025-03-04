<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="helper" uri="http://www.matsd.mg/javaframework/tags/jsp-helpers" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3 fw-bold text-primary">
                ${empty id ? "Nouveau" : "Modifier le"} vol
            </h1>
            <a href="<c:url value="/backoffice/vols"/>" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Retour à la liste
            </a>
        </div>

        <jsp:useBean id="f" scope="request" type="mg.itu.ticketing.request.FlightRequest"/>
        <jsp:useBean id="cities" scope="request" type="java.util.List<mg.itu.ticketing.entity.City>"/>
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <form method="post" class="row g-4">
                    <div class="col-md-6">
                        <div class="row g-3">
                            <div class="col-12">
                                <label for="departure-city" class="form-label fw-semibold">
                                    Ville de départ
                                </label>
                                <c:set var="departureCityErrors" value="${helper:fieldErrors('f.departureCityId')}"/>
                                <select class="form-select ${not empty departureCityErrors ? "is-invalid" : ""}"
                                        id="departure-city"
                                        name="f.departureCityId"
                                        required>
                                    <option value="">Sélectionner une ville</option>
                                    <c:forEach items="${cities}" var="city">
                                        <option value="${city.id}" ${f.departureCityId == city.id ? "selected" : ""}>
                                            ${city.name}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${not empty departureCityErrors}">
                                    <div class="invalid-feedback">
                                        <c:out value="${departureCityErrors[0].message}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-12">
                                <label for="departure-timestamp" class="form-label fw-semibold">
                                    Date et heure de départ
                                </label>
                                <c:set var="departureTimestampErrors" value="${helper:fieldErrors('f.departureTimestamp')}"/>
                                <input type="datetime-local"
                                       class="form-control ${not empty departureTimestampErrors ? "is-invalid" : ""}"
                                       id="departure-timestamp"
                                       name="f.departureTimestamp"
                                       value="${f.departureTimestamp}"
                                       required>
                                <c:if test="${not empty departureTimestampErrors}">
                                    <div class="invalid-feedback">
                                        <c:out value="${departureTimestampErrors[0].message}"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <div class="row g-3">
                            <div class="col-12">
                                <label for="arrival-city" class="form-label fw-semibold">
                                    Ville d'arrivée
                                </label>
                                <c:set var="arrivalCityErrors" value="${helper:fieldErrors('f.arrivalCityId')}"/>
                                <select class="form-select ${not empty arrivalCityErrors ? "is-invalid" : ""}"
                                        id="arrival-city"
                                        name="f.arrivalCityId"
                                        required>
                                    <option value="">Sélectionner une ville</option>
                                    <c:forEach items="${cities}" var="city">
                                        <option value="${city.id}" ${f.arrivalCityId == city.id ? "selected" : ""}>
                                            ${city.name}
                                        </option>
                                    </c:forEach>
                                </select>
                                <c:if test="${not empty arrivalCityErrors}">
                                    <div class="invalid-feedback">
                                        <c:out value="${arrivalCityErrors[0].message}"/>
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-12">
                                <label for="arrival-timestamp" class="form-label fw-semibold">
                                    Date et heure d'arrivée
                                </label>
                                <c:set var="arrivalTimestampErrors" value="${helper:fieldErrors('f.arrivalTimestamp')}"/>
                                <input type="datetime-local"
                                       class="form-control ${not empty arrivalTimestampErrors ? "is-invalid" : ""}"
                                       id="arrival-timestamp"
                                       name="f.arrivalTimestamp"
                                       value="${f.arrivalTimestamp}"
                                       required>
                                <c:if test="${not empty arrivalTimestampErrors}">
                                    <div class="invalid-feedback">
                                        <c:out value="${arrivalTimestampErrors[0].message}"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <div class="col-12">
                        <label for="plane" class="form-label fw-semibold">
                            Avion
                        </label>
                        <c:set var="planeErrors" value="${helper:fieldErrors('f.planeId')}"/>
                        <select class="form-select ${not empty planeErrors ? "is-invalid" : ""}"
                                id="plane"
                                name="f.planeId"
                                required>
                            <option value="">Sélectionner un avion</option>
                            <jsp:useBean id="planes" scope="request" type="java.util.List<mg.itu.ticketing.entity.Plane>"/>
                            <c:forEach items="${planes}" var="plane">
                                <option value="${plane.id}" ${f.planeId == plane.id ? "selected" : ""}>
                                    ${plane.model}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty planeErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${planeErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-12 mt-4">
                        <hr class="mb-4">
                        <button type="submit" class="btn btn-primary px-2">
                            <i class="bi bi-${empty id ? "plus-lg" : "check-lg"}"></i>
                            ${empty id ? "Créer le vol" : "Enregistrer les modifications"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
