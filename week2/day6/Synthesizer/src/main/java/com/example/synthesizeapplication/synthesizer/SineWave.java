package com.example.synthesizeapplication.synthesizer;

public class SineWave implements AudioComponent {
    private final int waveFrequency;

    public SineWave(int frequency) {
        this.waveFrequency = frequency;
    }

    @Override
    public AudioClip produceClip() {
        AudioClip sineClip = new AudioClip();

        for (int i = 0; i < sineClip.dataBuffer.length / 2; i++) {
            double angle = 2 * Math.PI * waveFrequency * i / AudioClip.sampleRate;
            short sampleValue = (short) (Short.MAX_VALUE * Math.sin(angle));
            sineClip.assignSample(i, sampleValue);
        }

        return sineClip;
    }

    @Override
    public boolean hasInputConnection() {
        return false;
    }

    @Override
    public void attachInput(AudioComponent component) {
        throw new UnsupportedOperationException("SineWave does not accept inputs.");
    }
}
