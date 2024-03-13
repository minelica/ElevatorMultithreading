package AufzugAufgabe;

import java.util.*;

public class Person extends Thread implements Comparable<Person> {

	public int startFloor;
	public int destinationFloor;
	public String direction;
	public Starter starter;

	/**
	 * Konstruktor, mit Überprüfungen und Ausgaben
	 * @param startFloor -> das aktuelle Stockwerk
	 * @param destinationFloor -> das gewünschte Stockwerk
	 * @param starter 
	 */
	public Person(int startFloor, int destinationFloor, Starter starter) {
		this.startFloor = startFloor;
		this.destinationFloor = destinationFloor;
		this.starter = starter;

		System.out.println("Die Person " + (getId() - 13) + " wartet im Stockwerk " + startFloor
				+ " und möchte zum Stockwerk " + destinationFloor + " transportiert werden.");

//		if (startFloor == destinationFloor) {
//			System.out.println("Die Person " + (getId() - 13) + " befindet sich schon auf dem gewünschten Stockwerk.");
//			System.exit(1);
//		}

		if (startFloor < destinationFloor) {
			direction = "up";
		} else {
			direction = "down";
		}
	}

	@Override
	public void run() {
		try {
			synchronized (starter) {
				starter.callElevator(this);
			}
			synchronized (this) {
				wait();
			}
			System.out.println("Thread-Ende: " + this);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}

	public void wakeUp() {
		try {
//			System.out.println("Thread " + this + " ?steht auf?");
			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int compareTo(Person person) {
		if (this.startFloor < person.startFloor) {
			return -1;
		} else if (this.startFloor > person.startFloor) {
			return 1;
		} else {
			return 0;
		}

	}

	public String toString() {
		return "Person " + (getId() - 13);
	}
}
