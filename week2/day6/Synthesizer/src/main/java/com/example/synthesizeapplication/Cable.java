package com.example.synthesizeapplication;

public class Cable {
    private final AudioComponent inputComponent;
    private final AudioComponent outputComponent;

    public Cable(AudioComponentWidgetBase inputWidget, AudioComponentWidgetBase outputWidget) {
        this.inputComponent = inputWidget.getAudioComponent();
        this.outputComponent = outputWidget.getAudioComponent();

        if (this.inputComponent == null || this.outputComponent == null) {
            throw new NullPointerException("Input 和 Output 組件必須初始化.");
        }


        this.outputComponent.connectInput(this.inputComponent);
    }
}