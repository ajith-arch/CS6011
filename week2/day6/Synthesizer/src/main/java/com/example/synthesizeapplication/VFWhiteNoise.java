package com.example.synthesizeapplication;

import java.util.Random;

public class VFWhiteNoise implements AudioComponent {
    private AudioComponent inputComponent;
    private final Random randomGenerator = new Random();

    @Override
    public AudioClip produceClip() {
        if (inputComponent == null) {
            throw new IllegalStateException("Input is not connected for VFWhiteNoise.");
        }

        AudioClip inputClip = inputComponent.produceClip();
        AudioClip outputClip = new AudioClip();

        for (int i = 0; i < AudioClip.duration * AudioClip.sampleRate; i++) {
            double maxAmplitude = inputClip.fetchSample(i);
            int noiseValue = randomGenerator.nextInt((int) (2 * maxAmplitude + 1)) - (int) maxAmplitude;
            outputClip.assignSample(i, noiseValue);
        }

        return outputClip;
    }

    @Override
    public boolean hasInputConnection() {
        return inputComponent != null;
    }

    @Override
    public void attachInput(AudioComponent component) {
        this.inputComponent = component;
    }
}


