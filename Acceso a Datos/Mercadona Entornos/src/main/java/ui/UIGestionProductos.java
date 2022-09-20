package ui;

import jakarta.inject.Inject;
import modelo.Producto;
import modelo.ProductoNormal;
import servicios.ServiciosProductos;
import ui.common.ConstantesAdmin;

import java.util.Objects;
import java.util.Scanner;

public class UIGestionProductos {

    private final ServiciosProductos sp;

    @Inject
    public UIGestionProductos(ServiciosProductos sp) {
        this.sp = sp;
    }

    public void menuAdmin(Scanner sc) {
        int numero;
        do {
            System.out.println(ConstantesAdmin.INTERFAZ_DE_GESTIÓN_DE_PRODUCTOS);
            System.out.println(ConstantesAdmin.MENU_ADMIN_PRODUCTOS);
            numero = sc.nextInt();
            sc.nextLine();
            switch (numero) {
                case 1:
                    addProducto(sc);
                    break;
                case 2:
                    modificarNombre(sc);
                    break;
                case 3:
                    modificarPrecio(sc);
                    break;
                case 4:
                    modificarStock(sc);
                    break;
                case 5:
                    System.out.println(sp.mostrarProductos());
                    break;
                default:
                    if (numero == 0) {
                        System.out.println(ConstantesAdmin.SALIR_DEL_PROGRAMA);
                    } else
                        System.out.println(ConstantesAdmin.NÚMERO_INCORRECTO);
            }
        } while (numero != 0);
    }

    private void addProducto(Scanner sc) {
        System.out.println(ConstantesAdmin.ESCRIBIR_NOMBRE_PRODUCTO);
        String nombreProducto = sc.nextLine();
        System.out.println(ConstantesAdmin.ESCRIBIR_PRECIO_PRODUCTO);
        double precio = sc.nextDouble();
        System.out.println(ConstantesAdmin.CANTIDAD_STOCK_INICIAL_DEL_PRODUCTO);
        int cantidad = sc.nextInt();
        Producto p = new ProductoNormal(nombreProducto, precio, cantidad);
        if (sp.addProducto(p)) {
            System.out.println(ConstantesAdmin.PRODUCTO_AÑADIDO);
        } else System.out.println(ConstantesAdmin.PRODUCTO_NO_MODIFICADO);
    }

    private void modificarNombre(Scanner sc) {
        boolean saltarModificacion = false;
        if (sp.hayProductos()) {
            System.out.println(ConstantesAdmin.AÑADE_ALGÚN_PRODUCTO_ANTES_DE_MODIFICARLO);
            saltarModificacion = true;
        }
        String nombreProducto;
        String nombreNuevo;
        if (!saltarModificacion) {
            do {
                System.out.println(ConstantesAdmin.ESCRIBE_EL_NOMBRE_DEL_PRODUCTO_QUE_QUIERAS_MODIFICAR);
                nombreProducto = sc.nextLine();
                System.out.println(ConstantesAdmin.DATOS_ACTUALES_DEL_PRODUCTO + sp.getProducto(nombreProducto));
            } while (Objects.equals(sp.getProducto(nombreProducto), ConstantesAdmin.NO_SE_HA_ENCONTRADO_EL_PRODUCTO));
            System.out.println(ConstantesAdmin.ESCRIBIR_NOMBRE_NUEVO_PRODUCTO);
            nombreNuevo = sc.nextLine();
            if (sp.editarNombre(nombreProducto, nombreNuevo)) {
                System.out.println(ConstantesAdmin.PRODUCTO_MODIFICADO_CORRECTAMENTE);
            } else System.out.println(ConstantesAdmin.PRODUCTO_NO_MODIFICADO);
        }
    }

    private void modificarPrecio(Scanner sc) {
        boolean saltarModificacion = false;
        if (sp.hayProductos()) {
            System.out.println(ConstantesAdmin.AÑADE_ALGÚN_PRODUCTO_ANTES_DE_MODIFICARLO);
            saltarModificacion = true;
        }
        String nombreProducto;
        double precio;
        if (!saltarModificacion) {
            do {
                System.out.println(ConstantesAdmin.ESCRIBE_EL_NOMBRE_DEL_PRODUCTO_QUE_QUIERAS_MODIFICAR);
                nombreProducto = sc.nextLine();
                System.out.println(ConstantesAdmin.DATOS_ACTUALES_DEL_PRODUCTO + sp.getProducto(nombreProducto));
            } while (Objects.equals(sp.getProducto(nombreProducto), ConstantesAdmin.NO_SE_HA_ENCONTRADO_EL_PRODUCTO));
            System.out.println(ConstantesAdmin.ESCRIBIR_PRECIO_PRODUCTO);
            precio = sc.nextDouble();
            if (sp.editarPrecio(nombreProducto, precio)) {
                System.out.println(ConstantesAdmin.PRODUCTO_MODIFICADO_CORRECTAMENTE);
            } else System.out.println(ConstantesAdmin.PRODUCTO_NO_MODIFICADO);
        }
    }

    private void modificarStock(Scanner sc) {
        boolean saltarModificacion = false;
        if (sp.hayProductos()) {
            System.out.println(ConstantesAdmin.AÑADE_ALGÚN_PRODUCTO_ANTES_DE_MODIFICARLO);
            saltarModificacion = true;
        }
        String nombreProducto;
        int stock;
        if (!saltarModificacion) {
            do {
                System.out.println(ConstantesAdmin.ESCRIBE_EL_NOMBRE_DEL_PRODUCTO_QUE_QUIERAS_MODIFICAR);
                nombreProducto = sc.nextLine();
                System.out.println(ConstantesAdmin.DATOS_ACTUALES_DEL_PRODUCTO + sp.getProducto(nombreProducto));
            } while (Objects.equals(sp.getProducto(nombreProducto), ConstantesAdmin.NO_SE_HA_ENCONTRADO_EL_PRODUCTO));
            System.out.println(ConstantesAdmin.CANTIDAD_DE_STOCK_AÑADIR_O_QUITAR);
            stock = sc.nextInt();
            if (sp.editarStock(nombreProducto, stock)) {
                System.out.println(ConstantesAdmin.PRODUCTO_MODIFICADO_CORRECTAMENTE);
            } else System.out.println(ConstantesAdmin.PRODUCTO_NO_MODIFICADO);
        }
    }
}