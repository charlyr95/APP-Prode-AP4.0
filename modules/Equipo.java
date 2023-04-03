package modules;

public class Equipo {
	private String name;

	public Equipo (String nombre){
		this.name = nombre;
	}

	public String getNombre(){
		return this.name;
	};

	public void setName(String nombre){
		this.name = nombre;
	}

}
