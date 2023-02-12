package com.minicursoadsfg.minicursoadsfg.comum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UtilApi {
	
	
	public static UtilApi novaInstancia() {return new UtilApi();};
	
	@Value("${release.version}")
	private  String releaseVersion;
	
	@Value("${api.version}")
	private  String apiVersion;
	
	public  String RELEASE_VERSION() {
		return releaseVersion;
	}
	
	public  String API_VERSION() {
		return apiVersion;
	}
	
	public boolean emailEhValido(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expressao = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expressao, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	
	/**
	 * Retorna um DateTimeFormatter com o pdrão yyyy-MM-dd
	 * @return
	 */
	public static DateTimeFormatter getDateFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
	
	
	/**
	 * Retorna um DateTimeFormatter com o padrão "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" em conformidade com a ISO8601
	 * @return
	 */
	public static DateTimeFormatter getDateTimeFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	}
	

	/**
	 * Retornará a data formatada no padrao ISO8601 a partir da string informada
	 * @param dateAsString
	 * @return
	 * @throws ParseException
	 */
	public static LocalDateTime obterLocalDateTimeAtravesDeString(String dateAsString) throws ParseException{
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateISO8601 = inputFormat.parse(dateAsString);
        return dateISO8601.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	
	/**
	 * Converte uma data para uma data com tempo
	 * @param date
	 * @return
	 */
	public static LocalDateTime converterLocalDateParaLocalDateTime(LocalDate date) {
		return date.atTime(0, 0, 0);
	}
}
