package mg.itu.ticketing.request;

import lombok.Data;
import mg.itu.prom16.annotations.BindRequestParameter;
import mg.matsd.javaframework.validation.constraints.basic.Required;

@Data
public final class RegistrationRequest {
    @BindRequestParameter("prenom")
    @Required
    private String firstname;

    @BindRequestParameter("nom")
    @Required
    private String lastname;

    @Required
    private String email;

    @BindRequestParameter("mot-de-passe")
    @Required
    private String password;
}
