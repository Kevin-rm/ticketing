package mg.itu.ticketing.controller;

import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.ticketing.utils.ApplicationConstants;

@Controller
public class PublicController {

    @Get
    public String home() {
        return ApplicationConstants.Views.FRONTOFFICE_PREFIX +  "home";
    }
}
