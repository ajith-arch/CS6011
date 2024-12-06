package com.example.synthesizeapplication;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.nio.file.DirectoryStream;
import java.util.ArrayList;
public class SynthesizeApplication extends Application {
    // Static list to store all AudioComponentWidgetBase instances
    public static ArrayList<AudioComponentWidgetBase> widgets = new ArrayList<>();
    // Static list to store all FiltersComponentWidget instances
    public static ArrayList<FiltersComponentWidget> fwidgets = new ArrayList<>();
    // Static AnchorPane representing the main canvas
    public static AnchorPane _mainCanvas = new AnchorPane();
    // Static reference for a FiltersComponentWidget instance
    public static FiltersComponentWidget fcw;
    // Static StackPane representing the speaker output
    public static StackPane _speakerOutput;

    @Override
    public void start(Stage stage) {
        // Set the style of the main canvas and create a BorderPane for layout
        _mainCanvas.setStyle("-fx-background-color: grey");
        BorderPane root = new BorderPane();

        // Create the scene with specific dimensions and set the background color
        Scene scene = new Scene(root, 800, 600);
        root.setStyle("-fx-background-color: lightgrey");

        // Create buttons for the right side widget box
        VBox rightBox = new VBox();
        rightBox.setStyle("-fx-background-color: lightblue;");
        rightBox.setPadding(new Insets(8));
        Button sineWaveBtn = new Button("Add Sine Wave");
        Button vFSineWaveBtn = new Button("Add VFSine Wave");
        Button filterBtn = new Button("Add Filter");
        filterBtn.setStyle("-fx-font-family: Monocraft");
        vFSineWaveBtn.setStyle("-fx-font-family: 'Monocraft';");
        sineWaveBtn.setStyle("-fx-font-family: 'Monocraft';");
        // Add buttons to the right-side box
        rightBox.getChildren().addAll(sineWaveBtn, vFSineWaveBtn, filterBtn);
        rightBox.setSpacing(10);
        // Set actions for each button
        sineWaveBtn.setOnMousePressed(e -> createSoundWidget());
        vFSineWaveBtn.setOnMousePressed(e -> createVFSoundWidget());
        filterBtn.setOnMousePressed(e -> createFilterWidget());

        // Create a label for the bottom widget with application information
        Label myInformation = new Label(" Synthesizere");
        myInformation.setStyle("-fx-font-size: 8; -fx-font-family: Monocraft");
        HBox bottomBox = new HBox();
        VBox playBox = new VBox();
        Button playBtn = new Button("PLAY");
        playBtn.setStyle("-fx-font-family: 'Monocraft'; -fx-font-size: 12;");
        // Set action for play button
        playBtn.setOnMousePressed(e -> playSounds());
        // Add play button and information label to playBox
        playBox.getChildren().addAll(playBtn, myInformation);
        playBox.setAlignment(Pos.CENTER);
        playBox.setSpacing(3);
        // Configure the bottom box layout
        bottomBox.setPrefHeight(40);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().add(playBox);

        // Create the speaker output visualization
        _speakerOutput = new StackPane();
        Circle circleFirst = new Circle(20);
        Circle circleSecond = new Circle(10);
        circleFirst.setFill(Color.RED);
        circleFirst.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, -1, 1, 0.5);");
        circleFirst.setStrokeWidth(2);
        circleFirst.setStroke(Color.valueOf("silver"));
        circleSecond.setFill(Color.BLACK);
        _speakerOutput.getChildren().addAll(circleFirst, circleSecond);

        // Set the position of the speaker output on the canvas
        _speakerOutput.relocate(580, 280);
        _mainCanvas.getChildren().add(_speakerOutput);

        // Create a button to close all widgets
        Button closeAllBtn = new Button("Close All");
        closeAllBtn.setStyle("-fx-font-family: Monocraft");
        closeAllBtn.setOnMousePressed(e -> closeAllBtn());

        // Add the close button to the main canvas
        _mainCanvas.getChildren().add(closeAllBtn);

        // Set the layout for the main root pane
        root.setBottom(bottomBox);
        root.setRight(rightBox);
        root.setCenter(_mainCanvas);

        // Configure the stage and show it
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        // Set the position of the close button based on main canvas dimensions
        closeAllBtn.setLayoutX(_mainCanvas.getWidth() / 2);
        closeAllBtn.setLayoutY(20);
    }

    // Method to close all widgets on the main canvas
    private void closeAllBtn() {
        int size = _mainCanvas.getChildren().size();
        // Remove all widgets except for the speaker output and close button
        if (size > 2) {
            _mainCanvas.getChildren().subList(2, size).clear();
        }
        // Clear the widget lists
        widgets.clear();
        fwidgets.clear();
    }

    // Method to play the sounds generated by the connected components
    private void playSounds() {
        try {
            // Create a Clip to play audio
            Clip c = AudioSystem.getClip();
            AudioListener listener = new AudioListener(c);
            AudioFormat format = new AudioFormat(44100, 16, 1, true, false);
            Mixer mixer = new Mixer();
            // Connect components that are marked as connected
            for (AudioComponentWidgetBase component : widgets) {
                if (component._isConnected) {
                    AudioComponent ac = component.getAudioComponent();
                    mixer.connectInput(ac);
                    System.out.println(component._isConnected + "is");
                }
                if (component._isFilterConnected) {
                    // Connect the filter component to the mixer
                    AudioComponent f1 = fcw.getAudioComponent();
                    f1.connectInput(component.getAudioComponent());
                    mixer.connectInput(f1);
                    System.out.println(component._isFilterConnected + "filter");
                }
            }

            // Get the mixed audio data and play it
            byte[] data = mixer.getClip().getData();
            c.open(format, data, 0, data.length);
            c.start();
            c.addLineListener(listener);
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to create a filter widget and add it to the main canvas
    private void createFilterWidget() {
        fcw = new FiltersComponentWidget("Filter", _mainCanvas);
        fwidgets.add(fcw);
    }

    // Method to create a variable frequency sine wave widget and add it to the main canvas
    private void createVFSoundWidget() {
        AudioComponentWidgetBase acw = new AudioComponentWidgetBase("VFSineWave", _mainCanvas, false);
        widgets.add(acw);
    }

    // Method to create a sine wave widget and add it to the main canvas
    private void createSoundWidget() {
        AudioComponentWidgetBase acw = new AudioComponentWidgetBase("SineWave", _mainCanvas, true);
        widgets.add(acw);
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch();
    }
}