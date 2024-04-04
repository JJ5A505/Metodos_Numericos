package componentes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.function.Function;

public class SecanteSolver extends Stage {
    private String functionString;
    private double intervalA;
    private double intervalB;
    private double error;

    public SecanteSolver(String functionString, double intervalA, double intervalB, double error) {
        this.functionString = functionString;
        this.intervalA = intervalA;
        this.intervalB = intervalB;
        this.error = error;
    }

    public void solve() {
        // Inicializar el evaluador de la función
        Function<Double, Double> functionEvaluator = evaluateFunction(functionString);

        ObservableList<IterationResult> results = FXCollections.observableArrayList();

        double xiMinus1 = intervalA;
        double xi = intervalB;
        double xiPlus1;

        int iteration = 1;
        double errorPercentage = Double.MAX_VALUE;

        while (errorPercentage > error) {
            double fxiMinus1 = functionEvaluator.apply(xiMinus1);
            double fxi = functionEvaluator.apply(xi);

            if (fxi == fxiMinus1) {
                showErrorAlert("Error: La función no cruza el eje x dentro del intervalo proporcionado.");
                return;
            }

            xiPlus1 = xi - fxi * ((xi - xiMinus1) / (fxi - fxiMinus1));

            errorPercentage = Math.abs((xiPlus1 - xi) / xiPlus1) * 100;

            results.add(new IterationResult(iteration, xiMinus1, xi, fxiMinus1, fxi, xiPlus1, errorPercentage));

            xiMinus1 = xi;
            xi = xiPlus1;
            iteration++;
        }

        showResults(results);
    }

    private Function<Double, Double> evaluateFunction(String expression) {
        return x -> {
            try {
                Expression exp = new ExpressionBuilder(expression)
                        .variables("x")
                        .build()
                        .setVariable("x", x);
                return exp.evaluate();
            } catch (IllegalArgumentException e) {
                // Manejar el caso en el que la expresión no sea válida
                e.printStackTrace();
                return Double.NaN; // Valor de retorno para indicar un error en la evaluación de la expresión
            }
        };
    }

    private void showResults(ObservableList<IterationResult> results) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        for (IterationResult result : results) {
            Label label = new Label(
                    String.format("Iteración: %d, Xi-1: %.6f, Xi: %.6f, f(Xi-1): %.6f, f(Xi): %.6f, Xi+1: %.6f, Error: %.6f%%",
                            result.getIteration(),
                            result.getXiMinus1(),
                            result.getXi(),
                            result.getFxiMinus1(),
                            result.getFxi(),
                            result.getXiPlus1(),
                            result.getErrorPercentage()
                    )
            );
            root.getChildren().add(label);
        }

        Scene scene = new Scene(root);
        setScene(scene);
        setTitle("Resultados de la Secante");
        show();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
