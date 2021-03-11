package it.reply.fantabosco.solver;

import java.util.Comparator;
import java.util.List;

import it.reply.fantabosco.model.Antenna;
import it.reply.fantabosco.model.AntennaPosition;
import it.reply.fantabosco.model.Building;
import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;

public class SolverPelle implements ISolver {

	@Override
	public SolverOutput solver(SolverInput solverInput) {
		int numberOfAntennas = solverInput.getAntennas().size();
		
		int numberOfBuildings = solverInput.getBuildings().size();
		
		int buildingInAntenna = numberOfBuildings / numberOfAntennas;
		
		
		
		SolverOutput output = new SolverOutput();
		
		List<Building> buildings = solverInput.getBuildings();
		buildings.sort(new Comparator<Building>() {
			// Compare desc
			@Override
			public int compare(Building a, Building b) {
				int a_score = a.getConnectionSpeedWeight() - a.getLatencyWeight();
				int b_score = b.getConnectionSpeedWeight() - b.getLatencyWeight();
				return b_score - b_score;
			}
		});
		List<Antenna> antennas = solverInput.getAntennas();
		for(int i = 0; i < antennas.size(); i++) {
			Building b = buildings.get(i);
			Antenna a = antennas.get(i);
			output.getAntennaPositions().add(new AntennaPosition(a.getId(), b.getX(), b.getY()));
		}
		
		
		return output;
	}
	
	private int distance(Building a, Building b) {
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()); 
	}
	

}
