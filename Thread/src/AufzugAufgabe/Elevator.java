package AufzugAufgabe;

import java.util.*;

public class Elevator extends Thread {

	public String name;
	public final static int minFloor = 1;
	public static int maxFloor = 4;
	public static int currentFloor;
	public String direction;
	public String door;
	public Starter starter = null;
	public LinkedList<Person> upList;
	public LinkedList<Person> downList;
	public LinkedList<Person> inList;

	/**
	 * upList -> Personen, die darauf warten, nach oben transportiert zu werden
	 * downList -> Personen, die darauf warten, nach unten transportiert zu werden
	 * inList -> Personen, die sich im Aufzug befinden
	 * @param n
	 */
	public Elevator(int n) {
		maxFloor = n;
		currentFloor = minFloor;
		direction = "up";
		door = "closed";
		upList = new LinkedList<Person>();
		downList = new LinkedList<Person>();
		inList = new LinkedList<Person>();
	}

	@Override
	public void run() {
		while (true) {
			if (isInterrupted()) {
				while (upList.head != null || downList.head != null || inList.head != null) {
					if (direction == "up" && currentFloor <= maxFloor) {
						servePeopleGoingUp();
						direction = "down";
					} else if (direction == "down" && currentFloor >= minFloor) {
						servePeopleGoingDown();
						direction = "up";
					}
				}
				break;
			}
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn die Personen hochfahren müssen.
	 * Zuerst werden zwei Knoten erstellt
	 */
	public void servePeopleGoingUp() {
		Node<Person> currentPersonInUpList = upList.head;
		Node<Person> currentPersonInElevator = inList.head;
		LinkedList<Person> tempList = new LinkedList<Person>();
		boolean isDoorOpen = false;

		while (currentPersonInUpList != null || currentPersonInElevator != null) { // schaut ob jemand rein/ raus möchte

			System.out.println("\n------------------ STOCKWERK " + currentFloor + " ---------------------\n");

			if (currentPersonInElevator != null) { // schaut ob jemand raus muss
				currentPersonInElevator = inList.head;
				while (currentPersonInElevator != null) {
					if (currentPersonInElevator.data.destinationFloor == currentFloor) {
						if (!isDoorOpen) {
							openDoor();
							isDoorOpen = false;
						}
						inList.remove(currentPersonInElevator.data);
						System.out.println("Die Person " + currentPersonInElevator.data + " steigt aus.");
						currentPersonInElevator.data.wakeUp();
					}
					currentPersonInElevator = currentPersonInElevator.next;
				}
			}

			if (currentPersonInUpList != null) { // schaut ob jemand rein möchte
				if (currentPersonInUpList.data.startFloor == currentFloor) {
					openDoor();
					while (currentPersonInUpList.data.startFloor == currentFloor) {
						System.out.println(
								"Die Person " + currentPersonInUpList.data + " steigt ein. Stockwerk: " + currentFloor);
						inList.addAsc(currentPersonInUpList.data);
						upList.remove(currentPersonInUpList.data);
						currentPersonInUpList = currentPersonInUpList.next;
						if (currentPersonInUpList == null)
							break;
					}
					closeDoor();
				}
			} else {
				closeDoor();
			}
			System.out.println("Möchten hoch: " + upList);
			System.out.println("Im Aufzug: " + inList);
			System.out.println("Möchten runter: " + downList);
			currentPersonInUpList = upList.head;
			currentPersonInElevator = inList.head;
			if (currentPersonInUpList != null || currentPersonInElevator != null)
				currentFloor = currentFloor + 1;
		}

//		hier werden die Menschen zu upList hinzugefügt, die nicht mit dem Aufzug gefahren sind
		Node<Person> currentPersonInTempList = tempList.head;
		while (currentPersonInTempList != null) {
			upList.addAsc(currentPersonInTempList.data);
			currentPersonInTempList = currentPersonInTempList.next;
		}

//		falls das höchste Stockwerk nicht erreicht wurde
		if (downList.head != null) {
			int downFloorStart = downList.head.data.startFloor;
			if (downFloorStart > currentFloor) {
				while (downFloorStart != currentFloor) {
					currentFloor = currentFloor + 1;
					System.out.println("\n------------------ STOCKWERK " + currentFloor + " ---------------------\n");
					System.out.println("Möchten hoch: " + upList);
					System.out.println("Im Aufzug: " + inList);
					System.out.println("Möchten runter: " + downList);
				}
			}
		}
	}

	public void servePeopleGoingDown() {

		Node<Person> currentPersonInDownList = downList.head;
		Node<Person> currentPersonInElevator = inList.head;
		LinkedList<Person> tempList = new LinkedList<Person>();
		boolean isDoorOpen = false;

		while (currentPersonInDownList != null || currentPersonInElevator != null) { // falls jemand rein oder raus

			System.out.println("\n------------------ STOCKWERK " + currentFloor + " ---------------------\n");

			if (currentPersonInElevator != null) { // falls jemand raus möchte

//				hier werden alle Personen überprüft
				currentPersonInElevator = inList.head;
				while (currentPersonInElevator != null) {
					if (currentPersonInElevator.data.destinationFloor == currentFloor) { // schauen ob jemandraus möchte
						if (!isDoorOpen) {
							openDoor();
							isDoorOpen = false;
						}
						inList.remove(currentPersonInElevator.data);
						System.out.println("Die Person " + currentPersonInElevator.data + " steigt aus.");
						currentPersonInElevator.data.wakeUp();
					}
					currentPersonInElevator = currentPersonInElevator.next;
				}
			}
			if (currentPersonInDownList != null) { // schauen ob jemand rein möchte
				if (currentPersonInDownList.data.startFloor == currentFloor) {
					openDoor();
					while (currentPersonInDownList.data.startFloor == currentFloor) {
						System.out.println("Die Person " + currentPersonInDownList.data + " steigt ein. Stockwerk: "
								+ currentFloor);
						inList.addDesc(currentPersonInDownList.data);
						downList.remove(currentPersonInDownList.data);
						currentPersonInDownList = currentPersonInDownList.next;
						if (currentPersonInDownList == null)
							break;
					}
					closeDoor();
				}
			} else {
				closeDoor();
			}

			System.out.println("Möchten hoch: " + upList);
			System.out.println("Im Aufzug: " + inList);
			System.out.println("Möchten runter: " + downList);

			currentPersonInDownList = downList.head;
			currentPersonInElevator = inList.head;

			if (currentPersonInDownList != null || currentPersonInElevator != null)
				currentFloor = currentFloor - 1;
		}

//		hier werden die Menschen zu downList hinzugefügt, die nicht mit dem Aufzug gefahren sind
		Node<Person> currentPersonInTempList = tempList.head;
		while (currentPersonInTempList != null) {
			downList.addDesc(currentPersonInTempList.data);
			currentPersonInTempList = currentPersonInTempList.next;
		}

//		falls das höchste Stockwerk nicht erreicht wurde
		if (upList.head != null) {
			int upFloorStart = upList.head.data.startFloor;
			if (upFloorStart < currentFloor) {
				while (upFloorStart != currentFloor) {
					currentFloor = currentFloor - 1;
					System.out.println("\n------------------ STOCKWERK " + currentFloor + " ---------------------\n");
					System.out.println("Möchten hoch: " + upList);
					System.out.println("Im Aufzug: " + inList);
					System.out.println("Möchten runter: " + downList);
				}
			}
		}
	}

	public void openDoor() {
		door = "open";
		System.out.println("Die Tür wird geöffnet.");
	}

	public void closeDoor() {
		door = "closed";
		System.out.println("Die Tür wird geschlossen.");
	}

}
