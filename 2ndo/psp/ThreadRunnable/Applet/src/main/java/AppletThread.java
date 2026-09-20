import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AppletThread extends Application {

    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        // Inicialitzar el fil com a línia de temps que es repeteix
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            // Tasca repetitiva: pots definir ací què farà el fil
            System.out.println("El fil està corrent.");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        // Pots utilitzar un Stage si necessites una interfície visual
        primaryStage.setTitle("Fil en JavaFX");
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Aturar el fil (línia de temps)
        timeline.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
