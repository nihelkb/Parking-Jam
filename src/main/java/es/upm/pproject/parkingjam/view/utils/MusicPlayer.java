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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

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

    private boolean ready;

    private static MusicPlayer instance; // singleton

    private static final Logger logger = LoggerFactory.getLogger(MusicPlayer.class);
    private static final Marker musicMarker = MarkerFactory.getMarker("MUSIC");
    private static final Marker fatalMarker = MarkerFactory.getMarker("FATAL");

    /**
     * Constructs a MusicPlayer object and initializes the music tracks.
     */
    private MusicPlayer(){
        this.tracks = new HashMap<>();
        this.ready = loadMusicTracks();
    }

    /**
     * Creates a new MusicPlayer instance or returns one if created
     * @return The MusicPlayer instance
     */
    public static MusicPlayer getInstance(){
        if(instance == null){
            instance = new MusicPlayer();
        }
        return instance;
    }

    /**
     * Loads the music tracks from the specified file paths.
     * Supported audio formats: WAV, AIFF, AU.
     */
    private boolean loadMusicTracks() {
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
            logger.info(musicMarker, "Music tracks succesfully loaded");   
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            logger.error(fatalMarker, "Music tracks can not be loaded");
            return false;
        }
        return true;
    }

    /**
     * Plays the background music track in a loop.
     */
    public void playBackgroundMusic() {
        if(!ready) return;
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
        bgMusic.start();
    }

    /**
     * Plays a specific sound effect identified by its soundPath.
     * @param soundPath The path of the sound effect to be played.
     */
    private void playSound(String soundPath) {
        if(!ready) return;
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
        if(!ready) return;
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        this.currentFrame = bgMusic.getMicrosecondPosition();
        bgMusic.stop();
    }

    /**
     * Resumes the background music from the position it was paused.
     */
    public void resumeBackgroundMusic(){
        if(!ready) return;
        Clip bgMusic = tracks.get(Constants.BACKGROUND_MUSIC);
        bgMusic.setMicrosecondPosition(currentFrame);
        bgMusic.start();
    }

    /**
     * Restarts the background music from the beginning.
     */
    public void restartBackgroundMusic(){
        if(!ready) return;
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
        if(!ready) return;
        playSound(Constants.MOVE_CAR_SOUND);
    }

    /**
     * Plays the new game sound effect.
     */
    public void newGameSound(){
        if(!ready) return;
        playSound(Constants.NEW_GAME_SOUND);
    }

    /**
     * Plays the reset sound effect.
     */
    public void resetSound(){
        if(!ready) return;
        playSound(Constants.RESET_SOUND);
    }

    /**
     * Plays the undo sound effect.
     */
    public void undoSound(){
        if(!ready) return;
        playSound(Constants.UNDO_SOUND);
    }

    /**
     * Plays the default sound effect.
     */
    public void defaultSound(){
        if(!ready) return;
        playSound(Constants.DEFAULT_SOUND);
    }

    /**
     * Plays the level sound effect.
     */
    public void levelSound(){
        if(!ready) return;
        playSound(Constants.LEVEL_SOUND);
    }

    /**
     * Plays the game sound effect.
     */
    public void gameSound(){
        if(!ready) return;
        playSound(Constants.GAME_SOUND);
    }
}