package com.example.metods;

import vistas.GaussSeidel;
import vistas.Secante;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vistas.Biseccion;
import vistas.GaussJordan;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {

    private Scene escena;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu menuAbiertos,menuCerrados,menuSalir;
    private MenuItem mitBiseccion,mitSecante,mitGaussJordan,mitGaussSeidel,mitSalir;
    private void CrearUI(){
        mitBiseccion=new MenuItem("Metodo Biseccion");
        mitBiseccion.setOnAction(actionEvent -> new Biseccion());
        mitSecante=new MenuItem("Metodo Secante");
        mitSecante.setOnAction(actionEvent -> new Secante());
        menuCerrados=new Menu("Metodos Cerrados");
        menuCerrados.getItems().addAll(mitBiseccion,mitSecante);
        //menu de metodos cerrados
        mitGaussJordan=new MenuItem("Metodo Gauss Jordan");
        mitGaussJordan.setOnAction(event -> new GaussJordan());
        mitGaussSeidel=new MenuItem("Metodo Gauss Seidel");
        mitGaussSeidel.setOnAction(actionEvent -> new GaussSeidel());
        menuAbiertos=new Menu("Metodos Abiertos");
        menuAbiertos.getItems().addAll(mitGaussJordan,mitGaussSeidel);
        //menu para metodos abiertos

        mitSalir=new MenuItem("Salir");
        mitSalir.setOnAction(actionEvent -> Salir());
        menuSalir=new Menu("Mas Opciones");
        menuSalir.getItems().add(mitSalir);
        menuBar=new MenuBar(menuCerrados,menuAbiertos,menuSalir);
    }
    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        borderPane= new BorderPane();
        borderPane.setTop(menuBar);
        escena=new Scene(borderPane,300,400);
        escena.getStylesheets().add(getClass().getResource("/estilos/estilos.css").toString());
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
    }
    private void Salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setHeaderText("Confirmar cerrar sitema?");
        Optional<ButtonType> option =alert.showAndWait();
        if(option.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}