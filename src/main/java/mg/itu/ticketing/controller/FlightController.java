package mg.itu.ticketing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.request.FlightRequest;
import mg.itu.ticketing.service.FlightService;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.matsd.javaframework.validation.annotations.Validate;

@Log4j2
@RequiredArgsConstructor
@Controller
public class FlightController {
    private static final String BACKOFFICE_VIEWS_PATH = "/views/backoffice/flight/";
    private static final String BACKOFFICE_URL_PREFIX = "/backoffice/vols";

    private final FlightService flightService;

    @Get(BACKOFFICE_URL_PREFIX)
    public String index(Model model) {
        DatabaseUtils.execute(entityManager ->
            model.addData("flights", flightService.getAll(entityManager)));

        return BACKOFFICE_VIEWS_PATH + "list";
    }

    @Get(BACKOFFICE_URL_PREFIX + "/creer")
    public String create(Model model) {
        return BACKOFFICE_VIEWS_PATH + "form";
    }

    @Post(BACKOFFICE_URL_PREFIX + "/creer")
    public String store() {
        return "redirect:" + BACKOFFICE_URL_PREFIX;
    }

    @Get(BACKOFFICE_URL_PREFIX + "/{id}/modifier")
    public String edit(@PathVariable Integer id, Model model) {
        return BACKOFFICE_VIEWS_PATH + "form";
    }

    @Post(BACKOFFICE_URL_PREFIX + "/{id}/modifier")
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

    @Post(BACKOFFICE_URL_PREFIX + "/supprimer/{id}")
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
