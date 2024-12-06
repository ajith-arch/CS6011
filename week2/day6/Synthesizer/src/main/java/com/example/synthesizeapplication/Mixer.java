package com.example.synthesizeapplication;

import java.util.ArrayList;

public class Mixer implements AudioComponent {

    // List to store all the input AudioComponents to be mixed
    private ArrayList<AudioComponent> _list;

    // Constructor to initialize the list
    Mixer() {
        _list = new ArrayList<>();
    }

    @Override
    public AudioClip getClip() {
        // Temporary AudioClip objects for storing original and mixed output
        AudioClip original;
        AudioClip output = new AudioClip();

        double temp;
        // Iterate over all input components to mix their audio data
        for (int i = 0; i < _list.size(); i++) {
            original = _list.get(i).getClip();
            // Iterate through each sample of the AudioClip
            for (int j = 0; j < original.bArray.length / 2; j++) {
                // Add the current sample value from the original to the output
                temp = output.getSample(j);
                temp += original.getSample(j);
                // Clamp the value to ensure it stays within the range of a short
                if (temp > Short.MAX_VALUE) {
                    temp = Short.MAX_VALUE;
                }
                if (temp < Short.MIN_VALUE) {
                    temp = Short.MIN_VALUE;
                }
                // Set the mixed sample value in the output AudioClip
                output.setSample(j, (int) temp);
            }
        }

        return output;
    }

    @Override
    public boolean hasInput() {
        // Mixer can have multiple inputs, so this method always returns false
        return false;
    }

    @Override
    public void connectInput(AudioComponent component) {
        // Add the given AudioComponent to the list of components to be mixed
        _list.add(component);
    }
}