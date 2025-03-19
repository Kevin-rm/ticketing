<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="helper" uri="http://www.matsd.mg/javaframework/tags/jsp-helpers" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">Configuration des prix du vol</h3>
            <a href="<c:url value="/backoffice/vols"/>" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Retour à la liste
            </a>
        </div>

        <jsp:useBean id="flight" scope="request" type="mg.itu.ticketing.entity.Flight"/>
        <jsp:useBean id="seats" scope="request" type="java.util.List<mg.itu.ticketing.entity.Seat>"/>
        <jsp:useBean id="pricingMap" scope="request" type="java.util.Map<java.lang.Integer, mg.itu.ticketing.request.SeatPricingRequest>"/>
        
        <div class="card shadow-sm mb-4">
            <div class="card-header">
                <h5 class="mb-0">Détails du vol</h5>
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <h6 class="fw-bold">ID du vol</h6>
                        <p>${flight.id}</p>
                    </div>
                    <div class="col-md-4 mb-3">
                        <h6 class="fw-bold">De</h6>
                        <p>${flight.departureCity.name}</p>
                    </div>
                    <div class="col-md-4 mb-3">
                        <h6 class="fw-bold">Vers</h6>
                        <p>${flight.arrivalCity.name}</p>
                    </div>
                    <div class="col-md-4 mb-3">
                        <h6 class="fw-bold">Date de départ</h6>
                        <p>${flight.departureTimestamp}</p>
                    </div>
                    <div class="col-md-4 mb-3">
                        <h6 class="fw-bold">Avion</h6>
                        <p>${flight.plane.model}</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header">
                <h5 class="mb-0">Configuration des prix par type de siège</h5>
            </div>
            <div class="card-body p-4">
                <form method="post" class="row g-4">
                    <c:forEach items="${seats}" var="seat">
                        <div class="col-md-4">
                            <div class="card">
                                <div class="card-header bg-light">
                                    <h6 class="mb-0">${seat.seatType.designation}</h6>
                                </div>
                                <div class="card-body">
                                    <p class="mb-2">Nombre de sièges disponibles: ${seat.seatsCount}</p>
                                    <div class="mb-3">
                                        <label for="price-${seat.id}" class="form-label fw-semibold">Prix unitaire (Ar)</label>
                                        <input type="number"
                                               class="form-control"
                                               id="price-${seat.id}"
                                               name="prices[${seat.id}].unitPrice"
                                               value="${pricingMap[seat.id].unitPrice}"
                                               min="0"
                                               step="0.01"
                                               required>
                                        <input type="hidden" name="prices[${seat.id}].seatId" value="${seat.id}">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <div class="col-12">
                        <hr class="mb-4">
                        <button type="submit" class="btn btn-primary px-2">
                            <i class="bi bi-check-lg"></i>
                            Enregistrer les prix
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
