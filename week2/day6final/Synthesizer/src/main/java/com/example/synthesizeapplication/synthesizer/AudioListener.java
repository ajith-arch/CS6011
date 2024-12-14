package com.example.synthesizeapplication.synthesizer;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioListener implements LineListener {
    private final Clip audioClip;

    public AudioListener(Clip clip) {
        this.audioClip = clip;
    }

    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            audioClip.close();
        }
    }
}

