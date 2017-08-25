package co.com.vicorious.persistencia.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

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
 *   TO encargado de guardar cada elemento mensaje del archivos Mensajes.xml
 * 
 *
 */
@XmlRootElement(name = "mensaje")
public class MensajeXML 
{
	private String codigo;
	
	private String valor_raiz;

	/**
	 * Getter
	 * @return: codigo del TO
	 */
	public String getCodigo() 
	{
		return codigo;
	}

	/**
	 * Setter
	 * @param codigo: codigo a asignar
	 */
	@XmlAttribute
	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	/**
	 * Getter
	 * @return: valor raiz del TO
	 */
	public String getValor_raiz() 
	{
		return valor_raiz;
	}

	/**
	 * Setter
	 * @param valor_raiz: valor_raiz a asignar
	 */
	@XmlValue
	public void setValor_raiz(String valor_raiz) 
	{
		this.valor_raiz = valor_raiz;
	}
				
}//NoBorrar
