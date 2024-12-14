package com.example.synthesizeapplication.synthesizer;

public class VFSquareWave implements AudioComponent {
    private AudioComponent inputComponent;

    @Override
    public AudioClip produceClip() {
        if (inputComponent == null) {
            throw new IllegalStateException("Input is not connected for VFSquareWave.");
        }

        double currentPhase = 0;
        AudioClip inputClip = inputComponent.produceClip();
        AudioClip outputClip = new AudioClip();

        for (int i = 0; i < inputClip.dataBuffer.length / 2; i++) {
            currentPhase += 2 * Math.PI * inputClip.fetchSample(i) / AudioClip.sampleRate;
            int squareValue = Math.sin(currentPhase) > 0 ? Short.MAX_VALUE : Short.MIN_VALUE;
            outputClip.assignSample(i, squareValue);
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
