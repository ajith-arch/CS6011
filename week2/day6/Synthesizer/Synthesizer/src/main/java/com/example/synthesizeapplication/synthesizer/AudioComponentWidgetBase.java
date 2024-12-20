package com.example.synthesizeapplication.synthesizer;


import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javax.sound.sampled.LineUnavailableException;

public class AudioComponentWidgetBase {

    protected HBox titleBox;
    protected HBox widget;
    protected HBox sliderBox;
    protected AudioComponent component;
    protected SynthesizeApplication app;
    private double initialX;
    private double initialY;
    private double mouseX;
    private double mouseY;
    protected boolean isConnected = false;
    protected boolean isVolumeAdjuster = false;
    protected Line connectedLine;
    protected Button connectButton;
    protected Circle inputCircle;
    protected Circle outputCircle;
    private boolean isDraggingOutputCircle = false;
    private Pane ap;
    private AudioComponentWidgetBase connectedWidget;
    private boolean isConnectedToSpeaker = false;

    public AudioComponentWidgetBase(Pane ap, String type, SynthesizeApplication app, boolean hasInput){
        this.app = app;
        this.ap = ap;
        HBox hBox = new HBox();
        hBox.setStyle("-fx-background-color: #abacf7");
        hBox.setPadding(new Insets(5));

        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-border-color: black");
        vBox1.setPadding(new Insets(5));
        vBox1.setSpacing(5);
        HBox hBox1_1 = new HBox();
        HBox hBox1_2 = new HBox();

        this.titleBox = hBox1_1;
        this.sliderBox = hBox1_2;
        //add input circle if needed,
        if(hasInput){
            inputCircle = new Circle(10);
            inputCircle.setStyle("-fx-background-color: GREEN");
            this.sliderBox.getChildren().add(inputCircle);
        }

        vBox1.getChildren().add(hBox1_1);
        vBox1.getChildren().add(hBox1_2);

        VBox vBox2 = new VBox();
        vBox2.setPadding(new Insets(5));
        vBox2.setSpacing(5);
        HBox hBox2_1 = new HBox();
        Button closeButton = new Button("X");
//        closeButton.setOnAction(e->app.removeWidget(this));
        closeButton.setOnAction(e->closeWidget());
        hBox2_1.getChildren().add(closeButton);
        HBox hBox2_2 = new HBox();

        //setup circle for output
        this.outputCircle = new Circle(10);
        hBox2_2.getChildren().add(this.outputCircle);
        //when mouse clicks on output circle, get mouse x,y and print if circle contains mouse x,y
        // Initialize line
//        this.connectedLine = new Line();
//        this.connectedLine.setStroke(Color.RED); // Set the line color
//        ap.getChildren().add(this.connectedLine); // Add the line to the Pane
//        connectedLine.toFront();

        this.outputCircle.setOnMousePressed(e -> {

//            if(this.connectedLine != null){
//                connectedLine = null;
//                ap.getChildren().remove(connectedLine);
//                this.isConnected = false;
//                this.connectedWidget.isConnected = false;
//                if(this.isConnectedToSpeaker){
//                    this.isConnectedToSpeaker = false;
//                    app.toggleSpeaker();
//                } else if(this.connectedWidget.isConnectedToSpeaker){
//                    this.connectedWidget.isConnectedToSpeaker = false;
//                    connectedWidget.connectedLine = null;
//                    ap.getChildren().remove(connectedWidget.connectedLine);
//                    app.toggleSpeaker();
//                }
//
//            } else {

            this.connectedLine = new Line();
            this.connectedLine.setStroke(Color.RED);
            ap.getChildren().add(this.connectedLine); // Add the line to the Pane
            connectedLine.toFront();
            this.isDraggingOutputCircle = true;
            // Set the start point of the line to the center of the output circle
            double circleCenterX = this.outputCircle.localToScene(this.outputCircle.getCenterX(), this.outputCircle.getCenterY()).getX();
            double circleCenterY = this.outputCircle.localToScene(this.outputCircle.getCenterX(), this.outputCircle.getCenterY()).getY();

            System.out.println(circleCenterX);
            System.out.println(e.getSceneX());
            connectedLine.setStartX(circleCenterX);
            connectedLine.setStartY(circleCenterY);
            connectedLine.setEndX(circleCenterX); // Initial end point is the same as the start point
            connectedLine.setEndY(circleCenterY); // Initial end point is the same as the start point
            connectedLine.toFront();
//            }
        });

        this.outputCircle.setOnMouseDragged(e -> {
            // Update the end point of the line to follow the mouse
            connectedLine.setEndX(e.getSceneX());
            connectedLine.setEndY(e.getSceneY());
            connectedLine.toFront();

        });

        this.outputCircle.setOnMouseReleased(e-> {
            connectedLine.toFront();
            this.isDraggingOutputCircle = false;
            boolean isConnectedToAnyInput = false;
            // Get the end point coordinates of the line in scene coordinates
            double endX = e.getSceneX();
            double endY = e.getSceneY();
            // Update the end point of the line
            connectedLine.setEndX(endX);
            connectedLine.setEndY(endY);

            for(AudioComponentWidgetBase otherWidget: app.widgets){

                if(otherWidget.inputCircle != null){
                    double localEndX = otherWidget.inputCircle.sceneToLocal(endX, endY).getX();
                    double localEndY = otherWidget.inputCircle.sceneToLocal(endX, endY).getY();

                    if (otherWidget.inputCircle.contains(localEndX, localEndY)) {
                        System.out.println("Connected to input of another widget.");
                        this.connectedWidget = otherWidget;
                        this.isConnected = true;
                        otherWidget.isConnected = true;
                        isConnectedToAnyInput = true; // Mark as connected
                        break; // No need to check other widgets
                    }
                }

            }

            //check if connected to speaker button
            //if so, set speakerOn in app to true and isConnectedToAnyInput to true here
            double localEndX = app.speakerButton.sceneToLocal(endX, endY).getX();
            double localEndY = app.speakerButton.sceneToLocal(endX, endY).getY();
            if(app.speakerButton.contains(localEndX, localEndY)){
                System.out.println("Speaker connected");
                this.isConnectedToSpeaker = true;
                this.isConnected = true;
                isConnectedToAnyInput = true;
                app.speakerOn = true;
                app.speakerButton.setFill(Color.LIGHTGREEN);
            }

            // If not connected to any input, remove the connected line
            if (!isConnectedToAnyInput) {
                ap.getChildren().remove(connectedLine);
                connectedLine = null; // Reset the connected line
            }

        });

        vBox2.getChildren().add(hBox2_1);
        vBox2.getChildren().add(hBox2_2);

        hBox.getChildren().add(vBox1);
        hBox.getChildren().add(vBox2);

//        AudioComponentWidgetBase widget = new SineWaveWidget(ap, "Sine wave", hBox);
        this.widget = hBox;
        setupDragEvents(hBox);
//        ap.getChildren().add(widget.hBox);
    }

    public void setupDragEvents(HBox widget){
        widget.setOnMousePressed(e->{
            initialX = widget.getLayoutX();
            initialY = widget.getLayoutY();
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        widget.setOnMouseDragged(e->{
            if(isDraggingOutputCircle){
                return;
            } else {
                double offsetX = e.getSceneX() - mouseX;
                double offsetY = e.getSceneY() - mouseY;
                widget.setLayoutX(initialX + offsetX);
                widget.setLayoutY(initialY + offsetY);

            }
        });
    }

    public AudioComponent getComponent() {
        return component;
    }

    private double getConnectButtonCenterX(){
        return widget.getLayoutX() + connectButton.getLayoutX() + connectButton.getWidth() /2;
    }

    private double getConnectButtonCenterY(){
        return widget.getLayoutY() + connectButton.getLayoutY() + connectButton.getHeight() /2;
    }

    private double calculateDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void closeWidget(){
        app.removeWidget(this);
        // Check if connectedLine exists before trying to remove it
        if (connectedLine != null) {
            ap.getChildren().remove(connectedLine);
            connectedLine = null; // Reset the connected line
        }
    }


}