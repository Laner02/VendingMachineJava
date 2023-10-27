package es.uva.inf.poo.practica;

/**
 * Clase que implementa la funcionalidad de un objeto Vendible, que se encuentran en los slot
 * de la máquina de vending.
 * Pueden ser productos como tal o packs de varios productos.
 * @author rauvill, alvdela
 *
 */
public abstract class Vendible {
	
	private String name;
	private String idVendible;
	
	/**
	 * Inicializa un vendible, ya sea un Producto o un Pack de productos, con un nombre y un identificador 
	 * único de cada vendible.
	 * @param name Nombre del vendible.
	 * @param idVendible Identificador del vendible.
	 * @throws IllegalArgumentException Cuando el nombre recibido es nulo.
	 * @throws IllegalArgumentException Cuando el nombre recibido esta vacío.
	 * @throws IllegalArgumentException Cuando el identificador recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador recibido esta vacío.
	 */
	protected Vendible(String name, String idVendible) {
		if (name == null) {
			throw
					new IllegalArgumentException("El nombre no puede ser nulo.");
		}
		if (name.isEmpty()) {
			throw
					new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		if (idVendible == null) {
			throw
					new IllegalArgumentException("El identificador no puede ser nulo.");
		}
		if (idVendible.isEmpty()) {
			throw
					new IllegalArgumentException("El identificador no puede estar vacío.");
		}
		this.name = name;
		this.idVendible = idVendible;
	}
	
	/**
	 * Consulta el nombre del vendible.
	 * @return El nombre del vendible.
	 */
	public String getName() {		//public si lo puede usar tod o el mundo, protected si solo quieres que los hijos lo usen (como checkdigit y tal)
		return name;
	}
	
	/**
	 * Consulta el precio que tiene el vendible.
	 * @return El precio del vendible.
	 */
	public abstract double getPrice();	//Este es un metodo abstracto, lo implementa cada clase hijo como tenga que hacerlo (pack tiene que calcularlo, y en product es un atributo)
	
	/**
	 * Consulta el identificador del vendible
	 * @return El identificador que tiene el vendible, ya sea un pack o un producto.
	 */
	public String getIdentifier() {
		return idVendible;
	}
	
	/**
	 * Devuelve una copia del Vendible con el que se llama al método, ya sea un pack o un producto.
	 * @return Un objeto vendible igual que el actual.
	 */
	public abstract Vendible duplicate();
}
