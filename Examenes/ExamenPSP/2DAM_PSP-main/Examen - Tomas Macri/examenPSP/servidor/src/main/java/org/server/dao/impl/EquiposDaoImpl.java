package org.server.dao.impl;

import org.server.dao.EquiposDao;
import org.server.domain.modelo.errores.BaseDeDatosException;
import org.server.domain.modelo.errores.DataIntegrityException;
import org.server.domain.modelo.errores.NoFoundException;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EquiposDaoImpl implements EquiposDao {
    private static final List<Equipo> equipos = new ArrayList<>();


    static {
        List<Jugador> jugadores = new ArrayList<>();
        Equipo argentina = new Equipo("Argentina", "Lionel Scaloni", new ArrayList<>());
        equipos.add(argentina);
        argentina.getJugadores().add(new Jugador(getNewID(), "Lionel Messi", "Argentina"));
        argentina.getJugadores().add(new Jugador(getNewID(), "Lautaro Martinez", "Argentina"));
        argentina.getJugadores().add(new Jugador(getNewID(), "Marcos Acuña", "Argentina"));

        Equipo espana = new Equipo("España", "Luis Enrique", new ArrayList<>());
        equipos.add(espana);
        espana.getJugadores().add(new Jugador(getNewID(), "Gavi", "España"));
        espana.getJugadores().add(new Jugador(getNewID(), "Koke", "España"));
        espana.getJugadores().add(new Jugador(getNewID(), "Pedri", "España"));


        Equipo brasil = new Equipo("Brasil", "Tite", new ArrayList<>());
        equipos.add(brasil);
        brasil.getJugadores().add(new Jugador(getNewID(), "Neymar", "Brasil"));
        brasil.getJugadores().add(new Jugador(getNewID(), "Alisson", "Brasil"));
        brasil.getJugadores().add(new Jugador(getNewID(), "Richarlison", "Brasil"));


        Equipo qatar = new Equipo("Qatar", "Alguien", new ArrayList<>());
        equipos.add(qatar);

    }

    @Override
    public List<Equipo> getAllSinJugadores() {
        List<Equipo> equiposSimples = equipos.stream().map(equipo -> new Equipo(equipo.getNombre(), equipo.getEntrenador())).toList();
        if (!equiposSimples.isEmpty()) {
            return equiposSimples;
        }
        throw new NoFoundException("No hay equipos en la bd");
    }

    @Override
    public List<Equipo> getAll() {
        if (!equipos.isEmpty()) {
            return equipos;
        }
        throw new NoFoundException("No hay equipos en la bd");
    }

    @Override
    public List<Jugador> getJugadoresPorEquipo(String nombre) {
        Equipo equipo = getEquipoByNombreOrElseNull(nombre);
        String mensaje;
        if (equipo != null) {
            List<Jugador> jugadors = equipo.getJugadores();
            if (!jugadors.isEmpty()) {
                return jugadors;
            } else {
                mensaje = "El equipo " + nombre + " existe pero aún no tiene jugadores";
            }
        } else {
            mensaje = "El equipo " + nombre + " no existe.";
        }
        throw new NoFoundException(mensaje);
    }

    @Override
    public Equipo getEquipo(String nombre) {
        Equipo equipo = getEquipoByNombreOrElseNull(nombre);
        if (equipo != null) {
            return equipo;
        }
        throw new NoFoundException("El equipo " + nombre + " no existe");
    }

    private Equipo getEquipoByNombreOrElseNull(String nombre) {
        return equipos.stream().filter(equipo1 -> equipo1.getNombre().equalsIgnoreCase(nombre)).findFirst().orElse(null);
    }

    @Override
    public Jugador addPlayer(String nEquipo, Jugador jugador) {
        Equipo equipo = getEquipoByNombreOrElseNull(nEquipo);
        if (equipo != null) {
            List<Jugador> jugs = equipo.getJugadores();
            if (!jugs.contains(jugador)) {
                //Mantenemos nombre del equipo para el jugador que se meta
                jugador.setEquipo(equipo.getNombre());
                jugador.setId(getNewID());
                equipo.getJugadores().add(jugador);
                return jugador;
            } else {
                throw new BaseDeDatosException("Ya existe un jugador con el id " + jugador.getId() + " en " + nEquipo);
            }
        } else {
            throw new NoFoundException("El equipo " + nEquipo + " no existe");
        }

    }

    @Override
    public Equipo addTeam(Equipo equipo) {
        if (!equipos.contains(equipo)) {
            equipos.add(new Equipo(equipo.getNombre(), equipo.getEntrenador(), new ArrayList<>()));
            // meto mismo nombre del equipo por las dudas
            equipo.getJugadores().forEach(jugador -> {
                jugador.setEquipo(equipo.getNombre());
                jugador.setId(getNewID());
                equipos.get(equipos.indexOf(equipo)).getJugadores().add(jugador);
            });
            return equipo;
        } else {
            throw new BaseDeDatosException("Ya existe un equipo con el nombre " + equipo.getNombre());
        }
    }

    @Override
    public Jugador editPlayer(String nEquipo, Jugador jugador){
        Equipo equipo = getEquipoByNombreOrElseNull(nEquipo);
        String mensaje;
        if (equipo != null){
            List<Jugador> currentPlayers = equipo.getJugadores();
            int posiJugador = currentPlayers.indexOf(jugador);
            if (posiJugador > -1){
                equipo.getJugadores().set(posiJugador, jugador);
                return jugador;
            } else {
                mensaje = ("No hay ningun jugador con el id " + jugador.getId() + " en " + nEquipo);
            }
        } else {
            mensaje = ("El equipo " + nEquipo + " no existe");
        }
        throw new NoFoundException(mensaje);
    }

    @Override
    public Equipo deleteTeam(String nombre) {
        Equipo equipo = getEquipoByNombreOrElseNull(nombre);
        if (equipo != null){
            if (equipo.getJugadores().isEmpty()){
                equipos.remove(equipo);
                return equipo;
            } else {
                throw new DataIntegrityException(nombre + " tiene jugadores. Mátalos primero y luego borra el equipo.");
            }
        } else {
            throw new NoFoundException("El equipo " + nombre + " no existe");
        }
    }

    @Override
    public Jugador deletePlayer(String id) {
        Jugador j = equipos.stream().flatMap(equipo -> equipo.getJugadores().stream()).filter(jugador -> (jugador.getId()+"").equals(id)).findFirst().orElse(null);
        if (j != null){
            Equipo equipoPlayer = new Equipo(j.getEquipo(), "", new ArrayList<>());
            equipos.get(equipos.indexOf(equipoPlayer)).getJugadores().remove(j);
            return j;
        } else {
            throw new NoFoundException("El jugador con el id " + id + " no existe");
        }

    }

    private static int getNewID() {
        return equipos.stream().flatMap(equipo -> equipo.getJugadores().stream()).max(Comparator.comparingInt(Jugador::getId)).orElse(new Jugador(0, "", "")).getId() + 1;
    }
}
