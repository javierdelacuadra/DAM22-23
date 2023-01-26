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
import ui.pantallas.common.constantes.ConstantesPantallas;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
                        state.set(new ListCarpetasState(null, null, ConstantesPantallas.NO_HAY_NINGUNA_CARPETA_SELECCIONADA, true));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft(), false));
                    }
                });
    }

    public void addMensaje(Mensaje mensaje, String passCarpeta) {
        if (state.get().isLoaded == null || !state.get().isLoaded) {
            state.set(new ListCarpetasState(null, null, ConstantesPantallas.NO_SE_HA_INTRODUCIDO_LA_PASSWORD_DE_ESTA_CARPETA, false));
        } else {
            servicios.addMensaje(mensaje, passCarpeta)
                    .subscribe(either -> {
                        if (either.isRight()) {
                            List<Mensaje> mensajes;
                            mensajes = Objects.requireNonNullElseGet(state.get().mensajes, ArrayList::new);
                            mensajes.add(either.get());
                            state.set(new ListCarpetasState(null, mensajes, null, state.get().isLoaded));
                        } else {
                            state.set(new ListCarpetasState(null, null, either.getLeft(), state.get().isLoaded));
                        }
                    });
        }
    }

    public void updateMensaje(Mensaje mensaje, String passCarpeta) {
        if (state.get().isLoaded == null || !state.get().isLoaded) {
            state.set(new ListCarpetasState(null, null, ConstantesPantallas.NO_SE_HA_INTRODUCIDO_LA_PASSWORD_DE_ESTA_CARPETA, false));
        } else {
            servicios.updateMensaje(mensaje, passCarpeta)
                    .subscribe(either -> {
                        if (either.isRight()) {
                            List<Mensaje> mensajes = state.get().mensajes;
                            mensajes.removeIf(m -> m.getId() == mensaje.getId());
                            mensajes.add(either.get());
                            mensajes.sort(Comparator.comparingInt(Mensaje::getId));
                            state.set(new ListCarpetasState(null, mensajes, null, state.get().isLoaded));
                        } else {
                            state.set(new ListCarpetasState(null, null, either.getLeft(), state.get().isLoaded));
                        }
                    });
        }
    }

    public void deleteMensaje(Mensaje mensaje) {
        if (state.get().isLoaded == null || !state.get().isLoaded) {
            state.set(new ListCarpetasState(null, null, ConstantesPantallas.NO_SE_HA_INTRODUCIDO_LA_PASSWORD_DE_ESTA_CARPETA, false));
        } else {
            servicios.deleteMensaje(String.valueOf(mensaje.getId()))
                    .subscribe(either -> {
                        if (either.isRight()) {
                            ObservableList<Mensaje> mensajes = FXCollections.observableArrayList(state.get().mensajes);
                            mensajes.removeIf(m -> m.getId() == mensaje.getId());
                            mensajes.sort(Comparator.comparingInt(Mensaje::getId));
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

    public void cambiarPasswordCarpeta(Carpeta carpeta, String nuevaPass) {
        serviciosCarpetas.cambiarPasswordCarpeta(carpeta, nuevaPass)
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.set(new ListCarpetasState(serviciosCarpetas.getCarpetas(String.valueOf(carpeta.getIDUsuario())).blockingGet().getOrElse(() -> null), null, ConstantesPantallas.PASSWORD_CAMBIADA_CORRECTAMENTE, null));
                    } else {
                        state.set(new ListCarpetasState(null, null, either.getLeft(), null));
                    }
                });

    }
}
