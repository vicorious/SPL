package co.com.vicorious.persistencia.to;

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
 * Transfer Object (TO) encargado de manejar los archivos
 * 
 * 
 * 
 *
 */
public class ArchivoTO 
{
	private String ruta;
	
	private String contenido;		

	/**
	 * Constructor
	 * @param ruta: ruta a asignar
	 * @param contenido: contenido a asignar
	 */
	public ArchivoTO(String ruta, String contenido) 
	{
		super();
		this.ruta = ruta;
		this.contenido = contenido;
		
	}//Constructor
	
	/**
	 * Constructor
	 * @param ruta: ruta a asignar
	 */
	public ArchivoTO(String ruta) 
	{
		super();
		this.ruta = ruta;
		this.contenido = "N/A";
	}

	/**
	 * Getters
	 * @return la ruta del TO
	 */
	public String getRuta() 
	{
		return ruta;
	}

	/**
	 * Setter
	 * @param ruta: ruta a asignar
	 */
	public void setRuta(String ruta) 
	{
		this.ruta = ruta;
	}

	/**
	 * Getter
	 * @return retorna el contenido del TO
	 */
	public String getContenido() 
	{
		return contenido;
	}

	/**
	 * Setter
	 * @param contenido: contenido a asignar
	 */
	public void setContenido(String contenido) 
	{
		this.contenido = contenido;
	}		

}//NoBorrar
