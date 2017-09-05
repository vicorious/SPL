package prueba;

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
 *    Clase que encapsula un procedimiento en postgre
 *    
 *   */
@co.com.vicorious.persistencia.anotaciones.Procedimiento(nombre = "new_procedure")
public class Procedimiento 
{
	@Parametro(tipodato = TipoElementoDB.VARCHAR, tipoparametro = TipoParametro.IN, orden = 1)
	private String entrada;
	
	@Parametro(tipodato = TipoElementoDB.VARCHAR, tipoparametro = TipoParametro.OUT, orden = 2)
	private String salida;

	/**
	 * Getter
	 * @return entrada del procedimiento
	 */
	public String getEntrada() 
	{
		return entrada;
	}

	/**
	 * Setter
	 * @param entrada: entrada a asignar
	 */
	public void setEntrada(String entrada) 
	{
		this.entrada = entrada;
	}

	/**
	 * Getter
	 * @return: salida del procedimiento
	 */
	public String getSalida() 
	{
		return salida;
	}

	/**
	 * Setter
	 * @param salida: salida a asignar
	 */
	public void setSalida(String salida) 
	{
		this.salida = salida;
	}
	
	

}//No borrar
