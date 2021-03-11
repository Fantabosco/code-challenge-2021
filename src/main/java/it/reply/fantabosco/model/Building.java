package it.reply.fantabosco.model;

import lombok.Data;

@Data
public class Building {

	int id;
	int x;
	int y;
	int latencyWeight;
	int connectionSpeedWeight;
	
}
