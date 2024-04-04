package componentes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolutionWindow extends Stage {
    public SolutionWindow(double[][] matrix) {
        setTitle("Solución");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setSpacing(5);


        resolverGaussJordan(matrix, root);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);

        Scene scene = new Scene(scrollPane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toExternalForm());
        setScene(scene);
    }

    private void resolverGaussJordan(double[][] matriz, VBox root) {
        int filas = matriz.length;
        int columnas = matriz[0].length;

        agregarTexto(root, "Matriz inicial:");
        imprimirMatriz(matriz, root);
        agregarTexto(root, "");

        for (int i = 0; i < filas; i++) {
            agregarTexto(root, "Paso " + (i + 1) + ":");

            // Paso 1: Hacer que el elemento diagonal sea 1
            double pivote = matriz[i][i];
            if (pivote != 0) {
                agregarTexto(root, "Dividiendo la fila " + (i + 1) + " por " + pivote);
                for (int j = 0; j < columnas; j++) {
                    matriz[i][j] /= pivote;
                }
                imprimirMatriz(matriz, root);
                agregarTexto(root, "");
            } else {
                agregarTexto(root, "No se puede hacer cero el pivote en la fila " + (i + 1) + ". Pasando a la siguiente fila.");
                continue;
            }

            // Paso 2: Hacer ceros debajo del pivote
            for (int k = 0; k < filas; k++) {
                if (k != i) {
                    double factor = matriz[k][i];
                    agregarTexto(root, "Restando " + factor + " * fila " + (i + 1) + " de la fila " + (k + 1));
                    for (int j = 0; j < columnas; j++) {
                        matriz[k][j] -= factor * matriz[i][j];
                    }
                }
            }
            imprimirMatriz(matriz, root);
            agregarTexto(root, "");
        }

        // Mostrar las soluciones
        agregarTexto(root, "Soluciones:");
        for (int i = 0; i < filas; i++) {
            agregarTexto(root, "x" + (i + 1) + " = " + matriz[i][columnas - 1]);
        }
    }

    private void imprimirMatriz(double[][] matriz, VBox root) {
        for (double[] fila : matriz) {
            StringBuilder rowText = new StringBuilder();
            for (double valor : fila) {
                rowText.append(valor).append("\t");
            }
            agregarTexto(root, rowText.toString());
        }
    }

    private void agregarTexto(VBox root, String texto) {
        Label label = new Label(texto);
        root.getChildren().add(label);
    }
}