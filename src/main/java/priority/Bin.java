/*
 * Bin.java
 *
 * Created on March 9, 2007, 9:05 PM
 *
 * From "Multiprocessor Synchronization and Concurrent Data Structures",
 * by Maurice Herlihy and Nir Shavit.
 * Copyright 2007 Elsevier Inc. All rights reserved.
 */

package priority;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple bin implementation used to service priority queues.
 * @param T item type
 * @author phantomD
 */
public class Bin<T> {
  List<T> list;
  public Bin() {
    list = new ArrayList<T>();
  }
  
  synchronized void put(T item) {
    list.add(item);
  }
  
  synchronized T get() {
    try {
      return list.remove(0);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }
  
  synchronized boolean isEmpty() {
    return list.isEmpty();
  }
  
}
