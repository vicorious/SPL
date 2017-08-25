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
 * Encargada de encapsular los tipos de dato en la aplicacion y su respectivo formato para la base de datos
 * 
 * 
 * 
 *
 */
public enum TipoElemento 
{
	STRING("String","'"),
	DECIMAL("Double","'"),
	INTEGER("Int",""),
	DATE("Date","'"),
	OBJECT("Object","'");
	
	private String llave;
	private String formato;

	private TipoElemento(String llave, String formato) 
	{
		this.llave = llave;
		this.formato = formato;
	}

	public String getLlave() 
	{
		return llave;
	}

	public void setLlave(String llave) 
	{
		this.llave = llave;
	}

	public String getFormato() 
	{
		return formato;
	}

	public void setFormato(String formato) 
	{
		this.formato = formato;
	}
	
		
}//No Borrar
