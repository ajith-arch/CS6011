package com.example.synthesizeapplication;

public class Cable {
    private final AudioComponent inputComponent;
    private final AudioComponent outputComponent;

    public Cable(AudioComponentWidgetBase inputWidget, AudioComponentWidgetBase outputWidget) {
        this.inputComponent = inputWidget.produceAudioComponent();
        this.outputComponent = outputWidget.produceAudioComponent();

        if (this.inputComponent == null || this.outputComponent == null) {
            throw new NullPointerException("Input and output components must not be null.");
        }

        this.outputComponent.attachInput(this.inputComponent);
    }
}
