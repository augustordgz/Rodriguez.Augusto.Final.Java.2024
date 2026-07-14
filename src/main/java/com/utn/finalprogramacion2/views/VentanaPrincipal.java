package com.utn.finalprogramacion2.views;

import com.utn.finalprogramacion2.controllers.FiltroProductos;
import com.utn.finalprogramacion2.controllers.GestorProductos;
import com.utn.finalprogramacion2.controllers.ModificadorProductos;
import com.utn.finalprogramacion2.models.*;
import com.utn.finalprogramacion2.utils.*;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VentanaPrincipal {

    private GestorProductos gestor;
    private TableView<Producto> tabla;
    private ObservableList<Producto> datosTabla;

    private TextField campoCodigo = new TextField();
    private TextField campoNombre = new TextField();
    private TextField campoPrecio = new TextField();
    private TextField campoStock = new TextField();
    private ComboBox<Categoria> comboCategoria = new ComboBox<>();
    private ComboBox<String> comboTipo = new ComboBox<>();
    private ComboBox<Categoria> comboFiltroCategoria = new ComboBox<>();
    private ComboBox<String> comboFormato = new ComboBox<>();

    private TextField campoExtra1 = new TextField();
    private TextField campoExtra2 = new TextField();
    private Label labelExtra1 = new Label();
    private Label labelExtra2 = new Label();

    private Button btnAgregar = new Button("Agregar");
    private Button btnActualizar = new Button("Actualizar");
    private Button btnEliminar = new Button("Eliminar");

    public VentanaPrincipal() {
        this.gestor = new GestorProductos();
        cargarDatosDePrueba();
    }

    public void mostrar(Stage stage) {
        BorderPane raiz = new BorderPane();
        raiz.setTop(construirBarraHerramientas());
        raiz.setCenter(construirTabla());
        raiz.setBottom(construirFormulario());

        Scene escena = new Scene(raiz, 950, 700);
        stage.setTitle("CRUD Gaming - Sistema de Gestión");
        stage.setScene(escena);

        stage.setOnCloseRequest(event -> {
            System.out.println("Cerrando aplicación.");
        });
        stage.show();
    }

    private TableView<Producto> construirTabla() {
        tabla = new TableView<>();

        TableColumn<Producto, String> colCodigo = new TableColumn<>("Código");
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Producto, Object> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        tabla.getColumns().addAll(colCodigo, colNombre, colPrecio, colStock, colCategoria);

        datosTabla = FXCollections.observableArrayList(gestor.listarTodos());
        tabla.setItems(datosTabla);
        tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tabla.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            actualizarEstadoBotones(seleccionado);
            if (seleccionado != null) {
                cargarProductoEnFormulario(seleccionado);
            }
        });

        return tabla;
    }

    private VBox construirFormulario() {
        comboCategoria.setItems(FXCollections.observableArrayList(Categoria.values()));
        comboTipo.setItems(FXCollections.observableArrayList("CONSOLA", "VIDEOJUEGO", "ACCESORIO"));
        comboTipo.setOnAction(e -> actualizarCamposSegunTipo());
        comboTipo.getSelectionModel().selectFirst();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        grid.addRow(0, new Label("Tipo:"), comboTipo, new Label("Código:"), campoCodigo);
        grid.addRow(1, new Label("Nombre:"), campoNombre, new Label("Precio:"), campoPrecio);
        grid.addRow(2, new Label("Stock:"), campoStock, new Label("Categoría:"), comboCategoria);
        grid.addRow(3, labelExtra1, campoExtra1, labelExtra2, campoExtra2);

        btnAgregar.setOnAction(e -> agregarProducto());
        btnActualizar.setOnAction(e -> actualizarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());

        Button btnLimpiar = new Button("Limpiar formulario");
        btnLimpiar.setOnAction(e -> limpiarFormulario());

        HBox botones = new HBox(10, btnAgregar, btnActualizar, btnEliminar, btnLimpiar);
        botones.setPadding(new Insets(5, 10, 10, 10));

        actualizarEstadoBotones(null); 

        return new VBox(grid, botones);
    }

    private VBox construirBarraHerramientas() {

        comboFiltroCategoria.setItems(FXCollections.observableArrayList(Categoria.values()));

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setOnAction(e -> aplicarFiltro());
        Button btnQuitarFiltro = new Button("Ver todos");
        btnQuitarFiltro.setOnAction(e -> refrescarTabla());

        HBox filaOrdenFiltro = new HBox(10,
                new Label("Ordenar por categoría:"), comboFiltroCategoria,
                btnFiltrar, btnQuitarFiltro);
        filaOrdenFiltro.setPadding(new Insets(10));
        filaOrdenFiltro.setAlignment(Pos.CENTER_LEFT);

        comboFormato.setItems(FXCollections.observableArrayList("Serialización (.dat)", "CSV", "JSON"));
        comboFormato.getSelectionModel().selectFirst();

        Button btnGuardarFormato = new Button("Guardar");
        btnGuardarFormato.setOnAction(e -> guardarSegunFormato());

        Button btnCargarFormato = new Button("Cargar");
        btnCargarFormato.setOnAction(e -> cargarSegunFormato());

        Button btnDescuento = new Button("Aplicar 10% descuento a seleccionados");
        btnDescuento.setOnAction(e -> aplicarDescuentoSeleccionados());

        Button btnExportarTxt = new Button("Exportar listado actual a .txt");
        btnExportarTxt.setOnAction(e -> exportarTxt());

        HBox filaAcciones = new HBox(10,
                new Label("Formato:"), comboFormato, btnGuardarFormato, btnCargarFormato,
                new Separator(),
                btnDescuento, btnExportarTxt);
        filaAcciones.setPadding(new Insets(10));
        filaAcciones.setAlignment(Pos.CENTER_LEFT);

        return new VBox(filaOrdenFiltro, filaAcciones);
    }   

    private void actualizarEstadoBotones(Producto seleccion) {
        boolean haySeleccion = (seleccion != null);
        btnActualizar.setDisable(!haySeleccion);
        btnEliminar.setDisable(!haySeleccion);
    }

    private void actualizarCamposSegunTipo() {
        String tipo = comboTipo.getValue();
        if (tipo == null) return;
        switch (tipo) {
            case "CONSOLA" -> {
                labelExtra1.setText("Marca:");
                labelExtra2.setText("Almacenamiento:");
            }
            case "VIDEOJUEGO" -> {
                labelExtra1.setText("Género:");
                labelExtra2.setText("Plataforma (PC/PLAYSTATION/XBOX/NINTENDO):");
            }
            case "ACCESORIO" -> {
                labelExtra1.setText("Tipo (ej: Control):");
                labelExtra2.setText("Multiplataforma (true/false):");
            }
        }
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (campoCodigo.getText().isBlank()) errores.append("El código es obligatorio.\n");
        if (campoNombre.getText().isBlank()) errores.append("El nombre es obligatorio.\n");
        if (comboCategoria.getValue() == null) errores.append("Seleccioná una categoría.\n");
        if (comboTipo.getValue() == null) errores.append("Seleccioná un tipo de producto.\n");

        try {
            Double.parseDouble(campoPrecio.getText());
        } catch (NumberFormatException e) {
            errores.append("El precio debe ser un número válido.\n");
        }
        try {
            Integer.parseInt(campoStock.getText());
        } catch (NumberFormatException e) {
            errores.append("El stock debe ser un número entero válido.\n");
        }

        if (errores.length() > 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos inválidos", errores.toString());
            return false;
        }
        return true;
    }  

    private void aplicarFiltro() {
        Categoria categoriaFiltro = comboFiltroCategoria.getValue();

        List<Producto> filtrados = FiltroProductos.filtrar(gestor.listarTodos(), p -> p.getCategoria() == categoriaFiltro);

        datosTabla.setAll(filtrados);
    }   

    private void agregarProducto() {
        if (!validarCampos()) return;
        try {
            Producto nuevo = construirProductoDesdeFormulario();
            gestor.agregar(nuevo);
            refrescarTabla();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto agregado correctamente.");
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar: " + e.getMessage());
        }
    }

    private void actualizarProducto() {
        if (!validarCampos()) return;
        try {
            String codigo = campoCodigo.getText();
            Producto actualizado = construirProductoDesdeFormulario();
            boolean ok = gestor.actualizar(codigo, actualizado);
            refrescarTabla();
            if (ok) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "No encontrado", "No se encontró un producto con ese código.");
            }
            limpiarFormulario();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Desea eliminar el producto seleccionado?", ButtonType.YES, ButtonType.NO);
        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.YES) {
                String codigo = campoCodigo.getText();
                boolean ok = gestor.eliminar(codigo);
                refrescarTabla();
                mostrarAlerta(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING,
                        ok ? "Éxito" : "No encontrado",
                        ok ? "Producto eliminado correctamente." : "No se encontró el producto.");
                limpiarFormulario();
            }
        });
    }

    private Producto construirProductoDesdeFormulario() {
        String codigo = campoCodigo.getText();
        String nombre = campoNombre.getText();
        double precio = Double.parseDouble(campoPrecio.getText());
        int stock = Integer.parseInt(campoStock.getText());
        Categoria categoria = comboCategoria.getValue();
        String tipo = comboTipo.getValue();

        return switch (tipo) {
            case "CONSOLA" -> new Consola(codigo, nombre, precio, stock, categoria,
                    campoExtra1.getText(), campoExtra2.getText());
            case "VIDEOJUEGO" -> new Videojuego(codigo, nombre, precio, stock, categoria,
                    campoExtra1.getText(), Plataforma.valueOf(campoExtra2.getText().toUpperCase()));
            case "ACCESORIO" -> new Accesorio(codigo, nombre, precio, stock, categoria,
                    campoExtra1.getText(), Boolean.parseBoolean(campoExtra2.getText()));
            default -> throw new IllegalArgumentException("Tipo de producto inválido.");
        };
    }

    private void cargarProductoEnFormulario(Producto p) {
        campoCodigo.setText(p.getCodigo());
        campoNombre.setText(p.getNombre());
        campoPrecio.setText(String.valueOf(p.getPrecio()));
        campoStock.setText(String.valueOf(p.getStock()));
        comboCategoria.setValue(p.getCategoria());

        if (p instanceof Consola c) {
            comboTipo.setValue("CONSOLA");
            campoExtra1.setText(c.getMarca());
            campoExtra2.setText(c.getAlmacenamiento());
        } else if (p instanceof Videojuego v) {
            comboTipo.setValue("VIDEOJUEGO");
            campoExtra1.setText(v.getGenero());
            campoExtra2.setText(v.getPlataforma().name());
        } else if (p instanceof Accesorio a) {
            comboTipo.setValue("ACCESORIO");
            campoExtra1.setText(a.getTipo());
            campoExtra2.setText(String.valueOf(a.isCompatibleMultiplataforma()));
        }
    }

    private void limpiarFormulario() {
        campoCodigo.clear();
        campoNombre.clear();
        campoPrecio.clear();
        campoStock.clear();
        campoExtra1.clear();
        campoExtra2.clear();
        comboCategoria.setValue(null);
        tabla.getSelectionModel().clearSelection();
        actualizarEstadoBotones(null);
    }

    private void refrescarTabla() {
        datosTabla.setAll(gestor.listarTodos());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private void aplicarDescuentoSeleccionados() {
        ObservableList<Producto> seleccionados = tabla.getSelectionModel().getSelectedItems();
        if (seleccionados.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Seleccioná al menos un producto de la tabla.");
            return;
        }

        ModificadorProductos.aplicarATodos(seleccionados, p -> {
            if (p instanceof Descontable d) {
                d.aplicarDescuento(10);
            }
        });

        tabla.refresh(); 
        mostrarAlerta(Alert.AlertType.INFORMATION, "Descuento aplicado", "Se aplicó 10% de descuento a los productos seleccionados.");
    }

    private void guardarSegunFormato() {
        String formato = comboFormato.getValue();
        try {
            switch (formato) {
                case "Serialización (.dat)" -> Serializacion.guardar(gestor.listarTodos(), "productos.dat");
                case "CSV" -> CSV.guardar(gestor.listarTodos(), "productos.csv");
                case "JSON" -> JSON.guardar(gestor.listarTodos(), "productos.json");
            }
            mostrarAlerta(Alert.AlertType.INFORMATION, "Guardado", "Datos guardados correctamente en formato " + formato);
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar: " + e.getMessage());
        }
    }   

    private void cargarSegunFormato() {
        String formato = comboFormato.getValue();
        try {
            List<Producto> cargados = switch (formato) {
                case "Serialización (.dat)" -> Serializacion.cargar("productos.dat");
                case "CSV" -> CSV.cargar("productos.csv");
                case "JSON" -> JSON.cargar("productos.json");
                default -> throw new IllegalStateException("Formato no soportado.");
            };
            reemplazarDatosGestor(cargados);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cargado", "Datos cargados correctamente desde formato " + formato);
        } catch (IOException | ClassNotFoundException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar: " + e.getMessage());
        }
    }   

    private void exportarTxt() {
        try {
            List<Producto> listadoActual = datosTabla; // lo que se ve ahora en pantalla (con filtro aplicado, si hay)
            Texto.exportar(listadoActual, "reporte_" + System.currentTimeMillis() + ".txt",
                    "Listado exportado desde la interfaz gráfica");
            mostrarAlerta(Alert.AlertType.INFORMATION, "Exportado", "Reporte de texto generado correctamente.");
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo exportar: " + e.getMessage());
        }
    }

    private void reemplazarDatosGestor(List<Producto> nuevosDatos) {
        for (Producto p : gestor.listarTodos()) {
            gestor.eliminar(p.getCodigo());
        }
        for (Producto p : nuevosDatos) {
            gestor.agregar(p);
        }
        refrescarTabla();
    }

    private void cargarDatosDePrueba() {
        gestor.agregar(new Consola("C001", "PlayStation 5", 800000, 10, Categoria.HARDWARE, "Sony", "1TB"));
        gestor.agregar(new Videojuego("V001", "Elden Ring", 45000, 20, Categoria.SOFTWARE, "RPG", Plataforma.PC));
        gestor.agregar(new Accesorio("A001", "Control DualSense", 60000, 15, Categoria.PERIFERICO, "Control", true));
    }
}