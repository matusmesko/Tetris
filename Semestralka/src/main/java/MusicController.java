
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MusicController {

    private Clip clip;
    private FloatControl volume;
    private boolean canPlay;

    public MusicController(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void playMusicLoop(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!this.getCanPlay()) return;
        InputStream audioStream = getClass().getResourceAsStream(filepath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioInputStream);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.clip.start();
    }

    public void playMusic(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if (!this.getCanPlay()) return;
        InputStream audioStream = getClass().getResourceAsStream(filepath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
        this.clip = AudioSystem.getClip();
        this.clip.open(audioInputStream);
        this.clip.start();

    }

    public void stopMusic() {
        if (this.clip != null && this.clip.isRunning()) {
            this.clip.stop();
            this.clip.close();
        }

    }

    public void pauseMusic() {
            if (this.clip != null && this.clip.isRunning()) {
                this.clip.stop();
            }

    }

    public void resumeMusic() {
        if (!this.getCanPlay()) return;
        if (this.clip != null && !this.clip.isRunning()) {
            this.clip.start();
        }

    }

    public boolean getCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }


}
