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

public class AudioComponentWidgetBase extends Pane {
    // Variables to track mouse and widget position during dragging
    double _mouseStartDragX, _mouseStartDragY, _widgetStartDragX, _widgetStartDragY;
    // Reference to the parent AnchorPane
    private final AnchorPane _parent;
    // Boolean indicating if this is a sine wave component
    private final boolean _isSineWave;

    // Boolean indicating if the widget is connected
    public boolean _isConnected;
    // Boolean indicating if the filter is connected
    public boolean _isFilterConnected;
    // Line representing the connection
    private Line _line;
    // StackPane representing the output connector
    private StackPane _output;
    // Slider to control the sound frequency
    private final Slider _soundSlider = new Slider();

    // Constructor initializes the widget with specified parameters
    AudioComponentWidgetBase(String name, AnchorPane parent, boolean isSineWave) {
        this._parent = parent;
        this._isSineWave = isSineWave;

        // Create the left side of the widget containing the slider and label
        VBox leftSide = new VBox();
        HBox _baseLayout = new HBox();
        _baseLayout.setStyle("-fx-background-color: lightblue; -fx-border-color: red; -fx-border-width: 2");

        // Configure the sound slider
        this._soundSlider.setMax(10000);
        this._soundSlider.setMin(0);
        this._soundSlider.setValue(5000);
        Label soundTitle = new Label(name + " (" + Math.round(this._soundSlider.getValue()) + "Hz)");
        soundTitle.setMouseTransparent(true);
        soundTitle.setStyle("-fx-font-family: Monocraft");
        // Update the label as the slider is adjusted
        this._soundSlider.setOnMouseDragged((e) -> soundTitle.setText(name + " (" + Math.round(this._soundSlider.getValue()) + "Hz)"));
        this._soundSlider.setOnMousePressed((e) -> soundTitle.setText(name + " (" + Math.round(this._soundSlider.getValue()) + "Hz)"));
        leftSide.getChildren().add(soundTitle);
        leftSide.getChildren().add(this._soundSlider);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setSpacing(5);

        // Create the right side of the widget containing the close button and output connector (inside component)
        VBox rightSide = new VBox();
        Button closeBtn = new Button("x");
        closeBtn.setOnMousePressed(e -> closeXBtn());
        _output = new StackPane();
        Circle circleFirst = new Circle(10);
        Circle circleSecond = new Circle(5);
        circleFirst.setFill(Color.WHITE);
        circleFirst.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 5, -1, 1, 0.5);");
        circleFirst.setStrokeWidth(2);
        circleFirst.setStroke(Color.valueOf("silver"));
        circleSecond.setFill(Color.BLACK);
        circleFirst.setMouseTransparent(false);
        circleSecond.setMouseTransparent(false);
        _output.setMouseTransparent(false);
        _output.getChildren().addAll(circleFirst, circleSecond);
        // Event handlers for connecting to other components
        _output.setOnMousePressed(e -> startConnection(e));
        _output.setOnMouseDragged(this::moveConnection);
        _output.setOnMouseReleased(this::endConnection);

        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(_output);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);
        rightSide.setMouseTransparent(false);

        // Add left and right sides to the base layout and add to this Pane
        _baseLayout.getChildren().add(leftSide);
        _baseLayout.getChildren().add(rightSide);
        _baseLayout.setOnMousePressed(this::handlePressed);
        _baseLayout.setOnMouseDragged(this::handleDragged);

        this.getChildren().add(_baseLayout);
        // Randomly relocate the widget on the screen
        this.relocate((int) (Math.random() * (450 - 10 + 1) + 1), (int) (Math.random() * (500 - 10 + 1) + 1));
        _parent.getChildren().add(this);
    }

    // Starts a connection from this widget's output connector
    private void startConnection(MouseEvent e) {
        // Remove the existing line if already connected
        if (_line != null) {
            _parent.getChildren().remove(_line);
            _isConnected = false;
            _isFilterConnected = false;
        }

        // Get the bounds for the parent and the output connector
        Bounds parentBounds = _parent.getBoundsInParent();
        Bounds bounds = _output.localToScene(_output.getBoundsInLocal());

        // Create a new line for the connection
        _line = new Line();
        _line.setStrokeWidth(4);
        _line.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        _line.setStartY(bounds.getCenterY() - parentBounds.getMinY());
        _line.setEndX(e.getSceneX());
        _line.setEndY(e.getSceneY());
        _parent.getChildren().add(_line);

        e.consume();
    }

    // Moves the connection line as the mouse is dragged
    private void moveConnection(MouseEvent e) {
        Bounds parentBounds = _parent.getBoundsInParent();
        _line.setEndX(e.getSceneX() - parentBounds.getMinX());
        _line.setEndY(e.getSceneY() - parentBounds.getMinY());
        e.consume();
    }

    // Ends the connection and checks if it connects to a valid target
    private void endConnection(MouseEvent e) {
        StackPane speaker = SynthesizeApplication._speakerOutput;
        StackPane leftSpeaker = FiltersComponentWidget.leftOutput;
        Bounds leftSpeakerBounds = leftSpeaker.localToScreen(leftSpeaker.getBoundsInLocal());

        // Check if connection is made to a filter output
        if (SynthesizeApplication.fwidgets.size() > 0) {
            double leftSpeakerDistance = Math.sqrt(Math.pow(leftSpeakerBounds.getCenterX() - e.getScreenX(), 2.0) +
                    Math.pow(leftSpeakerBounds.getCenterY() - e.getScreenY(), 2.0));
            if (leftSpeakerDistance < 10) {
                System.out.println("filter connected!!");
                _isFilterConnected = true;
            }
        } else {
            // Check if connection is made to the speaker output
            Bounds speakerBounds = speaker.localToScreen(speaker.getBoundsInLocal());
            double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getScreenX(), 2.0) +
                    Math.pow(speakerBounds.getCenterY() - e.getScreenY(), 2.0));
            if (distance < 10) {
                System.out.println("Distance less than 10: " + distance);
                System.out.println("output Included!!");
                _isConnected = true;
            } else {
                // Remove the line if no valid connection is made
                _parent.getChildren().remove(_line);
                _line = null;
            }
        }
    }

    // Handles widget dragging by updating the position
    private void handleDragged(MouseEvent mouse) {
        double mouseDelX = mouse.getSceneX() - _mouseStartDragX;
        double mouseDelY = mouse.getSceneY() - _mouseStartDragY;
        this.relocate(_widgetStartDragX + mouseDelX, _widgetStartDragY + mouseDelY);
        Bounds parentBounds = _parent.getBoundsInParent();
        Bounds bounds = _output.localToScene(_output.getBoundsInLocal());
        _line.setStartX(bounds.getCenterX() - parentBounds.getMinX());
        _line.setStartY(bounds.getCenterY() - parentBounds.getMinY());
    }

    // Records initial mouse and widget positions when dragging starts
    private void handlePressed(MouseEvent mouse) {
        _mouseStartDragX = mouse.getSceneX();
        _mouseStartDragY = mouse.getSceneY();
        _widgetStartDragX = this.getLayoutX();
        _widgetStartDragY = this.getLayoutY();
    }

    // Closes the widget by removing it and any connected line
    private void closeXBtn() {
        _parent.getChildren().remove(_line);
        _parent.getChildren().remove(this);
        SynthesizeApplication.widgets.remove(this);
    }

    // Returns an AudioComponent based on whether this is a sine wave or not
    public AudioComponent getAudioComponent() {
        if (this._isSineWave) {
            return new SineWave((int) this._soundSlider.getValue());
        } else {
            AudioComponent vFSineWave = new VFSineWave();
            LinearRamp linear = new LinearRamp(0, (int) this._soundSlider.getValue());
            vFSineWave.connectInput(linear);
            return vFSineWave;
        }
    }
}