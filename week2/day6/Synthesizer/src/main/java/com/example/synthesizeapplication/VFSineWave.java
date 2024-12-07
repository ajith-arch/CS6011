package com.example.synthesizeapplication;

public class VFSineWave implements AudioComponent {
    private AudioComponent inputComponent;

    @Override
    public AudioClip produceClip() {
        if (inputComponent == null) {
            throw new IllegalStateException("Input is not connected for VFSineWave.");
        }

        double currentPhase = 0;
        AudioClip inputClip = inputComponent.produceClip();
        AudioClip outputClip = new AudioClip();

        for (int i = 0; i < inputClip.dataBuffer.length / 2; i++) {
            currentPhase += 2 * Math.PI * inputClip.fetchSample(i) / AudioClip.sampleRate;
            int sineValue = (int) (Short.MAX_VALUE * Math.sin(currentPhase));
            outputClip.assignSample(i, sineValue);
        }

        return outputClip;
    }

    @Override
    public boolean hasInputConnection() {
        return inputComponent != null;
    }

    @Override
    public void attachInput(AudioComponent component) {
        this.inputComponent = component;
    }
}
