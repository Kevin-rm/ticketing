package mg.itu.ticketing.request;

import lombok.Data;
import mg.itu.ticketing.entity.Settings;
import mg.itu.ticketing.entity.User;
import mg.matsd.javaframework.core.annotations.Nullable;
import mg.matsd.javaframework.validation.constraints.basic.Required;
import mg.matsd.javaframework.validation.constraints.number.Positive;

import java.time.LocalDateTime;

@Data
public class SettingsRequest {
    @Required
    @Positive
    private Integer minReservationHours;

    @Required
    @Positive
    private Integer minCancellationHours;

    @Nullable
    private final LocalDateTime modifiedAt;

    @Nullable
    private final String modifiedBy;

    public static SettingsRequest fromSettings(final Settings settings) {
        User user = settings.getModifiedBy();

        SettingsRequest settingsRequest = new SettingsRequest(settings.getModifiedAt(), user == null ? null : user.fullName());
        settingsRequest.setMinReservationHours(settings.getMinReservationHours());
        settingsRequest.setMinCancellationHours(settings.getMinCancellationHours());

        return settingsRequest;
    }
}
