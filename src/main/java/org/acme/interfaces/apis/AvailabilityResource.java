package org.acme.interfaces.apis;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import org.acme.interfaces.requests.HorarioDisponibleSchemaRequest;
import org.acme.interfaces.responses.HorarioDisponibleSchemaResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * A JAX-RS interface. An implementation of this interface must be provided.
 */
@Path("/availability-hours")
public interface AvailabilityResource {
  /**
   * 
   */
  @Operation(description = "", summary = "Obtener Horarios de Disponibilidad", operationId = "listAvailabilityHour")
  @GET
  @Produces("application/json")
  CompletionStage<List<HorarioDisponibleSchemaResponse>> listAvailabilityHour();

  /**
   * 
   */
  @Operation(description = "", summary = "Crear Horarios de Disponibilidad", operationId = "createAvailabilityHour")
  @POST
  @Consumes("application/json")
  CompletionStage<Void> createAvailabilityHour(@NotNull HorarioDisponibleSchemaRequest data);

  /**
   * 
   */
  @Operation(description = "", summary = "Buscar Horarios de Disponibilidad", operationId = "findAvailabilityHour")
  @Path("/{availabilityHoursId}")
  @GET
  @Produces("application/json")
  CompletionStage<HorarioDisponibleSchemaResponse> findAvailabilityHour(
      @PathParam("availabilityHoursId") String availabilityHoursId);

  /**
   * 
   */
  @Operation(description = "", summary = "Editar Horarios de Disponibilidad", operationId = "updateAvailabilityHour")
  @Path("/{availabilityHoursId}")
  @PUT
  @Consumes("application/json")
  CompletionStage<Void> updateAvailabilityHour(@PathParam("availabilityHoursId") String availabilityHoursId,
      @NotNull HorarioDisponibleSchemaRequest data);

  /**
   * 
   */
  @Operation(description = "", summary = "Borrar Horarios de Disponibilidad", operationId = "deleteAvailabilityHour")
  @Path("/{availabilityHoursId}")
  @DELETE
  CompletionStage<Void> deleteAvailabilityHour(@PathParam("availabilityHoursId") String availabilityHoursId);
}
