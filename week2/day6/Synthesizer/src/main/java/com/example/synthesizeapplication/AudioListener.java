package com.example.synthesizeapplication;



import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioListener implements LineListener {
    private final Clip _clip;
    AudioListener(Clip c) {
        this._clip = c;
    }


    @Override
    public void update(LineEvent event) {
        if (event.getType() == LineEvent.Type.STOP) {
            _clip.close();
        }
    }
}
