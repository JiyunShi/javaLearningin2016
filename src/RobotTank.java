import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;


public class RobotTank extends Tank {
	 
	private static Random rd = new Random();
	private TankSound addEnemy = new TankSound("audio/add.wav");
	
	public RobotTank(Vector<RobotTank> enemyList) {
		super();
		int rndX, rndY;
		boolean flag = true;
		rndX = rd.nextInt(800);
		rndY = rd.nextInt(600);
		while(flag){
			flag = false;
			for(int i=0;i<enemyList.size();i++)
			{
				if(enemyList.get(i).getRect().intersects(new Rectangle(rndX,rndY,d,d))){
					rndX = rd.nextInt(800);
					rndY = rd.nextInt(550);
					flag = true;
				}
			}
		}
		this.x = rndX;
		this.y = rndY;
		this.direction = rd.nextInt(4);
		this.enemyList = enemyList;
		
	}
	
	
	
	
	public void draw(Graphics g)
	{	
		
		if(super.getAlive()){
			BufferedImage tankImg = null;
			try {
				tankImg = ImageIO.read(new File("jpg/tankgreen.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.isMoving=true;
			super.move();
			switch(this.direction){
			case 0:
				g.drawImage(tankImg, x, y,x+d,y+d,23+this.shiftX,132,23+this.shiftX+d,132+d,null);
				break;
			case 1:
				g.drawImage(tankImg, x, y,x+d,y+d,23+this.shiftX,302,23+this.shiftX+d,302+d,null);
				break;
			case 2:
				g.drawImage(tankImg, x, y,x+d,y+d,23+this.shiftX,217,23+this.shiftX+d,217+d,null);
				break;
			case 3:
				g.drawImage(tankImg, x, y,x+d,y+d,23+this.shiftX,47,23+this.shiftX+d,47+d,null);
				break;	
			}
		}
		
		for(int i=0;i<this.mybullets.size();i++)
			{
			this.mybullets.get(i).draw(g);
			if(mybullets.get(i).isOutFrame()) mybullets.remove(i);
			}
		
		
		if(this.enemyList.size()<3){
			this.addEnemy.run();
			this.enemyList.add(new RobotTank(this.enemyList));
			this.enemyList.add(new RobotTank(this.enemyList));
			this.enemyList.add(new RobotTank(this.enemyList));
		}
		
		
		
		
	}
	
	
	public void changeDir(){
		this.direction = rd.nextInt(4);
	}
	
	public void autoFire()
	{	if(super.getAlive()){
		if(rd.nextBoolean()) this.shot(true);}
	}
	
	
	public void crash(RobotTank tk){
		if(super.crash(tk)) {
			this.direction = (this.direction+2)%4;
		}
		
	}


}
