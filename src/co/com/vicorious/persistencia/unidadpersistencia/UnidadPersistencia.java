package co.com.vicorious.persistencia.unidadpersistencia;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.MuchosAUno;
import co.com.vicorious.persistencia.anotaciones.Parametro;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.anotaciones.UnoAMuchos;
import co.com.vicorious.persistencia.enums.TipoElementoDB;
import co.com.vicorious.persistencia.enums.TipoElementoFP;
import co.com.vicorious.persistencia.enums.TipoParametro;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.utilidades.Configuracion;
import co.com.vicorious.persistencia.utilidades.Constantes;
import co.com.vicorious.persistencia.utilidades.Logueable;
import co.com.vicorious.persistencia.utilidades.Utilidades;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesMensajes;
import co.com.vicorious.persistencia.utilidades.Utilidades.SQL;

/**
 *  <p>Unidad persistencia</p>	
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
 * Clase encargada de encapsular y orquestar la mayoria de funcionalidades del framework
 * 
 *
 */
public class UnidadPersistencia extends Logueable implements UnidadPersistenciaInterface 
{
	private Optional<Connection> conexion;
	
	public UnidadPersistencia(Optional<Connection> conexion)
	{
		this.conexion = conexion;

	}//instancia
	
	public UnidadPersistencia()
	{
	}//instancia
			
	/***************************************************************************************************************/
	@SuppressWarnings("unchecked")
	@Override
	public Optional<?> get(Class<?> entidad, Predicate<Object> predicado) throws PersistenciaException 
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		List<Object> resultadoSelect =  new ArrayList<Object>();
		List<Object> resultadoPredicate = new ArrayList<Object>();
		
		Optional<List<Object>> resultado = (Optional<List<Object>>) get(entidad);
		
		if(resultado.isPresent())
		{
			resultadoSelect = (List<Object>) resultado.get();
		}
		
		resultadoPredicate = (List<Object>) resultadoSelect.stream().filter(predicado).collect(Collectors.toList());
		
		Optional<List<Object>> resultadoop = Optional.of(resultadoPredicate);
		
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
		
		return resultadoop;
		
	}//getEntidad

	/***************************************************************************************************************/
	@Override
	public Optional<?> get(Object objetowhere, String... columnanowhere) throws PersistenciaException 
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Statement sentencia = null;
		ResultSet resultado = null;
		Class<?> entidadwhere = objetowhere.getClass();
		
		List<Object> resultadoSelect =  new ArrayList<Object>();
		Optional<?> resultadop = null;
		
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		
		try
		{

			StringBuilder sql = SQL.getSQL(objetowhere,columnanowhere);			
			sentencia = conexion.createStatement();
			//Consultamos el SQL
			resultado = sentencia.executeQuery(sql.toString());		
			
			while(resultado.next())
			{
				Object singular = entidadwhere.newInstance();
				Optional<Field> nombrecampoid = Arrays.asList(entidadwhere.getDeclaredFields()).stream().filter(field -> field.getAnnotation(ID.class) != null).findFirst();
				if(!nombrecampoid.isPresent())
				{
					throw new PersistenciaException("La entidad "+entidadwhere.getSimpleName()+" Debe tener una campo con la etiqueta ID");
				}
				
				for(Field campo: entidadwhere.getDeclaredFields())
				{
					campo.setAccessible(true);	
					
					if(!campo.isAnnotationPresent(Columna.class))
					{
						continue;
					}
					
					Columna columna = campo.getAnnotation(Columna.class);
					MuchosAUno muchos = campo.getAnnotation(MuchosAUno.class);
					UnoAMuchos uno = campo.getAnnotation(UnoAMuchos.class);
					
					//Uno
					if(uno == null)
					{
						//Esperando
					}else if(uno.lazy())
					{
						singular = setObjectoUno(campo, singular,conexion);
						continue;
					}else
					{
						continue;
					}
												
					Method metodo = resultado.getClass().getMethod("get"+columna.tipoDato().getValor().getLlave(), String.class);
					metodo.setAccessible(true);
					Object resultadoResulset = metodo.invoke(resultado, columna.nombre());
					
					if(columna.tipoDato() == TipoElementoDB.DATE)
					{
						LocalDate date = null;
					
						if(columna.formato() != null && !columna.formato().isEmpty())
						{
							date = LocalDate.parse(resultadoResulset.toString());
						}else
						{
							try
							{
								SimpleDateFormat formato = new SimpleDateFormat(columna.formato());
								String fechaformateada = formato.format(formato.parse(resultadoResulset.toString()));
								date = LocalDate.parse(fechaformateada);
								
							}catch(ParseException ex)
							{
								throw new PersistenciaException("Error al formatear la fecha: "+resultadoResulset+" con formato: "+columna.formato());
							}
						}
						
						Utilidades.set(singular, campo.getName(), date);
						
						continue;
					}else if(columna.tipoDato() == TipoElementoDB.TIMESTAMP)//LocalDateTime
					{
						LocalDateTime date = null;
						
							
							if(columna.formato() == null || columna.formato().isEmpty())
							{
								date = LocalDateTime.parse(resultadoResulset.toString());
							}else
							{
								Pattern patron = Pattern.compile("(.+\\d\\:\\d+)\\.(\\d+)");//Si viene el formato yyyy-MM-dd HH:mm:ss.SSSS (no se puede mapear)
								Matcher match = patron.matcher(resultadoResulset.toString());
								if(match.find())
								{
									String fecha = match.group(1);
									LocalDateTime ldt = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern(columna.formato()));
									date = ldt;
								}
								
							}
							
							Utilidades.set(singular, campo.getName(), date);
							
							continue;
					}											
			
					
					//Muchos
					if(muchos == null)
					{
						//Esperando
					}else if(muchos.lazy())
					{
						boolean esdefault = Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo();
						Optional<?> hijomuchos = setObjetoMuchos(campo, resultadoResulset, conexion, esdefault);
						//Optional<?> hijomuchos = setObjetoMuchos(campo, resultadoResulset, conexion, false);
						Utilidades.set(singular, campo.getName(), hijomuchos.get());
						continue;
					}else
					{
						continue;
					}
															
					Utilidades.set(singular, campo.getName(), resultadoResulset);
					
				}
				resultadoSelect.add(singular);
			}			
			resultadop = Optional.of(resultadoSelect);
			
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex.getMessage());
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
			
		}
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
		return resultadop;
														
	}//getEntidad

	/***************************************************************************************************************/
	@Override
	public Optional<?> get(Class<?> entidad) throws PersistenciaException 
	{
		
		//Log
		String nombremetodo2 = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo2));
		
		List<Object> resultadoSelect =  new ArrayList<Object>();
		Statement sentencia = null;
		ResultSet resultado = null;
		Optional<List<Object>> opcional = null; 
		String campoactual = new String();
		
		Connection conexion = null; 		
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		try
		{			
			StringBuilder sql = SQL.getSQL(entidad);			
			sentencia = conexion.createStatement();
			//Consultamos el SQL
			resultado = sentencia.executeQuery(sql.toString());					
			while(resultado.next())
			{
				Object singular = entidad.newInstance();
				Optional<Field> nombrecampoid = Arrays.asList(entidad.getDeclaredFields()).stream().filter(field -> field.getAnnotation(ID.class) != null).findFirst();
				if(!nombrecampoid.isPresent())
				{
					throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" Debe tener una campo con la etiqueta ID");
				}
				for(Field campo: entidad.getDeclaredFields())
				{
					campo.setAccessible(true);	
					
					if(!campo.isAnnotationPresent(Columna.class))
					{
						continue;
					}
					
					Columna columna = campo.getAnnotation(Columna.class);
					MuchosAUno muchos = campo.getAnnotation(MuchosAUno.class);
					UnoAMuchos uno = campo.getAnnotation(UnoAMuchos.class);
					
					//Uno
					if(uno == null)
					{
						//Esperando
					}else if(uno.lazy())
					{
						singular = setObjectoUno(campo, singular,conexion);
						continue;
					}else
					{
						continue;
					}
					
					campoactual = columna.nombre();
					
					String nombremetodo = "get"+columna.tipoDato().getValor().getLlave();
					//Optional<Method> metodop = Arrays.asList(metodos).stream().filter(m -> m.getName().equalsIgnoreCase(nombremetodo)).findFirst();
					Method metodo = resultado.getClass().getMethod(nombremetodo, String.class);
					
					metodo.setAccessible(true);
					Object resultadoResulset = metodo.invoke(resultado, columna.nombre());
					
					if(columna.tipoDato() == TipoElementoDB.DATE)
					{
						LocalDate date = null;
					
						if(columna.formato() != null && !columna.formato().isEmpty())
						{
							date = LocalDate.parse(resultadoResulset.toString());
						}else
						{
							try
							{
								SimpleDateFormat formato = new SimpleDateFormat(columna.formato());
								String fechaformateada = formato.format(formato.parse(resultadoResulset.toString()));
								date = LocalDate.parse(fechaformateada);
								
							}catch(ParseException ex)
							{
								throw new PersistenciaException("Error al formatear la fecha: "+resultadoResulset+" con formato: "+columna.formato());
							}
						}
						
						Utilidades.set(singular, campo.getName(), date);
						
						continue;
					}else if(columna.tipoDato() == TipoElementoDB.TIMESTAMP)//LocalDateTime
					{
						LocalDateTime date = null;
						
						if(columna.formato() == null || columna.formato().isEmpty())
						{
							date = LocalDateTime.parse(resultadoResulset.toString());
						}else
						{
							Pattern patron = Pattern.compile("(.+\\d\\:\\d+)\\.(\\d+)");//Si viene el formato yyyy-MM-dd HH:mm:ss.SSSS (no se puede mapear)
							Matcher match = patron.matcher(resultadoResulset.toString());
							if(match.find())
							{
								String fecha = match.group(1);
								LocalDateTime ldt = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern(columna.formato()));
								date = ldt;
							}
							
						}
						
						Utilidades.set(singular, campo.getName(), date);
						
						continue;
						
					}
															
					//Muchos
					if(muchos == null)
					{
						//Esperando
					}else if(muchos.lazy())
					{
						boolean esdefault = Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo();
						Optional<?> hijomuchos = setObjetoMuchos(campo, resultadoResulset, conexion, esdefault);
						Utilidades.set(singular, campo.getName(), hijomuchos.get());
						continue;
					}else
					{
						continue;
					}
					
					Utilidades.set(singular, campo.getName(), resultadoResulset);
					
				}
				resultadoSelect.add(singular);
			}
			
			opcional = Optional.of(resultadoSelect);
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo2));
			
			return opcional;
		}catch(IllegalArgumentException ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo2, ex.getMessage()));
			throw new PersistenciaException("El campo con el nombre de columna: "+campoactual+" para la entidad: "+entidad.getName()+" Tiene problemas para el asignamiento: ERROR: "+ex.getMessage());
		}
		catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo2, ex.getMessage()));
			throw new PersistenciaException(ex);
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
				
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
	}//getEntidad

	/***************************************************************************************************************/
	@SuppressWarnings("resource")
	@Override
	public boolean borrar(Object objectowhere, String... columnanowhere) throws PersistenciaException 
	{

		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Statement sentencia = null;		
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		try
		{						
			StringBuilder sql = SQL.getDeleteSQL(objectowhere, columnanowhere);			
			sentencia = conexion.createStatement();
			//Consultamos el SQL
			sentencia.executeUpdate(sql.toString());			
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			return true;
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex);
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}			
			
	}//EliminarEntidad
	
	/***************************************************************************************************************/
	@SuppressWarnings("resource")
	@Override
	public boolean borrar(Object objectowhere) throws PersistenciaException 
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Statement sentencia = null;		
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		HashMap<TipoElementoDB, String> defaults = null;
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		try
		{
			boolean esdefault = Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo();
			if(esdefault)
			defaults = Configuracion.getInstancia().getDefaults();
			StringBuilder sql = SQL.getDeleteSQL(objectowhere, defaults);			
			sentencia = conexion.createStatement();
			//Consultamos el SQL
			sentencia.executeUpdate(sql.toString());			
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			return true;
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex);
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}			
			
	}//EliminarEntidad
	
	/***************************************************************************************************************/
	@SuppressWarnings("resource")
	public boolean insertar(Object entidad) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Statement sentencia = null;	
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		try 
		{			
			StringBuilder sql = SQL.getInsertSQL(entidad);
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql.toString());			
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			return true;
		} catch (Exception e) 
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, e.getMessage()));
			throw new PersistenciaException(e.getMessage());
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
						
	}//insertarEntidad
	
	/***************************************************************************************************************/
	@SuppressWarnings("resource")
	public boolean actualizar(Object entidad, String... columnanowhere) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Statement sentencia = null;
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		try 
		{			
			StringBuilder sql = SQL.getUpdateSQL(entidad, columnanowhere);
			sentencia = conexion.createStatement();
			sentencia.executeUpdate(sql.toString());			
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			return true;
		} catch (Exception e) 
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, e.getMessage()));
			throw new PersistenciaException(e.getMessage());
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
	}//actualizarEntidad
	
	/**
	 * Metodo encargado de gestionar el procedimiento
	 * 
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	public <T> Optional<?> procedimiento(Object procedimiento) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Class<?> clase = procedimiento.getClass();
		StringBuilder llamadaprocedimiento = SQL.getCallableProcedimiento(clase);
		Map<Integer,Method> metodosSalida = new HashMap<Integer,Method>();
		Map<Integer,Object> salida = new HashMap<Integer,Object>();
		CallableStatement callable = null;
		Optional<Object> opcional = null;
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		
		try
		{
			callable = conexion.prepareCall(llamadaprocedimiento.toString());
			
			for(Field campo: clase.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Parametro.class))
				{
					continue;
				}
				
				Parametro parametro =  campo.getAnnotation(Parametro.class);				
				TipoParametro tipoparametro = parametro.tipoparametro();
				TipoElementoDB tipoelemento = parametro.tipodato();
				
				Object objeto = Utilidades.get(procedimiento, campo.getName());
				
				if(objeto == null && tipoparametro == TipoParametro.IN)
				{
					continue;
				}
				
				if(tipoparametro == TipoParametro.IN)
				{
					TipoElementoFP elemento = TipoElementoFP.valueOf(tipoelemento.name());
					Class<?> clazz = Class.forName(elemento.getValor());
					Class<?> claseorden = (Class<?>) Class.forName(Constantes.CLASE_ORDEN).getField(Constantes.TYPE).get(null);
					Method metodo = null;
					if(elemento.getLlave().equalsIgnoreCase(Constantes.TYPE))
					{
						Class<?> primitivo = (Class<?>) clazz.getField(Constantes.TYPE).get(null);
						T instanciain = (T) Utilidades.convertirInstanciaObjeto(clazz, objeto);
						metodo = callable.getClass().getMethod("set"+tipoelemento.getValor().getLlave(),claseorden , primitivo);
						
						metodo.setAccessible(true);
						//Es muy frustrante, pero no he podido solucionar esto de una forma mas elegante :(
						if(elemento.getValor().equalsIgnoreCase(String.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (String)instanciain);
						}else if(elemento.getValor().equalsIgnoreCase(Integer.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (int)instanciain);
						}else if(elemento.getValor().equalsIgnoreCase(Date.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (Date)instanciain);
						}else if(elemento.getValor().equalsIgnoreCase(BigDecimal.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (BigDecimal)instanciain);
						}
					}else
					{
						
						T a = (T) Utilidades.convertirInstanciaObjeto(clazz, objeto);
						metodo = callable.getClass().getMethod("set"+tipoelemento.getValor().getLlave(), claseorden, clazz);
						
						metodo.setAccessible(true);
						//Es muy frustrante, pero no he podido solucionar esto de una forma mas elegante :(
						if(elemento.getValor().equalsIgnoreCase(String.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (String)a);
						}else if(elemento.getValor().equalsIgnoreCase(Integer.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (int)a);
						}else if(elemento.getValor().equalsIgnoreCase(Date.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (Date)a);
						}else if(elemento.getValor().equalsIgnoreCase(BigDecimal.class.getName()))
						{
							metodo.invoke(callable, parametro.orden(), (BigDecimal)a);
						}
						
					}
					
					//metodo.invoke(callable, parametro.orden(), finalo);


				}else if(tipoparametro == TipoParametro.OUT)
				{
					Class<?> claseorden = (Class<?>) Class.forName(Constantes.CLASE_ORDEN).getField(Constantes.TYPE).get(null);
					Method metodo = callable.getClass().getMethod("get"+tipoelemento.getValor().getLlave(), claseorden);					
					callable.registerOutParameter(parametro.orden(), tipoelemento.getLlave());
					metodosSalida.put(parametro.orden(),metodo);
				}
			}
			
			callable.execute();
			
			//Ejecutamos los metodos de salida
			for(Map.Entry<Integer, Method> parametro: metodosSalida.entrySet())
			{
				parametro.getValue().setAccessible(true);
				Object resultado = parametro.getValue().invoke(callable, parametro.getKey());
				salida.put(parametro.getKey(), resultado);
			}
						
			
			opcional = Optional.of(salida);
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return opcional;
			
		}
		catch(IllegalAccessException ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException("Error al generar llamar al procedimiento: Posibles problemas con el driver: "+ex.getMessage());
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex.getMessage());
		}
		
		finally
		{
			try
			{
				if(callable != null)
				{
					callable.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
		
	}//llamarProcedimiento
	
	/***************************************************************************************************************/
	@SuppressWarnings("resource")
	public Optional<?> funcion(Object funcion) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Class<?> clase = funcion.getClass();
		StringBuilder queryString = SQL.getCallableFunction(clase);
		Map<Integer,Object> salida = new HashMap<Integer,Object>();
		Map<Integer,Method> metodosSalida = new HashMap<Integer,Method>();
		CallableStatement callable = null;
		Optional<Object> opcional = null;
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		
		try
		{
			callable = conexion.prepareCall(queryString.toString());
			
			
			for(Field campo: clase.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Parametro.class))
				{
					continue;
				}
				
				Parametro parametro =  campo.getAnnotation(Parametro.class);				
				TipoParametro tipoparametro = parametro.tipoparametro();
				TipoElementoDB tipoelemento = parametro.tipodato();
				
				Object objeto = Utilidades.get(funcion, campo.getName());
				
				if(objeto == null)
				{
					continue;
				}
				
				if(tipoparametro == TipoParametro.IN)
				{
					TipoElementoFP elemento = TipoElementoFP.valueOf(tipoelemento.name());
					Class<?> clazz = Class.forName(elemento.getValor());
					Method metodo = null;
					if(elemento.getLlave().equalsIgnoreCase(Constantes.TYPE))
					{
						Class<?> primitivo = (Class<?>) clazz.getField(Constantes.TYPE).get(null);
						try
						{
							objeto = primitivo.cast(objeto);
							metodo = callable.getClass().getMethod("set"+tipoelemento.getValor().getLlave(), primitivo, primitivo);
						}catch(ClassCastException ex)
						{
							metodo = callable.getClass().getMethod("set"+tipoelemento.getValor().getLlave(), primitivo, primitivo);
						}
					}else
					{
						objeto = clazz.cast(objeto);
						metodo = callable.getClass().getMethod("set"+tipoelemento.getValor().getLlave(), clazz, clazz);
						
					}
					metodo.setAccessible(true);
					metodo.invoke(callable, parametro.orden(), (int)objeto);


				}else if(tipoparametro == TipoParametro.OUT)
				{
					TipoElementoFP elemento = TipoElementoFP.valueOf(tipoelemento.name());
					Class<?> clazz = Class.forName(elemento.getValor());
					Method metodo = null;
					if(elemento.getLlave().equalsIgnoreCase(Constantes.TYPE))
					{
						Class<?> primitivo = (Class<?>) clazz.getField(Constantes.TYPE).get(null);
						metodo = callable.getClass().getMethod("get"+tipoelemento.getValor().getLlave(), primitivo);					
						
					}else
					{
						metodo = callable.getClass().getMethod("get"+tipoelemento.getValor().getLlave(), clazz);					

					}
					callable.registerOutParameter(parametro.orden(), tipoelemento.getLlave());
					metodosSalida.put(parametro.orden(),metodo);
				}
			}
			callable.executeUpdate();
			
			//Ejecutamos los metodos de salida
			for(Map.Entry<Integer, Method> parametro: metodosSalida.entrySet())
			{
				parametro.getValue().setAccessible(true);
				Object resultado = parametro.getValue().invoke(callable, parametro.getKey());
				Optional<Field> resuop = Arrays.asList(clase.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(Parametro.class) && p.getAnnotation(Parametro.class).orden() == parametro.getKey()).findFirst();
				if(resuop.isPresent())
				{
					Utilidades.set(funcion, resuop.get().getName(), resultado);
				}
				salida.put(parametro.getKey(), resultado);
			}
			
									
			opcional = Optional.of(funcion);
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return opcional;
			
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex.getMessage());
		}finally
		{
			try
			{
				if(callable != null)
				{
					callable.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
		
	}//llamarFuncion
	
	/***************************************************************************************************************/
	
	/**
	 * Buscar 
	 * @param campo
	 * @param resultadoResulset
	 * @return
	 * @throws PersistenciaException
	 */
	private Optional<?> setObjetoMuchos(Field campo, Object resultadoResulset,Connection conexion, boolean esdefault) throws PersistenciaException
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Object hija = null;
		Optional<?> resultadop = null; 
		HashMap<TipoElementoDB, String> defaults = null;
		Statement sentenciahija = null;
		try
		{
			Class<?> claseHija = campo.getType();
			Optional<Field> id = Arrays.asList(claseHija.getDeclaredFields()).stream().filter(c -> c.getAnnotation(ID.class) != null).findFirst();
		
			if(id.isPresent())
			{
				hija = claseHija.newInstance();
				Field campoid = id.get();
				String nombrecampo = campoid.getName();
				Utilidades.set(hija, nombrecampo, resultadoResulset);
				sentenciahija = conexion.createStatement();
				if(esdefault)
				{
					defaults = Configuracion.getInstancia().getDefaults();
				}
				StringBuilder sqlHija = SQL.getSQL(hija, defaults);
				ResultSet hijaresultado = sentenciahija.executeQuery(sqlHija.toString());
			
				//Reiniciamos el objeto hija
			
				hija = claseHija.newInstance();
				while(hijaresultado.next())
				{
					for(Field campohija: claseHija.getDeclaredFields())
					{
						campohija.setAccessible(true);	
					
						if(!campohija.isAnnotationPresent(Columna.class) || campohija.isAnnotationPresent(UnoAMuchos.class))
						{
							continue;
						}
					
						Columna columnahija = campohija.getAnnotation(Columna.class);
													
						Method metodo = hijaresultado.getClass().getMethod("get"+columnahija.tipoDato().getValor().getLlave(), String.class);
						metodo.setAccessible(true);
				 
						Object resultadoR = metodo.invoke(hijaresultado, columnahija.nombre());
					
						Utilidades.set(hija, campohija.getName(), resultadoR);
					
					}
				}
			
			
			}else
			{
				String mensaje = new String("Para la entidad: "+claseHija.getSimpleName()+" No tiene un campo con la etiqueta ID");
				super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, mensaje));
				throw new PersistenciaException(mensaje);
			}
			resultadop = Optional.of(hija);
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return resultadop;
		}catch(Exception ex)
		{
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getLocalizedMessage()));
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo));
			throw new PersistenciaException(ex.getMessage());
			
		}
	}//setObjetoMuchos
		
	/***************************************************************************************************************/
	
	/**
	 * 
	 * @param campo
	 * @param objetoIDPadre
	 * @return
	 * @throws PersistenciaException
	 */
	private Optional<?> setObjectoUno(Field campo, Object objetoIDPadre, Connection conexion) throws PersistenciaException
	{		
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Class<?> entidad = null;
			
		ParameterizedType tipoLista;
		try 
		{
			tipoLista = (ParameterizedType) campo.getGenericType();
		} catch (SecurityException e) 
		{
			throw new PersistenciaException("Error al obtener el campo: "+campo.getName()+" su getGenericType ");
		}
		String nombreentidad = tipoLista.getActualTypeArguments()[0].getTypeName();
		
		//Ahora buscamos en la entidad donde esta el campo
		
		try 
		{
			entidad = Class.forName(nombreentidad);
		} catch (ClassNotFoundException e) 
		{
			//
			throw new PersistenciaException("No encontramos la entidad "+nombreentidad);
		}
		
		 //Obtenemos el valor del ID
		 Optional<Field> campoId = Arrays.asList(objetoIDPadre.getClass().getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(ID.class)).findFirst();
		 if(campoId.isPresent())
		 {
			 //Obtenemos el valor del id del objeto padre
			 Object idpadre = Utilidades.get(objetoIDPadre, campoId.get().getName()); 
			 //Obtenemos los campos que tengan el tipo de objetoIdPadre en la entidad de la coleccion
			 Field[] campos = Arrays.asList(entidad.getDeclaredFields()).stream().filter(c -> c.isAnnotationPresent(MuchosAUno.class) 
					 && c.getGenericType().getTypeName().equalsIgnoreCase(objetoIDPadre.getClass().getName())).toArray(Field[]::new);
						//&& ((ParameterizedType)(c.getGenericType())).getActualTypeArguments()[0].getTypeName().equalsIgnoreCase(objetoIDPadre.getClass().getName())).toArray();
			 if(campos == null || campos.length == 0)
			 {
				 throw new PersistenciaException("La entidad "+entidad.getName()+" debe tener un campo de tipo uno a muchos con el tipo de clase: "+objetoIDPadre.getClass().getName());
			 }
			//El objetoPadreId en los campos de la tabla hija
			for(Field cam: campos)	 
			{
				cam.setAccessible(true);
				LinkedHashMap<String, Object> parametros = new LinkedHashMap<String, Object>();
				Columna columna = cam.getAnnotation(Columna.class);

				if(columna != null)
				{
					parametros.put(columna.nombre(), idpadre);
					try 
					{
						Optional<?> lista = getEntidad(entidad, conexion ,parametros);
						Utilidades.set(objetoIDPadre, campo.getName(), lista.get());
					}catch (Exception e) 
					{
						super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, e.getMessage()));
						throw new PersistenciaException("No encontramos la entidad "+nombreentidad+" con el parametro id padre: "+idpadre);
					}
				}
					 
			}
		 }
		 Optional<?> resultado = Optional.of(objetoIDPadre);
		 
		 super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
		 
		 return resultado;
		
	}//setObjectoUno
	
	/***************************************************************************************************************/

	@SuppressWarnings("resource")
	@Override
	public Optional<?> getForID(Object objectowhere) throws PersistenciaException 
	{		
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		Optional<?> entidadop = null;
		Statement sentenciahija = null;
		HashMap<TipoElementoDB,String> defaults = null;
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		
		try
		{
			boolean esdefault = Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo();
			sentenciahija = conexion.createStatement();
			Object objetoformateado = Utilidades.setNullId(objectowhere);
			if(esdefault)
			{
				defaults = Configuracion.getInstancia().getDefaults();
			}
			StringBuilder sqlHija = SQL.getSQL(objetoformateado, defaults);
			ResultSet hijaresultado = sentenciahija.executeQuery(sqlHija.toString());
			// TODO Auto-generated method stub
			Object objeto = objectowhere;
			for(Field campohija: objectowhere.getClass().getDeclaredFields())
			{
				campohija.setAccessible(true);	
			
				if(!campohija.isAnnotationPresent(Columna.class))
				{
					continue;
				}
			
				Columna columnahija = campohija.getAnnotation(Columna.class);
											
				Method metodo = hijaresultado.getClass().getMethod("get"+columnahija.tipoDato().getValor().getLlave(), String.class);
				metodo.setAccessible(true);
		 
				Object resultadoR = metodo.invoke(hijaresultado, columnahija.nombre());
			
				Utilidades.set(objeto, campohija.getName(), resultadoR);
			
			}
			entidadop = Optional.of(objeto);
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return entidadop;
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException("No se ha podido obtener la conexion a traves de singleton de configuracion");
		}finally
		{
			try
			{
				if(sentenciahija != null)
				{
					sentenciahija.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
	}//getEntidadID
	/***************************************************************************************************************/	
	@Override
	public Optional<?> get(Class<?> entidad, LinkedHashMap<String, Object> parametros) throws PersistenciaException 
	{
	
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		List<Object> resultadoSelect =  new ArrayList<Object>();
		Statement sentencia = null;
		ResultSet resultado = null;
		Optional<List<Object>> opcional = null; 
		
		Connection conexion = null; 
		Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
		if(!conexionp.isPresent() && !this.conexion.isPresent())
		{
			throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
		}
		conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
		try
		{				
			Object objetoID = entidad.newInstance();
			StringBuilder sql = SQL.getSQL(entidad, parametros);			
			sentencia = conexion.createStatement();
			//Consultamos el SQL
			resultado = sentencia.executeQuery(sql.toString());					
			while(resultado.next())
			{
				Object singular = entidad.newInstance();
				Optional<Field> nombrecampoid = Arrays.asList(entidad.getDeclaredFields()).stream().filter(field -> field.getAnnotation(ID.class) != null).findFirst();
				if(!nombrecampoid.isPresent())
				{
					throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" Debe tener una campo con la etiqueta ID");
				}
				for(Field campo: entidad.getDeclaredFields())
				{
					campo.setAccessible(true);	
					
					if(!campo.isAnnotationPresent(Columna.class))
					{
						continue;
					}
					
					Columna columna = campo.getAnnotation(Columna.class);
					MuchosAUno muchos = campo.getAnnotation(MuchosAUno.class);
					UnoAMuchos uno = campo.getAnnotation(UnoAMuchos.class);
					
					//Uno
					if(uno == null)
					{
						//Esperando
					}else if(uno.lazy())
					{
						Object lista = setObjectoUno(campo, objetoID ,conexion);
						Utilidades.set(singular, campo.getName(), lista);
						continue;
					}else
					{
						continue;
					}
													
					Method metodo = resultado.getClass().getMethod("get"+columna.tipoDato().getValor().getLlave(), String.class);
					metodo.setAccessible(true);
					Object resultadoResulset = metodo.invoke(resultado, columna.nombre());
					
					//Muchos
					if(muchos == null)
					{
						//Esperando
					}else if(muchos.lazy())
					{
						boolean esdefault = Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo();
						Optional<?> hijomuchos = setObjetoMuchos(campo, resultadoResulset, conexion, esdefault);
						//Optional<?> hijomuchos = setObjetoMuchos(campo, resultadoResulset,conexion, false);
						Utilidades.set(singular, campo.getName(), hijomuchos.get());
						continue;
					}else
					{
						continue;
					}
					
					Utilidades.set(singular, campo.getName(), resultadoResulset);
					
				}
				resultadoSelect.add(singular);
			}			
			opcional = Optional.of(resultadoSelect);
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return opcional;
		}catch(Exception ex)
		{
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex);
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
	}//getEntidad
	
	/***************************************************************************************************************/
	private Optional<?> getEntidad(Class<?> entidad,Connection pconexion ,LinkedHashMap<String, Object> parametros) throws PersistenciaException 
	{		
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		List<Object> resultadoSelect =  new ArrayList<Object>();
		Statement sentencia = null;
		ResultSet resultado = null;
		Optional<List<Object>> opcional = null; 
		
		Connection conexion = pconexion;
		try
		{				
			StringBuilder sql = SQL.getSQL(entidad, parametros);			
			sentencia = conexion.createStatement();
			//Consultamos el SQL
			resultado = sentencia.executeQuery(sql.toString());					
			while(resultado.next())
			{
				Object singular = entidad.newInstance();
				Optional<Field> nombrecampoid = Arrays.asList(entidad.getDeclaredFields()).stream().filter(field -> field.getAnnotation(ID.class) != null).findFirst();
				if(!nombrecampoid.isPresent())
				{
					throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" Debe tener una campo con la etiqueta ID");
				}
				for(Field campo: entidad.getDeclaredFields())
				{
					campo.setAccessible(true);	
					
					if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(MuchosAUno.class) || campo.isAnnotationPresent(UnoAMuchos.class))
					{
						continue;
					}
					
					Columna columna = campo.getAnnotation(Columna.class);
										
					Method metodo = resultado.getClass().getMethod("get"+columna.tipoDato().getValor().getLlave(), String.class);
					metodo.setAccessible(true);
					Object resultadoResulset = metodo.invoke(resultado, columna.nombre());
					
					Utilidades.set(singular, campo.getName(), resultadoResulset);
					
				}
				resultadoSelect.add(singular);
			}	
			opcional = Optional.of(resultadoSelect);
			
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return opcional;
		}catch(Exception ex)
		{	
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex.getMessage());
		}finally
		{
			try
			{
				if(sentencia != null)
				{
					sentencia.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
	}//getEntidad
	
	/***************************************************************************************************************/
	@SuppressWarnings("resource")
	@Override
	public Object createDML(String sql, Class<?>... mapeo) throws PersistenciaException 
	{
		//Log
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		Connection conexion = null; 
		boolean esselect = false;
		Object[] arreglo = null;
		Statement statement = null;
		List<Object> listafinal =  new ArrayList<Object>();		
		String regexdigit = new String("(.+)\\d");
		String regexselect = new String("SELECT");
		boolean elmapeoescorrecto = false;
		
		try
		{
			if(mapeo != null && (mapeo.length > 1 ) || (mapeo.length > 0 && !mapeo[0].isAnnotationPresent(Tabla.class)))
			{
				throw new PersistenciaException("El parametro mapeo, debe tener solo una clase para su mapeo, se genera asi para que el atributo sea opcional o no es una entidad (Etiqueta tabla y sus etiquetas hijo)");
			}else if(mapeo.length == 0)
			{
				elmapeoescorrecto = false;
			}			
			else
			{
				elmapeoescorrecto = true;
			}

			Pattern patron = Pattern.compile(regexdigit);		
			Pattern patronselect = Pattern.compile(regexselect);
			Optional<Connection> conexionp = Configuracion.getInstancia().getConexion();
			if(!conexionp.isPresent() && !this.conexion.isPresent())
			{
				throw new PersistenciaException("No se ha podido obtener la conexion atraves de singleton de configuracion, ni de la conexion local de la Unidad de Persistencia, intente crear una conexion con la clase Conexion");
			}
			conexion = this.conexion.isPresent() ? this.conexion.get() : conexionp.get();
			statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				    ResultSet.CONCUR_READ_ONLY);
			
			Matcher matchselect = patronselect.matcher(sql);
			
			if(matchselect.find())//SI ES UN SELECT
			{
				esselect = true;
				ResultSet resultset = statement.executeQuery(sql);
						
				int contadoracolumnas = 1;
				int indexarreglo = 0;
				int numerocolumnas = resultset.getMetaData().getColumnCount();//Utilizado para crear el arreglo
				int numerofilas = 0;
				if(resultset.last())
				{
					numerofilas = resultset.getRow();
					resultset.beforeFirst();
				}
				arreglo = (Object[]) Array.newInstance(Object.class, (numerocolumnas * 2)*numerofilas);//*2 porque debemos encapsular tambien el nombre de la columna, y * numerofilas, para que encapsular si viene mas de una fila
				while(resultset.next())
				{
					numerocolumnas = resultset.getMetaData().getColumnCount();
					contadoracolumnas = 1;
					while(numerocolumnas > 0)
					{
						String columna = resultset.getMetaData().getColumnName(contadoracolumnas);//Columna actual												
						String clase = resultset.getMetaData().getColumnClassName(contadoracolumnas);//Java.lang.Integer, Java.lang.String
						String tipo = new String();
						String tipogeneral = new String();	
						Class<?> clazz = Class.forName(clase);//Me da la clase que debo acceder para el metodo get de la implementacion del ResultSet (getInt, getString... getObject) al ser esta la mas padre de todas, es de esta que intentamos sacar el primitivo
						try
						{
							Class<?> primitiva = (Class<?>) clazz.getField(Constantes.TYPE).get(null);////Accede al primitivo de la clase seleccionada (primitivo TYPE)
							tipo = primitiva.getSimpleName();
						}catch(NoSuchFieldException ex)
						{
							tipo = clazz.getSimpleName();
						}
				
						//Es posible que el valor tenga algun digito, se filtran para que solo se tome la parte alfanumerica (int4 -> int, String1 -> String) 
						Matcher matcher = patron.matcher(tipo);
						if(matcher.find())
						{
							tipogeneral = matcher.group(1);
						}else
						{
							tipogeneral = tipo;
						}
						//Se generan mayuscula a la primera letra (int -> Int)
						char[] tipoarray = tipogeneral.toCharArray();
						String caractermayus = (tipoarray[0] + "").toUpperCase();
						tipoarray[0] = caractermayus.charAt(0);
						String tipodatocorrecto = new String(tipoarray);
				
						arreglo[indexarreglo] = columna;
						indexarreglo++;
				
						Method metodo = resultset.getClass().getMethod("get"+tipodatocorrecto, String.class);
						metodo.setAccessible(true);
				
						Object resultadoreflexivo = metodo.invoke(resultset, columna);

						arreglo[indexarreglo] = resultadoreflexivo.toString();
						indexarreglo++;
						contadoracolumnas++;
						numerocolumnas--;
					}
				}
				
				//Mapeo al objeto
				if(elmapeoescorrecto)
				{
										
					Field[] campos = mapeo[0].getDeclaredFields();
					int numerocamposarreglo = arreglo.length / 2;
					
					int numerodecolumnasconsultadas = numerocamposarreglo - campos.length;
					if(numerodecolumnasconsultadas < 0)
					{
						throw new PersistenciaException("No es posible incluir en el objeto a mapear, una columna mas grande que el tamaño del total de sus atributos:  Campos en el select: "+numerocamposarreglo+" Campos en la clase: "+mapeo[0].getName()+ " : "+campos.length);
					}
					
					int i = 0;//Maneja el indice del arreglo IMPORTANTE FUERA DEL CICLO DE LAS FILAS
					while(numerofilas > 0)
					{
						Object instancia = mapeo[0].newInstance();
						Object valorsetear = new Object();						
						for(Field campo: campos)
						{
							campo.setAccessible(true);							
							Class<?> clas = campo.getType();
							Class<?> clasecasteo = null;
							if(clas.isPrimitive())
							{
								switch(clas.getName())
								{
									case "int":
										clasecasteo = Class.forName(Constantes.CLASE_ORDEN);
										break;
									default:
										
										break;
								}
							}else
							{
								clasecasteo = clas;
							}
							
							Method metodocasteo = null;
							
							try
							{							
								metodocasteo = clasecasteo.getMethod("valueOf", String.class);
							}catch(Exception ex)
							{
								try
								{
									metodocasteo = clasecasteo.getMethod("valueOf", Object.class);
								}catch(Exception e)
								{
									throw new PersistenciaException(e);
								}
							}
							metodocasteo.setAccessible(true);
							
							if(i % 2 != 0)//es impar
							{
								Object objetocast = arreglo[i];																
								valorsetear = metodocasteo.invoke(clasecasteo, objetocast);
								Utilidades.set(instancia, campo.getName(), valorsetear);
								i++;
							}else
							{						
								i++;						
								if(i % 2 != 0)//es impar
								{
									Object objetocast = arreglo[i];
									valorsetear = metodocasteo.invoke(clasecasteo, objetocast);
									Utilidades.set(instancia, campo.getName(), valorsetear);
									i++;
							
								}else
								{
									throw new PersistenciaException("Consulta invalida");
								}
							}
																		
						}
						
						listafinal.add(instancia);
						numerofilas--;				
					}	
				}
			}else
			{
				statement.executeUpdate(sql);
			}	
			// TODO Auto-generated method stub
			return esselect ? mapeo.length == 0 ? arreglo : listafinal: true;
			
		}catch(Exception ex)
		{
			super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, true, nombremetodo, ex.getMessage()));
			throw new PersistenciaException(ex);
		}finally
		{
			try
			{
				if(statement != null)
				{
					statement.close();
				}
				/*if(conexion != null)
				{					
					conexion.close();
				}*/
			}catch(SQLException ex)
			{
				throw new PersistenciaException("Error cerrando la conexion: "+ex.getMessage());
			}
		}
	}//createDML
	
	/**
	 * Metodo encargado de cerrar la conexion de la unidad de persistencia
	 * @throws PersistenciaException si hay error cerrando la conexion
	 */
	public void cerrarConexion() throws PersistenciaException
	{
		try
		{
			if(this.conexion.isPresent() && this.conexion != null)
			{
				this.conexion.get().close();
			}
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al cerrar la conexion: ERROR:"+ex.getMessage());
		}
	}//cerrarConexion
	
	/**
	 * Metodo encargado de confirmar una transaccion
	 * @throws PersistenciaException si hay error confirmando la transaccion
	 */
	public void cerrarTransaccion() throws PersistenciaException 
	{
		try
		{
			if(this.conexion.isPresent() && this.conexion != null)
			{
				this.conexion.get().commit();
			}
			
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al confirmar la transaccion: ERROR: "+ex.getMessage());
		}
		
	}//cerrarTransaccion

	/**
	 * Metodo encargado de realizar rollback o devolver una transaccion
	 * @throws PersistenciaException si hay error confirmando la transaccion
	 */
	public void rollbackTransaccion() throws PersistenciaException 
	{
		try
		{
			if(this.conexion.isPresent() && this.conexion != null)
			{
				this.conexion.get().rollback();
			}
			
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al confirmar la transaccion: ERROR: "+ex.getMessage());
		}
		
	}//rollbackTransaccion	
	
	
	
	
	/************************************************* GETTERS AND SETTERS **************************************************************/	
	
	/**
	 * 
	 * @return Retorna la conexion
	 */
	public Optional<Connection> getConexion() 
	{
		return conexion;
	}

	public void setConexion(Optional<Connection> conexion) 
	{
		this.conexion = conexion;
	}

	
}//No borrar