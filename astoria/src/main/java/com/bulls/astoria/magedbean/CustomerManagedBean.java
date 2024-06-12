package com.bulls.astoria.magedbean;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.primefaces.event.RowEditEvent;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.bulls.astoria.persistence.Customer;
import com.bulls.astoria.service.CustomerService;

@ManagedBean(name="customerMB")
@RequestScoped
public class CustomerManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "success";
    private static final String ERROR   = "error";

    @ManagedProperty(value="#{CustomerService}")
    CustomerService customerService;

    List<Customer> customerList;

    private int id;
    private String name;
    private String surname;
 
    public String addCustomer() {
        try {
            Customer customer = new Customer();
            customer.setId(getId());
            customer.setName(getName());
            customer.setSurname(getSurname());
            getCustomerService().addCustomer(customer);
            reset();
            return SUCCESS;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return ERROR;
    }

    public String updateCustomer(Customer customer) {
        try {
            getCustomerService().updateCustomer(customer);
            return SUCCESS;       
        } catch (DataAccessException e) {
            e.printStackTrace();       
        }    
        return ERROR;
    } 
 
    public String deleteCustomer(Customer customer) {
        try {
            getCustomerService().deleteCustomer(customer);
            customerList = null;
            getCustomerList();
            return SUCCESS;       
        } catch (DataAccessException e) {
            e.printStackTrace();       
        }    
        return ERROR;
    }

    public void onEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        updateCustomer((Customer)event.getObject());
    }
    
    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Item Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }   
 
    public void reset() {
        this.setId(0);
        this.setName("");
        this.setSurname("");
    }

    public List<Customer> getCustomerList() {
        if(customerList == null){
            customerList = new ArrayList<Customer>();
            customerList.addAll(getCustomerService().getCustomers());
        }
        return customerList;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public void borrarSession(){
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("aerolineaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("cargaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarNotasMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoDespachadoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("buscarPedidoProgramadoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("catalogoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("clienteMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("comisionesMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("conciliarPedidoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearCiudadMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearFlorMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("notasMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("crearPaisMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("pedidoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("customerMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("destinoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarAerolineaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarCargaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarClaveMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarClienteMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarListaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPedidoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarPlantacionMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("editarUsuarioMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("empresaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("estadoCuentaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("franjaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gradoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("gradomultipleMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("handlerMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idiomaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("invoicePedidoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginMgmtBean");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("personaMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("plantacionMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("preciosMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("productoMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("truckMB");
  	//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioMB");
  	  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("variedadMB");

     }

}
