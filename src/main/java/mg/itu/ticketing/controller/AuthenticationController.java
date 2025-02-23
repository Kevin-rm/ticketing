package mg.itu.ticketing.controller;

import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.Controller;
import mg.itu.prom16.annotations.Get;
import mg.itu.prom16.annotations.ModelData;
import mg.itu.prom16.annotations.Post;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.utils.AuthFacade;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.request.LoginRequest;
import mg.itu.ticketing.request.RegistrationRequest;
import mg.itu.ticketing.service.UserService;
import mg.matsd.javaframework.security.annotation.Anonymous;
import mg.matsd.javaframework.security.exceptions.InvalidCredentialsException;
import mg.matsd.javaframework.validation.annotations.Validate;

@Log4j2
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
        @Validate @ModelData("l") LoginRequest request,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("l", request);

            return "redirect:/connexion";
        }

        try {
            AuthFacade.login(request.getEmail(), request.getPassword());
            return AuthFacade.getCurrentUser().hasRole("ADMIN") ? "redirect:/admin" : "redirect:/";
        } catch (InvalidCredentialsException e) {
            redirectData.add("error", e.getMessage());
            redirectData.add("l", request);

            return "redirect:/connexion";
        }
    }

    @Get("/inscription")
    public String register(Model model) {
        if (!model.hasData("r"))
            model.addData("r", new RegistrationRequest());

        return "/views/auth/register";
    }

    @Post("/inscription")
    public String doRegister(
        @Validate @ModelData("r") RegistrationRequest request,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData,
        UserService userService
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("r", request);

            return "redirect:/inscription";
        }

        try {
            userService.insert(request);
            redirectData.add("success", "Inscription réussie");

            return "redirect:/connexion";
        } catch (Exception e) {
            log.error("Erreur lors d'une inscription d'un utilisateur", e);
            redirectData.add("error", "Erreur lors de l'inscription");
            redirectData.add("r", request);

            return "redirect:/inscription";
        }
    }

    @Post("/deconnexion")
    public String logout() {
        AuthFacade.logout();
        return "redirect:/connexion";
    }
}
