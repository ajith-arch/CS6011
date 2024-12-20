package com.example.synthesizeapplication.synthesizer;

public class VolumeAdjuster implements AudioComponent {

    private double scale;
    private int sampleRate = 44100;
    private int sampleSize = sampleRate*2;
    byte[] input = new byte[sampleSize];

    public VolumeAdjuster(double scale){
        this.scale = scale;
    }

    public int clamp(int input){
        if(input < Short.MIN_VALUE){
            input = Short.MIN_VALUE;
        } else if(input > Short.MAX_VALUE){
            input = Short.MAX_VALUE;
        }
        return input;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip(input);
        for(int i=0; i<sampleRate; i++){
            int sample = clamp(clip.getSample(i));
            clip.setSample(i, (int) (sample*scale));
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input = input.getClip().getData();
    }
}
