package br.unb.cic.lp.gol.estrategias;

import br.unb.cic.lp.gol.EstrategiaDeDerivacao;
import br.unb.cic.lp.gol.GameEngine;

/**
 * Implementacao de uma estrategia de derivacao 
 * baseada nas regras do Conway. 
 * 
 * @author rbonifacio
 */
public class Conway implements EstrategiaDeDerivacao {

	@Override
	public boolean shouldKeepAlive(int i, int j, GameEngine engine) {
		return engine.isCellAlive(i, j) && 
				engine.numberOfNeighborhoodAliveCells(i, j) == 2 || 
				engine.numberOfNeighborhoodAliveCells(i, j) == 3;
	}

	@Override
	public boolean shouldRevive(int i, int j, GameEngine engine) {
		return !engine.isCellAlive(i, j) &&
				engine.numberOfNeighborhoodAliveCells(i, j) == 3; 
	}

	@Override
	public String getName() {
		return "Conway";
	}

}
