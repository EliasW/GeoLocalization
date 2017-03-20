package com.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.CustomerService;
@Component
public class CsvRead {
	@Autowired
	CustomerService select = new CustomerService();
	BufferedReader importBuffer = null;
	String line = "";
	String cvsSplitBy = ",";
	public void CsvRead(String filename){

		
		
	//	return null;		
	}
}
