package es.uva.inf.poo.practica;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que implementa la funcionalidad de un sistema 
 * que se encarga de gestionar las ciudades y máquinas que tiene asignadas.
 * @author rauvill, alvdela
 *
 */
public class VendingSystem {
	private ArrayList<VendingCity> cities;
	private static final String msgError1 = "El id de sede no puede ser nulo";
	private static final String msgError2 = "La sede con ese identificador no está en el sistema";
	private static final String msgError3 = "El id de sede no puede estar vacío";
	

	/**
	 * Inicializa un sistema nuevo sin ninguna provincia(ciudad).
	 */
	public VendingSystem() {
		cities = new ArrayList<>();
	}

	/**
	 * Inicializa un sistema nuevo con una lista inicial de sedes
	 * @param cities Lista inicial de sedes.
	 * @throws IllegalArgumentException La lista de provincias es nula.
	 * @throws IllegalArgumentException La lista esta vacia.
	 * @throws IllegalArgumentException Una provincia(ciudad) es nula.
	 * @throws IllegalArgumentException Si 2 o más sedes tienen un id repetido.
	 */
	public VendingSystem(ArrayList<VendingCity> cities) {
		if(cities == null) {
			throw 
				new IllegalArgumentException("La lista de provincias no puede ser nula.");
		}
		if(cities.isEmpty()) {
			throw
				new IllegalArgumentException("La lista no puede estar vacía.");
		}
		this.cities = new ArrayList<>();
		for (VendingCity city: cities) {
			addVendingCity(city);
		}
	}

	/**
	 * Añade una nueva provincia(ciudad) al sistema. Las provincias no pueden tener el mismo
	 * identificador que alguna de las que ya se encuentran en el sistema.
	 * @param newCity Provincia que se quiere añadir al sistema.
	 * @throws IllegalArgumentException La provincia es null.
	 * @throws IllegalArgumentException Una provincia tiene el mismo id que una provincia del sistema.
	 */
	public void addVendingCity(VendingCity newCity) {
		if (newCity == null) {
			throw new IllegalArgumentException("La provincia no puede estar vacía");
		}
		if (findCity(newCity.getIdVC())) {
			throw new IllegalArgumentException("La provincia ya está registrada en el sistema");
		}
		cities.add(newCity);
	}

	/**
	 * Busca si el id de una provincia(ciudad) se encuentra o no en el sistema.
	 * @param idCity de la provincia
	 * @return true si está en el sistema/false si no
	 */
	private boolean findCity(String idCity) {
		boolean find = false;
		for (VendingCity city: cities) {
			if (city.getIdVC().equals(idCity)) {
				find = true;
			}
		}
		return find;
	}

	/**
	 * Elimina una provincia(ciudad) del sistema.
	 * @param idCity Identificador de la provincia.
	 * @throws IllegalArgumentException El identificador de la provincia es null.
	 * @throws IllegalArgumentException El identificador de la provincia esta vacío.
	 * @throws IllegalArgumentException La provincia no se encuentra en el sistema.
	 */
	public void removeVendingCity(String idCity) {
		if (idCity == null) {
			throw new IllegalArgumentException(msgError1);
		}
		if (idCity.isEmpty()) {
			throw new IllegalArgumentException(msgError3);
		}
		if (!findCity(idCity)) {
			throw new IllegalArgumentException(msgError2);
		}
		for (VendingCity city : cities) {
			if (city.getIdVC().equals(idCity)) {
				cities.remove(city);
			}
		}
	}

	/**
	 * A partir de un identificador de provincia(ciudad) devuelve el número de las maquinas
	 * vending en esa provincia
	 * @param idCity
	 * @return número de máquinas en la provincia.
	 * @throws IllegalArgumentException El identificador de la provincia es null.
	 * @throws IllegalArgumentException El identificador de la provincia está vacío.
	 * @throws IllegalArgumentException La provincia no se encuentra en el sistema.
	 */
	public int getNumMachineXCity(String idCity) {
		int numMachines = 0;
		if (idCity == null) {
			throw new IllegalArgumentException(msgError1);
		}
		if (idCity.isEmpty()) {
			throw new IllegalArgumentException(msgError3);
		}
		if (!findCity(idCity)) {
			throw new IllegalArgumentException(msgError2);
		}
		for (VendingCity city : cities) {
			if (city.getIdVC().equals(idCity)) {
				numMachines = city.getNumMachines();
			}
		}
		return numMachines;
	}
	
	/**
	 * A partir de un identificador de provincia(ciudad) devuelve una lista de las máquinas
	 * vending en esa provincia.
	 * @param idCity
	 * @return list lista de máquinas vending en la provincia(ciudad)
	 * @throws IllegalArgumentException El identificador de la provincia es nulo.
	 * @throws IllegalArgumentException El identificador de la provincia está vacío.
	 * @throws IllegalArgumentException La provincia no se encuentra en el sistema.
	 */
	public ArrayList<VendingMachine> getMachinesXCities(String idCity) {
		ArrayList<VendingMachine> list = null;
		if (idCity == null) {
			throw new IllegalArgumentException(msgError1);
		}
		if (idCity.isEmpty()) {
			throw new IllegalArgumentException(msgError3);
		}
		if (!findCity(idCity)) {
			throw new IllegalArgumentException(msgError2);
		}
		for (VendingCity city : cities) {
			if (city.getIdVC().equals(idCity)) {
				list = city.getMachines();
			}
		}

		return list;
	}
	
	/**
	 * Devuelve el número de provincias que se gestionan
	 * @return nº de provincias(ciudades)
	 */
	public int getNumCities() {
		return cities.size();
	}
	
	/**
	 * Devuelve los nombres de todas las provincias donde hay una cuidad(sede).
	 * @return String con todos los nombres de provincias separados por ", "
	 */
	public ArrayList<String> getNamesCities() {
		if(cities.isEmpty()) {
			throw new IllegalArgumentException("El sistema no gestiona ninguna ciudad");
		}
		ArrayList<String> list = new ArrayList<>();
		for (VendingCity city: cities) {
			list.add(city.getProvince());
		}
		return list;
	}
	
	/**
	 * Devuelve una lista con la cantidad de máquinas vending que se 
	 * gestionan en cada provincia(ciudad) junto al nombre de dicha provincia.
	 * @return Lista con el nombre de la provincia junto a sus máquinas.
	 */
	public ArrayList<SimpleEntry<String, Integer>> getListCityMachines() {
		ArrayList<SimpleEntry<String, Integer>> list = new ArrayList<>();
		for (VendingCity city : cities) {
			SimpleEntry<String, Integer> cityMachines = new SimpleEntry<>(city.getProvince(),city.getNumMachines());
			list.add(cityMachines);
		}
		return list;
	}
	
	/**
	 * Devuelve una lista de todas las provincia(ciudades) que se encuentran
	 * el sistema.
	 * @return lista de las provincias que se gestionan. 
	 */
	public ArrayList<VendingCity> getCities(){
		ArrayList<VendingCity> list = new ArrayList<>();
		list.addAll(cities);
		return list;
	}
	
}
