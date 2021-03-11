package it.reply.fantabosco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AntennaPosition {
	
	Antenna antenna;
	int x;
	int y;

}
