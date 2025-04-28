<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/frontoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">Mes réservations</h3>
            <a href="<c:url value="/vols"/>" class="btn btn-outline-secondary">
                <i class="bi bi-search"></i> Chercher un vol
            </a>
        </div>

        <div class="card shadow-sm">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead class="table-light">
                        <tr>
                            <th>N° Réservation</th>
                            <th>Trajet</th>
                            <th>Date de départ</th>
                            <th>Date de réservation</th>
                            <th>Type de siège</th>
                            <th>Passagers</th>
                            <th>Prix total</th>
                            <th>Statut</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <jsp:useBean id="reservations" scope="request" type="java.util.List<mg.itu.ticketing.entity.Reservation>"/>
                        <c:choose>
                            <c:when test="${not empty reservations}">
                                <c:forEach items="${reservations}" var="reservation">
                                    <c:set var="seatPricing" value="${reservation.seatPricing}"/>
                                    <c:set var="flight" value="${seatPricing.flight}"/>
                                    <tr style="vertical-align: middle">
                                        <td><strong>#${reservation.id}</strong></td>
                                        <td>${flight.departureCity.name} → ${flight.arrivalCity.name}</td>
                                        <td>
                                            <fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="departureTimestamp"/>
                                            <fmt:formatDate value="${departureTimestamp}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                        <td>
                                            <fmt:parseDate value="${reservation.timestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="reservationTimestamp"/>
                                            <fmt:formatDate value="${reservationTimestamp}" pattern="dd/MM/yyyy HH:mm"/>
                                        </td>
                                        <td>${seatPricing.seat.seatType.designation}</td>
                                        <td>
                                            <div>${reservation.adultCount} adulte(s)</div>
                                            <div>${reservation.childCount} enfant(s)</div>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${reservation.totalPrice}" type="currency" currencySymbol="Ar"/>
                                        </td>
                                        <td>
                                            <span class="badge bg-${reservation.status == 'CANCELLED' ? "danger" : "success"}">
                                                ${reservation.status.value}
                                            </span>
                                        </td>
                                        <td class="text-center">
                                            <div class="btn-group btn-group-sm">
                                                <a href="<c:url value="/reservations/${reservation.id}/pdf"/>" 
                                                   class="btn btn-outline-primary">
                                                    <i class="bi bi-file-pdf"></i> PDF
                                                </a>
                                                <c:if test="${reservation.status != 'CANCELLED'}">
                                                    <button type="button"
                                                            class="btn btn-outline-danger"
                                                            data-bs-toggle="modal"
                                                            data-bs-target="#cancel-reservation-modal_${reservation.id}"
                                                    >
                                                        <i class="bi bi-x-circle"></i> Annuler
                                                    </button>
                                                </c:if>
                                            </div>
                                        </td>

                                        <div class="modal fade" id="cancel-reservation-modal_${reservation.id}" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Confirmation</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        Voulez-vous vraiment annuler cette réservation ?
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                                        <form action="<c:url value="/reservations/${reservation.id}/annuler"/>" method="post" class="d-inline">
                                                            <button type="submit" class="btn btn-danger">Confirmer l'annulation</button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="10" class="text-center py-4">
                                        <div class="alert alert-info mb-0">
                                            <i class="bi bi-info-circle me-2"></i> Vous n'avez aucune réservation pour le moment.
                                        </div>
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </layout:put>
</layout:extends>
