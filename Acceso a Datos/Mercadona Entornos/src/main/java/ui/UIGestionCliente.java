package ui;

import jakarta.inject.Inject;
import modelo.Cliente;
import modelo.ClienteNormal;
import servicios.ServiciosClientes;
import ui.common.ConstantesAdmin;
import ui.common.ConstantesGestionClientes;

import java.util.Scanner;

public class UIGestionCliente {

    private final ServiciosClientes sclientes;

    @Inject
    public UIGestionCliente(ServiciosClientes sclientes) {
        this.sclientes = sclientes;
    }

    public void menuClientes(Scanner sc) {
        int numero;
        do {
            System.out.println(ConstantesGestionClientes.INTERFAZ_DE_GESTIÓN_DE_CLIENTES);
            System.out.println(ConstantesGestionClientes.MENU_GESTIÓN_CLIENTES);
            numero = sc.nextInt();
            sc.nextLine();
            switch (numero) {
                case 1:
                    addCliente(sc);
                    break;
                case 2:
                    eliminarCliente(sc);
                    break;
                case 3:
                    modificarNombre(sc);
                    break;
                case 4:
                    modificarDNI(sc);
                    break;
                case 5:
                    System.out.println(sclientes.verClientes());
                    break;
                case 6:
                    System.out.println(sclientes.verClientesOrdenados());
                    break;
                default:
                    if (numero == 0) {
                        System.out.println(ConstantesAdmin.SALIR_DEL_PROGRAMA);
                    } else System.out.println(ConstantesAdmin.NÚMERO_INCORRECTO);
            }
        } while (numero != 0);
    }

    private void addCliente(Scanner sc) {
        System.out.println(ConstantesGestionClientes.INTRODUCE_EL_NOMBRE_DEL_CLIENTE);
        String nombre = sc.nextLine();
        System.out.println(ConstantesGestionClientes.INTRODUCE_EL_DNI_DEL_CLIENTE);
        String dni = sc.nextLine();
        Cliente c = new ClienteNormal(nombre, dni);
        if (sclientes.registrarCliente(c)) {
            System.out.println(ConstantesGestionClientes.USUARIO_AÑADIDO_CON_ÉXITO);
        } else {
            System.out.println(ConstantesGestionClientes.NO_SE_HA_PODIDO_AÑADIR_EL_USUARIO_DATOS_INCORRECTOS);
        }
    }

    private void eliminarCliente(Scanner sc) {
        System.out.println(ConstantesGestionClientes.INTRODUCE_EL_DNI_DEL_USUARIO_QUE_QUIERAS_ELIMINAR);
        String dni = sc.nextLine();
        ClienteNormal c = new ClienteNormal(dni);
        if (sclientes.eliminarCliente(c)) {
            System.out.println(ConstantesGestionClientes.USUARIO_ELIMINADO_CON_ÉXITO);
        } else {
            System.out.println(ConstantesGestionClientes.EL_USUARIO_ESPECIFICADO_NO_EXISTE);
        }
    }

    private void modificarNombre(Scanner sc) {
        System.out.println(ConstantesGestionClientes.ESCRIBE_EL_DNI_DEL_CLIENTE_AL_QUE_QUIERAS_CAMBIAR_EL_NOMBRE);
        String dni = sc.nextLine();
        System.out.println(ConstantesGestionClientes.ESCRIBE_EL_NUEVO_NOMBRE_QUE_TENDRÁ_EL_CLIENTE);
        String nombre = sc.nextLine();
        ClienteNormal c = new ClienteNormal(dni, nombre);
        //if (sclientes.modificarNombre(c)) {
        //    System.out.println(ConstantesGestionClientes.NOMBRE_CAMBIADO);
        //} else {
        //    System.out.println(ConstantesGestionClientes.NO_SE_HAN_PODIDO_MODIFICAR_LOS_DATOS);
       // }
    }

    private void modificarDNI(Scanner sc) {
        System.out.println(ConstantesGestionClientes.ESCRIBE_EL_DNI_DEL_CLIENTE_AL_QUE_QUIERAS_CAMBIAR_EL_NOMBRE);
        String dni = sc.nextLine();
        System.out.println(ConstantesGestionClientes.ESCRIBE_EL_NUEVO_DNI_QUE_TENDRÁ_EL_CLIENTE);
        String nuevoDni = sc.nextLine();
        ClienteNormal c = new ClienteNormal(nuevoDni);
        ClienteNormal cviejo = new ClienteNormal(dni);
        if (sclientes.modificarDNI(c, cviejo)) {
            System.out.println(ConstantesGestionClientes.DNI_CAMBIADO);
        } else {
            System.out.println(ConstantesGestionClientes.NO_SE_HAN_PODIDO_MODIFICAR_LOS_DATOS);
        }
    }
}