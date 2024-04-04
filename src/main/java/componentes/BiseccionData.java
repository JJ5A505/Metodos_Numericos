package componentes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.function.Function;

public class BiseccionData extends Stage {
    private String functionString;
    private double intervalA;
    private double intervalB;
    private double error;
    private double lastApproximation;

    public BiseccionData(String functionString, double intervalA, double intervalB, double error) {
        this.functionString = functionString;
        this.intervalA = intervalA;
        this.intervalB = intervalB;
        this.error = error;

        setTitle("Resultados del Método de Bisección");
        setScene(createScene());
    }

    private Scene createScene() {
        VBox root = new VBox(10);
        root.getChildren().addAll(new Label("Resultados del Método de Bisección"));

        Function<Double, Double> function = evaluateFunction(functionString);

        double a = intervalA;
        double b = intervalB;
        double fa = function.apply(a);
        double fb = function.apply(b);

        if (fa * fb > 0) {
            showError("La función no cambia de signo en el intervalo dado. No se puede aplicar el método de bisección.");
            return null;
        }

        int iteration = 0;
        double previousApproximation = Double.MAX_VALUE;
        double currentApproximation = 0;

        do {
            double c = (a + b) / 2;
            double fc = function.apply(c);

            iteration++;
            currentApproximation = c;
            double error = Math.abs(currentApproximation - previousApproximation) * 100;

            String result = String.format("Iteración %d: a=%.6f, b=%.6f, f(a)=%.6f, f(b)=%.6f, aproximación=%.6f, f(aprox)=%.6f, error=%.6f",
                    iteration, a, b, fa, fb, currentApproximation, fc, error);
            root.getChildren().add(new Label(result));

            if (fa * fc < 0) {
                b = c;
                fb = fc;
            } else {
                a = c;
                fa = fc;
            }

            previousApproximation = currentApproximation;

        } while (Math.abs(function.apply(currentApproximation)) > error);

        lastApproximation = currentApproximation;

        String finalResult = String.format("Respuesta: %.6f", lastApproximation);
        root.getChildren().add(new Label(finalResult));

        return new Scene(root, 800, 600);
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

    private void showError(String message) {
        VBox root = new VBox(10);
        root.getChildren().addAll(new Label(message));
        setScene(new Scene(root, 400, 200));
    }
}
