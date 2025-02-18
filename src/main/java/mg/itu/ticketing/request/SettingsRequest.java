package mg.itu.ticketing.request;

import lombok.Data;
import mg.itu.ticketing.entity.Settings;
import mg.matsd.javaframework.validation.constraints.basic.Required;
import mg.matsd.javaframework.validation.constraints.number.Positive;

@Data
public class SettingsRequest {
    @Required
    @Positive
    private Integer minReservationHours;

    @Required
    @Positive
    private Integer minCancellationHours;

    public static SettingsRequest fromSettings(final Settings settings) {
        SettingsRequest settingsRequest = new SettingsRequest();
        settingsRequest.setMinReservationHours(settings.getMinReservationHours());
        settingsRequest.setMinCancellationHours(settings.getMinCancellationHours());

        return settingsRequest;
    }
}
