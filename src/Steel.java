import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Steel {
	private int x,y;
	
	public Steel(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g)
	{
		BufferedImage steelImg = null;
		try {
			steelImg = ImageIO.read(new File("steel.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(steelImg, x, y, null);
	}
	
	
	public Rectangle getRect(){
		return new Rectangle(x,y,1,1);
	}
	
	
	
	
	
}
