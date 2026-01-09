package org.acme.interfaces.apis;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import org.acme.interfaces.resources.requests.ClienteSchemaRequest;
import org.acme.interfaces.resources.responses.ClienteSchemaResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * A JAX-RS interface. An implementation of this interface must be provided.
 */
@Path("/clients")
public interface ClientsResource {
  /**
   * 
   */
  @Operation(description = "", summary = "Obtener Clientes", operationId = "listClients")
  @GET
  @Produces("application/json")
  CompletionStage<List<ClienteSchemaResponse>> listClients();

  /**
   * 
   */
  @Operation(description = "", summary = "Crear clientes", operationId = "createClients")
  @POST
  @Consumes("application/json")
  CompletionStage<Void> createClients(@NotNull ClienteSchemaRequest data);

  /**
   * 
   */
  @Operation(description = "", summary = "Buscar Clientes", operationId = "findClients")
  @Path("/{idClients}")
  @GET
  @Produces("application/json")
  CompletionStage<ClienteSchemaResponse> findClients(@PathParam("idClients") String idClients);

  /**
   * 
   */
  @Operation(description = "", summary = "Editar clientes", operationId = "updateClients")
  @Path("/{idClients}")
  @PUT
  @Consumes("application/json")
  CompletionStage<Void> updateClients(@PathParam("idClients") String idClients, @NotNull ClienteSchemaRequest data);

  /**
   * 
   */
  @Operation(description = "", summary = "Borrar Clientes", operationId = "deleteClients")
  @Path("/{idClients}")
  @DELETE
  CompletionStage<Void> deleteClients(@PathParam("idClients") String idClients);
}
