package it.unibs.arnaldo.planetarium;

public class PlanetariumMain {

	public static void main(String[] args) {
		// TODO: everything with menu ecc...
		
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
	}

}
