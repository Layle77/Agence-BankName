package javaBeans;

import java.math.BigDecimal;

public class TestListeParDate {
	
	public static void main(String[] args) throws Exception {
		var op = new BOperations("","test","test",BigDecimal.ONE);
		op.setNoDeCompte("12A5");
		op.ouvrirConnexion();
		op.consulter();
		op.setDateInf("20191010");
		op.setDateSup("20191015");		
		op.listerParDate();
		var list = op.getOpList();
		for(String s : list) {
			System.out.println(s);
		}
		
		System.out.println("--------------------------");
		
		op.setDateInf("20191015");
		op.setDateSup("20191020");		
		op.listerParDate();
		var list2 = op.getOpList();
		for(String s : list2) {
			System.out.println(s);
		}
		
		op.fermerConnexion();
		
	}
}
