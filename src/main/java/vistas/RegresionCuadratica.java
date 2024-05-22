package vistas;

import componentes.RegCuaDatos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class RegresionCuadratica extends Stage {
    private Scene scene;
    private VBox vbox;
    private TextField nDatos;
    private Button aceptar;

    public RegresionCuadratica() {
        CrearUI();
        scene = new Scene(vbox, 400, 200);
        scene.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toString());
        this.setTitle("Selecciona el número de datos que tengas");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        vbox = new VBox();

        // Crear el TextField que solo acepta números
        nDatos = new TextField();
        nDatos.setPromptText("Número de datos");

        // Restricción para solo aceptar números
        Pattern pattern = Pattern.compile("\\d*");
        TextFormatter<String> formatter = new TextFormatter<>(change -> {
            if (pattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        });
        nDatos.setTextFormatter(formatter);

        // Crear el botón
        aceptar = new Button("Aceptar");
        aceptar.setOnAction(actionEvent -> {
            if (nDatos.getText().isEmpty() | nDatos.getText().contains("0")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Por favor, ingrese un número válido de datos.");
                alert.showAndWait();
            } else {
                try {
                    int numDatos = Integer.parseInt(nDatos.getText());
                    new RegCuaDatos(numDatos);
                    this.close();
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Por favor, ingrese un número válido.");
                    alert.showAndWait();
                }
            }
        });

        // Agregar el TextField y el botón al VBox
        vbox.getChildren().addAll(nDatos, aceptar);
    }

}

