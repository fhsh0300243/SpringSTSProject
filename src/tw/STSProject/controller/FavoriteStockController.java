package tw.STSProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import tw.STSProject.model.FavoriteStockService;
import tw.STSProject.model.OutputService;
import tw.STSProject.model.StockInformation;
import tw.STSProject.model.StockInformationService;
import tw.STSProject.util.STSNecessaryTools;


@Controller
@SessionAttributes(names = {"userID"})
@RequestMapping(path ="/fStock")
public class FavoriteStockController {
	private FavoriteStockService fsService;
	private StockInformationService siService;
	private OutputService oService;
	private STSNecessaryTools stsnt=new STSNecessaryTools();
	
	@Autowired
	public FavoriteStockController(FavoriteStockService fsService, StockInformationService siService, OutputService oService) {
		this.oService = oService;
		this.fsService=fsService;
		this.siService=siService;
	}
	
	@RequestMapping(path ="/add" , method = RequestMethod.POST)
	public String addFavoriteStock(@RequestParam("Scode") String sCode, @ModelAttribute("userID") int userID) throws Exception {
		StockInformation stock = stsnt.getStockInfoFromInternet(sCode);
		Thread.sleep(1500);
		siService.insertOrUpdateStockInformation(stock.getStockCode(), stock.getStockName(), stock.getTradePrice(),stock.getChange(), stock.getTradeVolume(), stock.getAccTradeVolume(), stock.getOpeningPrice(), stock.getHighestPrice(), stock.getLowestPrice());
		fsService.insertFavoriteStock(userID, sCode);
		oService.outputStockInformationToJSON(userID);
		return "redirect:/main/toMainTable";
		
	}
	
	@RequestMapping(path ="/delete" , method = RequestMethod.POST)
	public String deleteFavoriteStock(@RequestParam("Scode") String sCode, @ModelAttribute("userID") int userID) throws Exception {
		fsService.deleteFavoriteStock(userID, sCode);
		oService.outputStockInformationToJSON(userID);
		return "redirect:/main/toMainTable";
	}
}
