package com.etteplanmore.assignment.store;

import com.etteplanmore.assignment.model.Equipment;
import java.util.List;

public interface EquipmentStore {

  /**
   * Fetch equipment
   * @param number
   * @return
   * @throws StoreException
   */
  List<Equipment> getEquipments(int number) throws StoreException;

  /**
   * Search equipment
   * @param id
   * @return
   * @throws StoreException
   */
  List<Equipment> search(String id) throws StoreException;

  /**
   * Add equipment
   * @param equipment
   * @return
   * @throws StoreException
   */
  void saveEquipment(Equipment equipment) throws StoreException;

}
