package com.example.synthesizeapplication.synthesizer;

import java.util.ArrayList;

public class Mixer implements AudioComponent {
    private final ArrayList<AudioComponent> inputComponents;

    public Mixer() {
        inputComponents = new ArrayList<>();
    }

    @Override
    public AudioClip produceClip() {
        AudioClip mixedClip = new AudioClip();

        for (AudioComponent component : inputComponents) {
            AudioClip componentClip = component.produceClip();

            for (int i = 0; i < componentClip.dataBuffer.length / 2; i++) {
                int existingSample = mixedClip.fetchSample(i);
                int newSample = existingSample + componentClip.fetchSample(i);

                if (newSample > Short.MAX_VALUE) {
                    newSample = Short.MAX_VALUE;
                } else if (newSample < Short.MIN_VALUE) {
                    newSample = Short.MIN_VALUE;
                }

                mixedClip.assignSample(i, newSample);
            }
        }

        return mixedClip;
    }

    @Override
    public boolean hasInputConnection() {
        return !inputComponents.isEmpty();
    }

    @Override
    public void attachInput(AudioComponent component) {
        inputComponents.add(component);
    }
}
