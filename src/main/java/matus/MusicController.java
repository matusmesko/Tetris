package matus;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioSystem;
import java.io.BufferedInputStream;
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
    private boolean canPlay;

    public MusicController(boolean canPlay) {
        this.canPlay = canPlay;
    }

    /**
     * Plays a music file from the specified file path.
     * This method checks if music can be played, and if so, it loads the audio file,
     * opens a clip, and starts playing the audio. If an error occurs during playback,
     * it prints the stack trace and rethrows the exception.
     *
     * @param filepath the path to the audio file to be played.
     * @throws UnsupportedAudioFileException if the audio file format is not supported.
     * @throws LineUnavailableException if a line cannot be opened because it is unavailable.
     */
    public void playMusicLoop(String filepath) throws UnsupportedAudioFileException, LineUnavailableException {
        if (!this.getCanPlay()) {
            return;
        }
        try (InputStream audioStream = getClass().getResourceAsStream(filepath);
             BufferedInputStream bufferedIn = new BufferedInputStream(audioStream)) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.clip.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a music file in a loop from the specified file path.
     * This method checks if music can be played, and if so, it loads the audio file,
     * opens a clip, and starts playing the audio in a continuous loop. If an error occurs
     * during playback, it prints the stack trace and rethrows the exception.
     *
     * @param filepath the path to the audio file to be played in a loop.
     * @throws UnsupportedAudioFileException if the audio file format is not supported.
     * @throws LineUnavailableException if a line cannot be opened because it is unavailable.
     */
    public void playMusic(String filepath) throws UnsupportedAudioFileException, LineUnavailableException {
        if (!this.getCanPlay()) {
            return;
        }
        try (InputStream audioStream = getClass().getResourceAsStream(filepath);
             BufferedInputStream bufferedIn = new BufferedInputStream(audioStream)) {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
            this.clip.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing music clip and releases its resources.
     * If the clip is currently running, it will be stopped and closed.
     */
    public void stopMusic() {
        if (this.clip != null && this.clip.isRunning()) {
            this.clip.stop();
            this.clip.close();
        }
    }

    /**
     * Pauses the currently playing music clip.
     * If the clip is currently running, it will be stopped but not closed,
     * allowing it to be resumed later.
     */
    public void pauseMusic() {
        if (this.clip != null && this.clip.isRunning()) {
            this.clip.stop();
        }
    }

    /**
     * Resumes the currently paused music clip.
     * The music will only resume if the clip is not currently running
     * and if the music can be played (as determined by the getCanPlay() method).
     */
    public void resumeMusic() {
        if (!this.getCanPlay()) {
            return;
        }
        if (this.clip != null && !this.clip.isRunning()) {
            this.clip.start();
        }
    }

    /**
     *  Getter for boolean canPlay
     * @return boolean if music can be played
     */
    public boolean getCanPlay() {
        return this.canPlay;
    }

    /**
     * Setting if music can be played or not
     * @param canPlay boolean setting if music can be played
     */
    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }


}
