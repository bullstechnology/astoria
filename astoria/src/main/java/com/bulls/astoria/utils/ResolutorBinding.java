package com.bulls.astoria.utils;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class ResolutorBinding {   
	
	private static final String INICIO_REF = "#{" ;
    private static final String FIN_REF = "}" ;
    private static final String SEPARADOR_REF = ".";   
    private static final String[]  OBJETOS_IMPLICITOS_FACES = {"requestScope","param","paramValues","sessionScope","applicationScope","initParam"};
    
    
    public ResolutorBinding() {
        super();
    }    
    
    public static Object resolverBinding(String unNombreVariable){
        FacesContext facesContexto = FacesContext.getCurrentInstance();
        ValueBinding unValorBinding = null;
        Object unValor = null; 
        for(int i = 0; i < OBJETOS_IMPLICITOS_FACES.length; i++){
             String miReferencia = INICIO_REF + OBJETOS_IMPLICITOS_FACES[i] +
            		 	SEPARADOR_REF + unNombreVariable + FIN_REF;
             	unValorBinding = facesContexto.getApplication().createValueBinding(miReferencia); 
             	FacesContext context = FacesContext.getCurrentInstance();
             	//String bean = context.getApplication().evaluateExpressionGet(context, "#{beanName}", String.class);
             	
             	try{
             		unValor = unValorBinding.getValue(facesContexto);
             	}catch(FacesException unaFacesExcepcion){}            
             	if(unValor !=null){
                break;
                }
                }        
               return unValor;
    	}
    
}