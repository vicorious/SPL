package prueba;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
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
 *    Esta clase nos encapsula la tabla "Usuario" de la base de datos
 *    
 *   */
@Tabla(nombre = "Usuario")
public class Usuario 
{
	@ID(secuencia = "usuario_secuencia")
	@Columna(nombre = "ID", notNull = true, tipoDato = TipoElementoDB.INTEGER)
	private int id;
	
	@Columna(nombre = "NOMBRE", tipoDato = TipoElementoDB.TEXT)
	private String nombre;
	
	@Columna(nombre = "APELLIDOS", tipoDato = TipoElementoDB.TEXT)
	private String apellidos;
	
	@Columna(nombre = "CORREO", tipoDato = TipoElementoDB.TEXT)
	private String correo;
	
	@Columna(nombre = "CONTRASENA", tipoDato = TipoElementoDB.TEXT)
	private String contrasena;

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
	 * @return: nombre de la entidad
	 */
	public String getNombre() 
	{
		return nombre;
	}

	/**
	 * Stter
	 * @param nombre: nombre a asignar
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * Getter
	 * @return: apellidos de la entidad
	 */
	public String getApellidos() 
	{
		return apellidos;
	}

	/**
	 * Setter
	 * @param apellidos: apellidos a asignar
	 */
	public void setApellidos(String apellidos) 
	{
		this.apellidos = apellidos;
	}

	/**
	 * Getter
	 * @return: correo de la entidad
	 */
	public String getCorreo() 
	{
		return correo;
	}

	/**
	 * Setter
	 * @param correo: correo a asignar
	 */
	public void setCorreo(String correo) 
	{
		this.correo = correo;
	}

	/**
	 * Getter
	 * @return: contrasena de la entidad
	 */
	public String getContrasena() 
	{
		return contrasena;
	}

	/**
	 * Setter
	 * @param contrasena: contrasena a asignar
	 */
	public void setContrasena(String contrasena) 
	{
		this.contrasena = contrasena;
	}	
	
}//NoBorrar
