package tw.STSProject.model;

import java.util.List;

public interface IStockInformationDAO {
	public boolean insertOrUpdateStockInformation(String stockCode, String stockName, float tradePrice, float change, int tradeVolume, int accTradeVolume, float openingPrice, float highestPrice, float lowestPrice);
	public int deleteStockInformation(String stockCode);
	public List<StockInformation> findStockInformation(String stockCode);
}
