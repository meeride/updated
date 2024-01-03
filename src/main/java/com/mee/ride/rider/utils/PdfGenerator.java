package com.mee.ride.rider.utils;
import java.io.IOException;
import java.util.List;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mee.ride.user.model.UserOrderModel;

import jakarta.servlet.http.HttpServletResponse;

public class PdfGenerator {
	public void generate(UserOrderModel model, HttpServletResponse response) throws DocumentException, IOException {
	    // Creating the Object of Document
	    Document document = new Document(PageSize.A4);
	    // Getting instance of PdfWriter
	    PdfWriter.getInstance(document, response.getOutputStream());
	    // Opening the created document to change it
	    document.open();
	    
	    
	   
	    Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
	    fontTiltle.setSize(20);
	    fontTiltle.setColor(CMYKColor.RED);
	    // Creating paragraph
	    Paragraph paragraph1 = new Paragraph("INVOICE", fontTiltle);
	    paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(paragraph1);
	    
	    Image jpg = Image.getInstance("logo.png");
	    jpg.scaleAbsolute(140, 140);
	    jpg.setAlignment(Image.ALIGN_CENTER);
        document.add(jpg);
	    
	    Font fontSubTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
	    fontSubTiltle.setSize(15);
	    fontSubTiltle.setColor(CMYKColor.BLACK);
	    Paragraph paragraph2 = new Paragraph("ORDER NO : "+model.getOrderId(), fontSubTiltle);
	    paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(paragraph2);
	    
	    fontSubTiltle.setSize(13);
	    Paragraph paragraph3 = new Paragraph("DATE : "+model.getOrderDate(), fontSubTiltle);
	    paragraph3.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(paragraph3);
	    
	    fontSubTiltle.setSize(25);
	    Paragraph p4 = new Paragraph("", fontSubTiltle);
	    p4.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p4);
	    
	    fontSubTiltle.setSize(15);
	    Paragraph p5 = new Paragraph("DISTANCE : "+model.getKm()+" KM", fontSubTiltle);
	    p5.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p5);
	    
	    
	    Paragraph p6 = new Paragraph("DURATION : "+model.getDuration()+" Minutes", fontSubTiltle);
	    p6.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p6);
	    
	    fontSubTiltle.setSize(13);
	    Paragraph p7 = new Paragraph("Pickup : "+model.getPickAddress(), fontSubTiltle);
	    p7.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p7);
	    
	    Paragraph p8 = new Paragraph("Drop   : "+model.getDropAddress(), fontSubTiltle);
	    p8.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p8);
	    
	    fontSubTiltle.setSize(15);
	    fontSubTiltle.setColor(CMYKColor.RED);
	    Paragraph p9 = new Paragraph("TOTAL : "+model.getTotalAmount(), fontSubTiltle);
	    p9.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p9);
	    
	    fontSubTiltle.setSize(11);
	    fontSubTiltle.setColor(CMYKColor.RED);
	    Paragraph p10 = new Paragraph("Thank you for your ride with MeeRide", fontSubTiltle);
	    p10.setAlignment(Paragraph.ALIGN_CENTER);
	    document.add(p10);
	    
	    
	    
	    
	    
	   
	    // Closing the document
	    document.close();
	  }
}
