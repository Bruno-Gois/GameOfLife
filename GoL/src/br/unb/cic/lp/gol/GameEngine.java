package br.unb.cic.lp.gol;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JToggleButton;

public class GameEngine {

	private int height;
	private int width;
	private Cell[][] cells;
	private Statistics statistics = new Statistics();
	
	private EstrategiaDeDerivacao estrategia;
	
	
	//Construtor
	public GameEngine(int height, int width) { 
		this.height = height;
		this.width = width;

		cells = new Cell[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				cells[i][j] = new Cell();
			}
		}
	}
	
	public Cell[][] getCells(){
		return cells;
	}
	
	public void setEstrategia(EstrategiaDeDerivacao e) {
		estrategia = e;
	}
	public EstrategiaDeDerivacao getEstrategia() {
		return estrategia;
	}
	
	public void nextGeneration() {
	
		List<Cell> mustRevive = new ArrayList<Cell>();
		List<Cell> mustKill = new ArrayList<Cell>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (estrategia.shouldRevive(i, j, this)) {
					mustRevive.add(cells[i][j]);
				} 
				else if ((!estrategia.shouldKeepAlive(i, j, this)) && cells[i][j].isAlive()) {
					mustKill.add(cells[i][j]);
				}
			}
		}
		for (Cell cells : mustRevive) 
			cells.revive();
		for (Cell cells : mustKill) 
			cells.kill();
		
	}
	
	
	public int numberOfNeighborhoodAliveCells(int i, int j) {
		int alive = 0;
		for (int a = i - 1; a <= i + 1; a++) {
			for (int b = j - 1; b <= j + 1; b++) {
				if (validPosition(a, b)  && (!(a==i && b == j)) && cells[a][b].isAlive()) 
					alive++;
				else{
					//Implementação do Torus-Noção de mapa infinito
					int ax= ((a%height) + height) % height;
					int by = ((b%width) + width)% width;
					
					if(validPosition(ax, by) && (!(ax==i && by == j))&& cells[ax][by].isAlive())
						alive++;
					}
				
			}
		}
		return alive;
	}
	
	private boolean validPosition(int a, int b) {
		return a >= 0 && a < height && b >= 0 && b < width;
	}
	
	public boolean isCellAlive(int i, int j) throws InvalidParameterException {
		if(validPosition(i, j)) {
			return cells[i][j].isAlive();
		}
		else {
			throw new InvalidParameterException("Invalid position (" + i + ", " + j + ")" );
		}
	}
	

	
	

}
