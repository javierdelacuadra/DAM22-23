package ui.pantallas.listcarpetas;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Carpeta;
import modelo.Mensaje;
import servicios.ServiciosCarpetas;
import servicios.ServiciosMensajes;

import java.util.List;

public class ListCarpetasViewModel {

    private final ServiciosMensajes servicios;
    private final ServiciosCarpetas serviciosCarpetas;
    private final ObjectProperty<ListCarpetasState> state;

    @Inject
    public ListCarpetasViewModel(ServiciosMensajes servicios, ServiciosCarpetas serviciosCarpetas) {
        this.servicios = servicios;
        this.serviciosCarpetas = serviciosCarpetas;
        state = new SimpleObjectProperty<>(new ListCarpetasState(null, null, null, null));
    }

    public ObjectProperty<ListCarpetasState> getState() {
        return state;
    }


    public void cargarMensajes(Carpeta carpeta) {
        servicios.getMensajes(carpeta)
                .subscribe(either -> {
                    if (either.isRight() && either.get().size() != 0) {
                        state.set(new ListCarpetasState(null, either.get(), null, true));
                    } else if (either.isRight() && either.get().size() == 0) {
                        state.set(new ListCarpetasState(null, null, "No hay mensajes en esta carpeta", true));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft(), false));
                    }
                });
    }

    public void addMensaje(Mensaje mensaje, String nombreCarpeta) {
        if (state.get().isLoaded == null || !state.get().isLoaded) {
            state.set(new ListCarpetasState(null, null, "No se ha introducido la contraseña de esta carpeta", false));
        } else {
            servicios.addMensaje(mensaje, nombreCarpeta)
                    .subscribe(either -> {
                        if (either.isRight()) {
                            List<Mensaje> mensajes = state.get().mensajes;
                            mensajes.add(either.get());
                            state.set(new ListCarpetasState(null, mensajes, null, state.get().isLoaded));
                        } else {
                            state.set(new ListCarpetasState(null, null, either.getLeft(), state.get().isLoaded));
                        }
                    });
        }
    }

    public void updateMensaje(Mensaje mensaje, String nombreCarpeta) {
        if (state.get().isLoaded == null || !state.get().isLoaded) {
            state.set(new ListCarpetasState(null, null, "No se ha introducido la contraseña de esta carpeta", false));
        } else {
            servicios.updateMensaje(mensaje, nombreCarpeta)
                    .subscribe(either -> {
                        if (either.isRight()) {
                            List<Mensaje> mensajes = state.get().mensajes;
                            mensajes.removeIf(m -> m.getId() == mensaje.getId());
                            mensajes.add(either.get());
                            state.set(new ListCarpetasState(null, mensajes, null, state.get().isLoaded));
                        } else {
                            state.set(new ListCarpetasState(null, null, either.getLeft(), state.get().isLoaded));
                        }
                    });
        }
    }

    public void deleteMensaje(Mensaje mensaje) {
        if (state.get().isLoaded == null || !state.get().isLoaded) {
            state.set(new ListCarpetasState(null, null, "No se ha introducido la contraseña de esta carpeta", false));
        } else {
            servicios.deleteMensaje(String.valueOf(mensaje.getId()))
                    .subscribe(either -> {
                        if (either.isRight()) {
                            ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(state.get().mensajes);
                            mensajes.removeIf(m -> m.getId() == mensaje.getId());
                            state.set(new ListCarpetasState(null, mensajes, null, state.get().isLoaded));
                        } else {
                            state.set(new ListCarpetasState(null, null, either.getLeft(), state.get().isLoaded));
                        }
                    });
        }
    }

    public void getCarpetas(int id) {
        serviciosCarpetas.getCarpetas(String.valueOf(id))
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new ListCarpetasState(either.get(), null, null, null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft(), null));
                    }
                });
    }
}
