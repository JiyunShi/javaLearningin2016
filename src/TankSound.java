import java.applet.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;



public class TankSound extends Thread{
	AudioClip clip;
	
	
	public TankSound(String str){
		
		URL urlClip;
		try {
			urlClip = new File(str).toURI().toURL();
			clip = Applet.newAudioClip(urlClip);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void run(){
		clip.stop();
		clip.play();
	}
	
	
	
}
