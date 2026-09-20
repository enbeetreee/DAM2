import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ComptadorApp extends Application {

    private long comptador = 0;
    private boolean parar = false;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        // Crear els elements de la interfície
        Text comptadorText = new Text(Long.toString(comptador));
        comptadorText.setFont(new Font("Verdana", 26));

        Button b1 = new Button("Iniciar comptador");
        Button b2 = new Button("Parar comptador");

        // Configurar l'esdeveniment per al botó "Iniciar comptador"
        b1.setOnAction(e -> {
            if (timeline == null || parar) {
                parar = false;
                timeline.play();
                b1.setText("Continuar comptador");
            }
        });

        // Configurar l'esdeveniment per al botó "Parar comptador"
        b2.setOnAction(e -> {
            parar = true;
            timeline.stop();
        });

        // Definir la línia de temps per actualitzar el comptador cada 300ms
        timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            if (!parar) {
                comptador++;
                comptadorText.setText(Long.toString(comptador));
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Crear el layout i afegir els elements
        VBox root = new VBox(10, comptadorText, b1, b2);
        root.setStyle("-fx-background-color: yellow; -fx-padding: 20px;");

        // Crear l'escena i mostrar-la
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setTitle("Comptador en JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

