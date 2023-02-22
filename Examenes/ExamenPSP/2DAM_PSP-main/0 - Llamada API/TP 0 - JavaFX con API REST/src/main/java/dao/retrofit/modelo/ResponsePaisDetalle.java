package dao.retrofit.modelo;

import com.squareup.moshi.Json;
import dao.common.ConstantesDAO;
import lombok.Data;

@Data
public class ResponsePaisDetalle{
	private String capital;
	private String emojiU;
	@Json(name = ConstantesDAO.CURRENCY_NAME) // las variables que vienen sin camel case del JSON se definen con esta linea
	private String currencyName;
	private String emoji;
	@Json(name = ConstantesDAO.CURRENCY_SYMBOL) // las variables que vienen sin camel case del JSON se definen con esta linea
	private String currencySymbol;
	private String subregion;
	private String latitude;
	private String phonecode;
	private String tld;
	@Json(name = ConstantesDAO.NUMERIC_CODE) // las variables que vienen sin camel case del JSON se definen con esta linea
	private String numericCode;
	private String timezones;
	private String jsonMemberNative;
	private String translations;
	private String name;
	private String currency;
	private int id;
	private String iso2;
	private String region;
	private String iso3;
	private String longitude;
}
