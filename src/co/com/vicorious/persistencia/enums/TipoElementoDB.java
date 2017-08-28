package co.com.vicorious.persistencia.enums;

import java.sql.Types;

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
 * 
 * Encargado de encapsular los tipos de elemento de la base de datos, relacionado con su tipo de dato en Java (Varchar -- String)
 * 
 * 
 *
 */
public enum TipoElementoDB 
{
	VARCHAR2(Types.VARCHAR, TipoElemento.STRING),
	INTEGER(Types.INTEGER, TipoElemento.INTEGER),
	NUMBER(Types.NUMERIC, TipoElemento.INTEGER),
	TEXT(Types.VARCHAR, TipoElemento.STRING),
	DATE(Types.DATE, TipoElemento.STRING),
	TIMESTAMP(Types.TIMESTAMP, TipoElemento.STRING),
	DECIMAL(Types.DECIMAL, TipoElemento.DECIMAL),
	VARCHAR(Types.VARCHAR, TipoElemento.STRING),
	OBJETO(Types.JAVA_OBJECT, TipoElemento.STRING);
	
	private int llave;
	private TipoElemento valor;

	private TipoElementoDB(int llave, TipoElemento valor) 
	{
		this.llave = llave;
		this.valor = valor;
	}

	public int getLlave() 
	{
		return llave;
	}

	public void setLlave(int llave) 
	{
		this.llave = llave;
	}

	public TipoElemento getValor() 
	{
		return valor;
	}

	public void setValor(TipoElemento valor) 
	{
		this.valor = valor;
	}
		
			
}//No borrar
