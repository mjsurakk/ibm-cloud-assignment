package com.etteplanmore.assignment.api;

import com.etteplanmore.assignment.model.Equipment;
import com.etteplanmore.assignment.store.EquipmentStore;
import com.etteplanmore.assignment.store.StoreException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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

  @GET
  @Path("/search")
  @Produces(MediaType.APPLICATION_JSON)
  public String search(@QueryParam("limit") int limit) {
    return gson.toJson(store.getEquipments(limit));
  }

  @GET
  @Path("/{equipmentNumber}")
  @Produces(MediaType.APPLICATION_JSON)
  public String search(@PathParam("equipmentNumber") String equipmentNumber) {
    return gson.toJson(store.search(equipmentNumber));
  }

  @POST
  @Path("/")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createEquipment(Equipment equipment) {

    try {
      store.saveEquipment(equipment);
      return Response.ok(gson.toJson(equipment)).build();
    } catch (StoreException ex) {
      ex.printStackTrace(System.out);
      JsonObject o = new JsonObject();
      o.addProperty("error", ex.getMessage());
      return Response.status(Status.BAD_REQUEST).entity(gson.toJson(o)).build();
    }
  }

}
