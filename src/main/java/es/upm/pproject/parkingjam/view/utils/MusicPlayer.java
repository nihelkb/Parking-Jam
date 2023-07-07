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

/**
 * Class that is responsible for managing and playing different 
 * music tracks and sound effects in the application.
 * It uses the Java Sound API to load and play audio files.
 *
 * @author Nihel Kella Bouziane
 * @version 1.0
 * @since 18/06/2023
 */
public class MusicPlayer {
    private Map<String, Clip> tracks;
    private Long currentFrame;

    /**
     * Constructs a MusicPlayer object and initializes the music tracks.
     */
    public MusicPlayer(){
        this.tracks = new HashMap<>();
        loadMusicTracks();
        playBackgroundMusic();
    }

    /**
     * Loads the music tracks from the specified file paths.
     * Supported audio formats: WAV, AIFF, AU.
     */
    private void loadMusicTracks() {
        String[] trackPaths = { Constants.BACKGROUND_MUSIC, Constants.MOVE_CAR_SOUND, Constants.NEW_GAME_SOUND,
                Constants.RESET_SOUND, Constants.UNDO_SOUND, Constants.DEFAULT_SOUND, Constants.LEVEL_SOUND,
                Constants.GAME_SOUND };
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

    /**
     * Plays the background music track in a loop.
     */
    private void playBackgroundMusic() {
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        bgMusic.start();
    }

    /**
     * Plays a specific sound effect identified by its soundPath.
     * 
     * @param soundPath The path of the sound effect to be played.
     */
    private void playSound(String soundPath) {
        Clip soundClip = tracks.get(soundPath);
        soundClip.stop();
        soundClip.setMicrosecondPosition(0L);
        soundClip.start();
    }

     /**
     * Pauses the background music.
     * The current position of the music is saved to be resumed later.
     */
    public void pauseBackgroundMusic(){
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        this.currentFrame = bgMusic.getMicrosecondPosition();
        bgMusic.stop();
    }

    /**
     * Resumes the background music from the position it was paused.
     */
    public void resumeBackgroundMusic(){
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.setMicrosecondPosition(currentFrame);
        bgMusic.start();
    }

    /**
     * Restarts the background music from the beginning.
     */
    public void restartBackgroundMusic(){
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.stop();
        currentFrame = 0L;
        bgMusic.setMicrosecondPosition(currentFrame);
        bgMusic.start();
    }

    /**
     * Plays the move car sound effect.
     */
    public void moveCarSound(){
        playSound(Constants.MOVE_CAR_SOUND);
    }

    /**
     * Plays the new game sound effect.
     */
    public void newGameSound(){
        playSound(Constants.NEW_GAME_SOUND);
    }

    /**
     * Plays the reset sound effect.
     */
    public void resetSound(){
        playSound(Constants.RESET_SOUND);
    }

    /**
     * Plays the undo sound effect.
     */
    public void undoSound(){
        playSound(Constants.UNDO_SOUND);
    }

    /**
     * Plays the default sound effect.
     */
    public void defaultSound(){
        playSound(Constants.DEFAULT_SOUND);
    }

    /**
     * Plays the level sound effect.
     */
    public void levelSound(){
        playSound(Constants.LEVEL_SOUND);
    }

    /**
     * Plays the game sound effect.
     */
    public void gameSound(){
        playSound(Constants.GAME_SOUND);
    }
}