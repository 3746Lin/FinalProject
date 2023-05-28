package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PlayMusic implements Runnable{
    private File Bgm=new File("CS109-2023-Sping-ChessDemo/music/backgroundmusicwav.wav");
    private int MusicStyle;
    private Clip clip;
    private boolean alreadythread=true;
    public PlayMusic(int MusicStyle){
        this.MusicStyle=MusicStyle;
    }
    public void PlayExactMusic()throws Exception{
        if (alreadythread) {
            Thread thread = new Thread(this);
            alreadythread = false;
            thread.start();
        }
    }

    @Override
    public void run() {
        try {

            if (MusicStyle == 0) {
                AudioInputStream Music = AudioSystem.getAudioInputStream(Bgm);
                clip = AudioSystem.getClip();
                clip.open(Music);
                clip.loop(-1);

            } else {
                AudioInputStream Music = AudioSystem.getAudioInputStream(Bgm);
                clip = AudioSystem.getClip();
                clip.open(Music);
                clip.loop(-1);
            }
            //clip.loop(-1);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void stop(){
        if (clip!=null&&clip.isRunning()){
            clip.stop();
            alreadythread=true;
        }
    }
    public Clip getClip(){
        return clip;
    }
}
