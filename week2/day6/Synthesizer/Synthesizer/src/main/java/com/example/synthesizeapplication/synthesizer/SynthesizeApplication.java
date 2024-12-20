package com.example.synthesizeapplication.synthesizer;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.*;

import javax.sound.sampled.*;
//import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SynthesizeApplication extends Application {
    private Clip c;
    private AnchorPane ap;
    ArrayList<AudioComponentWidgetBase> widgets = new ArrayList<>();
    private double currentY = 10;
    public boolean speakerOn = false;
    public Circle speakerButton;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            c = new PlaySounds().getClip();

            BorderPane bp = new BorderPane();
            bp.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            ap = new AnchorPane();
            ap.setStyle("-fx-background-color: #D3D3D3");

            this.speakerButton = new Circle(30);
            this.speakerButton.setFill(Color.RED);
            this.speakerButton.setStroke(Color.BLACK);
            AnchorPane.setRightAnchor(speakerButton, 20.0);
            AnchorPane.setBottomAnchor(speakerButton, 20.0);
            this.speakerButton.setOnMouseClicked(e -> this.toggleSpeaker());
            ap.getChildren().add(speakerButton);

            // Left Menu for Buttons
            VBox leftMenu = new VBox();
            leftMenu.setStyle("-fx-border-color: black");
            leftMenu.setPadding(new Insets(10));
            leftMenu.setSpacing(15); // Add spacing between buttons (15 pixels)

            Button btn1 = new Button("Sine wave");
            btn1.setOnAction(e -> createWidget(btn1.getText()));
            Button btn2 = new Button("Square wave");
            btn2.setOnAction(e -> createWidget(btn2.getText()));
            Button btn3 = new Button("White noise");
            btn3.setOnAction(e -> createWidget(btn3.getText()));
            Button btn4 = new Button("Linear ramp");
            btn4.setOnAction(e -> createWidget(btn4.getText()));
            Button btn5 = new Button("Volume adjuster");
            btn5.setOnAction(e -> createWidget(btn5.getText()));

            leftMenu.getChildren().addAll(btn1, btn2, btn3, btn4, btn5);

            // Bottom Box for Play Button
            HBox bottomBox = new HBox();
            bottomBox.setStyle("-fx-border-color: black");
            bottomBox.setMinHeight(30);
            bottomBox.setAlignment(Pos.CENTER);

            Button playButton = new Button("Play");
            playButton.setOnAction(e -> {
                try {
                    playMixer();
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            });

            bottomBox.getChildren().add(playButton);

            // Set Layout
            bp.setLeft(leftMenu); // Buttons on the left
            bp.setBottom(bottomBox);
            bp.setCenter(ap);

            Scene scene = new Scene(bp, 800, 600);
            stage.setScene(scene);
            stage.setTitle("Synthesizer");
            stage.show();
        } catch (LineUnavailableException e) {
            System.out.println("Audio system error");
            e.printStackTrace();
        }
    }

    public void toggleSpeaker() {
        if (this.speakerOn) {
            this.speakerOn = false;
            this.speakerButton.setFill(Color.RED);
        } else {
            this.speakerOn = true;
            this.speakerButton.setFill(Color.LIGHTGREEN);
        }
    }

    public void createWidget(String buttonText) {
        if (buttonText.equals("Sine wave")) {
            AudioComponentWidgetBase widget = new SineWaveWidget(ap, buttonText, this, false);
            widgets.add(widget);
            widget.widget.setLayoutX(10);
            widget.widget.setLayoutY(currentY);
            currentY += 8 * (widget.widget.getHeight() + 10);
        } else if (buttonText.equals("Square wave")) {
            AudioComponentWidgetBase widget = new SquareWaveWidget(ap, buttonText, this, false);
            widgets.add(widget);
            widget.widget.setLayoutX(10);
            widget.widget.setLayoutY(currentY);
            currentY += 8 * (widget.widget.getHeight() + 10);
        } else if (buttonText.equals("White noise")) {
            AudioComponentWidgetBase widget = new WhiteNoiseWidget(ap, buttonText, this, false);
            widgets.add(widget);
            widget.widget.setLayoutX(10);
            widget.widget.setLayoutY(currentY);
            currentY += 8 * (widget.widget.getHeight() + 10);
        } else if (buttonText.equals("Linear ramp")) {
            System.out.println("Linear ramp button");
            AudioComponentWidgetBase widget = new LinearRampWidget(ap, buttonText, this, false);
            widgets.add(widget);
            widget.widget.setLayoutX(10);
            widget.widget.setLayoutY(currentY);
            currentY += 8 * (widget.widget.getHeight() + 10);
        } else if (buttonText.equals("Volume adjuster")) {
            System.out.println("Vol adjuster button");
            AudioComponentWidgetBase widget = new VolumeAdjusterWidget(ap, buttonText, this, true);
            widgets.add(widget);
            widget.widget.setLayoutX(10);
            widget.widget.setLayoutY(currentY);
            currentY += 8 * (widget.widget.getHeight() + 10);
        }
    }

    public void playMixer() throws LineUnavailableException {
        System.out.println("play button clicked");
        boolean volumeExists = false;
        int volumeWidgetIndex = 0;
        AudioComponent mixer = new Mixers();

        if (!widgets.isEmpty() && this.speakerOn) {
            AudioComponent volumeAdjuster = null;

            for (AudioComponentWidgetBase widget : widgets) {
                if (widget.isConnected) {
                    if (widget.isVolumeAdjuster) {
                        volumeAdjuster = widget.getComponent();
                    } else {
                        mixer.connectInput(widget.getComponent());
                    }
                }
            }

            if (volumeAdjuster != null) {
                volumeAdjuster.connectInput(mixer);
            }
            AudioClip clip = (volumeAdjuster != null) ? volumeAdjuster.getClip() : mixer.getClip();

            c.open(c.getFormat(), clip.getData(), 0, clip.getData().length);
            System.out.println("About to play...");

            c.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    System.out.println("Playback finished.");
                    c.close();
                }
            });
            c.start();
        }
    }

    public void removeWidget(AudioComponentWidgetBase widget) {
        System.out.println("close widget");
        widgets.remove(widget);
        Node widgetNode = widget.widget;
        ap.getChildren().remove(widgetNode);
        if (widgets.isEmpty()) {
            this.toggleSpeaker();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
