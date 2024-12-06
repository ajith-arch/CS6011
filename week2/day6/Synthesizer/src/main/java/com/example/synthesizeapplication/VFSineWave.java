package com.example.synthesizeapplication;

public class VFSineWave implements AudioComponent {
    // Variable to store the input AudioComponent
    AudioComponent _input;

    @Override
    public AudioClip getClip() {
        // Variable to track the current phase of the sine wave
        double phase = 0;
        // Get the input audio clip from the connected AudioComponent
        AudioClip clip = _input.getClip();
        // Create a new AudioClip to store the generated output
        AudioClip output = new AudioClip();
        // Iterate through each sample of the input clip
        for (int i = 0; i < clip.bArray.length / 2; i++) {
            // Update the phase based on the input sample value and the sample rate
            phase += 2 * Math.PI * clip.getSample(i) / AudioClip.rate;
            // Calculate and set the current sample value using the sine function
            output.setSample(i, (int) (Short.MAX_VALUE * Math.sin(phase)));
        }
        return output;
    }

    @Override
    public boolean hasInput() {
        // VFSineWave does not accept any input components directly
        return false;
    }

    @Override
    public void connectInput(AudioComponent component) {
        // Set the given AudioComponent as the input to this VFSineWave
        this._input = component;
    }
}