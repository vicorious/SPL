package co.com.vicorious.persistencia.xml;

import javax.xml.bind.annotation.XmlAttribute;
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
 *   TO que encapsula cada elemento default del archivo DEFAULTS.XML
 * .
 *
 */
@XmlRootElement(name = "default")
public class DefaultXML 
{
	private String name;
	
	private String tipodato;
	
	private String valor;

	/**
	 * Getter
	 * @return: nombre del TO
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Setter
	 * @param name: nombre a asignar
	 */
	@XmlAttribute
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * Getter
	 * @return: tipo de datos del TO
	 */
	public String getTipodato() 
	{
		return tipodato;
	}

	/**
	 * Setter
	 * @param tipodato: tipodato a asignar
	 */
	@XmlElement
	public void setTipodato(String tipodato) 
	{
		this.tipodato = tipodato;
	}

	/**
	 * Getter
	 * @return: valor del TO
	 */
	public String getValor() 
	{
		return valor;
	}

	/**
	 * Setter
	 * @param valor: valor a asignar
	 */
	@XmlElement
	public void setValor(String valor) 
	{
		this.valor = valor;
	}
	
}//NoBorrar
