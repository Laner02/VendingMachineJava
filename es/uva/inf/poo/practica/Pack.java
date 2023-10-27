package es.uva.inf.poo.practica;

import java.util.ArrayList;

/**
 * Clase que hereda de Vendible e implementa la funcionalidad de un pack de varios productos, 
 * perteneciente a un slot de la m�quina de vending.
 * @author rauvill, alvdela
 *
 */
public class Pack extends Vendible {
	
	private ArrayList<Product> products;
	
	/**
	 * Inicializa un pack de productos, con el nombre, identificador y una lista array de productos recibidos.
	 * Un pack deber� contener al menos 2 productos, y los productos que forman un pack no podr�n
	 * estar repetidos entre ellos (mismo identificador UPC).
	 * @param name Nombre del pack.
	 * @param identifier Identificador �nico del pack.
	 * @param products Vector array de productos(Product), con los productos que contendr� el pack a inicializar.
	 * @throws IllegalArgumentException Cuando el nombre del pack es nulo.
	 * @throws IllegalArgumentException Cuando el nombre del pack esta vac�o.
	 * @throws IllegalArgumentException Cuando el identificador es nulo.
	 * @throws IllegalArgumentException Cuando el identificador esta vac�o.
	 * @throws IllegalArgumentException Cuando la lista de productos es nula.
	 * @throws IllegalArgumentException Cuando la lista de productos no contiene al menos 2 productos.
	 * @throws IllegalArgumentException Cuando la lista de productos recibida contiene productos repetidos.
	 */
	public Pack(String name, String identifier, Product[] products) {		//ponemos 2 constructores para que el vector de productos sea array o arraylist a eleccion
		super(name, identifier);
		if (products == null) {
			throw
					new IllegalArgumentException("La lista de productos no puede ser nula.");
		}
		if (products.length < 2) {
			throw
					new IllegalArgumentException("La lista de productos debe contener un m�nimo 2 productos.");
		}
		this.products = new ArrayList<>();
		for(int i=0;i<products.length;i++) {
			this.products.add(products[i]);
		}
		
		if (this.checkRepeated(this.products)) {
			throw
					new IllegalArgumentException("La lista de productos no puede contener productos repetidos.");
		}
	}
	
	/**
	 * Inicializa un pack de productos, con el nombre, identificador y una lista ArrayList de productos recibidos.
	 * Un pack deber� contener al menos 2 productos, y los productos que forman un pack no podr�n
	 * estar repetidos entre ellos (mismo identificador UPC).
	 * @param name Nombre del pack.
	 * @param identifier Identificador �nico del pack. 
	 * @param products ArrayList de productos(Product), con los productos que contendr� el pack a inicializar.
	 * @throws IllegalArgumentException Cuando el nombre del pack es nulo.
	 * @throws IllegalArgumentException Cuando el nombre del pack esta vac�o.
	 * @throws IllegalArgumentException Cuando el identificador es nulo.
	 * @throws IllegalArgumentException Cuando el identificador esta vac�o.
	 * @throws IllegalArgumentException Cuando la lista de productos es nula.
	 * @throws IllegalArgumentException Cuando la lista de productos recibida tiene menos de 2 productos.
	 * @throws IllegalArgumentException Cuando la lista de productos recibida tiene 2 o m�s productos repetidos.
	 */
	public Pack(String name, String identifier, ArrayList<Product> products) {
		super(name, identifier);
		if(products == null) {
			throw
					new IllegalArgumentException("La lista de productos no puede ser nula.");
		}
		if(products.size() < 2) {					//con esto ya comprobamos que la lista no este vacia, pero por si acaso hacerle un test
			throw
					new IllegalArgumentException("La lista de productos debe contener un m�nimo de 2 productos.");
		}
		if(this.checkRepeated(products)) {
			throw
					new IllegalArgumentException("La lista de productos recibida no puede contener productos repetidos.");
		}
		ArrayList<Product> p = new ArrayList<>();
		p.addAll(products);
		this.products = p;
	}
	
	/**
	 * Consulta el nombre de los productos que contiene el pack.
	 * @return Una cadena con los nombres de los productos que se encuentran en el pack, o una cadena
	 * diciendo que no tiene productos en case de estar vac�o.
	 */
	public String getProductsName() {
		if (products.isEmpty()) {
			return "Este pack no contiene productos.";
		}
		String productsName = "Este Pack esta formado por los productos: " + products.get(0).getName();
		for(int i = 1;i<products.size();i++) {
			productsName = productsName + ", " + products.get(i).getName();
		}
		return productsName + ".";
	}
	
	public Pack duplicate() {					//No hace falta javadoc porque el javadoc que se lee es el del padre
		return new Pack(super.getName(), super.getIdentifier(), products);
	}

	public double getPrice() {
		double price = 0;						//no puede no devolver un precio porque para crear un objeto pack, tienes que meter minimo 2 productos
		for(int i=0;i<products.size();i++) {
			price += products.get(i).getPrice();
		}
		return price * 0.2;						//aplicamos el descuento a la suma de los precios de los productos
	}
	
	/**
	 * Consulta el n�mero de productos que forman el pack.
	 * @return La cantidad de productos que hay en el pack.
	 */
	public int getPackSize() {
		return products.size();
	}
	
	/**
	 * A�ade un producto al pack. Los productos a a�adir no podr�n 
	 * ser iguales que cualquiera de los que ya estaban en el pack.
	 * @param product Producto a a�adir al pack.
	 * @throws IllegalArgumentException Cuando el producto recibido es nulo.
	 * @throws IllegalAgumentException Cuando el producto recibido ya se encontraba en el pack.
	 */
	public void addProduct(Product product) {
		if (product == null) {
			throw
					new IllegalArgumentException("El producto a a�adir no puede ser nulo.");
		}
		if (products.contains(product)) {
			throw
					new IllegalArgumentException("No se admiten productos repetidos en un mismo pack.");
		}
		products.add(product);
	}
	
	/**
	 * Elimina un producto del pack a partir de su identificador �nico UPC. 
	 * Si el pack ya estaba vac�o no podr� eliminarlo.
	 * @param upc Identificador del producto a eliminar del pack.
	 * @throws IllegalArgumentException Cuando el identificador recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador recibido esta vac�o.
	 * @throws IllegalArgumentException Cuando se intenta eliminar un producto de un pack vac�o.
	 * @throws IllegalArgumentException Cuando el producto a eliminar no esta en el pack.
	 */
	public void removeProduct(String upc) {
		if (upc == null) {
			throw
					new IllegalArgumentException("El identificador �nico UPC no puede ser nulo.");
		}
		if (upc.isEmpty()) {
			throw
					new IllegalArgumentException("El identificador �nico UPC no puede estar vac�o.");
		}
		if (products.isEmpty()) {
			throw
					new IllegalArgumentException("El pack ya esta vac�o.");
		}
		if (!containsProduct(upc)) {
			throw
					new IllegalArgumentException("El producto no se encuentra en el pack.");
		}
		for(int i=0;i<products.size();i++) {
			if(upc.equals(products.get(i).getIdentifier()))
				products.remove(i);
		}
	}
	
	/**
	 * Comprueba si el pack contiene el producto con el identificador �nico recibido.
	 * @param upc Identificador del producto a buscar dentro del pack.
	 * @return True si el producto se encuentra en el pack, false si no.
	 * @throws IllegalArgumentException Cuando el identificador de producto recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador de producto recibido esta vac�o.
	 * @throws IllegalArgumentException Cuando el pack esta vac�o.
	 */
	public boolean containsProduct(String upc) {
		if (upc == null) {
			throw
					new IllegalArgumentException("El identificador UPC no puede ser nulo.");
		}
		if (upc.isEmpty()) {
			throw
					new IllegalArgumentException("El identificador UPC no puede estar vac�o.");
		}
		if (products.isEmpty()) {
			throw
					new IllegalArgumentException("El pack esta vac�o.");
		}
		boolean check = false;
		for(int i=0;i<products.size();i++) {
			if(upc.equals(products.get(i).getIdentifier())) {
				check = true;
			}
		}
		return check;
	}

	/**
	 * Modifica el precio de uno de los productos que contiene el pack.
	 * @param upc Identificador �nico del producto a modificar.
	 * @param newPrice Precio que tendr� el producto tras la modificaci�n.
	 * @throws IllegalArgumentException Cuando el upc es nulo.
	 * @throws IllegalArgumentException Cuando el upc esta vac�o.
	 * @throws IllegalArgumentException Cuando el nuevo precio es negativo o 0.
	 * @throws IllegalArgumentException Cuando el pack a modificar esta vac�o.
	 */
	public void changePricePack(String upc, double newPrice) {
		if (upc == null) {
			throw
					new IllegalArgumentException("El identificador de producto no puede ser nulo.");
		}
		if (upc.isEmpty()) {
			throw
					new IllegalArgumentException("El identificador de producto no puede estar vac�o.");
		}
		if (products.isEmpty()) {
			throw
					new IllegalArgumentException("El pack esta vac�o.");
		}
		for(int i=0; i<products.size(); i++) {
			if(upc.equals(products.get(i).getIdentifier())) {
				products.get(i).setPrice(newPrice);
			}
		}
	}
	
	/**
	 * M�todo privado que comprueba que en un ArrayList de Productos reccibido 
	 * todos los productos que lo formas sean id�nticos (mismo identificador UPC).
	 * @param p Lista de productos a comprobar.
	 * @return True si todos los productos son iguales, false si uno o m�s de ellos son diferentes.
	 */
	private boolean checkRepeated(ArrayList<Product> p) {	//Comprueba si esta repetido un producto en el arraylist de productos recibido
		boolean repeated = false;
		for(int i=0;i<p.size();i++) {
			int cont = 0;									//un contador dentro de for, para que cada producto tenga su propio contador
			String identificador = p.get(i).getIdentifier();
			for(int j=0;j<p.size();j++) {
				if(identificador.equals(p.get(j).getIdentifier()))
					cont++;
			}
			if(cont>1)
				repeated = true;
		}
		return repeated;
	}
}
