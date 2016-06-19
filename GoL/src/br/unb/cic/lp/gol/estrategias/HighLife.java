package br.unb.cic.lp.gol.estrategias;

import br.unb.cic.lp.gol.GameEngine;

/**
 * Implementacao de uma estrategia de derivacao baseda nas 
 * regras HighLife. Note que essa estrategia reusa o comportamento 
 * definido nas regras do Conway mas sobrescreve (overrides) a 
 * implementacao do metodo shouldRevive. 
 * 
 * @author rbonifacio
 */
public class HighLife extends Conway {	
	@Override
	public String getName() {
		return "HighLife"; 
	}
	
	@Override
	public boolean shouldRevive(int i, int j, GameEngine engine) {
		return !engine.isCellAlive(i, j) &&
				(engine.numberOfNeighborhoodAliveCells(i, j) == 3 ||
				 engine.numberOfNeighborhoodAliveCells(i, j) == 6);
	}

	
}
