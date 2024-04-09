package it.unibs.arnaldo.planetarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StarSystem {

	private String name;
	private Celestial star;
	private double massCenterX;
	private double massCenterY;
	public static final int PLANET_LIMIT = 26000;
	public static final int MOON_LIMIT = 5000;
	private static final String ADDPLANET_ERROR = "Impossibile aggiungere il pianeta! Limite fisico raggiunto!";
	private static final String ADDMOON_ERROR = "Impossibile aggiungere la luna! Limite fisico raggiunto!";
	private static final String SUCCESS = "Creazione avvenuta con successo!";
	private static final double EPSILON = 0.0001;

	public StarSystem(String name, Celestial star) {
		this.name = name;
		this.star = star;
		this.massCenterX = 0.00;
		this.massCenterY = 0.00;
	}

	public String getName() {
		return name;
	}

	public Celestial getStar() {
		return star;
	}

	public double getMassCenterX() {
		return massCenterX;
	}

	public double getMassCenterY() {
		return massCenterY;
	}

	public List<Celestial> getPlanets() {
		return star.getChildren();
	}

	public List<Celestial> getMoons() {
		List<Celestial> moons = new ArrayList<>();
		for (Celestial planet : star.getChildren()) {
			moons.addAll(planet.getChildren());
		}
		return moons;
	}

	public String addPlanet(int mass, int posX, int posY) {
		String outcome = "";
		if (star.getChildren().size() >= PLANET_LIMIT) {
			outcome = ADDPLANET_ERROR;
		} else {
			Celestial planet = new Celestial(name, mass, posX, posY, star);
			star.addChild(planet);
			outcome = SUCCESS + planet.toString();
		}
		return outcome;
	}

	public String addMoon(int mass, int posX, int posY, Celestial planet) {
		String outcome = "";
		if (planet.getChildren().size() >= MOON_LIMIT) {
			outcome = ADDMOON_ERROR;
		} else {
			Celestial moon = new Celestial(name, mass, posX, posY, planet);
			planet.addChild(moon);
			outcome = SUCCESS + moon.toString();
		}
		return outcome;
	}

	// eliminare un pianeta elimina anche tutte le sue lune
	public void deletePlanet(Celestial toDelete) {
		star.removeChild(toDelete);
	}

	public void deleteMoon(Celestial toDelete) {
		Celestial parent = toDelete.getParent();
		parent.removeChild(toDelete);
	}

	public boolean contains(String uid) {
		return findCelestial(uid) != null;
	}

	public Celestial getReferencePlanet(Celestial moon) {
		return moon.getParent();
	}

	public List<Celestial> getOrbitatingMoons(Celestial planet) {
		return planet.getChildren();
	}

	public List<Celestial> getPath(Celestial celestial) {
		List<Celestial> path = new ArrayList<>();
		while (celestial != null) {
			path.add(celestial);
			celestial = celestial.getParent();
		}
		Collections.reverse(path);
		return path;
	}

	public void calculateMassCenter() { //TODO: recursion ??
		// STELLA
		int massSum = star.getMass();
		int xSum = star.getPosX() * star.getMass();
		int ySum = star.getPosY() * star.getMass();
		List<Celestial> planets = star.getChildren();
		for (Celestial planet : planets) {
			int[] pos = getAbsolutePos(planet);
			// PIANETI
			massSum += planet.getMass();
			xSum += pos[0] * planet.getMass();
			ySum += pos[1] * planet.getMass();
			List<Celestial> moons = planet.getChildren();
			for (Celestial moon : moons) {
				//LUNE
				pos = getAbsolutePos(moon);
				massSum += moon.getMass();
				xSum += pos[0] * moon.getMass();
				ySum += pos[1] * moon.getMass();
			}
		}
		massCenterX = (double)xSum / massSum;
		massCenterY = (double)ySum / massSum;
	}

	public Route getRoute(Celestial c1, Celestial c2) {
		return new Route(this, c1, c2);
	}

	public boolean possibleCollisions() {
		List<Celestial> leaves = getLeaves();
		for (int i = 0; i < leaves.size(); i++) {
			for (int j = i + 1; j < leaves.size(); j++) {
				Celestial c1 = leaves.get(i);
				Celestial c2 = leaves.get(j);
				if (c1.getParent().equals(c2.getParent())) {
					if (Math.abs(c1.getDistance() - c2.getDistance()) <= EPSILON) return true;
				} else {
					int[] refP1 = getAbsolutePos(c1.getParent());
					int[] refP2 = getAbsolutePos(c2.getParent());
					double refDistance = Math.sqrt(
							Math.pow((double)refP1[0] - refP2[0], 2) +
									Math.pow((double)refP1[1] - refP2[1], 2)
					);
					if (c1.getDistance() + c2.getDistance() >= refDistance) return true;
				}
			}
		}
		return false;
	}

	private Celestial findCelestial(String uid) {
		if (star.getUid().equals(uid)) return star;
		List<Celestial> planets = star.getChildren();
		for (Celestial planet : planets) {
			if (planet.getUid().equals(uid)) {
				return planet;
			}
			List<Celestial> moons = planet.getChildren();
			for (Celestial moon : moons) {
				if (moon.getUid().equals(uid)) {
					return moon;
				}
			}
		}
		return null;
	}

	private int[] getAbsolutePos(Celestial celestial) {
		int[] pos = new int[2];
		if (celestial == null) {
			pos[0] = 0;
			pos[1] = 0;
		} else {
			while (celestial != null) {
				pos[0] += celestial.getPosX();
				pos[1] += celestial.getPosY();
				celestial = celestial.getParent();
			}
		}
		return pos;
	}

	private List<Celestial> getLeaves() {
		List<Celestial> leaves = new ArrayList<>();
		List<Celestial> planets = star.getChildren();
		for (Celestial planet : planets) {
			if (planet.getChildren().size() == 0) {
				leaves.add(planet);
			}
			List<Celestial> moons = planet.getChildren();
			for (Celestial moon : moons) {
				leaves.add(moon);
			}
		}
		leaves.add(star);
		return leaves;
	}

	@Override
	public String toString() {
		return "Sistema Stellare - " + name;
	}
}
