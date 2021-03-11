package it.reply.fantabosco.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.reply.fantabosco.model.Antenna;
import it.reply.fantabosco.model.AntennaPosition;
import it.reply.fantabosco.model.Building;
import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SolverGiova implements ISolver {

	@Override
	public SolverOutput solver(SolverInput in) {
		SolverOutput out = new SolverOutput();
		List<AntennaPosition> antennaPositions = new ArrayList<>();
		
		// Ordinamento iniziale di building e antenne per connection speed
		Collections.sort(in.getAntennas(), (Antenna a1, Antenna a2) -> a2.getConnectionSpeed() - a1.getConnectionSpeed());
		Collections.sort(in.getBuildings(), (Building a1, Building a2) -> a2.getConnectionSpeedWeight() - a1.getConnectionSpeedWeight());
		List<Antenna> freeA = new ArrayList<>(in.getAntennas());
		List<Building> okB = new ArrayList<>(in.getBuildings());

		// Per cominciare, posiziono l'antenna più potente sull'edificio più potente
		AntennaPosition ap0 = new AntennaPosition();
		ap0.setAntenna(freeA.remove(0));
		ap0.setX(in.getBuildings().get(0).getX());
		ap0.setY(in.getBuildings().get(0).getY());
		antennaPositions.add(ap0);
		
		while(!freeA.isEmpty() && !okB.isEmpty()) {
			// Identifico l'edificio più promettente (già coperto o no)
			Building b = getPromisingBuilding(okB, antennaPositions);
			
			// Identifico l'antenna libera più promettente
			Antenna a = getPromisingAntenna(freeA, in, out);
			
			// Identifico la posizione
			AntennaPosition ap = getAccettablePosition(a, b, in, antennaPositions);
			if(ap != null) {
				antennaPositions.add(ap);
				freeA.remove(ap.getAntenna());
			} else {
				// Sposta l'edificio in coda 
				okB.remove(b);
				okB.add(b);
			}
		}
		out.setAntennaPositions(antennaPositions);
		return out;
	}

	private Antenna getPromisingAntenna(List<Antenna> freeA, SolverInput in, SolverOutput out) {
		//TODO
		return freeA.get(0);
	}

	private Building getPromisingBuilding(List<Building> buildings, List<AntennaPosition> antennaPositions) {
		
		/* 
		 * Minimizza il prodotto delle connection speed
		 * Ordina i building per connection speed, le antenne per connection speed, e matchali
		 */
		
		
		/*
		 * Minimizza la latenza
		 * Ordina i building per latenza descrescente, e assegnagli le antenne vicine 
		 */
		
		/*
		 * Massimizza il reward
		 * Ordina i building per quanto sono isolati, e assegnagli antenne vicine
		 */
		// Massimizzo la distanza da altre antenne
		if(antennaPositions.isEmpty()) {
			return buildings.get(0);
		}
		
		// Cerca l'edificio con distanza media più lontana dalle antenne
		Building bestB = null;
		int distMax = -99999999;
		int i=0;
		for(Building b : buildings) {
			int distMedia = 0;
			for(AntennaPosition ap : antennaPositions) {
				distMedia += this.getManhattanDistance(ap, b) - ap.getAntenna().getRange();
			}
			distMedia /= antennaPositions.size();
			if(distMedia > distMax) {
				distMax = distMedia;
				bestB = b;
			}
			// Considera solo fino a metà degli edifici
			if(i/buildings.size() > 0.5) {
				break;
			}
		}
		return bestB;
	}
	
	
	private AntennaPosition getAccettablePosition(Antenna a, Building b, SolverInput in, List<AntennaPosition> antennaPositions) {
		AntennaPosition ap = new AntennaPosition();
		ap.setAntenna(a);
		boolean flagFirst = true;
		int x = b.getX();
		int y = b.getY();

		// Finchè non trovo un buco, gira attorno a b
		int counter = 10;
		while(isOccupied(x, y, antennaPositions) || isOutOfMap(x, y, in)) {
			if(counter > 0) {
				counter--;
			} else {
//				log.warn("Failed positioning");
				return null;
			}
			if(flagFirst) {
				flagFirst = false;
				x++;
			} else {
				if(Math.abs(x - b.getX()) > Math.abs(y - b.getY())) {
					if(x > b.getX()) {
						y--;
					} else {
						y++;
					}
				} else {
					if(y > b.getY()) {
						x++;
					} else {
						x--;
					}	
				}
			}
		}
		ap.setX(x);
		ap.setY(y);
		return ap;
	}

	private boolean isOutOfMap(int x, int y, SolverInput in) {
		return x<0 || x>=in.getW() || y<0 || y>=in.getH();
	}

	private boolean isOccupied(int x, int y, List<AntennaPosition> antennaPositions) {
		for(AntennaPosition ap: antennaPositions) {
			if(ap.getX() == x && ap.getY() == y) {
				return true;
			}
		}
		return false;
	}	
}
