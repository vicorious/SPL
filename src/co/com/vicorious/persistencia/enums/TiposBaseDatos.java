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
 * Encargado de encapsular todos los tipos de base de datos que soporta el SPL y sus versiones (Este ENUM va directamente
 * relacionado con el archivo tipodb.xml, deben tener la misma informacion en su respectiva forma (Como XML en archivo, como instancia en el ENUM))
 * 
 * 
 * 
 *
 */
public enum TiposBaseDatos 
{
	MYSQL("MYSQL","5.1","mysql"),
	MYSQL57("MYSQL","5.7","mysql"),
	POSTGRES("POSTGRESQL","9.3.5","postgresql"),
	POSTGRES96("POSTGRESQL","9.6","postgresql"),
	SQLSERVER("SQLSERVER","2012","sqlserver"),
	ORACLE("ORACLE","12C","oracle"),
	MARIADB("MARIADB","10.2.7","mariadb");
	
	
	private final String nombre;
	private final String version;
	private final String jdbc;

	private TiposBaseDatos(String nombre, String version, String jdbc) 
	{
		this.nombre = nombre;
		this.version = version;
		this.jdbc = jdbc;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public String getVersion() 
	{
		return version;
	}
	
	public String getJDBC()
	{
		return this.jdbc;
	}
		
	
}//No borrar
