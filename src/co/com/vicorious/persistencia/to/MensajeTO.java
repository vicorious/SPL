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
 * Transfer Object (TO) encargado de encapsular un elemento mensaje del archivo (mensajes.xml)
 * 
 * mensaje codigo="primermensaje"-Log programatico SPL-mensaje
 * 
 * 
 *
 */
public class MensajeTO 
{
	private String codigo;
	private String mensaje;
			
	public MensajeTO()
	{	
	}
	
	/**
	 * Constructor
	 * @param codigo: codigo a asignar
	 * @param mensaje: mensaje a asignar
	 */
	public MensajeTO(String codigo, String mensaje) 
	{
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
		
	}//MensajeTO
	
	/**
	 * Constructor (Codigo se setea automaticamente como N/A 
	 * @param mensaje: mensaje a asignar
	 */
	public MensajeTO(String mensaje)
	{
		this.codigo = "N/A";
		this.mensaje = mensaje;
		
	}//Constructor
	
	/**
	 * Getter
	 * @return: codigo de TO
	 */
	public String getCodigo() 
	{
		return codigo;
	}
	
	/**
	 * Setter
	 * @param codigo: codigo a asignar
	 */
	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}
	
	/**
	 * Getter
	 * @return: mensaje del TO
	 */
	public String getMensaje() 
	{
		return mensaje;
	}
	
	/**
	 * Setter
	 * @param mensaje: mensaje a asignar
	 */
	public void setMensaje(String mensaje) 
	{
		this.mensaje = mensaje;
	}		

}//No Borrar
