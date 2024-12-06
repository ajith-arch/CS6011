package com.example.synthesizeapplication;

public class Filters implements AudioComponent {

    double _scale;
    AudioComponent _input;

    AudioClip newClip;

    // Constructor to initialize the Filters object with a scale factor
    Filters(double scale) {
        this._scale = scale;
    }

    @Override
    public AudioClip getClip() {
        // Get the input clip from the connected AudioComponent
        AudioClip o = _input.getClip();
        // Create a new AudioClip to store the filtered result
        newClip = new AudioClip();

        // Iterate through each sample and apply the scaling factor, clamping if needed
        for (int i = 0; i < AudioClip.duration * AudioClip.rate; i++) {
            newClip.setSample(i, (short) clamping((this._scale * o.getSample(i))));
        }

        return newClip;
    }

    // Method to clamp the value to ensure it stays within the range of a short
    public double clamping(double i) {
        double clamp;
        if (i > Short.MAX_VALUE) {
            clamp = Short.MAX_VALUE;
        } else if (i < Short.MIN_VALUE) {
            clamp = Short.MIN_VALUE;
        } else {
            clamp = i;
        }
        return clamp;
    }

    @Override
    public boolean hasInput() {
        // This method returns false because this filter may or may not have an input connected
        return false;
    }

    @Override
    public void connectInput(AudioComponent component) {
        // Connects the given AudioComponent as input to this filter
        this._input = component;
    }
}