package prueba;

import java.time.LocalDateTime;
import java.util.List;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.anotaciones.UnoAMuchos;
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
 *    Esta clase nos encapsula la tabla "Entidad_2" de la base de datos
 *    
 *   */
@Tabla(nombre = "ENTIDAD_2")
public class Entidad2 
{
	@ID
	@Columna(nombre = "ID_ENTIDAD_2", notNull = true, tipoDato = TipoElementoDB.INTEGER)
	private int identidad;
	
	@Columna(nombre = "ATRIBUTO_VARCHAR", tipoDato = TipoElementoDB.VARCHAR)
	private String atributovarchar;
	
	@Columna(nombre = "ATRIBUTO_DECIMAL", tipoDato = TipoElementoDB.DECIMAL)
	private double atributodecimal;
	
	@Columna(nombre = "ATRIBUTO_TIMESTAMP", tipoDato = TipoElementoDB.TIMESTAMP, formato = "yyyy-MM-dd HH:mm:ss")	
	private LocalDateTime atributo_timestamp;
	
	@UnoAMuchos
	@Columna
	private List<Entidad> entidades;

	/**
	 * Getter
	 * @return: id de la entidad
	 */
	public int getIdentidad() 
	{
		return identidad;
	}

	/**
	 * Setter
	 * @param identidad: identidad a asignar
	 */
	public void setIdentidad(int identidad) 
	{
		this.identidad = identidad;
	}

	/**
	 * Getter
	 * @return: atributovarchar de la entidad
	 */
	public String getAtributovarchar() 
	{
		return atributovarchar;
	}

	/**
	 * Setter
	 * @param atributovarchar: atributovarchar a asignar
	 */
	public void setAtributovarchar(String atributovarchar) 
	{
		this.atributovarchar = atributovarchar;
	}

	/**
	 * Getter
	 * @return: atributodecimal de la entidad
	 */
	public double getAtributodecimal() 
	{
		return atributodecimal;
	}

	/**
	 * Setter
	 * @param atributodecimal: atributodecimal a asignar
	 */
	public void setAtributodecimal(double atributodecimal) 
	{
		this.atributodecimal = atributodecimal;
	}

	/**
	 * Getter
	 * @return lista de entidades uno a muchos
	 */
	public List<Entidad> getEntidades() 
	{
		return entidades;
	}

	/**
	 * Setter
	 * @param entidades: entidadesa a asignar 
	 */
	public void setEntidades(List<Entidad> entidades) 
	{
		this.entidades = entidades;
	}

	/**
	 * Getter
	 * @return atributo timestamp de la entidad
	 */
	public LocalDateTime getAtributo_timestamp() 
	{
		return atributo_timestamp;
	}

	/**
	 * Setter
	 * @param atributo_timestamp: atributo_timestamp a asignar
	 */
	public void setAtributo_timestamp(LocalDateTime atributo_timestamp) 
	{
		this.atributo_timestamp = atributo_timestamp;
	}
	
	
			
}//No borrar
