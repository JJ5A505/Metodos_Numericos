package vistas;
import componentes.InterpolacionLinealSolver;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class InterpolacionLineal extends Stage {
    private final double[][] datos;

    public InterpolacionLineal() {
        this.datos = new double[2][2]; // Crear la matriz de 2x2
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
        TextField[][] textFields = new TextField[2][2]; // Matriz de 2x2
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                textFields[i][j] = new TextField();
                textFields[i][j].setPromptText((j == 0 ? "X" : "Y") + " [" + i + "]");
                gridPane.add(textFields[i][j], j, i + 1); // Ajuste de fila para dejar espacio a las etiquetas
            }
        }

        // TextField para ingresar el valor a encontrar
        Label valorLabel = new Label("Valor a encontrar:");
        TextField valorTextField = new TextField();
        valorTextField.setPromptText("Valor");
        gridPane.add(valorLabel, 0, 3);
        gridPane.add(valorTextField, 1, 3);

        // Botón para guardar los datos
        Button guardar = new Button("Guardar Datos");
        guardar.setOnAction(event -> guardarDatos(textFields, valorTextField));

        vbox.getChildren().addAll(new Label("Ingrese los datos:"), gridPane, guardar);

        Scene scene = new Scene(vbox, 300, 350); // Incrementamos la altura para dar espacio al nuevo TextField
        scene.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toString());
        this.setTitle("Interpolación Lineal");
        this.setScene(scene);
        this.show();
    }

    private void guardarDatos(TextField[][] textFields, TextField valorTextField) {
        try {
            // Verificar que el valor a encontrar esté dentro del rango de los valores de x
            double valor = Double.parseDouble(valorTextField.getText());
            double minX = Double.parseDouble(textFields[0][0].getText());
            double maxX = Double.parseDouble(textFields[1][0].getText());
            if (valor < minX || valor > maxX) {
                throw new IllegalArgumentException("El valor a encontrar debe estar dentro del rango de los valores de x.");
            }

            // Guardar los datos ingresados en la matriz
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    String text = textFields[i][j].getText();
                    if (text == null || text.isEmpty()) {
                        throw new NumberFormatException("Campo vacío en la posición [" + i + ", " + j + "]");
                    }
                    datos[i][j] = Double.parseDouble(text);
                }
            }

            // Llamar al solver con los datos y el valor a encontrar
            new InterpolacionLinealSolver(datos, valor);
           this.close();
            // mostrarAlerta(Alert.AlertType.INFORMATION, "Datos Guardados", "Los datos se han guardado correctamente.");
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Por favor, ingrese valores numéricos válidos.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", e.getMessage());
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



