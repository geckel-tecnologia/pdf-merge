package br.com.geckel.main;

import java.nio.file.Files;
import java.nio.file.Paths;

import br.com.geckel.lib.MergePDF;

public class MergePdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fullFileName = args[0];
		try {
			String[] files = new String(Files.readAllBytes(Paths.get(fullFileName))).trim().split(" ");
			MergePDF merge = new MergePDF();
			merge.JuncaoPDF(files);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
