package com.example.synthesizeapplication.synthesizer;


public interface AudioComponent {

    AudioClip getClip();
    boolean hasInput();
    void connectInput(AudioComponent input);

}