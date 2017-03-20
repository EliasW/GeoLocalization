package com.example;

import com.example.ImportProcess;
import com.vaadin.annotations.Theme;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	@Autowired
	private ImportProcess importProcess;
    @Autowired
    private CustomerService service;

    private Customer customer;

    private Grid grid = new Grid();
//    private TextField firstName = new TextField("Codice Cliente");
//    private TextField lastName = new TextField("Indrizzo");
    private TextField cliente = new TextField("Codice Cliente");
    private TextField indrizzo = new TextField("Indrizzo");
    private TextField CodiceCliente = new TextField("Codice Cliente");

    Button importButton = new Button("geolocalize"); 
    Button excute = new Button("Excute");
    Button export = new Button("Export");
    //private Button save = new Button("Save");
    private Button save = new Button("Save", e -> saveCustomer());

    @Override
    protected void init(VaadinRequest request) {
        updateGrid();

        //grid.setColumns("Codice Cliente");
        grid.setWidth("1300px");
        grid.addSelectionListener(e -> updateForm());
        importButton.addClickListener(e -> {try {
			importProcess.upload();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}});
        
        VerticalLayout layout = new VerticalLayout(importButton, grid, cliente, indrizzo,CodiceCliente,
                save);

       /* VerticalLayout layout = new VerticalLayout(importButton, excute, export, grid, cliente, indrizzo,CodiceCliente,
                save);
                */
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
    }

    private void updateGrid() {
        List customers = service.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class,
                customers));
        setFormVisible(false);
    }

    private void updateForm() {
        if (grid.getSelectedRows().isEmpty()) {
            setFormVisible(false);
        } else {
            customer = (Customer) grid.getSelectedRow();
            BeanFieldGroup.bindFieldsUnbuffered(customer, this);
            setFormVisible(true);
        }
    }

    private void setFormVisible(boolean visible) {
    	cliente.setVisible(visible);
    	indrizzo.setVisible(visible);
        CodiceCliente.setVisible(visible);
        save.setVisible(visible);
    }

    private void saveCustomer() {
        service.update(customer);
        updateGrid();
    }

}