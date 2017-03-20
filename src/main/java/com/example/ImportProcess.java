package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

//import com.example.CustomerRepositoryImpl;

import com.example.CustomerService;
import com.example.AddressConverter;
import com.example.GoogleResponse;
import com.example.Result;
import com.example.ExcelRead;
import com.example.CsvRead;

import com.jayway.jsonpath.internal.function.text.Concatenate;
import com.vaadin.data.util.filter.IsNull;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

@Component
public class ImportProcess {
	@Autowired
	CustomerService select = new CustomerService();
	private List updateCustomer;
//	@Autowired
	ExcelRead excelRead = new ExcelRead();
//	@Autowired
	CsvRead csvRead = new CsvRead();

	List buffureList = new LinkedList<String>();

	// @Override
	@SuppressWarnings("null")
	public void upload() throws IOException {

		JFileChooser importFile = new JFileChooser();
		String importcsvFile = null;
		String modifycsvFile = "/Algol_Documents/Algol Documents/Java/Java Dev/UpdateMap/modify.csv";
		BufferedReader importBuffer = null;
		BufferedReader modifyBuffer = null;
		String line = "";
		String cvsSplitBy = ",";
		int apiCalls = 0;

		FileNameExtensionFilter filter = new FileNameExtensionFilter("csv and excel files", "csv", "xlsx");
		importFile.setFileFilter(filter);
		importFile.setCurrentDirectory(new java.io.File("."));
		importFile.setDialogTitle("choosertitle");
		importFile.setAcceptAllFileFilterUsed(false);

		String extension = "";

		System.out.println("length of imported file ");
		if (importFile.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): " + importFile.getCurrentDirectory());
			System.out.println("getSelectedFile() : " + importFile.getSelectedFile());
			importcsvFile = importFile.getSelectedFile().getName();			

			int i = importcsvFile.lastIndexOf('.');
			int p = Math.max(importcsvFile.lastIndexOf('/'), importcsvFile.lastIndexOf('\\'));

			if (i > p) {
				extension = importcsvFile.substring(i + 1);
			}
			System.out.println("File extension: " + extension);			
			switch (extension) {
			case "csv":
				System.out.println("File extension: " + extension);
				//csvRead.CsvRead(importcsvFile);
				/* to browse csv file */	
				try {

					importBuffer = new BufferedReader(new FileReader(importcsvFile));
					while ((line = importBuffer.readLine()) != null) {
						// use comma as separator
						String[] importedFile = line.split(cvsSplitBy);
						System.out.println("Nome Azienda =" + importedFile[2] + " , Codice Cliente=" + importedFile[1]
								+ " , Regione Sociale=" + importedFile[3] + " , Indrizzo=" + importedFile[4] + " , comune="
								+ importedFile[5] + " , provincia=" + importedFile[6] + " , stato=" + importedFile[7]
								+ "]");
						
						Customer customers = select.selectCustomer(importedFile[1]);
						String value = importedFile[4] + "," + importedFile[6] + "," + "Italy";
						System.out.println("Address to convert: " + value);
						if (customers != null) {
							if (!(customers.getCodiceCliente().equals("codice_cliente"))) {
								System.out.println(" Customer is not null ");
								if (!(customers.getIndrizzo().equals(importedFile[4]))){
								//if (customers.Indrizzo() != importedFile[4]) {
									System.out.println(" Customer Indrizzo is not null ");
									System.out.printf("Matched Codice Cliente: " + customers.getCodiceCliente());
									System.out.println(" Matched Indrizzo: " + customers.getIndrizzo());
									/*
									 * TODO GET LAN & LOG AND UPDATE THE TABLE
									 */
									GoogleResponse res = new AddressConverter().convertToLatLong(value);
									if (res.getStatus().equals("OK")) {
										for (Result result : res.getResults()) {
											System.out.println("Lattitude of address is :"
													+ result.getGeometry().getLocation().getLat());
											System.out.println("Longitude of address is :"
													+ result.getGeometry().getLocation().getLng());
											System.out.println("Location is " + result.getGeometry().getLocation_type());
											select.updateTable(importedFile[1], importedFile[4],
													result.getGeometry().getLocation().getLat(),
													result.getGeometry().getLocation().getLng());
										}
									} else {
										System.out.println(res.getStatus());
									}
								}
							}
						} if (customers == null) {
						{
							if (!(importedFile[1].equals("codice_cliente"))) {
								// insert the new customer into database
								GoogleResponse res = new AddressConverter().convertToLatLong(value);
								if (res.getStatus().equals("OK")) {
									System.out.println("Implementation of NULL");								
								
									boolean j = true;
									for (Result result : res.getResults()) {
										System.out.println(
												"Lattitude of address is :" + result.getGeometry().getLocation().getLat());
										System.out.println(
												"Longitude of address is :" + result.getGeometry().getLocation().getLng());
										System.out.println("Location is " + result.getGeometry().getLocation_type());
										if(j==true){
											j = false;
											select.insertCustomer(importedFile[2], importedFile[1], importedFile[3],
												importedFile[4], importedFile[5], importedFile[6], "Italy",
												result.getGeometry().getLocation().getLat(),
												result.getGeometry().getLocation().getLng());
										}

									}
								} else {
									System.out.println(res.getStatus());
								}
							}
						}

					}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (importBuffer != null) {
						try {
							importBuffer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			

			default:
				break;
			}

		} else {
			System.out.println("No Selection ");
		}

	}

}
