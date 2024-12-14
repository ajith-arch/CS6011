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

public class FiltersComponentWidget extends Pane {
    double initialMouseX, initialMouseY, widgetStartX, widgetStartY;
    private final AnchorPane parentPane;
    public static StackPane leftConnector = new StackPane();
    public boolean isConnected;
    private Line connectionLine;
    private final Slider filterSlider = new Slider();

    public FiltersComponentWidget(String title, AnchorPane parent) {
        this.parentPane = parent;

        HBox layout = new HBox();
        layout.setStyle("-fx-background-color: lightgreen; -fx-border-color: darkblue; -fx-border-width: 2");

        VBox leftSection = new VBox();
        Circle outerCircle = new Circle(10, Color.YELLOW);
        Circle innerCircle = new Circle(5, Color.BLACK);
        outerCircle.setStrokeWidth(2);
        outerCircle.setStroke(Color.SILVER);
        leftConnector.getChildren().addAll(outerCircle, innerCircle);
        leftSection.getChildren().add(leftConnector);
        leftSection.setAlignment(Pos.CENTER_LEFT);

        VBox centerSection = new VBox();
        filterSlider.setMax(10);
        filterSlider.setMin(0);
        filterSlider.setValue(1.0);
        Label filterLabel = new Label(title + " (" + filterSlider.getValue() + ")");
        filterSlider.setOnMouseDragged(e -> filterLabel.setText(title + " (" + String.format("%.2f", filterSlider.getValue()) + ")"));
        filterSlider.setOnMousePressed(e -> filterLabel.setText(title + " (" + String.format("%.2f", filterSlider.getValue()) + ")"));
        centerSection.getChildren().addAll(filterLabel, filterSlider);
        centerSection.setAlignment(Pos.CENTER);
        centerSection.setSpacing(5);

        VBox rightSection = new VBox();
        Button closeButton = new Button("Close");
        closeButton.setOnMousePressed(e -> removeWidget());
        StackPane outputConnector = new StackPane();
        Circle outputOuterCircle = new Circle(10, Color.WHITE);
        Circle outputInnerCircle = new Circle(5, Color.BLACK);
        outputOuterCircle.setStrokeWidth(2);
        outputOuterCircle.setStroke(Color.GRAY);
        outputConnector.getChildren().addAll(outputOuterCircle, outputInnerCircle);
        outputConnector.setOnMousePressed(this::initiateConnection);
        outputConnector.setOnMouseDragged(this::dragConnection);
        outputConnector.setOnMouseReleased(this::finalizeConnection);
        rightSection.getChildren().addAll(closeButton, outputConnector);
        rightSection.setAlignment(Pos.CENTER);
        rightSection.setPadding(new Insets(5));
        rightSection.setSpacing(5);

        layout.getChildren().addAll(leftSection, centerSection, rightSection);
        layout.setOnMousePressed(this::handleMousePressed);
        layout.setOnMouseDragged(this::handleMouseDragged);

        this.getChildren().add(layout);
        this.relocate((int) (Math.random() * 400), (int) (Math.random() * 400));
        parent.getChildren().add(this);
    }

    private void initiateConnection(MouseEvent event) {
        if (connectionLine != null) {
            parentPane.getChildren().remove(connectionLine);
        }
        Bounds parentBounds = parentPane.getBoundsInParent();
        Bounds connectorBounds = leftConnector.localToScene(leftConnector.getBoundsInLocal());
        connectionLine = new Line();
        connectionLine.setStartX(connectorBounds.getCenterX() - parentBounds.getMinX());
        connectionLine.setStartY(connectorBounds.getCenterY() - parentBounds.getMinY());
        connectionLine.setEndX(event.getSceneX());
        connectionLine.setEndY(event.getSceneY());
        connectionLine.setStrokeWidth(3);
        parentPane.getChildren().add(connectionLine);
        event.consume();
    }

    private void dragConnection(MouseEvent event) {
        Bounds parentBounds = parentPane.getBoundsInParent();
        connectionLine.setEndX(event.getSceneX() - parentBounds.getMinX());
        connectionLine.setEndY(event.getSceneY() - parentBounds.getMinY());
        event.consume();
    }

    private void finalizeConnection(MouseEvent event) {
        // Connection finalization logic placeholder
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

    private void removeWidget() {
        parentPane.getChildren().remove(this);
        if (connectionLine != null) {
            parentPane.getChildren().remove(connectionLine);
        }
    }

    public AudioComponent produceAudioComponent() {
        return new Filters(filterSlider.getValue());
    }
}
