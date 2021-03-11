package it.reply.fantabosco.solver;

import java.util.ArrayList;
import java.util.List;

import it.reply.fantabosco.model.AntennaPosition;
import it.reply.fantabosco.model.Building;
import it.reply.fantabosco.model.SolverInput;
import it.reply.fantabosco.model.SolverOutput;

public interface ISolver {
	
	public SolverOutput solver(SolverInput solverInput);
	
	default int getBuildingScore(Building b, AntennaPosition a) {
		/*
			s(a, b) = BC[b] × AC[a] − BL[b] × dist(b, a)
			dove: 
				s(a, b) = building score
				BC[b] = connection speed b
				AC[a] = connection speed a
				BL[b] = latency weight b
				dist(b, a) = manhattan distance between the two coordinate
		*/
		return b.getConnectionSpeedWeight() * a.getAntenna().getConnectionSpeed()
				- b.getLatencyWeight() * getManhattanDistance(a, b);
	}
	
	default int getBuildingScore(Building b, List<AntennaPosition> antennas) {
		// 	A building b will connect to the antenna that maximize the score s(a, b):
		int maxScore = 0;
		List<AntennaPosition> reachables = getReachableAntennas(b, antennas);
		for(AntennaPosition a : reachables) {
			int tmpScore = getBuildingScore(b, a);
			if(tmpScore > maxScore) {
				maxScore = tmpScore;
			}
		}
		return maxScore;
	}
	
	default List<AntennaPosition> getReachableAntennas(Building b, List<AntennaPosition> antennas) {
		List<AntennaPosition> reachables = new ArrayList<>();
		for(AntennaPosition a: antennas) {
			if(getManhattanDistance(a, b) > a.getAntenna().getRange()) {
				reachables.add(a);
			}
		}
		return reachables;
	}
	
	default int getManhattanDistance(AntennaPosition a, Building b) {
		return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
	}

	default boolean isRewardAssigned(List<Building> bs, List<AntennaPosition> aps) {
		for(Building b : bs) {
			int score = getBuildingScore(b, aps);
			if(score == 0) {
				return false;
			}
		}
		return true;
	}
	
	default int getTotalScore(SolverInput solverInput, SolverOutput solverOutput) {
		int totalScore = 0;
		for(Building b : solverInput.getBuildings()) {
			totalScore += getBuildingScore(b, solverOutput.getAntennaPositions());
		}
		if(isRewardAssigned(solverInput.getBuildings(), solverOutput.getAntennaPositions())) {
			totalScore += solverInput.getReward();
		}
		return totalScore;
	}
}
