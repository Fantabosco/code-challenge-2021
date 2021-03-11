package it.reply.fantabosco.solver;

import java.util.ArrayList;
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
		
		List<Antenna> freeA = new ArrayList<>(in.getAntennas());
		while(!freeA.isEmpty()) {
			// Identifico l'edificio più promettente (già coperto o no)
			Building b = getPromisingBuilding(in);
			
			// Identifico l'antenna libera più promettente
			Antenna a = getPromisingAntenna(freeA, in, out);
			
			// Identifico la posizione
			AntennaPosition ap = getAccettablePosition(a, b, in, antennaPositions);
			if(ap != null) {
				antennaPositions.add(ap);
			}
			// Rimuovo l'antenna fra quelle disponibili (anche se non l'ho posizionata TODO migliorabile)
			freeA.remove(a);
		}
		out.setAntennaPositions(antennaPositions);
		return out;
	}

	private Antenna getPromisingAntenna(List<Antenna> freeA, SolverInput in, SolverOutput out) {
		//TODO
		return freeA.get(0);
	}

	private Building getPromisingBuilding(SolverInput in) {

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
		//TODO
		return in.getBuildings().get(0);
	}
	
	
	private AntennaPosition getAccettablePosition(Antenna a, Building b, SolverInput in, List<AntennaPosition> antennaPositions) {
		AntennaPosition ap = new AntennaPosition();
		ap.setAntenna(a);
		boolean flagFirst = true;
		int x = b.getX();
		int y = b.getY();

		// Finchè non trovo un buco, gira attorno a b
		int counter = 100;
		while(isOccupied(x, y, antennaPositions) || isOutOfMap(x, y, in)) {
			if(counter>0) {
				counter--;
			} else {
				log.warn("Failed positioning");
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
		return x<0 || x>in.getW() || y<0 || y>in.getH();
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
