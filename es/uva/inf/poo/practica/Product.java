package es.uva.inf.poo.practica;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase que hereda de Vendible, e implementa la funcionalidad de un producto perteneciente a un slot
 * de una máquina de vending.
 * @author rauvill, alvdela
 *
 */
public class Product extends Vendible{

	private double price;						//ahora el upc es el identifier que hereda de vendible
	private Calendar expiryDate;
	
	/**
	 * Inicializa un producto con un precio que puede tener decimales, una fecha de caducidad,
	 * un nombre de producto, y un código de producto universal(UPC) estandarizado, que debe
	 * ser correcto.
	 * El precio no podrá ser negativo o 0, y la fecha deberá ser posterior a la actual.
	 * @param price Precio del producto.
	 * @param expiryDate Fecha de caducidad.
	 * @param name Nombre del producto.
	 * @param upc Código de producto universal, que debe ser correcto.
	 * @throws IllegalArgumentException Cuando el nombre del producto es nulo.
	 * @throws IllegalArgumentException Cuando el nombre del producto esta vacío.
	 * @throws IllegalArgumentException Cuando el identificador UPC del producto es nulo.
	 * @throws IllegalArgumentException Cuando el identficador UPC del producto esta vacío.
	 * @throws IllegalArgumentException Cuando la longitud de la cadena del upc no es 12.
	 * @throws IllegalArgumentException Cuando el código de producto universal recibido es incorrecto.
	 * @throws IllegalArgumentException Cuando el código de producto universal no esta compuesto únicamente por números.
	 * @throws IllegalArgumentException Cuando el precio es negativo o cero.
	 * @throws IllegalArgumentException Cuando la fecha recibida es nula.
	 * @throws IllegalArgumentException Cuando la fecha recibida es anterior a la actual.
	 */
	public Product(double price, Calendar expiryDate, String name, String upc) {
		super(name, upc);
		if(expiryDate == null) {
			throw
					new IllegalArgumentException("La fecha de caducidad no puede ser nula.");
		}
		if(expiryDate.before(new GregorianCalendar())) {					//comprobamos que la fecha de caducidad no sea anterior a la actual
			throw
					new IllegalArgumentException("La fecha de caducidad no puede ser anterior a la actual.");
		}
		if(upc.length() != 12) {
			throw
					new IllegalArgumentException("El UPC debe ser de 12 dígitos.");
		}
		try {
			Long.parseLong(upc);
		} catch(Exception e) {
			throw
					new IllegalArgumentException("El UPC debe estar compuesto por 12 dígitos numericos.");
		}
		if(!checkDigit(upc)) {
			throw
					new IllegalArgumentException("El UPC no es válido.");
		}
		this.setPrice(price);							//en el setPrice ya controlo la excepcion de que sea 0 o negativo
		this.expiryDate = new GregorianCalendar();
		this.expiryDate.set(expiryDate.get(Calendar.YEAR), expiryDate.get(Calendar.MONTH), expiryDate.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Consulta el precio del Producto.
	 * @return El precio del producto solicitado.
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Modifica el precio del producto.
	 * @param price nuevo precio del producto.
	 * @throws IllegalArgumentException Si el precio recibido es negativo o 0.
	 */
	public void setPrice(double price) {
		if (price <= 0) {
			throw
					new IllegalArgumentException("El nuevo precio no puede ser 0 o negativo.");
		}
		this.price = price;
	}
	
	/**
	 * Consulta la fecha de caducidad del producto.
	 * @return Un objeto Calendar con la fecha de caducidad.
	 */
	public Calendar getExpiryDate() {
		Calendar c = new GregorianCalendar();
		c.set(expiryDate.get(Calendar.YEAR), expiryDate.get(Calendar.MONTH), expiryDate.get(Calendar.DAY_OF_MONTH));
		return c;
	}
	
	public Product duplicate() {				//no hace falta ponerle javadoc porque el javadoc que se lee es el del padre
		return new Product(price, expiryDate, super.getName(), super.getIdentifier());
	}
	
	/**
	 * Método privado que comprueba que el UPC recibido concuerda con los estándares del UPC.
	 * @param upc UPC del producto a inicializar.
	 * @return True si el UPC es correcto, false si es incorrecto.
	 */
	private boolean checkDigit(String upc) {
		boolean valid = false;
		int digControl = 0;
		int factor;
		int res;
		int mult;
		for(int i=0; i<upc.length()-1; i++) {
			
			if(i%2==0) {	//si la posicion es "par", le multiplicamos 3, si es impar multiplicamos 1
				factor = 3;
			}else {
				factor = 1;
			}
			
			digControl = digControl + Integer.parseInt(Character.toString(upc.charAt(i)))*factor;	//esto se puede hacer mejor con *= no?
		}
		
		res = digControl%10;
		
		if(res>5) {		//si el resto de la division del numero entre 10 es mayor que 5, esta mas cerca del sig. mulitplo, asi que sumo lo que le queda que es 10-res
			mult = digControl + (10 - res);
		} else {		//si no es mayor que 5, es que esta mas cerca del anterior, asi que le resto res.
			mult = digControl - res;
		}
		
		int d = Math.abs(digControl - mult);	//valor absoluto por si mult es mayor que el digito de control
		
		if(Integer.parseInt(Character.toString(upc.charAt(11))) == d) {
			valid = true;
		}
		
		return valid;
	}	
}
