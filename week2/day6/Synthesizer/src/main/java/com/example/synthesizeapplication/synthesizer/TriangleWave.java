package com.example.synthesizeapplication.synthesizer;


public class TriangleWave implements AudioComponent {
    private final int waveFrequency;

    public TriangleWave(int frequency) {
        this.waveFrequency = frequency;
    }

    @Override
    public AudioClip produceClip() {
        AudioClip triangleClip = new AudioClip();
        int amplitude = Short.MAX_VALUE;

        for (int i = 0; i < triangleClip.dataBuffer.length / 2; i++) {
            double period = AudioClip.sampleRate / (double) waveFrequency;
            double position = i % period;
            double value = (2 * amplitude / period) * position - amplitude;

            if ((i / period) % 2 == 0) {
                value = -value;
            }

            triangleClip.assignSample(i, (int) value);
        }

        return triangleClip;
    }

    @Override
    public boolean hasInputConnection() {
        return false;
    }

    @Override
    public void attachInput(AudioComponent component) {
        throw new UnsupportedOperationException("TriangleWave does not accept inputs.");
    }
}
