package javaBeans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import gestionDB.BDDGlobalVar;

public class BOperations {
	private  String noDeCompte;
	private  String nom;
	private  String prenom;
	private String op;
	private BigDecimal ancienSolde;
	private BigDecimal nouveauSolde;
	private BigDecimal solde;
	private BigDecimal valeur;
	private Connection connexion;

	public BOperations(String noDeCompte, String nom, String prenom, BigDecimal solde) {
		this.noDeCompte = noDeCompte;
		this.nom = nom;
		this.prenom = prenom;
		this.solde = solde;
	}

	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = Objects.requireNonNull(noDeCompte);
	}

	public String getValeur() {
		return valeur.toString();
	}

	public BigDecimal getAncienSolde() {
		return this.ancienSolde;
	}

	public BigDecimal getNouveauSolde() {
		return this.nouveauSolde;
	}

	public String getOp() {
		return this.op;
	}

	public void setValeur(String valeur) {
		this.valeur = new BigDecimal(valeur);
	}


	public void ouvrirConnexion() {

		//STEP 3: Open a connection
		try {
			connexion = DriverManager.getConnection(BDDGlobalVar.BDD_URL,BDDGlobalVar.BDD_LOGIN,BDDGlobalVar.BDD_PWD);
		} catch (SQLException e) {
			try {
				connexion.close();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
	}

	public void fermerConnexion() {
		try {
			connexion.close();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void consulter() throws Exception {

		//requete
		String sql = "SELECT * FROM COMPTE WHERE NOCOMPTE = '"+this.noDeCompte+"'";
		Statement stmt;
		ResultSet rs;
		try{	
			stmt = connexion.createStatement();
			rs = stmt.executeQuery(sql);
		}		
		catch(SQLException e) {
			//TODO
			e.printStackTrace();
			throw new Exception(e);
			//Display values
		}

		try {
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.solde = rs.getBigDecimal("SOLDE");
				this.prenom = rs.getString("PRENOM");
				this.nom = rs.getString("NOM");

			}}catch(SQLException e) {
				e.printStackTrace();			
			}finally {
				//STEP 6: Clean-up environment
				rs.close();
				stmt.close();
			}
	}

	public void traiter() throws Exception {
		//requete
		String sql = "SELECT SOLDE FROM COMPTE WHERE NOCOMPTE = '"+this.noDeCompte+"'";
		String sql2;
		Statement stmt;
		ResultSet rs;
		try{	
			stmt = connexion.createStatement();
			rs = stmt.executeQuery(sql);
		}		
		catch(SQLException e) {
			//TODO
			e.printStackTrace();
			throw new Exception(e);
			//Display values
		}

		try {
			//STEP 5: Extract data from result set
			while(rs.next()){
				//Retrieve by column name
				this.ancienSolde = rs.getBigDecimal("SOLDE");

				
				switch(this.op) {
				case "+" : this.nouveauSolde = new BigDecimal("0").add(ancienSolde).add(this.valeur);
				break;
				case "-" : 
					this.nouveauSolde = new BigDecimal("0").add(ancienSolde).subtract(this.valeur);
					if(this.nouveauSolde.compareTo(BigDecimal.ZERO) < 0) {
						System.out.println("Nouveau solde est negatif refus de l'opération de transaction");
						return;
					}
				break;
				default : throw new Exception();
				}
				sql = "UPDATE COMPTE SET SOLDE = "+this.nouveauSolde.doubleValue()+" WHERE NOCOMPTE = '"+this.noDeCompte+"'";
				sql2 = "INSERT INTO OPERATIONS (NOCOMPTE,DATE,HEURE,OP,VALEUR) VALUES"+
				"('"+this.noDeCompte+"',"+
				"CURRENT_DATE, "+
				"CURRENT_TIME, "+
				"'"+this.op+"',"+this.valeur.doubleValue()+")";
				
				rs = stmt.executeQuery(sql);
				rs = stmt.executeQuery(sql2);
				
			}}catch(SQLException e) {
				e.printStackTrace();			
			}finally {
				//STEP 6: Clean-up environment
				rs.close();
				stmt.close();
			}




	}


	@Override
	public String toString() {
		return "BOperations [noDeCompte=" + noDeCompte + ", nom=" + nom + ", prenom=" + prenom + ", solde=" + solde
				+ ", connexion=" + connexion + "]";
	}


}

