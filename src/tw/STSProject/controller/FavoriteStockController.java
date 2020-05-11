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
	
	@Autowired
	public FavoriteStockController(FavoriteStockService fsService, StockInformationService siService, OutputService oService) {
		this.oService = oService;
		this.fsService=fsService;
		this.siService=siService;
	}
	
	@RequestMapping(path ="/add" , method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String addFavoriteStock(@RequestParam("Scode") String sCode, @ModelAttribute("userID") int userID) throws Exception {
		STSNecessaryTools stsnt= new STSNecessaryTools();
		StockInformation si= stsnt.getStockInfoFromInternet(sCode);
        siService.insertOrUpdateStockInformation(si.getStockCode(), si.getStockName(), si.getTradePrice(), si.getChange(),
        		si.getTradeVolume(), si.getAccTradeVolume(), si.getOpeningPrice(), si.getHighestPrice(), si.getLowestPrice());
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
