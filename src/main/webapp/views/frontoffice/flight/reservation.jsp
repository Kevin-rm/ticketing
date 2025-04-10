<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/frontoffice/_layout">
    <layout:put block="content">
        <jsp:useBean id="flight" scope="request" type="mg.itu.ticketing.entity.Flight"/>
        <jsp:useBean id="seatTypes" scope="request" type="java.util.List<java.util.Map>"/>
        
        <h2 class="mb-4 text-primary">Réservation de vol</h2>
        
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                Détails du vol
            </div>
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h5>Vol #${flight.id}</h5>
                        <p>
                            <strong>Trajet:</strong> ${flight.departureCity.name} → ${flight.arrivalCity.name}<br>
                            <strong>Date et heure de départ:</strong> 
                            <fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                            <fmt:formatDate value="${parsedDate}" pattern="dd MMMM yyyy 'à' HH:mm" /><br>
                            <strong>Avion:</strong> ${flight.plane.model}
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <div class="card">
            <div class="card-header bg-primary text-white">
                Sélection des sièges
            </div>
            <div class="card-body">
                <form action="<c:url value="/vols/${flight.id}/reserver"/>" method="post">
                    <div class="row">
                        <c:forEach items="${seatTypes}" var="seatType" varStatus="status">
                            <div class="col-md-4 mb-3">
                                <div class="card h-100">
                                    <div class="card-header">
                                        ${seatType.type}
                                    </div>
                                    <div class="card-body">
                                        <p class="card-text">
                                            <strong>Prix unitaire:</strong> ${seatType.unitPrice} €<br>
                                            <strong>Places disponibles:</strong> ${seatType.availableSeats}
                                            <c:if test="${seatType.discount != null}">
                                                <br><span class="badge bg-success">Réduction de ${seatType.discount}% à partir de ${seatType.discountSeatsCount} places</span>
                                            </c:if>
                                        </p>
                                        <div class="form-group">
                                            <label for="seat-${seatType.seatPricingId}" class="form-label">Nombre de places</label>
                                            <input type="number" 
                                                   class="form-control" 
                                                   id="seat-${seatType.seatPricingId}" 
                                                   name="seatReservations[${status.index}].seatsCount" 
                                                   min="0" 
                                                   max="${seatType.availableSeats}" 
                                                   value="0">
                                            <input type="hidden" 
                                                   name="seatReservations[${status.index}].seatPricingId" 
                                                   value="${seatType.seatPricingId}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    
                    <div class="mt-4 d-flex justify-content-between">
                        <a href="<c:url value="/vols"/>" class="btn btn-secondary">
                            <i class="bi bi-arrow-left"></i> Retour aux vols
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-check-lg"></i> Confirmer la réservation
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
