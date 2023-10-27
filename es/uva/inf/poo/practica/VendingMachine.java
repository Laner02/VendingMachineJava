package es.uva.inf.poo.practica;

import java.util.ArrayList;

import fabricante.externo.tarjetas.TarjetaMonedero;

/**
 * Clase que implementa las características y funcionalidad de
 * una máquina de vending.
 * @author rauvill, alvdela
 *
 */
public class VendingMachine {

	private final String msgErrorIdNull = "El identificador no puede ser nulo.";
	private final String msgErrorEmptyId = "El identificador no puede estar vacío";
	private final String msgErrorIdSlot = "El identificador de slot no puede estar vacio.";
	private final String msgErrorBusqueda = "El slot solicitado no se encuentra en la maquina.";
	
	private String idMachine;
	private boolean operative;													//true en servicio, false fuera de servicio
	private ArrayList<ArrayList<Slot>> slots;
	private char[] dictionary = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();		//creo un diccionario con las letras del abecedario para los id de casillas
	
	/**
	 * Inicializa una máquina de vending con un identificador propio, y las filas y columnas
	 * que introduzca el usuario.
	 * Ni las filas ni las columnas pueden ser negativas, y tampoco pueden ser 0.
	 * El número de columnas no puede superar 26, al caracterizarse cada slot(casilla) con una letra del
	 * abecedario inglés y un entero (ej.: A9), no hay letras para columnas de más. No existiran slots
	 * con el mismo identificador dentro de la misma máquina.
	 * @param idMachine La cadena de caracteres que identifica a la máquina.
	 * @param numCol El número de columnas de la máquina.
	 * @param numLin El número de líneas de la máquina.
	 * @throws IllegalArgumentException Cuando el identificador de slot es nulo.
	 * @throws IllegalArgumentException Cuando el identificador esta vacío.
	 * @throws IllegalArgumentException Cuando el numero de columnas es negativo, o cero.
	 * @throws IllegalArgumentException Cuando el numero de filas es negativo, o cero.
	 * @throws IllegalArgumentException Cuando el número de columnas supera 26 (el número de letras del diccionario inglés).
	 */
	public VendingMachine(String idMachine, int numCol, int numLin) {
		if (idMachine == null) {
			throw
					new IllegalArgumentException(msgErrorIdNull);
		}
		if (idMachine.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorEmptyId);
		}
		if (numCol <= 0) {
			throw
					new IllegalArgumentException("El numero de columnas no puede ser negativo ni cero.");
		}
		if (numLin <= 0) {
			throw
					new IllegalArgumentException("El numero de filas no puede ser negativo ni cero.");
		}
		if (numCol > dictionary.length) {
			throw
					new IllegalArgumentException("El número de columnas no puede superar el numero de letras del diccionario: 26.");
		}
		this.idMachine = idMachine;
		operative = true;
		slots = new ArrayList<>();
		for(int i=0; i<numCol; i++) {
			ArrayList<Slot> slot = new ArrayList<>();
			String letra = Character.toString(dictionary[i]);
			for(int j=0;j<numLin; j++) {
				Slot s = new Slot(letra + j);
				slot.add(s);
			}
			slots.add(slot);
		}
	}
	
	/**
	 * Consulta el identificador de la máquina de vending.
	 * @return El identificador asignado a la máquina.
	 */
	public String getIdMachine() {
		return idMachine;
	}
	
	/**
	 * Consulta el estado de la máquina de vending.
	 * @return True si esta operativa, False si no lo esta.
	 */
	public boolean isOperative() {
		return operative;
	}
	
	/**
	 * Cambia el estado de la máquina actual por el opuesto.
	 */
	public void switchOperative() {
		operative = !operative;
	}
	
	/**
	 * Reabastece el slot(casilla) elegido por el identificador, con el Vendible dado.
	 * No se almacenaran en el mismo slot productos y packs juntos, debido a que cada uno 
	 * tiene identificadores únicos diferentes, que nunca pueden ser iguales.
	 * Si el slot no estaba vacío, el Vendible debe ser del mismo tipo que los que ya se 
	 * encontraban allí (mismo identificador único).
	 * @param idSlot El identificador del slot a reabastecer.
	 * @param vendible Vendible a añadir que deber ser el mismo que el que ya se encuentra en el slot si hay uno.
	 * @throws IllegalArgumentException Cuando el identificador de slot recibido es nulo.
	 * @throws IllegalArgumentException Cuando el identificador de slot esta vacío.
	 * @throws IllegalArgumentException Cuando el vendible recibido es nulo.
	 * @throws IllegalArgumentException Cuando el slot con el identificador recibido no se encuentra en la máquina.
	 * @throws IllegalArgumentException Cuando el slot no esta vacío y el identificador del nuevo vendible no es igual al que ya había.
	 */
	public void restockSlot(String idSlot, Vendible vendible) {
		if (idSlot == null) {
			throw
					new IllegalArgumentException(msgErrorIdNull);
		}
		if (idSlot.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorIdSlot);
		}
		if (vendible == null) {
			throw
					new IllegalArgumentException("El producto no puede ser nulo.");
		}
		if (!this.checkSlot(idSlot)) {
			throw
					new IllegalArgumentException(msgErrorBusqueda);
		}
		Slot c = this.findSlot(idSlot);
		c.addVendible(vendible);					//solo paso vendible como referencia porque en addVendible ya lo duplicamos
	}

	/**
	 * Reabastece el slot(casilla) de la máquina elegida con varios vendibles al mismo tiempo,
	 * recibidos en una lista. 
	 * Los vendibles de la lista deben ser todos iguales (mismo identificador único).
	 * Si ya habia un vendible en ese slot, los vendibles recibidos de la lista deberan 
	 * ser iguales (mismo identificador) que los que ya se encontraban en el slot.
	 * No se podran meter productos y packs en el mismo slot puesto que tendrán identificadores diferentes.
	 * @param idSlot Identificador del slot a reabastecer.
	 * @param vendibles Lista ArrayList de Vendible con los vendibles a añadir al slot.
	 * @throws IllegalArgumentException Cuando el identificador de slot es nulo.
	 * @throws IllegalArgumentException Cuando el identificador de slot esta vacío.
	 * @throws IllegalArgumentException Cuando el ArrayList de vendibles es nulo.
	 * @throws IllegalArgumentException Cuando el ArrayList de vendibles esta vacío.
	 * @throws IllegalArgumentException Cuando el slot recibido no esta en la máquina.
	 * @throws IllegalArgumentException Cuando el ArrayList de vendibles contiene vendibles distintos al resto.
	 * @throws IllegalArgumentException Cuando el slot no estaba vacío, y el identificador del vendible no se corresponde con el que ya estaba en el slot. 
	 */
	public void restockMultipleVendible(String idSlot, ArrayList<Vendible> vendibles) {
		if (idSlot == null) {
			throw
					new IllegalArgumentException(msgErrorIdNull);
		}
		if (idSlot.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorIdSlot);
		}
		if (vendibles == null) {
			throw
					new IllegalArgumentException("La lista de vendibles no puede ser nula.");
		}
		if (vendibles.isEmpty()) {
			throw
					new IllegalArgumentException("La lista de vendibles no puede estar vacía.");
		}
		if (!this.checkSlot(idSlot)) {
			throw
					new IllegalArgumentException(msgErrorBusqueda);
		}
		if(!this.checkRepeatedVendible(vendibles)) {
			throw
					new IllegalArgumentException("La lista debe contener vendibles iguales (con el mismo identificador único).");
		}
		Slot s = this.findSlot(idSlot);
		for(int i=0;i<vendibles.size();i++) {
			s.addVendible(vendibles.get(i));
		}
	}
	
	/**
	 * Obtiene el precio del producto en el slot(casilla) correspondiente al identificador recibido.
	 * @param idSlot Identificador del slot a consultar. 
	 * @return Precio del producto en el slot elegido.
	 * @throws IllegalArgumentException Cuando el identificador del slot es nulo.
	 * @throws IllegalArgumentException Cuando el identificador del slot esta vacío.
	 * @throws IllegalArgumentException Cuando el slot con el identificador recibido no se encuentra en la máquina.
	 * @throws IllegalArgumentException Si el slot recibido esta vacío (sin vendibles).
	 */
	public double getPriceVendible(String idSlot) {
		if (idSlot == null) {
			throw
					new IllegalArgumentException(msgErrorIdNull);
		}
		if (idSlot.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorIdSlot);
		}
		if (!this.checkSlot(idSlot)) {
			throw
					new IllegalArgumentException(msgErrorBusqueda);
		}
		Slot c = this.findSlot(idSlot);
		return c.getVendiblePrice();
	}
		
	/**
	 * Cambia el precio de un producto que se encuentra en la máquina,referenciado por el identificador único (UPC) 
	 * recibido, por el precio nuevo elegido.
	 * @param upc Identificador del producto a modificar.
	 * @param newPrice Nuevo valor del precio que deberá tener ese producto.
	 * @throws IllegalArgumentException Cuando el identificador de producto es nulo.
	 * @throws IllegalArgumentException Cuando el identificador de producto esta vacío.
	 * @throws IllegalArgumentException Cuando el nuevo precio es negativo o 0.
	 */
	public void changeProductPrice(String upc, double newPrice) {
		if (upc == null) {
			throw
					new IllegalArgumentException("El identificador UPC no puede ser nulo.");
		}
		if(upc.isEmpty()) {
			throw
					new IllegalArgumentException("El identificador UPC no puede estar vacío.");
		}
		if (newPrice <= 0) {
			throw
					new IllegalArgumentException("El nuevo precio no puede ser 0 o negativo.");
		}
		for(int i=0; i<slots.size(); i++) {
			ArrayList<Slot> slotList = slots.get(i);
			for(int j=0; j<slotList.size(); j++) {
				Slot slot = slotList.get(j);
				if(!slot.isSlotEmpty() && slot.containsProduct(upc)) {			//antes de comprobar si contiene el vendible, tenemos que comprobar que no este vacío
					slot.changePrice(upc, newPrice);
				}
			}
		}
	}
	
	/**
	 * Compra el producto del slot(casilla) recibido, descontando su precio del saldo de la TarjetaMonedero utilizada.
	 * El saldo de la TarjetaMonedero usada no podrá ser menor que el precio del producto que se solicita, y
	 * deberá disponerse de suficientes existencias del vendible en el slot elegido para poder realizar la compra.
	 * @param idSlot Identificador del slot a consultar.
	 * @param t TarjetaMonedero a usar para procesar el pago del producto.
	 * @param credencial Credenciales del cliente, pedidas para verificar el pago.
	 * @throws IllegalArgumentException Cuando el identificador del slot es nulo.
	 * @throws IllegalArgumentException Cuando el identificador del slot esta vacío.
	 * @throws IllegalArgumentException Cuando no se introduce ninguna tarjetaMonedero.
	 * @throws IllegalArgumentException Cuando las credenciales son nulas.
	 * @throws IllegalArgumentException Cuando las credenciales estan vacías.
	 * @throws IllegalArgumentException Cuando el slot buscado no esta en la máquina.
	 * @throws IllegalArgumentException Cuando no hay saldo suficiente en la tarjeta para comprar el producto.
	 * @throws IllegalArgumentException Si el slot recibido esta vacío (sin productos).
	 */
	public void buyVendible(String idSlot, TarjetaMonedero t, String credential) {
		if (idSlot == null) {
			throw
					new IllegalArgumentException(msgErrorIdNull);
		}
		if (idSlot.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorIdSlot);
		}
		if(t == null) {
			throw
					new IllegalArgumentException("No se ha introducido ninguna tarjeta.");
		}
		if(credential == null) {
			throw
					new IllegalArgumentException("La credencial no puede ser nula.");
		}
		if (credential.isEmpty()) {
			throw
					new IllegalArgumentException("La credencial no puede estar vacía.");
		}
		if(!checkSlot(idSlot)) {
			throw
					new IllegalArgumentException(msgErrorBusqueda);
		}
		if(t.getSaldoActual()<getPriceVendible(idSlot)) {
			throw
					new IllegalArgumentException("No hay saldo suficiente en la tarjeta.");
		}
		t.descontarDelSaldo(credential, getPriceVendible(idSlot));
		Slot c = findSlot(idSlot);
		c.removeVendible(); 				//Se comprueba en este metodo del Slot que el slot no este vacio
	}
	
	/**
	 * Comprueba si la máquina tiene algun slot(casilla) vacío.
	 * @return True si la máquina tiene uno o más slots de vendibles vacíos, false si esta completa.
	 */
	public boolean checkEmptySlots() {
		boolean found = false;
		for(int i=0; i<slots.size(); i++) {
			ArrayList<Slot> slotList = slots.get(i);
			for(int j = 0; j<slotList.size();j++) {
				Slot slot = slotList.get(j);
				if (slot.isSlotEmpty()) {
					found = true;
				}
			}
		}
		return found;
	}
	
	/**
	 * Comprueba si el slot correspondiente al identificador recibido esta vacío.
	 * @param idSlot El identificador del slot a comprobar.
	 * @return True si el slot esta vacío, false si tiene vendibles ya.
	 * @throws IllegalArgumentException Cuando el identificador del slot es nulo.
	 * @throws IllegalArgumentException Cuando el identificador del slot esta vacío.
	 * @throws IllegalArgumentException Cando el slot no se encuentra en la máquina.
	 */
	public boolean isSlotEmpty(String idSlot) {
		if(idSlot == null) {
			throw
					new IllegalArgumentException(msgErrorIdNull);
		}
		if(idSlot.isEmpty()) {
			throw
					new IllegalArgumentException(msgErrorIdSlot);
		}
		if (!this.checkSlot(idSlot)) {
			throw
					new IllegalArgumentException(msgErrorBusqueda);
		}
		Slot slot = findSlot(idSlot);
		return slot.isSlotEmpty();								//aqui slot no puede ser null, porque aunque lo inicialicemos en buscacasilla a null, nunca puede devorver null porque antes comprobamos que este en la maquina
	}
	
	/**
	 * Método privado que obtiene el slot correspondiente al identificador recibido.
	 * El Slot que devuelve NO PODRA SER NULL NUNCA, debido a que antes de llamar a este método, 
	 * siempre se comprueba antes que el slot a buscar está en la máquina, y al ser un método privado, 
	 * nadie más podra usarlo.
	 * @param idSlot Identificador del slot buscado.
	 * @return El slot buscado con el identificador que se ha recibido.
	 */
	private Slot findSlot(String idSlot) {
		Slot found = null;
		for(int i=0; i<slots.size(); i++) {
			ArrayList<Slot> slotList = slots.get(i);
			for(int j=0; j<slotList.size(); j++) {
				Slot slot = slotList.get(j);
				if (idSlot.equals(slot.getIdSlot())) {
					found = slot;
				}
			}
		}
		return found;
	}
	
	/**
	 * Método privado que comprueba si el slot con el identificador que recibe
	 * existe en la máquina de vending o no.
	 * @param idSlot El identificador del slot que se va a buscar.
	 * @return True si el slot se encuentra en la máquina, false si no esta.
	 */
	private boolean checkSlot(String idSlot) {
		boolean found = false;
		for(int i=0; i<slots.size(); i++) {
			ArrayList<Slot> slotList = slots.get(i);
			for(int j=0; j<slotList.size(); j++) {
				Slot slot = slotList.get(j);
				if (idSlot.equals(slot.getIdSlot())) {
					found = true;
				}
			}
		}
		return found;
	}
	
	/**
	 * Método privado que comprueba que todos los vendibles del ArrayList de vendibles recibido
	 * sean iguales (tengan el mismo identificador único).
	 * @param v ArrayList de vendibles.
	 * @return true si todos los vendibles de la lista son iguales, false si alguno es diferente.
	 */
	private boolean checkRepeatedVendible(ArrayList<Vendible> v) {
		boolean repeated = true;
		String id = v.get(0).getIdentifier();
		for(int i=0;i<v.size();i++) {
			if(!id.equals(v.get(i).getIdentifier())) {
				repeated = false;
			}
		}
		return repeated;
	}

}
