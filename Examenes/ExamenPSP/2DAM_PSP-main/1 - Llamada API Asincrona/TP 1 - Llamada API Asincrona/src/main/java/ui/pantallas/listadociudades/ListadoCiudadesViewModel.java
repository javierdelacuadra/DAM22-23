package ui.pantallas.listadociudades;

import dominio.modelo.*;
import dominio.serivcios.impl.ServiciosCiudadesImpl;
import dominio.serivcios.impl.ServiciosEstadosImpl;
import dominio.serivcios.impl.ServiciosPaisesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

public class ListadoCiudadesViewModel {

    ServiciosPaisesImpl serviciosPaisesImpl;
    ServiciosEstadosImpl serviciosEstadosImpl;

    ServiciosCiudadesImpl serviciosCiudadesImpl;

    @Inject
    public ListadoCiudadesViewModel(ServiciosPaisesImpl serviciosPaisesImpl, ServiciosEstadosImpl serviciosEstadosImpl, ServiciosCiudadesImpl serviciosCiudadesImpl) {
        this.serviciosPaisesImpl = serviciosPaisesImpl;
        this.serviciosEstadosImpl = serviciosEstadosImpl;
        this.serviciosCiudadesImpl = serviciosCiudadesImpl;
        state = new SimpleObjectProperty<>(new ListadoCiudadesState(null, null, null, null, false));
    }

    private final ObjectProperty<ListadoCiudadesState> state;

    public ReadOnlyObjectProperty<ListadoCiudadesState> getState() {
        return state;
    }

    public void loadPaisesSingleRetrofit() {
        ListadoCiudadesState currentState = state.getValue();
        state.setValue(new ListadoCiudadesState(currentState.getError(), currentState.getPaisesList(), currentState.getEstadoList(), currentState.getCiudadesList(), true));
        serviciosPaisesImpl.getTodosLosPaisesLlamadaRetrofitSingle()
                .observeOn(Schedulers.single())
                .delay(1, java.util.concurrent.TimeUnit.SECONDS)
                .subscribe(either -> {
                    ListadoCiudadesState lc;
                    if (either.isLeft())
                        lc = new ListadoCiudadesState(either.getLeft(), null, null, null, false);
                    else
                        lc = new ListadoCiudadesState(null, either.get(), null, null, false);
                    state.setValue(lc);
                });
    }

    public Either<String, List<Ciudad>> getCiudadesPorEstadoAsyncConTask(Estado estado, Pais paisSelected) {
        return serviciosCiudadesImpl.getCiudadesPorEstado(estado, paisSelected);
    }

    public Either<String, List<Estado>> getEstadosPorPaisAsyncConSingleEnUI(Pais pais) {
        return serviciosEstadosImpl.getEstadosPorPais(pais);
    }
}
