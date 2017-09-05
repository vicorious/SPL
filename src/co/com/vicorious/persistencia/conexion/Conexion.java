package co.com.vicorious.persistencia.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Predicate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import co.com.vicorious.persistencia.enums.TiposBaseDatos;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.to.BaseDatosConfigTO;
import co.com.vicorious.persistencia.to.CredencialesTO;
import co.com.vicorious.persistencia.utilidades.Configuracion;
import co.com.vicorious.persistencia.utilidades.Logueable;
import co.com.vicorious.persistencia.utilidades.Utilidades;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesMensajes;
import co.com.vicorious.persistencia.utilidades.Utilidades.SQL;

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
 * Encargado de gestionar las conexion del SPL
 * 
 * 
 *
 */
public abstract class Conexion
{
	/**
	 * Metodo encargado de crear la configuracion a raiz de un objeto BaseDatosConfigTO
	 * y un objeto como CredencialesTO
	 * @param configuracion: Objeto BaseDatosConfig
	 * @param credenciales: Objeto tipo CredencialesTO
	 * @return Objeto Connection
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> crearConexion(BaseDatosConfigTO configuracion, CredencialesTO credenciales) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);				
		try 
		{
			Class.forName(configuracion.getDriver());
			StringBuilder cadenaconexion = SQL.getCadenaConexionJDBC(configuracion, credenciales);
			Connection nuevaconexion = DriverManager.getConnection(cadenaconexion.toString());	
			nuevaconexion.setAutoCommit(Configuracion.getInstancia().getConfiguracionframework().getAutoCommitBoo());
			Configuracion.getInstancia().setConexion(Optional.ofNullable(nuevaconexion));
						
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);

			return Optional.ofNullable(nuevaconexion);
		} catch (SQLException | ClassNotFoundException e) 
		{
			throw new PersistenciaException("crearConexion error: "+e.getMessage());			
		}
				
	}//crearConexion
	
	/**
	 * Metodo encargado de crear una conexion por Tipo de base de datos
	 * @param tiposBaseDatos: Enum tipo TipoBaseDatos
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> crearConexion(TiposBaseDatos tiposBaseDatos) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
		
		try
		{						
			Predicate<BaseDatosConfigTO> predicado = p -> p.getNombre().equals(tiposBaseDatos.getNombre()) && p.getVersion().equals(tiposBaseDatos.getVersion());
			Optional<BaseDatosConfigTO> opbaseDatos = Configuracion.getInstancia().elegirConfiguracionFiltro(predicado);
			if(!opbaseDatos.isPresent())
			{
				throw new PersistenciaException("Error al buscar la configuracion para la base de datos: "+tiposBaseDatos.getNombre());

			}
			CredencialesTO credenciales = Configuracion.getInstancia().credencialesPorDefecto();			
			StringBuilder cadenaConexion = SQL.getCadenaConexionJDBC(opbaseDatos.get(), credenciales);
			
			Connection conexion = DriverManager.getConnection(cadenaConexion.toString());
			conexion.setAutoCommit(Configuracion.getInstancia().getConfiguracionframework().getAutoCommitBoo());
			Configuracion.getInstancia().setConexion(Optional.ofNullable(conexion));
			
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);			
			
			return Optional.ofNullable(conexion);
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex.getMessage());
		}
	}//conexionPorDefecto
	
	/**
	 * Metodo encargado de realizar una conexion por datasource
	 * @param datasource datasource creado en el servidor de aplicaciones
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> crearConexion(String datasource) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO); 
		
		Connection conexion = null;
		InitialContext contexto = null;
		try
		{
			contexto = new InitialContext();
			Optional<?> data = localizarPorContexto(datasource ,contexto);
			DataSource dataso = data.isPresent() ? (DataSource)data.get() : null;
			conexion = dataso.getConnection();
			conexion.setAutoCommit(Configuracion.getInstancia().getConfiguracionframework().getAutoCommitBoo());
			Configuracion.getInstancia().setConexion(Optional.ofNullable(conexion));
			
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return Optional.ofNullable(conexion);
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al obtener el datasource: "+datasource+" Error: "+ex.getMessage());
		}				
		
	}//conexionDataSource
	
	/**
	 * Metodo encargado de crear una conexion a partir de datos planos
	 * @param usuario usuario de la base de datos
	 * @param password password correspondiente al usuario
	 * @param basedatos instancia de la base de datos
	 * @param puerto puerto de la base de datos
	 * @param host ip donde se encuentra la base datos
	 * @param tiposBaseDatos: enum que nos encapsula el nombre de la base de datos y su respectiva version
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<Connection> crearConexion(String usuario, String password, String basedatos, String puerto, String host, TiposBaseDatos tiposBaseDatos) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO); 
		
		try
		{
			CredencialesTO credenciales = (CredencialesTO) Utilidades.set(CredencialesTO.class, usuario, password, basedatos, puerto, host);
			Predicate<BaseDatosConfigTO> predicado = p -> p.getNombre().equals(tiposBaseDatos.getNombre()) && p.getVersion().equals(tiposBaseDatos.getVersion());
			Optional<BaseDatosConfigTO> opbaseDatos = Configuracion.getInstancia().elegirConfiguracionFiltro(predicado);
			if(!opbaseDatos.isPresent())
			{
				throw new PersistenciaException("Error al buscar la configuracion para la base de datos: "+tiposBaseDatos.getNombre());

			}
			StringBuilder cadenaConexion = SQL.getCadenaConexionJDBC(opbaseDatos.get(), credenciales);
			
			Connection conexion = DriverManager.getConnection(cadenaConexion.toString());
			conexion.setAutoCommit(Configuracion.getInstancia().getConfiguracionframework().getAutoCommitBoo());
			Configuracion.getInstancia().setConexion(Optional.ofNullable(conexion));
			
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return Optional.ofNullable(conexion);
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex.getMessage());
		}
	}
	/**
	 * Metodo encargado de localizar un EJB por un JDNI(Java Naming and Directory Interface)
	 * @param jdni Java Naming and directory interface creado en el servidor de aplicaciones
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<?> localizarPorContexto(String jdni) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO); 
				
		Object objeto = null;
		InitialContext contexto = null;
		Optional<?> resultado = null;
		
		if(jdni == null)
		{
			throw new PersistenciaException("El jdni debe ser valido o diferente de nulo, intente de nuevo: jdni: "+jdni);
		}
		
		try
		{
			contexto = new InitialContext();
			objeto = contexto.lookup(jdni);
			
		}catch(NamingException ex)
		{
			throw new PersistenciaException("Error al localizar el objeto: "+jdni+" Error: "+ex.getMessage());
			
		}
		
		resultado = Optional.ofNullable(objeto);
		
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
		return resultado;
	}//localizarPorContexto
	
	/**
	 * Metodo encargado de localizar un EJB por un JDNI(Java Naming and Directory Interface)
	 * @param jdni encapsula cualquier error generado por JAVA
	 * @param initialcontext contexto donde se va contextualizar el JNDI
	 * @return Conexion creada exitosamente (Connection)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Optional<?> localizarPorContexto(String jdni, Context initialcontext) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO); 
		
		Object objeto = null;
		InitialContext contexto = null;
		Optional<?> resultado = null;
		
		if(jdni == null)
		{
			throw new PersistenciaException("El jdni debe ser valido o diferente de nulo, intente de nuevo: jdni: "+jdni);
		}
		
		try
		{
			contexto = (InitialContext) initialcontext;			
			objeto = contexto.lookup(jdni);
			
		}catch(NamingException ex)
		{
			throw new PersistenciaException("Error al localizar el objeto: "+jdni+" Error: "+ex.getMessage());
			
		}
		
		resultado = Optional.ofNullable(objeto);
		
		Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
		return resultado;
	}//localizarPorContexto
	

}//No borrar
