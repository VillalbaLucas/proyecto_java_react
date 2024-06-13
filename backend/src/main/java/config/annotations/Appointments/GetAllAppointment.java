package config.annotations.Appointments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@APIResponses(
    value={
        @APIResponse(description = "Turnos encontrados", responseCode = "201"),
        @APIResponse(description = "Turnos no encontrados", responseCode = "400"),
        @APIResponse(description = "Error interno del servidor", responseCode = "500")
    }
)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface GetAllAppointment {
    String apiValue() default "";
}

