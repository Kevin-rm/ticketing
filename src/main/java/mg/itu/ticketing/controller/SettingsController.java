package mg.itu.ticketing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.utils.AuthFacade;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.entity.User;
import mg.itu.ticketing.request.SettingsRequest;
import mg.itu.ticketing.service.SettingsService;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.matsd.javaframework.security.annotation.Authorize;
import mg.matsd.javaframework.validation.annotations.Validate;

@Log4j2
@RequiredArgsConstructor
// @Authorize("ADMIN")
@Controller
@RequestMapping("/backoffice/parametres")
public class SettingsController {
    private final SettingsService settingsService;

    @Get
    public String showForm(Model model) {
        DatabaseUtils.execute(entityManager -> model.addData("s",
            SettingsRequest.fromSettings(settingsService.getFirstOrNew(entityManager))
        ));

        return "/views/backoffice/settings-form";
    }

    @Post
    public String handleForm(
        @Validate @ModelData("s") SettingsRequest settingsRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("s", settingsRequest);
        }

        try {
            DatabaseUtils.executeTransactional(entityManager ->
                settingsService.save(settingsRequest, entityManager, (User) AuthFacade.getCurrentUser()));
            redirectData.add("success", "Paramètres enregistrés avec succès");
        } catch (Exception e) {
            log.error("Erreur lors d'un enregistrement des paramètres", e);
            redirectData.add("error", "Erreur lors de l'enregistrement des paramètres");
            redirectData.add("s", settingsRequest);
        }

        return "redirect:/backoffice/parametres";
    }
}
