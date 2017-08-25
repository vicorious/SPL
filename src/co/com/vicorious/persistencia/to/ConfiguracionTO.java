package co.com.vicorious.persistencia.to;

import java.io.Serializable;

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
 * Transfer Object (TO) Encargado de encapsular la configuracion (configuraciones.xml)
 * 
 *  configuracion
 *     esdefault-true-esdefault
 *     log-true-log
 *	   loglimite-10-loglimite
 *  configuracion
 * 
 * @author user
 *
 */
public class ConfiguracionTO implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String esdefault;	
	private String log;	
	private String loglimite;
	private String autocommit;
	private String sobreescribearchivo;
	
	public ConfiguracionTO(){};

	/**
	 * Constructor
	 * @param esdefault: default a asignar
	 * @param log: log a asignar
	 * @param loglimite: loglimite a asignar
	 * @param autocommit: autocommit a asignar
	 */
	public ConfiguracionTO(String esdefault, String log, String loglimite, String autocommit) 
	{
		super();
		this.esdefault = esdefault;
		this.log = log;
		this.loglimite = loglimite;
		this.autocommit = autocommit;
		
	}//Constructor

	
	/**
	 * Getter
	 * @return default del TO
	 */
	public String isEsdefault() 
	{
		return esdefault;
	}

	/**
	 * Setter
	 * @param esdefault: esdefault a asignar
	 */
	public void setEsdefault(String esdefault) 
	{
		this.esdefault = esdefault;
	}

	
	/**
	 * Getter 
	 * @return log del TO
	 */
	public String isLog() 
	{
		return log;
	}

	/**
	 * Setter
	 * @param log: log a asignar
	 */
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
	 * @param loglimite:loglimite a asignar
	 */
	public void setLoglimite(String loglimite) 
	{
		this.loglimite = loglimite;
	}

	/**
	 * Getter
	 * @return esdefault como booleano
	 */
	public boolean esDefaultBoo()
	{
		return Boolean.parseBoolean(isEsdefault());
	}
	
	/**
	 * Getter
	 * @return eslog como booleano
	 */
	public boolean esLogBoo()
	{
		return Boolean.parseBoolean(isLog());
	}
	
	/**
	 * Getter
	 * @return loglimite como entero
	 */
	public int getLogLimiteInt()
	{
		return Integer.parseInt(getLoglimite());
	}
	
	/**
	 * Getter
	 * @return autocommit del TO
	 */
	public String getAutoCommit()
	{
		return this.autocommit;
	}
	
	/**
	 * Getter
	 * @return autocommit como booleano
	 */
	public boolean getAutoCommitBoo()
	{
		return Boolean.parseBoolean(this.autocommit);
	}
	
	/**
	 * Getter
	 * @return tienelimite como booleano
	 */
	public boolean tieneLimiteLog()
	{
		String limite = getLoglimite();
		if(!limite.equalsIgnoreCase(Boolean.FALSE.toString()))//Si no viene la palabra FALSE 
		{
			return true;
		}
		return Boolean.parseBoolean(limite);
	}

	/**
	 * Getter
	 * @return sobreescribearchivo del TO
	 */
	public String getSobreescribearchivo() 
	{
		return sobreescribearchivo;
	}
	
	/**
	 * Getter
	 * @return sobreescribearchivo como booleano
	 */
	public boolean getSobreescribearchivoBool()
	{
		return Boolean.parseBoolean(this.sobreescribearchivo);
	}		

}//No borrar
