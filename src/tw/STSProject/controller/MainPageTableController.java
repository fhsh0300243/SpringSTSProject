package tw.STSProject.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
		System.out.println("userID: "+userID);
		System.out.println("userMoney: "+userMoney);
		oService.outputInventoryToJSON(userID);
		oService.outputTransactionRecordToJSON(userID);
		oService.outputStockInformationToJSON(userID);
		Map<String, String> msg = new HashMap<String, String>();
		model.addAttribute("msg", msg);
		
		List<String> newResponseForFS=new ArrayList<String>();
		int lineCountForFS=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("StockInformation.json"));
			while(br.ready()) {
				br.readLine();
				lineCountForFS++;		
			}
			br.close();
			String[] favoriteStockInfo = new String[lineCountForFS*6]; 
			int indexForFS=0;
			BufferedReader br1 = new BufferedReader(new FileReader("StockInformation.json"));
		    while(br1.ready()) {
		    	String brOneStringForFS = br1.readLine();
		    	newResponseForFS=Arrays.asList(brOneStringForFS.split("\""));
		    	favoriteStockInfo[indexForFS]=newResponseForFS.get(3);
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=newResponseForFS.get(7);
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=newResponseForFS.get(15);
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=newResponseForFS.get(11);
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=newResponseForFS.get(31);
		    	indexForFS++;
		    	favoriteStockInfo[indexForFS]=newResponseForFS.get(35);
		    	indexForFS++;
		    }
		    br1.close();
		    
		    List<String> newResponseForI=new ArrayList<String>();
		    int lineCountForI=0;
			BufferedReader bs = new BufferedReader(new FileReader("Inventory.json"));
			while(bs.ready()) {
				bs.readLine();
				lineCountForI++;		
			}
			bs.close();
			String[] InventoryData = new String[lineCountForI*5]; 
			int indexForI=0;
			BufferedReader bs1 = new BufferedReader(new FileReader("Inventory.json"));
		    while(bs1.ready()) {
		    	String brOneStringForI = bs1.readLine();
		    	newResponseForI=Arrays.asList(brOneStringForI.split("\""));
		    	InventoryData[indexForI]=newResponseForI.get(3);
		    	indexForI++;
		    	InventoryData[indexForI]=newResponseForI.get(7);
		    	indexForI++;
		    	InventoryData[indexForI]=newResponseForI.get(11);
		    	indexForI++;
		    	InventoryData[indexForI]=newResponseForI.get(15);
		    	indexForI++;
		    	InventoryData[indexForI]=newResponseForI.get(19);
		    	indexForI++;
		    }
		    bs1.close();
		    
		    List<String> newResponseForTR=new ArrayList<String>();
		    int lineCountForTR=0;
			BufferedReader bt = new BufferedReader(new FileReader("TransactionRecord.json"));
			while(bt.ready()) {
				bt.readLine();
				lineCountForTR++;		
			}
			bt.close();
			String[] transactionRecordData = new String[lineCountForTR*6]; 
			int indexForTR=0;
			BufferedReader bt1 = new BufferedReader(new FileReader("TransactionRecord.json"));
		    while(bt1.ready()) {
		    	String brOneStringForTR = bt1.readLine();
		    	newResponseForTR=Arrays.asList(brOneStringForTR.split("\""));
		    	transactionRecordData[indexForTR]=newResponseForTR.get(3);
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=newResponseForTR.get(7);
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=newResponseForTR.get(11);
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=newResponseForTR.get(15);
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=newResponseForTR.get(19);
		    	indexForTR++;
		    	transactionRecordData[indexForTR]=newResponseForTR.get(23);
		    	indexForTR++;
		    }
		    bt1.close();
		    
		    if(favoriteStockInfo.length==0) {
		    	msg.put("FSnull", "尚無自選股");
		    }
		    if(InventoryData.length==0) {
		    	msg.put("Inull", "尚無庫存股");
		    }
		    if(transactionRecordData.length==0) {
		    	msg.put("TRnull", "尚無交易紀錄");
		    }
		    
		    model.addAttribute("userMoney", userMoney);
		    model.addAttribute("favoriteStockInfo", favoriteStockInfo);
		    model.addAttribute("InventoryData", InventoryData);
		    model.addAttribute("transactionRecordData", transactionRecordData);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "MainPage";
		
	}

}
