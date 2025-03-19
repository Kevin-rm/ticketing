package mg.itu.ticketing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.entity.Flight;
import mg.itu.ticketing.request.FlightRequest;
import mg.itu.ticketing.request.FlightSearchRequest;
import mg.itu.ticketing.request.SeatPricingRequest;
import mg.itu.ticketing.service.CityService;
import mg.itu.ticketing.service.FlightService;
import mg.itu.ticketing.service.PlaneService;
import mg.itu.ticketing.service.SeatService;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.matsd.javaframework.security.annotation.Authorize;
import mg.matsd.javaframework.validation.annotations.Validate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
public class FlightController {
    private static final String BACKOFFICE_VIEWS_PATH = "/views/backoffice/flight/";
    private static final String BACKOFFICE_URL_PREFIX = "/backoffice/vols";

    private final FlightService flightService;
    private final CityService   cityService;
    private final PlaneService  planeService;
    private final SeatService   seatService;

    // @Authorize("ADMIN")
    @Get("/backoffice/vols")
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

        return BACKOFFICE_VIEWS_PATH + "list";
    }

    // @Authorize("ADMIN")
    @Get("/backoffice/vols/creer")
    public String create(Model model) {
        if (!model.hasData("f")) model.addData("f", new FlightRequest());

        DatabaseUtils.execute(entityManager ->
            model.addData("cities", cityService.getAll(entityManager))
                .addData("planes", planeService.getAll(entityManager))
        );

        return BACKOFFICE_VIEWS_PATH + "form";
    }

    // @Authorize("ADMIN")
    @Post("/backoffice/vols/creer")
    public String store(
        @Validate @ModelData("f") FlightRequest flightRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("f", flightRequest);

            return String.format("redirect:%s/creer", BACKOFFICE_URL_PREFIX);
        }

        try {
            DatabaseUtils.executeTransactional(entityManager -> flightService.insert(flightRequest, entityManager));
            redirectData.add("success", "Vol créé avec succès");

            return "redirect:" + BACKOFFICE_URL_PREFIX;
        } catch (Exception e) {
            log.error("Erreur lors de la création d'un vol", e);
            redirectData.add("error", "Erreur lors de la création de vol");
            redirectData.add("f", flightRequest);

            return String.format("redirect:%s/creer", BACKOFFICE_URL_PREFIX);
        }
    }

    // @Authorize("ADMIN")
    @Get("/backoffice/vols/{id}/modifier")
    public String edit(@PathVariable Integer id, Model model) {
        DatabaseUtils.execute(entityManager -> {
            if (!model.hasData("f"))
                model.addData("f", FlightRequest.fromFlight(flightService.getById(id, entityManager)));

            return model.addData("id", id)
                .addData("cities", cityService.getAll(entityManager))
                .addData("planes", planeService.getAll(entityManager));
        });

        return BACKOFFICE_VIEWS_PATH + "form";
    }

    // @Authorize("ADMIN")
    @Post("/backoffice/vols/{id}/modifier")
    public String update(
        @PathVariable Integer id,
        @Validate @ModelData("f")FlightRequest flightRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("f", flightRequest);
        }

        try {
            DatabaseUtils.executeTransactional(entityManager ->
                flightService.update(flightService.getById(id, entityManager), flightRequest, entityManager));

            redirectData.add("success", "Vol modifié avec succès");
        } catch (Exception e) {
            log.error("Erreur lors d'une modification d'un vol", e);
            redirectData.add("error", "Erreur lors de la modification de vol");
            redirectData.add("f", flightRequest);
        }

        return String.format("redirect:%s/%d/modifier", BACKOFFICE_URL_PREFIX, id);
    }

    // @Authorize("ADMIN")
    @Post("/backoffice/vols/{id}/supprimer")
    public String delete(@PathVariable Integer id, RedirectData redirectData) {
        try {
            DatabaseUtils.executeTransactional(entityManager ->
                flightService.delete(flightService.getById(id, entityManager), entityManager));

            redirectData.add("success", "Vol supprimé avec succès");
        } catch (Exception e) {
            log.error("Erreur lors d'une suppression d'un vol", e);
            redirectData.add("error", "Erreur lors de la suppression de vol");
        }

        return "redirect:" + BACKOFFICE_URL_PREFIX;
    }

    // @Authorize("ADMIN")
    @Get("/backoffice/vols/{id}/prix")
    public String showPricingForm(@PathVariable Integer id, Model model) {
        DatabaseUtils.execute(entityManager -> {
            Flight flight = flightService.getById(id, entityManager);
            var seats = seatService.getAllByPlane(flight.getPlane(), entityManager);
            
            // Create a map of seat ID to pricing data
            Map<Integer, SeatPricingRequest> pricingMap = new HashMap<>();
            
            // Populate the pricing map with existing prices or default values
            /*for (var seat : seats) {
                Double price = flightService.getSeatPrice(flight.getId(), seat.getId(), entityManager);
                SeatPricingRequest pricingRequest = new SeatPricingRequest(
                    seat.getId(),
                    price != null ? price : 0.0 // Default to 0 if no price is set
                );
                pricingMap.put(seat.getId(), pricingRequest);
            }*/

            return model.addData("flight", flight)
                .addData("seats", seats)
                .addData("pricingMap", pricingMap);
        });

        return BACKOFFICE_VIEWS_PATH + "pricing-form";
    }

    // @Authorize("ADMIN")
    @Post("/backoffice/vols/{id}/prix")
    public String handlePricingForm(
        @PathVariable Integer id,
        @RequestParameter(name = "prices") List<SeatPricingRequest> prices,
        RedirectData redirectData
    ) {
        try {
            DatabaseUtils.executeTransactional(entityManager -> {
                Flight flight = flightService.getById(id, entityManager);
                
                // Save each seat price
                /*for (SeatPricingRequest price : prices) {
                    flightService.updateSeatPrice(
                        flight.getId(), 
                        price.getSeatId(), 
                        price.getUnitPrice(), 
                        entityManager
                    );
                }*/
            });
            
            redirectData.add("success", "Prix des sièges mis à jour avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour des prix", e);
            redirectData.add("error", "Erreur lors de la mise à jour des prix");
        }
        
        return String.format("redirect:%s/%d/prix", BACKOFFICE_URL_PREFIX, id);
    }
}
