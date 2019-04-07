package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class SoundManager {
	public static void playSound(String filePath) {
		File audioFile = new File(filePath);
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip audioClip = (Clip) AudioSystem.getLine(info);
			audioClip.open(audioStream);
			audioClip.loop(0);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
