package edu.util.newbold.griffin;

import edu.jenks.dist.util.*;
import java.util.LinkedList;
import static java.lang.System.*;

import java.lang.reflect.Array;

public class Hashtable<K, V> implements Map<K, V> {

	private int capacity;
	private int size = 0;
	private V[] valueArray;
	private K[] keyArray;
	private static final float LOAD_FACTOR = Map.LOAD_FACTOR;
	private LinkedList<ValuePair>[] table;

	class ValuePair {
		K key;
		V value;

		public ValuePair(K k, V v) {
			key = k;
			value = v;
		}

		public V getValue() {
			return value;
		}

		public K getKey() {
			return key;
		}

		public String toString() {
			return "<" + key + ", " + value + ">, ";
		}
	}

	public Hashtable(int initialCapacity) {
		capacity = initialCapacity;
		table = new LinkedList[initialCapacity];
	}

	public int hashFunction(Object o) {
		return MapHelper.hashFunction(o);
	}

	public void clear() {
		int length = table.length;
		size = 0;
		table = new LinkedList[length];
	}

	@Override
	public String toString() {
		String result = "Hashtable (" + size() + ") ";
		for (int i = 0; i < table.length; i++) {
			result += i + ": ";
			if (table[i] != null) {
				result += "LinkedList(" + table[i].size() + ") [";
				for (int j = 0; j < table[i].size(); j++) {
					result += table[i].get(j).toString();
				}
				result += "]; ";
			} else {
				result += "null; ";
			}
		}
		return result;
	}

	public boolean containsKey(Object key) {
		for (int i = 0; i < table.length; i++) {
			LinkedList<ValuePair> innerLooped = table[i];
			if (innerLooped != null) {
				for (int j = 0; j < innerLooped.size(); j++) {
					ValuePair testingPair = innerLooped.get(j);
					if (testingPair.getKey().equals(key)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			LinkedList<ValuePair> innerLooped = table[i];
			if (innerLooped != null) {
				for (int j = 0; j < innerLooped.size(); j++) {
					ValuePair testingPair = innerLooped.get(j);
					if (testingPair.getValue().equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int getArrayIndexFromKey(Object key) {
		return Math.abs(hashFunction(key) % table.length);
	}

	public int setCapacity(int capacity) {
		this.capacity = capacity;
		return this.capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public int size() {
		return size;
	}

	private void expandBackingArray() {
		LinkedList<ValuePair>[] doubledArray = new LinkedList[setCapacity(2 * getCapacity())];
		LinkedList<ValuePair>[] offcialTableCopy = table;
		table = doubledArray;
		size = 0;
		LinkedList<ValuePair> elementsToCopy = new LinkedList<>();
		for (int i = 0; i < offcialTableCopy.length; i++) {
			if (offcialTableCopy[i] != null) {
				for (int j = 0; j < offcialTableCopy[i].size(); j++) {
					elementsToCopy.add(offcialTableCopy[i].get(j));
				}
			}
		}
		for (int g = 0; g < elementsToCopy.size(); g++) {
			put(elementsToCopy.get(g).getKey(), elementsToCopy.get(g).getValue());
			System.out.println("This is size: " + size());
		}
	}

	public V get(Object key) {
		int tableIndex = getArrayIndexFromKey(key);
		LinkedList<ValuePair> listWithKey = table[tableIndex];
		for (int index = 0; index < listWithKey.size(); index++) {
			ValuePair currentPair = listWithKey.get(index);
			if (currentPair.getKey().equals(key)) {
				return currentPair.getValue();
			}
		}
		return null;
	}

	private boolean checkLoadFactor() {
		float currentFactor = (float) size() / getCapacity();
		if (currentFactor <= LOAD_FACTOR) {
			return false;
		} else {
			return true;
		}
	}

	public V put(K key, V value) {
		ValuePair newPair = new ValuePair(key, value);
		int index = getArrayIndexFromKey(key);
		if (table[index] == null) {
			table[index] = new LinkedList<ValuePair>();
		}
		LinkedList<ValuePair> toAdd = table[index];
		int desiredIndex = containsPreviousMapping(toAdd, key);
		if (desiredIndex >= 0) {
			ValuePair oldPair = toAdd.get(desiredIndex);
			toAdd.set(desiredIndex, newPair);
			return oldPair.getValue();
		} else {
			toAdd.add(newPair);
			size++;
			boolean safety = checkLoadFactor();
			if (safety) {
				// recovery = newPair;
				expandBackingArray();
			}
			return null;
		}
	}

	private int containsPreviousMapping(LinkedList<ValuePair> checkingList, K key) {
		for (int i = 0; i < checkingList.size(); i++) {
			ValuePair test = checkingList.get(i);
			if (test.getKey() != null && test.getKey().equals(key)) {
				return i;
			} else if (null == key) {
				return i;
			}
		}
		return -1;
	}

	public V remove(Object key) {
		if (!containsKey(key)) {
			return null;
		} else {
			ValuePair selectValue = null;
			int indexToRemove = getArrayIndexFromKey(key);
			LinkedList<ValuePair> listWithKey = table[indexToRemove];
			for (int i = 0; i < listWithKey.size(); i++) {
				ValuePair currentPair = listWithKey.get(i);
				if (currentPair.getKey().equals(key)) {
					selectValue = currentPair;
					listWithKey.remove(i);
					break;
				}
			}
			size--;
			return selectValue.getValue();
		}
	}
	//gets the values in the pairs
	public V[] getValues() {
		int tableIndex = findValidTable();
		int pairIndex = findValidPair(tableIndex);
		int count = 0;
		valueArray = (V[]) Array.newInstance(table[tableIndex].get(pairIndex).getValue().getClass(), size());
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				for (int j = 0; j < table[i].size(); j++) {
					valueArray[count++] = table[i].get(j).getValue();
				}
			}
		}
		return valueArray;
	}

	private int findValidTable() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				return i;
			}
		}
		return -1;
	}

	private int findValidPair(int tableIndex) {
		for (int i = 0; i < table[tableIndex].size(); i++) {
			if (table[tableIndex] != null) {
				return i;
			}
		}
		return -1;
	}
	//gets the keys in the pairs
	public K[] getKeys() {
		int tableIndex = findValidTable();
		int pairIndex = findValidPair(tableIndex);
		int count = 0;
		keyArray = (K[]) Array.newInstance(table[tableIndex].get(pairIndex).getKey().getClass(), size());
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				for (int j = 0; j < table[i].size(); j++) {
					keyArray[count++] = table[i].get(j).getKey();
				}
			}
		}
		return keyArray;
	}
}