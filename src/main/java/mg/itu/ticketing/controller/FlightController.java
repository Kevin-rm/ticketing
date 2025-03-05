package mg.itu.ticketing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.request.FlightRequest;
import mg.itu.ticketing.request.FlightSearchRequest;
import mg.itu.ticketing.service.CityService;
import mg.itu.ticketing.service.FlightService;
import mg.itu.ticketing.service.PlaneService;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.matsd.javaframework.validation.annotations.Validate;

@Log4j2
@RequiredArgsConstructor
@Controller
public class FlightController {
    private static final String BACKOFFICE_VIEWS_PATH = "/views/backoffice/flight/";
    private static final String BACKOFFICE_URL_PREFIX = "/backoffice/vols";

    private final FlightService flightService;
    private final CityService   cityService;
    private final PlaneService  planeService;

    // @Authorize("ADMIN")
    @Get("/backoffice/vols")
    public String index(
        Model model, @Validate @ModelData("f") FlightSearchRequest flightSearchRequest
    ) {
        DatabaseUtils.execute(entityManager ->
            model.addData("flights", flightService.search(flightSearchRequest, entityManager)));

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
        try {
            if (modelBindingResult.hasErrors()) {
                redirectData.addAll(modelBindingResult.getFieldErrorsMap());
                redirectData.add("f", flightRequest);

                return String.format("redirect:%s/creer", BACKOFFICE_URL_PREFIX);
            }

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
}
