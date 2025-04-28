package mg.itu.ticketing.controller.backoffice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mg.itu.prom16.annotations.*;
import mg.itu.prom16.base.Model;
import mg.itu.prom16.base.RedirectData;
import mg.itu.prom16.validation.ModelBindingResult;
import mg.itu.ticketing.request.DiscountRequest;
import mg.itu.ticketing.service.DiscountService;
import mg.itu.ticketing.service.SeatPricingService;
import mg.itu.ticketing.utils.DatabaseUtils;
import mg.matsd.javaframework.validation.annotations.Validate;

import static mg.itu.ticketing.utils.ApplicationConstants.Views.BACKOFFICE_PREFIX;

@Log4j2
@RequiredArgsConstructor
// @Authorize("ADMIN")
@Controller
@RequestMapping("/backoffice/promotions")
public class DiscountController {
    private static final String VIEWS_PATH = BACKOFFICE_PREFIX + "discount/";
    private static final String URL_PREFIX = "/backoffice/promotions";

    private final DiscountService discountService;
    private final SeatPricingService seatPricingService;

    @Get
    public String index(Model model) {
        DatabaseUtils.execute(entityManager ->
            model.addData("discounts", discountService.getAll(entityManager)
        ));

        return VIEWS_PATH + "list";
    }

    @Get("/creer")
    public String create(Model model) {
        if (!model.hasData("d")) model.addData("d", new DiscountRequest());

        DatabaseUtils.execute(entityManager ->
            model.addData("seatPricingList", seatPricingService.getAll(entityManager)));

        return VIEWS_PATH + "form";
    }

    @Post("/creer")
    public String store(
        @Validate @ModelData("d") DiscountRequest discountRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        System.out.println(modelBindingResult.getFieldErrorsMap());
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("d", discountRequest);

            return String.format("redirect:%s/creer", URL_PREFIX);
        }

        try {
            DatabaseUtils.executeTransactional(entityManager -> 
                discountService.insert(discountRequest, entityManager));
            redirectData.add("success", "Promotion créée avec succès");

            return "redirect:" + URL_PREFIX;
        } catch (Exception e) {
            log.error("Erreur lors de la création d'une promotion", e);
            redirectData.add("error", "Erreur lors de la création de promotion");
            redirectData.add("d", discountRequest);

            return String.format("redirect:%s/creer", URL_PREFIX);
        }
    }

    @Get("/{id}/modifier")
    public String edit(@PathVariable Integer id, Model model) {
        DatabaseUtils.execute(entityManager -> {
            if (!model.hasData("d"))
                model.addData("d", DiscountRequest.fromDiscount(discountService.getById(id, entityManager)));

            return model.addData("id", id)
                .addData("seatPricingList", seatPricingService.getAll(entityManager));
        });

        return VIEWS_PATH + "form";
    }

    @Post("/{id}/modifier")
    public String update(
        @PathVariable Integer id,
        @Validate @ModelData("d") DiscountRequest discountRequest,
        ModelBindingResult modelBindingResult,
        RedirectData redirectData
    ) {
        if (modelBindingResult.hasErrors()) {
            redirectData.addAll(modelBindingResult.getFieldErrorsMap());
            redirectData.add("d", discountRequest);

            return String.format("redirect:%s/%d/modifier", URL_PREFIX, id);
        }

        try {
            DatabaseUtils.executeTransactional(entityManager ->
                discountService.update(discountService.getById(id, entityManager), discountRequest, entityManager));

            redirectData.add("success", "Promotion modifiée avec succès");
        } catch (Exception e) {
            log.error("Erreur lors d'une modification d'une promotion", e);
            redirectData.add("error", "Erreur lors de la modification de promotion");
            redirectData.add("d", discountRequest);
        }

        return String.format("redirect:%s/%d/modifier", URL_PREFIX, id);
    }

    @Post("/{id}/supprimer")
    public String delete(@PathVariable Integer id, RedirectData redirectData) {
        try {
            DatabaseUtils.executeTransactional(entityManager ->
                discountService.delete(discountService.getById(id, entityManager), entityManager));

            redirectData.add("success", "Promotion supprimée avec succès");
        } catch (Exception e) {
            log.error("Erreur lors d'une suppression d'une promotion", e);
            redirectData.add("error", "Erreur lors de la suppression de promotion");
        }

        return "redirect:" + URL_PREFIX;
    }
}
