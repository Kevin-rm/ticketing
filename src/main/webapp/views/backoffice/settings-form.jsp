<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" uri="http://www.matsd.mg/javaframework/tags/template-inheritance" %>
<%@ taglib prefix="helper" uri="http://www.matsd.mg/javaframework/tags/jsp-helpers" %>

<layout:extends file="/views/backoffice/_layout">
    <layout:put block="content">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-primary">Paramètres</h3>
        </div>

        <jsp:useBean id="s" scope="request" type="mg.itu.ticketing.request.SettingsRequest"/>
        <div class="card shadow-sm">
            <div class="card-body p-4">
                <form method="post" class="row g-4">
                    <div class="col-md-6">
                        <label for="min-reservation-hours" class="form-label fw-semibold">
                            Délai minimum de réservation (en heures)
                        </label>
                        <c:set var="minReservationHoursErrors" value="${helper:fieldErrors('s.minReservationHours')}"/>
                        <input type="number"
                               class="form-control ${not empty minReservationHoursErrors ? "is-invalid" : ""}"
                               id="min-reservation-hours"
                               name="s.minReservationHours"
                               value="${s.minReservationHours}"
                               min="0"
                               required>
                        <c:if test="${not empty minReservationHoursErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${minReservationHoursErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label for="min-cancellation-hours" class="form-label fw-semibold">
                            Délai minimum d'annulation (en heures)
                        </label>
                        <c:set var="minCancellationHoursErrors" value="${helper:fieldErrors('s.minCancellationHours')}"/>
                        <input type="number"
                               class="form-control ${not empty minCancellationHoursErrors ? "is-invalid" : ""}"
                               id="min-cancellation-hours"
                               name="s.minCancellationHours"
                               value="${s.minCancellationHours}"
                               min="0"
                               required>
                        <c:if test="${not empty minCancellationHoursErrors}">
                            <div class="invalid-feedback">
                                <c:out value="${minCancellationHoursErrors[0].message}"/>
                            </div>
                        </c:if>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            Dernière modification le
                        </label>
                        <div class="form-control-plaintext border-bottom">
                            <c:choose>
                                <c:when test="${not empty s.modifiedAt}">
                                    ${s.modifiedAt}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted fst-italic">Non modifié</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-semibold">
                            Modifié par
                        </label>
                        <div class="form-control-plaintext border-bottom">
                            <c:choose>
                                <c:when test="${not empty s.modifiedBy}">
                                    ${s.modifiedBy}
                                </c:when>
                                <c:otherwise>
                                    <span class="text-muted fst-italic">Non spécifié</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="col-12">
                        <hr class="mb-4">
                        <button type="submit" class="btn btn-primary px-2">
                            <i class="bi bi-check-lg"></i>
                            Enregistrer les modifications
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </layout:put>
</layout:extends>
