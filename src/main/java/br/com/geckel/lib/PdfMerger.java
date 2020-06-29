package br.com.geckel.lib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

public class PdfMerger {

    public static void MergePDFS(List<InputStream> pdfsStream,OutputStream pdfSaida, boolean paginacao,Rectangle tamanhoFolhaRosto, Rectangle tamanhoDocumento) {

        Rectangle tamanhoDaPaginaAtual = tamanhoFolhaRosto;
        Document documentoPDF = new Document(tamanhoDaPaginaAtual);

        try {

            List<InputStream> listaPDF = pdfsStream;
            List<PdfReader> leitorPDF = new ArrayList<PdfReader>();
            int totalDePaginas = 0;

            for (InputStream pdf : listaPDF) {

                PdfReader pdfReader = new PdfReader(pdf);
                leitorPDF.add(pdfReader);
                totalDePaginas += pdfReader.getNumberOfPages();

            }

            PdfWriter escreveBytesPDF = PdfWriter.getInstance(documentoPDF,pdfSaida);

            documentoPDF.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte pdfContentByte = escreveBytesPDF.getDirectContent(); 

            PdfImportedPage paginaAtual;
            int numeroPaginaAtual = 0;
            int paginaAtualReaderPDF = 0;

            boolean folhaRosto = false;

            for (PdfReader pdfReader : leitorPDF) {

                if (folhaRosto == false) {

                    tamanhoDaPaginaAtual = tamanhoDocumento;

                } else {

                    tamanhoDaPaginaAtual = tamanhoFolhaRosto;
                    folhaRosto = false;

                    documentoPDF.setPageSize(tamanhoDaPaginaAtual);

                    paginaAtualReaderPDF++;
                    numeroPaginaAtual++;

                    paginaAtual = escreveBytesPDF.getImportedPage(pdfReader,paginaAtualReaderPDF);

                    pdfContentByte.addTemplate(paginaAtual, 0, 0);

                    continue;
                }

                paginaAtualReaderPDF = 0;

                while (paginaAtualReaderPDF < pdfReader.getNumberOfPages()) {

                    documentoPDF.setPageSize(tamanhoDaPaginaAtual);
                    documentoPDF.newPage();

                    paginaAtualReaderPDF++;
                    numeroPaginaAtual++;

                    paginaAtual = escreveBytesPDF.getImportedPage(pdfReader,paginaAtualReaderPDF);

                    pdfContentByte.addTemplate(paginaAtual, 0, 0);


                    if (paginacao) {
                        pdfContentByte.beginText();
                        pdfContentByte.setFontAndSize(bf, 9);
                        pdfContentByte.showTextAligned(PdfContentByte.ALIGN_CENTER, "" + numeroPaginaAtual + " of "+ totalDePaginas, 520, 5, 0);
                        pdfContentByte.endText();
                    }
                }

                paginaAtualReaderPDF = 0;

            }

            pdfSaida.flush();

            documentoPDF.close();

            pdfSaida.close();

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            if (documentoPDF.isOpen())
                documentoPDF.close();

            try {
                if (pdfSaida != null)
                    pdfSaida.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }

    }

}