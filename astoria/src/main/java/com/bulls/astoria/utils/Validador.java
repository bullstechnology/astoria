package com.bulls.astoria.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public final class Validador {
	
	private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static boolean esEntero(String cadena){
		if (cadena.matches("[0-9]*"))
			  return true;
			else
			  return false;
	}
	
	
	public static boolean esLongitudCorrecta(String cadena, Integer minimo, Integer maximo){
		if (cadena.length()>= minimo && cadena.length() <= maximo){
			return true;
		}  else  {
			return false;
		}
	}
	
	public static boolean esCorreoCorrecto(String cadena){
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(cadena);
        return matcher.matches();
	}
	
	 public static boolean esDecimal(String cad) {
		 try {
			 	Double.parseDouble(cad);
			 	return true;
		 } catch(NumberFormatException nfe)	 {
			 	return false;
		 }
	 }

}
