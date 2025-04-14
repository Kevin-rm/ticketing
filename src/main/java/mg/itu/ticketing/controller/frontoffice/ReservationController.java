package mg.itu.ticketing.controller.frontoffice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.request.ReservationRequest;
import mg.itu.ticketing.service.FlightService;
import mg.itu.ticketing.service.ReservationService;
import mg.itu.ticketing.utils.ApplicationConstants;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.itu.ticketing.utils.Facade;
import mg.matsd.javaframework.security.annotation.Authorize;
import mg.matsd.javaframework.validation.annotations.Validate;

@Log4j2
@RequiredArgsConstructor
@Authorize({"USER", "ADMIN"})
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
            model.addData("flight", flightService.getById(flightId, entityManager)));

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
}
