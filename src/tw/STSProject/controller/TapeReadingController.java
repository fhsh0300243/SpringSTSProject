package tw.STSProject.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import tw.STSProject.model.OutputService;
import tw.STSProject.model.StockInformation;
import tw.STSProject.model.StockInformationService;
import tw.STSProject.util.STSNecessaryTools;

@Controller
@SessionAttributes(names = { "userID", "userMoney" })
@RequestMapping(path = "/tapeReading")
public class TapeReadingController {
	private OutputService oService;
	private StockInformationService siService;
	private STSNecessaryTools stsnt = new STSNecessaryTools();

	@Autowired
	public TapeReadingController(OutputService oService, StockInformationService siService) {
		this.oService = oService;
		this.siService = siService;
	}

	@RequestMapping(path = "/start", method = {RequestMethod.POST,RequestMethod.GET})
	public String stratTapeReading(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userID") int userID, @ModelAttribute("userMoney") int userMoney) {
		response.setContentType("text/html;charset=UTF-8");
		response.setIntHeader("Refresh", 1);

		try {
			request.setCharacterEncoding("UTF-8");
			oService.outputStockInformationToJSON(userID);
			List<String> newResponse = new ArrayList<String>();
			int lineCount = 0;

			BufferedReader br = new BufferedReader(new FileReader("StockInformation.json"));
			while (br.ready()) {
				br.readLine();
				lineCount++;
			}
			br.close();

			String[] favoriteStockNum = new String[lineCount];
			int index = 0;
			BufferedReader br1 = new BufferedReader(new FileReader("StockInformation.json"));
			while (br1.ready()) {
				String brOneString = br1.readLine();
				newResponse = Arrays.asList(brOneString.split("\""));
				favoriteStockNum[index] = newResponse.get(3);
				index++;
			}
			br1.close();

			for (int i = 0; i < favoriteStockNum.length; i++) {
				System.out.println(favoriteStockNum[i]);
				StockInformation si = stsnt.getStockInfoFromInternet(favoriteStockNum[i]);
				siService.insertOrUpdateStockInformation(si.getStockCode(), si.getStockName(), si.getTradePrice(),
						si.getChange(), si.getTradeVolume(), si.getAccTradeVolume(), si.getOpeningPrice(),
						si.getHighestPrice(), si.getLowestPrice());
			}
			oService.outputStockInformationToJSON(userID);

			String[] favoriteStockInfo = new String[lineCount * 6];
			index = 0;
			BufferedReader br2 = new BufferedReader(new FileReader("StockInformation.json"));
			while (br2.ready()) {
				String brOneString = br2.readLine();
				newResponse = Arrays.asList(brOneString.split("\""));
				favoriteStockInfo[index] = newResponse.get(3);
				index++;
				favoriteStockInfo[index] = newResponse.get(7);
				index++;
				favoriteStockInfo[index] = newResponse.get(15);
				index++;
				favoriteStockInfo[index] = newResponse.get(11);
				index++;
				favoriteStockInfo[index] = newResponse.get(31);
				index++;
				favoriteStockInfo[index] = newResponse.get(35);
				index++;
			}
			br2.close();

			List<String> newResponseForI = new ArrayList<String>();
			int lineCountForI = 0;
			BufferedReader bs = new BufferedReader(new FileReader("Inventory.json"));
			while (bs.ready()) {
				bs.readLine();
				lineCountForI++;
			}
			bs.close();
			String[] InventoryData = new String[lineCountForI * 5];
			int indexForI = 0;
			BufferedReader bs1 = new BufferedReader(new FileReader("Inventory.json"));
			while (bs1.ready()) {
				String brOneStringForI = bs1.readLine();
				newResponseForI = Arrays.asList(brOneStringForI.split("\""));
				InventoryData[indexForI] = newResponseForI.get(3);
				indexForI++;
				InventoryData[indexForI] = newResponseForI.get(7);
				indexForI++;
				InventoryData[indexForI] = newResponseForI.get(11);
				indexForI++;
				InventoryData[indexForI] = newResponseForI.get(15);
				indexForI++;
				InventoryData[indexForI] = newResponseForI.get(19);
				indexForI++;
			}
			bs1.close();

			List<String> newResponseForTR = new ArrayList<String>();
			int lineCountForTR = 0;
			BufferedReader bt = new BufferedReader(new FileReader("TransactionRecord.json"));
			while (bt.ready()) {
				bt.readLine();
				lineCountForTR++;
			}
			bt.close();
			String[] transactionRecordData = new String[lineCountForTR * 6];
			int indexForTR = 0;
			BufferedReader bt1 = new BufferedReader(new FileReader("TransactionRecord.json"));
			while (bt1.ready()) {
				String brOneStringForTR = bt1.readLine();
				newResponseForTR = Arrays.asList(brOneStringForTR.split("\""));
				transactionRecordData[indexForTR] = newResponseForTR.get(3);
				indexForTR++;
				transactionRecordData[indexForTR] = newResponseForTR.get(7);
				indexForTR++;
				transactionRecordData[indexForTR] = newResponseForTR.get(11);
				indexForTR++;
				transactionRecordData[indexForTR] = newResponseForTR.get(15);
				indexForTR++;
				transactionRecordData[indexForTR] = newResponseForTR.get(19);
				indexForTR++;
				transactionRecordData[indexForTR] = newResponseForTR.get(23);
				indexForTR++;
			}
			bt1.close();

			request.setAttribute("userMoney", userMoney);
			request.setAttribute("favoriteStockInfo", favoriteStockInfo);
			request.setAttribute("InventoryData", InventoryData);
			request.setAttribute("transactionRecordData", transactionRecordData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "MainPageWithTape";

	}

}
