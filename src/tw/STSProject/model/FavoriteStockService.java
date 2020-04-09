package tw.STSProject.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteStockService implements IFavoriteStockService {
	private FavoriteStockDAO fsDAO;
	
	@Autowired
	public FavoriteStockService(FavoriteStockDAO fsDAO) {
		this.fsDAO = fsDAO;
	}
	
	public boolean insertFavoriteStock(int userID, String stockCode) {
		return fsDAO.insertFavoriteStock(userID, stockCode);
	}
	
	public int deleteFavoriteStock(int userID, String stockCode) {
		return fsDAO.deleteFavoriteStock(userID, stockCode);
	}
	
	public List<FavoriteStock> findAllUserFavoriteStock(int userID){
		return fsDAO.findAllUserFavoriteStock(userID);
	}
	
	public FavoriteStock findUserFavoriteStock(int userID, String stockCode) {
		return fsDAO.findUserFavoriteStock(userID, stockCode);
	}
}
