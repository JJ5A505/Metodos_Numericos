package componentes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InterpolacionLinealSolver extends Stage {
    private final double[][] datos;
    private final double valor;

    public InterpolacionLinealSolver(double[][] datos, double valor) {
        this.datos = datos;
        this.valor = valor;
        resolverInterpolacionLineal();
    }

    private void resolverInterpolacionLineal() {
        // Obtener los valores de x e y
        double x1 = datos[0][0];
        double y1 = datos[0][1];
        double x2 = datos[1][0];
        double y2 = datos[1][1];

        // Calcular la interpolación lineal
        double y = ((y2 - y1) / (x2 - x1)) * (valor - x1) + y1;

        // Mostrar los resultados en una ventana
        VBox vbox = new VBox();
        Label resultadoLabel = new Label("Resultado de la interpolación lineal:");
        Label valorXLabel = new Label("Valor de x: " + valor);
        Label valorYLabel = new Label("Valor de y interpolado: " + y);
        vbox.getChildren().addAll(resultadoLabel, valorXLabel, valorYLabel);

        Scene scene = new Scene(vbox, 300, 200);
        this.setTitle("Interpolación Lineal");
        this.setScene(scene);
        this.show();
    }
}