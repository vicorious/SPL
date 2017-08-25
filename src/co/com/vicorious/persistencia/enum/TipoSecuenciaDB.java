package co.com.vicorious.persistencia.enums;

/**
 *  <p>Enum</p>	
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
 * Enum encargado de relacionar el jdbc de la base de datos, con su forma de llamar secuencias
 * el parametro TIPOSECUENCIA, va el formato de como se debe llamar la secuencia (Por ejemplo en postgresql se llama con la funcion nextval(_secuencia_)
 * 
 *
 */
public enum TipoSecuenciaDB 
{
	MYSQL("mysql","NORMAL"),
	POSTGRE("postgresql","nextval('_secuencia_')"),
	ORACLE("oracle","NORMAL"),
	SQLSERVER("sqlserver", "NORMAL"),
	MARIADB("mariadb","NORMAL");
		
	private String jdbc;	
	private String tiposecuencia;
	
	private TipoSecuenciaDB(String jdbc, String tiposecuencia) 
	{
		this.jdbc = jdbc;
		this.tiposecuencia = tiposecuencia;
		
	}//Constructor

	public String getJdbc() 
	{
		return jdbc;
	}

	public void setJdbc(String jdbc) 
	{
		this.jdbc = jdbc;
	}

	public String getTiposecuencia() 
	{
		return tiposecuencia;
	}

	public void setTiposecuencia(String tiposecuencia) 
	{
		this.tiposecuencia = tiposecuencia;
	}			
	
}//NoBorrar
