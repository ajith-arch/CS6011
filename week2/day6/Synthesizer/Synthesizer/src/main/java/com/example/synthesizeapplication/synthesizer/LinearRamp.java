package com.example.synthesizeapplication.synthesizer;

public class LinearRamp implements AudioComponent{
    private int start;
    private int stop;
    byte[] clipBytes = new byte[AudioClip.sampleRate*2];

    public LinearRamp(int start, int stop){
        this.start = start;
        this.stop = stop;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip(clipBytes);
        for(int i=0; i<AudioClip.sampleRate; i++){
            int sample = (start*(AudioClip.sampleRate - i)+stop*i)/AudioClip.sampleRate;
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
