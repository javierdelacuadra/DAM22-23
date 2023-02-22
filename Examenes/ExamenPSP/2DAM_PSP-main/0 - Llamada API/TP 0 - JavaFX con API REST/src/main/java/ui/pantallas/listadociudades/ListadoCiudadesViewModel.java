package ui.pantallas.listadociudades;

import dominio.modelo.*;
import dominio.serivcios.impl.ServiciosCiudadesImpl;
import dominio.serivcios.impl.ServiciosEstadosImpl;
import dominio.serivcios.impl.ServiciosPaisesImpl;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
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
        state = new SimpleObjectProperty<>(new ListadoCiudadesState(null, null, null, null));
    }

    private final ObjectProperty<ListadoCiudadesState> state;

    public ReadOnlyObjectProperty<ListadoCiudadesState> getState() {
        return state;
    }

    public void getEstadosPorPais(String pais) {
        Either<String, Pais> paisSeleccionado = serviciosPaisesImpl.getPais(pais);
        ListadoCiudadesState lc;
        if (paisSeleccionado.isRight()) {
            Either<String, List<Estado>> estadosPais = serviciosEstadosImpl.getEstadosPorPais(paisSeleccionado.get());
            if (estadosPais.isRight()) {
                lc = new ListadoCiudadesState(null, state.get().getNombrePaises(), estadosPais.get(), null);
            } else {
                lc = new ListadoCiudadesState(estadosPais.getLeft(), state.get().getNombrePaises(), null, null);
            }
        } else {
            lc = new ListadoCiudadesState(paisSeleccionado.getLeft(), state.get().getNombrePaises(), null, null);
        }
        state.setValue(lc);

    }

    public void getTodosLosPaises() {
        Either<String, List<Pais>> paises = serviciosPaisesImpl.getTodosLosPaises();
        ListadoCiudadesState lc;
        if (paises.isRight()) {
            List<String> paisesString = new ArrayList<>();
            for (Pais pais : paises.get()) {
                paisesString.add(pais.nombre());
            }
            lc = new ListadoCiudadesState(null, paisesString, null, null);
        } else {
            lc = new ListadoCiudadesState(paises.getLeft(), null, null, null);
        }
        state.setValue(lc);

    }

    public void getCiudadesPorEstado(Estado estado, String selectedItem) {
        Either<String, Pais> paisSeleccionado = serviciosPaisesImpl.getPais(selectedItem);
        ListadoCiudadesState lc;
        if (paisSeleccionado.isRight()) {
            Either<String, List<Ciudad>> listCiudades = serviciosCiudadesImpl.getCiudadesPorEstado(estado, paisSeleccionado.get());
            if (listCiudades.isRight()) {
                lc = new ListadoCiudadesState(null, state.get().getNombrePaises(), state.get().getEstadoList(), listCiudades.get());
            } else
                lc = new ListadoCiudadesState(listCiudades.getLeft(), state.get().getNombrePaises(), state.getValue().getEstadoList(), null);
        } else {
            lc = new ListadoCiudadesState(paisSeleccionado.getLeft(), null, null, null);
        }
        state.setValue(lc);
    }
}
