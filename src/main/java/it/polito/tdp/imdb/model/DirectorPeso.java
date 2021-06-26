package it.polito.tdp.imdb.model;

public class DirectorPeso implements Comparable<DirectorPeso> {

	private Director director;
	private double peso;
	
	public DirectorPeso(Director director, double d) {
		super();
		this.director = director;
		this.peso = d;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return director + " - " + "# attori condivisi: " + (int)peso;
	}

	@Override
	public int compareTo(DirectorPeso o) {
		return (int) -(this.peso - o.peso);
	}
	
	
	
}
