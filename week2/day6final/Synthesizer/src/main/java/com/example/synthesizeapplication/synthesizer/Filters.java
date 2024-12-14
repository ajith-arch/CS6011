package com.example.synthesizeapplication.synthesizer;

public class Filters implements AudioComponent {

    private double scalingFactor;
    private AudioComponent inputComponent;

    @Override
    public AudioClip produceClip() {
        AudioClip inputClip = inputComponent.produceClip();
        AudioClip outputClip = new AudioClip();

        for (int i = 0; i < AudioClip.duration * AudioClip.sampleRate; i++) {
            int scaledValue = (int) clampAmplitude(scalingFactor * inputClip.fetchSample(i));
            outputClip.assignSample(i, scaledValue);
        }

        return outputClip;
    }

    private double clampAmplitude(double value) {
        if (value > Short.MAX_VALUE) {
            return Short.MAX_VALUE;
        } else if (value < Short.MIN_VALUE) {
            return Short.MIN_VALUE;
        }
        return value;
    }

    @Override
    public boolean hasInputConnection() {
        return inputComponent != null;
    }

    @Override
    public void attachInput(AudioComponent component) {
        this.inputComponent = component;
    }

    public Filters(double scale) {
        this.scalingFactor = scale;
    }
}
