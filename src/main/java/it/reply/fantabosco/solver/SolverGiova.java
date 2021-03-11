package it.reply.fantabosco.solver;

import java.util.ArrayList;
import java.util.List;

import it.reply.fantabosco.model.AntennaPosition;
import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;

public class SolverGiova implements ISolver {

	@Override
	public SolverOutput solver(SolverInput solverInput) {
		SolverOutput out = new SolverOutput();
		List<AntennaPosition> antennaPositions = new ArrayList<>();
		out.setAntennaPositions(antennaPositions);
		
		
		
		
		
		return out;
	}
	
}
