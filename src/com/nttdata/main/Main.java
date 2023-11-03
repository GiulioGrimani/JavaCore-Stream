package com.nttdata.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.nttdata.model.Citta;
import com.nttdata.model.Persona;

public class Main {

	public static void main(String[] args) {
		/*
		 * Stream
		 * 
		 * Gli stream introdotti in Java 8 (java.util.stream.Stream<T>) sono uno
		 * strumento che permette una gestione avanzata degli insiemi di dati (ad
		 * esempio array e collections) grazie all'uso della programmazione funzionale e
		 * delle lambda expression.
		 * 
		 * Difatti, gli stream si avvalgono delle interfacce funzionali per operare
		 * sugli elementi dell'insieme. Java mette a disposizione un set di interfacce
		 * funzionali standard usate dai metodi degli stream. Vediamole brevemente
		 * giusto per poi capire meglio gli stream.
		 */

		String functionalInterfaces = """
				*************************
				* Interfacce funzionali *
				*************************
				""";
		System.out.println(functionalInterfaces);

		/*
		 * Function<T, R>
		 * 
		 * Input: <T> (un generico T di Tipo)
		 * 
		 * Output: <R> (un generico R di Risultato)
		 * 
		 * metodo: apply(T t)
		 * 
		 * spiegazione: e' la tipica y = f(x), dove il risultato y e' di tipo R, l'input
		 * x e' di tipo T e la funzione f la decidi tu.
		 */

		Function<Integer, String> generaFraseEta = n -> "Ho ben " + n + " anni!";
		String frase = generaFraseEta.apply(500);
		System.out.println("Output di Function<T, R>: " + frase);

		/*
		 * BinaryOperator<T>
		 * 
		 * Input: <T> (due generici T di Tipo)
		 * 
		 * Output: <T> (un generico T di Tipo)
		 * 
		 * metodo: apply(T t1, T t2)
		 * 
		 * spiegazione: tipico operatore binario, ovvero che prende in input due
		 * argomenti (dello stesso tipo T) e restituisce in output un valore (sempre
		 * dello stesso tipo degli argomenti in input).
		 */

		BinaryOperator<Integer> trovaMax = (n1, n2) -> n1 > n2 ? n1 : n2;
		int max = trovaMax.apply(1, 2);
		System.out.println("Output di BinaryOperator<T>: " + max);

		/*
		 * Supplier<T>
		 * 
		 * Input: nessuno
		 * 
		 * Output: <T> (un generico T di Tipo)
		 * 
		 * metodo: get()
		 * 
		 * spiegazione: non fa altro che restituire un'istanza del tipo generico
		 * specificato in base a quanto dichiarato
		 */

		Supplier<Persona> generaPersona = () -> new Persona("Giulio");
		Persona giulio = generaPersona.get();
		System.out.println("Output di Supplier<T>: " + giulio);

		/*
		 * Consumer<T>
		 * 
		 * Input: <T> (un generico T di Tipo)
		 * 
		 * Output: nessuno (e' un void)
		 * 
		 * metodo: accept(T t)
		 * 
		 * spiegazione: esegue semplicemente delle istruzioni
		 */

		Consumer<String> strilla = s -> System.out.println(s.toUpperCase());
		System.out.print("Output di Consumer<T>: ");
		strilla.accept("c'e' nessuno?");

		/*
		 * Predicate<T>
		 * 
		 * Input: <T> (un generico T di Tipo)
		 * 
		 * Output: boolean
		 * 
		 * metodo: test(T t)
		 * 
		 * spiegazione: restituisce un booleano corrispondente all'esito della
		 * valutazione dell'espressione logica da te definita. L'ideale per validare dei
		 * valori.
		 */

		Predicate<String> rappresentaNumero = s -> s.matches("\\d+");
		boolean res = rappresentaNumero.test("123");
		System.out.println("Output di Predicate<T>: " + res);

		/*
		 * Fatto questo piccolo e non esaustivo riassunto sulle interfacce funzionali
		 * usate dagli stream, iniziamo col dire che:
		 * 
		 * - uno stream e' una struttura dati che NON contiene gli elementi
		 * dell'insieme, ma li estrae dall'insieme sorgente uno ad uno, in un flusso
		 * (stream, per l'appunto) di dati che e' possibile manipolare usando gli
		 * strumenti tipici della programmazione funzionale (interfacce funzionali e
		 * lambda) anziche' usare i tradizionali cicli for
		 * 
		 * - uno stream viene quindi prodotto a partire da una sorgente (array o
		 * collections ad esempio) e NON modifica mai la sorgente dalla quale si
		 * origina. Ad esempio: se la sorgente e' una List di oggetti qualsiasi, le
		 * attivita' che svolge il relativo stream (magari di filtro o di trasformazione
		 * varia degli elementi) non modifichera' in alcun modo gli elementi della List
		 * sorgente.
		 * 
		 * L'API di riferimento degli stream e', come gia' detto,
		 * java.util.stream.Stream<T>. Tuttavia a questa componente si affiancano anche
		 * IntStream, DoubleStream e LongStream che, come avrete capito, sono degli
		 * stream specifici per la gestione dei numeri e che, per questo, offrono delle
		 * funzionalita' piu' legate al calcolo.
		 * 
		 * Su uno stream di qualsiasi tipo e' possibile eseguire una e una sola
		 * operazione terminale (operazione che estrae/restituisce il risultato di una
		 * elaborazione) e zero o piu' operazioni intermedie (operazioni che agiscono
		 * sullo stream restituendo sempre e comunque un altro stream). E' quindi
		 * possibile agganciare piu' operazioni intermedie per terminare con
		 * un'operazione terminale, creando quella che vien definita una "pipe":
		 * 
		 * Sorgente --> op. intermedia --> op. intermedia --> op. terminale
		 * 
		 * Una volta eseguita l'operazione terminale su uno stream, lo stream viene
		 * chiuso e non e' piu' riutilizzabile.
		 * 
		 * Vediamo quindi i mille modi di creare uno stream:
		 */

		// Il metodo statico empty() di Stream crea uno stream vuoto:
		Stream<String> emptyStream = Stream.empty();

		// Il metodo statico of() di Stream crea uno stream contenente gli elementi
		// passati in input, che in questo caso fanno da sorgente:
		Stream<String> ofStream = Stream.of("All", "you", "need", "is", "love");

		// Si ottiene un risultato analogo al precedente se usiamo l'inner Builder della
		// classe Stream, aggiungendo ogni elemento col metodo add() e finalizzando il
		// tutto col metodo build()
		Stream.Builder<String> builder = Stream.builder();
		Stream<String> builderStream = builder.add("All").add("you").build();

		// Non dimentichiamoci dei fratellini minori:
		IntStream integerStream = IntStream.of(1, 2, 3, 4, 5);
		IntStream integerStream2 = IntStream.range(1, 6);

		// Il metodo stream() invocato su un oggetto di tipo Collection<T> restituisce
		// il relativo stream<T>. E' il metodo piu' utilizzato per creare uno stream,
		// poiche' e' abbastanza raro creare uno stream da zero e perche' normalmente
		// sono le Collection gia' esistenti le sorgenti principali degli stream
		String[] arrayBeatles = { "John", "Paul", "George", "Ringo" };
		List<String> listBeatles = new ArrayList<>(Arrays.asList(arrayBeatles));
		Stream<String> streamBeatles = listBeatles.stream();

		// Si puo' generare uno stream anche direttamente dagli array, usando il metodo
		// statico stream(T[] array) della classe Arrays
		Stream<String> arrayStream = Arrays.stream(arrayBeatles);

		// Qualche modo di creare uno stream da zero puo' tornare comunque utile. Ad
		// esempio, invocare i metodi statici ints(int n) o longs(int n) o doubles(int
		// n) su un oggetto di tipo Random restituisce un IntStream (o LongStream o
		// DoubleStream) fatto da n numeri generati casualmente:
		IntStream rng = (new Random()).ints(5);

		/*
		 * Quando creiamo uno stream da una Collection possiamo scegliere tra due
		 * modalita' con le quali verranno effettuate le operazioni intermedie della
		 * pipe dello stream: modalita' sequenziale o in parallelo.
		 * 
		 * Se usiamo il metodo stream() otterremo uno stream che svolge le operazioni
		 * intermedie in modalita' sequenziale. Se usiamo il metodo parallelStream()
		 * otterremo uno stream che svolge le operazioni intermedie in modalita'
		 * parallela, sfruttando quindi i core del processore per svolgere in parallelo
		 * piu' operazioni.
		 * 
		 * Uno stream creato in modalita' parallela puo' essere convertito in uno stream
		 * in modalita' sequenziale tramite l'invocazione del metodo sequential().
		 * 
		 * Viceversa, il metodo parallel() trasforma uno stream nato in modalita'
		 * sequenziale in modalita' parallela
		 */

		Stream<String> sequentialBeatlesStream = listBeatles.stream();
		Stream<String> parallelBeatlesStream = listBeatles.parallelStream();

		Stream<String> fromParallelToSequential = parallelBeatlesStream.sequential();
		Stream<String> fromSequentialToParallel = sequentialBeatlesStream.parallel();

		/*
		 * Il vantaggio del fare le operazioni intermedie in parallelo e' evidente, ma
		 * tale modalita' paga un prezzo computazionale piu' alto rispetto alla
		 * modalita' sequenziale in fase di creazione e chiusura dello stream, pertanto
		 * conviene solo quando le operazioni intermedie sono massive
		 * 
		 * Cosa si intende esattamente per operazioni terminali e operazioni intermedie?
		 * 
		 * Le operazioni terminali sono tutti quei metodi che, invocati su uno stream,
		 * restituiscono una qualsiasi cosa diversa da uno stream. Una volta eseguito
		 * uno di questi metodi, lo stream non e' piu' riutilizzabile.
		 * 
		 * Viceversa, le operazioni intermedie sono tutti quei metodi che, invocati su
		 * uno stream, restituiscono uno stream. Lo stream restituito sara' ovviamente
		 * frutto di un'elaborazione (intermedia per l'appunto) sullo stream precedente.
		 * 
		 * Vediamo quali sono alcune delle piu' importanti operazioni terminali:
		 */

		String streamTerminalOps = """
				************************
				*        Stream        *
				* Operazioni terminali *
				************************
					""";
		System.out.println("\n\n" + streamTerminalOps);

		/*
		 * metodo: count()
		 * 
		 * argomenti: nessuno
		 * 
		 * tipo restituito: long
		 * 
		 * spiegazione: restituisce la dimensione dello stream, ovvero il numero di
		 * elementi contenuti
		 */
		Stream<String> beatlesStream1 = Arrays.stream(arrayBeatles);
		long size = beatlesStream1.count();
		System.out.println("output di count(): " + size);

		/*
		 * metodo: anyMatch(Predicate<T> predicate)
		 * 
		 * argomenti: Predicate<T> predicate
		 * 
		 * tipo restituito: boolean
		 * 
		 * spiegazione: restituisce true se almeno uno degli elementi dello stream
		 * soddisfa il predicato dato in input
		 */
		Stream<String> beatlesStream2 = Arrays.stream(arrayBeatles);
		Predicate<String> findPaul = s -> s.matches("Paul");
		boolean isPaulPresent = beatlesStream2.anyMatch(findPaul);
		System.out.println("output di anyMatch(Predicate<T> predicate): " + isPaulPresent);

		/*
		 * metodo: allMatch(Predicate<T> predicate)
		 * 
		 * argomenti: Predicate<T> predicate
		 * 
		 * tipo restituito: boolean
		 * 
		 * spiegazione: restituisce true sse tutti gli elementi dello stream soddisfano
		 * il predicato dato in input
		 */
		Stream<String> numberStream = Arrays.stream(new String[] { "uno", "due", "tre" });
		Predicate<String> threeLetters = s -> s.length() == 3;
		boolean only3Letters = numberStream.allMatch(threeLetters);
		System.out.println("output di allMatch(Predicate<T> predicate): " + only3Letters);

		/*
		 * metodo: noneMatch(Predicate<T> predicate)
		 * 
		 * argomenti: Predicate<T> predicate
		 * 
		 * tipo restituito: boolean
		 * 
		 * spiegazione: restituisce sse tutti gli elementi dello stream non soddisfano
		 * il predicato dato in input
		 */
		Stream<String> numberStream2 = Arrays.stream(new String[] { "uno", "due", "3" });
		Predicate<String> isNumber = s -> s.matches("\\d+");
		boolean onlyWords = numberStream2.noneMatch(isNumber);
		System.out.println("output di noneMatch(Predicate<T> predicate): " + onlyWords);

		/*
		 * metodo: toArray()
		 * 
		 * argomenti: nessuno
		 * 
		 * tipo restituito: Object[]
		 * 
		 * spiegazione: restituisce un array di tipo Object contenente tutti gli
		 * elementi presenti nello Stream. Attenzione: li restituisce di tipo Object,
		 * non del tipo generico specificato dallo stream
		 */
		Stream<String> numberStream3 = Stream.of("uno", "due", "tre");
		Object[] numArray = numberStream3.toArray();
		System.out.println("output di toArray(): " + Arrays.toString(numArray));

		/*
		 * metodo: forEach(Consumer<T> consumer)
		 * 
		 * argomenti: Consumer<T> consumer
		 * 
		 * tipo restituito: void
		 * 
		 * spiegazione: applica la funzione definita dal consumer ad ogni elemento dello
		 * stream, non restituendo nulla. Viene tipicamente usato per iterare su ogni
		 * elemento dello stream
		 */
		Stream<String> beatlesStream3 = Arrays.stream(arrayBeatles);
		Consumer<String> printWithSpace = s -> System.out.print(s + " ");
		System.out.print("output di forEach(Consumer<T> consumer): ");
		beatlesStream3.forEach(printWithSpace);
		System.out.println();

		/*
		 * metodo: collect(Collectors collector)
		 * 
		 * argomenti: principalmente quattro:
		 * 
		 * - Collectors.toList()
		 * 
		 * - Collectors.toSet()
		 * 
		 * - Collectors.joining(String separator)
		 * 
		 * - Collectors.groupingBy(Function <T, R> classifier)
		 * 
		 * tipo restituito: in ordine di apparizione:
		 * 
		 * - una List<T> con T tipo dello stream
		 * 
		 * - un Set<T> con T tipo dello stream
		 * 
		 * - una String in cui ogni elemento dello stream e' separato dalla String di
		 * separazione data in input
		 * 
		 * - Una Map<R, List<T>>
		 * 
		 * spiegazione: questo metodo consente sostanzialmente di trasformare lo stream
		 * nella Collection che ci pare usando dei metodi della classe Collectors
		 */
		// Collectors.toList()
		Stream<String> beatlesStream4 = Arrays.stream(arrayBeatles);
		List<String> beatlesList4 = beatlesStream4.collect(Collectors.toList());
		System.out.print("output di collect(Collector.toList()): ");
		beatlesList4.forEach(printWithSpace);
		System.out.println();

		// Collectors.toSet()
		Stream<String> numberStream4 = Stream.of("uno", "due", "tre", "uno", "tre");
		Set<String> numberSet4 = numberStream4.collect(Collectors.toSet());
		System.out.print("output di collect(Collector.toSet()): ");
		numberSet4.forEach(printWithSpace);
		System.out.println();

		// Collectors.joining(String separator)
		Stream<String> messageStream = Stream.of("All", "you", "need", "is", "love");
		String messageString = messageStream.collect(Collectors.joining("*"));
		System.out.println("output di collect(Collector.toSet(separator *): " + messageString);

		// Collectors.groupingBy(Function<T, R> classifier)
		Stream<Citta> streamCitta = Stream.of(new Citta("Roma", "Lazio"), new Citta("Viterbo", "Lazio"),
				new Citta("Torino", "Piemonte"), new Citta("Vercelli", "Piemonte"), new Citta("Palermo", "Sicilia"),
				new Citta("Agrigento", "Sicilia"));
		Function<Citta, String> getRegion = c -> c.getRegione();
		Map<String, List<Citta>> mappaCittaPerRegione = streamCitta.collect(Collectors.groupingBy(getRegion));
		System.out.println("output di collect(Collectors.groupingBy(Function <T, R> classifier)):");
		for (String key : mappaCittaPerRegione.keySet()) {
			System.out.print(key + ": ");
			mappaCittaPerRegione.get(key).forEach(c -> System.out.print(c + " "));
			System.out.println();
		}

		// Vale la pena menzionare brevemente il metodo toList() che, invocato su uno
		// stream, restituisce anch'esso una List dello stesso tipo dello stream. La
		// differenza con il collect(Collectors.toList()) e' che il toList() restituisce
		// una List immutabile, ovvero una List sulla quale non si possono fare
		// operazioni di aggiunta o rimozione di elementi ne' di modifica degli
		// elementi, pena il lancio di una UnsupportedOperationException
		Stream<String> niceMessage = Stream.of("All", "you", "need", "is", "love");
		List<String> niceMessageList = niceMessage.toList();
//		niceMessageList.add("dududurudu"); // questa istruzione lancia un'eccezione

		/*
		 * metodo: reduce(T identity, BinaryOperator<T,T> accumulator)
		 * 
		 * argomenti:
		 * 
		 * - T identity
		 * 
		 * - BinaryOperator<T,T> accumulator
		 * 
		 * tipo restituito: un oggetto di tipo T, ovvero dello stesso tipo dello stream
		 * 
		 * spiegazione: questo metodo consente di ridurre l'intero stream ad un valore
		 * per cosi' dire 'rappresentativo', ed opera in modo abbastanza peculiare: usa
		 * il BinaryOperator dove il primo operando rappresenta il valore che verra'
		 * restituito e il secondo operando rappresenta, di volta in volta, l'elemento
		 * all'interno dello stream. Ad ogni iterazione viene quindi eseguita la
		 * funzione definita nel BinaryOperator tra il primo ed il secondo operando, e
		 * viene salvato il risultato nel primo operando. Inizialmente, il primo
		 * operando vale identity. L'esempio tipico di come opera questo metodo e' la
		 * sommatoria:
		 */
		IntStream numeri = IntStream.of(1, 2, 3, 4, 5);
		IntBinaryOperator somma = (n1, n2) -> n1 + n2;
		int risultato = numeri.reduce(0, somma);
		System.out.println("output di reduce(T identity, BinaryOperator<T,T> accumulator): " + risultato);

		/*
		 * Passiamo ora a vedere quali sono le piu' comuni operazioni intermedie.
		 * Ricordo che le operazioni intermedie sono quelle che, invocate su uno stream,
		 * restituiscono in output un altro stream frutto dell'elaborazione intermedia
		 * in questione
		 */
		String streamMiddleOps = """
				*************************
				*        Stream         *
				* Operazioni intermedie *
				*************************
					""";
		System.out.println("\n\n" + streamMiddleOps);

		/*
		 * metodo: limit(long maxSize)
		 * 
		 * argomenti: long maxSize
		 * 
		 * stream restituito: i primi maxSize elementi dello stream
		 */
		Stream<String> giorni = Stream.of("Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica");
		System.out.print("output di limit(long maxSize): ");
		giorni.limit(4).forEach(s -> System.out.print(s + " "));
		System.out.println();

		/*
		 * metodo: skip(long n)
		 * 
		 * argomenti: long n
		 * 
		 * stream restituito: complementare di limit, restituisce tutti gli elementi
		 * dello stream saltando i primi n elementi
		 */
		Stream<String> days = Stream.of("Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica");
		System.out.print("output di limit(skip n): ");
		days.skip(4).forEach(s -> System.out.print(s + " "));
		System.out.println();

		/*
		 * metodo: distinct()
		 * 
		 * argomenti: nessuno
		 * 
		 * stream restituito: lo stream di partenza senza eventuali duplicati
		 */
		Stream<String> nums = Stream.of("uno", "due", "tre", "due", "tre", "uno");
		System.out.print("output di distinct(): ");
		nums.distinct().forEach(n -> System.out.print(n + " "));
		System.out.println();

		/*
		 * metodo: sorted()
		 * 
		 * argomenti: nessuno
		 * 
		 * stream restituito: lo stream di partenza con gli elementi ordinati, a patto
		 * che gli elementi siano di un tipo che implementa l'interfaccia Comparable,
		 * come ad esempio String
		 */
		Stream<String> animali = Stream.of("Zebra", "Alce", "Gatto", "Cane");
		System.out.print("output di sorted(): ");
		animali.sorted().forEach(a -> System.out.print(a + " "));
		System.out.println();

		/*
		 * metodo: takeWhile(Predicate<T> predicate)
		 * 
		 * argomenti: Predicate<T> predicate
		 * 
		 * stream restituito: preleva a partire dal primo tutti gli elementi dello
		 * stream di partenza fin tanto che incontra elementi che soddisfano il
		 * predicato. Appena incontra un elemento che non soddisfa il predicato, smette
		 * di prelevare elementi e restituisce lo stream (quindi lo stream dei primi n
		 * elementi consecutivi che soddisfano il predicato)
		 */
		Stream<Citta> streamCities = Stream.of(new Citta("Roma", "Lazio"), new Citta("Viterbo", "Lazio"),
				new Citta("Torino", "Piemonte"), new Citta("Vercelli", "Piemonte"), new Citta("Palermo", "Sicilia"),
				new Citta("Agrigento", "Sicilia"), new Citta("Latina", "Lazio"));
		Predicate<Citta> isLazio = c -> c.getRegione().equals("Lazio");
		System.out.print("output di takeWhile(Predicate<T> predicate): ");
		streamCities.takeWhile(isLazio).forEach(c -> System.out.print(c + " "));
		System.out.println();

		/*
		 * metodo: dropWhile(Predicate<T> predicate)
		 * 
		 * argomenti: Predicate<T> predicate
		 * 
		 * stream restituito: complementare di takeWhile, scarta a partire dal primo
		 * tutti gli elementi dello stream di partenza fin tanto che incontra elementi
		 * che soddisfano il predicato. Appena incontra un elemento che non soddisfa il
		 * predicato, restituisce lo stream composto da tutti gli altri elementi (quindi
		 * lo stream di partenza esclusi i primi n elementi consecutivi che soddisfano
		 * il predicato)
		 */
		Stream<Citta> streamOfCities = Stream.of(new Citta("Roma", "Lazio"), new Citta("Viterbo", "Lazio"),
				new Citta("Torino", "Piemonte"), new Citta("Vercelli", "Piemonte"), new Citta("Palermo", "Sicilia"),
				new Citta("Agrigento", "Sicilia"), new Citta("Latina", "Lazio"));
		System.out.print("output di dropWhile(Predicate<T> predicate): ");
		streamOfCities.dropWhile(isLazio).forEach(c -> System.out.print(c + " "));
		System.out.println();

		/*
		 * metodo: filter(Predicate<T> predicate)
		 * 
		 * argomenti: Predicate<T> predicate
		 * 
		 * stream restituito: tutti gli elementi dello stream che soddisfano il
		 * predicato. Filtra sostanzialmente gli elementi dello stream in base ad un
		 * criterio
		 */
		Stream<String> nomi = Stream.of("Tom", "Jenny", "Alice", "John", "Ken", "Joe");
		System.out.print("output di filter(Predicate<T> predicate): ");
		nomi.filter(nome -> nome.startsWith("J")).forEach(s -> System.out.print(s + " "));
		System.out.println();

		/*
		 * metodo: map(Function<T, R> mapper)
		 * 
		 * argomenti: Function<T, R> mapper
		 * 
		 * stream restituito: lo stream di partenza dove su ogni elemento e' stata
		 * applicata la Function in input
		 */
		Stream<Citta> cities = Stream.of(new Citta("Roma", "Lazio"), new Citta("Viterbo", "Lazio"),
				new Citta("Torino", "Piemonte"), new Citta("Vercelli", "Piemonte"), new Citta("Palermo", "Sicilia"),
				new Citta("Agrigento", "Sicilia"));
		Function<Citta, String> getRegione = c -> c.getRegione();
		System.out.print("output di map(Function<T, R> mapper): ");
		cities.map(getRegione).distinct().forEach(r -> System.out.print(r + " "));
		System.out.println();
	}
}
