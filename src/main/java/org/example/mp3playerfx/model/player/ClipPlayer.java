package org.example.mp3playerfx.model.player;
import lombok.Getter;
import lombok.Setter;
import org.example.mp3playerfx.model.playlist.Playlist;
import org.example.mp3playerfx.model.Song;

import javax.sound.sampled.*;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

@Getter
@Setter
public class ClipPlayer implements Player {
    private Playlist playlist;
    public int songNumber;
    private double speed;
    private double volume;
    private Clip clip;
    private AudioInputStream stream;
    AudioFormat format;
    private long position;

    public ClipPlayer(Playlist playlist) {
        this.playlist = playlist;
        position = 0;
        volume = 0.5;
        stream = null;
    }


    @Override
    public void play() {
        DataLine.Info info;

        try {
            stream = AudioSystem.getAudioInputStream(playlist.getSongs().get(songNumber).getFile());
            format = stream.getFormat();
            setSpeed(0);
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error while playing");
            e.printStackTrace();
        }

        clip.setMicrosecondPosition(position);
        setVolume(volume);
        clip.start();

    }

    @Override
    public void pause() {
        position = clip.getMicrosecondPosition();
        clip.close();
        try {
            stream.close();
        } catch (IOException e) {
            System.out.println("Error while pausing");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if(clip!=null) {
            position = 0;
            clip.close();
            try {
                stream.close();
            } catch (IOException e) {
                System.out.println("Error while stopping");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void previous() {
        if(songNumber > 0) {
            songNumber--;
        }
        else {
            songNumber = playlist.getSongs().size()-1;
        }
        stop();
        play();

    }

    @Override
    public void next() {
        songNumber = (songNumber+1)%playlist.getSongs().size();
        stop();
        play();
    }

    @Override
    public void setVolume(double newVolume) {
        System.out.println(newVolume);

        float calculatedVolume = 20f * (float) Math.log10(newVolume);
        if(clip!=null) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (volumeControl != null) {
                volumeControl.setValue(calculatedVolume);
            }
        }

        volume = newVolume;
    }

    @Override
    public void setSpeed(double newSpeed) {
        AudioFormat inFormat = stream.getFormat();
        int ch = inFormat.getChannels();
        float rate = inFormat.getSampleRate();
        format = new AudioFormat(PCM_SIGNED, 72000, 16, ch, ch * 2, rate/2,
                inFormat.isBigEndian());
    }


    @Override
    public double getCurrentTime() {
        if(clip!=null) {
            return clip.getMicrosecondPosition() * 1000000;
        }
        else {
            return position;
        }
    }

    @Override
    public double getDuration() {
        return clip.getMicrosecondLength() * 1000000;
    }

    @Override
    public Song getCurrentSong() {
        return playlist.getSongs().get(songNumber);
    }

    @Override
    public int getSongNum() {
       return songNumber;
    }

    @Override
    public void setSongNum(int num) {
        songNumber = num;
    }

    @Override
    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

}
