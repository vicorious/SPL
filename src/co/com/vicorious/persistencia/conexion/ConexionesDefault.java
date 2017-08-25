package co.com.vicorious.persistencia.conexion;

import java.sql.Connection;
import java.util.Optional;

import co.com.vicorious.persistencia.enums.TiposBaseDatos;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.utilidades.Configuracion;
import co.com.vicorious.persistencia.utilidades.Logueable;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesMensajes;

/**
 *  <p>Conexion</p>	
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
 * Encargado de encapsular todas las conexion que provee SPL por default (Para ahorrar codigo)
 * 
 * 
 *
 */
public abstract class ConexionesDefault 
{
	
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
	
	/**
	 * Encargado de generar una conexion por Default para ORACLE
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> generarConexionDefaultOracle() throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);		
		
		Optional<Connection> conexion = Conexion.crearConexion(TiposBaseDatos.ORACLE);
		Configuracion.getInstancia().setConexion(conexion);
		
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
		return conexion;
		
	}//generarConexionDefaultOracle
	
	/**
	 * Encargado de generar una conexion por Default para POSTGRESQL
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> generarConexionDefaultPostgres() throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);	
		
		Optional<Connection>conexion = Conexion.crearConexion(TiposBaseDatos.POSTGRES);
		Configuracion.getInstancia().setConexion(conexion);
		
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
		return conexion;
		
	}//generarConexionDefaultPostgres
	
	/**
	 * Encargado de generar una conexion por Default para MYSQL
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> generarConexionDefaultMysql() throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
		
		Optional<Connection> conexion = Conexion.crearConexion(TiposBaseDatos.MYSQL);
		Configuracion.getInstancia().setConexion(conexion);
		
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
		return conexion;
		
	}//generarConexionDefaultMysql
	
	/**
	 * Encargado de generar una conexion por Default para SQL SERVER
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> generarConexionDefaultSQLServer() throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
		
		Optional<Connection> conexion = Conexion.crearConexion(TiposBaseDatos.SQLSERVER);
		Configuracion.getInstancia().setConexion(conexion);
		
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
		return conexion;
		
	}//generarConexionDefaultSQLServer	

}//No borrar
