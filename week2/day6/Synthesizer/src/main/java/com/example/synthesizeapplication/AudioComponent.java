package com.example.synthesizeapplication;

public interface AudioComponent {
    AudioClip produceClip();

    boolean hasInputConnection();

    void attachInput(AudioComponent inputComponent);
}





