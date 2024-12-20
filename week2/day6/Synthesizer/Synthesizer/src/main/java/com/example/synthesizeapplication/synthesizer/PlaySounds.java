package com.example.synthesizeapplication.synthesizer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class PlaySounds {

    Clip c = AudioSystem.getClip();
    AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

    public PlaySounds() throws LineUnavailableException {
    }

    public Clip getClip(){
        return c;
    }

}
