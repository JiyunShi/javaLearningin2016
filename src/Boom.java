import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Boom {
	
	private int x,y;
	private int life = 9;
	private boolean isLive = true;
	
	public Boom(int x, int y)
	{
		this.x=x;
		this.y=y;
	}
	public boolean getAlive(){
		return this.isLive;
	}
	public void lifeDown()
	{
		if(life>0) life--;
		else this.isLive=false;
	}
	
	public void drawBoom(Graphics g){
		
			BufferedImage boomImg = null;
			try {
				
				switch(this.life){
				case 9:boomImg =ImageIO.read(new File("jpg/blast1.gif"));
						break;
				case 8:boomImg =ImageIO.read(new File("jpg/blast2.gif"));
				break;
				case 7:boomImg =ImageIO.read(new File("jpg/blast3.gif"));
				break;
				case 6:boomImg =ImageIO.read(new File("jpg/blast4.gif"));
				break;
				case 5:boomImg =ImageIO.read(new File("jpg/blast5.gif"));
				break;
				case 4:boomImg =ImageIO.read(new File("jpg/blast6.gif"));
				break;
				case 3:boomImg =ImageIO.read(new File("jpg/blast7.gif"));
				break;
				case 2:
				case 1:boomImg =ImageIO.read(new File("jpg/blast8.gif"));
				break;
								
				}
				g.drawImage(boomImg, this.x, this.y, null);
			} catch (IOException e) {
				e.printStackTrace();
			
			}
			
			this.lifeDown();
			
		
		
		
	}
	
	
}
