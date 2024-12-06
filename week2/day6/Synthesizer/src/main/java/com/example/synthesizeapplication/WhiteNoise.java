package com.example.synthesizeapplication;

import java.util.Random;

public class WhiteNoise implements AudioComponent {
    private Random random = new Random();

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        int maxValue = Short.MAX_VALUE;

        for (int i = 0; i < clip.bArray.length; i++) {
            int sampleValue = random.nextInt(2 * maxValue + 1) - maxValue;
            clip.setSample(i, sampleValue);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        assert false : "WhiteNoise does not accept inputs.";
    }
}