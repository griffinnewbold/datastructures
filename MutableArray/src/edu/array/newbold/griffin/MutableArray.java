package edu.array.newbold.griffin;
import edu.jenks.dist.array.*;


public class MutableArray implements List{
	
	private Object[] arr;
	private Object[] newArr;
	private int size = 0;
	
	public static void main(String[] args){
		System.out.println("Start");
		MutableArray test = new MutableArray(10);
		test.add(0, "First");
		test.add("New");
		test.add("Zero");
		test.add("one");
		test.add("hoi");
		test.isEmpty();
		test.add(2, "Replaced");
	//	test.remove(0);
		test.display();
		System.out.println("End");
	
		
	}
	
	public MutableArray(){
		this(10);
	}
	
	public MutableArray(int capacity){
		arr = new Object[capacity];
		
	}
	
	public void clear(){
        size = 0;
	}
	
	public boolean isEmpty(){
		if(size == 0){
			return true;
		}else{
			return false;
		}
		
	}
	
	public void add(int index, Object element) throws IndexOutOfBoundsException{
		checkBounds(index);
		if(arr.length-size <= 0){
            doubleArraySize();
        }
		for(int i = (size-1); i >= index ; i-- ){
			arr[i+1] = arr[i];
		}
		arr[index] = element;
		size++;
	}
	public boolean add(Object element){
		if(arr.length-size <= 0){
            doubleArraySize();
        }
		arr[size++] = element;
		display();
		return true;
	}
	public Object get(int index) throws IndexOutOfBoundsException{
		checkBoundsGetSetRemove(index);
		Object gettingObject = arr[index];
		return gettingObject;
	}
	public Object remove(int index) throws IndexOutOfBoundsException {
		checkBoundsGetSetRemove(index);
		Object removedItem = arr[index];
		for(int i = index; i < (size-1); i++){
			arr[i] = arr[i + 1];
		}
		size--;
		return removedItem;
	}
	
	public Object set(int index, Object element) throws IndexOutOfBoundsException {
		checkBoundsGetSetRemove(index);
		Object previousItem = arr[index];
		arr[index] = element;
		return previousItem;
	}
	
	public int size(){return size;}
	
	public Object[] toArray(){
		Object[] toArray = new Object[size];
		for(int g = 0; g < size; g++){
			if(arr[g] != null){
				toArray[g] = arr[g];
			}
		}
		return toArray;
	}
		
	
	
	public Object[] getBackingArray(){
		return arr;
	}
	
	private void doubleArraySize(){
		int newCapacity = arr.length * 2;
		newArr = new Object[newCapacity];
		for(int i = 0; i < size; i++){
			newArr[i] = arr[i];
		}
		arr = newArr;
		
	}
	private void checkBounds(int index){
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException("Index out of Bounds.");
		}
	}
	
	private void checkBoundsGetSetRemove(int index){
		if(index < 0 || index == size){
			throw new IndexOutOfBoundsException("Index out of Bounds.");
		}
	}
	private void display(){
		for(int i = 0; i < size; i++){
			System.out.println(arr[i]);
		}
		System.out.println(size);
	}
}