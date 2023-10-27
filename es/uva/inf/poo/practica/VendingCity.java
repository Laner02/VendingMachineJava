package es.uva.inf.poo.practica;

import java.util.ArrayList;

/**
 * Clase que implementa las caracter�sticas y funcionalidad de un sistema para
 * gestionar a todas las m�quinas vending de la empresa en una provincia. Todas
 * las m�quinas deben de ser distintas.
 * 
 * @author rauvill, alvdela
 *
 */
public class VendingCity {

	private ArrayList<VendingMachine> machines;
	private String idProvince;
	private String province;
	private static final String msgError1 = "El id de una m�quina no puede ser nulo";
	private static final String msgError2 = "El id de una m�quina no puede estar vac�o";

	/**
	 * Inicializa una provincia nueva sin ninguna m�quina.
	 * @throws IllegalArgumentException Si el nombre de la provincia es nulo.
	 * @throws IllegalArgumentException Si el nombre de la provincia esta vac�o.
	 * @throws IllegalArgumentException Si el id de la province es nulo.
	 * @throws IllegalArgumentException Si el id de la provincia esta vac�o.
	 */
	public VendingCity(String idProvince, String province) {
		setIdVC(idProvince);
		setProvince(province);
		machines = new ArrayList<>();
	}

	/**
	 * Inicializa una provincia nueva con una lista de m�quinas inicial.
	 * @param machines   lista de m�quinas
	 * @param province   nombre de la provincia
	 * @param idProvince identificador de la provincia
	 * @throws IllegalArgumentException Si la lista de m�quinas es nula.
	 * @throws IllegalArgumentException Si la lista esta vacia.
	 * @throws IllegalArgumentException Si 2 o m�s maquinas tienen un id repetido.
	 * @throws IllegalArgumentException Si una m�quina es null.
	 * @throws IllegalArgumentException Si el nombre de la provincia es nulo.
	 * @throws IllegalArgumentException Si el nombre de la provincia esta vac�o.
	 * @throws IllegalArgumentException Si el id de la province es nulo.
	 * @throws IllegalArgumentException Si el id de la provincia esta vac�o.
	 */
	public VendingCity(ArrayList<VendingMachine> machines, String idProvince, String province) {
		if (machines == null) {
			throw new IllegalArgumentException("La lista de m�quinas no puede ser nula.");
		}
		if (machines.isEmpty()) {
			throw new IllegalArgumentException("La lista de m�quinas no puede estar vac�a.");
		}
		setIdVC(idProvince);
		setProvince(province);
		this.machines = new ArrayList<>();
		for (VendingMachine machine : machines) {
			addVendingMachine(machine);
		}
	}

	/**
	 * A�ade una nueva m�quina a la provincia. Las m�quinas a a�adir no pueden tener el
	 * mismo identificador que alguna de las que ya se encuentra en la provincia.
	 * @param newMachine M�quina de vending a a�adir al sistema.
	 * @throws IllegalArgumentException Si la m�quina es null.
	 * @throws IllegalArgumentException Si una m�quina tiene el mismo id que una
	 *                                  m�quina del sistema.
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
	 * @param idMachine de la m�quina
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
	 * Elimina una m�quina de la provincia.
	 * @param idMachine Identificador de la m�quina.
	 * @throws IllegalArgumentException Si el identificador est� vac�o.
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
			throw new IllegalArgumentException("La m�quina con ese identificador no est� en el sistema");
		}
		for (int i = 0; i < machines.size(); i++) {
			if (machines.get(i).getIdMachine().equals(idMachine)) {
				machines.remove(i);
			}
		}
	}

	/**
	 * Devuelve una lista de todas las m�quinas vending que gestiona.
	 * @return La lista de m�quinas que gestiona
	 */
	public ArrayList<VendingMachine> getMachines() {
		ArrayList<VendingMachine> list = new ArrayList<>();
		list.addAll(machines);
		return list;
	}

	/**
	 * Devuelve el n�mero de m�quinas vending operativas.
	 * @return operatives N�mero de m�quinas operativas en la provincia.
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
	 * Devuelve una lista de las m�quinas que tienen algun hueco vac�o
	 * @return list de m�quinas con algun hueco vac�o
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
	 * Devuelve el numero de m�quinas de la provincia.
	 * @return n�mero de m�quinas de la provincia.
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
	 * @throws IllegalArgumentException Si el id de la province esta vac�o.
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
	 * @throws IllegalArgumentException Si el nombre de la provincia esta vac�o.
	 */
	private void setProvince(String province) {
		if (province == null) {
			throw new IllegalArgumentException("El nombre de la provincia no puede ser nulo");
		}
		if (province.isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede estar vac�o");
		}
		this.province = province;
	}
}
