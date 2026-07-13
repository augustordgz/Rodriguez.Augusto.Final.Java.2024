package com.utn.finalprogramacion2;

import com.utn.finalprogramacion2.views.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.mostrar(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
