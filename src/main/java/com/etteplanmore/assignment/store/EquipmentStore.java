package com.etteplanmore.assignment.store;

import com.etteplanmore.assignment.model.Equipment;
import java.util.List;

public interface EquipmentStore {

  List<Equipment> getEquipments(int number) throws StoreException;

  List<Equipment> search(String id) throws StoreException;

  void saveEquipment(Equipment equipment) throws StoreException;

  String getDbInfo();

}
