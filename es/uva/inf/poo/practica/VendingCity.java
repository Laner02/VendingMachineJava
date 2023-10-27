package es.uva.inf.poo.practica;

import java.util.ArrayList;

/**
 * Clase que implementa las características y funcionalidad de un sistema para
 * gestionar a todas las máquinas vending de la empresa en una provincia. Todas
 * las máquinas deben de ser distintas.
 * 
 * @author rauvill, alvdela
 *
 */
public class VendingCity {

	private ArrayList<VendingMachine> machines;
	private String idProvince;
	private String province;
	private static final String msgError1 = "El id de una máquina no puede ser nulo";
	private static final String msgError2 = "El id de una máquina no puede estar vacío";

	/**
	 * Inicializa una provincia nueva sin ninguna máquina.
	 * @throws IllegalArgumentException Si el nombre de la provincia es nulo.
	 * @throws IllegalArgumentException Si el nombre de la provincia esta vacío.
	 * @throws IllegalArgumentException Si el id de la province es nulo.
	 * @throws IllegalArgumentException Si el id de la provincia esta vacío.
	 */
	public VendingCity(String idProvince, String province) {
		setIdVC(idProvince);
		setProvince(province);
		machines = new ArrayList<>();
	}

	/**
	 * Inicializa una provincia nueva con una lista de máquinas inicial.
	 * @param machines   lista de máquinas
	 * @param province   nombre de la provincia
	 * @param idProvince identificador de la provincia
	 * @throws IllegalArgumentException Si la lista de máquinas es nula.
	 * @throws IllegalArgumentException Si la lista esta vacia.
	 * @throws IllegalArgumentException Si 2 o más maquinas tienen un id repetido.
	 * @throws IllegalArgumentException Si una máquina es null.
	 * @throws IllegalArgumentException Si el nombre de la provincia es nulo.
	 * @throws IllegalArgumentException Si el nombre de la provincia esta vacío.
	 * @throws IllegalArgumentException Si el id de la province es nulo.
	 * @throws IllegalArgumentException Si el id de la provincia esta vacío.
	 */
	public VendingCity(ArrayList<VendingMachine> machines, String idProvince, String province) {
		if (machines == null) {
			throw new IllegalArgumentException("La lista de máquinas no puede ser nula.");
		}
		if (machines.isEmpty()) {
			throw new IllegalArgumentException("La lista de máquinas no puede estar vacía.");
		}
		setIdVC(idProvince);
		setProvince(province);
		this.machines = new ArrayList<>();
		for (VendingMachine machine : machines) {
			addVendingMachine(machine);
		}
	}

	/**
	 * Añade una nueva máquina a la provincia. Las máquinas a añadir no pueden tener el
	 * mismo identificador que alguna de las que ya se encuentra en la provincia.
	 * @param newMachine Máquina de vending a añadir al sistema.
	 * @throws IllegalArgumentException Si la máquina es null.
	 * @throws IllegalArgumentException Si una máquina tiene el mismo id que una
	 *                                  máquina del sistema.
	 */
	public void addVendingMachine(VendingMachine newMachine) {
		if (newMachine == null) {
			throw new IllegalArgumentException("La maquina no puede ser nula");
		}
		if (findMachine(newMachine.getIdMachine())) {
			throw new IllegalArgumentException("La maquina tiene un id ya registrado en el sistema");
		}
		machines.add(newMachine);
	}

	/**
	 * Busca si el id de una VendingMachine se encuentra o no en el sistema.
	 * @param idMachine de la máquina
	 * @return true si se encuentra / false si no se encuentra
	 */
	private boolean findMachine(String idMachine) {
		boolean find = false;
		for (VendingMachine machine : machines) {
			if (machine.getIdMachine().equals(idMachine)) {
				find = true;
			}
		}
		return find;
	}

	/**
	 * Elimina una máquina de la provincia.
	 * @param idMachine Identificador de la máquina.
	 * @throws IllegalArgumentException Si el identificador está vacío.
	 * @throws IllegalArgumentException Si la maquina con ese id no esta en el
	 *                                  sistema
	 */
	public void removeVendingMachine(String idMachine) {
		if (idMachine == null) {
			throw new IllegalArgumentException(msgError1);
		}
		if (idMachine.isEmpty()) {
			throw new IllegalArgumentException(msgError2);
		}
		if (!findMachine(idMachine)) {
			throw new IllegalArgumentException("La máquina con ese identificador no está en el sistema");
		}
		for (int i = 0; i < machines.size(); i++) {
			if (machines.get(i).getIdMachine().equals(idMachine)) {
				machines.remove(i);
			}
		}
	}

	/**
	 * Devuelve una lista de todas las máquinas vending que gestiona.
	 * @return La lista de máquinas que gestiona
	 */
	public ArrayList<VendingMachine> getMachines() {
		ArrayList<VendingMachine> list = new ArrayList<>();
		list.addAll(machines);
		return list;
	}

	/**
	 * Devuelve el número de máquinas vending operativas.
	 * @return operatives Número de máquinas operativas en la provincia.
	 */
	public int getVendingOperative() {
		int operatives = 0;
		for (VendingMachine machine: machines) {
			if (machine.isOperative()) {
				operatives++;
			}
		}
		return operatives;
	}

	/**
	 * Devuelve una lista de las máquinas que tienen algun hueco vacío
	 * @return list de máquinas con algun hueco vacío
	 */
	public ArrayList<VendingMachine> getAvailableMachines() {
		ArrayList<VendingMachine> list = new ArrayList<>();
		for (VendingMachine machine: machines) {
			if (machine.checkEmptySlots()) {
				list.add(machine);
			}
		}
		return list;
	}

	/**
	 * Devuelve el numero de máquinas de la provincia.
	 * @return número de máquinas de la provincia.
	 */
	public int getNumMachines() {
		return machines.size();
	}

	/**
	 * Devuelve el identificador de la sede de esa provincia.
	 * @return idProvince Identificador de la provincia
	 */
	public String getIdVC() {
		return idProvince;
	}

	/**
	 * Establece un identificador para la provincia.
	 * @param idProvince
	 * @throws IllegalArgumentException Si el id de la province es nulo.
	 * @throws IllegalArgumentException Si el id de la province esta vacío.
	 */
	private void setIdVC(String idProvince) {
		if (idProvince == null) {
			throw new IllegalArgumentException(msgError1);
		}
		if (idProvince.isEmpty()) {
			throw new IllegalArgumentException(msgError2);
		}
		this.idProvince = idProvince;
	}

	/**
	 * Devuelve el nombre de la provincia donde se encuantra la sede.
	 * @return province nombre de la provincia
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Establece el nombre de la provincia donde se encuentra la sede.
	 * 
	 * @param idProvince
	 * @throws IllegalArgumentException Si el nombre de la provincia es nulo.
	 * @throws IllegalArgumentException Si el nombre de la provincia esta vacío.
	 */
	private void setProvince(String province) {
		if (province == null) {
			throw new IllegalArgumentException("El nombre de la provincia no puede ser nulo");
		}
		if (province.isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío");
		}
		this.province = province;
	}
}
