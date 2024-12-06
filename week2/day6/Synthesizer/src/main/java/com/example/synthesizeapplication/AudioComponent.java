package com.example.synthesizeapplication;

import java.util.ArrayList;

public interface AudioComponent {
    AudioClip getClip();

    boolean hasInput();
    void connectInput(AudioComponent component);
}
