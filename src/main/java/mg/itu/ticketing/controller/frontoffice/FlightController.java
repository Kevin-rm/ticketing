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
            model.addData("flights", flightService.search(flightSearchRequest, true, entityManager))
                .addData("cities", cityService.getAll(entityManager)));

        return VIEWS_PATH + "list";
    }
}
