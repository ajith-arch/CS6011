package com.example.synthesizeapplication.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WhiteNoiseWidget extends AudioComponentWidgetBase {

    public WhiteNoiseWidget(Pane ap, String type, SynthesizeApplication app, boolean hasInput) {
        super(ap, type, app, hasInput);

        // Custom Label with bold font and color
        Label whiteNoiseLabel = new Label("White Noise");
        whiteNoiseLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        whiteNoiseLabel.setStyle("-fx-text-fill: #D7263D;"); // Red color
        titleBox.getChildren().add(whiteNoiseLabel);

        // Custom widget background style
        widget.setStyle(
                "-fx-background-color: #F6F5F5;" +         // Light gray background
                        "-fx-border-color: #D7263D;" +             // Red border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 6px;" +
                        "-fx-background-radius: 6px;"
        );

        ap.getChildren().add(widget);
    }

    @Override
    public AudioComponent getComponent() {
        return new WhiteNoise();
    }
}
