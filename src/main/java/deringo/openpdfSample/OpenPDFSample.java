package deringo.openpdfSample;

import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.alignment.HorizontalAlignment;
import com.lowagie.text.alignment.VerticalAlignment;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;

public class OpenPDFSample {
    private static final Path FILE_OPENPDF = getTMPFile();

    private static final Path BILD1 = Paths.get("src/main/resources/cat-2934720_1280.jpg");
    private static final Path BILD2 = Paths.get("src/main/resources/tiger-2535888_640.jpg");
    private static final Path BILD3 = Paths.get("src/main/resources/kingfisher-2046453_1920.jpg");
    private static final Path BILD4 = Paths.get("src/main/resources/ireland-1985088_1280.jpg");
    

    private static final String IDENTIFIKATIONSNUMMER = "DE-NRW-S-23-0001";

    private static final LocalDate DATUM = LocalDate.now();
    private static final String INFO1 = "Lorem Ipsum";
    private static final String INFO2 = "Lorem ipsum dolor sit amet";

    public static void main(String[] args) throws Exception {
        createPDF();

        // PDF öffnen
        Desktop.getDesktop().open(FILE_OPENPDF.toFile());
    }
    
    private static void createPDF() throws Exception {
        // Erstellen eines neuen Dokuments
        Document document = new Document();
        PdfDocument pdf = new PdfDocument();
        pdf.addWriter(PdfWriter.getInstance(document, Files.newOutputStream(FILE_OPENPDF)));
        pdf.setDocumentLanguage("de_DE");

        // Dokument öffnen
        document.open();

        // Identifikationsnummer
        Table  table0 = new Table (2);
        table0.setWidth(100);

        Cell cell01 = new Cell(new Paragraph("Identification Number:"));
        Cell cell02 = new Cell(new Paragraph(IDENTIFIKATIONSNUMMER));
        cell01.setBorder(Rectangle.NO_BORDER);
        cell02.setBorder(Rectangle.NO_BORDER);
        table0.addCell(cell01);
        table0.addCell(cell02);
        table0.setBorder(Rectangle.NO_BORDER);
        document.add(table0);
        
        // Bilder
        for (int i=0; i<6; i++) {
            document.add(new Paragraph(""));
            
            // Fügen Sie Bilder hinzu (angepasst an die Breite des A4-Papiers)
            float width = PageSize.A4.getWidth() / 2 - 50; // Abstand hinzufügen
            float height = PageSize.A4.getHeight() / 2 - 10;
            
            // Erstellen von Image-Objekten für die Bilder
            Image img1;
            Image img2;
            if (i%2 == 0) {
                img1 = Image.getInstance(BILD1.toUri().toURL());
                img2 = Image.getInstance(BILD2.toUri().toURL());
            } else {
                img1 = Image.getInstance(BILD3.toUri().toURL());
                img2 = Image.getInstance(BILD4.toUri().toURL());
            }
            
            // Skalieren Sie die Bilder, um nebeneinander zu passen
            img1.scaleToFit(width, height);
            img2.scaleToFit(width, height);
            
    
            // Erstellen einer Tabelle
            Table  table = new Table (2);
            table.setWidth(100);
            table.setTableFitsPage(true);
            table.getDefaultCell().setBorder(0);

            //
            Cell cell1  = new Cell(new Paragraph("Date Taken:"));
            Cell cell2  = new Cell(new Paragraph(DATUM.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
            Cell cell3  = new Cell(new Paragraph("Info 1:"));
            Cell cell4  = new Cell(new Paragraph(INFO1));
            Cell cell5  = new Cell(new Paragraph("Info 2:"));
            Cell cell6  = new Cell(new Paragraph(INFO2));
            Cell cell7  = new Cell(new Paragraph("Picture left:"));
            Cell cell8  = new Cell(new Paragraph("Picture right:"));
            Cell cell9  = new Cell(img1);
            Cell cell10 = new Cell(img2);

            // center Images
            cell9.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell9.setVerticalAlignment(VerticalAlignment.CENTER);
            cell10.setHorizontalAlignment(HorizontalAlignment.CENTER);
            cell10.setVerticalAlignment(VerticalAlignment.CENTER);
            
            
            
            
            // set Borders
//            cell1.setBorder(Rectangle.NO_BORDER);
//            cell2.setBorder(Rectangle.NO_BORDER);
//            cell3.setBorder(Rectangle.NO_BORDER);
//            cell4.setBorder(Rectangle.NO_BORDER);
//            cell5.setBorder(Rectangle.NO_BORDER);
//            cell6.setBorder(Rectangle.NO_BORDER);
//            cell7.setBorder(Rectangle.NO_BORDER);
//            cell8.setBorder(Rectangle.NO_BORDER);
            cell9.setBorder(Rectangle.TOP + Rectangle.RIGHT);
            cell10.setBorder(Rectangle.TOP);
            
//            cell1.setBorderRight(Rectangle.NO_BORDER);
//            cell2.setBorderLeft(Rectangle.NO_BORDER);
//            cell3.setBorderRight(Rectangle.NO_BORDER);
//            cell4.setBorderLeft(Border.NO_BORDER);
//            cell5.setBorderRight(Border.NO_BORDER);
//            cell6.setBorderLeft(Border.NO_BORDER);
//            cell7.setBorderBottom(Border.NO_BORDER);
//            cell9.setBorderTop(Border.NO_BORDER);
//            cell8.setBorderBottom(Border.NO_BORDER);
//            cell10.setBorderTop(Border.NO_BORDER);
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            table.addCell(cell5);
            table.addCell(cell6);
            table.addCell(cell7);
            table.addCell(cell8);
//            table.addCell(new Cell((new Paragraph(""))));
//            table.addCell(new Cell(""));
            table.addCell(cell9);
            table.addCell(cell10);
            
            // Fügen Sie die Tabelle zum Dokument hinzu
            document.add(table);
        }
        
        // Dokument schließen
        document.close();
        pdf.close();
    }

    public static Path getTMPFile() {
        Path tmpdir = Paths.get(System.getProperty ("java.io.tmpdir"));
        Path tmpfile = tmpdir.resolve(String.valueOf(System.currentTimeMillis()) + ".pdf");
        return tmpfile;
    }
}
