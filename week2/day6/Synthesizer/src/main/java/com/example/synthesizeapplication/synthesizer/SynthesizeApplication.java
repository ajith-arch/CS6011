package com.example.synthesizeapplication.synthesizer;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SynthesizeApplication extends Application {

    @Override
    public void start(Stage stage) {
        // Set up the main application layout
        BorderPane rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(10));
        Scene scene = new Scene(rootLayout, 800, 600);

        // Create a control panel for adding components
        VBox controlPanel = new VBox();
        controlPanel.setSpacing(10);
        controlPanel.setPadding(new Insets(10));
        controlPanel.setAlignment(Pos.CENTER);
        Button addSineWaveButton = new Button("Add Sine Wave");
        Button addSquareWaveButton = new Button("Add Square Wave");
        Button playButton = new Button("Play");
        controlPanel.getChildren().addAll(addSineWaveButton, addSquareWaveButton, playButton);

        // Add the control panel to the right side of the layout
        rootLayout.setRight(controlPanel);

        // Set up the main application window
        stage.setTitle("Audio Synthesizer Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}