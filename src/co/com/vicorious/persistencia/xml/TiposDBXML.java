package co.com.vicorious.persistencia.xml;

import java.util.List;

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
 *   TO encargado de encapsular la informacion del archivo Tiposdb.xml 
 * 
 *
 */
@XmlRootElement(name = "basededatos")
public class TiposDBXML 
{
	private List<TipoDBXML> item;

	/**
	 * Getter
	 * @return: tiposdb del framework
	 */
	public List<TipoDBXML> getItem() 
	{
		return item;
	}

	/**
	 * Setter
	 * @param item: asignar lista de tipos de db 
	 */
	@XmlElement(nillable = true)
	public void setItem(List<TipoDBXML> item) 
	{
		this.item = item;
	}			
}//NoBorrar
