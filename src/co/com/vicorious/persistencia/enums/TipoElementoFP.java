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
 * Tipo de elemento usado solo en Funcion y Procedimiento (TipoElemento|FP|) relacionado con su respectiva clase adaptadora en Java
 * 
 * 
 *
 */
public enum TipoElementoFP 
{
	VARCHAR2("NO", "java.lang.String"),
	INTEGER("TYPE", "java.lang.Integer"),
	NUMBER("TYPE", "java.lang.Integer"),
	TEXT("NO", "java.lang.String"),
	DATE("NO", "java.sql.Date"),
	DECIMAL("TYPE", "java.lang.Integer"),
	VARCHAR("NO", "java.lang.String"),
	OBJETO("NO", "java.lang.String");
	
	private String llave;
	private String valor;

	private TipoElementoFP(String llave, String valor) 
	{
		this.llave = llave;
		this.valor = valor;
	}

	public String getLlave() 
	{
		return llave;
	}

	public void setLlave(String llave) 
	{
		this.llave = llave;
	}

	public String getValor() 
	{
		return valor;
	}

	public void setValor(String valor) 
	{
		this.valor = valor;
	}

}//No borrar
