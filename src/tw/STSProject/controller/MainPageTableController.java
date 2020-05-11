package tw.STSProject.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import tw.STSProject.model.OutputService;

@Controller
@SessionAttributes(names = {"userID", "userMoney"})
@RequestMapping(path ="/main")
public class MainPageTableController {
	private OutputService oService;

	@Autowired
	public MainPageTableController(OutputService oService) {
		this.oService = oService;
	}
	
	@RequestMapping(path = "/toMainTable", method = {RequestMethod.POST,RequestMethod.GET})
	public String processMainPageAction(@ModelAttribute("userID") int userID, @ModelAttribute("userMoney") int userMoney,HttpServletResponse response, Model model) throws IOException, SQLException {
		JSONArray inJArray=oService.outputInventoryToJSON(userID);
		JSONArray trJArray=oService.outputTransactionRecordToJSON(userID);
		JSONArray siJArray=oService.outputStockInformationToJSON(userID);
		Map<String, String> msg = new HashMap<>();
		model.addAttribute("msg", msg);
		
		try {
			
			String[] favoriteStockInfo = new String[siJArray.length()*6]; 
			int indexForFS=0;
			
			for(int i=0; i<siJArray.length(); i++) {
				JSONObject siJObject=(JSONObject)siJArray.get(i);
				favoriteStockInfo[indexForFS]=(String) siJObject.get("StockCode");
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=(String) siJObject.get("StockName");
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=(String) siJObject.get("Change");
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=(String) siJObject.get("TradePrice");
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=(String) siJObject.get("HighestPrice");
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=(String) siJObject.get("LowestPrice");
		    	indexForFS++;
			}
			   
			String[] inventoryData = new String[inJArray.length()*5]; 
			int indexForI=0;
			for(int j=0; j<inJArray.length(); j++) {
				JSONObject inJObject=(JSONObject)inJArray.get(j);
				inventoryData[indexForI]=(String)inJObject.get("StockCode");
		    	indexForI++;
		    	inventoryData[indexForI]=(String)inJObject.get("StockName");
		    	indexForI++;
		    	inventoryData[indexForI]=(String)inJObject.get("Quantity");
		    	indexForI++;
		    	inventoryData[indexForI]=(String)inJObject.get("TradePrice");
		    	indexForI++;
		    	inventoryData[indexForI]=(String)inJObject.get("MarketCap");
		    	indexForI++;
			}
		    
			String[] transactionRecordData = new String[trJArray.length()*6]; 
			int indexForTR=0;
			for(int k=0; k<trJArray.length(); k++) {
				JSONObject trJObject=(JSONObject)trJArray.get(k);
				transactionRecordData[indexForTR]=(String)trJObject.get("StockCode");
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=(String)trJObject.get("StockName");
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=(String)trJObject.get("SellOrBuy");
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=(String)trJObject.get("Quantity");
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=(String)trJObject.get("TradePrice");
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=(String)trJObject.get("TradeDay");
		    	indexForTR++;
			}
		    
		    if(favoriteStockInfo.length==0) {
		    	msg.put("FSnull", "無自選股");
		    }
		    if(inventoryData.length==0) {
		    	msg.put("Inull", "無庫存紀錄");
		    }
		    if(transactionRecordData.length==0) {
		    	msg.put("TRnull", "無交易紀錄");
		    }
		    
		    model.addAttribute("userMoney", userMoney);
		    model.addAttribute("favoriteStockInfo", favoriteStockInfo);
		    model.addAttribute("inventoryData", inventoryData);
		    model.addAttribute("transactionRecordData", transactionRecordData);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "MainPage";
		
	}

}
