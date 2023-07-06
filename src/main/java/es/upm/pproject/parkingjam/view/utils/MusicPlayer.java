package es.upm.pproject.parkingjam.view.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicPlayer {
    private Map<String, Clip> tracks;
    private Long currentFrame;

    public MusicPlayer(){
        this.tracks = new HashMap<>();
        loadMusicTracks();
        playBackgroundMusic();
    }

    private void loadMusicTracks() {
        String[] trackPaths = { Constants.BACKGROUND_MUSIC, Constants.MOVE_CAR_SOUND, Constants.NEW_GAME_SOUND,
                Constants.RESET_SOUND, Constants.UNDO_SOUND, Constants.DEFAULT_SOUND };
        AudioInputStream audioInputStream;
        try {
            for (int i = 0; i < trackPaths.length; i++) {
                audioInputStream = AudioSystem
                        .getAudioInputStream(new File(trackPaths[i]).getAbsoluteFile());
                Clip trackClip = AudioSystem.getClip();
                trackClip.open(audioInputStream);
                tracks.put(trackPaths[i], trackClip);
            }   
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            // couldnt load music
        }
    }

    private void playBackgroundMusic() {
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        bgMusic.start();
    }

    private void playSound(String soundPath) {
        Clip soundClip = tracks.get(soundPath);
        soundClip.stop();
        soundClip.setMicrosecondPosition(0L);
        soundClip.start();
    }

    public void pauseBackgroundMusic(){
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        this.currentFrame = bgMusic.getMicrosecondPosition();
        bgMusic.stop();
    }

    public void resumeBackgroundMusic(){
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.setMicrosecondPosition(currentFrame);
        bgMusic.start();
    }

    public void restartBackgroundMusic(){
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.stop();
        currentFrame = 0L;
        bgMusic.setMicrosecondPosition(currentFrame);
        bgMusic.start();
    }

    public void moveCarSound(){
        playSound(Constants.MOVE_CAR_SOUND);
    }

    public void newGameSound(){
        playSound(Constants.NEW_GAME_SOUND);
    }

    public void resetSound(){
        playSound(Constants.RESET_SOUND);
    }

    public void undoSound(){
        playSound(Constants.UNDO_SOUND);
    }

    public void defaultSound(){
        playSound(Constants.DEFAULT_SOUND);
    }
}