package com.bilgeadam;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import lombok.Cleanup;

public class PDFBoxExample
{
	public static void main(String[] args)
	{
		PDFBoxExample pdfBox = new PDFBoxExample();
		
		try
		{
			pdfBox.createPDF();
			pdfBox.createPdfWithImage();
			pdfBox.readPDF();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void createPDF() throws IOException
	{
		@Cleanup
		PDDocument pdf = new PDDocument();
		PDPage page = new PDPage();
		
		pdf.addPage(page);
		
		@Cleanup
		PDPageContentStream content = new PDPageContentStream(pdf, page);
		
		content.beginText();
		
		content.setFont(PDType1Font.TIMES_BOLD, 14);
		content.setLeading(14.5f);
		// page size for A4 "792x612"
		content.newLineAtOffset(15, 765);
		content.showText("PDFBox Example");
		
		content.setFont(PDType1Font.TIMES_ROMAN, 12);
		content.newLine();
		content.showText("PDFBox Example");
		
		content.endText();
		content.close();
		
		pdf.save("D:\\javalib\\PDFExcel\\PDFBoxExample.pdf");
	}
	
	private void createPdfWithImage() throws IOException
	{
		String imagePath = "D:\\javalib\\PDFExcel\\src\\main\\resources\\profile.png";
		
		@Cleanup
		PDDocument pdf = new PDDocument();
		PDPage page = new PDPage();
		
		pdf.addPage(page);
		
		PDImageXObject image = PDImageXObject.createFromFile(imagePath, pdf);
		
		int width = image.getWidth() / 5;
		int height = image.getHeight() / 5;
		
		@Cleanup
		PDPageContentStream content = new PDPageContentStream(pdf, page);
		
		// x, y, size x, size y.
		content.drawImage(image, (612 - width) / 2, (792 - height) / 2, width, height);
		
		content.close();
		
		pdf.save("D:\\javalib\\PDFExcel\\PDFBoxImageExample.pdf");
	}
	
	private void readPDF() throws IOException
	{
		String documentPath = "D:\\javalib\\PDFExcel\\PDFBoxExample.pdf";
		
		PDDocument doc = PDDocument.load(new File(documentPath));
		PDFTextStripper stripper = new PDFTextStripper();
		String text = stripper.getText(doc);
		System.out.println(text);
	}
}
