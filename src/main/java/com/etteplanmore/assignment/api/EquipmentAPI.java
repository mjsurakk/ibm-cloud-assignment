package com.etteplanmore.assignment.api;

import com.etteplanmore.assignment.model.Equipment;
import com.etteplanmore.assignment.store.DuplicateEquipmentException;
import com.etteplanmore.assignment.store.EquipmentStore;
import com.etteplanmore.assignment.store.StoreException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@ApplicationPath("equipment")
@Path("/")
public class EquipmentAPI extends Application {

  @Inject
  private EquipmentStore store;

  private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

  /**
   * Assignment Use case 1 endpoint.
   * Fetch {@limit} equipments.
   * @param limit The amount to fetch
   * @return Equipment array as json
   */
  @GET
  @Path("/search")
  @Produces(MediaType.APPLICATION_JSON)
  public Response fetch(@QueryParam("limit") int limit) {
    return Response.ok(gson.toJson(store.getEquipments(limit))).build();
  }

  /**
   * Assignment Use case 2 endpoint.
   * Search equipment by equipment number. All equipment numbers which contain search term are returned.
   * @param equipmentNumber The search term
   * @return Equipment array as json
   */
  @GET
  @Path("/{equipmentNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response search(@PathParam("equipmentNumber") String equipmentNumber) {
    return Response.ok(gson.toJson(store.search(equipmentNumber))).build();
  }

  /**
   * Assignment Use case 3 endpoint.
   * Add new equipment. Equipment number must be unique, otherwise request will result in 400 Bad request error.
   * @param equipment
   * @return Added equipment as json
   */
  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createEquipment(Equipment equipment) {

    try {
      store.saveEquipment(equipment);
      return Response.ok(gson.toJson(equipment)).build();
    } catch (DuplicateEquipmentException ex) {
      JsonObject o = new JsonObject();
      o.addProperty("error", "duplicateEquipmentNumber");
      return Response.status(Status.BAD_REQUEST).entity(gson.toJson(o)).build();
    }
  }

}
