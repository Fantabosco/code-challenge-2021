package it.reply.fantabosco.model;

import java.util.List;

import lombok.Data;

@Data
public class SolverInput {
	
	// the reward assigned if all the buildings are connected to the network
	int reward;
	
	int w; // width of the grid
	int h; // height of the grid
	
	List<Antenna> antennas;
	List<Building> buildings;

}
