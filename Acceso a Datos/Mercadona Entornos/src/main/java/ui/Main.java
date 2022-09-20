package ui;

import jakarta.inject.Inject;
import modelo.Cliente;
import modelo.ClienteNormal;
import servicios.ServiciosClientes;
import ui.common.ConstantesAdmin;
import ui.common.ConstantesClientes;

import java.util.Scanner;

public class Main {

    private final ServiciosClientes sclientes;
    private final UICliente clienteUI;
    private final UIGestionCliente uiGestionCliente;
    private final UIGestionProductos adminUI;

    @Inject
    public Main(ServiciosClientes sclientes, UICliente clienteUI, UIGestionCliente uiGestionCliente, UIGestionProductos adminUI) {
        this.sclientes = sclientes;
        this.clienteUI = clienteUI;
        this.uiGestionCliente = uiGestionCliente;
        this.adminUI = adminUI;
    }

    public void run() {
        menu();
    }

    private void menu() {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println(ConstantesAdmin.MENU_INICIAL);
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    inicioSesion(sc);
                    break;
                case 2:
                    registroClientes(sc);
                    break;
                default:
                    if (opcion == 3) {
                        System.out.println(ConstantesAdmin.SALIR_DEL_PROGRAMA);
                    } else System.out.println(ConstantesAdmin.NÚMERO_INCORRECTO);
            }
        } while (opcion != 3);
    }

    private void inicioSesion(Scanner sc) {

        System.out.println(ConstantesAdmin.INTRODUZCA_NOMBRE_DE_USUARIO);
        String nombreUsuario = sc.nextLine();
        if (nombreUsuario.equalsIgnoreCase(ConstantesAdmin.ADMIN)) {
            System.out.println(ConstantesAdmin.MENU_ADMIN);
            int opcion = sc.nextInt();
            sc.nextLine();
            if (opcion == 1) {
                adminUI.menuAdmin(sc);
            } else if (opcion == 2) {
                uiGestionCliente.menuClientes(sc);
            }
        } else {
            //cliente
            System.out.println(ConstantesAdmin.INTRODUCE_TU_DNI);
            String dni = sc.nextLine();
            Cliente c = new ClienteNormal(dni);
            if (sclientes.inicioSesion(c)) {
                clienteUI.menuCliente(sc, dni);
            } else {
                System.out.println(ConstantesClientes.DNI_INCORRECTO);
            }
        }
    }

    private void registroClientes(Scanner sc) {
        System.out.println(ConstantesAdmin.INTRODUZCA_NOMBRE_DE_USUARIO);
        String nombreUsuario = sc.nextLine();
        System.out.println(ConstantesAdmin.INTRODUCE_TU_DNI);
        String dni = sc.nextLine();
        ClienteNormal c = new ClienteNormal(dni, nombreUsuario);
        if (sclientes.registrarCliente(c)) {
            System.out.println(ConstantesAdmin.CLIENTE_REGISTRADO);
        } else {
            System.out.println(ConstantesClientes.CLIENTE_NO_REGISTRADO);
        }
    }
}