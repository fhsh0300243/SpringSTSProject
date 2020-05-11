package tw.STSProject.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tw.STSProject.util.STSNecessaryTools;

@Repository
public class OuputDAO implements IOutputDAO {

	private FavoriteStockService fsService;
	private StockInformationService siService;
	private InventoryService iService;
	private TransactionRecordService trService;

	@Autowired
	public OuputDAO(FavoriteStockService fsService, StockInformationService siService, 
			InventoryService iService, TransactionRecordService trService) {
		this.fsService=fsService;
		this.siService=siService;
		this.iService=iService;
		this.trService=trService;
	}

	
	public void outputFavoriteStockToCsv(int userID) throws IOException, SQLException {

		List<FavoriteStock> fsSearchResult=fsService.findAllUserFavoriteStock(userID);
		Iterator<FavoriteStock> fsSearchResultIT =fsSearchResult.iterator();
		
		List<StockInformation> siList = new ArrayList<StockInformation>();
		while(fsSearchResultIT.hasNext()) {
			FavoriteStock fsBean = fsSearchResultIT.next();
			List<StockInformation> siListTemp = siService.findStockInformation(fsBean.getStockCode());
			siList.add(siListTemp.get(0));
	    }
		
		Iterator<StockInformation> siListIT =siList.iterator();
		BufferedWriter bw = new BufferedWriter(new FileWriter("StockInformation.csv",true));		
		while(siListIT.hasNext()) {
			StockInformation siBean=siListIT.next();
			bw.append(
					  siBean.getStockCode()+","+
					  Float.toString(siBean.getTradePrice())+","+
					  Float.toString(siBean.getChange())+","+
					  Integer.toString(siBean.getTradeVolume())+","+
					  Integer.toString(siBean.getAccTradeVolume())+","+
					  Float.toString(siBean.getOpeningPrice())+","+
					  Float.toString(siBean.getHighestPrice())+","+
					  Float.toString(siBean.getLowestPrice()));
			bw.newLine();
		}
		bw.close();
	}
	
	public JSONArray outputStockInformationToJSON(int userID) throws IOException, SQLException {	
		List<FavoriteStock> fsSearchResult=fsService.findAllUserFavoriteStock(userID);
		Iterator<FavoriteStock> fsSearchResultIT =fsSearchResult.iterator();
		
		List<StockInformation> siList = new ArrayList<>();
		while(fsSearchResultIT.hasNext()) {
			FavoriteStock fsBean = fsSearchResultIT.next();
			List<StockInformation> siListTemp = siService.findStockInformation(fsBean.getStockCode());
			siList.add(siListTemp.get(0));
	    }
		
		Iterator<StockInformation> siListIT =siList.iterator();	
		JSONArray jArray = new JSONArray();
		while(siListIT.hasNext()) {
			JSONObject jObject = new JSONObject();
			StockInformation siBean=siListIT.next();
			jObject.put("StockCode", siBean.getStockCode());
			jObject.put("StockName", siBean.getStockName());
			jObject.put("TradePrice", Float.toString(siBean.getTradePrice()));
			jObject.put("Change", Float.toString(siBean.getChange()));
			jObject.put("TradeVolume", Integer.toString(siBean.getTradeVolume()));
			jObject.put("AccTradeVolume", Integer.toString(siBean.getAccTradeVolume()));
			jObject.put("OpeningPrice", Float.toString(siBean.getOpeningPrice()));
			jObject.put("HighestPrice", Float.toString(siBean.getHighestPrice()));
			jObject.put("LowestPrice", Float.toString(siBean.getLowestPrice()));
			jArray.put(jObject);
		}
		return jArray;
	}
	
	public JSONArray outputInventoryToJSON(int userID) throws IOException, SQLException {
		List<Inventory> inventoryList =iService.findAllUserInventory(userID);
		Iterator<Inventory> ilIT = inventoryList.iterator();
		JSONArray jArray = new JSONArray();
		while(ilIT.hasNext()) {
			JSONObject jObject = new JSONObject();
			Inventory iBean=ilIT.next();
			List<StockInformation> siList =siService.findStockInformation(iBean.getStockCode());
			Iterator<StockInformation> siListIT= siList.iterator();
			StockInformation siBean=siListIT.next();
			jObject.put("StockCode", iBean.getStockCode());
			jObject.put("StockName", siBean.getStockName());
			jObject.put("Quantity", String.valueOf(iBean.getQuantity()));
			jObject.put("TradePrice", String.valueOf(siBean.getTradePrice()));
			jObject.put("MarketCap", String.valueOf(iBean.getQuantity()*siBean.getTradePrice()));
			jArray.put(jObject);
		}
		return jArray;
	}
	
	public JSONArray outputTransactionRecordToJSON(int userID) throws IOException, SQLException{
		List<TransactionRecord> trList =trService.findAllUserTransactionRecord(userID);
		Iterator<TransactionRecord> trListIT=trList.iterator();
		JSONArray jArray = new JSONArray();
		while(trListIT.hasNext()) {
			JSONObject jObject = new JSONObject();
			TransactionRecord trBean=trListIT.next();
			List<StockInformation> siList =siService.findStockInformation(trBean.getStockCode());
			Iterator<StockInformation> siListIT= siList.iterator();
			StockInformation siBean=siListIT.next();
			jObject.put("StockCode", trBean.getStockCode());
			jObject.put("StockName", siBean.getStockName());
			jObject.put("SellOrBuy", trBean.getSellOrBuy());
			jObject.put("Quantity", String.valueOf(trBean.getQuantity()));
			jObject.put("TradePrice", String.valueOf(trBean.getPrice()));
			jObject.put("TradeDay", trBean.getRecordDay());
			jArray.put(jObject);
		}
		return jArray;
	}
}
