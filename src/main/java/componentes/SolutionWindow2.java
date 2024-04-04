package componentes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SolutionWindow2 extends Stage {

    public SolutionWindow2(double[][] solutionMatrix, double error) {
        setTitle("Solución utilizando Gauss-Seidel");

        // Crear un VBox para contener las etiquetas de resultados
        VBox vbox = new VBox();

        // Realizar cálculos utilizando el método de Gauss-Seidel
        int n = solutionMatrix.length;
        double[] x = new double[n]; // Inicializar x con valores iniciales (pueden ser 0, por ejemplo)
        double[] newX = new double[n]; // Guardar los nuevos valores de x
        double[] errors = new double[n]; // Guardar los errores

        // Iteraciones para resolver el sistema
        int maxIterations = 1000; // Establecer un límite de iteraciones
        double tolerance = error; // Establecer la tolerancia del error
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        sum += solutionMatrix[i][j] * newX[j];
                    }
                }
                newX[i] = (solutionMatrix[i][n] - sum) / solutionMatrix[i][i];
                errors[i] = Math.abs(newX[i] - x[i]);
            }

            // Agregar las etiquetas de resultados de esta iteración al VBox
            Label iterationLabel = new Label("Iteración " + (iteration + 1));
            vbox.getChildren().add(iterationLabel);
            for (int i = 0; i < n; i++) {
                Label label = new Label("x[" + i + "]: " + String.format("%.6f", newX[i]) + ", Error: " + String.format("%.6f", errors[i]));
                vbox.getChildren().add(label);
            }

            // Comprobar la convergencia
            boolean converged = true;
            for (int i = 0; i < n; i++) {
                if (errors[i] > tolerance) {
                    converged = false;
                    break;
                }
            }
            if (converged) {
                break;
            }

            // Actualizar los valores de x para la siguiente iteración
            System.arraycopy(newX, 0, x, 0, n);
        }

        // Agregar un espacio entre las iteraciones y el resultado final
        vbox.getChildren().add(new Label("Resultado final:"));

        // Agregar las etiquetas de resultados finales al VBox
        for (int i = 0; i < n; i++) {
            Label label = new Label("x[" + i + "]: " + String.format("%.6f", newX[i]) + ", Error: " + String.format("%.6f", errors[i]));
            vbox.getChildren().add(label);
        }

        // Crear un ScrollPane y agregar el VBox para hacer scroll
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Crear la escena y mostrarla en la ventana
        Scene scene = new Scene(scrollPane, 400, 300);
        setScene(scene);
    }
}