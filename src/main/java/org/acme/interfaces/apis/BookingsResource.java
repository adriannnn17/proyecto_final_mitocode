package org.acme.interfaces.apis;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;

import org.acme.interfaces.resources.requests.RegistroReservaSchemaRequest;
import org.acme.interfaces.resources.responses.RegistroReservaSchemaResponse;
import org.acme.interfaces.resources.responses.ReservasSegunProfesionalSchemaResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * A JAX-RS interface. An implementation of this interface must be provided.
 */
@Path("/bookings")
public interface BookingsResource {
  /**
   * 
   */
  @Operation(description = "", summary = "Consultas de profesionales", operationId = "listProfessionalQuery")
  @Path("/professional-query")
  @GET
  @Produces("application/json")
  CompletionStage<ReservasSegunProfesionalSchemaResponse> listProfessionalQuery();

  /**
   * 
   */
  @Operation(description = "", summary = "Obtener Reservas", operationId = "listBookings")
  @GET
  @Produces("application/json")
  CompletionStage<List<RegistroReservaSchemaResponse>> listBookings(@QueryParam("idClient") String idClient,
                                                                    @QueryParam("idProfessional") String idProfessional, @QueryParam("maxDate") String maxDate,
                                                                    @QueryParam("minDate") String minDate, @QueryParam("specialty") String specialty);

  /**
   * 
   */
  @Operation(description = "", summary = "Crear Reservas", operationId = "createBookings")
  @POST
  @Consumes("application/json")
  CompletionStage<Void> createBookings(@NotNull RegistroReservaSchemaRequest data);

  /**
   * 
   */
  @Operation(description = "", summary = "Buscar Reservas", operationId = "findBookings")
  @Path("/{idBookings}")
  @GET
  @Produces("application/json")
  CompletionStage<RegistroReservaSchemaResponse> findBookings(@PathParam("idBookings") String idBookings);

  /**
   * 
   */
  @Operation(description = "", summary = "Editar Reservas", operationId = "updateBookings")
  @Path("/{idBookings}")
  @PUT
  @Consumes("application/json")
  CompletionStage<Void> updateBookings(@PathParam("idBookings") String idBookings,
      @NotNull RegistroReservaSchemaRequest data);

  /**
   * 
   */
  @Operation(description = "", summary = "Borrar Reservas", operationId = "deleteBookings")
  @Path("/{idBookings}")
  @DELETE
  CompletionStage<Void> deleteBookings(@PathParam("idBookings") String idBookings);
}
