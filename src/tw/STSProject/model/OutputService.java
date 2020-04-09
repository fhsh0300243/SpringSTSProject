package tw.STSProject.model;

import java.io.IOException;
import java.sql.SQLException;

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
	
	public void outputStockInformationToJSON(int userID) throws IOException, SQLException{
		oDAO.outputStockInformationToJSON(userID);
	}
	
	public void outputInventoryToJSON(int userID) throws IOException, SQLException{
		oDAO.outputInventoryToJSON(userID);
	}
	
	public void outputTransactionRecordToJSON(int userID) throws IOException, SQLException{
		oDAO.outputTransactionRecordToJSON(userID);
	}
}
