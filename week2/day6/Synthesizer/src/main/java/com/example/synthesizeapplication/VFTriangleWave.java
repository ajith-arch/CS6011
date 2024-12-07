package com.example.synthesizeapplication;

public class VFTriangleWave implements AudioComponent {
    private AudioComponent inputComponent;

    @Override
    public AudioClip produceClip() {
        if (inputComponent == null) {
            throw new IllegalStateException("Input is not connected for VFTriangleWave.");
        }

        double currentPhase = 0;
        AudioClip inputClip = inputComponent.produceClip();
        AudioClip outputClip = new AudioClip();

        for (int i = 0; i < inputClip.dataBuffer.length / 2; i++) {
            currentPhase += 2 * Math.PI * inputClip.fetchSample(i) / AudioClip.sampleRate;

            double normalizedPhase = currentPhase % (2 * Math.PI);
            double triangleValue = 2 * Short.MAX_VALUE * Math.abs(normalizedPhase / Math.PI - 1) - Short.MAX_VALUE;

            outputClip.assignSample(i, (int) triangleValue);
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
