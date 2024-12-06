package com.example.synthesizeapplication;

import java.util.Random;

public class VFWhiteNoise implements AudioComponent {
    private AudioComponent input;
    private Random random = new Random();

    @Override
    public AudioClip getClip() {
        if (input == null) {
            throw new IllegalStateException("No input connected to VFWhiteNoise.");
        }

        AudioClip frequencyClip = input.getClip();
        AudioClip result = new AudioClip();

        for (int i = 0; i < AudioClip.duration; i++) {
            double maxAmplitude = frequencyClip.getSample(i);
            int sampleValue = random.nextInt((int) (2 * maxAmplitude + 1)) - (int) maxAmplitude;

            result.setSample(i, sampleValue);
        }

        return result;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input = input;
    }
}