package mg.itu.ticketing.controller.frontoffice;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.exception.AlreadyCancelledReservationException;
import mg.itu.ticketing.request.ReservationRequest;
import mg.itu.ticketing.service.FlightService;
import mg.itu.ticketing.service.ReservationService;
import mg.itu.ticketing.utils.ApplicationConstants;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.itu.ticketing.utils.Facade;
import mg.matsd.javaframework.security.annotation.Authorize;
import mg.matsd.javaframework.validation.annotations.Validate;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log4j2
@RequiredArgsConstructor
@Authorize({"CUSTOMER", "ADMIN"})
@Controller
public class ReservationController {
    private static final String VIEWS_PATH = ApplicationConstants.Views.FRONTOFFICE_PREFIX + "reservation/";

    private final ReservationService reservationService;
    private final FlightService flightService;

    @Get("/mes-reservations")
    public String showMyReservations(Model model) {
        DatabaseUtils.execute(entityManager ->
            model.addData("reservations", reservationService.getAllByUser(Facade.requireCurrentUser(), entityManager)
        ));

        return VIEWS_PATH + "my-reservations";
    }

    @Get("/reservations/vol-{flightId}/creer")
    public String create(@PathVariable Integer flightId, Model model) {
        if (!model.hasData("r"))
            model.addData("r", new ReservationRequest());

        DatabaseUtils.execute(entityManager ->
            model.addData("flight", flightService.getById(flightId, true, entityManager)
        ));

        return VIEWS_PATH + "create";
    }

    @Post("/reservations/vol-{flightId}/creer")
    public String store(
        @PathVariable Integer flightId,
        @Validate @ModelData("r") ReservationRequest reservationRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("r", reservationRequest);

            return String.format("redirect:/reservations/vol-%d/creer", flightId);
        }

        try {
            DatabaseUtils.executeTransactional(entityManager ->
                reservationService.insert(reservationRequest, entityManager, Facade.requireCurrentUser()
            ));
            redirectData.add("success", "Réservation créée avec succès");

            return "redirect:/mes-reservations";
        } catch (Exception e) {
            log.error("Erreur lors de la création d'une réservation", e);
            redirectData.add("error", "Erreur lors de la création de réservation");
            redirectData.add("r", reservationRequest);

            return String.format("redirect:/reservations/vol-%d/creer", flightId);
        }
    }

    @Post("/reservations/{id}/annuler")
    public String cancel(@PathVariable Integer id, RedirectData redirectData) {
        try {
            DatabaseUtils.executeTransactional(entityManager ->
                reservationService.cancel(reservationService.getById(id, entityManager), entityManager));

            redirectData.add("success", "Réservation annulée avec succès");
        } catch (Exception e) {
            log.error("Erreur lors d'une annulation de réservation", e);
            redirectData.add("error", e instanceof AlreadyCancelledReservationException ? e.getMessage() :
                "Erreur lors de l'annulation de réservation");
        }

        return "redirect:/mes-reservations";
    }

    @Get("/reservations/{id}/pdf")
    public String generatePdf(
        @PathVariable Integer id, RedirectData redirectData, HttpServletResponse httpServletResponse
    ) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            String apiUrl = "http://localhost:8080/api/reservations/" + id + "/pdf";

            try {
                HttpResponse<InputStream> httpResponse = httpClient.send(HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build(), HttpResponse.BodyHandlers.ofInputStream());

                if (httpResponse.statusCode() == 200) {
                    HttpHeaders httpHeaders = httpResponse.headers();
                    httpHeaders.firstValue("Content-Type")
                        .ifPresent(httpServletResponse::setContentType);
                    httpHeaders.firstValue("Content-Disposition")
                        .ifPresent(cd -> httpServletResponse.setHeader("Content-Disposition", cd));

                    try (final InputStream inputStream = httpResponse.body();
                         final OutputStream outputStream = httpServletResponse.getOutputStream()
                    ) { inputStream.transferTo(outputStream); }

                    return null;
                }

                redirectData.add("error", "Erreur lors de la génération du PDF");
            } catch (Exception e) {
                log.error("Erreur lors de la communication avec le service PDF", e);
                redirectData.add("error", "Une erreur inattendue est survenue");
            }

            return "redirect:/mes-reservations";
        }
    }
}
