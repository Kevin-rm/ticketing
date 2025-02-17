package mg.itu.ticketing.controller;

import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.ModelData;
import mg.itu.prom16.annotations.Post;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.ticketing.request.LoginRequest;
import mg.matsd.javaframework.security.annotation.Anonymous;
import mg.matsd.javaframework.validation.annotations.Validate;

@Anonymous
@Controller
public class AuthenticationController {

    @Get("/connexion")
    public String login(Model model) {
        model.addData("l", new LoginRequest());
        return "/views/auth/login";
    }

    @Post("/connexion")
    public String doLogin(
        @Validate @ModelData("l") LoginRequest request,
        RedirectData redirectData
    ) {

        return "redirect:/connexion";
    }

    @Get("/inscription")
    public String register() {
        return "/views/auth/register";
    }

    @Post("/inscription")
    public String doRegister() {
        return "redirect:/inscription";
    }
}
