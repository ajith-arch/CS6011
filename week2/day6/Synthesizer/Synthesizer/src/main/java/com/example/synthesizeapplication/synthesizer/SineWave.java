package com.example.synthesizeapplication.synthesizer;

public class SineWave implements AudioComponent {

    private double frequency;
    private int maxValue = Short.MAX_VALUE;
    private int sampleRate = 44100;
    private int sampleSize = sampleRate*2;

    public SineWave(double frequency){
        this.frequency = frequency;
    }

    @Override
    public AudioClip getClip(){
        byte[] sample = new byte[sampleSize];
        AudioClip clip = new AudioClip(sample);
        for(int i=0; i<sampleRate; i++){
            double sampleBit = maxValue * Math.sin(2 * Math.PI * frequency * i / sampleRate);
            clip.setSample(i, (int) sampleBit);
        }
        return new AudioClip(clip.getData());
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {

    }

}
