package it.polito.tdp.imdb.model;

import java.time.Year;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private SimpleWeightedGraph<Director, DefaultWeightedEdge> graph;
	private ImdbDAO dao;
	private List<Director> vertices;
	private Map<String, Director> verticesMap;
	
	public Model() {
		dao = new ImdbDAO();
	}
	
	public String createGraph(Year year) {
		
		graph = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertices = dao.getVertices(year);
		Collections.sort(vertices);
		
//		// mappa
//		verticesMap = new HashMap<>();
//		for (Director d : vertices) {
//			verticesMap.put(d.getId(), d);
//		}
//		
//		Graphs.addAllVertices(graph, vertices);
//		
//		List<Arco> edges = dao.getEdges();
//		for (Arco arco : edges) {
//			Graphs.addEdge(graph, verticesMap.get(arco.getVertex1()), verticesMap.get(arco.getVertex2()), arco.getWeight());
//		}
//		
		return String.format("Grafo creato con %d vertici e %d archi\n\n", vertices.size(), graph.edgeSet().size());
//		
	}

}
