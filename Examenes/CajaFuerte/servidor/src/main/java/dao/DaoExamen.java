package dao;

import domain.exceptions.ObjectNotFoundException;
import modelo.Informe;
import modelo.Raton;
import modelo.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DaoExamen {
    static List<User> usuarios = new ArrayList<>();
    static List<Informe> informes = new ArrayList<>();
    static List<Raton> ratones = new ArrayList<>();

    static {
        //Usuarios
        User usuario1 = new User();
        usuario1.setNombre("usuario1");
        usuario1.setPassword("pass1");
        usuario1.setRoles(List.of("BIOLOGO"));
        User usuario2 = new User();
        usuario2.setNombre("usuario2");
        usuario2.setPassword("pass2");
        usuario2.setRoles(List.of("INFORMES", "NIVEL2"));
        User usuario3 = new User();
        usuario3.setNombre("usuario3");
        usuario3.setPassword("pass3");
        usuario3.setRoles(List.of("ESPIA", "NIVEL1"));
        User usuario4 = new User();
        usuario4.setNombre("usuario4");
        usuario4.setPassword("pass4");
        usuario4.setRoles(List.of("RATONES"));
        usuarios.add(usuario1);
        usuarios.add(usuario2);
        usuarios.add(usuario3);
        usuarios.add(usuario4);

        //Ratones
        Raton raton1 = new Raton("raton1", 12);
        Raton raton2 = new Raton("raton2", 32);
        Raton raton3 = new Raton("raton3", 18);
        Raton raton4 = new Raton("raton4", 7);
        ratones.add(raton1);
        ratones.add(raton2);
        ratones.add(raton3);
        ratones.add(raton4);

        //Informes
        Informe informe1 = new Informe();
        informe1.setNombre("informe1");
        informe1.setFecha(LocalDate.now());
        informe1.setRol("NIVEL1");
        informe1.setRatones(List.of(raton1, raton2, raton3));

        Informe informe2 = new Informe();
        informe2.setNombre("informe2");
        informe2.setFecha(LocalDate.now());
        informe2.setRol("NIVEL2");
        informe2.setRatones(List.of(raton4, raton2));

        Informe informe3 = new Informe();
        informe3.setNombre("informe3");
        informe3.setFecha(LocalDate.now());
        informe3.setRol("NIVEL2");
        informe3.setRatones(List.of(raton1, raton3, raton4));

        informes.add(informe1);
        informes.add(informe2);
        informes.add(informe3);
    }

    private DaoExamen() {
    }

    public static List<Raton> getRatones() {
        return ratones;
    }

    public static void addRaton(Raton raton) {
        ratones.add(raton);
    }

    public static List<Informe> getInformes(boolean nivel2) {
        if (nivel2) {
            return informes;
        } else {
            return informes.stream().filter(informe -> informe.getRol().equals("NIVEL1"))
                    .toList();
        }
    }

    public static void addInforme(Informe informe) {
        informes.add(informe);
    }

    public static User getUserByName(String name) {
        User usuario = usuarios.stream().filter(user -> user.getNombre().equals(name))
                .findFirst().orElse(null);
        if (usuario == null) {
            throw new ObjectNotFoundException("No se ha encontrado el usuario");
        } else {
            return usuario;
        }
    }

    public static User checkLogin(String user, String pass) {
        User usuario = usuarios.stream().filter(u -> u.getNombre().equals(user) && u.getPassword().equals(pass))
                .findFirst().orElse(null);
        if (usuario == null) {
            throw new ObjectNotFoundException("No se ha encontrado el usuario");
        } else {
            return usuario;
        }
    }

    public Informe getInforme(String nombre, boolean nivel2) {
        Informe informe = informes.stream().filter(i -> i.getNombre().equals(nombre))
                .findFirst().orElse(null);
        if (informe == null) {
            throw new ObjectNotFoundException("No se ha encontrado el informe");
        } else {
            String rol = informe.getRol();
            if (nivel2) {
                return informe;
            } else {
                if (rol.equals("NIVEL1")) {
                    return informe;
                } else {
                    throw new ObjectNotFoundException("No tiene permisos para acceder a este informe");
                }
            }
        }
    }
}