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
 *  
 *   Encargado de encapsular la informacion del XML
 * 
 *
 */
@XmlRootElement(name = "configuracion")
public class ConfiguracionXML 
{
	private String esdefault;
	
	private String log;
	
	private String loglimite;
	
	private String autocommit;
	
	private String sobreescribearchivo;

	/**
	 * Getter
	 * @return: default del TO
	 */
	public String getEsdefault() 
	{
		return esdefault;
	}

	/**
	 * Setter
	 * @param esdefault: default a asignar
	 */
	@XmlElement
	public void setEsdefault(String esdefault) 
	{
		this.esdefault = esdefault;
	}

	/**
	 * Getter
	 * @return: log del TO
	 */
	public String getLog() 
	{
		return log;
	}

	/**
	 * Setter
	 * @param log: log a asignar
	 */
	@XmlElement
	public void setLog(String log) 
	{
		this.log = log;
	}

	/**
	 * Getter
	 * @return loglimite del TO
	 */
	public String getLoglimite() 
	{
		return loglimite;
	}

	/**
	 * Setter
	 * @param loglimite : loglimite a asignar
	 */
	@XmlElement
	public void setLoglimite(String loglimite) 
	{
		this.loglimite = loglimite;
	}

	/**
	 * Getter
	 * @return: autocommit del TO
	 */
	public String getAutocommit() 
	{
		return autocommit;
	}
	
	/**
	 * Setter
	 * @param autocommit: autocommit a asignar
	 */
	@XmlElement
	public void setAutocommit(String autocommit) 
	{
		this.autocommit = autocommit;
	}

	/**
	 * Getter
	 * @return: sobreescribearchivo del TO
	 */
	public String getSobreescribearchivo() 
	{
		return sobreescribearchivo;
	}

	/**
	 * Setter
	 * @param sobreescribearchivo: sobreeescribearchivo a asignar
	 */
	@XmlElement
	public void setSobreescribearchivo(String sobreescribearchivo) 
	{
		this.sobreescribearchivo = sobreescribearchivo;
	}
			
}//NoBorrar
