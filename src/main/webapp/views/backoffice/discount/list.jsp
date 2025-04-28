<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">Liste des promotions</h3>
            <a href="<c:url value="/backoffice/promotions/creer"/>" class="btn btn-outline-secondary">
                <i class="bi bi-plus-lg"></i> Nouvelle une promotion
            </a>
        </div>

        <div class="card">
            <div class="table-responsive">
                <table class="table table-hover mb-0">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Vol</th>
                            <th>Type de siège</th>
                            <th>Prix unitaire</th>
                            <th>Pourcentage</th>
                            <th>Nombre de sièges</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <jsp:useBean id="discounts" scope="request" type="java.util.List<mg.itu.ticketing.entity.Discount>"/>
                        <c:forEach items="${discounts}" var="discount">
                            <c:set var="seatPricing" value="${discount.seatPricing}"/>
                            <c:set var="flight" value="${seatPricing.flight}"/>
                            <c:set var="seat" value="${seatPricing.seat}"/>
                            <tr>
                                <th scope="row">${discount.id}</th>
                                <td>
                                    <div>${flight.departureCity.name} → ${flight.arrivalCity.name}</div>
                                    <div class="text-muted">
                                        <fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="departureTimestamp"/>
                                        <fmt:formatDate value="${departureTimestamp}" pattern="dd/MM/yyyy HH:mm"/>
                                    </div>
                                </td>
                                <td>${seat.seatType.designation}</td>
                                <td>
                                    <fmt:formatNumber value="${seatPricing.unitPrice}" type="currency" currencySymbol="Ar"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${discount.percentage}" type="percent" maxFractionDigits="2"/>
                                </td>
                                <td>${discount.seatCount}</td>
                                <td class="text-center">
                                    <div class="btn-group btn-group-sm">
                                        <a href="<c:url value="/backoffice/promotions/${discount.id}/modifier"/>"
                                           class="btn btn-primary">
                                            <i class="bi bi-pencil-square"></i> Modifier
                                        </a>
                                        <button type="button"
                                                class="btn btn-danger"
                                                data-bs-toggle="modal"
                                                data-bs-target="#delete-discount-modal_${discount.id}">
                                            <i class="bi bi-trash"></i> Supprimer
                                        </button>
                                    </div>

                                    <div class="modal fade" id="delete-discount-modal_${discount.id}" tabindex="-1">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title">Confirmation</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Voulez-vous vraiment supprimer cette promotion ?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
                                                    <form action="<c:url value="/backoffice/promotions/${discount.id}/supprimer"/>" method="post" class="d-inline">
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
