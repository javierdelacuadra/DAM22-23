package dao.impl;

import dao.DaoEstados;
import dao.retrofit.llamadas.WorldApi;
import dao.retrofit.modelo.ResponseStateItem;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.*;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static dao.common.ConstantesDAO.*;

public class DaoEstadosImpl implements DaoEstados {

    private final WorldApi worldApi;

    @Inject
    public DaoEstadosImpl(WorldApi worldApi) {
        this.worldApi = worldApi;
    }


    @Override
    public Either<String, List<Estado>> getEstadosPorPais(Pais paisSeleccionado) {
        /* CODIGO PARA LA LLAMADA A LA API*/
        Either<String, List<Estado>> either;
        try {
            Response<List<ResponseStateItem>> r = worldApi.getStatesByCountry(paisSeleccionado.iso2()).execute();
            if (r.isSuccessful() && r.body() != null) {
                List<ResponseStateItem> responseStateItems = r.body();
                List<Estado> estados = responseStateItems.stream().map(responseStateItem -> new Estado(responseStateItem.getId(), responseStateItem.getName(), responseStateItem.getIso2())).toList();
                if (estados.isEmpty()) {
                    either = Either.left(paisSeleccionado.nombre() + SPACE + NO_TIENE_ESTADOS);
                } else {
                    either = Either.right(estados);
                }
            } else {
                if (r.errorBody() != null) {
                    either = Either.left(ERROR_AL_OBTENER_LOS_ESTADOS_DE + paisSeleccionado.nombre() + BREAK + r.errorBody().string());
                } else {
                    either = Either.left(ERROR_AL_OBTENER_LOS_ESTADOS_DE + paisSeleccionado.nombre() + BREAK + ERROR_EN_LA_REQUEST);
                }
            }
        } catch (Exception e) {
            either = Either.left(ERROR_AL_OBTENER_LOS_ESTADOS_DE + paisSeleccionado.nombre() + BREAK + e.getMessage());
        }
        return either;
    }
}