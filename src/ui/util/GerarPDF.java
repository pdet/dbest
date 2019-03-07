/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.net.MalformedURLException;
import javax.swing.JTable;

public class GerarPDF {

    private static Document doc = null;
    private static OutputStream os = null;
    private int idDoTeste;

    
    public void colocarGraficos() throws BadElementException, MalformedURLException, IOException, DocumentException {
        Image img = Image.getInstance("grafbarra.jpg");
        img.setAlignment(Element.ALIGN_CENTER);
        img.scaleAbsolute(500, 400);
        doc.add(img);
        img = Image.getInstance("grafpizza.jpg");
        img.setAlignment(Element.ALIGN_CENTER);
        img.scaleAbsolute(500, 400);
        doc.add(img);

    }

    public void textoDetalhesTeste(String tipo, String id, String tempoUtilizado, String numeroIteracoes, String ordem, String benchmark) throws DocumentException {
        String texto = "Tipo: " + tipo;
        String t1 = "Detalhes do teste:";
        String t2 = "ID: " + id;
        this.idDoTeste = Integer.parseInt(id);
        String t3 = "Duração: " + tempoUtilizado + " milisegundos";
        String t4 = "Benchmark: " + benchmark;
        String t5 = "Número total de iterações: " + numeroIteracoes;
        String t6 = "Ordem: " + ordem;
        String tVazio = "   ";
        Font f = new Font(FontFamily.COURIER, 14, Font.NORMAL);

        Paragraph p = new Paragraph(t1, f);
        p.setAlignment(Element.ALIGN_LEFT);
        doc.add(p);
        Paragraph p1 = new Paragraph(t2, f);
        p1.setAlignment(Element.ALIGN_LEFT);
        doc.add(p1);
        Paragraph p2 = new Paragraph(t4, f);
        p2.setAlignment(Element.ALIGN_LEFT);
        doc.add(p2);
        Paragraph p3 = new Paragraph(texto, f);
        p3.setAlignment(Element.ALIGN_LEFT);
        doc.add(p3);
        Paragraph p4 = new Paragraph(t6, f);
        p4.setAlignment(Element.ALIGN_LEFT);
        doc.add(p4);
        Paragraph p5 = new Paragraph(t3, f);
        p5.setAlignment(Element.ALIGN_LEFT);
        doc.add(p5);
        Paragraph p6 = new Paragraph(t5, f);
        p6.setAlignment(Element.ALIGN_LEFT);
        doc.add(p6);
        Paragraph tV = new Paragraph(tVazio, f);
        p6.setAlignment(Element.ALIGN_LEFT);
        for (int i = 0; i < 5; i++) {
            doc.add(tV);
        }
    }

    public void abrePDF(JTable table) throws DocumentException, FileNotFoundException {
        doc = new Document(PageSize.A4, 72, 72, 72, 72);
        os = new FileOutputStream(idDoTeste + ".pdf");
        PdfWriter writer = PdfWriter.getInstance(doc, os);
        doc.open();

        //adiciona o texto ao PDF
        Font f = new Font(FontFamily.COURIER, 36, Font.BOLD);
        Paragraph p = new Paragraph("Dados do Teste :", f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        doc.add(p);




        PdfContentByte cb = writer.getDirectContent();

        cb.saveState();
        Graphics2D g2 = cb.createGraphicsShapes(500, 500);

        Shape oldClip = g2.getClip();
        g2.clipRect(500, 500, 1500, 1500);

        table.print(g2);
        g2.setClip(oldClip);

        g2.dispose();
        cb.restoreState();
    }

    public void colocarTabelaConsultas(JTable jConsultas) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        PdfPCell header = new PdfPCell(new Paragraph("Consultas do Teste:"));
        header.setColspan(4);
        table.addCell(header);
        table.addCell("Iteração");
        table.addCell("ID");
        table.addCell("Ordem");
        table.addCell("Duração");
        for (int i = 0; i < jConsultas.getRowCount(); i++) {
            table.addCell(jConsultas.getValueAt(i, 1).toString());
            table.addCell(jConsultas.getValueAt(i, 0).toString());
            table.addCell(jConsultas.getValueAt(i, 2).toString());
            table.addCell(jConsultas.getValueAt(i, 3).toString());
        }
        doc.add(table);
    }

    public void fechaPDF() throws IOException {
        if (doc != null) {
            doc.close();
        }
        if (os != null) {
            os.close();
        }
    }
}