package br.unb.cic.lp.gol;

import br.unb.cic.lp.gol.GameEngine;

/**
 * Interface com a declaracao dos metodos  que apoiam o 
 * calculo de uma proxima derivacao no GameOfLife. 
 * 
 * Definicoes: 
 * 
 * Uma interface (historicamente) nao contem implementacao, 
 * serve <b>apenas</b> para definir um novo tipo. O mecanismo 
 * de interfaces em Java (ou classes 100% abstratas em C++) 
 * permite um tipo especifico de heranca, conhecido como 
 * <b>heranca de tipo</b>. Nesse caso, nao ocorre heranca 
 * de comportamento- uma vez que nenhum comportamento 
 * eh implementado. 
 *  
 * @author rbonifacio
 *
 */
public interface EstrategiaDeDerivacao {

	/** Retorna o nome da regra de derivacao */	
	public String getName();
	
	/**
	 * Verifica se uma celula (na posica (i, j)) deve 
	 * ser mantida viva. 
	 * 
	 * @param i posicao da celula na ith linha
	 * @param j posicao da celula na jth coluna 
	 * 
	 * @param engine referencia para a Game Engine
	 * 
	 * @return <i>true</i> caso a celula deva ser mantida viva. <i>false</i> 
	 * caso contrario. 
	 */
	public boolean shouldKeepAlive(int i, int j, GameEngine engine);
	
	/**
	 * Verifica se uma celula (na posica (i, j)) deve 
	 * se tornar uma celula viva. 
	 * 
	 * @param i posicao da celula na ith linha
	 * @param j posicao da celula na jth coluna 
	 * 
	 * @param engine referencia para a Game Engine
	 * 
	 * @return <i>true</i> caso a celula deva se tornar viva. <i>false</i> 
	 * caso contrario. 
	 */
	public boolean shouldRevive(int i, int j, GameEngine engine);
}
