import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {

    public static Clip music;

    public static void PlaySound(String filePath) {
        Sound(filePath, false);
    }

    public static void PlayMusic(String filePath) {
        Sound(filePath, true);
    }

    private static void Sound(String filePath, boolean loop) {

        File file = new File("src/resources/sounds/" + filePath + ".wav");

        try {
            if (loop && music != null) {
                music.stop();
                music.close();
            }
            Clip clip = AudioSystem.getClip();
            if (loop) {
                music = clip;
            }
            clip.open(AudioSystem.getAudioInputStream(file));

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

    }

}
