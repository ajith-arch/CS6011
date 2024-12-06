import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello!");

        BorderPane bp = new BorderPane();

        HBox hbox = new HBox();
        bp.setCenter(hbox);

        Button btn = new Button("Press Me");
        btn.setOnAction(e -> System.out.println("Button Pressed!"));

        Slider slider = new Slider(0.0, 1.0, 0.5); // Initial value: 0.5
        Text text = new Text("Value: 0.5");

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            text.setText("Value: " + String.format("%.2f", newValue.doubleValue()));
        });

        hbox.getChildren().addAll(btn, slider, text);

        Scene scene = new Scene(bp, 400, 240);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
