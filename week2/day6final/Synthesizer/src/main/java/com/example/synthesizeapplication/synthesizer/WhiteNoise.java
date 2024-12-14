package com.example.synthesizeapplication.synthesizer;

import java.util.Random;

public class WhiteNoise implements AudioComponent {
    private final Random randomGenerator = new Random();

    @Override
    public AudioClip produceClip() {
        AudioClip noiseClip = new AudioClip();

        for (int i = 0; i < noiseClip.dataBuffer.length / 2; i++) {
            int noiseValue = randomGenerator.nextInt(2 * Short.MAX_VALUE + 1) - Short.MAX_VALUE;
            noiseClip.assignSample(i, noiseValue);
        }

        return noiseClip;
    }

    @Override
    public boolean hasInputConnection() {
        return false;
    }

    @Override
    public void attachInput(AudioComponent component) {
        throw new UnsupportedOperationException("WhiteNoise does not accept inputs.");
    }
}
