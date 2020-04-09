package tw.STSProject.model;

import java.util.List;

public interface ITransactionRecordDAO {
	public void insertTransactionRecord(int quantity, String sellOrBuy, float price, String stockCode, int userID);
	public int deleteTransactionRecord(String date);
	public List<TransactionRecord> findAllUserTransactionRecord(int userID);
}