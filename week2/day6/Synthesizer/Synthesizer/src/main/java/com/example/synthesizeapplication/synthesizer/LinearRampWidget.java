package com.example.synthesizeapplication.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LinearRampWidget extends AudioComponentWidgetBase {

    public TextField startField;
    public TextField stopField;

    public LinearRampWidget(Pane ap, String type, SynthesizeApplication app, boolean hasInput) {
        super(ap, type, app, hasInput);

        // Custom Label
        Label linearRampLabel = new Label("Linear Ramp");
        linearRampLabel.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        linearRampLabel.setStyle("-fx-text-fill: #2A9D8F;"); // Greenish-blue color
        titleBox.getChildren().add(linearRampLabel);

        // Custom text fields for start and stop values
        this.startField = new TextField();
        this.startField.setPromptText("Start Value");
        this.stopField = new TextField();
        this.stopField.setPromptText("Stop Value");
        sliderBox.getChildren().addAll(startField, stopField);

        // Style for the widget
        widget.setStyle(
                "-fx-background-color: #E9F5DB;" +          // Pale green background
                        "-fx-border-color: #2A9D8F;" +             // Greenish-blue border
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;"
        );

        ap.getChildren().add(widget);
    }

    @Override
    public AudioComponent getComponent() {
        int start = Integer.parseInt(this.startField.getText());
        int stop = Integer.parseInt(this.stopField.getText());
        return new LinearRamp(start, stop);
    }
}

