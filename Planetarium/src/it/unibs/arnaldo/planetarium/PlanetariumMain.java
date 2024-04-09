package it.unibs.arnaldo.planetarium;

import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class PlanetariumMain {

	private static final String COLLISIONS = "Allarme! Rilevate possibili collisioni";
	private static final String NO_COLLISIONS = "Nessuna collisione rilevata";
	private static final String CONTAINS = "Il Corpo Celeste appartiene al Sistema Stellare";
	private static final String NO_CONTAINS = "Il Corpo Celeste NON appartiene al Sistema Stellare";
	private static final String UID_INPUT = "Inserire l'UID del Corpo Celeste da ricercare: ";
	private static final String STAR_INPUT_INTRODUCTION = "Inizio inserimento dati della stella...";
	private static final String POSY_INPUT = "Inserire la posizione Y del Corpo Celeste: ";
	private static final String POSX_INPUT = "Inserire la posizione X del Corpo Celeste: ";
	private static final String MASS_INPUT = "Inserire la massa del Corpo Celeste: ";
	private static final String NAME_INPUT = "Inserire il nome del Sistema Stellare: ";
	private static final String SHOW_MASSCENTER = "Il centro di massa del Sistema Stellare si trova in (%.3f, %.3f)";
	private static final String MENU_TITLE = "- PLANETARIUM -";
	private static final String[] MENU_ENTRIES = {
			"Aggiungere Pianeta",
			"Aggiungere Luna",
			"Rimuovere Pianeta",
			"Rimuovere Luna",
			"Ricercare Corpo Celeste",
			"Elencare Pianeti",
			"Elencare Lune",
			"Identificare Pianeta Orbitale",
			"Elencare Lune Orbitanti",
			"Visualizzare Percorso Luna",
			"Calcolare Centro di Massa",
			"Visualizzazare Centro di Massa",
			"Calcolare Rotta",
			"Verificare Possibili Collisioni"
	};

	public static void main(String[] args) {

		StarSystem starSystem;

		welcome();

		starSystem = init();

		manageOptions(starSystem);

		exit();

		/*
		String name = "Cha Halpha 1";
		Celestial star = new Celestial(name, 30, 0, 0, null);
		StarSystem ss = new StarSystem(name, star);
		ss.addPlanet(5, 0, -3);
		ss.addPlanet(7, 3, 3);
		ss.addMoon(1, -1, -1, star.getChildren().get(0));
		ss.addMoon(2, -1, 0, star.getChildren().get(1));
		ss.addMoon(1, 1, 1, star.getChildren().get(1));
		double[] mc = ss.calculateMassCenter();
		System.out.println(ss.toString() + String.format(" - Centro di massa: %.3f - %.3f", mc[0], mc[1]));

		//System.out.println(star.toString() + star.getChildren().get(1).toString());
		Route route = ss.getRoute(star.getChildren().get(0).getChildren().get(0), star.getChildren().get(1).getChildren().get(0));
		System.out.println(route.toString());
		System.out.println(route.getTravelDistance());

		if (ss.possibleCollisions()) System.out.println("Possibili collisioni");
		else System.out.println("Nessuna collisione");
		*/
	}

	private static void welcome() {
		//TODO: welcome
	}

	private static void exit() {
		//TODO: exit
	}

	private static StarSystem init() {
		String name = InputData.readNonEmptyString(NAME_INPUT, true);
		System.out.println(STAR_INPUT_INTRODUCTION);
		int[] data = inputData();
		Celestial star  = new Celestial(name, data[0], data[1], data[2], null);
		//TODO: messaggio creazione stella
		return new StarSystem(name, star);
	}

	private static void manageOptions(StarSystem starSystem) {
		Menu menu = new Menu(MENU_TITLE, MENU_ENTRIES, true, true, true);
		int choice;
		do {
			choice = menu.choose();
			switch (choice) {
				case 1:
					addPlanet(starSystem);
					break;
				case 2:
					addMoon(starSystem);
					break;
				case 3:
					removePlanet(starSystem);
					break;
				case 4:
					removeMoon(starSystem);
					break;
				case 5:
					searchCelestial(starSystem);
					break;
				case 6:
					showPlanets(starSystem);
					break;
				case 7:
					showMoons(starSystem);
					break;
				case 8:
					getReferencePlanet(starSystem);
					break;
				case 9:
					getOrbitatingMoons(starSystem);
					break;
				case 10:
					getMoonPath(starSystem);
					break;
				case 11:
					calculateMassCenter(starSystem);
					break;
				case 12:
					showMassCenter(starSystem);
					break;
				case 13:
					calculateRoute(starSystem);
					break;
				case 14:
					checkCollisions(starSystem);
					break;
				default:
					break;
			}
		} while (choice != 0);
	}

	private static void checkCollisions(StarSystem starSystem) {
		if (starSystem.possibleCollisions()) {
			System.out.println(COLLISIONS);
		} else {
			System.out.println(NO_COLLISIONS);
		}
	}

	private static void calculateRoute(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void showMassCenter(StarSystem starSystem) {
		System.out.println(String.format(SHOW_MASSCENTER, starSystem.getMassCenterX(), starSystem.getMassCenterY()));
	}

	private static void calculateMassCenter(StarSystem starSystem) {
		starSystem.calculateMassCenter();
		showMassCenter(starSystem);
	}

	private static void getMoonPath(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void getOrbitatingMoons(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void getReferencePlanet(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void showMoons(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void showPlanets(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void searchCelestial(StarSystem starSystem) {
		String uid = InputData.readNonEmptyString(UID_INPUT, true);
		if (starSystem.contains(uid)) {
			System.out.println(CONTAINS);
		} else {
			System.out.println(NO_CONTAINS);
		}
	}

	private static void removeMoon(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void removePlanet(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void addMoon(StarSystem starSystem) {
		// TODO Auto-generated method stub

	}

	private static void addPlanet(StarSystem starSystem) {
		int[] data = inputData();
		starSystem.addPlanet(data[0], data[1], data[2]);
	}

	private static int[] inputData() {
		int[] data = new int[3];
		data[0] = InputData.readIntegerWithMinimum(MASS_INPUT, 1);
		data[1] = InputData.readInteger(POSX_INPUT);
		data[2] = InputData.readInteger(POSY_INPUT);
		return data;
	}
}
