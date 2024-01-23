package org.example.mp3playerfx.model.player.engine;

import org.example.mp3playerfx.model.song.Song;

import javax.sound.sampled.*;
import java.io.IOException;

import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;


public class ClipPlayerEngine implements PlayerEngine {

    private double speed;
    private double volume;
    private long position;
    private Clip clip;
    private AudioInputStream stream;
    private AudioFormat format;
    private Song currentSong;

    public ClipPlayerEngine() {
        position = 0;
        stream = null;

    }


    @Override
    public void play() {
        DataLine.Info info;

        try {
            stream = AudioSystem.getAudioInputStream(currentSong.getFile());
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error while playing");
            e.printStackTrace();
        }

        clip.setMicrosecondPosition(position);
        changeVolume(volume);
        clip.start();
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
    public void changeVolume(double newVolume) {
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
    public void changeSpeed(double newSpeed) {
        if(stream!=null) {
            AudioFormat inFormat = stream.getFormat();
            int ch = inFormat.getChannels();
            float rate = inFormat.getSampleRate();
            format = new AudioFormat(PCM_SIGNED, 72000, 16, ch, ch * 2, rate / 2,
                    inFormat.isBigEndian());
        }
        speed = newSpeed;
    }

    @Override
    public void setCurrentSong(Song song) {
        currentSong = song;
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


}
