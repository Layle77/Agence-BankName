package javaBeans;

import java.math.BigDecimal;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;

public class TestConsultation {

	public static void main(String[] args) {
		var op = new BOperations("","test","test",BigDecimal.ONE);
		op.setNoDeCompte("12A5");
		System.out.println(op.toString());
		try {
			op.ouvrirConnexion();
			System.out.println("Co open");
			op.consulter();
			System.out.println(op.toString());
			op.fermerConnexion();
		}catch(TraitementException te) {
			System.err.println(MessagesDErreurs.getMessageDErreur(te.getMessage()));
		}
		System.out.println("Co closed");
	}
}
