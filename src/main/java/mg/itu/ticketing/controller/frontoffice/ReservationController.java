package mg.itu.ticketing.controller.frontoffice;

import lombok.RequiredArgsConstructor;
import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.PathVariable;
import mg.itu.prom16.annotations.Post;
import mg.itu.prom16.base.Model;
import mg.itu.ticketing.service.ReservationService;
import mg.itu.ticketing.utils.ApplicationConstants;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.itu.ticketing.utils.Facade;
import mg.matsd.javaframework.security.annotation.Authorize;

@RequiredArgsConstructor
// @Authorize({"USER", "ADMIN"})
@Controller
public class ReservationController {
    private static final String VIEWS_PATH = ApplicationConstants.Views.FRONTOFFICE_PREFIX + "reservation/";

    private final ReservationService reservationService;

    @Get("/mes-reservations")
    public String showMyReservations(Model model) {
        DatabaseUtils.execute(entityManager ->
            model.addData("reservations", reservationService.getAllByUser(Facade.requireCurrentUser(), entityManager)
        ));

        return VIEWS_PATH + "my-reservations";
    }

    @Get("/reservations/vol-{flightId}/creer")
    public String create(@PathVariable Integer flightId, Model model) {
        return VIEWS_PATH + "create";
    }

    @Post("/reservations/vol-{flightId}/creer")
    public String store(@PathVariable Integer flightId) {
        return "redirect:/mes-reservations";
    }
}
