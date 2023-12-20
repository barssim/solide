package utils;


import java.util.Random;

public class CodeManager {
	
	private static int randInt( int min, int max )
	{
    
		Random rand = new Random();
    // nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt( ( max - min ) + 1 ) + min;
		return randomNum;
	}
	//method for generating new code
	public static int generateCode()
	{
		int generatedCode = randInt(1001, 9998);
		return generatedCode;
	}
	
	public boolean checkCode(int code, int generatedCode)
	{
		if(code == generatedCode)
		{
			return true;
		}
		else 
		{
			return false;
		}
		// test mode
//		return true;
		
	}

}
