
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class MusicController {

    private Clip clip;
    private FloatControl volume;
    private boolean canPlay = true;

    public void playMusicLoop(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath));
        this.clip = AudioSystem.getClip();
        //this.volume = (FloatControl) this.clip.getControl(FloatControl.Type.VOLUME);
        //this.volume = (FloatControl) this.clip.getControl(FloatControl.Type.BALANCE);
        this.clip.open(audioInputStream);
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.clip.start();
    }

    public void playMusic(String filepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filepath));
            this.clip = AudioSystem.getClip();
            this.clip.open(audioInputStream);
            clip.start();

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
            if (this.clip != null && !this.clip.isRunning()) {
                this.clip.start();
            }

    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public void checkSettings() {
        if (!this.canPlay) {
            this.clip.stop();
        }
    }

    //    public void setVolume(float volume) {
//        if (volume < 0 || volume > 100) return;
//        float db = (float) (volume / 100.0 * 6.0206);
//        this.volume.setValue(db);
//    }

}
