package com.bilgeadam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ExcelExample
{
	public static void main(String[] args)
	{
		ExcelExample excel = new ExcelExample();
		try
		{
			excel.create();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<RowData> data = getData();

	private ArrayList<RowData> getData()
	{
		if (this.data == null)
		{
			this.data = new ArrayList<>();
			this.data.add(new RowData(1, "John", LocalDate.of(1998, 11, 23), 36.7f));
			this.data.add(new RowData(2, "Derek", LocalDate.of(1996, 1, 13), 50.7f));
			this.data.add(new RowData(3, "Mary", LocalDate.of(2000, 6, 22), 10.7f));
		}
		return this.data;
	}
	
	private void create() throws IOException
	{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data");
		// excel width unit is 1/256 of a character.
		sheet.setColumnWidth(3, 15*256);
		sheet.autoSizeColumn(2);
		
		XSSFRow row;
		int rowCount = 0;
		String[] header = {"ID", "Name", "Date", "Height"};
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(BorderStyle.THICK);
		headerStyle.setBorderTop(BorderStyle.THICK);
		headerStyle.setBorderLeft(BorderStyle.THICK);
		headerStyle.setBorderRight(BorderStyle.THICK);
		headerStyle.setFillForegroundColor((short)200);
		headerStyle.setFillPattern(FillPatternType.LEAST_DOTS);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		
		sheet.createRow(rowCount++);
		row = sheet.createRow(rowCount++);
		int columnCount = 0;
		row.createCell(columnCount++);
		
		for (String head : header)
		{
			Cell cell = row.createCell(columnCount++);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
		}
		
		for (RowData rowData : data)
		{
			row = sheet.createRow(rowCount++);
			columnCount = 0;
			row.createCell(columnCount++);
			Cell cell = row.createCell(columnCount++);
            cell.setCellValue(rowData.getNumber());
            cell = row.createCell(columnCount++);
            cell.setCellValue(rowData.getName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(rowData.getDate().toString());
            cell = row.createCell(columnCount++);
            cell.setCellValue(rowData.getHeight());
		}
		
		FileOutputStream fos = new FileOutputStream(new File("D:\\javalib\\PDFExcel\\test.xlsx"));
		workbook.write(fos);
		workbook.close();
	}
	
	@AllArgsConstructor
	@Getter
	private class RowData
	{
		private int number;
		private String name;
		private LocalDate date;
		private float height;
	}
}
