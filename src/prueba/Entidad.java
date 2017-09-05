package prueba;

import java.time.LocalDate;

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
 *    Esta clase nos encapsula la tabla "Entidad" de la base de datos
 *    
 *   */
@Tabla(nombre = "ENTIDAD")
public class Entidad 
{
	@ID
	@Columna(nombre = "ID", notNull = true, tipoDato = TipoElementoDB.INTEGER)
	private int identidad;
	
	@Columna(nombre = "ATRIBUTO_VARCHAR", tipoDato = TipoElementoDB.VARCHAR2)
	private String atributovarchar;
	
	@Columna(nombre = "ATRIBUTO_ENTERO", tipoDato = TipoElementoDB.INTEGER)
	private int atributoentero;
	
	@Columna(nombre = "ATRIBUTO_DATE", tipoDato = TipoElementoDB.DATE, formato = "yyyy-mm-dd")
	private LocalDate atributodate;
	
	@MuchosAUno
	@Columna(nombre = "ENTIDAD_2_FOREIGN", tipoDato =  TipoElementoDB.INTEGER)
	private Entidad2 entidad2foreign;
	
	
	/**
	 * Getter
	 * @return id de la entidad
	 */
	public int getIdentidad() 
	{
		return identidad;
	}

	/**
	 * Setter
	 * @param identidad: asignar identidad a la clase
	 */
	public void setIdentidad(int identidad) 
	{
		this.identidad = identidad;
	}

	/**
	 * Getter
	 * @return atributovarchar de la entidad
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
	 * @return: atributoentero de la entidad
	 */
	public int getAtributoentero() 
	{
		return atributoentero;
	}

	/**
	 * Setter
	 * @param atributoentero: atributoentero a asignar
	 */
	public void setAtributoentero(int atributoentero) 
	{
		this.atributoentero = atributoentero;
	}

	/**
	 * Getter
	 * @return: atributo date de la entidad
	 */
	public LocalDate getAtributodate() 
	{
		return atributodate;
	}

	/**
	 * Setter
	 * @param atributodate: atributodate a asignar
	 */
	public void setAtributodate(LocalDate atributodate) 
	{
		this.atributodate = atributodate;
	}

	/**
	 * Getter
	 * @return entidad2 foranea
	 */
	public Entidad2 getEntidad2foreign() 
	{
		return entidad2foreign;
	}

	/**
	 * Setter
	 * @param entidad2foreign: entidad2foreign a asignar
	 */
	public void setEntidad2foreign(Entidad2 entidad2foreign) 
	{
		this.entidad2foreign = entidad2foreign;
	}
	
}//No borrar
