package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {
	private static HashMap<String, String> errorMap;
	
	public static String getMessageDErreur(String errCode) {
		return errorMap.get(errCode);
	}
}
