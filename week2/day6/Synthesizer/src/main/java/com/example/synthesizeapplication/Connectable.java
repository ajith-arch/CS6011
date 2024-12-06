package com.example.synthesizeapplication;

public interface Connectable {
    void setInput(Connectable input);
    AudioComponent getAudioComponent();
}
