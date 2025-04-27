<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/frontoffice/_layout">
    <layout:put block="content">
        <h3 class="fw-bold text-primary mb-4">Rechercher un vol</h3>

        <div class="card mb-4">
            <div class="card-body">
                <form class="row g-3" method="get">
                    <jsp:useBean id="f" scope="request" type="mg.itu.ticketing.request.FlightSearchRequest"/>
                    <jsp:useBean id="cities" scope="request" type="java.util.List<mg.itu.ticketing.entity.City>"/>

                    <div class="col-md-6 col-lg-3">
                        <label for="departure-city" class="form-label">Ville de départ</label>
                        <select class="form-select" id="departure-city" name="f.departureCityId">
                            <option value="">Toutes les villes</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}" ${f.departureCityId == city.id ? "selected" : ""}>
                                    ${city.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <label for="arrival-city" class="form-label">Ville d'arrivée</label>
                        <select class="form-select" id="arrival-city" name="f.arrivalCityId">
                            <option value="">Toutes les villes</option>
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.id}" ${f.arrivalCityId == city.id ? "selected" : ""}>
                                    ${city.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <label for="min-departure-timestamp" class="form-label">Date de départ au plus tôt</label>
                        <input type="datetime-local"
                               class="form-control"
                               id="min-departure-timestamp"
                               name="f.minDepartureTimestamp"
                               value="${f.minDepartureTimestamp}">
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <label for="max-departure-timestamp" class="form-label">Date de départ au plus tard</label>
                        <input type="datetime-local"
                               class="form-control"
                               id="max-departure-timestamp"
                               name="f.maxDepartureTimestamp"
                               value="${f.maxDepartureTimestamp}">
                    </div>
                    <div class="col-12">
                        <div class="d-flex gap-2 justify-content-end">
                            <a href="<c:url value="/vols"/>" class="btn btn-light">
                                <i class="bi bi-x-lg"></i> Réinitialiser
                            </a>
                            <button type="submit" class="btn btn-primary">
                                <i class="bi bi-search"></i> Rechercher
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <h3 class="fw-bold text-primary mb-4">Vols disponibles</h3>

        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
            <jsp:useBean id="flights" scope="request" type="java.util.List<mg.itu.ticketing.entity.Flight>"/>
            <c:choose>
                <c:when test="${not empty flights}">
                    <c:forEach items="${flights}" var="flight">
                        <div class="col">
                            <div class="card h-100 shadow-sm">
                                <div class="card-header bg-primary text-white">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <h5 class="mb-0">Vol #${flight.id}</h5>
                                        <span class="fw-bold">
                                            <fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="departureTimestamp"/>
                                            <fmt:formatDate value="${departureTimestamp}" pattern="dd/MM/yyyy HH:mm"/>
                                        </span>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <div class="mb-4">
                                        <div class="row text-center position-relative">
                                            <div class="col-5">
                                                <div class="fs-5 fw-bold text-truncate">
                                                    ${flight.departureCity.name}
                                                </div>
                                                <div class="text-muted">Départ</div>
                                            </div>
                                            <div class="col-2">
                                                <div class="position-absolute top-50 start-50 translate-middle px-2">
                                                    <i class="bi bi-airplane fs-4"></i>
                                                </div>
                                            </div>
                                            <div class="col-5">
                                                <div class="fs-5 fw-bold text-truncate">
                                                    ${flight.arrivalCity.name}
                                                </div>
                                                <div class="text-muted">Arrivée</div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="mb-3">
                                        <div class="fw-bold mb-2">Avion: ${flight.plane.model}</div>
                                        <div class="fw-bold mb-2">Prix des sièges: </div>
                                        <ul class="list-group">
                                            <c:forEach items="${flight.seatPricingList}" var="pricing">
                                                <li class="list-group-item d-flex justify-content-between align-items-center">
                                                    <span>${pricing.seat.seatType.designation}</span>
                                                    <span class="badge bg-primary rounded-pill">
                                                        <fmt:formatNumber value="${pricing.unitPrice}" type="currency" currencySymbol="Ar"/>
                                                    </span>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                                <div class="card-footer">
                                    <a href="<c:url value="/reservations/vol-${flight.id}/creer"/>" class="btn btn-primary w-100">
                                        <i class="bi bi-bookmark-check"></i> Réserver
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="col-12">
                        <div class="alert alert-info">
                            <i class="bi bi-info-circle me-2"></i> Aucun vol ne correspond à vos critères de recherche.
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </layout:put>
</layout:extends>
