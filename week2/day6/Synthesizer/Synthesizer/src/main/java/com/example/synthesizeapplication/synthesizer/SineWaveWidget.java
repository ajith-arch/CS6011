package com.example.synthesizeapplication.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SineWaveWidget extends AudioComponentWidgetBase {

    public Slider widgetSlider;

    public SineWaveWidget(Pane ap, String type, SynthesizeApplication app, boolean hasInput) {
        super(ap, type, app, hasInput);

        // Custom Label with updated font and color
        Label sineLabel = new Label(type);
        sineLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        sineLabel.setStyle("-fx-text-fill: #1D3557;"); // Dark blue color
        titleBox.getChildren().add(sineLabel);

        // Slider with updated style
        this.widgetSlider = new Slider(20, 20000, 440);
        this.widgetSlider.setStyle(
                "-fx-control-inner-background: #A8DADC;" +  // Light teal background
                        "-fx-track-color: #457B9D;" +               // Blue slider track
                        "-fx-thumb-color: #E63946;");               // Red slider thumb
        sliderBox.getChildren().add(widgetSlider);

        // Custom styling for widget background
        widget.setStyle(
                "-fx-background-color: #F1FAEE;" +          // Light background
                        "-fx-border-color: #1D3557;" +             // Dark blue border
                        "-fx-border-width: 2px;" +                 // Border width
                        "-fx-border-radius: 5px;" +                // Rounded corners
                        "-fx-background-radius: 5px;"              // Rounded corners
        );

        ap.getChildren().add(widget);
    }

    @Override
    public AudioComponent getComponent() {
        System.out.println("from sinewavewidget " + widgetSlider.getValue());
        return new SineWave(widgetSlider.getValue());
    }
}
