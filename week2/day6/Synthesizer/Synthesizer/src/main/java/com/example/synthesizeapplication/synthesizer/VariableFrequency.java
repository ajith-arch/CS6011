package com.example.synthesizeapplication.synthesizer;

public class VariableFrequency implements AudioComponent{
    private byte[] clipBytes;

    public VariableFrequency(){}

    @Override
    public AudioClip getClip() {
        int numSamples = this.clipBytes.length / 2;
        AudioClip clip = new AudioClip(clipBytes);
        double phase = 0;
        for(int i=0; i<numSamples; i++){
            phase += 2 * Math.PI * clip.getSample(i) / numSamples;
            int output = (int) (Short.MAX_VALUE * Math.sin(phase));
            clip.setSample(i, output);
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.clipBytes = input.getClip().getData();
    }
}