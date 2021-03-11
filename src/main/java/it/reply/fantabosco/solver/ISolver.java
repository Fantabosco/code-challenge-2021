package it.reply.fantabosco.solver;

import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;

public interface ISolver {
	
	public SolverOutput solver(SolverInput solverInput);

}
