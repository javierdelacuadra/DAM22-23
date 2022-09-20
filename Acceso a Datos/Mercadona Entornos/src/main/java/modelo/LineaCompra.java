package modelo;

import ui.common.ConstantesGestionClientes;

public class LineaCompra {
    private final Producto producto;
    private int cantidad;

    public LineaCompra(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return ConstantesGestionClientes.CORCHETE1 + producto +
                ConstantesGestionClientes.CORCHETE2;
    }
}