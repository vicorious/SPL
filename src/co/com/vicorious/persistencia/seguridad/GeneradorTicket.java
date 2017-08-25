package co.com.vicorious.persistencia.seguridad;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

/**
 *  <p>Seguridad</p>	
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
 * Clase encargadade generar Tickets (Token) de manera dinamica
 * 
 *
 */
public abstract class GeneradorTicket 
{
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * Encargado de generar un ticket
	 * @return ticket generador
	 */
	public static String ticket()
	{
		return new BigInteger(130, random).toString(32);
		
	}//ticket
	
	/**
	 * Encargado de generar un ticket UUID
	 * @return ticket con UUID
	 */
	public static String ticket_UUID()
	{
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
		
	}//ticket_UUID	

}//NoBorrar
