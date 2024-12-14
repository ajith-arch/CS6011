package com.example.synthesizeapplication.synthesizer;

public interface Connectable {
    void linkInput(Connectable input);

    AudioComponent fetchAudioComponent();
}
