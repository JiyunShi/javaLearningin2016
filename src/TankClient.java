import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class TankClient extends JFrame{
	private static final long serialVersionUID = 1L;
	public static final int WINDOW_WIDTH=800;
	public static final int WINDOW_HEIGHT=600;
	private static final int ENEMYQTY=5;
	private TankPanel tp = new TankPanel();
	public Vector<RobotTank> enemys = new Vector<RobotTank>();
	public Vector<Steel> steels = new Vector<Steel>();
	private Tank myTank = new Tank(400,500,2,this.enemys);
	private Vector<Boom> booms = new Vector<Boom>(); 
	private TankSound boom = new TankSound("audio/blast.wav");
	private TankSound start =new TankSound("audio/start.wav");
	
	
	public TankClient()
	{	
		this.createEnemy();
		this.lauchFrame();
		this.initialBoom();
		new Thread(new RobotThread()).start();
		new Thread(new PaintThread()).start();
		start.run();
		
	}
	
	public void createEnemy(){
		
		for(int i=0;i<ENEMYQTY;i++)
		{
			enemys.add(new RobotTank(this.enemys));
		}
		
	}
	
	public void initialBoom(){
		booms.add(new Boom(-1000,-1000));
	}
	
	
	
	public void lauchFrame(){
		this.setLocation(400, 300);
		this.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		this.setTitle("Tank War");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.getContentPane().setBackground(Color.BLACK);
		this.add(tp,BorderLayout.CENTER);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
		
	}
	
	
	private class TankPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//Image offScreenImage = null;
		
		public TankPanel()
		{
			
			this.setVisible(true);
			this.setBackground(Color.BLACK);
				
		}
		
		public void paintComponent(Graphics g)
		{	
			super.paintComponent(g);
			
			myTank.draw(g);
			
			for(int i=0;i<enemys.size();i++)
			{
				RobotTank etk = enemys.get(i);
				if((!myTank.getImmunity())&&(etk.destoryTank(myTank)||(myTank.crash(etk)&&etk.getAlive()))){
					booms.add(new Boom(myTank.getBoomX(),myTank.getBoomY()));
					boom.run();
					myTank.reset();
				}
				
				if(myTank.destoryTank(etk)&&etk.getAlive()) {
					etk.setAlive(false);
					myTank.kills++;
					boom.run();
					booms.add(new Boom(etk.x-etk.d,etk.y-etk.d));
					
				}
				if(!etk.getAlive()&&etk.mybullets.size()==0)
					enemys.remove(i);
				//doesn't work
				else
				{	
					for(int j=0;j<enemys.size();j++)
					{
						if(i!=j){
							etk.crash(enemys.get(j));
						}
					}
					etk.draw(g);
				}
			}
			
						
			for(int i=0; i<booms.size();i++){
				if(booms.get(i).getAlive()) booms.get(i).drawBoom(g);
				else booms.remove(i);
			}
			
			for(int i=0; i<steels.size();i++){
				steels.get(i).draw(g);
			}
			
						
		}
		
		//double buffer
		Image offScreenImage = null;
		public void update(Graphics g){
			if(offScreenImage ==null){
				offScreenImage = this.createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
			}
			Graphics gOffScreen =offScreenImage.getGraphics();
			gOffScreen.setColor(Color.BLACK);
			gOffScreen.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
			paintComponent(gOffScreen);
			g.drawImage(offScreenImage, 0, 0, null);
		}
		
	}
	
	
	
	private class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			myTank.keyPressed(e);
		}
		public void keyReleased(KeyEvent e){
			myTank.keyReleased(e);
		}
	}

	
	
	private class PaintThread implements Runnable {

		public void run() {
			while(true)
			{
				tp.repaint();
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private class RobotThread implements Runnable {

		public void run() {
			while(true)
			{	
				for(int i=0;i<enemys.size();i++)
					{
					enemys.get(i).changeDir();
					enemys.get(i).autoFire();
				}
				try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		new TankClient();

	}

}
