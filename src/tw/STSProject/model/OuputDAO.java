package tw.STSProject.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import tw.STSProject.util.STSNecessaryTools;


@Repository
public class OuputDAO implements IOutputDAO {

	private FavoriteStockService fsService;
	private StockInformationService siService;
	private InventoryService iService;
	private TransactionRecordService trService;
	
	public OuputDAO(FavoriteStockService fsService, StockInformationService siService, InventoryService iService, TransactionRecordService trService) {
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
	
	public void outputStockInformationToJSON(int userID) throws IOException, SQLException {
		STSNecessaryTools stsnt=new STSNecessaryTools();
		stsnt.removeStockInformationFile();
		
		List<FavoriteStock> fsSearchResult=fsService.findAllUserFavoriteStock(userID);
		System.out.println("fsSearchResult: "+fsSearchResult);
		Iterator<FavoriteStock> fsSearchResultIT =fsSearchResult.iterator();
		
		List<StockInformation> siList = new ArrayList<StockInformation>();
		while(fsSearchResultIT.hasNext()) {
			FavoriteStock fsBean = fsSearchResultIT.next();
			List<StockInformation> siListTemp = siService.findStockInformation(fsBean.getStockCode());
			siList.add(siListTemp.get(0));
	    }
		
		Iterator<StockInformation> siListIT =siList.iterator();	
		BufferedWriter bw = new BufferedWriter(new FileWriter("StockInformation.json",true));
		while(siListIT.hasNext()) {
			StockInformation siBean=siListIT.next();
			bw.append("{"+
					"\"StockCode\""+":"+"\""+siBean.getStockCode()+"\""+","+
					"\"StockName\""+":"+"\""+siBean.getStockName()+"\""+","+
					"\"TradePrice\""+":"+"\""+Float.toString(siBean.getTradePrice())+"\""+","+
					"\"Change\""+":"+"\""+Float.toString(siBean.getChange())+"\""+","+
					"\"TradeVolume\""+":"+"\""+Integer.toString(siBean.getTradeVolume())+"\""+","+
					"\"AccTradeVolume\""+":"+"\""+Integer.toString(siBean.getAccTradeVolume())+"\""+","+
					"\"OpeningPrice\""+":"+"\""+Float.toString(siBean.getOpeningPrice())+"\""+","+
					"\"HighestPrice\""+":"+"\""+Float.toString(siBean.getHighestPrice())+"\""+","+
					"\"LowestPrice\""+":"+"\""+Float.toString(siBean.getLowestPrice())+"\""+
					"}");
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}
	
	public void outputInventoryToJSON(int userID) throws IOException, SQLException {
		STSNecessaryTools stsnt=new STSNecessaryTools();
		stsnt.removeInventoryFile();
		
		List<Inventory> inventoryList =iService.findAllUserInventory(userID);
		Iterator<Inventory> ilIT = inventoryList.iterator();
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("Inventory.json",true));
		while(ilIT.hasNext()) {
			Inventory iBean=ilIT.next();
			List<StockInformation> siList =siService.findStockInformation(iBean.getStockCode());
			Iterator<StockInformation> siListIT= siList.iterator();
			StockInformation siBean=siListIT.next();
			bw1.append("{"+
					   "\"StockCode\""+":"+"\""+iBean.getStockCode()+"\""+","+
					   "\"StockName\""+":"+"\""+siBean.getStockName()+"\""+","+
					   "\"Quantity\""+":"+"\""+String.valueOf(iBean.getQuantity())+"\""+","+
					   "\"TradePrice\""+":"+"\""+String.valueOf(siBean.getTradePrice())+"\""+","+
					   "\"MarketCap\""+":"+"\""+String.valueOf(iBean.getQuantity()*siBean.getTradePrice())+"\""+","+
					   "}");
			bw1.newLine();
		}
		bw1.flush();
		bw1.close();
	}
	
	public void outputTransactionRecordToJSON(int userID) throws IOException, SQLException{
		STSNecessaryTools stsnt=new STSNecessaryTools();
		stsnt.removeTransanctionRecordFile();
		
		List<TransactionRecord> trList =trService.findAllUserTransactionRecord(userID);
		Iterator<TransactionRecord> trListIT=trList.iterator();
		BufferedWriter bw2 = new BufferedWriter(new FileWriter("TransactionRecord.json",true));
		while(trListIT.hasNext()) {
			TransactionRecord trBean=trListIT.next();
			List<StockInformation> siList =siService.findStockInformation(trBean.getStockCode());
			Iterator<StockInformation> siListIT= siList.iterator();
			StockInformation siBean=siListIT.next();
			bw2.append("{"+
					"\"StockCode\""+":"+"\""+trBean.getStockCode()+"\""+","+
					"\"StockName\""+":"+"\""+siBean.getStockName()+"\""+","+
					"\"SellOrBuy\""+":"+"\""+trBean.getSellOrBuy()+"\""+","+
					"\"Quantity\""+":"+"\""+String.valueOf(trBean.getQuantity())+"\""+","+
					"\"TradePrice\""+":"+"\""+String.valueOf(trBean.getPrice())+"\""+","+
					"\"TradeDay\""+":"+"\""+trBean.getRecordDay()+"\""+","+
					"}");
			bw2.newLine();
		}
		bw2.flush();
		bw2.close();
	}
}
