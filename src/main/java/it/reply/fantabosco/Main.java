package it.reply.fantabosco;

import java.util.List;

import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;
import it.reply.fantabosco.solver.ISolver;
import it.reply.fantabosco.solver.SolverGiova;
import it.reply.fantabosco.utils.FileUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Main {

	public static void main(String[] args) {
		//TODO inserire qui tutti i file di input da risolvere
		challenge("a.txt");
	}
	
	public static void challenge(String dataset) {

		// Reader
		List<String> file = FileUtils.readFile("in/" + dataset);
		
		// Parser
		//TODO popola SolverInput con i dati di input
		SolverInput solverInput = new SolverInput();
		if(!file.isEmpty()) {
			int w = Integer.valueOf(file.get(0).split(" ")[0]);
			int h = Integer.valueOf(file.get(0).split(" ")[1]);
			for(int H=1; H<=h; H++) {
				String line = file.get(H);
				for(int W=0; W<w; W++) {
	
				}
			}
		}
				
		// Solver
		ISolver solver = new SolverGiova(); //FIXME metti qui il tuo solver
		SolverOutput solverOutput = solver.solver(solverInput);
		
		// Validator
		
		// Serializer
		StringBuilder sb = new StringBuilder();
		if(solverOutput.getData() != null) {
			for(String s : solverOutput.getData()) {
				sb.append(s);
				sb.append("\n");
			}
		}

		// Writer
		log.info("Writing solution for \"{}\", with score: {}", dataset, solverOutput.getScore());
		FileUtils.writeFile(dataset, sb.toString(), solverOutput.getScore());
	}
}
