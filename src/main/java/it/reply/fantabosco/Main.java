package it.reply.fantabosco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.reply.fantabosco.model.Antenna;
import it.reply.fantabosco.model.AntennaPosition;
import it.reply.fantabosco.model.Building;
import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;
import it.reply.fantabosco.solver.ISolver;
import it.reply.fantabosco.solver.SolverGiova;
import it.reply.fantabosco.utils.FileUtils;
import it.reply.fantabosco.utils.ValidationException;

public class Main {

	public static void main(String[] args) {
		challenge("data_scenarios_a_example.in");
		challenge("data_scenarios_b_mumbai.in");
		challenge("data_scenarios_c_metropolis.in");
		challenge("data_scenarios_d_polynesia.in");
		challenge("data_scenarios_e_sanfrancisco.in");
		challenge("data_scenarios_f_tokyo.in");
	}
	
	public static void challenge(String dataset) {

		// Reader
		List<String> file = FileUtils.readFile("in/" + dataset);
		
		// Parser
		SolverInput solverInput = new SolverInput();
		if(!file.isEmpty()) {
			// Parse header
			int w = Integer.parseInt(file.get(0).split(" ")[0]);
			int h = Integer.parseInt(file.get(0).split(" ")[1]);
			int n = Integer.parseInt(file.get(1).split(" ")[0]);
			int m = Integer.parseInt(file.get(1).split(" ")[1]);
			int rw = Integer.parseInt(file.get(1).split(" ")[2]);
			solverInput.setW(w);
			solverInput.setH(h);
			solverInput.setReward(rw);
			solverInput.setBuildings(new ArrayList<>(n));
			solverInput.setAntennas(new ArrayList<>(m));
			int i = 2;
			// Parse buildings
			for(int j=0; j<n; j++) {
				String[] line = file.get(j + i).split(" ");
				int x = Integer.parseInt(line[0]);
				int y = Integer.parseInt(line[1]);
				int l = Integer.parseInt(line[2]);
				int c = Integer.parseInt(line[3]);
				Building b = new Building();
				b.setId(j);
				b.setX(x);
				b.setY(y);
				b.setConnectionSpeedWeight(c);
				b.setLatencyWeight(l);
				solverInput.getBuildings().add(b);
			}
			// Parse antennas
			i += n;
			for(int j=0; j<m; j++) {
				String[] line = file.get(j + i).split(" ");
				int r = Integer.parseInt(line[0]);
				int c = Integer.parseInt(line[1]);
				Antenna a = new Antenna();
				a.setId(j);
				a.setRange(r);
				a.setConnectionSpeed(c);
				solverInput.getAntennas().add(a);
			}
		}
				
		// Solver
		ISolver solver = new SolverGiova();
		SolverOutput solverOutput = solver.solver(solverInput);
		
		// Validator
		validate(solverInput, solverOutput);
		
		// Scoring
		scoring(solverInput, solverOutput);
		
		// Serializer
		StringBuilder sb = new StringBuilder();
		sb.append(solverOutput.getAntennaPositions().size());
		sb.append("\n");
		for(AntennaPosition s : solverOutput.getAntennaPositions()) {
			sb.append(s.getAntenna().getId());
			sb.append(" ");
			sb.append(s.getX());
			sb.append(" ");
			sb.append(s.getY());
			sb.append("\n");
		}

		// Writer
		FileUtils.writeFile(dataset, sb.toString(), solverOutput.getScore());
	}

	private static void scoring(SolverInput solverInput, SolverOutput solverOutput) {
		int score = (new ISolver() {
			public int scoreOutput() {
				return this.getTotalScore(solverInput, solverOutput);
			}
			
			@Override
			public SolverOutput solver(SolverInput solverInput) {
				return null;
			}
		}).scoreOutput();
		solverOutput.setScore(score);
	}

	private static void validate(SolverInput solverInput, SolverOutput solverOutput) {
		if(solverOutput.getAntennaPositions() == null || solverOutput.getAntennaPositions().isEmpty()) {
			throw new ValidationException("Nessuna antenna posizionata");
		}
		if(solverOutput.getAntennaPositions().size() > solverInput.getAntennas().size()) {
			throw new ValidationException("Posizionate pi?? antenne di quelle esistenti");
		}
		Set<Integer> ids = new HashSet<>();
		for(AntennaPosition a : solverOutput.getAntennaPositions()) {
			ids.add(a.getAntenna().getId());
		}
		if(ids.size() < solverOutput.getAntennaPositions().size()) {
			throw new ValidationException("Posizionata pi?? volte la stessa antenna");
		}
		
		for(int i = 0; i<solverOutput.getAntennaPositions().size(); i++) {
			AntennaPosition a1 = solverOutput.getAntennaPositions().get(i);
			for(int j = i+1; i<solverOutput.getAntennaPositions().size(); i++) {
				AntennaPosition a2 = solverOutput.getAntennaPositions().get(j);
				if(a1.getX() == a2.getX() && a1.getY() == a2.getY()) {
					throw new ValidationException("Posizionate pi?? antenne nella stessa cella");
				}
			}
			if(a1.getX() < 0 || a1.getX() > solverInput.getW()) {
				throw new ValidationException("Antenna posizionata in X non valida: " + a1);
			}
			if(a1.getY() < 0 || a1.getY() > solverInput.getH()) {
				throw new ValidationException("Antenna posizionata in Y non valida: " + a1);
			}
		}
	}
}
