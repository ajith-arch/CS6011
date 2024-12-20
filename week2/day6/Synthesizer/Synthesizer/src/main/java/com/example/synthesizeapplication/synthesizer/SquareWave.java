package com.example.synthesizeapplication.synthesizer;


public class SquareWave implements AudioComponent{
    private int sampleRate = 44100;
    private byte[] bytes = new byte[sampleRate * 2];
    private int maxValue = Short.MAX_VALUE;
    private double frequency;

    public SquareWave(double frequency){
        this.frequency = frequency;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip(bytes);
        for(int i=0; i<sampleRate; i++){
            int sample;
            if((frequency * i / sampleRate) % 1 > 0.5){
                sample = maxValue;
            } else {
                sample = -maxValue;
            }
            clip.setSample(i,sample);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }
}
