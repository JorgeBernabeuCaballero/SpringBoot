package es.ua.biblioteca.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import es.ua.biblioteca.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class GeneratePdfReport {
    private static final Logger logger = LoggerFactory.getLogger(GeneratePdfReport.class);

    public static ByteArrayInputStream booksReport(List<Book> books) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 3});

            // Define custom colors
            BaseColor lightBlue = new BaseColor(173, 216, 230); // Light blue pastel color
            BaseColor darkBlue = new BaseColor(135, 206, 235); // Slightly darker blue pastel color

            // Define custom fonts
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.DARK_GRAY);

            // Add table header
            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("Id", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(darkBlue);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Title", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(darkBlue);
            hcell.setPadding(5);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Author", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBackgroundColor(darkBlue);
            hcell.setPadding(5);
            table.addCell(hcell);

            // Add table rows
            for (Book book : books) {
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(book.getId().toString(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                cell.setBackgroundColor(lightBlue);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(book.getTitle(), cellFont));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPadding(5);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(book.getAuthor(), cellFont));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                cell.setPadding(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
