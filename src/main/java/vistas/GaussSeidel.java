package vistas;

import componentes.MatrixInputWindow;
import componentes.MatrixInputWindow2;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GaussSeidel extends Stage {
    private Scene scene;
    private VBox vBox;
    private GridPane grdOpc;
    private String[] opciones = {"2x2", "3x3", "4x4", "5x5", "6x6", "Salir"};
    private Button[][] arOpc = new Button[2][3];


    public GaussSeidel() {
        createUI();
        scene = new Scene(vBox, 300, 300);
        scene.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toString());
        this.setTitle("Selecciona el tamaño ");
        this.setScene(scene);
        this.show();
    }

    public void createUI() {
        int pos = 0;
        vBox = new VBox();
        vBox.getStylesheets().add(getClass().getResource("/estilos/GaussJordan.css").toString());
        grdOpc = new GridPane();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                arOpc[i][j] = new Button(String.valueOf(opciones[pos]));
                arOpc[i][j].setPrefSize(100, 100);
                grdOpc.add(arOpc[i][j], i, j);
                pos++;
            }
        }

        arOpc[0][0].setOnAction(event -> {
            MatrixInputWindow2 matrixWindow = new MatrixInputWindow2(2, 3); // Tamaño de la matriz 2x2
            matrixWindow.showAndWait();
            double[][] matrixValues = matrixWindow.getMatrixValues();
        });
        arOpc[0][1].setOnAction(event -> {
            MatrixInputWindow2 matrixWindow = new MatrixInputWindow2(3, 4); // Tamaño de la matriz 2x2
            matrixWindow.showAndWait();
            double[][] matrixValues = matrixWindow.getMatrixValues();
        });
        arOpc[0][2].setOnAction(event -> {
            MatrixInputWindow2 matrixWindow = new MatrixInputWindow2(4, 5); // Tamaño de la matriz 2x2
            matrixWindow.showAndWait();
            double[][] matrixValues = matrixWindow.getMatrixValues();
        });
        arOpc[1][0].setOnAction(event -> {
            MatrixInputWindow2 matrixWindow = new MatrixInputWindow2(5, 6); // Tamaño de la matriz 2x2
            matrixWindow.showAndWait();
            double[][] matrixValues = matrixWindow.getMatrixValues();
        });
        arOpc[1][1].setOnAction(event -> {
            MatrixInputWindow2 matrixWindow = new MatrixInputWindow2(6, 7); // Tamaño de la matriz 2x2
            matrixWindow.showAndWait();
            double[][] matrixValues = matrixWindow.getMatrixValues();
        });
        arOpc[1][2].setOnAction(event -> this.close());


        vBox.getChildren().add(grdOpc);
    }
}

