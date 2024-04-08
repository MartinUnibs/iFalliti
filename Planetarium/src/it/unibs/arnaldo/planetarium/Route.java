package it.unibs.arnaldo.planetarium;

import java.util.Collections;
import java.util.List;

public class Route {
	
	private List<Celestial> lane;
	private double travelDistance;
	
	public Route(StarSystem starSystem, Celestial c1, Celestial c2) {
		List<Celestial> p1 = starSystem.getPath(c1);
		List<Celestial> p2 = starSystem.getPath(c2);
		int end = Math.min(p1.size(), p2.size());
		int index;
		for (index = 0; index < end; index++) {
			if (!p1.get(index).equals(p2.get(index))) break;
		}
		index--;
		Celestial lca = p1.get(index);
		Collections.reverse(p1);
		List<Celestial> lane = p1.subList(0, p1.size() - index);
		lane.addAll(p2.subList(index + 1, p2.size()));
		this.lane = lane;
		double travelDistance = 0.00;
		for (Celestial c : lane) {
			if (!c.equals(lca)) travelDistance += c.getDistance();
		}
		this.travelDistance = travelDistance;
	}

	public List<Celestial> getLane() {
		return lane;
	}
	
	public double getTravelDistance() {
		return travelDistance;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Celestial c : lane) {
			builder.append(c.getUid());
			builder.append(" > ");
		}
		builder.delete(builder.length() - 3, builder.length());
		return builder.toString();
	}
	
}
