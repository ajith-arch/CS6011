package com.example.synthesizeapplication.synthesizer;

import java.util.Iterator;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class trailAudioWidget extends Pane{

    protected String name;
    protected AudioComponent audioComponent;
    protected Circle output;
    protected Pane parent;
    protected BorderPane widgetOuterBox;
    protected boolean outputPressed;
    protected double localMouseX;
    protected double localMouseY;

    trailAudioWidget(Pane parent, String name) {
        this.output = new Circle(10.0, Color.DARKMAGENTA);
        this.outputPressed = false;
        this.parent = parent;
        this.name = name;
        HBox topBorder = new HBox();
        Label widgetName = new Label(name);
        widgetName.setPadding(new Insets(4.0));
        widgetName.setUnderline(true);
        widgetName.setFont(Font.font("Chalkboard", FontWeight.BOLD, 20.0));
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button closeButton = new Button("X");
        closeButton.setOnAction((event) -> {
//            this.removeWidget();
        });
        topBorder.getChildren().addAll(new Node[]{widgetName, spacer, closeButton});
        this.widgetOuterBox = new BorderPane();
        this.widgetOuterBox.setStyle("-fx-background-color: lavender;-fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5; ");
        this.widgetOuterBox.setPadding(new Insets(3.0, 3.0, 3.0, 3.0));
        this.widgetOuterBox.setTop(topBorder);
        this.widgetOuterBox.setRight(this.output);
        this.getChildren().add(this.widgetOuterBox);
        this.parent.getChildren().add(this.widgetOuterBox);

//        this.setOnMousePressed((event) -> {
//            this.handleMousePressed(event);
//        });
//        this.setOnMouseDragged((event) -> {
//            this.handleMouseDragged(event);
//        });
    }


}