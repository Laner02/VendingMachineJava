package es.uva.inf.poo.practica;

import java.util.ArrayList;

/**
 * Implementación de un slot(una casilla) de una máquina de vending, que facilita la consulta o gestión de
 * vendibles, productos o packs que contiene.
 * @author rauvill, alvdela
 * 
 */
public class Slot {
	
	private String idSlot;
	private ArrayList<Vendible> vendibles;
	
	private final String msgErrorEmptySlot = "El slot esta vacío.";
	private final String msgErrorUPCNull = "El identificador UPC no puede ser nulo.";
	private final String msgErrorEmptyUPC = "El identificador UPC no puede estar vacío";
	
	/**
	 * Inicialización de un slot(casilla) vacío (sin vendibles).
	 * @param idSlot Identificador del slot formado por una letra del abecedario
	 * que representa la línea en la que se encuentra, seguida de un entero, que indica la columna.
	 * @throws IllegalArgumentException Cuando el identificador recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador recibido esta vacío.
	 */
	public Slot(String idSlot) {
		if(idSlot == null) {
			throw
					new IllegalArgumentException("El identificador del slot no puede ser nulo.");
		}
		if (idSlot.isEmpty()) {
			throw
					new IllegalArgumentException("El identificador del slot no puede estar vacío.");
		}
		this.idSlot = idSlot;					//No le pasamos un parametro de producto porque se inicia vacia, y le meten los productos que quieran a la linea
		vendibles = new ArrayList<>();
	}
	
	/**
	 * Consulta el identificador del slot.
	 * @return El identificador del slot.
	 */
	public String getIdSlot() {
		return idSlot;
	}
	
	/**
	 * Añade un vendible al slot.
	 * El vendible no puede ser nulo, y si el slot ya tiene algun vendible,
	 * el identificador del nuevo vendible debe ser igual que el del que esta en el slot.
	 * No se podran mezclar en la misma casilla productos y packs, debido a que sus identificadores únicos 
	 * serán diferentes.
	 * @param vendible El vendible a añadir, que puede ser tanto un producto como un pack de productos.
	 * @throws IllegalArgumentException Cuando el vendible es nulo.
	 * @throws IllegalArgumentException Cuando el slot no esta vacío y el identificador del nuevo vendible
	 * no es igual al del vendible que ya estaba.
	 */
	public void addVendible(Vendible vendible) {
		if(vendible == null) {
			throw
					new IllegalArgumentException("El vendible no puede ser nulo.");
		}
		if(!vendibles.isEmpty() && (!((vendible.getIdentifier()).equals(vendibles.get(0).getIdentifier())))) {		//Comprueba que si no esta vacia los upc sean iguales porque asi los productos serian iguales.
			throw
					new IllegalArgumentException("El vendible debe tener el mismo identificador que los que ya estan en el slot: " + vendible.getIdentifier() + " != " + vendibles.get(0).getIdentifier());
		}
		Vendible nuevoV = vendible.duplicate();				//clonamos el vendible que nos pasan
		vendibles.add(nuevoV);
	}
	
	/**
	 * Elimina un vendible del slot.
	 * @throws IllegalArgumentException Si el slot esta vacío.
	 */
	public void removeVendible() {
		if(vendibles.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptySlot);
		}
		vendibles.remove(0);
	}
	
	/**
	 * Consulta el precio del vendible que se encuentra en el slot.
	 * @return El precio del vendible, al que se le aplica un descuento en caso de ser un pack.
	 * @throws IllegalArgumentException Si el slot esta vacío.
	 */
	public double getVendiblePrice() {
		if(vendibles.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptySlot);
		}
		Vendible v = vendibles.get(0);
		return v.getPrice();
	}
	
	/**
	 * Consulta el identificador único del vendible que se encuentra en el slot.
	 * @return El identificador del vendible en el slot.
	 * @throws IllegalArgumentException Cuando el slot esta vacío.
	 */
	public String getVendibleId() {
		if(vendibles.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptySlot);
		}
		Vendible v = vendibles.get(0);
		return v.getIdentifier();
	}
	
	/**
	 * Comprueba si el slot de vendibles esta vacío.
	 * @return True si esta vacío, false si tiene productos.
	 */
	public boolean isSlotEmpty() {
		return vendibles.isEmpty();
	}
	
	/**
	 * Comprueba si el Slot contiene el producto, o un pack que contenga el producto 
	 * con el identificador único recibido.
	 * @param upc Identificador único del producto a buscar en el slot.
	 * @return True si el slot contiene ese producto (o un pack con el producto), false si no lo contiene.
	 * @throws IllegalArgumentException Cuando el identificador de producto recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador de producto recibido esta vacío.
	 * @throws IllegalArgumentException Cuando el slot está vacio.
	 */
	public boolean containsProduct(String upc) {
		if (upc == null) {
			throw
					new IllegalArgumentException(msgErrorUPCNull);
		}
		if (upc.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptyUPC);
		}
		if (vendibles.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptySlot);
		}
		boolean check = false;
		Vendible vendible = vendibles.get(0);
		try {
			Pack pack = (Pack)vendible;				//intentamos castear a un pack el vendible
			if(pack.containsProduct(upc))
				check = true;
		} catch(Exception e) {						//si el cast falla, es un producto
			if(upc.equals(vendible.getIdentifier())) {
				check = true;
			}
		}
		return check;
	}
	
	/**
	 * Cambia el precio de los vendibles del slot si son Productos, y el precio de ese producto 
	 * en un pack si el slot contiene packs.
	 * El nuevo precio de los productos no podra ser negativo ni 0.
	 * @param newPrice El precio que tendran los productos tras la modificación.
	 * @throws IllegalArgumentException Cuando el identificador de producto recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador de producto recibido esta vacío.
	 * @throws IllegalArgumentException Cuando el nuevo precio es negativo o 0.
	 * @throws IllegalArgumentException Cuando el slot esta vacío.
	 */
	public void changePrice(String upc, double newPrice) {
		if (upc == null) {
			throw
					new IllegalArgumentException(msgErrorUPCNull);
		}
		if (upc.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptyUPC);
		}
		if(newPrice <= 0) {
			throw
					new IllegalArgumentException("El nuevo precio no puede ser 0 o negativo.");
		}
		if(vendibles.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptySlot);
		}
		for(int i=0; i<vendibles.size(); i++) {
			Vendible vendible = vendibles.get(i);
			try {
				Pack pack = (Pack)vendible;							//probamos a castearlo a un pack
				pack.changePricePack(upc, newPrice);
			} catch(Exception e) {									//si el cast falla, es un producto
				Product product = (Product)vendible;
				product.setPrice(newPrice);
			}
		}
	}
}
