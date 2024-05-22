package componentes;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegresionCuadraticaSolver extends Stage {
    private double[][] datos;

    public RegresionCuadraticaSolver(double[][] datos) {
        this.datos = datos;
        double[] coeficientes = resolverRegresionCuadratica(datos);
        mostrarResultados(coeficientes);
    }

    private double[] resolverRegresionCuadratica(double[][] datos) {
        int n = datos.length;

        // Matrices para el sistema normal
        double sumX = 0, sumX2 = 0, sumX3 = 0, sumX4 = 0;
        double sumY = 0, sumXY = 0, sumX2Y = 0;

        for (int i = 0; i < n; i++) {
            double x = datos[i][0];
            double y = datos[i][1];
            double x2 = x * x;
            double x3 = x2 * x;
            double x4 = x3 * x;

            sumX += x;
            sumX2 += x2;
            sumX3 += x3;
            sumX4 += x4;
            sumY += y;
            sumXY += x * y;
            sumX2Y += x2 * y;
        }

        double[][] A = {
                {n, sumX, sumX2},
                {sumX, sumX2, sumX3},
                {sumX2, sumX3, sumX4}
        };

        double[] B = {sumY, sumXY, sumX2Y};

        return resolverSistemaLineal(A, B);
    }

    private double[] resolverSistemaLineal(double[][] A, double[] B) {
        int n = B.length;

        for (int i = 0; i < n; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[max][i])) {
                    max = j;
                }
            }

            double[] temp = A[i];
            A[i] = A[max];
            A[max] = temp;

            double t = B[i];
            B[i] = B[max];
            B[max] = t;

            for (int j = i + 1; j < n; j++) {
                double factor = A[j][i] / A[i][i];
                B[j] -= factor * B[i];
                for (int k = i; k < n; k++) {
                    A[j][k] -= factor * A[i][k];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (B[i] - sum) / A[i][i];
        }
        return x;
    }

    private void mostrarResultados(double[] coeficientes) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(15); // Añadir espacio horizontal entre columnas
        gridPane.setVgap(10); // Añadir espacio vertical entre filas

        // Adding headers
        String[] headers = {"X", "Y", "X²", "XY", "X³", "X²Y", "X⁴", "St", "Sr"};
        for (int j = 0; j < headers.length; j++) {
            Label headerLabel = new Label(headers[j]);
            headerLabel.setStyle("-fx-padding: 0 30 0 0;"); // Aumentar padding
            gridPane.add(headerLabel, j, 0);
        }

        double sumX = 0, sumY = 0, sumX2 = 0, sumXY = 0, sumX3 = 0, sumX2Y = 0, sumX4 = 0, sumSt = 0, sumSr = 0;

        for (int i = 0; i < datos.length; i++) {
            double x = datos[i][0];
            double y = datos[i][1];
            double x2 = x * x;
            double xy = x * y;
            double x3 = x2 * x;
            double x2y = x2 * y;
            double x4 = x3 * x;
            double yEst = coeficientes[2] * x2 + coeficientes[1] * x + coeficientes[0];

            double st = (y - (sumY / datos.length)) * (y - (sumY / datos.length));
            double sr = (y - yEst) * (y - yEst);

            sumX += x;
            sumY += y;
            sumX2 += x2;
            sumXY += xy;
            sumX3 += x3;
            sumX2Y += x2y;
            sumX4 += x4;
            sumSt += st;
            sumSr += sr;

            gridPane.add(new Label(String.format("%.2f", x)), 0, i + 1);
            gridPane.add(new Label(String.format("%.2f", y)), 1, i + 1);
            gridPane.add(new Label(String.format("%.2f", x2)), 2, i + 1);
            gridPane.add(new Label(String.format("%.2f", xy)), 3, i + 1);
            gridPane.add(new Label(String.format("%.2f", x3)), 4, i + 1);
            gridPane.add(new Label(String.format("%.2f", x2y)), 5, i + 1);
            gridPane.add(new Label(String.format("%.2f", x4)), 6, i + 1);
            gridPane.add(new Label(String.format("%.2f", st)), 7, i + 1);
            gridPane.add(new Label(String.format("%.2f", sr)), 8, i + 1);
        }

        // Adding sum row
        gridPane.add(new Label("Σ"), 0, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumX)), 0, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumY)), 1, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumX2)), 2, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumXY)), 3, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumX3)), 4, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumX2Y)), 5, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumX4)), 6, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumSt)), 7, datos.length + 1);
        gridPane.add(new Label(String.format("%.2f", sumSr)), 8, datos.length + 1);

        // Calculate and display mean of Y, R^2, r, and equation
        double meanY = sumY / datos.length;
        double r2 = 1 - (sumSr / sumSt);
        double r = Math.sqrt(r2);
        String equation = String.format("y = %.2f + %.2fx + %.2fx²", coeficientes[0], coeficientes[1], coeficientes[2]);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(new Label("Resultados de la Regresión Cuadrática:"), gridPane);
        vbox.getChildren().add(new Label("\nMedia de Y: " + String.format("%.2f", meanY)));
        vbox.getChildren().add(new Label("Coeficiente de Determinación (R²): " + String.format("%.2f", r2)));
        vbox.getChildren().add(new Label("Coeficiente de Correlación (r): " + String.format("%.2f", r)));
        vbox.getChildren().add(new Label("Ecuación de la Regresión Cuadrática: " + equation));

        Scene scene = new Scene(vbox, 900, 500);
        this.setTitle("Resultados de la Regresión Cuadrática");
        this.setScene(scene);
        this.show();
    }
}