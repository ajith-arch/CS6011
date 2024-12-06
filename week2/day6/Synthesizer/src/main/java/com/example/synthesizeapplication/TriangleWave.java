package com.example.synthesizeapplication;

public class TriangleWave implements AudioComponent {
    private int frequency;

    public TriangleWave(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        int maxValue = Short.MAX_VALUE;

        for (int i = 0; i < clip.bArray.length; i++) {
            double period = clip.rate / (double) frequency;
            double value = (2 * maxValue / period) * (i % period) - maxValue;
            if ((i / period) % 2 == 0) {
                value = -value;
            }
            clip.setSample(i, (int) value);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        assert false : "TriangleWave does not accept inputs.";
    }
}