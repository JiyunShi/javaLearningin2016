import java.awt.*;

public class Bullet {
	int x,y,direction;
	int speed = Tank.SPEED+2;
	boolean isRobot = false;
	
	public Bullet(int x, int y, int direction, boolean robot){
		this.x=x;
		this.y=y;
		this.direction =direction;
		this.isRobot=robot;
	}
	
	public void draw(Graphics g)
	{
		
		Color c = g.getColor();
		if(this.isRobot) g.setColor(Color.GREEN);
		else g.setColor(Color.YELLOW);
		g.drawOval(this.x, this.y, 3, 3);
		g.setColor(c);
		switch(this.direction){
		case 0: x-=speed;
				break;
		case 1:	y-=speed;
				break;
		case 2: x+=speed;
				break;
		case 3: y+=speed;
				break;
		}
		
	}

	public boolean isOutFrame()
	{
		if(this.x<0||this.x>TankClient.WINDOW_WIDTH||this.y<0||this.y>TankClient.WINDOW_HEIGHT)
			return true;
		else return false;
	}
	
	public boolean hitTank(Tank tk)
	{	
		if(tk.getAlive()&&(this.x>tk.x&&this.x<tk.x+tk.d)&&(this.y>tk.y&&this.y<tk.y+tk.d)) return true;
		return false;
	}
	
	
	
}
