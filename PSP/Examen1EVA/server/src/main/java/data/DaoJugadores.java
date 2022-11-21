package data;

import domain.exceptions.ElementNotFoundException;
import domain.exceptions.ForeignKeyConstraintException;
import modelo.Equipo;
import modelo.Jugador;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DaoJugadores {

    static List<Equipo> equipos = new ArrayList<>();
    static List<Jugador> jugadores = new ArrayList<>();

    static {
        Jugador vinicius = new Jugador("Vinicius Jr", "Brasil", 1);
        Jugador messi = new Jugador("Messi", "Argentina", 2);
        Jugador lewandoski = new Jugador("Lewandoski", "Polonia", 3);
        Jugador kroos = new Jugador("Kroos", "Alemania", 4);
        Jugador pedri = new Jugador("Pedri", "Spain", 5);

        jugadores.add(vinicius);
        jugadores.add(messi);
        jugadores.add(lewandoski);
        jugadores.add(kroos);
        jugadores.add(pedri);

        List<Jugador> jugadoresSpain = new ArrayList<>();
        jugadoresSpain.add(pedri);

        List<Jugador> jugadoresAlemania = new ArrayList<>();
        jugadoresAlemania.add(kroos);

        List<Jugador> jugadoresArgentina = new ArrayList<>();
        jugadoresArgentina.add(messi);

        List<Jugador> jugadoresBrasil = new ArrayList<>();
        jugadoresBrasil.add(vinicius);

        List<Jugador> jugadoresPolonia = new ArrayList<>();
        jugadoresPolonia.add(lewandoski);

        equipos.add(new Equipo("Spain", "Luis Enrique", jugadoresSpain));
        equipos.add(new Equipo("Alemania", "entrenador alemania", jugadoresAlemania));
        equipos.add(new Equipo("Argentina", "entrenador argentina", jugadoresArgentina));
        equipos.add(new Equipo("Brasil", "entrenador brasil", jugadoresBrasil));
        equipos.add(new Equipo("Polonia", "entrenador polonia", jugadoresPolonia));
        equipos.add(new Equipo("Portugal", "entrenador portugal", new ArrayList<>()));
    }

    //ver todos los equipos del mundial
    public List<Equipo> verTodosLosEquiposSinJugadores() {
        List<Equipo> equiposSinJugadores = new ArrayList<>();
        equipos.forEach(equipo -> equiposSinJugadores.add(new Equipo(equipo.getNombre(), equipo.getEntrenador())));
        return equiposSinJugadores;
    }

    //ver los equipos con jugadores
    public List<Equipo> verTodosLosEquipos() {
        return equipos;
    }

    //ver jugadores de un equipo devuelve 404
    public List<Jugador> verJugadoresDeUnEquipo(String nombreEquipo) {
        if (equipos.stream().anyMatch(equipo -> equipo.getNombre().equals(nombreEquipo))) {
            Equipo equipo = equipos.stream().filter(equipo1 -> equipo1.getNombre().equals(nombreEquipo))
                    .findFirst().get();
            return equipo.getJugadores();
        } else {
            throw new ElementNotFoundException("No se ha encontrado un equipo con ese nombre");
        }
    }

    //ver un solo equipo devuelve 404
    public Equipo verUnEquipo(String nombreEquipo) {
        if (equipos.stream().anyMatch(equipo -> equipo.getNombre().equals(nombreEquipo))) {
            return equipos.stream().filter(equipo -> equipo.getNombre().equals(nombreEquipo)).findFirst().orElse(null);
        } else {
            throw new ElementNotFoundException("No se ha encontrado un equipo con ese nombre");
        }
    }

    //añadir jugadores a un equipo
    public Jugador addJugador(Jugador jugador) {
        jugador.setId(jugadores.size() + 1);
        if (equipos.stream().anyMatch(equipo -> equipo.getNombre().equals(jugador.getNombreEquipo()))) {
            equipos.stream().filter(equipo -> equipo.getNombre().equals(jugador.getNombreEquipo()))
                    .forEach(equipo -> {
                        List<Jugador> lista = equipo.getJugadores();
                        lista.add(jugador);
                        equipo.setJugadores(lista);
                    });
        }
        return jugador;
    }

    //añadir un equipo
    public Equipo addEquipo(Equipo equipo) {
        equipos.add(equipo);
        return equipo;
    }

    //actualizar jugador
    public Jugador updateJugador(Jugador jugador) {
        if (jugadores.stream().anyMatch(jugador1 -> Objects.equals(jugador1.getId(), jugador.getId()))) {
            equipos.stream().filter(equipo -> equipo.getNombre().equals(jugador.getNombreEquipo()))
                    .forEach(equipo -> {
                        equipo.getJugadores().stream()
                                .filter(jugador1 -> Objects.equals(jugador1.getId(), jugador.getId()))
                                .forEach(jugador1 -> jugador1.setNombre(jugador.getNombre()));
                    });
            return jugador;
        } else {
            throw new ElementNotFoundException("No se ha actualizado el jugador");
        }
    }

    //borrar equipo comprobando FK
    public boolean borrarEquipo(String nombreEquipo) {
        try {
            Equipo equipoABorrar = equipos.stream()
                    .filter(equipo -> equipo.getNombre().equals(nombreEquipo)).findFirst().get();
            if (equipoABorrar.getJugadores().size() == 0) {
                equipos.remove(equipoABorrar);
                return true;
            } else {
                throw new ForeignKeyConstraintException("No se ha podido borrar el equipo porque tiene jugadores");
            }
        } catch (NoSuchElementException exception) {
            throw new ElementNotFoundException("No se ha podido borrar el equipo, no existe ninguno con ese nombre");
        }
    }

    //borrar jugador
    public Jugador borrarJugador(String nombreJugador) {
        try {
            Jugador jugadorABorrar = jugadores.stream().filter(jugador -> jugador.getNombre().equals(nombreJugador)).findFirst().get();
            jugadores.remove(jugadorABorrar);
            equipos.stream().filter(equipo -> equipo.getNombre().equals(jugadorABorrar.getNombreEquipo()))
                    .forEach(equipo -> {
                        equipo.getJugadores().remove(jugadorABorrar);
                    });
            return jugadorABorrar;
        } catch (NoSuchElementException exception) {
            throw new ElementNotFoundException("No se ha podido borrar el jugador, no existe ninguno con ese nombre");
        }
    }
}