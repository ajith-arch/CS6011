package com.example.synthesizeapplication;

import com.example.synthesizeapplication.AudioComponent;
import com.example.synthesizeapplication.AudioClip ;

public class SineWave implements AudioComponent {
    // Variable to store the frequency of the sine wave
    private int _frequency;

    // Constructor to initialize the SineWave with a given frequency
    SineWave(int frequency) {
        this._frequency = frequency;
    }

    @Override
    public AudioClip getClip() {
        // Create a new AudioClip to store the generated sine wave audio data
        AudioClip clip = new AudioClip();
        // Iterate through each sample of the audio clip
        for (int index = 0; index < clip.bArray.length / 2; index++) {
            // Calculate and set each sample value using the sine function
            clip.setSample(index, (short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * _frequency * index / clip.rate)));
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        // SineWave does not accept any input components
        return false;
    }

    @Override
    public void connectInput(AudioComponent component) {
        // No implementation needed as SineWave does not accept input components
    }
}