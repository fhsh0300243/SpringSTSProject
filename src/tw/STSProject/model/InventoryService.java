package tw.STSProject.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService implements IInventoryService {
	private InventoryDAO iDAO;
	
	@Autowired
	public InventoryService(InventoryDAO iDAO) {
		this.iDAO = iDAO;
	}
	
	public boolean insertOrUpadteInventory(int userID, String stockCode, int quantity) {
		return iDAO.insertOrUpadteInventory(userID, stockCode, quantity);
	}
	
	public int deleteInventory(int userID, String stockCode) {
		return iDAO.deleteInventory(userID, stockCode);
	}
	
	public List<Inventory> findAllUserInventory(int userID) {
		return iDAO.findAllUserInventory(userID);
	}
}