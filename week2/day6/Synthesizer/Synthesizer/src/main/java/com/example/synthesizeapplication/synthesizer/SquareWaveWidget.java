package com.example.synthesizeapplication.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class SquareWaveWidget extends AudioComponentWidgetBase {

    public Slider widgetSlider;

    public SquareWaveWidget(Pane ap, String type, SynthesizeApplication app, boolean hasInput) {
        super(ap, type, app, hasInput);

        // Custom Label with italic font and color
        Label squareLabel = new Label("Square Wave");
        squareLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 14));
        squareLabel.setStyle("-fx-text-fill: #6A0572;"); // Purple color
        titleBox.getChildren().add(squareLabel);

        // Slider with updated style
        this.widgetSlider = new Slider(30, 15000, 500);
        this.widgetSlider.setStyle(
                "-fx-control-inner-background: #FDE2E4;" +  // Pink background
                        "-fx-track-color: #C45BAA;" +               // Magenta track
                        "-fx-thumb-color: #570861;");               // Dark purple thumb
        sliderBox.getChildren().add(this.widgetSlider);

        // Custom widget background style
        widget.setStyle(
                "-fx-background-color: #F7F6DC;" +          // Cream background
                        "-fx-border-color: #6A0572;" +             // Purple border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-background-radius: 6px;"
        );

        ap.getChildren().add(widget);
    }

    @Override
    public AudioComponent getComponent() {
        return new SquareWave(widgetSlider.getValue());
    }
}
