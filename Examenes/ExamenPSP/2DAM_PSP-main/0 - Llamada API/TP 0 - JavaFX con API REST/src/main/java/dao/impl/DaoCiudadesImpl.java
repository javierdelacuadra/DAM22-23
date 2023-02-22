package dao.impl;

import dao.DaoCiudades;
import dao.retrofit.llamadas.WorldApi;
import dao.retrofit.modelo.ResponseCityItem;
import dominio.modelo.Estado;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Ciudad;
import dominio.modelo.Pais;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

import static dao.common.ConstantesDAO.*;

public class DaoCiudadesImpl implements DaoCiudades {

    private final WorldApi worldApi;

    @Inject
    public DaoCiudadesImpl(WorldApi worldApi) {
        this.worldApi = worldApi;
    }

    @Override
    public Either<String, List<Ciudad>> getCiudadesPorEstado(Estado estado, Pais paisSeleccionado) {
        //Llamada al método de la api que filtra las ciudades por estado
        Either<String, List<Ciudad>> either;
        try {
            //CODIGO LLAMADA A LA API PASANDO LOS ISO DE PAIS Y ESTADO
            Response<List<ResponseCityItem>> r = worldApi.getCitiesByCountryAndState(paisSeleccionado.iso2(), estado.iso2()).execute();
            if (r.isSuccessful() && r.body() != null) {
                List<ResponseCityItem> responseCityItems = r.body();
                List<Ciudad> ciudades = responseCityItems.stream().map(responseCityItem -> new Ciudad(responseCityItem.getId(), responseCityItem.getName())).toList();
                if (ciudades.isEmpty()) {
                    either = Either.left(estado.nombre() + COMA + paisSeleccionado.nombre() + SPACE + NO_TIENE_CIUDADES);
                } else {
                    either = Either.right(ciudades);
                }
            } else {
                if (r.errorBody() != null) {
                    either = Either.left(ERROR_AL_OBTENER_LAS_CIUDADES_DE + estado.nombre() + COMA + paisSeleccionado.nombre() + BREAK + r.errorBody().string());
                } else {
                    either = Either.left(ERROR_AL_OBTENER_LAS_CIUDADES_DE + estado.nombre() + COMA + paisSeleccionado.nombre() + BREAK + ERROR_EN_LA_REQUEST);
                }
            }

        } catch (Exception e) {
            either = Either.left(ERROR_AL_OBTENER_LAS_CIUDADES_DE + estado.nombre() + COMA + paisSeleccionado.nombre() + BREAK + e.getMessage());
        }
        return either;
    }
}
