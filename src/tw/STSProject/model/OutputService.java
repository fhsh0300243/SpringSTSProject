package tw.STSProject.model;

import java.io.IOException;
import java.sql.SQLException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutputService implements IOutputService{
	private OuputDAO oDAO;
	
	@Autowired
	public OutputService(OuputDAO oDAO) {
		this.oDAO = oDAO;
	}
	
	public void outputFavoriteStockToCsv(int userID) throws IOException, SQLException{
		oDAO.outputFavoriteStockToCsv(userID);
	}
	
	public JSONArray outputStockInformationToJSON(int userID) throws IOException, SQLException{
		return oDAO.outputStockInformationToJSON(userID);
	}
	
	public JSONArray outputInventoryToJSON(int userID) throws IOException, SQLException{
		return oDAO.outputInventoryToJSON(userID);
	}
	
	public JSONArray outputTransactionRecordToJSON(int userID) throws IOException, SQLException{
		return oDAO.outputTransactionRecordToJSON(userID);
	}
}
