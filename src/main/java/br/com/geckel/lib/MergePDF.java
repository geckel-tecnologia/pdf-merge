package br.com.geckel.lib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Rectangle;

public class MergePDF {

       public void JuncaoPDF(String[] args) {

           try {

            List<InputStream> pdfs = new ArrayList<InputStream>();

            for (String nomeArquivo : args) {
            	pdfs.add(new FileInputStream(nomeArquivo));
            }

            //System.out.println("Quantidade de arquivos PDF: " + totalArquivos);

            //System.out.println("Gerando arquivo que recebera o merge dos PDF's");
            String nomeArquivoSaida = args[0].replace(".pdf","-m.pdf");
            OutputStream output = new FileOutputStream(nomeArquivoSaida);

            //System.out.println("Processando o Merge...");

            Rectangle rectFolhaRosto = new Rectangle(595, 841);
            Rectangle rectDocumento = new Rectangle(595, 841);

            PdfMerger.MergePDFS(pdfs, output, false, rectFolhaRosto,rectDocumento);

            //System.out.println("Merge Realizado com sucesso: ");

        } catch (Exception e) {
            System.out.println("Erro ao gerar Mege...");
            e.printStackTrace();
        }
    }

}