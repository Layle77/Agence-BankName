package javaBeans;

import java.math.BigDecimal;

public class TestTraitement {
	
	public static void main(String[] args) throws Exception {
		var op = new BOperations("","test","test",BigDecimal.ONE);
		//Test +
		op.setNoDeCompte("12A5");
		op.ouvrirConnexion();
		System.out.println("Co open");
		op.consulter();
		System.out.println(op);
		System.out.println("Add 1000");
		op.setValeur("1000");
		op.setOP("+");
		op.traiter();
		op.consulter();
		System.out.println("Add done");
		System.out.println(op.toString());
		op.fermerConnexion();
		System.out.println("Co closed");
		
		//Test negative solde
		op.setNoDeCompte("145B");
		op.ouvrirConnexion();
		System.out.println("Co open");
		op.consulter();
		System.out.println(op);
		System.out.println("Sub 15");
		op.setValeur("1000");
		op.setOP("-");
		op.traiter();
		op.consulter();
		System.out.println("Sub done");
		System.out.println(op.toString());
		op.fermerConnexion();
		System.out.println("Co closed");
		
	}
}
