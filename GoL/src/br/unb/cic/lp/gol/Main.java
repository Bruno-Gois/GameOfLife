package br.unb.cic.lp.gol;

public class Main {
	public static void main(String args[]) {
		
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameView().setVisible(true);
                
            }
        });
    }
}