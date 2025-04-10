package mg.itu.ticketing.controller.frontoffice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.ModelData;
import mg.itu.prom16.annotations.RequestMapping;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.request.FlightSearchRequest;
import mg.itu.ticketing.service.CityService;
import mg.itu.ticketing.service.FlightService;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.matsd.javaframework.validation.annotations.Validate;

import static mg.itu.ticketing.utils.ApplicationConstants.Views.FRONTOFFICE_PREFIX;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/vols")
public class FlightController {
    private static final String VIEWS_PATH = FRONTOFFICE_PREFIX + "flight/";

    private final FlightService flightService;
    private final CityService cityService;

    @Get
    public String index(
        Model model,
        @Validate @ModelData("f") FlightSearchRequest flightSearchRequest,
        ModelBindingResult modelBindingResult
    ) {
        if (modelBindingResult.hasErrors())
            model.addData(modelBindingResult.getFieldErrorsMap());

        DatabaseUtils.execute(entityManager ->
            model.addData("flights", flightService.search(flightSearchRequest, entityManager))
                .addData("cities", cityService.getAll(entityManager)));

        return VIEWS_PATH + "list";
    }

    // @Authorize("USER")
    /*@Get("/vols/{id}/reserver")
    public String reservationForm(@PathVariable Integer id, Model model) {
        DatabaseUtils.execute(entityManager -> {
            Flight flight = flightService.getById(id, entityManager);
            model.addData("flight", flight);

            // Get all seat types with pricing for this flight
            List<SeatPricing> seatPricings = seatPricingService.getByFlight(flight, entityManager);
            List<Map<String, Object>> seatTypes = new ArrayList<>();

            for (SeatPricing seatPricing : seatPricings) {
                Seat seat = seatPricing.getSeat();
                Map<String, Object> seatTypeInfo = new HashMap<>();
                
                seatTypeInfo.put("seatPricingId", seatPricing.getId());
                seatTypeInfo.put("type", seat.getSeatType().getDesignation());
                seatTypeInfo.put("unitPrice", seatPricing.getUnitPrice());
                seatTypeInfo.put("availableSeats", seat.getSeatsCount());
                
                // Check if there's a discount for this seat type
                seatPricingService.getDiscountBySeatPricing(seatPricing, entityManager).ifPresent(discount -> {
                    seatTypeInfo.put("discount", discount.getPercentage());
                    seatTypeInfo.put("discountSeatsCount", discount.getSeatsCount());
                });
                
                seatTypes.add(seatTypeInfo);
            }
            
            model.addData("seatTypes", seatTypes);
        });

        return FRONTOFFICE_VIEWS_PATH + "reservation";
    } */

    // @Authorize("USER")
    /*@Post("/vols/{id}/reserver")
    public String processReservation(
        @PathVariable Integer id,
        @Validate @ModelData ReservationRequest reservationRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            return String.format("redirect:/vols/%d/reserver", id);
        }

        try {
            // Filter out seat reservations with zero seats
            reservationRequest.setSeatReservations(
                reservationRequest.getSeatReservations().stream()
                    .filter(sr -> sr.getSeatsCount() > 0)
                    .collect(Collectors.toList())
            );

            // Check if there's at least one seat reserved
            if (reservationRequest.getSeatReservations().isEmpty()) {
                redirectData.add("error", "Veuillez sélectionner au moins un siège pour effectuer une réservation.");
                return String.format("redirect:/vols/%d/reserver", id);
            }

            // Process the reservation
            DatabaseUtils.executeTransactional(entityManager -> 
                reservationService.insert(reservationRequest, entityManager, currentUser)
            );
            
            redirectData.add("success", "Votre réservation a été effectuée avec succès.");
            return "redirect:/reservations";
            
        } catch (Exception e) {
            log.error("Erreur lors de la réservation", e);
            redirectData.add("error", "Une erreur est survenue lors de la réservation.");
            return String.format("redirect:/vols/%d/reserver", id);
        }
    } */
}
