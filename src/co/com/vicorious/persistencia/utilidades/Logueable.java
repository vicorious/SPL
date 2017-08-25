package co.com.vicorious.persistencia.utilidades;

import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.to.MensajeTO;

/**
 *  <p>Utilidades</p>	
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
 * Encapsula el logueo de SPL a nivel de clase y objeto (static, nostatic)
 * 
 * 
 *
 */
public abstract class Logueable 
{
	/**
	 * Encargado de loguear cuando la clase es heredable (La clase que va heredar de esta no es Estatica)
	 * @param mensaje mensaje a loguear
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public void loguear(MensajeTO mensaje) throws PersistenciaException
	{
		Configuracion.getInstancia().agregarLog(mensaje);		
	}//loguear
	
	/**
	 * Encargado de escribir el log de manera manual (en cualquier momento)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public void escribirLog() throws PersistenciaException
	{
		Configuracion.getInstancia().escribirLogManualmente();		
	}//escribirLog
	
	/**
	 * Encargado de borrar el log de manera manual (en cualquier momento)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public void borrarLog() throws PersistenciaException
	{
		Configuracion.getInstancia().borrarLogManualmente();
	}//borrarLog
	
	/**
	 * Encargado de consultar el mensaje en el singleton
	 * @param codigo: codigo del mensaje
	 * @param tieneerror: si es para traer un mensaje de error
	 * @param parametros: parametros a enviar al mensaje
	 * @return mensaje completamente construido
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public MensajeTO getMensaje(String codigo, boolean tieneerror, String... parametros) throws PersistenciaException
	{
		return Configuracion.getInstancia().getMensaje(codigo, tieneerror, parametros);
	}//getMensaje
	
	/**
	 * Encargado de loguear por medio de la clase (Es un metodo de clase (static))	
	 * @param constantemensaje: Constante correspondiente al codigo del mensaje (codigo="finclaseerroneo") 
	 * @param nombremetodo: Parametros
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static void logueo(String nombremetodo, String constantemensaje) throws PersistenciaException
	{
		Configuracion.getInstancia().agregarLog(Configuracion.getInstancia().getMensaje(constantemensaje, false, nombremetodo));
	}//logueo
	
	/**
	 * Encargado de loguear simplemente un mensaje plano
	 * @param mensaje: literal
	 * @throws PersistenciaException Error de persistencia JAVA
	 */
	public static void logueo(String mensaje) throws PersistenciaException
	{
		Configuracion.getInstancia().agregarLog(mensaje);
	}//logueo
		
	
}//No Borrar
