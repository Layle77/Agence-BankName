package javaBeans;

import java.math.BigDecimal;

public class TestConsultation {
	
	public static void main(String[] args) throws Exception {
		var op = new BOperations("","test","test",BigDecimal.ONE);
		op.setNoDeCompte("12A5");
		System.out.println(op.toString());
		op.ouvrirConnexion();
		System.out.println("Co open");
		op.consulter();
		System.out.println(op.toString());
		op.fermerConnexion();
		System.out.println("Co closed");
	}
}
