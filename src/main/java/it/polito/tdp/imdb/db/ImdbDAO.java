package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Arco;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	// registi che hanno diretto almeno un film nell'anno selezionato
	/*
	 * id (directors)
	 * (movies_directors)
	 * year (movies)
	 */
	public List<Director> getVertices(Year year) {
		String sql = "SELECT DISTINCT d.id, d.first_name, d.last_name "
				+ "FROM directors d, movies_directors md, movies m "
				+ "WHERE m.year = ? "
				+ "AND d.id = md.director_id "
				+ "AND m.id = md.movie_id";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year.getValue());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 *  due registi che hanno diretto lo stesso attore nell'anno considerato 
	 *  peso = numero attori condivisi
	 *  attore condiviso se:
	 *  - registi hanno diretto insieme lo stesso film
	 *  - registi hanno diretto film diversi in cui recitava l'attore
	*/
	public List<Arco> getEdges(Year year, Map<Integer, Director> verticesIdMap) {
		
		String sql = "SELECT d1.id, d2.id, COUNT(distinct r1.actor_id) AS weight "
				+ "FROM directors d1, directors d2, movies_directors md1, movies_directors md2, movies m1, movies m2, roles r1, roles r2 "
				+ "WHERE d1.id > d2.id "
				+ "AND d1.id = md1.director_id AND m1.id = md1.movie_id AND m1.id = r1.movie_id "
				+ "AND d2.id = md2.director_id AND m2.id = md2.movie_id AND m2.id = r2.movie_id "
				+ "AND r1.actor_id = r2.actor_id "
				+ "AND m1.year = ? AND m1.year = m2.year "
				+ "GROUP BY d1.id, d2.id";
		
		List<Arco> result = new ArrayList<Arco>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year.getValue());
			ResultSet res = st.executeQuery();
			
			while (res.next()) {

				Director director1 = verticesIdMap.get(res.getInt("d1.id"));
				Director director2 = verticesIdMap.get(res.getInt("d2.id"));
				int peso = res.getInt("weight");
				
				Arco a = new Arco(director1, director2, peso);
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
