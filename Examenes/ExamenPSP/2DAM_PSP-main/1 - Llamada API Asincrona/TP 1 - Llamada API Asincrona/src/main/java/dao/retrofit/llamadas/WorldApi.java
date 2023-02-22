package dao.retrofit.llamadas;

import dao.common.ConstantesDAO;
import dao.retrofit.modelo.*;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface WorldApi {

    @GET(ConstantesDAO.COUNTRIES)
    Single<List<ResponsePaisItem>> getAllCountriesAsync();

    @GET(ConstantesDAO.COUNTRIES_COUNTRY_ISO_STATES)
    Call<List<ResponseStateItem>> getStatesByCountry(@Path(ConstantesDAO.COUNTRY_ISO) String countryISO);

    @GET(ConstantesDAO.COUNTRIES_COUNTRY_ISO_STATES_STATE_ISO_CITIES)
    Call<List<ResponseCityItem>> getCitiesByCountryAndState(@Path(ConstantesDAO.COUNTRY_ISO) String countryISO, @Path(ConstantesDAO.STATE_ISO) String stateISO);

    @GET(ConstantesDAO.COUNTRIES_COUNTRY_ISO)
    Single<ResponsePaisDetalle> getPaisDetalleAsync(@Path(ConstantesDAO.COUNTRY_ISO) String countryISO);

}
