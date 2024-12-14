package com.example.synthesizeapplication.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AudioComponentWidgetBase extends Pane {
    double initialMouseX, initialMouseY, widgetStartX, widgetStartY;
    private final AnchorPane parentContainer;
    private final boolean isSineWaveType;

    public boolean isConnectedToOutput;
    public boolean isConnectedToFilter;
    private Line connectionLine;
    private StackPane outputConnector;
    private final Slider frequencyControlSlider = new Slider();

    public AudioComponentWidgetBase(String componentName, AnchorPane parent, boolean isSineWave) {
        this.parentContainer = parent;
        this.isSineWaveType = isSineWave;

        VBox leftSection = new VBox();
        HBox layout = new HBox();
        layout.setStyle("-fx-background-color: lightblue; -fx-border-color: darkred; -fx-border-width: 2");

        this.frequencyControlSlider.setMax(10000);
        this.frequencyControlSlider.setMin(0);
        this.frequencyControlSlider.setValue(5000);
        Label componentLabel = new Label(componentName + " (" + Math.round(this.frequencyControlSlider.getValue()) + "Hz)");
        componentLabel.setMouseTransparent(true);
        componentLabel.setStyle("-fx-font-family: Consolas");
        this.frequencyControlSlider.setOnMouseDragged(e -> componentLabel.setText(componentName + " (" + Math.round(this.frequencyControlSlider.getValue()) + "Hz)"));
        this.frequencyControlSlider.setOnMousePressed(e -> componentLabel.setText(componentName + " (" + Math.round(this.frequencyControlSlider.getValue()) + "Hz)"));
        leftSection.getChildren().addAll(componentLabel, this.frequencyControlSlider);
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setSpacing(5);

        VBox rightSection = new VBox();
        Button closeButton = new Button("Close");
        closeButton.setOnMousePressed(e -> closeWidget());
        outputConnector = new StackPane();
        Circle outerCircle = new Circle(10, Color.WHITE);
        Circle innerCircle = new Circle(5, Color.BLACK);
        outerCircle.setStrokeWidth(2);
        outerCircle.setStroke(Color.GRAY);
        outputConnector.getChildren().addAll(outerCircle, innerCircle);
        outputConnector.setOnMousePressed(this::startConnection);
        outputConnector.setOnMouseDragged(this::dragConnection);
        outputConnector.setOnMouseReleased(this::finalizeConnection);
        rightSection.getChildren().addAll(closeButton, outputConnector);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPadding(new Insets(5));
        rightSection.setSpacing(5);

        layout.getChildren().addAll(leftSection, rightSection);
        layout.setOnMousePressed(this::handleMousePressed);
        layout.setOnMouseDragged(this::handleMouseDragged);

        this.getChildren().add(layout);
        this.relocate((int) (Math.random() * 400), (int) (Math.random() * 400));
        parent.getChildren().add(this);
    }

    private void startConnection(MouseEvent event) {
        if (connectionLine != null) {
            parentContainer.getChildren().remove(connectionLine);
            isConnectedToOutput = false;
            isConnectedToFilter = false;
        }

        Bounds parentBounds = parentContainer.getBoundsInParent();
        Bounds connectorBounds = outputConnector.localToScene(outputConnector.getBoundsInLocal());

        connectionLine = new Line();
        connectionLine.setStartX(connectorBounds.getCenterX() - parentBounds.getMinX());
        connectionLine.setStartY(connectorBounds.getCenterY() - parentBounds.getMinY());
        connectionLine.setEndX(event.getSceneX());
        connectionLine.setEndY(event.getSceneY());
        connectionLine.setStrokeWidth(3);
        parentContainer.getChildren().add(connectionLine);
        event.consume();
    }

    private void dragConnection(MouseEvent event) {
        Bounds parentBounds = parentContainer.getBoundsInParent();
        connectionLine.setEndX(event.getSceneX() - parentBounds.getMinX());
        connectionLine.setEndY(event.getSceneY() - parentBounds.getMinY());
        event.consume();
    }

    private void finalizeConnection(MouseEvent event) {
        // Placeholder for connection logic with speakers or filters
    }

    private void handleMousePressed(MouseEvent event) {
        initialMouseX = event.getSceneX();
        initialMouseY = event.getSceneY();
        widgetStartX = this.getLayoutX();
        widgetStartY = this.getLayoutY();
    }

    private void handleMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - initialMouseX;
        double deltaY = event.getSceneY() - initialMouseY;
        this.relocate(widgetStartX + deltaX, widgetStartY + deltaY);
    }

    private void closeWidget() {
        parentContainer.getChildren().remove(this);
        if (connectionLine != null) {
            parentContainer.getChildren().remove(connectionLine);
        }
    }

    public AudioComponent produceAudioComponent() {
        if (this.isSineWaveType) {
            return new SineWave((int) this.frequencyControlSlider.getValue());
        } else {
            AudioComponent variableFrequencySine = new VFSineWave();
            LinearRamp ramp = new LinearRamp(0, (int) this.frequencyControlSlider.getValue());
            variableFrequencySine.attachInput(ramp);
            return variableFrequencySine;
        }
    }
}
