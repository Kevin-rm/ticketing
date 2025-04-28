<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="helper" uri="http://www.matsd.mg/javaframework/tags/jsp-helpers" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">
                ${empty id ? "Nouvelle" : "Modifier la"} promotion
            </h3>
            <a href="<c:url value="/backoffice/promotions"/>" class="btn btn-outline-secondary">
                <i class="bi bi-arrow-left"></i> Retour à la liste
            </a>
        </div>

        <jsp:useBean id="d" scope="request" type="mg.itu.ticketing.request.DiscountRequest"/>
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <form method="post" class="row g-4">
                    <div class="col-md-12">
                        <label for="seat-pricing" class="form-label">Vol et type de siège</label>
                        <c:set var="seatPricingIdErrors" value="${helper:fieldErrors('d.seatPricingId')}"/>
                        <select class="form-select ${not empty seatPricingIdErrors ? "is-invalid" : ""}"
                                id="seat-pricing"
                                name="d.seatPricingId"
                                required>
                            <option value="">Sélectionner un vol et un type de siège</option>
                            <jsp:useBean id="seatPricingList" scope="request" type="java.util.List<mg.itu.ticketing.entity.SeatPricing>"/>
                            <c:forEach items="${seatPricingList}" var="seatPricing">
                                <c:set var="flight" value="${seatPricing.flight}"/>
                                <option value="${seatPricing.id}" ${d.seatPricingId == seatPricing.id ? "selected" : ""}>
                                    Vol #${flight.id}: ${flight.departureCity.name} → ${flight.arrivalCity.name} 
                                    (<fmt:parseDate value="${flight.departureTimestamp}" pattern="yyyy-MM-dd'T'HH:mm" var="departureTimestamp"/>
                                    <fmt:formatDate value="${departureTimestamp}" pattern="dd/MM/yyyy HH:mm"/>)
                                    - ${seatPricing.seat.seatType.designation} 
                                    - <fmt:formatNumber value="${seatPricing.unitPrice}" type="currency" currencySymbol="Ar"/>
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
                        <label for="percentage" class="form-label">Pourcentage de remise (%)</label>
                        <c:set var="percentageErrors" value="${helper:fieldErrors('d.percentage')}"/>
                        <input type="number"
                               class="form-control ${not empty percentageErrors ? "is-invalid" : ""}"
                               id="percentage"
                               name="d.percentage"
                               value="${d.percentage}"
                               min="0.01"
                               max="100"
                               step="0.01"
                               required>
                        <c:if test="${not empty percentageErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${percentageErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label for="seat-count" class="form-label">Nombre de sièges</label>
                        <c:set var="seatCountErrors" value="${helper:fieldErrors('d.seatCount')}"/>
                        <input type="number"
                               class="form-control ${not empty seatCountErrors ? "is-invalid" : ""}"
                               id="seat-count"
                               name="d.seatCount"
                               value="${d.seatCount}"
                               min="1"
                               required>
                        <c:if test="${not empty seatCountErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${seatCountErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-12">
                        <hr class="mb-4">
                        <button type="submit" class="btn btn-primary px-2">
                            <i class="bi bi-${empty id ? "plus-lg" : "check-lg"}"></i>
                            ${empty id ? "Créer la promotion" : "Enregistrer les modifications"}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
