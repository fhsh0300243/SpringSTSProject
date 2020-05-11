package tw.STSProject.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import tw.STSProject.model.StockInformation;

public class STSNecessaryTools {
	
	private Process proc;

	public StockInformation getStockInfoFromInternet(String stockCode) throws IOException, InterruptedException {
		System.out.println("stockCode: "+stockCode);
		Thread.sleep(3500);
		StockInformation si= new StockInformation();
		proc=Runtime.getRuntime().exec("python GetStockInfoFromWeb.py "+stockCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
		String line;
        line=in.readLine(); 
        System.out.println(line);
        JSONObject msgArray = (JSONObject) new JSONObject(line).getJSONArray("msgArray").get(0);
		si.setStockCode(msgArray.get("c").toString());
        si.setStockName(msgArray.get("n").toString());
        si.setTradePrice(msgArray.getFloat("z"));
        si.setTradeVolume(msgArray.getInt("tv"));
        si.setAccTradeVolume(msgArray.getInt("v"));
        si.setOpeningPrice(msgArray.getFloat("y"));
        si.setHighestPrice(msgArray.getFloat("h"));
        si.setLowestPrice(msgArray.getFloat("l"));
        si.setChange(msgArray.getFloat("z")-msgArray.getFloat("y"));
		in.close();  
        proc.waitFor();
		return si;
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
		
	public Map<String,String> getTAIEXFromInternet () throws IOException, InterruptedException {
		proc=Runtime.getRuntime().exec("python GetTAIEXFromWeb.py");
		BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "UTF-8"));
		String line;
        line=in.readLine();
        JSONObject JL= new JSONObject(line);
        JSONArray JLmsgArray = JL.getJSONArray("msgArray");
        JSONObject msgArray0 = (JSONObject) JLmsgArray.get(0);
        JSONObject msgArray1 = (JSONObject) JLmsgArray.get(1);
        JSONObject msgArray2 = (JSONObject) JLmsgArray.get(2);
		Thread.sleep(3500);
		Map<String,String> TAIEX= new HashMap<String,String>();
		TAIEX.put("TAIEX", msgArray0.getString("z"));
		TAIEX.put("TAIEXname", msgArray0.getString("n"));
		TAIEX.put("TAIEXY", msgArray0.getString("y"));
		TAIEX.put("TAIEXZ_Y", Float.toString((float)Math.round((msgArray0.getFloat("z")-msgArray0.getFloat("y"))*100)/100));
		TAIEX.put("BUY", msgArray1.getString("z"));
		TAIEX.put("BUYname", msgArray1.getString("n"));
		TAIEX.put("BUYY", msgArray1.getString("y"));
		TAIEX.put("BUYZ_Y", Float.toString((float)Math.round((msgArray1.getFloat("z")-msgArray1.getFloat("y"))*100)/100));
		TAIEX.put("Formosa", msgArray2.getString("z"));
		TAIEX.put("FormosaName", msgArray2.getString("n"));
		TAIEX.put("FormosaY", msgArray2.getString("y"));
		TAIEX.put("FormosaZ_Y", Float.toString((float)Math.round((msgArray2.getFloat("z")-msgArray2.getFloat("y"))*100)/100));
		return TAIEX;
	}
	
}
