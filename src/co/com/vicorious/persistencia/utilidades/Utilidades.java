package co.com.vicorious.persistencia.utilidades;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.Funcion;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.MuchosAUno;
import co.com.vicorious.persistencia.anotaciones.Parametro;
import co.com.vicorious.persistencia.anotaciones.Procedimiento;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.anotaciones.UnoAMuchos;
import co.com.vicorious.persistencia.enums.TipoElementoDB;
import co.com.vicorious.persistencia.enums.TipoParametro;
import co.com.vicorious.persistencia.enums.TipoSecuenciaDB;
import co.com.vicorious.persistencia.enums.TiposBaseDatos;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.to.BaseDatosConfigTO;
import co.com.vicorious.persistencia.to.CredencialesTO;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesMensajes;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesSQL;

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
 * Encargado de gestionar las utilidades de SPL (Contiene la clase SQL que es la encargada de generar dinamicamente el SQL)
 * 
 * 
 *
 */
public abstract class Utilidades 
{
	/**
	 * Metodo encargado de setear dinamicamente los campos a el objeto entrante
	 * @param objeto objeto a modificar
	 * @param nombreCampo: nombre campo del objeto a modificar
	 * @param valorCampo: valor a modificar
	 * @return true si pudo, false si no
	 */
	public static boolean set(Object objeto, String nombreCampo, Object valorCampo)
	{
		Class<?> clazz = objeto.getClass();
		while(clazz != null)
		{
			try
			{
				Field campo = clazz.getDeclaredField(nombreCampo);
				campo.setAccessible(true);
				
				if(campo.getType().getSimpleName().equalsIgnoreCase("LocalDate"))
				{
					valorCampo = LocalDate.parse(valorCampo.toString());
				}else if(campo.getType().getSimpleName().equalsIgnoreCase("LocalDateTime"))
				{
					valorCampo = LocalDateTime.parse(valorCampo.toString());
				}
				
				campo.set(objeto, valorCampo);
				return true;
			}catch(NoSuchFieldException e)
			{
				clazz = clazz.getSuperclass();
			}catch(Exception e)
			{
				throw new IllegalStateException(e);
			}
		}
		return false;
	}//set
	
	/**
	 * Metodo encargado de retornar el valor del campo en el objeto
	 * @param objeto objeto a extraer
	 * @param nombreCampo: nombre del campo a extraer
	 * @param <V> clase a la que debemos obtener la referencia
	 * @return valor del campo extraido
	 */
	@SuppressWarnings("unchecked")
	public static <V> V get(Object objeto, String nombreCampo)
	{
		Class<?> clazz = objeto.getClass();
		while(clazz != null)
		{
			try
			{
				Field campo = clazz.getDeclaredField(nombreCampo);
				campo.setAccessible(true);
				return (V) campo.get(objeto);
			}catch(NoSuchFieldException e)
			{
				clazz = clazz.getSuperclass();
			}catch(Exception e)
			{
				throw new IllegalStateException(e);
			}
		}
		
		return null;
	}//get
	
	/**
	 * Set dinamico
	 * @param padre tipo de objeto a crear
	 * @param parametros: parametros a setear
	 * @return retorna el objeto con los parametros ya asignados
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Object set(Class<?> padre, String... parametros) throws PersistenciaException
	{
		Object instanciap = null;
		try
		{
			Object instancia = padre.newInstance();
			Field[] campos = padre.getDeclaredFields();
			int tamano = campos.length > parametros.length ? parametros.length : campos.length;
			IntStream.range(0, tamano).forEach( i -> 
			{
				Utilidades.set(instancia, campos[i].getName(), parametros[i]); 
			});
			
			instanciap = instancia;
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error en SET utilidades: "+ex.getMessage());
		}
		
		return instanciap;
		
	}//set
	
	/**
	 * Metodo encargado de asignar valor null a todos los atributos exceptuando el que tenga la etiqueta ID
	 * @param objeto objeto a modificar
	 * @return nuevo objeto modificado
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Object setNullId(Object objeto) throws PersistenciaException
	{
		Class<?> padre = objeto.getClass();
		try
		{
			List<Field> campos = Arrays.asList(padre.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(ID.class) != true).collect(Collectors.toList());
			int tamano = campos.size();
			IntStream.range(0, tamano).forEach( i -> 
			{
				Utilidades.set(objeto, campos.get(i).getName(), campos.get(i)); 
			});
			
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error en setNullId: "+ex.getMessage());
		}
		return objeto;
	}//setNullId
	
	/**
	 * Metodo encargado de darnos los tipos de objeto en la lista: DEBEN SER DEL MISMO TIPO (toma igualmente el primero)
	 * @param lista lista a observar
	 * @return: tipo de objeto de la lista
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public static Class<?> TipoObjectoLista(Collection<?> lista) throws PersistenciaException
	{
		Class<?> resultado = null;
		
		if(lista instanceof Collection<?> == false)
		{
			throw new PersistenciaException("El parametro debe ser una instancia de una lista (Collection) ");
		}
		
		for(Object objeto: lista)
		{
			resultado = objeto.getClass();
			break;
		}
		
		return resultado;
	}//TipoObjectoLista
	
	/**
	 * Capaz de castear un objeto a la clase parametrica
	 * @param clase: clase a castear
	 * @param <T> clase a la cual convertir nuestra instancia
	 * @param objeto: objeto a castear
	 * @return T: Type generic
	 */
	public static <T> T convertirInstanciaObjeto(Class<T> clase, Object objeto)
	{
		return clase.cast(objeto);
	}//convertirInstanciaObjeto
	
	/**
	 * Clase que encapsula el comportamiento del SQL Dinamico
	 */
	public abstract static class SQL
	{
		/**
		 * 
		 * @param basedatos: configuracion de la base de datos
		 * @param credenciales: credenciales para generar cade de conexion
		 * @return Cadena de conexion
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getCadenaConexionJDBC(BaseDatosConfigTO basedatos, CredencialesTO credenciales) throws PersistenciaException
		{
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
									
			StringBuilder resultado = new StringBuilder();
		
			resultado.append("jdbc:");
			resultado.append(basedatos.getJdbc());
			resultado.append(":");
			resultado.append("//");
			resultado.append(credenciales.getHost());
			resultado.append(":");
			resultado.append(credenciales.getPuerto());
			resultado.append("/");
			resultado.append(credenciales.getBasededatos());
			resultado.append("?");
			resultado.append("user");
			resultado.append("=");
			resultado.append(credenciales.getUsuario());
			resultado.append("&");
			resultado.append("password");
			resultado.append("=");
			resultado.append(credenciales.getContrasena());		
			
			//Log
			Logueable.logueo(resultado.toString(), ConstantesMensajes.MENSAJE_JDBC);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
			return resultado;
		}//getCadenaConexionJDBC
	
		/**
		 * Encargado de generar un select DINAMICAMENTE
		 * @param entidad: entidad a generar el select
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getSQL(Class<?> entidad) throws PersistenciaException
		{			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			StringBuilder sql = new StringBuilder(); 
			StringBuilder camposDB = new StringBuilder();
			
			if(!entidad.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			
			Tabla tabla = entidad.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_SELECT);
			for(Field campo: entidad.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				camposDB.append(columnaAnotacion.nombre());
				camposDB.append(",");												
			}
			
			camposDB.deleteCharAt(camposDB.length() - 1);
			//FROM
			sql.append(camposDB);
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);			
			
			return sql;
						
		}//getSQL
		
		/**
		 * Encargado de generar un select DINAMICAMENTE
		 * @param entidad: entidad a generar SELECT
		 * @param parametros: valores a asignar dinamicamente
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getSQL(Class<?> entidad, LinkedHashMap<String, Object> parametros)throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder();
			StringBuilder where = new StringBuilder();
			StringBuilder camposDB = new StringBuilder();
			boolean tienewhere = false;
			boolean esnulo = false;
			Optional<?> valor = null;
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidad.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			
			Tabla tabla = entidad.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_SELECT);
			
			for(Field campo: entidad.getDeclaredFields())
			{
				campo.setAccessible(true);
																			
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				try
				{
					valor = Optional.of(parametros.get(columnaAnotacion.nombre()));
					camposDB.append(columnaAnotacion.nombre());
					camposDB.append(",");
				}catch(NullPointerException ex)
				{

					camposDB.append(columnaAnotacion.nombre());
					camposDB.append(",");
					continue;
				}	
				
				if(valor.isPresent())
				{
					
					String formato = columnaAnotacion.tipoDato().getValor().getFormato();
											
					if(tienewhere == false)
					{
					   where.append(ConstantesSQL.SQL_WHERE);			
					   tienewhere = true;
					}else
					{
						where.append(ConstantesSQL.SQL_AND);				
					}
					
					where.append(columnaAnotacion.nombre());			
					where.append(esnulo ? " IS ":" = ");			
					where.append(formato).append(valor.get()).append(formato);
					
				}
																							
			}
			camposDB.deleteCharAt(camposDB.length() - 1);
			sql.append(camposDB);
			sql.append(ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
			
		}//getSQL
	
		/**
		 * Encargado de generar un select DINAMICAMENTE
		 * @param objetowhere: objeto a evaluar para construir la cadena
		 * @param columnasnowhere columnas que no deben ir en la sentencia WHERE
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getSQL(Object objetowhere, String... columnasnowhere) throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder(); 
			StringBuilder camposDB = new StringBuilder();
			StringBuilder where = new StringBuilder();
			Class<?> entidad = objetowhere.getClass();
			boolean tienewhere = false;
			boolean esnulo = false;
			HashMap<TipoElementoDB,String> defaults = null;
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidad.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			Tabla tabla = entidad.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_SELECT);
			for(Field campo: entidad.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				Optional<String> columnano =  Arrays.asList(columnasnowhere).stream().filter(p -> p.equalsIgnoreCase(columnaAnotacion.nombre())).findFirst();
				if(columnano.isPresent())
				{
					camposDB.append(columnaAnotacion.nombre());
					camposDB.append(",");
					continue;	
				}								
				
				Object valorObjeto = null;
				
				valorObjeto = get(objetowhere, campo.getName());
				
				
				
				String formato = columnaAnotacion.tipoDato().getValor().getFormato();

				
				if(valorObjeto == null)
				{
					esnulo = true;
					formato = "";
				}
				
				camposDB.append(columnaAnotacion.nombre());
				camposDB.append(",");
								
				if(Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo())
				{
					defaults = Configuracion.getInstancia().defaults();
					Optional<Entry<TipoElementoDB, String>> columnanodefault =  defaults.entrySet().stream().filter(p -> p.getKey() == columnaAnotacion.tipoDato()).findFirst();
					if(columnanodefault.isPresent())continue;
				}				 
												
				if(!tienewhere)
				{
				   where.append(Constantes.ConstantesSQL.SQL_WHERE);			
				   tienewhere = true;
				}else
				{
					where.append(ConstantesSQL.SQL_AND);				
				}
				
				where.append(columnaAnotacion.nombre());			
				where.append(esnulo ? ConstantesSQL.SQL_IS : " = ");			
				where.append(formato).append(valorObjeto).append(formato);					
																		
			}
			
			camposDB.deleteCharAt(camposDB.length() - 1);
			//FROM
			sql.append(camposDB);
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
			
			
		}//getSQL
		
		/**
		 * Encargado de generar un select DINAMICAMENTE
		 * @param objetowhere: Objeto a evaluar para generar el SQL
		 * @param defaults: mapa con los Defaults (Manuales)
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getSQL(Object objetowhere, HashMap<TipoElementoDB,String> defaults) throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder(); 
			StringBuilder camposDB = new StringBuilder();
			StringBuilder where = new StringBuilder();
			Class<?> entidad = objetowhere.getClass();
			boolean tienewhere = false;
			boolean esnulo = false;
			boolean llaveprimary = false;
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidad.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			Tabla tabla = entidad.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_SELECT);
			for(Field campo: entidad.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				Object valorObjeto = null;
				
				valorObjeto = get(objetowhere, campo.getName());

				String formato = columnaAnotacion.tipoDato().getValor().getFormato();

				
				if(valorObjeto == null)
				{
					esnulo = true;
					formato = "";
				}
				
				camposDB.append(columnaAnotacion.nombre());
				camposDB.append(",");
				//Preguntamos si encontramos en la columna actual un valor "DEFAULT"
				
				Optional<Entry<TipoElementoDB, String>> columnano =  defaults.entrySet().stream().filter(p -> p.getKey() == columnaAnotacion.tipoDato()).findFirst();
				
				if(llaveprimary || (columnano.isPresent() && (valorObjeto != null && valorObjeto.toString().equalsIgnoreCase(columnano.get().getValue()))))continue;
				if(!tienewhere)
				{
				   where.append(Constantes.ConstantesSQL.SQL_WHERE);			
				   tienewhere = true;
				}else
				{
					where.append(ConstantesSQL.SQL_AND);				
				}
				
				
				where.append(columnaAnotacion.nombre());			
				where.append(esnulo ? ConstantesSQL.SQL_IS : " = ");			
				where.append(formato).append(valorObjeto).append(formato);	
				
				//Preguntamos si e campo agregado en el where tiene primary key 
				if(campo.isAnnotationPresent(ID.class))
				{
					llaveprimary = true;
				}
																		
			}
			
			camposDB.deleteCharAt(camposDB.length() - 1);
			//FROM
			sql.append(camposDB);
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString() == null ? "No SQL": sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			
			return sql;
			
		}//getSQL
		
		/**
		 * Encargado de generar SQL dinamicamente
		 * @param objetowhere: objeto a evaluar
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getSQL(Object objetowhere) throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder(); 
			StringBuilder camposDB = new StringBuilder();
			StringBuilder where = new StringBuilder();
			Class<?> entidad = objetowhere.getClass();
			boolean tienewhere = false;
			boolean esnulo = false;
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidad.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidad.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			Tabla tabla = entidad.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_SELECT);
			for(Field campo: entidad.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				Object valorObjeto = null;
				
				valorObjeto = get(objetowhere, campo.getName());

				String formato = columnaAnotacion.tipoDato().getValor().getFormato();

				
				if(valorObjeto == null)
				{
					esnulo = true;
					formato = "";
				}
				
				camposDB.append(columnaAnotacion.nombre());
				camposDB.append(",");
				if(!tienewhere)
				{
				   where.append(Constantes.ConstantesSQL.SQL_WHERE);			
				   tienewhere = true;
				}else
				{
					where.append(ConstantesSQL.SQL_AND);				
				}
				
				
				where.append(columnaAnotacion.nombre());			
				where.append(esnulo ? ConstantesSQL.SQL_IS : " = ");			
				where.append(formato).append(valorObjeto).append(formato);					
																		
			}
			
			camposDB.deleteCharAt(camposDB.length() - 1);
			//FROM
			sql.append(camposDB);
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			
			return sql;
			
		}//getSQL
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente al DELETE de una entidad
		 * @param objetowhere: objeto a evaluar
		 * @param columnanowhere: columnas que no van en el where
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getDeleteSQL(Object objetowhere, String... columnanowhere) throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder(); 		
			StringBuilder where = new StringBuilder();
			boolean tienewhere = false;
			boolean esnulo = false;
			Class<?> entidadDelete = objetowhere.getClass();
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidadDelete.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidadDelete.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			
			Tabla tabla = entidadDelete.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_DELETE);
			for(Field campo: entidadDelete.getDeclaredFields())
			{
				campo.setAccessible(true);	
																			
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				Optional<String> columnano =  Arrays.asList(columnanowhere).stream().filter(p -> p.equalsIgnoreCase(columnaAnotacion.nombre())).findFirst();
				if(columnano.isPresent())continue;	
				
				Object valorObjeto = get(objetowhere, campo.getName());		
				String formato = columnaAnotacion.tipoDato().getValor().getFormato();
				
				if(valorObjeto == null)
				{
					esnulo = true;
					formato = "";
				}
				
				if(!tienewhere)
				{
				   where.append(Constantes.ConstantesSQL.SQL_WHERE);			
				   tienewhere = true;
				}else
				{
					where.append(ConstantesSQL.SQL_AND);				
				}
				
				where.append(columnaAnotacion.nombre());			
				where.append(esnulo ? " IS ": " = ");			
				where.append(formato).append(valorObjeto).append(formato);					
																		
			}
			
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
		
		}//getDeleteSql
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente al DELETE de una entidad
		 * @param objetowhere objeto a tratar
		 * @param defaults mapa con los Defaults (Manuales)
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getDeleteSQL(Object objetowhere,HashMap<TipoElementoDB,String> defaults) throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder(); 		
			StringBuilder where = new StringBuilder();
			boolean tienewhere = false;
			boolean esnulo = false;
			boolean tieneprimary = false;
			Class<?> entidadDelete = objetowhere.getClass();
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidadDelete.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidadDelete.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			
			Tabla tabla = entidadDelete.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_DELETE);
			

			
			for(Field campo: entidadDelete.getDeclaredFields())
			{
				campo.setAccessible(true);	
																			
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				Object valorObjeto = get(objetowhere, campo.getName());		
				String formato = columnaAnotacion.tipoDato().getValor().getFormato();
				
				//Preguntamos si encontramos en la columna actual un valor "DEFAULT"
				Optional<Entry<TipoElementoDB, String>> columnano =  defaults.entrySet().stream().filter(p -> p.getKey() == columnaAnotacion.tipoDato()).findFirst();
				
				if(columnano.isPresent() && valorObjeto.equals(columnano.get().getValue()))continue;
				
				
				if(valorObjeto == null)
				{
					esnulo = true;
					formato = "";
				}
				
				Optional<Field> id = Arrays.asList(entidadDelete.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(ID.class)).findAny();
				
				if(id.isPresent())
				{
					tieneprimary = true;
					where.append(columnaAnotacion.nombre());			
					where.append(esnulo ? " IS ": " = ");			
					where.append(formato).append(valorObjeto).append(formato);
				}
				
				if(tieneprimary)
				{
				
					if(!tienewhere)
					{
						where.append(Constantes.ConstantesSQL.SQL_WHERE);			
						tienewhere = true;
					}else
					{
						where.append(ConstantesSQL.SQL_AND);				
					}
				
					where.append(columnaAnotacion.nombre());			
					where.append(esnulo ? " IS ": " = ");			
					where.append(formato).append(valorObjeto).append(formato);
				}
																		
			}
			
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
			return sql;
			
		}//getDeleteSql
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente al DELETE de una entidad
		 * @param objetowhere: objeto a evaluar para armar la cadena
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getDeleteSQL(Object objetowhere) throws PersistenciaException
		{
			StringBuilder sql = new StringBuilder(); 		
			StringBuilder where = new StringBuilder();
			boolean tienewhere = false;
			boolean esnulo = false;
			Class<?> entidadDelete = objetowhere.getClass();
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(!entidadDelete.isAnnotationPresent(Tabla.class))
			{
				throw new PersistenciaException("La entidad "+entidadDelete.getSimpleName()+" debe implementar la anotacion Tabla");
			}
			
			Tabla tabla = entidadDelete.getAnnotation(Tabla.class);
			
			sql.append(Constantes.ConstantesSQL.SQL_DELETE);
			for(Field campo: entidadDelete.getDeclaredFields())
			{
				campo.setAccessible(true);	
																			
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columnaAnotacion = campo.getAnnotation(Columna.class);
				
				Object valorObjeto = get(objetowhere, campo.getName());		
				String formato = columnaAnotacion.tipoDato().getValor().getFormato();
				
				if(valorObjeto == null)
				{
					esnulo = true;
					formato = "";
				}
				
				if(!tienewhere)
				{
				   where.append(Constantes.ConstantesSQL.SQL_WHERE);			
				   tienewhere = true;
				}else
				{
					where.append(ConstantesSQL.SQL_AND);				
				}
				
				where.append(columnaAnotacion.nombre());			
				where.append(esnulo ? " IS ": " = ");			
				where.append(formato).append(valorObjeto).append(formato);					
																		
			}
			
			sql.append(Constantes.ConstantesSQL.SQL_FROM);
			sql.append(tabla.nombre());
			
			//WHERE
			sql.append(where.toString());
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
		
			return sql;
			
		}//getDeleteSql
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente al INSERT
		 * @param entidad: Entidad a tratar
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getInsertSQL(Object entidad) throws PersistenciaException
		{
			Class<?> clase = entidad.getClass();
			Tabla tabla = clase.getAnnotation(Tabla.class);		
			StringBuilder sql = new StringBuilder();
			StringBuilder valores = new StringBuilder();
			StringBuilder campos = new StringBuilder();
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
							
			if(tabla == null)
			{
				throw new PersistenciaException("El objeto debe pertenecer a una entidad con la etiqueta TABLA");
			}
			
			sql.append(ConstantesSQL.SQL_INSERT_INTO);
			sql.append(tabla.nombre());
			campos.append("(");
			valores.append("(");
			for(Field campo: clase.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}
				
				Columna columna = campo.getAnnotation(Columna.class);
				
				MuchosAUno muchos = campo.getAnnotation(MuchosAUno.class);
				
				Object resultado = Utilidades.get(entidad, campo.getName());
				
				//Preguntamos si encontramos en la columna actual un valor "DEFAULT"
				if(Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo() && !campo.isAnnotationPresent(ID.class))
				{
					Optional<Entry<TipoElementoDB, String>> columnano =  Configuracion.getInstancia().defaults().entrySet().stream().filter(p -> p.getKey() == columna.tipoDato() && resultado.toString().equalsIgnoreCase(p.getValue())).findFirst();	
					if(columnano.isPresent())
						continue;
				}								
							
				if(resultado == null && columna.notNull())
				{
					throw new PersistenciaException(" La columna: "+columna.nombre()+" es un campo obligatorio y debe ir con algun valor diferente a Null");
				}else if(campo.isAnnotationPresent(ID.class) && resultado != null)
				{
					ID id = campo.getAnnotation(ID.class);
					
					Object valor_secuencia = new String(); 
					
					if(id.secuencia() == null || id.secuencia().isEmpty())
					{
						valor_secuencia = Utilidades.get(entidad, campo.getName());
					}else
					{
						valor_secuencia = obtenerSecuenciaDBMS(id.secuencia());
					}
															
					campos.append(columna.nombre()).append(",");
					valores.append(columna.tipoDato().getValor().getFormato()).append(valor_secuencia).append(columna.tipoDato().getValor().getFormato()).append(",");
				}
				else if(resultado != null)
				{
					if(muchos != null)//Si el campo es muchos a uno, extraemos su llave
					{
						Class<?> clasepadre = resultado.getClass();
						Optional<Field> id = Arrays.asList(clasepadre.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(ID.class)).findFirst();
						
						if(id.isPresent())
						{
							Field idcampo = id.get();
							Object resultadomuchos = Utilidades.get(resultado, idcampo.getName());
							campos.append(columna.nombre()).append(",");
							valores.append(columna.tipoDato().getValor().getFormato()).append(resultadomuchos.toString()).append(columna.tipoDato().getValor().getFormato()).append(",");
						}else
						{
							throw new PersistenciaException("El objeto anidado MuchosAUno necesita tener un campo con ID, intente de nuevo");
						}
					}else
					{
						campos.append(columna.nombre()).append(",");
						valores.append(columna.tipoDato().getValor().getFormato()).append(resultado.toString()).append(columna.tipoDato().getValor().getFormato()).append(",");
					}
				}else
				{
					continue;
				}
																
			}	
			
			campos.deleteCharAt(campos.length() - 1);
			valores.deleteCharAt(valores.length() - 1);		
			
			campos.append(")");
			valores.append(")");
			
			sql.append(campos);
			sql.append(ConstantesSQL.SQL_VALUES);
			sql.append(valores);
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
			
		}//getInsertSQL
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente al INSERT
		 * @param entidad: Entidad a tratar
		 * @param defaults mapa con los Defaults (Manuales)
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getInsertSQL(Object entidad,HashMap<TipoElementoDB,String> defaults) throws PersistenciaException
		{
			Class<?> clase = entidad.getClass();
			Tabla tabla = clase.getAnnotation(Tabla.class);		
			StringBuilder sql = new StringBuilder();
			StringBuilder valores = new StringBuilder();
			StringBuilder campos = new StringBuilder();
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
							
			if(tabla == null)
			{
				throw new PersistenciaException("El objeto debe pertenecer a una entidad con la etiqueta TABLA");
			}
			
			sql.append(ConstantesSQL.SQL_INSERT_INTO);
			sql.append(tabla.nombre());
			campos.append("(");
			valores.append("(");
			for(Field campo: clase.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}			
				
				Columna columna = campo.getAnnotation(Columna.class);
				
				Object resultado = Utilidades.get(entidad, campo.getName());
				
				if(Configuracion.getInstancia().getConfiguracionframework().esDefaultBoo() && !campo.isAnnotationPresent(ID.class))
				{				
					//	Preguntamos si encontramos en la columna actual un valor "DEFAULT"
					Optional<Entry<TipoElementoDB, String>> columnano =  defaults.entrySet().stream().filter(p -> p.getKey() == columna.tipoDato()).findFirst();
					if(columnano.isPresent() && resultado.equals(columnano.get().getValue()))continue;					
				}
				
				
				if(resultado == null && columna.notNull())
				{
					throw new PersistenciaException(" La columna: "+columna.nombre()+" es un campo obligatorio y debe ir con algun valor diferente a Null");
					
				}else if(campo.isAnnotationPresent(ID.class) && resultado != null)
				{
					ID id = campo.getAnnotation(ID.class);
					
					String valor_secuencia = obtenerSecuenciaDBMS(id.secuencia());
					
					campos.append(columna.nombre()).append(",");
					valores.append(columna.tipoDato().getValor().getFormato()).append(valor_secuencia).append(columna.tipoDato().getValor().getFormato()).append(",");
				}
				else if(resultado != null)
				{
					campos.append(columna.nombre()).append(",");
					valores.append(columna.tipoDato().getValor().getFormato()).append(resultado.toString()).append(columna.tipoDato().getValor().getFormato()).append(",");
				}else
				{
					continue;
				}
																
			}	
			
			campos.deleteCharAt(campos.length() - 1);
			valores.deleteCharAt(valores.length() - 1);		
			
			campos.append(")");
			valores.append(")");
			
			sql.append(campos);
			sql.append(ConstantesSQL.SQL_VALUES);
			sql.append(valores);
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
			
		}//getInsertSQL
		
		/**
		 * Obtiene la secuencia dependiendo del JDBC
		 * @param secuencia: Nombre de la secuencia parametrizada en ID
		 * @return: Formato de secuencia completado
		 * @throws PersistenciaException
		 */
		private static String obtenerSecuenciaDBMS(String secuencia) throws PersistenciaException
		{			
			BaseDatosConfigTO elegida = Configuracion.getInstancia().getElegida();
			
			String secuencia_finalizada = new String();
			
			Optional<TiposBaseDatos> filtrada = Arrays.asList(TiposBaseDatos.values()).stream().filter(b -> b.getJDBC().equalsIgnoreCase(elegida.getJdbc())).findFirst();
			if(!filtrada.isPresent())
				throw new PersistenciaException("No existe una configuracion de base de datos (TipoBaseDatos) para el jdbc: "+elegida.getJdbc());
			
			Optional<TipoSecuenciaDB> formatosecuencia = Arrays.asList(TipoSecuenciaDB.values()).stream().filter(t -> t.getJdbc().equalsIgnoreCase(elegida.getJdbc())).findFirst();
			if(!formatosecuencia.isPresent())
				throw new PersistenciaException("No existe una configuracion tipo de secuencia (TipoSecuenciaDB) para el jdbc: "+elegida.getJdbc());
			
			TipoSecuenciaDB secuenciafinalizada = formatosecuencia.get();
			
			if(secuenciafinalizada.getTiposecuencia().equalsIgnoreCase(Constantes.FLAG_SECUENCIA_NORMAL))
			{
				secuencia_finalizada = secuencia + ConstantesSQL.SQL_NEXT_VAL;
				
			}else
			{			
				secuencia_finalizada = formatosecuencia.get().getTiposecuencia().replaceAll(Constantes.FORMATO_SECUENCIA, secuencia);
			}
			
			return secuencia_finalizada;
			
		}//obtenerSecuenciaDBMS
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente a UPDATE
		 * @param entidad: Entidad a tratar
		 * @param columnanowhere: Columnas que deben ir en el Where del UPDATE
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getUpdateSQL(Object entidad, String... columnanowhere) throws PersistenciaException
		{
			Class<?> clase = entidad.getClass();
			Tabla tabla = clase.getAnnotation(Tabla.class);		
			StringBuilder sql = new StringBuilder();
			StringBuilder campos = new StringBuilder();
			StringBuilder where = new StringBuilder();
			boolean tieneset = false;
			boolean tienewhere = false;
			boolean tieneprimary = false;
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
							
			if(tabla == null)
			{
				throw new PersistenciaException("El objeto debe pertenecer a una entidad con la etiqueta TABLA");
			}
			
			Optional<Field> opcional = Arrays.asList(entidad.getClass().getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(ID.class)).findFirst();
			
			if(opcional.isPresent())
			{
				Field campo = opcional.get();
				Object valor = Utilidades.get(entidad, campo.getName());
				Columna column = campo.getAnnotation(Columna.class);
				String formato = column.tipoDato().getValor().getFormato();
				where.append(ConstantesSQL.SQL_WHERE);
				where.append(column.nombre());
				where.append(" = ");
				where.append(formato).append(valor).append(formato);				
				tieneprimary = true;
			}
			sql.append(ConstantesSQL.SQL_UPDATE);		
			sql.append(tabla.nombre());		
			for(Field campo: clase.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}														
				Columna columna = campo.getAnnotation(Columna.class);
				
				Optional<String> columnano =  Arrays.asList(columnanowhere).stream().filter(p -> p.equalsIgnoreCase(columna.nombre())).findFirst();
				if(columnano.isPresent())continue;
				
				Object resultado = Utilidades.get(entidad, campo.getName());
							
				if(resultado != null)
				{
					String formato = columna.tipoDato().getValor().getFormato();
					if(!tieneset)
					{
						campos.append(ConstantesSQL.SQL_SET);						
						tieneset = true;
					}
					campos.append(columna.nombre());
					campos.append(" = ").append(formato).append(resultado.toString()).append(formato).append(",");
										
					if(!tieneprimary)
					{
						if(!tienewhere)
						{
							where.append(ConstantesSQL.SQL_WHERE);
						
						}else
						{
							where.append(ConstantesSQL.SQL_AND);
						}
						where.append(columna.nombre()).append("=").append(formato).append(resultado.toString()).append(formato);
						tienewhere = true;						
					}
					
				}else
				{
					continue;
				}
																
			}	
			
			if(!tieneset)
			{
				throw new PersistenciaException("El objeto debe venir con algun atributo diferente a nulo para agregar a la clausula SET");
			}
			
			campos.deleteCharAt(campos.length() - 1);	
					
			sql.append(campos);	
			sql.append(where);
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
			
		}//getUpdateSQL
		
		/**
		 * Encargado de generar una cadena SQL dinamica correspondiente a UPDATE
		 * @param entidad: entidad a tratar
		 * @param defaults: mapa con los Defaults (Manuales)
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getUpdateSQL(Object entidad,HashMap<TipoElementoDB,String> defaults) throws PersistenciaException
		{
			Class<?> clase = entidad.getClass();
			Tabla tabla = clase.getAnnotation(Tabla.class);		
			StringBuilder sql = new StringBuilder();
			StringBuilder campos = new StringBuilder();
			boolean tieneset = false;
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
							
			if(tabla == null)
			{
				throw new PersistenciaException("El objeto debe pertenecer a una entidad con la etiqueta TABLA");
			}
			
			sql.append(ConstantesSQL.SQL_UPDATE);
			sql.append(ConstantesSQL.SQL_FROM);		
			sql.append(tabla.nombre());		
			for(Field campo: clase.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				if(!campo.isAnnotationPresent(Columna.class) || campo.isAnnotationPresent(UnoAMuchos.class))
				{
					continue;
				}			
				
				Columna columna = campo.getAnnotation(Columna.class);
				
				Object resultado = Utilidades.get(entidad, campo.getName());
				//Preguntamos si encontramos en la columna actual un valor "DEFAULT"
				Optional<Entry<TipoElementoDB, String>> columnano =  defaults.entrySet().stream().filter(p -> p.getKey() == columna.tipoDato()).findFirst();
				
				if(columnano.isPresent() && resultado.equals(columnano.get().getValue()))continue;
							
				if(resultado != null)
				{
					campos.append(ConstantesSQL.SQL_SET).append(columna.nombre()).
					append(" = ").append(columna.tipoDato().getValor().getFormato()).					
					append(resultado.toString()).append(columna.tipoDato().getValor().getFormato()).append(",");
					
				}else
				{
					continue;
				}
																
			}	
			
			if(!tieneset)
			{
				throw new PersistenciaException("El objeto debe venir con algun atributo diferente a nulo para agregar a la clausula SET");
			}
			
			campos.deleteCharAt(campos.length() - 1);	
					
			sql.append(campos);		
			
			//Log
			Logueable.logueo(sql.toString(), ConstantesMensajes.MENSAJE_SQL);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return sql;
			
		}//getUpdateSQL
		
		/**
		 * Encargado de generar un callable dinamico para la entidad procedimiento
		 * @param entidadProcedimiento: objeto a tratar
		 * @return Callable con la llamada del procedimiento
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getCallableProcedimiento(Class<?> entidadProcedimiento) throws PersistenciaException
		{
			StringBuilder llamada = new StringBuilder();
			StringBuilder parametros = new StringBuilder();
			llamada.append("{call ");
			Procedimiento procedimiento = entidadProcedimiento.getAnnotation(Procedimiento.class);
			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			if(procedimiento == null)
			{
				throw new PersistenciaException("La entidad debe tener la etiqueta Procedimiento");
			}
			
			llamada.append(procedimiento.nombre());
			llamada.append("(");
			
			for(Field campo: entidadProcedimiento.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				Parametro parametro = campo.getAnnotation(Parametro.class);
				
				if(parametro == null)
				{
					continue;
				}
				
				parametros.append("?").append(",");
			}
			
			parametros.deleteCharAt(parametros.length() - 1);		
			llamada.append(parametros);
			llamada.append(")}");
			
			//Log
			Logueable.logueo(llamada.toString(), ConstantesMensajes.MENSAJE_CALLABLE);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return llamada;
			
		}//getCallableProcedimiento
		
		/**
		 * Encargado de generar el callable para la funcion dinamicamente
		 * @param entidadFuncion: entidad a tratar
		 * @return Cadena SQL
		 * @throws PersistenciaException encapsula cualquier error generado por JAVA
		 */
		public static StringBuilder getCallableFunction(Class<?> entidadFuncion) throws PersistenciaException
		{			
			//Log
			String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_INICIO_METODO);
			
			StringBuilder llamada = new StringBuilder();
			StringBuilder parametros = new StringBuilder();
			llamada.append("{? = call ");
			
			if(!entidadFuncion.isAnnotationPresent(Funcion.class))
			{
				throw new PersistenciaException("La entidad debe tener la etiqueta Funcion");
			}
			Funcion funcion = entidadFuncion.getAnnotation(Funcion.class);
			
			llamada.append(funcion.nombre());
			llamada.append("(");
			
			for(Field campo: entidadFuncion.getDeclaredFields())
			{
				campo.setAccessible(true);
				
				Parametro parametro = campo.getAnnotation(Parametro.class);
				
				if(parametro == null || parametro.tipoparametro() == TipoParametro.OUT)
				{
					continue;
				}
				
				parametros.append("?").append(",");
			}
			
			parametros.deleteCharAt(parametros.length() - 1);		
			llamada.append(parametros);
			llamada.append(")}");
			
			//Log
			Logueable.logueo(llamada.toString(), ConstantesMensajes.MENSAJE_CALLABLE);
			Logueable.logueo(nombremetodo, ConstantesMensajes.MENSAJE_FINAL_METODO);
			
			return llamada;
			
		}//getCallableFunction
		
		/**
		 * Retorna columnas por entidad
		 * @param entidad: objeto a extraer las columnas
		 * @return Listado con las columnas de la entidad
		 */
		public static List<Columna> getColumnas(Class<?> entidad)
		{
			List<Columna> columnas = new ArrayList<Columna>();
			 Arrays.asList(entidad.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(Columna.class)).forEach(h ->
			 {
					Columna a = h.getAnnotation(Columna.class);
					columnas.add(a);
			 });
			 return columnas;
		}//getColumnas
		
		/**
		 * Retorna columna por entidad y por nombre
		 * @param entidad: Objeto para extraer la columna
		 * @param nombrecolumna: Nombre de la columna a tratar
		 * @return Opcional con la columna
		 */
		public static Optional<Columna> getColumna(Class<?> entidad, String nombrecolumna)
		{
			 Optional<Columna> columna = null;
			 Optional<Field> campop = Arrays.asList(entidad.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(Columna.class)).filter(f -> f.getName().equalsIgnoreCase(nombrecolumna)).findFirst();
			 if(campop.isPresent())
			 {
				 Field campo = campop.get();
				 Columna columnap = campo.getAnnotation(Columna.class);
				 columna = Optional.of(columnap);
			 }
			 return columna;
		}//getColumnas
		
		/**
		 * Encargado de retornar las columnas por tipo de elemento de la base de datos (Las columnas solo varchar, solo Integer... etc)
		 * @param entidad: Objeto a extraer las columnas
		 * @param tipodato: Tipo de dato para filtrar las columnas
		 * @return Listado de columnas resultado
		 */
		public static List<Columna> getColumnas(Class<?> entidad, TipoElementoDB tipodato)
		{
			List<Columna> columnas = new ArrayList<Columna>();
			Stream<Field> campop = Arrays.asList(entidad.getDeclaredFields()).stream().filter(p -> p.isAnnotationPresent(Columna.class));
			campop.forEach(c -> 
			{
				Columna co = c.getAnnotation(Columna.class);
				if(co.tipoDato().equals(tipodato))
					columnas.add(co);
				
			});
			
			return columnas;
		}//getColumnas
	
	}//SQL CLASE No borrar
	
		
}//No borrar
