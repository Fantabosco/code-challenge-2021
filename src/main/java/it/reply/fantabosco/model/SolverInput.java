package it.reply.fantabosco.model;

import java.util.List;

import lombok.Data;

@Data
public class SolverInput {
	
	// the reward assigned if all the buildings are connected to the network
	int reward;
	
	List<Antenna> antennas;
	List<Building> buildings;

}
