package com.example.synthesizeapplication;

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
    // Variables to track mouse and widget position during dragging
    double _mouseStartDragX, _mouseStartDragY, _widgetStartDragX, _widgetStartDragY;
    // Reference to the parent AnchorPane
    private final AnchorPane _parent;
    // StackPane for the left output connection point
    public static StackPane leftOutput = new StackPane();
    // Boolean indicating if the component is connected
    public boolean _isConnected;

    // Line representing the connection
    private Line _line;
    // Slider to control the filter value
    private final Slider _soundSlider = new Slider();

    // Constructor to initialize the FiltersComponentWidget with a name and parent AnchorPane
    FiltersComponentWidget(String name, AnchorPane parent) {
        this._parent = parent;
        HBox _baseLayout = new HBox();
        _baseLayout.setStyle("-fx-background-color: lightgreen; -fx-border-color: yellow; -fx-border-width: 2");

        // Left side: contains the output connection points in a VBox
        VBox leftSide = new VBox();
        Circle leftCircleFirst = new Circle(10);
        Circle leftCircleSecond = new Circle(5);
        leftCircleFirst.setFill(Color.YELLOW);
        leftCircleFirst.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, -1, 1, 0.5);");
        leftCircleFirst.setStrokeWidth(2);
        leftCircleFirst.setStroke(Color.valueOf("silver"));
        leftCircleSecond.setFill(Color.BLACK);
        leftCircleFirst.setMouseTransparent(false);
        leftCircleSecond.setMouseTransparent(false);
        leftOutput.getChildren().addAll(leftCircleFirst, leftCircleSecond);
        leftSide.getChildren().add(leftOutput);
        leftSide.setAlignment(Pos.CENTER_LEFT);

        // Center side: contains the slider control in a VBox
        VBox centerSide = new VBox();
        this._soundSlider.setMax(10);
        this._soundSlider.setMin(0);
        this._soundSlider.setValue(0.5);
        Label soundTitle = new Label(name + " (" + this._soundSlider.getValue() + "Hz)");
        soundTitle.setMouseTransparent(true);
        soundTitle.setStyle("-fx-font-family: Monocraft");
        // Update the label when the slider is dragged or pressed
        this._soundSlider.setOnMouseDragged((e) -> soundTitle.setText(name + " (" + Math.round(this._soundSlider.getValue() * 100.0) / 100.0 + "Hz)"));
        this._soundSlider.setOnMousePressed((e) -> soundTitle.setText(name + " (" + Math.round(this._soundSlider.getValue() * 100.0) / 100.0 + "Hz)"));
        centerSide.getChildren().add(soundTitle);
        centerSide.getChildren().add(this._soundSlider);
        centerSide.setAlignment(Pos.CENTER);
        centerSide.setSpacing(5);

        // Right side: contains the close button and output connection in a VBox
        VBox rightSide = new VBox();
        Button closeBtn = new Button("x");
        closeBtn.setOnMousePressed(e -> closeXBtn());
        StackPane output = new StackPane();
        Circle circleFirst = new Circle(10);
        Circle circleSecond = new Circle(5);
        circleFirst.setFill(Color.WHITE);
        circleFirst.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, -1, 1, 0.5);");
        circleFirst.setStrokeWidth(2);
        circleFirst.setStroke(Color.valueOf("silver"));
        circleSecond.setFill(Color.BLACK);
        circleFirst.setMouseTransparent(false);
        circleSecond.setMouseTransparent(false);
        output.getChildren().addAll(circleFirst, circleSecond);
        // Set event handlers for the output connection
        output.setOnMousePressed(e -> startConnection(e, output));
        output.setOnMouseDragged(this::moveConnection);
        output.setOnMouseReleased(this::endConnection);
        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(output);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);
        rightSide.setMouseTransparent(false);

        // Add left, center, and right sides to the base layout, and add to the Pane
        _baseLayout.getChildren().add(leftSide);
        _baseLayout.getChildren().add(centerSide);
        _baseLayout.getChildren().add(rightSide);
        _baseLayout.setOnMousePressed(this::handlePressed);
        _baseLayout.setOnMouseDragged(this::handleDragged);
        _baseLayout.setSpacing(5);
        this.getChildren().add(_baseLayout);
        // Randomly relocate the widget on the screen
        this.relocate((int) (Math.random() * (450 - 10 + 1) + 1), (int) (Math.random() * (500 - 10 + 1) + 1));
        _parent.getChildren().add(this);
    }

    // Start creating a connection from the output connector
    private void startConnection(MouseEvent e, StackPane output) {
        // If a line is already connected, remove it
        if (_line != null) {
            _parent.getChildren().remove(_line);
        }

        // Get the bounds for the parent and output connector
        Bounds parentBounds = _parent.getBoundsInParent();
        Bounds bounds = output.localToScene(output.getBoundsInLocal());

        // Create a new line as the connection
        _line = new Line();
        _line.setStrokeWidth(4);
        _line.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        _line.setStartY(bounds.getCenterY() - parentBounds.getMinY());
        _line.setEndX(e.getSceneX());
        _line.setEndY(e.getSceneY());
        _parent.getChildren().add(_line);

        e.consume();
    }

    // Move the connection line as the mouse is dragged
    private void moveConnection(MouseEvent e) {
        Bounds parentBounds = _parent.getBoundsInParent();
        _line.setEndX(e.getSceneX() - parentBounds.getMinX());
        _line.setEndY(e.getSceneY() - parentBounds.getMinY());
        e.consume();
    }

    // End the connection and check if it connects to a valid target
    private void endConnection(MouseEvent e) {
        StackPane speaker = SynthesizeApplication._speakerOutput;
        Bounds speakerBounds = speaker.localToScreen(speaker.getBoundsInLocal());
        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getScreenX(), 2.0) +
                Math.pow(speakerBounds.getCenterY() - e.getScreenY(), 2.0));

        // If the distance is less than 10 pixels, consider it connected
        if (distance < 10) {
            System.out.println("filter Distance less than 10: " + distance);
            System.out.println("output connected!!");
            _isConnected = true;
        } else {
            _parent.getChildren().remove(_line);
            _line = null;
        }
    }

    // Handle dragging of the widget by updating its position
    private void handleDragged(MouseEvent mouse) {
        double mouseDelX = mouse.getSceneX() - _mouseStartDragX;
        double mouseDelY = mouse.getSceneY() - _mouseStartDragY;
        this.relocate(_widgetStartDragX + mouseDelX, _widgetStartDragY + mouseDelY);
    }

    // Record the initial mouse and widget positions when dragging starts
    private void handlePressed(MouseEvent mouse) {
        _mouseStartDragX = mouse.getSceneX();
        _mouseStartDragY = mouse.getSceneY();

        _widgetStartDragX = this.getLayoutX();
        _widgetStartDragY = this.getLayoutY();
    }

    // Close button handler to remove the widget and any connected line
    private void closeXBtn() {
        _parent.getChildren().remove(_line);
        _parent.getChildren().remove(this);
        SynthesizeApplication.fwidgets.remove(this);
    }

    // Return the AudioComponent for the filter
    public AudioComponent getAudioComponent() {
        return new Filters((double) this._soundSlider.getValue());
    }
}