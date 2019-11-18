// Why? Yes

import java.io.File; 
import java.io.IOException; 
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.DataLine;
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
   }
   
   public void play()
   {
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      clip.start();
      this.status = "playing";
   }
   
   public void stop()
   {
      clip.stop();
      clip.close();
      this.status = "stopped";
   }
   
   public void setVolume(float volume)
   { 
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      
      double gain = volume; // number between 0 and 1 (loudest)
      float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
      gainControl.setValue(dB);
   }
   
   public String getStatus()
   {
      return this.status;
   }
}