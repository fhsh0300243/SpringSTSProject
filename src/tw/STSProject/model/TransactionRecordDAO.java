package tw.STSProject.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRecordDAO implements ITransactionRecordDAO {
	
	private SessionFactory sessionFacotry;

	@Autowired
	public TransactionRecordDAO(@Qualifier(value = "sessionFactory") SessionFactory sessionFacotry) {
		this.sessionFacotry = sessionFacotry;
	}

	
	public void insertTransactionRecord(int quantity, String sellOrBuy, float price, String stockCode, int userID) {
		Session session = sessionFacotry.getCurrentSession();
		TransactionRecord tTemp = new TransactionRecord();
		tTemp.setQuantity(quantity);
		tTemp.setSellOrBuy(sellOrBuy);
		tTemp.setPrice(price);
		tTemp.setStockCode(stockCode);
		tTemp.setUserID(userID);
		tTemp.setRecordDay(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
		session.save(tTemp);
	}
	
	public int deleteTransactionRecord(String date) {
		Session session = sessionFacotry.getCurrentSession();
		Query<TransactionRecord> query = session.createQuery("delete from TransactionRecord where RecordDay < :date", TransactionRecord.class);
		query.setParameter("date", date);
		int numData=query.executeUpdate();
		return numData;
	}
	
	public List<TransactionRecord> findAllUserTransactionRecord(int userID) {
		Session session = sessionFacotry.getCurrentSession();
		Query<TransactionRecord> query = session.createQuery("from TransactionRecord where UserID = :userID", TransactionRecord.class);
		query.setParameter("userID", userID);
		List<TransactionRecord> list = query.list();
		return list;
	}
}
