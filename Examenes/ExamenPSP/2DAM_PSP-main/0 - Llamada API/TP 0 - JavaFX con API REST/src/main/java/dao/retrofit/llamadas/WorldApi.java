package dao.retrofit.llamadas;

import dao.common.ConstantesDAO;
import dao.retrofit.modelo.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface WorldApi {

    @GET(ConstantesDAO.COUNTRIES)
    Call<List<ResponsePaisItem>> getAllCountries();

    @GET(ConstantesDAO.COUNTRIES_COUNTRY_ISO_STATES)
    Call<List<ResponseStateItem>> getStatesByCountry(@Path(ConstantesDAO.COUNTRY_ISO) String countryISO);

    @GET(ConstantesDAO.COUNTRIES_COUNTRY_ISO_STATES_STATE_ISO_CITIES)
    Call<List<ResponseCityItem>> getCitiesByCountryAndState(@Path(ConstantesDAO.COUNTRY_ISO) String countryISO, @Path(ConstantesDAO.STATE_ISO) String stateISO);

    @GET(ConstantesDAO.COUNTRIES_COUNTRY_ISO)
    Call<ResponsePaisDetalle> getPaisDetalle(@Path(ConstantesDAO.COUNTRY_ISO) String countryISO);

}
