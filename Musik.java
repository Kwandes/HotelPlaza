// Why? Yes

import java.io.File; 
import java.io.IOException; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 

public class Musik
{
   private String filePath;
   private AudioInputStream audio;
   private Clip clip;
   private String status;
   
   public Musik(String filepath)
      throws UnsupportedAudioFileException, IOException, LineUnavailableException
   {
      this.status = "idle";
      this.filePath = filepath;
      audio = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
      
      clip = AudioSystem.getClip();
      clip.open(audio);
      clip.loop(Clip.LOOP_CONTINUOUSLY);
   }
   
   public void play()
   {
      clip.start();
      this.status = "playing";
   }
   
   public void stop()
   {
      clip.stop();
      this.status = "stopped";
   }
   
   public String getStatus()
   {
      return this.status;
   }
}