//Diese Klasse definiert eine verkettete Liste mit generischen (allgemeingültigen) Elementen
//Die Elemente 

package AufzugAufgabe;

public class LinkedList<T extends Comparable<T>> {

	/**
	 * head ist der erste Knoten in der Liste
	 */
	public Node<T> head;

	/**
	 * Der Konstruktor initialisiert "head" mit "null", was bedeutet, dass die Liste
	 * beim Erstellen leer ist
	 */
	public LinkedList() {
		head = null;
	}

	/**
	 * Diese Methode fügt ein Element in die Liste in aufsteigender Reihenfolge ein.
	 * Wenn die Liste leer ist, wird "head" auf das neue Element gesetzt.
	 * Andernfalls wird das Element an der richtigen Stelle in die Liste eingefügt,
	 * indem der VErgleich der Elemente durchgeführt wird. Neue Elemente werden in
	 * aufsteigender Reihenfolge hinzugefügt.
	 * 
	 * @param object
	 */
	public void addAsc(T object) {
		if (head == null)
			head = new Node<T>(object, head);
		else {
			Node<T> newNode = new Node<>(object, null);
			Node<T> current = head;
			Node<T> previous = head;
			while (current != null) {
				if (current.data.compareTo(newNode.data) >= 0) {
					if (current != previous) {
						newNode.next = current;
						previous.next = newNode;
						break;
					} else {
						newNode.next = current;
						head = newNode;
						break;
					}
				}
				previous = current;
				current = current.next;
			}
			if (current == null) {
				previous.next = newNode;
			}
		}
	}

	/**
	 * Diese Methode führ ein Element in die Liste in absteigender Reihenfolge ein.
	 * Wenn die Liste leer ist, wird "head" auf das neue Element gesetzt.
	 * Andernfalls wird das Element an der richtigen Stelle in die Liste eingefügt,
	 * indem der VErgleich der Elemente durchgeführt wird. Neue Elemente werden in
	 * absteigender Reihenfolge hinzugefügt.
	 * 
	 * @param object
	 */
	public void addDesc(T object) {
		if (head == null)
			head = new Node<T>(object, head);
		else {
			Node<T> newNode = new Node<>(object, null);
			Node<T> current = head;
			Node<T> previous = head;
			while (current != null) {
				if (current.data.compareTo(newNode.data) <= 0) {
					if (current != previous) {
						newNode.next = current;
						previous.next = newNode;
						break;
					} else {
						newNode.next = current;
						head = newNode;
						break;
					}
				}
				previous = current;
				current = current.next;
			}
			if (current == null) {
				previous.next = newNode;
			}
		}
	}

	/**
	 * Diese Methode entfernt ein Element aus der Liste
	 * 
	 * @param object
	 */
	public void remove(T object) {
		Node<T> current = head;
		Node<T> previous = head;
		while (current != null) {
			if (current.data == object) {
				if (current != previous) {
					previous.next = current.next;
					break;
				} else {
					head = head.next;
					break;
				}
			}
			previous = current;
			current = current.next;
		}
	}

	/**
	 * Diese Methode "verschönert" die Ausgabe von Elevator
	 */
	public String toString() {
		Node<T> current = head;
		String temp = "\n[";
		while (current != null) {
			temp = temp + current.data + " ";
			current = current.next;
		}
		temp = temp + "] \n";
		return temp;
	}
}

/**
 * Das ist eine innere Klasse, die einen Knoten in der verketetten Liste
 * darstellt. Ein Knoten enthält Daten vom Typ "E" und einen Verweis auf den
 * nächsten Knoten in der Liste.
 * 
 * @param <E>
 */
class Node<E extends Comparable<E>> {
	E data;
	Node<E> next;

	Node(E data, Node<E> next) {
		this.data = data;
		this.next = next;
	}
}
