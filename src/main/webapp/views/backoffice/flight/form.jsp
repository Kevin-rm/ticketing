<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1 class="h3">${empty id ? "Nouveau" : "Modifier"} vol</h1>
            <a href="<c:url value="/backoffice/flights"/>" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Retour
            </a>
        </div>

        <jsp:useBean id="f" scope="request" type="mg.itu.ticketing.request.FlightRequest"/>
        <jsp:useBean id="cities" scope="request" type="java.util.List<mg.itu.ticketing.entity.City>"/>
        <div class="card">
            <div class="card-body">
                <form method="post" class="row g-3">
                    <div class="col-md-6">
                        <label for="departure-city" class="form-label">Ville de départ</label>
                        <select class="form-select"
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
                    </div>

                    <div class="col-md-6">
                        <label for="arrival-city" class="form-label">Ville d'arrivée</label>
                        <select class="form-select"
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
                    </div>

                    <div class="col-md-4">
                        <label for="plane" class="form-label">Avion</label>
                        <select class="form-select"
                                id="plane"
                                name="f.planeId"
                                required>
                            <option value="">Sélectionner un avion</option>
                            <c:forEach items="${planes}" var="plane">
                                <option value="${plane.id}" ${f.planeId == plane.id ? "selected" : ""}>
                                    ${plane.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label for="departure-timestamp" class="form-label">Date et heure de départ</label>
                        <input type="datetime-local"
                               class="form-control"
                               id="departure-timestamp"
                               name="f.departureTimestamp"
                               value="${f.departureTimestamp}"
                               required>
                    </div>
                    <div class="col-md-4">
                        <label for="arrival-timestamp" class="form-label">Date et heure d'arrivée</label>
                        <input type="datetime-local"
                               class="form-control"
                               id="arrival-timestamp"
                               name="f.arrivalTimestamp"
                               value="${f.arrivalTimestamp}"
                               required>
                    </div>

                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">
                            ${empty id ? "Créer" : "Modifier"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
