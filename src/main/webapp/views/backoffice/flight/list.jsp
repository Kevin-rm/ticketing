<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">Liste des vols</h3>
            <a href="<c:url value="/backoffice/vols/creer"/>" class="btn btn-outline-secondary">
                <i class="bi bi-plus-lg"></i> Nouveau vol
            </a>
        </div>

        <div class="card mb-4">
            <h5 class="card-header">Filtrer les vols</h5>
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
                        <label for="min-departure-timestamp" class="form-label">Date et heure de départ minimum</label>
                        <input type="datetime-local" 
                               class="form-control" 
                               id="min-departure-timestamp"
                               name="f.minDepartureTimestamp"
                               value="${f.minDepartureTimestamp}">
                    </div>
                    <div class="col-md-6 col-lg-3">
                        <label for="max-departure-timestamp" class="form-label">Date et heure de départ maximum</label>
                        <input type="datetime-local" 
                               class="form-control" 
                               id="max-departure-timestamp"
                               name="f.maxDepartureTimestamp"
                               value="${f.maxDepartureTimestamp}">
                    </div>
                    <div class="col-12">
                        <div class="d-flex gap-2 justify-content-end">
                            <a href="<c:url value="/backoffice/vols"/>" class="btn btn-light">
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

        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Date et heure de départ</th>
                        <th>Ville de départ</th>
                        <th>Ville d'arrivée</th>
                        <th>Avion</th>
                        <th class="text-center">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <jsp:useBean id="flights" scope="request" type="java.util.List<mg.itu.ticketing.entity.Flight>"/>
                    <c:forEach items="${flights}" var="flight">
                        <tr>
                            <th scope="row">${flight.id}</th>
                            <td>${flight.departureTimestamp}</td>
                            <td>${flight.departureCity.name}</td>
                            <td>${flight.arrivalCity.name}</td>
                            <td>${flight.plane.model}</td>
                            <td class="text-center">
                                <div class="btn-group btn-group-sm">
                                    <a href="<c:url value="/backoffice/vols/${flight.id}/modifier"/>"
                                       class="btn btn-primary">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <button type="button"
                                            class="btn btn-danger"
                                            data-bs-toggle="modal"
                                            data-bs-target="#delete-flight-modal_${flight.id}">
                                        <i class="bi bi-trash"></i>
                                    </button>
                                </div>

                                <div class="modal fade" id="delete-flight-modal_${flight.id}" tabindex="-1">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title">Confirmation</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                Voulez-vous vraiment supprimer ce vol ?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                                <form action="<c:url value="/backoffice/vols/${flight.id}/supprimer"/>" method="post" class="d-inline">
                                                    <button type="submit" class="btn btn-danger">Supprimer</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </layout:put>
</layout:extends>
