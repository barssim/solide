package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InvalidPropertiesFormatException;

import dbServices.NoRange;

public class NoRanger {
	static NoRange noRange;

	public static int getNext( String noObj) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, SQLException, InvalidPropertiesFormatException, FileNotFoundException, IOException {
		int iNextRange = 0;
		if (noRange == null) {
			noRange = new NoRange();
		}
		try (ResultSet rs = noRange.getRange( noObj)) {
			if (rs.next()) {
				iNextRange = rs.getInt(NoRange.FLD_NEXTNO);
			}
		}
		;

		return iNextRange;
	}
	/**
	 * 
	 * @param locNo
	 * @param noObj
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void incrementNextNo(String noObj) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException,  SQLException, IOException {
		int iNextRange = 0;
		int iRangeFrom = 0;
		int iRangeTo = 0;
		if (noRange == null) {
			noRange = new NoRange();
		}
		ResultSet rs = noRange.getRange(noObj);
			if (rs.next()) {
				iNextRange = rs.getInt(NoRange.FLD_NEXTNO);
				iRangeFrom = rs.getInt(NoRange.FLD_RANGEFROM);
				iRangeTo = rs.getInt(NoRange.FLD_RANGETO);
			}
		
			if (iNextRange >= iRangeTo) {
				noRange.updateRange(noObj, iRangeFrom);
			} else {
				noRange.updateRange(noObj, iNextRange + 1);
			}
		}
	
	/**
	 * 
	 * @param locNo
	 * @param iRangeToCheck
	 * @return
	 * @throws InvalidPropertiesFormatException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
	public boolean checkRange(int locNo, int iRangeToCheck) throws InvalidPropertiesFormatException, FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		boolean rangeIsOk = false;
		int iRangeFrom = 0;
		int iRangeTo = 0;
		if (noRange == null) {
			noRange = new NoRange();
		}
		try(ResultSet rs = noRange.getRange(SolideConstants.ARTICLE))
		{
			if (rs.next()) {
				iRangeFrom = rs.getInt(NoRange.FLD_RANGEFROM);
				iRangeTo = rs.getInt(NoRange.FLD_RANGETO);
			}
		
			if (iRangeToCheck >= iRangeFrom && iRangeToCheck <= iRangeTo) {
				rangeIsOk = true;
			}
			return rangeIsOk;
	}
	}
}
