package com.bulls.astoria.servlet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DynamicImageServlet extends HttpServlet {
	
	
	ResourceBundle bundleParam ;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

				String file = request.getParameter("file");
				bundleParam =  ResourceBundle.getBundle("parametros");
				

				BufferedInputStream in = new BufferedInputStream(new FileInputStream(bundleParam.getString("carpetafotos")+ file));

				byte[] bytes = new byte[in.available()];

				in.read(bytes);

				in.close();

				// Write image contents to response.

				response.getOutputStream().write(bytes);
			 
		} catch (IOException e) {
	
			e.printStackTrace();
		}

	}
 
}