package AufzugAufgabe;

import java.util.*;

public class Starter {

	public Elevator elevator;

	public Starter() {
		System.out.println();
		System.out.println("----------------------------------------------");
		System.out.println("Dieses Gebäude hat vier Stockwerke.");
		System.out.println("Die Kapazität des Aufzugs beträgt 15 Personen.");
		System.out.println();
		System.out.println("----------------------------------------------");
		System.out.println();
		elevator = new Elevator(4);
		elevator.start();
	}

	/**
	 * Diese Methode führt das Programm aus.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Starter starter = new Starter();
		starter.test(3);
	}

	/**
	 * Diese Methode ruft den Aufzug auf, indem sie eine Person zu upList oder
	 * downList einfügt.
	 * 
	 * @param p
	 */
	public void callElevator(Person person) {
		if (person.direction == "up")
			elevator.upList.addAsc(person);
		else
			elevator.downList.addDesc(person);
	}

	/**
	 * Diese Methode erzeugt die Person.
	 * @param n
	 */
	public void test(int n) {
		elevator.starter = this;
		Random random = new Random();
		int startFloor;
		int destinationFloor;
		Person person;
		
		for (int i = 0; i < n; i++) {
			startFloor = random.nextInt(Elevator.maxFloor) + 1;
			destinationFloor = random.nextInt(Elevator.maxFloor) + 1;
			
			if ((startFloor == destinationFloor && startFloor != 4)) {
				destinationFloor++;
			} else if (startFloor == destinationFloor && startFloor == 4){
				destinationFloor--;
			}
			
			person = new Person(startFloor, destinationFloor, this);
			person.start();
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		elevator.interrupt();
	}
}
