<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/frontoffice/_layout">
    <layout:put block="content">
        <h2 class="mb-4 text-primary">Rechercher un vol</h2>

        <div class="card mb-4">
            <div class="card-body">
                <form class="row g-3" method="get">
                    <jsp:useBean id="f" scope="request" type="mg.itu.ticketing.request.FlightSearchRequest"/>
                    <jsp:useBean id="cities" scope="request" type="java.util.List<mg.itu.ticketing.entity.City>"/>

                    <div class="col-md-6">
                        <label for="departure-city" class="form-label">Ville de départ</label>
                        <select class="form-select" id="departure-city" name="f.departureCityId" required>
                            <option value="">Sélectionnez une ville</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}" ${f.departureCityId == city.id ? "selected" : ""}>
                                    ${city.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label for="arrival-city" class="form-label">Ville d'arrivée</label>
                        <select class="form-select" id="arrival-city" name="f.arrivalCityId" required>
                            <option value="">Sélectionnez une ville</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}" ${f.arrivalCityId == city.id ? "selected" : ""}>
                                    ${city.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label for="min-departure-timestamp" class="form-label">Date de départ (à partir de)</label>
                        <input type="datetime-local" 
                               class="form-control" 
                               id="min-departure-timestamp"
                               name="f.minDepartureTimestamp"
                               value="${f.minDepartureTimestamp}">
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-search"></i> Rechercher
                        </button>
                        <a href="<c:url value="/vols"/>" class="btn btn-outline-secondary">
                            <i class="bi bi-x-lg"></i> Réinitialiser
                        </a>
                    </div>
                </form>
            </div>
        </div>

        <jsp:useBean id="flights" scope="request" type="java.util.List<mg.itu.ticketing.entity.Flight>"/>
        
        <h3 class="mb-3">Résultats de recherche</h3>
        
        <c:choose>
            <c:when test="${empty flights}">
                <div class="alert alert-info">
                    Aucun vol disponible selon vos critères de recherche.
                </div>
            </c:when>
            <c:otherwise>
                <div class="row row-cols-1 row-cols-md-2 g-4">
                    <c:forEach items="${flights}" var="flight">
                        <div class="col">
                            <div class="card h-100">
                                <div class="card-header bg-primary text-white">
                                    Vol #${flight.id} - ${flight.departureCity.name} → ${flight.arrivalCity.name}
                                </div>
                                <div class="card-body">
                                    <h5 class="card-title">${flight.plane.model}</h5>
                                    <p class="card-text">
                                        <strong>Date et heure de départ :</strong><br>
                                        <fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                                        <fmt:formatDate value="${parsedDate}" pattern="dd MMMM yyyy 'à' HH:mm" />
                                    </p>
                                    <a href="<c:url value="/vols/${flight.id}/reserver"/>" class="btn btn-primary">
                                        <i class="bi bi-ticket-perforated"></i> Réserver
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </layout:put>
</layout:extends>
