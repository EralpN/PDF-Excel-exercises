package com.bilgeadam;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ITextExample
{
	
	public static void main(String[] args)
	{
		ITextExample iText = new ITextExample();
		
		try
		{
			iText.createPdfFile();
			iText.pdf.open();
			iText.writePdf();
			iText.createImagePdf();
			iText.createTablePdf();
			iText.pdf.close();
			
		}
		catch (IOException | DocumentException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private final String FONT = "C:\\Windows\\Fonts\\l_10646.TTF";
	private Document pdf;
	private Font font;
	private PdfWriter writer;
	
	private void writePdf() throws DocumentException
	{
		this.pdf.add(new Paragraph("IText Example"));
		this.pdf.add(new Paragraph("IText Example", this.getFont()));
		
		this.pdf.addAuthor("The Author");
		this.pdf.addCreationDate();
		this.pdf.addCreator("The Creator");
		this.pdf.addKeywords("Keywords");
		this.pdf.getPageNumber();
		this.pdf.addTitle("Title");
	}
	
	private void createPdfFile() throws IOException, DocumentException
	{
		this.pdf = new Document(PageSize.A4, 20, 20, 20, 20);
		
		FileOutputStream fos = new FileOutputStream("D:\\javalib\\PDFExcel\\ITextExample.pdf");
		
		this.writer = PdfWriter.getInstance(this.pdf, fos);	
		
		this.writer.setEncryption("user".getBytes(), "password".getBytes(), PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_128);
	}

	private void createImagePdf() throws MalformedURLException, IOException, DocumentException
	{
		String imagePath = "D:\\javalib\\PDFExcel\\src\\main\\resources\\profile.png";
		Image image = Image.getInstance(imagePath);
		image.setAlignment(Image.ALIGN_CENTER);
		image.setBorder(Image.BOX);
		image.setBorderColor(BaseColor.BLACK);
		image.setBorderWidth(12.5f);
		image.scalePercent(30, 30);
		
		float width = image.getWidth() * 0.3f;
		float height = image.getHeight() * 0.3f;
		
		float pageWidth = PageSize.A4.getWidth();
		float pageHeight = PageSize.A4.getHeight();
		
		float xCoord = (pageWidth - width) / 2f;
		float yCoord = (pageHeight - height) / 2f;
		
		image.setAbsolutePosition(xCoord, yCoord);
		
		this.pdf.add(image);
	}
	
	private void createTablePdf() throws DocumentException
	{
		Font tableFont = new Font(FontFamily.TIMES_ROMAN, 10f);
		
		PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(new float[] {100f, 100f});
		table.setLockedWidth(true);
		
		PdfPCell cell = new PdfPCell(new Phrase("iText"));
		cell.setFixedHeight(30f);
		cell.setBorder(Rectangle.BOX);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("itext", tableFont));
		cell.setFixedHeight(30f);
		cell.setBorder(Rectangle.BOX);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Pdf Creator", tableFont));
		cell.setFixedHeight(30f);
		cell.setBorder(Rectangle.BOX);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		Barcode128 barcode = new Barcode128();
		barcode.setCodeType(Barcode128.CODE128);
		barcode.setCode("iText");
		PdfContentByte pdfcb = this.writer.getDirectContent();
		Image img = barcode.createImageWithBarcode(pdfcb, BaseColor.BLACK, BaseColor.BLACK);
		cell = new PdfPCell(img);
		cell.setFixedHeight(50f);
		cell.setBorder(Rectangle.BOX);
		cell.setColspan(2);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		table.setSpacingBefore(30f);
		
		this.pdf.add(table);
	}
	
	private Font getFont()
	{
		if (this.font == null)
		{
			this.font = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			this.font.setSize(14.4f);
			this.font.setStyle("BOLD");
		}
		return this.font;
	}

	
	
}
