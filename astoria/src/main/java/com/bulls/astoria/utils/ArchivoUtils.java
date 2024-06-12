package com.bulls.astoria.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public final class ArchivoUtils implements Serializable{

	
	public static void crearArchivo(String url,InputStream file){

		OutputStream outputStream = null;
		try {
		outputStream = new FileOutputStream(new File(url));
        int read = 0;
	    byte[] bytes = new byte[1024];
        while ((read = file.read(bytes)) != -1) {
        		outputStream.write(bytes, 0, read);
        }
        
        outputStream.close();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public static String getExtencion(String nombre){

		int posicion = nombre.lastIndexOf(".");
		String extencion = nombre.substring(posicion, nombre.length());
		return extencion;
	}
}
