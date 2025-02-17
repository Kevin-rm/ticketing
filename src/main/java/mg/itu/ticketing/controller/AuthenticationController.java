package mg.itu.ticketing.controller;

import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.ModelData;
import mg.itu.prom16.annotations.Post;
import mg.itu.prom16.base.Model;
import mg.itu.ticketing.request.LoginRequest;
import mg.itu.ticketing.request.RegistrationRequest;
import mg.matsd.javaframework.security.annotation.Anonymous;
import mg.matsd.javaframework.validation.annotations.Validate;

@Anonymous
@Controller
public class AuthenticationController {

    @Get("/connexion")
    public String login(Model model) {
        if (!model.hasData("l"))
            model.addData("l", new LoginRequest());

        return "/views/auth/login";
    }

    @Post("/connexion")
    public String doLogin(
        @Validate @ModelData("l") LoginRequest request
    ) {
        return "redirect:/connexion";
    }

    @Get("/inscription")
    public String register(Model model) {
        if (!model.hasData("r"))
            model.addData("r", new RegistrationRequest());

        return "/views/auth/register";
    }

    @Post("/inscription")
    public String doRegister(
        @Validate @ModelData("r") RegistrationRequest request
    ) {
        return "redirect:/inscription";
    }
}
