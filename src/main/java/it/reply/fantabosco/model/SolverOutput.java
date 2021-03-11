package it.reply.fantabosco.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SolverOutput {
	
	// Punteggio associato alla soluzione, per valutare quanto è buona
	int score;
	
	List<BuilidingScore> buildingScores;
	int reward;
	
	// Dati della soluzione
	List<AntennaPosition> antennaPositions = new ArrayList<>();

}
