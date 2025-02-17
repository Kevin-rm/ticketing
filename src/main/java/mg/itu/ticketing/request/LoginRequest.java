package mg.itu.ticketing.request;

import lombok.Data;
import mg.itu.prom16.annotations.BindRequestParameter;
import mg.matsd.javaframework.validation.constraints.basic.Required;
import mg.matsd.javaframework.validation.constraints.string.Email;

@Data
public final class LoginRequest {
    @Email
    @Required
    private String email;

    @BindRequestParameter("mot-de-passe")
    @Required
    private String password;
}
