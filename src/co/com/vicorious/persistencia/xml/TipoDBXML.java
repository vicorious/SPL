package co.com.vicorious.persistencia.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  <p>XML</p>	
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
 *   TO Encargado de encapsular cada tipo de base de dato del archivo Tiposdb.xml
 * 
 *
 */
@XmlRootElement(name = "item")
public class TipoDBXML 
{
	private String nombre;
	
	private String version;
	
	private String driver;
	
	private String jdbc;

	/**
	 * Getter
	 * @return: nombre del TO
	 */
	public String getNombre() 
	{
		return nombre;
	}
	
	/**
	 * Setter
	 * @param nombre: nombre a asignar
	 */
	@XmlElement
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	/**
	 * Getter
	 * @return: version del TO
	 */
	public String getVersion() 
	{
		return version;
	}

	/**
	 * Setter
	 * @param version: version a asignar
	 */
	@XmlElement
	public void setVersion(String version) 
	{
		this.version = version;
	}

	/**
	 * Getter
	 * @return: driver del TO
	 */
	public String getDriver() 
	{
		return driver;
	}
	
	/**
	 * Setter
	 * @param driver: driver a asignar
	 */
	@XmlElement
	public void setDriver(String driver) 
	{
		this.driver = driver;
	}

	/**
	 * Getter
	 * @return: jdbc del TO
	 */
	public String getJdbc() 
	{
		return jdbc;
	}
	
	/**
	 * Setter
	 * @param jdbc: jdbc a asignar
	 */
	@XmlElement
	public void setJdbc(String jdbc) 
	{
		this.jdbc = jdbc;
	}		
	
}//NoBorrar
