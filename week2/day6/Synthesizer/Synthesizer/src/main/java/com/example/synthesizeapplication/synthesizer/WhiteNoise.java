package com.example.synthesizeapplication.synthesizer;


public class WhiteNoise implements AudioComponent{

    private int sampleRate = 44100;
    private byte[] bytes = new byte[sampleRate * 2];
    private int max = Short.MAX_VALUE;
    private int min = Short.MIN_VALUE;

    public WhiteNoise(){}

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip(bytes);
        for(int i=0; i<sampleRate; i++){
            int randomInt = (int) (Math.random() * (max-min)) + min;
            clip.setSample(i, randomInt);
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
