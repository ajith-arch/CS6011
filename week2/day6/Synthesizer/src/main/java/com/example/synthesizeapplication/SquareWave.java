package com.example.synthesizeapplication;


import com.example.synthesizeapplication.AudioComponent;
import com.example.synthesizeapplication.AudioClip ;

public class SquareWave implements AudioComponent {
    private int frequency;

    public SquareWave(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public AudioClip getClip() {
        AudioClip clip = new AudioClip();
        int maxValue = Short.MAX_VALUE;

        for (int i = 0; i < clip.bArray.length; i++) {
            if ((frequency * i / (double) clip.rate) % 1 > 0.5) {
                clip.setSample(i, maxValue);
            } else {
                clip.setSample(i, -maxValue);
            }
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        assert false : "SquareWave does not accept inputs.";
    }
}