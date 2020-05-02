package tw.STSProject.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import tw.STSProject.model.StockInformation;

public class STSNecessaryTools {
	
	private Process proc;

	public StockInformation getStockInfoFromInternet(String stockCode) throws IOException, InterruptedException {

		StockInformation si= new StockInformation();
		proc=Runtime.getRuntime().exec("python GetStockInfoFromWeb.py "+stockCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
		String line;
		
        line=in.readLine(); 
        System.out.println(line);
        JSONObject msgArray = (JSONObject) new JSONObject(line).getJSONArray("msgArray").get(0);
		
        System.out.println(msgArray.get("n"));
        
        Thread.sleep(3500);
		
		
		
		System.out.println(msgArray);
		in.close();  
        proc.waitFor(); 
		return null;

	}
	
	public void removePerStockInfoFile(String stockCode) {
		String fileName="stock_"+stockCode+".txt";
		File f1 = new File(fileName);
		if(f1.exists()) {
			f1.delete();
		}
		f1=null;
	}
	
	public void removeStockInformationFile() {
		File f1 = new File("StockInformation.json");
		if(f1.exists()) {
			f1.delete();
		}
		f1=null;
	}
	
	public void removeInventoryFile() {
		File f1 = new File("Inventory.json");
		if(f1.exists()) {
			f1.delete();
		}
		f1=null;
	}
	
	public void removeTransanctionRecordFile() {
		File f1 = new File("TransactionRecord.json");
		if(f1.exists()) {
			f1.delete();
		}
		f1=null;
	}
		
	public String getTAIEXFromInternet () throws IOException, InterruptedException {
		Runtime.getRuntime().exec("python GetTAIEXFromWeb.py");
		Thread.sleep(3500);
		String responseForTAIEX="";
		String fileNameForTAIEX="TAIEX.txt";
		Scanner scannerForTAIEX = new Scanner( new File(fileNameForTAIEX) );
		while(scannerForTAIEX.hasNext()) {
			responseForTAIEX += scannerForTAIEX.next();
		}
		List<String> newResponseForTAIEX=new ArrayList<String>();
		newResponseForTAIEX=Arrays.asList(responseForTAIEX.split("\""));
		String TAIEX=newResponseForTAIEX.get(newResponseForTAIEX.indexOf("tidx.tw")+8);
				
		scannerForTAIEX.close();
		return TAIEX;
	}
	
}
