package it.reply.fantabosco.model;

import java.util.List;

import lombok.Data;

@Data
public class SolverOutput {
	
	// Punteggio associato alla soluzione, per valutare quanto è buona
	int score;
	
	// Dati della soluzione
	List<String> data;

}
