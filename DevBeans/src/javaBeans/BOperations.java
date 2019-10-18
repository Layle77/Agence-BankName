package javaBeans;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import gestionDB.BDDGlobalVar;
import gestionErreurs.TraitementException;

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
	private String dateInf;
	private String dateSup;
	private ArrayList<String> opList;

	public BOperations(String noDeCompte, String nom, String prenom, BigDecimal solde) {
		this.noDeCompte = noDeCompte;
		this.nom = nom;
		this.prenom = prenom;
		this.solde = solde;
		this.opList = new ArrayList<String>();
		this.op = "+";
	}

	//GETTERS---------------------------------

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

	public String getDateInf() {
		return this.dateInf;
	}

	public String getDateSup() {
		return this.dateSup;
	}

	public ArrayList<String> getOpList(){
		return this.opList;
	}

	//SETTERS-------------------------------------------
	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = Objects.requireNonNull(noDeCompte);
	}

	public void setValeur(String valeur) {
		this.valeur = new BigDecimal(Objects.requireNonNull(valeur));
	}

	public void setOP(String op) {
		this.op = Objects.requireNonNull(op);
	}

	public void setDateInf(String dateInf) {
		this.dateInf = Objects.requireNonNull(dateInf);
	}

	public void setDateSup(String dateSup) {
		this.dateSup = Objects.requireNonNull(dateSup);
	}


	public void ouvrirConnexion() throws TraitementException {

		//STEP 3: Open a connection
		try {
			connexion = DriverManager.getConnection(BDDGlobalVar.BDD_URL,BDDGlobalVar.BDD_LOGIN,BDDGlobalVar.BDD_PWD);
			//Set autocommit for transactinnal connection
			connexion.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TraitementException("21");
		}
	}

	public void fermerConnexion() throws TraitementException {
		try {
			connexion.close();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new TraitementException("22");
		}
	};

	public void consulter() throws TraitementException {

		//requete
		String sql = "SELECT * FROM COMPTE WHERE NOCOMPTE = '"+this.noDeCompte+"'";
		Statement stmt;
		ResultSet rs;
		try{	
			stmt = connexion.createStatement();
			rs = stmt.executeQuery(sql);
		}catch(SQLException e) {
			e.printStackTrace();
			throw new TraitementException("3");
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
				try {
					//STEP 6: Clean-up environment
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new TraitementException("22");
				}
			}
	}

	public void traiter() throws TraitementException {
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
			e.printStackTrace();
			throw new TraitementException("21");
		}

		try {
			//STEP 5: Extract data from result set
			if(rs.next()){
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
				}
				sql = "UPDATE COMPTE SET SOLDE = "+this.nouveauSolde.doubleValue()+" WHERE NOCOMPTE = '"+this.noDeCompte+"'";
				sql2 = "INSERT INTO OPERATION (NOCOMPTE,DATE,HEURE,OP,VALEUR) VALUES"+
						"('"+this.noDeCompte+"',"+
						"CURRENT_DATE, "+
						"CURRENT_TIME, "+
						"'"+this.op+"',"+this.valeur.doubleValue()+")";

				stmt.executeUpdate(sql);
				stmt.executeUpdate(sql2);

				//Valid the requests
				connexion.commit();

			}else{
				System.out.println("Client NOCOMPTE : "+this.noDeCompte+" not found");
				throw new TraitementException("3");
			}
		}catch(SQLException e) {
				if(connexion != null) {
					try {
						connexion.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				e.printStackTrace();			
			}finally {
				//STEP 6: Clean-up environment
				try {
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

	public void listerParDate() throws Exception{

		//requete
		String sql = "SELECT * FROM OPERATION WHERE NOCOMPTE = '"+this.noDeCompte+"' AND DATE BETWEEN '"+this.dateInf+"' AND '"+this.dateSup+"'";
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
				StringBuilder sResult = new StringBuilder();
				sResult.append(rs.getString("NOCOMPTE")+" ");
				sResult.append(rs.getString("DATE")+" ");
				sResult.append(rs.getString("HEURE")+" ");
				sResult.append(rs.getString("OP")+" ");
				sResult.append(rs.getString("VALEUR")+" ");
				opList.add(sResult.toString());
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

