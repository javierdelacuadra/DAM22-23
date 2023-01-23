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

public class ListCarpetasViewModel {

    private final ServiciosMensajes servicios;
    private final ServiciosCarpetas serviciosCarpetas;
    private final ObjectProperty<ListCarpetasState> state;

    @Inject
    public ListCarpetasViewModel(ServiciosMensajes servicios, ServiciosCarpetas serviciosCarpetas) {
        this.servicios = servicios;
        this.serviciosCarpetas = serviciosCarpetas;
        state = new SimpleObjectProperty<>(new ListCarpetasState(null, null, null));
    }

    public ObjectProperty<ListCarpetasState> getState() {
        return state;
    }


    public void cargarMensajes(Carpeta carpeta) {
        servicios.getMensajes(String.valueOf(carpeta.getId()))
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new ListCarpetasState(null, either.get(), null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft()));
                    }
                });
    }

    public void addMensaje(Mensaje mensaje) {
        servicios.addMensaje(mensaje)
                .subscribe(either -> {
                    if (either.isRight()) {
                        ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(state.get().mensajes);
                        mensajes.add(either.get());
                        state.set(new ListCarpetasState(null, mensajes, null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft()));
                    }
                });
    }

    public void updateMensaje(Mensaje mensaje) {
        servicios.updateMensaje(mensaje)
                .subscribe(either -> {
                    if (either.isRight()) {
                        ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(state.get().mensajes);
                        mensajes.removeIf(m -> m.getId() == mensaje.getId());
                        mensajes.add(either.get());
                        state.set(new ListCarpetasState(null, mensajes, null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft()));
                    }
                });
    }

    public void deleteMensaje(Mensaje mensaje) {
        servicios.deleteMensaje(String.valueOf(mensaje.getId()))
                .subscribe(either -> {
                    if (either.isRight()) {
                        ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(state.get().mensajes);
                        mensajes.removeIf(m -> m.getId() == mensaje.getId());
                        state.set(new ListCarpetasState(null, mensajes, null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft()));
                    }
                });
    }

    public void getCarpetas(int id) {
        serviciosCarpetas.getCarpetas(String.valueOf(id))
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new ListCarpetasState(either.get(), null, null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft()));
                    }
                });
    }
}
