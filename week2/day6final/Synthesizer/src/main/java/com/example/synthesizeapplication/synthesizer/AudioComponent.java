package com.example.synthesizeapplication.synthesizer;

public interface AudioComponent {
    AudioClip produceClip();

    boolean hasInputConnection();

    void attachInput(AudioComponent inputComponent);
}





