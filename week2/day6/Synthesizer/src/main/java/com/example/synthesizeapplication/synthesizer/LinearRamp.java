package com.example.synthesizeapplication.synthesizer;

public class LinearRamp implements AudioComponent {
    private final double initialValue;
    private final double finalValue;

    public LinearRamp(double start, double stop) {
        this.initialValue = start;
        this.finalValue = stop;
    }

    @Override
    public AudioClip produceClip() {
        AudioClip outputClip = new AudioClip();
        int totalSamples = outputClip.dataBuffer.length / 2;

        for (int i = 0; i < totalSamples; i++) {
            double interpolatedValue = ((initialValue * (totalSamples - i)) + (finalValue * i)) / totalSamples;
            outputClip.assignSample(i, (int) interpolatedValue);
        }

        return outputClip;
    }

    @Override
    public boolean hasInputConnection() {
        return false;
    }

    @Override
    public void attachInput(AudioComponent component) {
        throw new UnsupportedOperationException("LinearRamp does not accept inputs.");
    }
}
