package it.reply.fantabosco;

import java.util.ArrayList;
import java.util.List;

import it.reply.fantabosco.model.Antenna;
import it.reply.fantabosco.model.Building;
import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;
import it.reply.fantabosco.solver.ISolver;
import it.reply.fantabosco.solver.SolverGiova;
import it.reply.fantabosco.utils.FileUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

	public static void main(String[] args) {
		challenge("data_scenarios_a_example.in");
		challenge("data_scenarios_b_mumbai.in");
//		challenge("data_scenarios_c_metropolis.in");
//		challenge("data_scenarios_d_polynesia.in");
//		challenge("data_scenarios_a_example.in");
//		challenge("data_scenarios_e_sanfrancisco.in");
//		challenge("data_scenarios_f_tokyo.in");
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
		ISolver solver = new SolverGiova(); //FIXME metti qui il tuo solver
		SolverOutput solverOutput = solver.solver(solverInput);
		
		// Validator
		
		// Serializer
		StringBuilder sb = new StringBuilder();
//		if(solverOutput.getData() != null) {
//			for(String s : solverOutput.getData()) {
//				sb.append(s);
//				sb.append("\n");
//			}
//		}

		// Writer
		log.info("Writing solution for \"{}\", with score: {}", dataset, solverOutput.getScore());
		FileUtils.writeFile(dataset, sb.toString(), solverOutput.getScore());
	}
}
