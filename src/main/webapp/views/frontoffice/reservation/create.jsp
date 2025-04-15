<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="helper" uri="http://www.matsd.mg/javaframework/tags/jsp-helpers" %>

<layout:extends file="/views/frontoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">Création d'une réservation</h3>
            <a href="<c:url value="/vols"/>" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Retour aux vols
            </a>
        </div>

        <jsp:useBean id="flight" scope="request" type="mg.itu.ticketing.entity.Flight"/>
        <jsp:useBean id="r" scope="request" type="mg.itu.ticketing.request.ReservationRequest"/>
        
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Détails du vol #${flight.id}</h5>
            </div>
            <div class="card-body">
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <h6 class="fw-bold">Trajet</h6>
                            <p class="fs-5">${flight.departureCity.name} → ${flight.arrivalCity.name}</p>
                        </div>
                        <div class="mb-3">
                            <h6 class="fw-bold">Date et heure de départ</h6>
                            <p>
                                <fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="departureDate"/>
                                <fmt:formatDate value="${departureDate}" pattern="dd/MM/yyyy HH:mm"/>
                            </p>
                        </div>
                        <div class="mb-3">
                            <h6 class="fw-bold">Avion</h6>
                            <p>${flight.plane.model}</p>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <h6 class="fw-bold mb-2">Prix des sièges</h6>
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
            </div>
        </div>

        <div class="card shadow-sm">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Informations de réservation</h5>
            </div>
            <div class="card-body p-4">
                <form method="post" class="row g-4">
                    <div class="col-md-12">
                        <label for="seat-pricing" class="form-label fw-semibold">
                            Type de siège
                        </label>
                        <c:set var="seatPricingIdErrors" value="${helper:fieldErrors('r.seatPricingId')}"/>
                        <select class="form-select ${not empty seatPricingIdErrors ? "is-invalid" : ""}"
                                id="seat-pricing"
                                name="r.seatPricingId"
                                required>
                            <option value="">Sélectionner un type de siège</option>
                            <c:forEach items="${flight.seatPricingList}" var="pricing">
                                <option value="${pricing.id}" ${r.seatPricingId == pricing.id ? "selected" : ""}>
                                    ${pricing.seat.seatType.designation} - 
                                    <fmt:formatNumber value="${pricing.unitPrice}" type="currency" currencySymbol="Ar"/>
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${not empty seatPricingIdErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${seatPricingIdErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label for="adult-count" class="form-label fw-semibold">
                            Nombre d'adultes
                        </label>
                        <c:set var="adultCountErrors" value="${helper:fieldErrors('r.adultCount')}"/>
                        <input type="number"
                               class="form-control ${not empty adultCountErrors ? "is-invalid" : ""}"
                               id="adult-count"
                               name="r.adultCount"
                               value="${r.adultCount}"
                               min="0"
                               required>
                        <c:if test="${not empty adultCountErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${adultCountErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label for="child-count" class="form-label fw-semibold">
                            Nombre d'enfants
                        </label>
                        <c:set var="childCountErrors" value="${helper:fieldErrors('r.childCount')}"/>
                        <input type="number"
                               class="form-control ${not empty childCountErrors ? "is-invalid" : ""}"
                               id="child-count"
                               name="r.childCount"
                               value="${r.childCount}"
                               min="0"
                               required>
                        <c:if test="${not empty childCountErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${childCountErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-12">
                        <hr class="mb-4">
                        <div class="alert alert-info">
                            <i class="bi bi-info-circle me-2"></i> Assurez-vous que le nombre total de passagers (adultes + enfants) ne dépasse pas la capacité disponible.
                        </div>
                        <button type="submit" class="btn btn-primary px-4">
                            <i class="bi bi-check-lg"></i>
                            Confirmer la réservation
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
