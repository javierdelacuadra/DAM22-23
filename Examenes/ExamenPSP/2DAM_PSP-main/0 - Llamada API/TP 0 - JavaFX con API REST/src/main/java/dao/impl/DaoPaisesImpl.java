package dao.impl;

import dao.DaoPaises;
import dao.common.ConstantesDAO;
import dao.retrofit.llamadas.WorldApi;
import dao.retrofit.modelo.ResponsePaisDetalle;
import dao.retrofit.modelo.ResponsePaisItem;
import dominio.modelo.PaisDetalle;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import dominio.modelo.Pais;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DaoPaisesImpl implements DaoPaises {

    private final WorldApi worldApi;

    @Inject
    public DaoPaisesImpl(WorldApi worldApi) {
        this.worldApi = worldApi;
    }

    @Override
    public Either<String, List<Pais>> getTodosLosPaises() {
        Response<List<ResponsePaisItem>> r;
        try {
            r = worldApi.getAllCountries().execute();
            if (r.isSuccessful() && r.body() != null) {
                List<ResponsePaisItem> paisesItems = r.body();
                List<Pais> paises = paisesItems.stream().map(paisItem -> new Pais(paisItem.getId(), paisItem.getName(), paisItem.getIso2())).toList();
                if (paises.isEmpty()) {
                    return Either.left(ConstantesDAO.NO_HAY_PAISES);
                } else {
                    return Either.right(paises);
                }
            } else {
                return Either.left(r.message());
            }
        } catch (IOException e) {
            return Either.left(ConstantesDAO.ERROR_AL_OBTENER_LOS_PAISES + ConstantesDAO.BREAK + e.getMessage());
        }

    }


    @Override
    public Either<String, Pais> getPais(String pais) {
        Either<String, List<Pais>> paisList = getTodosLosPaises();
        if (paisList != null) {
            return Either.right(paisList.get().get(paisList.get().indexOf(new Pais(0, pais, ""))));
        } else return Either.left(ConstantesDAO.EL_PAIS_NO_EXISTE_AUN);
    }

    @Override
    public Either<String, PaisDetalle> getPaisDetalle(String iso) {
        try {
            Response<ResponsePaisDetalle> r = worldApi.getPaisDetalle(iso).execute();
            if (r.isSuccessful() && r.body() != null) {
                ResponsePaisDetalle respPais = r.body();
                PaisDetalle paisDetalle = new PaisDetalle(respPais.getName(), respPais.getCapital(), respPais.getRegion(),
                        (respPais.getCurrencyName() + ConstantesDAO.COMA + respPais.getCurrency() + ConstantesDAO.SPACE + respPais.getCurrencySymbol())
                        , respPais.getPhonecode(), respPais.getTld());
                return Either.right(paisDetalle);
            } else {
                return Either.left(r.message());
            }
        } catch (Exception e) {
            return Either.left(ConstantesDAO.EL_PAIS_NO_EXISTE_AUN + ConstantesDAO.BREAK + e.getMessage());
        }
    }
}
