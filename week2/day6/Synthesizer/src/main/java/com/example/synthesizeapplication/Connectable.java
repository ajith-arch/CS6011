package com.example.synthesizeapplication;

public interface Connectable {
    void linkInput(Connectable input);

    AudioComponent fetchAudioComponent();
}

