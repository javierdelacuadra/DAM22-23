package ui.pantallas.pantallaconfig;

import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Cliente;
import modelo.LineaCompra;
import modelo.Monedero;
import modelo.Producto;
import servicios.ServiciosClientes;
import servicios.ServiciosCompras;
import servicios.ServiciosMonedero;

import java.util.List;

public class PantallaConfigViewModel {

    private final ServiciosClientes sclientes;
    private final ServiciosCompras scompras;
    private final ServiciosMonedero sm;

    private final ObservableList<Monedero> monederos;
    private final ObservableList<List<Producto>> comprasAntiguas;

    @Inject
    public PantallaConfigViewModel(ServiciosClientes sclientes, ServiciosCompras scompras, ServiciosMonedero sm) {
        this.sclientes = sclientes;
        this.scompras = scompras;
        this.sm = sm;
        this.monederos = FXCollections.observableArrayList();
        this.comprasAntiguas = FXCollections.observableArrayList();
    }

    public ObservableList<Monedero> getMonederos() {
        return monederos;
    }

    public void cambiarNombre(String nombreViejo, String nombreNuevo, String dni) {
        sclientes.modificarNombre(nombreViejo, nombreNuevo, dni);
    }

    public Cliente getCliente(String nombre) {
        return sclientes.getCliente(nombre);
    }

    public Double getGastoTotal(String dni) {
        return sclientes.gastoTotalCliente(dni);
    }

    public void addMonedero(Monedero m, String dni) {
        sm.addMonedero(dni, m);
        monederos.clear();
        setMonederos(dni);
    }

    public void getComprasAntiguas(String dni) {
        comprasAntiguas.setAll(sclientes.verComprasAntiguas(dni));
    }

    public void setMonederos(String dni) {
        monederos.setAll(sm.verMonederos(dni));
    }
}