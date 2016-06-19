package br.unb.cic.lp.gol;

//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import br.unb.cic.lp.gol.estrategias.Conway;
import br.unb.cic.lp.gol.estrategias.HighLife;
import br.unb.cic.lp.gol.estrategias.LiveFreeOrDie;

public class GameView extends JFrame{
	int delay = 400;   //tempo de renovação de geração
	int altura = 10, largura = 10;

	private static boolean active = true;
	
	public GameView() {
		initComponents();
	}

	
	private void initComponents() {

		 
		JToggleButton[][] grid = new JToggleButton[altura][largura];
		JButton pauseBtn = new JButton();
		JButton startBtn = new JButton();
		JButton randomBtn = new JButton();
		JButton restartBtn = new JButton();
		JComboBox<String> strategiaBtn = new JComboBox<>(); //TODO MODIFICAR ESTRATEGIAS
		
		startBtn.setEnabled(false); //para iniciar o jogo, deve-se primeiro escolher uma estrategia
		randomBtn.setEnabled(false);
		restartBtn.setEnabled(false);
		pauseBtn.setEnabled(false); 
		
		JLabel jLabel1 = new JLabel();
		JLabel jLabel2 = new JLabel();
		JLabel killedCells = new JLabel();
		JLabel revivedCells = new JLabel();

		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();
		
		Statistics statistics = new Statistics();
		
		Random rnd = new Random();
		
		GameEngine game = new GameEngine(altura,largura);
		
		
//		//INJECAO DE DEPENDECIA
//	    ApplicationContext context = new ClassPathXmlApplicationContext("Spring.xml");
//	    //EstrategiaDeDerivacao conway = (EstrategiaDeDerivacao) context.getBean("ConwayBean");
//	    //game.setEstrategia(conway);
//	    EstrategiaDeDerivacao ListaDeRegrasBean = (EstrategiaDeDerivacao) context.getBean("ListaDeRegrasBean");
//	    game.setEstrategia(ListaDeRegrasBean);
//	    
		Cell[][] cels = game.getCells();
		
		for(int i = 0 ;i<altura ;i++){           //criação do grid do jogo
			for(int j = 0 ;j<largura ;j++){
				grid[i][j] = new JToggleButton();

				grid[i][j].setBackground(Color.white);
				UIManager.put("ToggleButton.select", Color.RED);
				SwingUtilities.updateComponentTreeUI(grid[i][j]);
			}
		}
		
		ItemListener gridListener = new ItemListener(){
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(active){
						if(e.getStateChange()==ItemEvent.SELECTED){  
							statistics.recordRevive();
							revivedCells.setText(""+statistics.getRevivedCells());
			
						} else if(e.getStateChange()==ItemEvent.DESELECTED){
							
							statistics.recordKill();
							killedCells.setText(""+statistics.getKilledCells());
							
						}
							for(int i = 0 ;i<altura ;i++){ 		//copia o grid de JToggleBtn para a matriz cels
			        			for(int j = 0 ;j<largura ;j++){
			        				if(grid[i][j].isSelected())
			        					cels[i][j].revive();
			        				else
			        					cels[i][j].kill();
			        			}
							}
					}
				}
		};
		
		
		ActionListener restartListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for(int i = 0 ;i<altura ;i++){
        			for(int j = 0 ;j<largura ;j++){
        				grid[i][j].setSelected(false);
        			}
        		}
                statistics.restart();
                revivedCells.setText(""+statistics.getRevivedCells());
                killedCells.setText(""+statistics.getKilledCells());
            }
        };
        
        ActionListener randomListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	
                for(int i = 0 ;i<altura ;i++){
        			for(int j = 0 ;j<largura ;j++){
        				grid[i][j].setSelected(rnd.nextBoolean());
        			}
        		}
                
                statistics.restart();
                revivedCells.setText(""+statistics.getRevivedCells());
                killedCells.setText(""+statistics.getKilledCells());
                startBtn.setEnabled(true);
                restartBtn.setEnabled(true);
            }
        };
        
        
        ActionListener strategiaListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setTitle("GAME OF LIFE -" + strategiaBtn.getSelectedItem());
				
				if(strategiaBtn.getSelectedItem() == "Conway")
					game.setEstrategia(new Conway());
				if(strategiaBtn.getSelectedItem() == "Highlife")
					game.setEstrategia(new HighLife());
				if(strategiaBtn.getSelectedItem() == "LiveFreeOrDie")
					game.setEstrategia(new LiveFreeOrDie());
				
				startBtn.setEnabled(true);
				randomBtn.setEnabled(true);
				restartBtn.setEnabled(false);
			}
        	
        };

        
        final Timer timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//mudar cels para proxima geracao
				game.nextGeneration();
				
				//igualar a grid de JToggleBtn com a grid de cel
				
				active = false;  //Impede que o GridListener mude o mapa grid[][] antes que a proxima geração seja
								//de fato processada
				
				for(int i = 0 ;i<altura ;i++){        
					for(int j = 0 ;j<largura ;j++){
						if(cels[i][j].isAlive()){
							grid[i][j].setSelected(true);
							statistics.recordRevive();
							revivedCells.setText(""+statistics.getRevivedCells());
						}
						else{
							grid[i][j].setSelected(false);
							statistics.recordKill();
							killedCells.setText(""+statistics.getKilledCells());
						}
    				}
    			}
				active = true;
			}
        	
        });
        
        ActionListener startListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	
            	timer.start();
            	pauseBtn.setEnabled(true); 
				startBtn.setEnabled(false);
            	
            }
        };
        
        ActionListener pauseListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
			
				pauseBtn.setEnabled(false); 
				startBtn.setEnabled(true);
				restartBtn.setEnabled(true);
				timer.stop();
				
			}
        };
        
        
		startBtn.setText("Start");
		startBtn.addActionListener(startListener);
		
		pauseBtn.setText("Pause");
		pauseBtn.addActionListener(pauseListener);
		
		randomBtn.setText("Random");
		randomBtn.addActionListener(randomListener);
		
		restartBtn.setText("Restart");
		restartBtn.addActionListener(restartListener);
		
		strategiaBtn.addActionListener(strategiaListener);
	
		for(int x = 0 ;x<altura ;x++)
			for(int y = 0 ;y<largura ;y++){
				jPanel4.add(grid[x][y]);
				grid[x][y].addItemListener(gridListener);
				}
			
		
		
		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		jPanel2.setBorder(BorderFactory.createTitledBorder(null,
				"Menu", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 12)));


		jPanel3.setBorder(BorderFactory.createTitledBorder(null,
				"Estatísticas", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Tahoma", 1, 12)));

		jLabel1.setText("Células revividas:");

		jLabel2.setText("Células Mortas:");

		revivedCells.setText(""+statistics.getRevivedCells());

		killedCells.setText(""+statistics.getKilledCells());
		
		strategiaBtn.setModel(new DefaultComboBoxModel<>(new String[]{ "Conway", "Highlife", "LiveFreeOrDie" }));


		
		//Feito pelo NetBeans Gui Builder
		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()

						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jLabel1)
								.addComponent(jLabel2))
						.addGap(18, 18, 18)

						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(killedCells)
								.addComponent(revivedCells))
						.addGap(0, 0, Short.MAX_VALUE))
				);
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addContainerGap()

						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel1)
								.addComponent(revivedCells))
						.addGap(42, 42, 42)

						.addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabel2)
								.addComponent(killedCells))
						.addContainerGap(48, Short.MAX_VALUE))
				);

		GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(pauseBtn)
								.addComponent(restartBtn)
								.addComponent(randomBtn)
								.addComponent(strategiaBtn, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startBtn, GroupLayout.PREFERRED_SIZE, 252,
										GroupLayout.PREFERRED_SIZE))
						.addGap(0, 55, Short.MAX_VALUE))
				);

		jPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[]
				{pauseBtn, startBtn, strategiaBtn, restartBtn, randomBtn});

		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(startBtn)
						.addGap(26, 26, 26)
						.addComponent(pauseBtn)
						.addGap(26, 26, 26)
						.addComponent(randomBtn)
						.addGap(26, 26, 26)
						.addComponent(restartBtn)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(strategiaBtn, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(60, 60, 60)
						.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(26, 26, 26)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		jPanel4.setBorder(BorderFactory.createTitledBorder(null, "Grid",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new
				Font("Tahoma", 1, 12))); 
		jPanel4.setLayout(new GridLayout(altura, largura));

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 683, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap()

						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(jPanel4, GroupLayout.PREFERRED_SIZE, 552,GroupLayout.PREFERRED_SIZE)
								.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
				);

		pack();
	}

}