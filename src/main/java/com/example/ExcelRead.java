package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A dirty simple program that reads an Excel file.
 * 
 * @author www.codejava.net
 *
 */
public class ExcelRead {

	public String[] ExcelRead(String filename) throws IOException {
		//String excelFilePath = "Books.xlsx";
		String[] excelFile = null;
		FileInputStream inputStream;

		try {
			inputStream = new FileInputStream(new File(filename));
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				int i = 0;
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					excelFile[i] = cell.getStringCellValue();
					i++;
					System.out.print(cell.getStringCellValue());
				}
				System.out.println();
			}

			workbook.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return excelFile;

	}
}