package ui.pantallas.carpetascompartidas;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import modelo.Mensaje;
import servicios.ServiciosCarpetas;
import servicios.ServiciosMensajes;
import ui.pantallas.common.constantes.ConstantesPantallas;

public class CarpetasCompartidasViewModel {

    private final ServiciosCarpetas serviciosCarpetas;
    private final ServiciosMensajes serviciosMensajes;
    private final ObjectProperty<CarpetasCompartidasState> state;

    @Inject
    public CarpetasCompartidasViewModel(ServiciosCarpetas serviciosCarpetas, ServiciosMensajes serviciosMensajes) {
        this.serviciosCarpetas = serviciosCarpetas;
        this.serviciosMensajes = serviciosMensajes;
        state = new SimpleObjectProperty<>(new CarpetasCompartidasState(null, null));
    }

    public ObjectProperty<CarpetasCompartidasState> getState() {
        return state;
    }

    public void cargarCarpetaCompartida(String nombreUsuario, String nombreCarpeta, String passwordCarpeta) {
        serviciosCarpetas.cargarCarpetaCompartida(nombreUsuario, nombreCarpeta, passwordCarpeta)
                .subscribe(either -> {
                    if (either.isRight() && either.get().getMensajes() != null) {
                        if (either.isRight() && either.get().getMensajes().size() == 0) {
                            state.set(new CarpetasCompartidasState(either.get(), ConstantesPantallas.NO_HAY_MENSAJES_EN_ESTA_CARPETA));
                        } else if (either.isRight() && either.get().getMensajes().size() != 0) {
                            state.set(new CarpetasCompartidasState(either.get(), null));
                        } else {
                            state.set(new CarpetasCompartidasState(null, either.getLeft()));
                        }
                    } else {
                        state.set(new CarpetasCompartidasState(null, ConstantesPantallas.NO_SE_HA_ENCONTRADO_NINGUNA_CARPETA_CON_ESOS_DATOS));
                    }
                });
    }

    public void addMensaje(Mensaje mensaje) {
        mensaje.setIDCarpeta(state.get().carpeta.getId());
        serviciosMensajes.addMensaje(mensaje, state.get().carpeta.getPassword())
                .subscribe(either -> {
                    if (either.isRight()) {
                        state.get().carpeta.getMensajes().add(either.get());
                        state.set(new CarpetasCompartidasState(state.get().carpeta, null));
                    } else {
                        state.set(new CarpetasCompartidasState(null, either.getLeft()));
                    }
                });
    }
}
