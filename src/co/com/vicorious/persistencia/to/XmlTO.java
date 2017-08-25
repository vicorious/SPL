package co.com.vicorious.persistencia.to;

import co.com.vicorious.persistencia.xml.ConfiguracionXML;
import co.com.vicorious.persistencia.xml.DefaultsXML;
import co.com.vicorious.persistencia.xml.MensajesXML;
import co.com.vicorious.persistencia.xml.TiposDBXML;

/**
 *  <p>TO</p>	
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
 *   
 * TO encargado de encapsular la informacion de toda la configuracion XML del framework
 * 
 *
 */
public class XmlTO
{
	private ConfiguracionXML configuracion_xml;	
	private DefaultsXML defaults_xml;	
	private MensajesXML mensajes_xml;	
	private TiposDBXML tiposdb_xml;
	private String credenciales_properties;
	
	public XmlTO()
	{		
	}	

	/**
	 * Getter
	 * @return: configuracion del framework
	 */
	public ConfiguracionXML getConfiguracion_xml() 
	{
		return configuracion_xml;
	}

	/**
	 * Setter
	 * @param configuracion_xml: configuracion a asignar
	 */
	public void setConfiguracion_xml(ConfiguracionXML configuracion_xml) 
	{
		this.configuracion_xml = configuracion_xml;
	}

	/**
	 * Getter
	 * @return: defaults del framework
	 */
	public DefaultsXML getDefaults_xml() 
	{
		return defaults_xml;
	}
	
	/**
	 * Setter
	 * @param defaults_xml: defaults a asignar
	 */
	public void setDefaults_xml(DefaultsXML defaults_xml) 
	{
		this.defaults_xml = defaults_xml;
	}

	/**
	 * Getter
	 * @return mensajes del framework
	 */
	public MensajesXML getMensajes_xml() 
	{
		return mensajes_xml;
	}

	/**
	 * Setter
	 * @param mensajes_xml: mensajes a asignar
	 */
	public void setMensajes_xml(MensajesXML mensajes_xml) 
	{
		this.mensajes_xml = mensajes_xml;
	}

	/**
	 * Getter
	 * @return: tipos de base de datos del framework
	 */
	public TiposDBXML getTiposdb_xml() 
	{
		return tiposdb_xml;
	}

	/**
	 * Setter
	 * @param tiposdb_xml: tipos de base de datos a asignar
	 */
	public void setTiposdb_xml(TiposDBXML tiposdb_xml) 
	{
		this.tiposdb_xml = tiposdb_xml;
	}

	/**
	 * Getter
	 * @return: credenciales del framework
	 */
	public String getCredenciales_properties() 
	{
		return credenciales_properties;
	}

	/**
	 * Setter
	 * @param credenciales_properties: credenciales del framework
	 */
	public void setCredenciales_properties(String credenciales_properties) 
	{
		this.credenciales_properties = credenciales_properties;
	}		

}//NoBorrar
