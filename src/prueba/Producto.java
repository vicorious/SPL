package prueba;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.MuchosAUno;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.enums.TipoElementoDB;

/**
 *  <p>entidades</p>	
 * 	 @author Alejandro Lindarte Castro <strong>Copyright 2017</strong>
 * 	 <br>
 * 	 <h3>Licencia: </h3>
 * 	 <p>
 * 	 This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see {@literal <http://www.gnu.org/licenses/>}
 *   </p>
 *   
 *   <h3>Descripcion :</h3>  
 *   
 *    Esta clase nos encapsula la tabla "Producto" de la base de datos
 *    
 *   */
@Tabla(nombre = "Producto")
public class Producto 
{
	@ID
	@Columna(nombre = "ID", tipoDato = TipoElementoDB.INTEGER, notNull = false)
	private int id;
	
	@Columna(nombre = "CODIGO", tipoDato = TipoElementoDB.TEXT)
	private String codigo;
	
	@Columna(nombre = "NOMBRE", tipoDato = TipoElementoDB.TEXT)
	private String nombre;
	
	@Columna(nombre = "DESCRIPCION", tipoDato = TipoElementoDB.TEXT)
	private String descripcion;
	
	@Columna(nombre = "PRECIO", tipoDato = TipoElementoDB.INTEGER)
	private int precio;
	
	@MuchosAUno
	@Columna(nombre = "ESTABLECIMIENTO_ID", tipoDato = TipoElementoDB.INTEGER)
	private Establecimiento establecimiento;

	/**
	 * Getter
	 * @return: id de la entidad
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * Setter
	 * @param id: id a asignar
	 */
	public void setId(int id) 
	{
		this.id = id;
	}

	/**
	 * Getter
	 * @return: codigo de la entidad
	 */
	public String getCodigo() 
	{
		return codigo;
	}

	/**
	 * Setter
	 * @param codigo: codigo a asignar
	 */
	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	/**
	 * Getter
	 * @return: nombre de la entidad
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * Setter
	 * @param nombre: nombre a asignar
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * Getter
	 * @return: descripcion de la entidad
	 */
	public String getDescripcion() 
	{
		return descripcion;
	}

	/**
	 * Setter
	 * @param descripcion: descripcion a asignar
	 */
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}

	/**
	 * Getter
	 * @return: retorna el precio de la entidad
	 */
	public int getPrecio() 
	{
		return precio;
	}

	/**
	 * Setter
	 * @param precio: precio a asignar
	 */
	public void setPrecio(int precio) 
	{
		this.precio = precio;
	}
	
	
	
}//NoBorrar
