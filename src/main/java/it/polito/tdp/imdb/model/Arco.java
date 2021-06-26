package it.polito.tdp.imdb.model;

public class Arco {
	
	private Director d1;
	private Director d2;
	private int weight;
	
	public Arco(Director d1, Director d2, int weight) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.weight = weight;
	}
	public Director getD1() {
		return d1;
	}
	public void setD1(Director d1) {
		this.d1 = d1;
	}
	public Director getD2() {
		return d2;
	}
	public void setD2(Director d2) {
		this.d2 = d2;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	@Override
	public String toString() {
		return "Arco [d1=" + d1 + ", d2=" + d2 + ", weight=" + weight + "]";
	}
	
	

}
