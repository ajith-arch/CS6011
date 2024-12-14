package com.example.synthesizeapplication.synthesizer;

public class SquareWave implements AudioComponent {
    private final int waveFrequency;

    public SquareWave(int frequency) {
        this.waveFrequency = frequency;
    }

    @Override
    public AudioClip produceClip() {
        AudioClip squareClip = new AudioClip();
        int amplitude = Short.MAX_VALUE;

        for (int i = 0; i < squareClip.dataBuffer.length / 2; i++) {
            double period = AudioClip.sampleRate / (double) waveFrequency;
            if ((i % period) < (period / 2)) {
                squareClip.assignSample(i, amplitude);
            } else {
                squareClip.assignSample(i, -amplitude);
            }
        }

        return squareClip;
    }

    @Override
    public boolean hasInputConnection() {
        return false;
    }

    @Override
    public void attachInput(AudioComponent component) {
        throw new UnsupportedOperationException("SquareWave does not accept inputs.");
    }
}
