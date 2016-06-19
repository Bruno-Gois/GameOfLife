package br.unb.cic.lp.gol;

public class Statistics {
	private int revivedCells;
	private int killedCells; //BUG: quando o grid � construido, conta para a estat�stica 100 celulas mortas
	
	public Statistics() {
		revivedCells = 0;
		killedCells = 0;
	}

	public int getRevivedCells() {
		return revivedCells;
	}

	public void recordRevive() {
		this.revivedCells++;
	}

	public int getKilledCells() {
		return killedCells;
	}

	public void recordKill() {
		this.killedCells++;
	}
	public void restart(){
		this.killedCells = 0;
		this.revivedCells = 0;
	}
}
