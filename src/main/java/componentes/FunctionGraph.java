package componentes;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.function.Function;

public class FunctionGraph extends Stage {

    public FunctionGraph(String functionString, double intervalA, double intervalB) {
        setTitle("Gráfica de la Función");

        Function<Double, Double> function = evaluateFunction(functionString);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Gráfica de la Función");
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Función");

        // Agregar puntos a la gráfica
        double minX = intervalA;
        double maxX = intervalB;
        double step = 0.1; // Paso entre cada punto

        for (double x = minX; x <= maxX; x += step) {
            double y = function.apply(x);
            series.getData().add(new XYChart.Data<>(x, y));
        }

        lineChart.getData().add(series);

        Scene scene = new Scene(lineChart, 800, 600);
        setScene(scene);
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
                e.printStackTrace();
                return Double.NaN;
            }
        };
    }
}