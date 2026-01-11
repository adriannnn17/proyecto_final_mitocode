package org.acme.interfaces.apis;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import org.acme.interfaces.resources.requests.ProfesionalSchemaRequest;
import org.acme.interfaces.resources.responses.ProfesionalSchemaResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.concurrent.CompletionStage;

import static org.acme.infraestructure.constants.Constant.UUID_PATTERN;

/**
 * A JAX-RS interface. An implementation of this interface must be provided.
 */
@Path("/professionals")
public interface ProfessionalsResource {
    /**
     *
     */
    @Operation(description = "", summary = "Obtener Profesionales", operationId = "listProfessional")
    @GET
    @Produces("application/json")
    CompletionStage<List<ProfesionalSchemaResponse>> listProfessional();

    /**
     *
     */
    @Operation(description = "", summary = "Crear profesionales", operationId = "createProfessional")
    @POST
    @Consumes("application/json")
    CompletionStage<Void> createProfessional(@NotNull ProfesionalSchemaRequest data);

    /**
     *
     */
    @Operation(description = "", summary = "Buscar Profesionales", operationId = "findProfessional")
    @Path("/{idProfessional}")
    @GET
    @Produces("application/json")
    CompletionStage<ProfesionalSchemaResponse> findProfessional(@Pattern(regexp = UUID_PATTERN, message = "El campo 'profesionalId' debe ser un UUID válido")
                                                                @PathParam("idProfessional")
                                                                @NotNull String idProfessional);

    /**
     *
     */
    @Operation(description = "", summary = "Editar profesionales", operationId = "updateProfessional")
    @Path("/{idProfessional}")
    @PUT
    @Consumes("application/json")
    CompletionStage<Void> updateProfessional(@Pattern(regexp = UUID_PATTERN, message = "El campo 'profesionalId' debe ser un UUID válido")
                                             @PathParam("idProfessional") String idProfessional,
                                             @NotNull ProfesionalSchemaRequest data);

    /**
     *
     */
    @Operation(description = "", summary = "Borrar Profesionales", operationId = "deleteProfessional")
    @Path("/{idProfessional}")
    @DELETE
    CompletionStage<Void> deleteProfessional(@Pattern(regexp = UUID_PATTERN, message = "El campo 'profesionalId' debe ser un UUID válido")
                                             @PathParam("idProfessional")
                                             @NotNull String idProfessional);
}
