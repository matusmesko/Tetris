
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Music Controller to access to playing music
 * and controlling music
 *
 * @author Matúš Meško
 */
public class MusicController {

    private Clip clip;
    private FloatControl volume;
    private boolean canPlay;

    public MusicController(boolean canPlay) {
        this.canPlay = canPlay;
    }


    /**
     * Play looped infinite music
     * @param filepath path to the .vaw file
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void playMusicLoop(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!this.getCanPlay()) return;

        // Use BufferedInputStream to support mark/reset
        try (InputStream audioStream = getClass().getResourceAsStream(filepath);
             BufferedInputStream bufferedIn = new BufferedInputStream(audioStream)) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.clip.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception if needed
        }
    }

    /**
     * Play music
     * @param filepath file path to .vaw file
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public void playMusic(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!this.getCanPlay()) return;

        // Use BufferedInputStream to support mark/reset
        try (InputStream audioStream = getClass().getResourceAsStream(filepath);
             BufferedInputStream bufferedIn = new BufferedInputStream(audioStream)) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
            this.clip.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception if needed
        }
    }

    /**
     * Stop playing music
     */
    public void stopMusic() {
        if (this.clip != null && this.clip.isRunning()) {
            this.clip.stop();
            this.clip.close();
        }

    }

    /**
     * Pause music
     */
    public void pauseMusic() {
            if (this.clip != null && this.clip.isRunning()) {
                this.clip.stop();
            }

    }

    /**
     * Resume music
     */
    public void resumeMusic() {
        if (!this.getCanPlay()) return;
        if (this.clip != null && !this.clip.isRunning()) {
            this.clip.start();
        }

    }

    /**
     *  Getter for boolean canPlay
     * @return boolean if music can be played
     */
    public boolean getCanPlay() {
        return canPlay;
    }

    /**
     * Setting atribute canPlay state
     * @param canPlay boolean setting if music can be played
     */
    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }


}
