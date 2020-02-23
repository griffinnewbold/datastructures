package edu.util.newbold.griffin;

import edu.jenks.dist.util.*;
import java.util.NoSuchElementException;

public class LinkedList<E> implements AbstractLinkedList<E> {

	Node head;
	public int size = 0;

	public LinkedList() {
		super();
	}

	// method passed testing ignore
	public boolean add(E e) {
		Node node = new Node();
		node.data = e;
		node.next = null;

		if (head == null) {
			head = node;
		} else {
			Node n = head;
			while (n.next != null) {
				n = n.next;
			}
			n.next = node;
		}
		size++;
		return true;
	}

	// method passed testing ignore
	public void add(int index, E element) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node node = new Node();
		node.data = element;
		node.next = null;

		if (index == 0) {
			addFirst(element);
		} else {
			Node n = head;
			for (int i = 0; i < index - 1; i++) {
				n = n.next;
			}
			node.next = n.next;
			n.next = node;
			size++;
		}
	}

	// method passed testing ignore
	public void addFirst(E e) {
		Node node = new Node();
		node.data = e;
		node.next = null;
		node.next = head;
		head = node;
		size++;
	}

	// method passed testing ignore
	public void addLast(E e) {
		add(size, e);
	}

	public void clear() {
		if (size > 0) {
			Node node = head;
			while (node.next != null) {
				remove(0);
				node = node.next;
			}
			remove(0);
		}
	}

	// method passed testing ignore
	public boolean contains(Object o) {
		if (size == 0) {
			return false;
		}
		Node node = head;
		while (node.next != null) {
			if (o != null) {
				if (node.data.equals(o)) {
					return true;
				}
			} else {
				if (node.data == null) {
					return true;
				}
			}
			node = node.next;
		}
		if (o != null) {
			if (node.data.equals(o)) {
				return true;
			}
		} else {
			if (node.data == null) {
				return true;
			}
		}
		return false;

	}

	// method passed testing ignore
	public E get(int index) {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node node = head;
		int count = 0;
		while (count != index) {
			count++;
			node = node.next;
		}
		return node.data;
	}

	// method passed testing ignore
	public E getFirst() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return head.data;
	}

	// method passed testing ignore
	public E getLast() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		Node node = head;
		while (node.next != null) {
			node = node.next;
		}
		return node.data;
	}

	public int indexOf(Object o) {
		Node node = head;
		int count = 0;
		while (node.next != null) {
			if (node.data == null) {
				if (node.data == o) {
					return count;
				}
			} else {
				if (node.data.equals(o)) {
					return count;
				}
			}
			node = node.next;
			count++;
		}
		//lastcheck 
		if (node.data == null) {
			if (node.data == o) {
				return count;
			}
		} else {
			if (node.data.equals(o)) {
				return count;
			}
		}
		return -1;

	}

	// null pointer error resolved at 11:38
	public int lastIndexOf(Object o) {
		int index = -1;
		int counter = 0;
		Node node = head;
		while (node.next != null) {
			if (node.data == null) {
				if (node.data == null && o == null) {
					index = counter;
				}
			} else {
				if (node.data.equals(o)) {
					index = counter;
				}
			}
			node = node.next;
			counter++;
		}
		if (node.data == null) {
			if (node.data == null) {
				index = counter;
			}
		} else {
			if (node.data.equals(o)) {
				index = counter;
			}
		}
		return index;
	}

	public E remove() {
		return remove(0);
	}

	public E remove(int index) {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node retrival;
		if (index == 0) {
			retrival = head;
			head = head.next;
		} else {
			Node n = head;
			Node n1 = null;
			for (int i = 0; i < index - 1; i++) {
				n = n.next;
			}
			n1 = n.next;
			n.next = n1.next;
			retrival = n1;
			n1 = null;
		}
		size--;
		return retrival.data;
	}

	public boolean remove(Object o) {
		if (contains(o)) {
			int index = indexOf(o);
			remove(index);
			return true;
		} else {
			return false;
		}
	}

	public E removeLast() {
		return remove(size - 1);

	}

	public E set(int index, E element) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds");
		}
		Node node = head;
		E old;
		int count = 0;
		while (count != index) {
			node = node.next;
			count++;
		}
		old = node.data;
		node.data = element;
		return old;
	}

	// method passed testing ignore
	public int size() {
		return size;
	}

	// method passed testing ignore
	public String toString() {
		if (size == 0) {
			return "LinkedList(0) []";
		}
		String result = "LinkedList(" + (size) + ") [";
		Node node = head;
		while (node.next != null) {
			result += node.data;
			result += ", ";
			node = node.next;
		}
		result += node.data;
		result += ", ";
		result += "]";
		return result;
	}

	class Node {
		E data;
		Node next;
	}

	public static void main(String[] args) {
		LinkedList<String> list = new LinkedList<String>();
		list.get(0);
		
	}
}