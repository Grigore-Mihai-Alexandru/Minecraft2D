package Sound;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip clip;
	URL url[] = new URL[5];
	
	public Sound (){
		url[0] = getClass().getResource("/sounds/hit.wav");
		url[1] = getClass().getResource("/sounds/C418-Subwoofer-Lullaby.wav");
	}
	
	public void setFile(int i) {
		try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void play() {
		clip.start();
	}
	public void stop() {
		clip.stop();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void sleep(int duration) throws InterruptedException {
		Thread.sleep(duration);
	}
	
}
