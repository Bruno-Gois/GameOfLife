package br.unb.cic.lp.gol;

import java.awt.event.ActionListener;

public class Cell {
    private boolean alive;

    public Cell() {
	alive = false;
    }

    public Cell(boolean alive) {
	this.alive = alive; 
    }

    public boolean isAlive() {
	return alive;
    }

    public void kill() {
	this.alive = false;
    }
	
    public void revive() {
	this.alive = true;
    }

}
