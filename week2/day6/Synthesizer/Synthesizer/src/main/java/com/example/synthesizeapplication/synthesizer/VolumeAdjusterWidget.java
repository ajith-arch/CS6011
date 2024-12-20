package com.example.synthesizeapplication.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class VolumeAdjusterWidget extends AudioComponentWidgetBase {

    public Slider widgetSlider;

    public VolumeAdjusterWidget(Pane ap, String type, SynthesizeApplication app, boolean hasInput) {
        super(ap, type, app, hasInput);

        // Custom Label with updated color
        Label volumeLabel = new Label("Volume Adjuster");
        volumeLabel.setFont(Font.font("Tahoma", 13));
        volumeLabel.setStyle("-fx-text-fill: #3A86FF;"); // Blue color
        titleBox.getChildren().add(volumeLabel);

        // Slider with custom style
        this.widgetSlider = new Slider(0, 1, 0.5);
        this.widgetSlider.setStyle(
                "-fx-control-inner-background: #EDEDED;" +  // Gray background
                        "-fx-track-color: #0077B6;" +               // Blue track
                        "-fx-thumb-color: #0096C7;");               // Aqua thumb
        sliderBox.getChildren().add(widgetSlider);

        // Custom widget styling
        widget.setStyle(
                "-fx-background-color: #CAF0F8;" +          // Light blue background
                        "-fx-border-color: #0077B6;" +             // Blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-background-radius: 5px;"
        );

        ap.getChildren().add(widget);
    }

    @Override
    public AudioComponent getComponent() {
        return new VolumeAdjuster(widgetSlider.getValue());
    }
}
