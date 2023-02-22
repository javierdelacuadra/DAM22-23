package ui.pantallas.paisdetalle;

import dominio.serivcios.impl.ServiciosPaisesImpl;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;


public class PaisDetalleViewModel {

    ServiciosPaisesImpl serviciosPaisesImpl;

    @Inject
    public PaisDetalleViewModel(ServiciosPaisesImpl serviciosPaisesImpl) {
        this.serviciosPaisesImpl = serviciosPaisesImpl;
        state = new SimpleObjectProperty<>(new PaisDetalleState(null, null, null, false));
    }

    private final ObjectProperty<PaisDetalleState> state;

    public ReadOnlyObjectProperty<PaisDetalleState> getState() {
        return state;
    }

    public void loadPaisesSingleRetrofit() {
        PaisDetalleState currentState = state.getValue();
        state.setValue(new PaisDetalleState(currentState.getError(), currentState.getPaisesList(), currentState.getPaisDetalle(), true));
        serviciosPaisesImpl.getTodosLosPaisesLlamadaRetrofitSingle()
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    PaisDetalleState pd;
                    if (either.isLeft())
                        pd = new PaisDetalleState(either.getLeft(), null, null, false);
                    else
                        pd = new PaisDetalleState(null, either.get(), null, false);
                    state.setValue(pd);
                });
    }


    public void loadPaisDetalleSingleRetrofit(String iso) {
        PaisDetalleState currentState = state.getValue();
        state.setValue(new PaisDetalleState(currentState.getError(), currentState.getPaisesList(), currentState.getPaisDetalle(), true));
        serviciosPaisesImpl.getPaisDetalleLlamadaRetrofitSingle(iso)
                .observeOn(Schedulers.single())
                .subscribe(either -> {
                    PaisDetalleState pd;
                    if (either.isLeft())
                        pd = new PaisDetalleState(either.getLeft(), state.getValue().getPaisesList(), null, false);
                    else
                        pd = new PaisDetalleState(null, state.getValue().getPaisesList(), either.get(), false);
                    state.setValue(pd);
                });
    }

}
