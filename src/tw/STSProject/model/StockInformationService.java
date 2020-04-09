package tw.STSProject.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockInformationService implements IStockInformationService{
	private StockInformationDAO siDAO;
	
	@Autowired
	public StockInformationService(StockInformationDAO siDAO) {
		this.siDAO = siDAO;
	}
	
	public boolean insertOrUpdateStockInformation(String stockCode, String stockName, float tradePrice, float change, int tradeVolume, int accTradeVolume, float openingPrice, float highestPrice, float lowestPrice) {
		return siDAO.insertOrUpdateStockInformation(stockCode, stockName, tradePrice, change, tradeVolume, accTradeVolume, openingPrice, highestPrice, lowestPrice);
	}
	
	public int deleteStockInformation(String stockCode) {
		return siDAO.deleteStockInformation(stockCode);
	}
	
	public List<StockInformation> findStockInformation(String stockCode) {
		return siDAO.findStockInformation(stockCode);
	}
}
