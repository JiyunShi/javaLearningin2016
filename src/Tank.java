import java.util.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;


public class Tank {
	int x, y, direction;
	boolean isMoving = false;
	int immunityCount=0;
	public static final int SPEED = 4;
	private final int maxBullet = 5;
	public final int d = 38;
	private boolean isAlive=true;
	protected Vector<Bullet> mybullets = new Vector<Bullet>(); 
	protected int shiftX=0;
	int lifes=5, kills=0;
	protected Vector<RobotTank> enemyList;
	protected TankSound shot = new TankSound("audio/fire.wav");
	
	public Tank(){}
	
	public Tank(int x, int y, int direction,Vector<RobotTank> enemyList) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.enemyList = enemyList;
	}
	
	public void setAlive(boolean alive){
		this.isAlive=alive;
	}
	
	public boolean getAlive(){
		return this.isAlive;
	}
	
	public void draw(Graphics g)
	{
		
		
		
		if(this.isAlive){
			BufferedImage tankImg = null;
			try {
				tankImg = ImageIO.read(new File("jpg/tankgold.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.move();
			if(this.immunityCount==0||!(this.immunityCount%6==0)){
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
		}
		if(mybullets.size()>0){
			Iterator<Bullet> i= mybullets.iterator();
			while(i.hasNext())
			{	
				Bullet bl = i.next();
				bl.draw(g);
				if(bl.isOutFrame()) i.remove();
			}
		}

	}
	
	
	
	
	public void move(){
		if(isMoving){
			if(this.shiftX>=255) this.shiftX=0;
			else this.shiftX+=85;
			switch(this.direction){
			case 0:
				if(this.x<=0) this.x=0;
				else this.x= (x-SPEED);
				break;
			case 1:
				if(this.y<=0) this.y=0;
				else this.y =(y-SPEED);
				break;
			case 2:
				if((this.x+d)>=TankClient.WINDOW_WIDTH) this.x=TankClient.WINDOW_WIDTH-d;
				else this.x =(x+SPEED);
				break;
			case 3:
				//1*d doesn't work
				if((this.y+2*d)>=TankClient.WINDOW_HEIGHT) this.y =TankClient.WINDOW_HEIGHT-2*d;
				else this.y =(y+SPEED);
				break;
			}
		}
		
	}
	
	public void keyPressed(KeyEvent e)
	{	
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			this.direction=0;
			isMoving =true;
			break;
		case KeyEvent.VK_UP:
			this.direction=1;
			isMoving =true;
			break;
		case KeyEvent.VK_RIGHT:
			this.direction=2;
			isMoving =true;
			break;
		case KeyEvent.VK_DOWN:
			this.direction=3;
			isMoving =true;
			break;
		
		}
	}
	
	public void keyReleased(KeyEvent e)
	{	
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_DOWN:
			this.isMoving = false;
			break;
		case KeyEvent.VK_SPACE:
			this.shot(false);
			break;	
		}
	}
	
	public void shot(boolean isRobot){
		if(this.mybullets.size()<maxBullet){
			int BulletX=this.x,BulletY=this.y;
			switch(this.direction)
			{
			case 0:
				BulletY +=d/2;
				break;
			case 1:
				BulletX +=d/2;
				break;
			case 2:
				BulletX +=d;
				BulletY +=d/2;
				break;
			case 3:
				BulletX +=d/2;
				BulletY +=d;
				break;
			}
			mybullets.add(new Bullet(BulletX,BulletY,this.direction,isRobot));
			if(!isRobot) shot.run();
		}
	}
	
	
	
	public boolean destoryTank(Tank tk){
		
		if(mybullets.size()>0){
			Iterator<Bullet> i= mybullets.iterator();
			while(i.hasNext())
			{	
				Bullet bl = i.next();
				
				if(bl.hitTank(tk)){
					i.remove();
					return true;
				}
			}
			return false;
		}
		return false;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x,y,d,d);
	}
	
	public boolean crash(Tank tk)
	{	
		return this.getRect().intersects(tk.getRect());
	}
	
	public int getBoomX()
	{
		return this.x-this.d;
	}
	public int getBoomY()
	{
		return this.y-this.d;
	}
	
	
	public void reset(){
		this.x=400;
		this.y=500;
		this.direction=1;
		this.lifes--;
		this.immunityCount =500;
	}
	
	public boolean getImmunity(){
	
		if(this.immunityCount>0){ this.immunityCount--;
		return true;}
		else return false;
	}
	
}
