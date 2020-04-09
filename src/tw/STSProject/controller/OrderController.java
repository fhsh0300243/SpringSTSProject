package tw.STSProject.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import tw.STSProject.model.Inventory;
import tw.STSProject.model.InventoryService;
import tw.STSProject.model.StockInformation;
import tw.STSProject.model.StockInformationService;
import tw.STSProject.model.TransactionRecordService;
import tw.STSProject.model.UsersService;
import tw.STSProject.util.STSNecessaryTools;

@Controller
@SessionAttributes(names = {"userID", "userMoney", "errorMsgMapFromOrder"})
@RequestMapping(path ="/order")
public class OrderController {
	private StockInformationService siService;
	private InventoryService iService;
	private UsersService uService;
	private TransactionRecordService trService;
	private STSNecessaryTools stsnt= new STSNecessaryTools();
	private Map<String, String> errorMsgMapFromOrder = new HashMap<String, String>();
	private Map<String,Object> resultMapForBuyOrSell = new HashMap<String,Object>();
	private StockInformation siBean;
	private int quantityInInventory=0;
	

	private Map<String,Object> preOder(String stockCode, int userID) throws Exception{
	    Map<String,Object> resultMap = new HashMap<String,Object>();
		siBean=stsnt.getStockInfoFromInternet(stockCode);
		siService.insertOrUpdateStockInformation(siBean.getStockCode(), siBean.getStockName(), 
				siBean.getTradePrice(), (siBean.getTradePrice()-siBean.getOpeningPrice()), 
				siBean.getTradeVolume(), siBean.getAccTradeVolume(), siBean.getOpeningPrice(), 
				siBean.getHighestPrice(), siBean.getLowestPrice());
		List<Inventory> iList=iService.findAllUserInventory(userID);
		Iterator<Inventory> iListIT=iList.iterator();
		List<StockInformation> siList =siService.findStockInformation(stockCode);
		StockInformation si=siList.get(0);
		resultMap.put("iListIT", iListIT);
		resultMap.put("si", si);
		return resultMap;
	}
	
	@Autowired
	public OrderController(StockInformationService siService, InventoryService iService
			, UsersService uService, TransactionRecordService trService){
		this.siService = siService;
		this.iService=iService;
		this.uService=uService;
		this.trService=trService;
	}
	
	@RequestMapping(path = "/buyOrSell", method = {RequestMethod.POST,RequestMethod.GET})
	public String checkBuyOrSell(@RequestParam("buyOrSell") String buyOrSell, @RequestParam("qantity") int qantity, 
			@RequestParam("stockCode") String stockCode,Model model) throws IOException, Exception {
		model.addAttribute("qantity", qantity);
		model.addAttribute("stockCode", stockCode);
		if(buyOrSell.equals("buy")) {
			return "redirect:/order/buy";
		}
		return "redirect:/order/sell";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/buy", method = {RequestMethod.POST,RequestMethod.GET})
	public String buyStock(@ModelAttribute("qantity") int qantity, @ModelAttribute("stockCode") String stockCode, 
			@ModelAttribute("userID") int userID, @ModelAttribute("userMoney") int userMoney , Model model) throws Exception {
		resultMapForBuyOrSell=preOder(stockCode, userID);
		siBean=(StockInformation)resultMapForBuyOrSell.get("si");
		Iterator<Inventory> iListIT=(Iterator<Inventory>) resultMapForBuyOrSell.get("iListIT");

		if(siBean.getTradePrice()*qantity>userMoney) {
			errorMsgMapFromOrder.put("moneyError", "你的模擬幣不夠");
			model.addAttribute("errorMsgMapFromOrder", errorMsgMapFromOrder);
			return "redirect:/main/toMainTable";
		}
		else {
			while(iListIT.hasNext()) {
				Inventory in= iListIT.next();
				if(in.getStockCode().equals(stockCode))
					quantityInInventory=in.getQuantity();	
			}
			iService.insertOrUpadteInventory(userID, stockCode, (qantity+quantityInInventory));
			trService.insertTransactionRecord(qantity, "Buy", siBean.getTradePrice(), stockCode, userID);
			
			
			uService.updateUserMoney(userID, (int)(userMoney-(siBean.getTradePrice()*qantity)));
			
			
			model.addAttribute("userMoney", (int)(userMoney-(siBean.getTradePrice()*qantity)));
			return "redirect:/main/toMainTable";
		}			
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/sell", method = {RequestMethod.POST,RequestMethod.GET})
	public String sellStock(@ModelAttribute("qantity") int qantity, @ModelAttribute("stockCode") String stockCode, 
			@ModelAttribute("userID") int userID, @ModelAttribute("userMoney") int userMoney , Model model) throws Exception {
		resultMapForBuyOrSell=preOder(stockCode, userID);
		siBean=(StockInformation)resultMapForBuyOrSell.get("si");
		Iterator<Inventory> iListIT=(Iterator<Inventory>) resultMapForBuyOrSell.get("iListIT");
		while(iListIT.hasNext()) {
			Inventory in= iListIT.next();
			if(in.getStockCode().equals(stockCode))
				quantityInInventory=in.getQuantity();	
		}
		if(qantity>quantityInInventory) {
			errorMsgMapFromOrder.put("inventoryError", "你的庫存不夠");
			model.addAttribute("errorMsgMapFromOrder", errorMsgMapFromOrder);
			return "redirect:/main/toMainTable";
		}
		else if(qantity==quantityInInventory) {
			iService.deleteInventory(userID, stockCode);
		}
		else {
			iService.insertOrUpadteInventory(userID, stockCode, (quantityInInventory-qantity));
		}
		userMoney=(int) (userMoney+(siBean.getTradePrice()*qantity));
		trService.insertTransactionRecord(qantity, "Sell", siBean.getTradePrice(), stockCode, userID);
		model.addAttribute("userMoney",userMoney);
		uService.updateUserMoney(userID, userMoney);
		return "redirect:/main/toMainTable";
	}
}
