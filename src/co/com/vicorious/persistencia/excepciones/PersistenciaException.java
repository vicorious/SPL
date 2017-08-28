package co.com.vicorious.persistencia.excepciones;


/**
 *  <p>Excepciones</p>	
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
 * Clase generica del framework (Encapsula todas las excepciones que genere Java)
 * 
 * 
 * 
 *
 */
public class PersistenciaException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PersistenciaException(Exception e)
	{
		/*StringBuilder mensajef = new StringBuilder();
		mensajef.append("Mensaje: "+e.getMessage());
	 	mensajef.append(" Causa: "+e.getCause().getMessage());
		Arrays.asList(e.getStackTrace()).stream().forEach(p -> mensajef.append(p.getLineNumber()).append(" Nombre archivo: ").append(p.getFileName()).append(" Clase: ").append(p.getClassName()).append(" Metodo: ").append(p.getMethodName()));
		 
		PersistenciaException(mensajef.toString());*/
		 
		super(e);
	}//Constructor
	 
	 public PersistenciaException(String mensaje)
	 {
		 super(mensaje);
		 
	 }//Constructor

}//No borrar
