package utils;

public class Formater {
	/**
	 * format with adding delimiter objects to be outputed
	 * @param obj
	 * @param strDelimiter
	 * @param strDefault
	 * @return
	 */
	public static String format( Object obj, String strDelimiter, String strDefault )
	{
		
		String strTobeFromated = strDefault;
		if ( obj != null )
		{
			strTobeFromated = obj.toString();
			replaceDelimiter( strTobeFromated, strDelimiter );
		}
		
		return String.format("%-30s", strTobeFromated) + strDelimiter;
	}
	
	
	/**
	 * replace strDelimiter character
	 * @param strTobeFormated
	 * @param strDelimiter
	 * @return
	 */
	
	public static String replaceDelimiter( String strTobeFormated, String strDelimiter )
	{
		
		switch ( strDelimiter )
		{
			case ",":
				strTobeFormated.replace( strDelimiter, ";" );
				break;
			case ";":
				strTobeFormated.replace( strDelimiter, ",");
				break;
			case "\t":
				strTobeFormated.replace( strDelimiter, " " );
				break;
			
			default:
			case "\n":
				strTobeFormated.replace( strDelimiter, " " );
		}
		return strTobeFormated;
	}

}
