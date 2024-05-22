package componentes;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegCuaDatos extends Stage {
    private double[][] datos;

    public RegCuaDatos(int nDatos) {
        this.datos = new double[nDatos][2]; // Crear la matriz de nDatos x 2
        inicializarUI();
    }

    private void inicializarUI() {
        VBox vbox = new VBox();

        // Crear el GridPane para los TextField
        GridPane gridPane = new GridPane();

        // Agregar etiquetas de encabezado para las columnas
        Label xLabel = new Label("X");
        Label yLabel = new Label("Y");
        gridPane.add(xLabel, 0, 0);
        gridPane.add(yLabel, 1, 0);

        // Crear los TextField para la entrada de datos
        TextField[][] textFields = new TextField[datos.length][2];
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < 2; j++) {
                textFields[i][j] = new TextField();
                textFields[i][j].setPromptText((j == 0 ? "X" : "Y") + " [" + i + "]");
                gridPane.add(textFields[i][j], j, i + 1); // Ajuste de fila para dejar espacio a las etiquetas
            }
        }

        // Botón para guardar los datos
        Button guardar = new Button("Guardar Datos");
        guardar.setOnAction(event -> guardarDatos(textFields));

        vbox.getChildren().addAll(new Label("Ingrese los datos:"), gridPane, guardar);

        Scene scene = new Scene(vbox, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toString());
        this.setTitle("Datos de Regresión Cuadrática");
        this.setScene(scene);
        this.show();
    }

    private void guardarDatos(TextField[][] textFields) {
        try {
            for (int i = 0; i < datos.length; i++) {
                for (int j = 0; j < 2; j++) {
                    String text = textFields[i][j].getText();
                    if (text == null || text.isEmpty()) {
                        throw new NumberFormatException("Campo vacío en la posición [" + i + ", " + j + "]");
                    }
                    datos[i][j] = Double.parseDouble(text);
                }
            }
            new RegresionCuadraticaSolver(datos);
            this.close();
            //mostrarAlerta(Alert.AlertType.INFORMATION, "Datos Guardados", "Los datos se han guardado correctamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Por favor, ingrese valores numéricos válidos.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setTitle(titulo);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
