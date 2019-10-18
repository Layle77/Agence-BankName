package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {
	private static HashMap<String, String> errorMap;
	public MessagesDErreurs() {
		errorMap.put("3", "Probleme pour acceder a ce compte client, verifiez qu'il est bien valide");
		errorMap.put("21", "Probleme d'acces a la base de donnee, veuillez le signaler a votre administrateur");
		errorMap.put("22", "Probleme apres traitement, le traitement a ete effectue correctement mais il y a eu un probleme a signaler a l'administrateur ");
		errorMap.put("24", "Operation refusee, debit demande superieur au credit du compte");
	}
	public static String getMessageDErreur(String errCode) {
		return errorMap.get(errCode);
	}
}
