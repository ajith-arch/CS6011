package com.example.synthesizeapplication;

public class LinearRamp implements AudioComponent {
    // Variables to store the start and stop values of the ramp
    private double _start;
    private double _stop;

    // Constructor to initialize the LinearRamp with start and stop values
    LinearRamp(double start, double stop) {
        this._start = start;
        this._stop = stop;
    }

    @Override
    public AudioClip getClip() {
        // Create a new AudioClip to store the generated linear ramp audio data
        AudioClip clip = new AudioClip();
        // Iterate through each sample of the audio clip to generate the ramp
        for (int i = 0; i < clip.bArray.length / 2; i++) {
            // Set each sample value based on linear interpolation between start and stop
            clip.setSample(i, (int) ((this._start * (clip.bArray.length / 2 - i) + this._stop * i) / (clip.bArray.length / 2)));
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        // This component does not have any input
        return false;
    }

    @Override
    public void connectInput(AudioComponent component) {
        // No implementation needed as LinearRamp does not accept input components
    }
}
