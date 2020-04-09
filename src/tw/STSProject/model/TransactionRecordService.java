package tw.STSProject.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionRecordService implements ITransactionRecordService{
	private TransactionRecordDAO trDAO;
	
	@Autowired
	public TransactionRecordService(TransactionRecordDAO trDAO) {
		this.trDAO = trDAO;
	}
	
	public void insertTransactionRecord(int quantity, String sellOrBuy, float price, String stockCode, int userID) {
		trDAO.insertTransactionRecord(quantity, sellOrBuy, price, stockCode, userID);
	}
	
	public int deleteTransactionRecord(String date) {
		return trDAO.deleteTransactionRecord(date);
	}
	
	public List<TransactionRecord> findAllUserTransactionRecord(int userID){
		return trDAO.findAllUserTransactionRecord(userID);
	}
}
