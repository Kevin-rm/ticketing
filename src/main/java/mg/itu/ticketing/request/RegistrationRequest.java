package mg.itu.ticketing.request;

import lombok.Data;
import mg.matsd.javaframework.validation.constraints.basic.Required;

@Data
public final class RegistrationRequest {
    @Required
    private String firstname;

    @Required
    private String lastname;

    @Required
    private String email;

    @Required
    private String password;
}
