package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Coppia;
import it.polito.tdp.genes.model.Genes;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Genes> getAllEssentialGenes(){
		String sql = "SELECT * "
				+ "FROM genes "
				+ "WHERE Essential = 'Essential' "
				+ "GROUP BY GeneID";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Coppia> getCoppie(Map<String, Genes> idMap){
		String sql = "SELECT g1.GeneID AS g1, g2.GeneID AS g2, g1.Chromosome AS c1, g2.Chromosome AS c2, ABS(i.Expression_Corr) AS peso "
				+ "FROM genes g1, genes g2, interactions i "
				+ "WHERE g1.Essential = 'Essential' "
				+ "AND g2.Essential = 'Essential' "
				+ "AND ((g1.GeneID = i.GeneID1 AND g2.GeneID = i.GeneID2) OR (g1.GeneID = i.GeneID2 AND g2.GeneID = i.GeneID1)) "
				+ "AND g1.GeneID > g2.GeneID "
				+ "GROUP BY g1.GeneID, g2.GeneID, c1, c2";
		List<Coppia> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Genes g1 = idMap.get(res.getString("g1"));
				Genes g2 = idMap.get(res.getString("g2"));
				if(res.getInt("c1") == res.getInt("c2")) {
					result.add(new Coppia(g1, g2, 2*res.getDouble("peso")));
				}
				else {
					result.add(new Coppia(g1, g2, res.getDouble("peso")));
				}
				
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	


	
}
