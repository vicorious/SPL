package prueba;

import co.com.vicorious.persistencia.anotaciones.Funcion;
import co.com.vicorious.persistencia.anotaciones.Parametro;
import co.com.vicorious.persistencia.enums.TipoElementoDB;
import co.com.vicorious.persistencia.enums.TipoParametro;

/**
 *  <p>dbobjects</p>	
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
 *    Clase que encapsula una funcion en postgre (Otra)
 *    
 *   */
@Funcion(nombre = "funcion_prueba")
public class FuncionPrueba 
{
	@Parametro(tipoparametro = TipoParametro.IN, tipodato = TipoElementoDB.INTEGER, orden = 2)
	private int parametroentrada;
	
	@Parametro(tipoparametro = TipoParametro.OUT, tipodato = TipoElementoDB.INTEGER, orden = 1)
	private int parametrosalida;

	/**
	 * Getter
	 * @return: parametro de entrada
	 */
	public int getParametroentrada() 
	{
		return parametroentrada;
	}

	/**
	 * Setter
	 * @param parametroentrada: parametroentrada a asignar
	 */
	public void setParametroentrada(int parametroentrada) 
	{
		this.parametroentrada = parametroentrada;
	}

	/**
	 * Getter
	 * @return parametro de salida de la funcion
	 */
	public int getParametrosalida() 
	{
		return parametrosalida;
	}

	/**
	 * Setter
	 * @param parametrosalida: parametrosalida a asignar
	 */
	public void setParametrosalida(int parametrosalida) 
	{
		this.parametrosalida = parametrosalida;
	}
	
}//No borrar
