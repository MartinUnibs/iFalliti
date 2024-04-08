package it.unibs.arnaldo.planetarium;

import java.util.ArrayList;
import java.util.List;

import it.kibo.fp.lib.RandomDraws;

public class Celestial {
	
	private String uid;
	private int mass;
	private int posX;
	private int posY;
	private double distance;
	private Celestial parent;
	private List<Celestial> children;

	public Celestial(String starSystemName, int mass, int posX, int posY, Celestial parent) {
		this.uid = createUid(starSystemName, mass, posX, posY, parent);
		this.mass = mass;
		this.posX = posX;
		this.posY = posY;
		this.distance = Math.sqrt(Math.pow((double)posX, 2) + Math.pow((double)posY, 2));
		this.parent = parent;
		this.children = new ArrayList<>();
	}
	
	public String getUid() {
		return uid;
	}

	public int getMass() {
		return mass;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public Celestial getParent() {
		return parent;
	}
	
	public List<Celestial> getChildren() {
		return children;
	}

	public void addChild(Celestial child) {
		children.add(child);
	}
	
	public void removeChild(Celestial child) {
		children.remove(child);
	}
	
	private String createUid(String name, int mass, int posX, int posY, Celestial reference) {
		StringBuilder uid = new StringBuilder();
		// SECTION 1: header
		for (String s : name.split(" ")) {
			uid.append(s.toUpperCase().charAt(0));
		}
		if (reference == null) {
			// STAR --> nothing to add
		} else {
			if (reference.getParent() == null) {
				// PLANET
				uid.append('P');
				uid.append(reference.getChildren().size() + 1);
			} else {
				// MOON
				uid.append('P');
				uid.append(reference.getParent().getChildren().indexOf(reference) + 1);
				uid.append('L');
				uid.append(reference.getChildren().size() + 1);
			}
		}
		uid.append('-');
		// SECTION 2: measures
		int[] measures = { mass, posX, posY };
		String strMeasures = getStringMeasures(measures);
		uid.append(strMeasures);
		uid.append('-');
		// SECTION 3: random
		int rand = RandomDraws.drawInteger(1, 99);
		uid.append(String.format("%02d", rand));
		return uid.toString();
	}
	
	private String getStringMeasures(int[] measures) {
		StringBuilder builder = new StringBuilder();
		for (int measure : measures) {
			String prefix = measure >= 0 ? "" : "M";
			builder.append(prefix);
			builder.append(Math.abs(measure));
		}
		return builder.toString();
	}
	
	@Override
	public String toString() {
		return "Corpo Celeste - " + uid;
	}
	
	public boolean equals(Celestial c) {
		if (c == null) return false;
		return this.uid.equals(c.getUid());
	}
}
