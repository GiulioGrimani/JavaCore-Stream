package com.nttdata.main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.nttdata.model.Persona;

/*
 * Esempi piu' comuni di applicazioni di lambda sugli Stream
 */

public class Main2 {
	public static void main(String[] args) {
		List<Persona> personList = new ArrayList<>();
		personList.add(new Persona("Giulio"));
		personList.add(new Persona("Marco"));
		personList.add(new Persona("Fabio"));

		/*
		 * Approccio Object Oriented
		 */

//		for tipico col quale si inizia a iterare sugli array
		for (int i = 0; i < personList.size(); i++) {
			System.out.println(personList.get(i));
		}

		// for each classico tipico della OOP
		for (Persona p : personList) {
			System.out.println(p);
		}

		/*
		 * Approccio funzionale
		 */

		Consumer<Persona> stampaPerson = p -> System.out.println(p);
		// uso la reference alla lambda Consumer
		personList.forEach(stampaPerson);

		// definisco la lambda direttamente all'interno delle tonde
		personList.forEach(p -> System.out.println(p));

		// uso la method reference
		personList.forEach(System.out::println);
	}
}
