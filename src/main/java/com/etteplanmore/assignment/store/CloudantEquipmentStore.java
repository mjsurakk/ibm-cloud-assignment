package com.etteplanmore.assignment.store;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.query.Expression;
import com.cloudant.client.api.query.QueryBuilder;
import com.cloudant.client.api.query.Selector;
import com.etteplanmore.assignment.VCAPHelper;
import com.etteplanmore.assignment.model.Equipment;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.inject.Default;
import javax.inject.Singleton;

@Default
@Singleton
public class CloudantEquipmentStore implements EquipmentStore {

  private Database db = null;
  private static final String databaseName = "msurakka-assignment";

  public CloudantEquipmentStore(){
    CloudantClient cloudant = createClient();

    if(cloudant != null){
      db = cloudant.database(databaseName, true);
    }
  }

  private static CloudantClient createClient() {
    System.out.println("########## CREATE CLIENT");
    String url;

    if (System.getenv("VCAP_SERVICES") != null) {
      // When running in IBM Cloud, the VCAP_SERVICES env var will have the credentials for all bound/connected services
      // Parse the VCAP JSON structure looking for cloudant.
      JsonObject cloudantCredentials = VCAPHelper.getCloudCredentials("cloudant");
      if(cloudantCredentials == null){
        System.out.println("No cloudant database service bound to this application");
        return null;
      }
      url = cloudantCredentials.get("url").getAsString();
    } else if (System.getenv("CLOUDANT_URL") != null) {
      url = System.getenv("CLOUDANT_URL");
    } else {
      System.out.println("Running locally. Looking for credentials in cloudant.properties");
      url = VCAPHelper.getLocalProperties("cloudant.properties").getProperty("cloudant_url");
      if(url == null || url.length()==0){
        System.out.println("To use a database, set the Cloudant url in src/main/resources/cloudant.properties");
        return null;
      }
    }

    try {
      System.out.println("Connecting to Cloudant");
      CloudantClient client = ClientBuilder.url(new URL(url)).build();
      return client;
    } catch (Exception e) {
      System.out.println("Unable to connect to database");
      //e.printStackTrace();
      return null;
    }
  }

  private Equipment convert(EquipmentDocument doc) {
    Equipment equipment = new Equipment();
    equipment.setEquipmentNumber(doc.get_id());
    equipment.setAddress(doc.getAddress());
    equipment.setContractStartDate(doc.getContractStartDate());
    equipment.setContractEndDate(doc.getContractEndDate());
    equipment.setStatus(doc.getStatus());
    return equipment;
  }

  private EquipmentDocument convert(Equipment equipment) {
    // Convert entity to cloudant document with _id and _rev fields.
    // Could use a mapper, but for a simple scenario it's ok to map manually.
    EquipmentDocument doc = new EquipmentDocument();
    doc.set_id(equipment.getEquipmentNumber());
    doc.setAddress(equipment.getAddress());
    doc.setContractStartDate(equipment.getContractStartDate());
    doc.setContractEndDate(equipment.getContractEndDate());
    doc.setStatus(equipment.getStatus());
    return doc;
  }

  @Override
  public List<Equipment> getEquipments(int number) throws StoreException {
    System.out.println("getEquipments: "+number);
    try {
      return db.getAllDocsRequestBuilder().limit(number)
          .includeDocs(true).build().getResponse().getDocsAs(EquipmentDocument.class)
          .stream().map(this::convert).collect(Collectors.toList());
    } catch (IOException e) {
      throw new StoreException(e);
    }
  }

  @Override
  public List<Equipment> search(String id) throws StoreException {
    System.out.println("search: "+id);
    // return all documents whose _id contains the search term
    String q = new QueryBuilder(Expression.regex("_id", id)).build();
    return db.query(q, EquipmentDocument.class).getDocs().stream().map(this::convert).collect(Collectors.toList());
  }

  @Override
  public void saveEquipment(Equipment equipment) throws StoreException {

    List<EquipmentDocument> existing = db.query(new QueryBuilder(Expression.eq("_id", equipment.getEquipmentNumber())).build(),
        EquipmentDocument.class).getDocs();
    if(!existing.isEmpty()) {
      System.out.println("Existing is not empty");
      throw new DuplicateEquipmentException(equipment.getEquipmentNumber());
    }
    EquipmentDocument doc = convert(equipment);

    Response response = db.save(doc);
    if(response.getError() != null) {
      throw new StoreException(response.getError() + ": " + response.getReason());
    }
  }

  @Override
  public String getDbInfo() {
    return db.info().toString();
  }
}
