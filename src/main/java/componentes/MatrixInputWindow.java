package componentes;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.util.function.UnaryOperator;

public class MatrixInputWindow extends Stage {
    private final int rows;
    private final int columns;
    private final TextField[][] matrixFields;
    private final Button resolver;

    public MatrixInputWindow(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrixFields = new TextField[rows][columns];
        this.resolver = new Button("Resolver");
        this.resolver.setPrefSize(70, 30);

        setTitle("Ingresa los números");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        // Crear un filtro para permitir solo números enteros en los campos de texto
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) { // Expresión regular para permitir números enteros positivos y negativos
                return change;
            }
            return null;
        };

        // Crear un TextFormatter con el filtro y el convertidor
        StringConverter<Integer> integerConverter = new IntegerStringConverter();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                TextField textField = new TextField();
                TextFormatter<Integer> textFormatter = new TextFormatter<>(integerConverter, null, integerFilter);
                textField.setTextFormatter(textFormatter);
                matrixFields[i][j] = textField;
                gridPane.add(textField, j, i);
            }
        }

        gridPane.add(resolver, 0, rows); // Agregar el botón "Resolver" debajo de la matriz

        resolver.setOnAction(event -> {
            double[][] solutionMatrix = getMatrixValues(); // Obtener la matriz de la entrada del usuario
            SolutionWindow solutionWindow = new SolutionWindow(solutionMatrix);
            solutionWindow.show(); // Mostrar la ventana de solución
        });

        Scene scene = new Scene(gridPane);
        scene.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toString());
        setScene(scene);
    }

    public double[][] getMatrixValues() {
        double[][] matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String text = matrixFields[i][j].getText();
                if (!text.isEmpty()) {
                    matrix[i][j] = Double.parseDouble(text);
                }
            }
        }
        return matrix;
    }
}
