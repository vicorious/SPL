package co.com.vicorious.persistencia.utilidades;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import co.com.vicorious.persistencia.enums.TipoElemento;
import co.com.vicorious.persistencia.enums.TipoElementoDB;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.to.BaseDatosConfigTO;
import co.com.vicorious.persistencia.to.ConfiguracionTO;
import co.com.vicorious.persistencia.to.CredencialesTO;
import co.com.vicorious.persistencia.to.MensajeTO;
import co.com.vicorious.persistencia.to.XmlTO;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesArchivos;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesMensajes;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesRegex;
import co.com.vicorious.persistencia.xml.ConfiguracionXML;
import co.com.vicorious.persistencia.xml.ConstructorXML;
import co.com.vicorious.persistencia.xml.DefaultsXML;
import co.com.vicorious.persistencia.xml.MensajesXML;
import co.com.vicorious.persistencia.xml.TiposDBXML;

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
 * Singleton encargado de obtener los datos de configuracion de SPL
 * 
 * 
 * 
 *
 */
public class Configuracion 
{
	private static Configuracion 			instancia;
	private List<BaseDatosConfigTO> 		resultado;
	private List<MensajeTO> 				mensajes;
	private CredencialesTO 					credencialesTO;
	private BaseDatosConfigTO 				configuracion_base_datos_elegida;
	private Optional<Connection> 			conexion;
	private HashMap<TipoElementoDB, String> defaults;
	private ConfiguracionTO 				configuracionframework;
	private boolean 						esDefault;
	private StringBuilder 					logprogramatico;
	private int 							contadorlog;
	private boolean							espathmanual;
	private String							pathmanualcredenciales;
	
	/**
	 * Encargado de darnos la instancia unica del singleton siempre
	 * @return instancia unica
	 * @throws PersistenciaException si genera un error anidado JAVA
	 */
	public synchronized static Configuracion getInstancia() throws PersistenciaException
	{
		if(instancia == null)
		{
			instancia = new Configuracion();
			return instancia;
			
		}else
		{
			return instancia;
			
		}
	}//getInstancia
	
	/**
	 * Encargado de darnos la instancia unica del singleton siempre
	 * @param credencialespath: ruta absoluta
	 * @param archivoconfiguracionpath: ruta absoluta
	 * @param mensajespath: ruta absoluta
	 * @param defaultpath: ruta absoluta
	 * @param tipodbpath: ruta absoluta
	 * @return instancia unica
	 * @throws PersistenciaException si genera un error anidado JAVA
	 */
	public synchronized static Configuracion getInstancia(String credencialespath, String archivoconfiguracionpath, String mensajespath, String defaultpath, String tipodbpath) throws PersistenciaException
	{
		if(instancia == null)
		{
			instancia = new Configuracion(credencialespath, archivoconfiguracionpath, mensajespath, defaultpath, tipodbpath);
			return instancia;
			
		}else
		{
			return instancia;
			
		}
	}//getInstancia
	
	/**
	 * Evita que la clase sea clonada
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}//clone
	
	/**
	 * 
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	private Configuracion(String...paths) throws PersistenciaException
	{
		this.logprogramatico = new StringBuilder();
		if(this.resultado == null)
		{
			this.resultado = new ArrayList<BaseDatosConfigTO>();
		}
		String nombreclase = this.getClass().getName();
		try 
		{ 	
			//Generamos archivos de configuracion del framework estaticos
			try
			{
				generarArchivosDeConfiguracion();
			}catch(Exception ex)
			{
				throw new PersistenciaException("Error al generar archivos de configuracion: ERROR:"+ex.getMessage());
			}
			if(paths != null && paths.length > 0)
			{
				if(paths != null && paths.length != 5)
				{
					throw new PersistenciaException("Deben venir los paths para Credenciales, configuracion, defaults, mensajes y tiposdb, en este momento tenemos: "+paths.length+" parametros");
				}
				this.espathmanual = true;//Nos ayuda a gestionar las credenciales de manera transparente
				this.pathmanualcredenciales = paths[0]; //Encapsulamos el path de las credenciales
				initParametros(paths);				
			}else
			{
				initDefault();
			}
			
			this.agregarLog(getMensaje(ConstantesMensajes.MENSAJE_FIN_CLASE, false, nombreclase));
			
		} catch (PersistenciaException e) 
		{
			//Log
			this.agregarLog(getMensaje(ConstantesMensajes.MENSAJE_CLASE_ERRONEO, false, nombreclase, e.getMessage()));
			throw new PersistenciaException(e);
		}
		
	}//Constructor
	
	/**
	 * Inicializacion por Default
	 * @throws PersistenciaException Default
	 */
	private void initDefault() throws PersistenciaException
	{		
		configuracion();
		//Se agregan porque son el primer metodo de ejecucion despues de configuracion (Solo se puede Loguear cuando el metodo de configuracion sea ejecutado)
		this.agregarLog("Empezo la clase "+this.getClass().getName()+" correctamente");
		this.agregarLog("configuracion inicial realizada correctamente");
		configuracionesBaseDeDatos();
		mensajes();
		defaults();
		
	}//initDefault
	
	/**
	 * Inicializacion por parametros
	 * @param paths
	 * @throws PersistenciaException Default
	 */
	private void initParametros(String...paths) throws PersistenciaException
	{
		//La primera posicion paths[0] corresponde al Path de la credenciales, por eso tomamos desde la segunda posición
		String pathconfiguracion = paths[1];
		String pathmensajes = paths[2];
		String pathdefault = paths[3];
		String pathtipodb = paths[4];
				
		configuracion(pathconfiguracion);
		//Se agregan porque son el primer metodo de ejecucion despues de configuracion (Solo se puede Loguear cuando el metodo de configuracion sea ejecutado)
		this.agregarLog("Empezo la clase "+this.getClass().getName()+" correctamente");
		this.agregarLog("configuracion inicial realizada correctamente");
		configuracionesBaseDeDatos(pathtipodb);
		mensajes(pathmensajes);
		defaults(pathdefault);	
		
	}//initParametros
	
	/**
	 * Encargado de generar los archivos de configuracion del framework
	 * @throws PersistenciaException
	 */
	private void generarArchivosDeConfiguracion() throws PersistenciaException
	{
		String pathconfiguracion = ConstantesArchivos.ARCHIVO_CONFIGURACION;
		String pathmensajes = ConstantesArchivos.ARCHIVO_MENSAJE;
		String pathdefaults = ConstantesArchivos.ARCHIVO_DEFAULTS;
		String pathtiposdb = ConstantesArchivos.ARCHIVO_TIPO_DB;
		String pathlogprogramatico = ConstantesArchivos.ARCHIVO_LOG;
		String pathcredenciales = ConstantesArchivos.ARCHIVO_CREDENCIALES_PROPERTIES;
		boolean yaexisten = false;
		try
		{
			File carpeta = new File(ConstantesArchivos.CARPETA_CONFIGURACION);
			carpeta.mkdir();
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al generar la carpeta de configuracion: porfavor revisar permisos: ERROR: "+ex.getMessage());
		}
						
		File configuraciones = new File(pathconfiguracion);
		File mensajes = new File(pathmensajes);
		File defaults = new File(pathdefaults);
		File tiposdb = new File(pathtiposdb);
		File logprogramatico = new File(pathlogprogramatico);
		File credenciales = new File(pathcredenciales);
		
		try
		{
			if(!configuraciones.exists())
			{
				yaexisten = true;
				configuraciones.createNewFile();
			}
			if(!mensajes.exists())
			{
				mensajes.createNewFile();
			}
			if(!defaults.exists())
			{
				defaults.createNewFile();
			}
			if(!tiposdb.exists())
			{
				tiposdb.createNewFile();
			}
			if(!logprogramatico.exists())
			{
				logprogramatico.createNewFile();
			}
			if(!credenciales.exists())
			{
				credenciales.createNewFile();
			}
			
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al crear los archivos, por favor verificar, ERROR: "+ex.getMessage());
		}	
		
		//Llenamos los archivos
		
		XmlTO xml = ConstructorXML.generarXMLTO();	
		FileWriter escritorcredenciales = null;
		try
		{					
			JAXBContext configuracion = JAXBContext.newInstance(ConfiguracionXML.class);			
			Marshaller configuracionmar = configuracion.createMarshaller();
			configuracionmar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			JAXBContext defaultsjax = JAXBContext.newInstance(DefaultsXML.class);			
			Marshaller defaultmar = defaultsjax.createMarshaller();
			defaultmar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			JAXBContext mensajesjax = JAXBContext.newInstance(MensajesXML.class);			
			Marshaller mensajesmar = mensajesjax.createMarshaller();
			mensajesmar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			JAXBContext tipodbjax = JAXBContext.newInstance(TiposDBXML.class);
			Marshaller tipodbmar = tipodbjax.createMarshaller();
			tipodbmar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			if(getConfiguracionframework() == null ? yaexisten : false)
			{
				configuracionmar.marshal(xml.getConfiguracion_xml(), configuraciones);
				defaultmar.marshal(xml.getDefaults_xml(), defaults);
				mensajesmar.marshal(xml.getMensajes_xml(), mensajes);
				tipodbmar.marshal(xml.getTiposdb_xml(), tiposdb);
				escritorcredenciales = new FileWriter(credenciales);
				escritorcredenciales.write(xml.getCredenciales_properties());	
			}																	
		}catch(Exception ex)
		{
			throw new PersistenciaException("Error al escribir la informacion en los archivos: ERROR: "+ex.getMessage());
		}finally
		{
			try
			{
				escritorcredenciales.close();
			}catch(Exception ex)
			{
				throw new PersistenciaException("Error al cerrar los archivos de escritura: ERROR: "+ex.getMessage());
			}
		}
		
	}//generarArchivosDeConfiguracion
		
	/**
	 * Encargado de retornar a nivel de objeto las configuraciones del archivo XML (tiposdb)
	 * @return lista de tipo BaseDatosConfigTO
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 * 
	 */
	public List<BaseDatosConfigTO> configuracionesBaseDeDatos() throws PersistenciaException
	{
		boolean archivocorrecto = false;
		StringBuilder items = null;
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		try
		{			
			//Log
			this.agregarLog("Inicio el metodo: "+nombremetodo+" Correctamente");		
			Archivo archivConfiguracionDB = new Archivo(ConstantesArchivos.ARCHIVO_TIPO_DB);	
			StringBuilder contenido = archivConfiguracionDB.getInformacionArchivoTextoPlano();				
			
			Pattern patrona = Pattern.compile(ConstantesRegex.REGEX_ITEM);
			Matcher matchitems = patrona.matcher(contenido.toString());
			
			if(!matchitems.find())
			{
				throw new PersistenciaException("El archivo con nombre: "+ConstantesArchivos.ARCHIVO_TIPO_DB+" se encuentra en estado invalido o su estructura no corresponde a la esperada");
			}else
			{
				items = new StringBuilder(matchitems.group(2));
			}

			Pattern patron = Pattern.compile(ConstantesRegex.REGEX_ATRIBUTOS_REGEX);
			Matcher match = patron.matcher(items.toString());
		
			while(match.find())
			{
				BaseDatosConfigTO objeto = new BaseDatosConfigTO();
				String itemActual = match.group();
				Pattern patronitem = Pattern.compile(ConstantesRegex.REGEX_HIJOS_ITEM);
				Matcher matchItem = patronitem.matcher(itemActual);
			
				while(matchItem.find())
				{	
					archivocorrecto = true;
					String hijoactual = matchItem.group(1);
					String valorhijoactual = matchItem.group(2);
				
					for(Field campo: BaseDatosConfigTO.class.getDeclaredFields())
					{
						campo.setAccessible(true);
					
						if(campo.getName().equalsIgnoreCase(hijoactual))
						{
							Utilidades.set(objeto, campo.getName(), valorhijoactual);
							break;
						}
					}
																
				}
				if(objeto !=null)
					resultado.add(objeto);
			}
			
			if(!archivocorrecto)
			{
				throw new PersistenciaException("El archivo con nombre: "+ConstantesArchivos.ARCHIVO_TIPO_DB+" se encuentra en estado invalido o su estructura no corresponde a la esperada");
			}			
			
			//Log
			this.agregarLog("Termino el metodo: "+nombremetodo+" Correctamente");
			
			return resultado;
			
		}catch(Exception e)
		{
			//Log
			this.agregarLog("Error al consultar tipos de base de datos: Error: "+e.getMessage());
			throw new PersistenciaException(e);
		}
				
	}//configuracionesBaseDeDatos
	
	/**
	 * Metodo encargado de la configuracion del framework (configuracion.xml)
	 * @return
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	private Optional<ConfiguracionTO> configuracion(String...path) throws PersistenciaException
	{
		//Log
		//this.agregarLog(new MensajeTO("Comenzo configuracion correctamente"));
		ConfiguracionTO configuracion = new ConfiguracionTO();
		String contenido_archivo_sin_etiquetaspadre = new String();	
		Archivo archivoconfiguracion = null;
		
		try
		{
			if(path != null && path.length > 1)
			{
				throw new PersistenciaException("Solo debe recibir un parametro como minimo y maximo (un solo path), porfavor intente de nuevo");
			}else if(path != null && path.length == 1)
			{
				archivoconfiguracion =  new Archivo(path[0]);
			}else
			{
				archivoconfiguracion =  new Archivo(ConstantesArchivos.ARCHIVO_CONFIGURACION);
			}
									
			StringBuilder configuracionplano = archivoconfiguracion.getInformacionArchivoTextoPlano();
			
			Pattern patronsinpadres = Pattern.compile(ConstantesRegex.REGEX_CONFIGURACION);
			Matcher matchsinpadres = patronsinpadres.matcher(configuracionplano.toString());
			
			if(matchsinpadres.find())
			{
				contenido_archivo_sin_etiquetaspadre =  matchsinpadres.group(1);
			}
			
			Pattern patronnumero = Pattern.compile(ConstantesRegex.REGEX_CONTENIDO_ARCHIVO_CONF);
			Matcher matchnumero = patronnumero.matcher(contenido_archivo_sin_etiquetaspadre);
			
			while(matchnumero.find())
			{
				String etiqueta = matchnumero.group(1);
				String valor = matchnumero.group(2);
				Utilidades.set(configuracion, etiqueta, valor);
			}//llena el objeto dinamicamente	
			
			this.setConfiguracionframework(configuracion);	
			
			//Log
			//this.agregarLog(new MensajeTO("Termino configuracion correctamente"));
			
			return Optional.of(configuracion);
			
			
		}catch(Exception ex)
		{	
			throw new PersistenciaException(ex);
		}
		
		
	}//configuracion
	
	/**
	 * Retorna listado con todos los mensajes del archivo
	 * @return Listado con todos los mensajes de SPL (archivo mensajes.xml)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	private List<MensajeTO> mensajes(String...path) throws PersistenciaException
	{
		this.agregarLog("Comenzo el metodo mensajes correctamente");
		List<MensajeTO> mensajes = new ArrayList<MensajeTO>();
		Archivo archivomensajes = null;
		if(resultado == null)
		{
			throw new PersistenciaException("Problemas cargando el archivo tiposdb.xml, porfavor intente de nuevo y verifique que la ruta se encuentre en estado valido");
		}		
		
		try
		{
			if(path != null && path.length > 1)
			{
				throw new PersistenciaException("Solo debe recibir un parametro como minimo y maximo (un solo path), porfavor intente de nuevo");
			}else if(path != null && path.length == 1)
			{
				archivomensajes =  new Archivo(path[0]);
			}else
			{
				archivomensajes = new Archivo(ConstantesArchivos.ARCHIVO_MENSAJE);
			}
			 
			StringBuilder mensajetext = archivomensajes.getInformacionArchivoTextoPlano();
			
			Pattern patronmensaje = Pattern.compile(ConstantesRegex.REGEX_MENSAJE);
			Matcher matchmensaje = patronmensaje.matcher(mensajetext.toString());
			
			while(matchmensaje.find())
			{
				MensajeTO mensaje = new MensajeTO();
				String codigo = matchmensaje.group(1);
				String valor = matchmensaje.group(2);				
				
				Utilidades.set(mensaje, "codigo", codigo);
				Utilidades.set(mensaje, "mensaje", valor);
				
				mensajes.add(mensaje);				
			}
			
			this.setMensajes(mensajes);
			this.agregarLog("Termino el metodo mensajes correctamente");
			return mensajes;
			
		}catch(Exception ex)
		{			
			throw new PersistenciaException(ex);
		}
	}//mensajes

		
	/**
	 * Encargado de consultar un mensaje en el singleton con los parametros definidos
	 * @param codigo: codigo del mensaje (codigo="finclaseerroneo")
	 * @param parametros: parametro1, parametro2... parametron
	 * @param tieneerror: si el mensaje contiene un error a reemplazar
	 * @return retorna el mensaje correspondiente
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public synchronized MensajeTO getMensaje(String codigo, boolean tieneerror, String... parametros) throws PersistenciaException
	{
		MensajeTO mensajefiltrado = null;
		if(this.getMensajes() == null)
		{
			throw new PersistenciaException("Contextualizar el metodo mensajes() del singleton Configuracion");
		}
		Optional<MensajeTO> mensaje = this.getMensajes().stream().filter(p -> p.getCodigo().equalsIgnoreCase(codigo)).findFirst();	
		if(!mensaje.isPresent())
		{
			throw new PersistenciaException("El mensaje con el codigo: "+codigo+" No existe: porfavor intente nuevo");
		}
		
		mensajefiltrado = mensaje.get();
		String valor = mensajefiltrado.getMensaje();
		
		if(parametros != null)
		{			
			if(tieneerror)
			{				
				for(int i = 0; i < parametros.length; i++)
				{
					String parametro = parametros[i];
					valor = valor.replaceAll("/error"+i+"/",parametro);
				}
			}else
			{				
				for(int i = 0; i < parametros.length; i++)
				{
					String parametro = parametros[i];
					valor = valor.replaceAll("\\?"+i,parametro);
				}
			}
						
		}
		
		//mensajefiltrado.setMensaje(valor);
		
		return new MensajeTO(mensaje.get().getCodigo(),valor);
		
	}//getMensaje
	
	/**
	 * Metodo encargado de retornar configuracion unica mediante un filtro (Toma el primero que encuentre con las caracteristicas)
	 * @param predicado: filtro lambda
	 * @return objeto BaseDatosConfigTo
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<BaseDatosConfigTO> elegirConfiguracionFiltro(Predicate<BaseDatosConfigTO> predicado) throws PersistenciaException
	{
		if(resultado == null)
		{
			throw new PersistenciaException("Problemas cargando el archivo tiposdb.xml, porfavor intente de nuevo y verifique que la ruta se encuentre en estado valido");
		}
		BaseDatosConfigTO db = new BaseDatosConfigTO();
		
		Optional<?> resulta = resultado.stream().filter(predicado).findFirst();
		if(!resulta.isPresent())
		{
			throw new PersistenciaException("No se encuentra la base de datos: "+predicado+" Intente de nuevo teniendo como referencia el archivo: "+ConstantesArchivos.ARCHIVO_TIPO_DB);
		}
		setElegida(resulta.isPresent() ? (BaseDatosConfigTO)resulta.get() :null);
		
		db = resulta.isPresent() ? (BaseDatosConfigTO)resulta.get():null;						
		
		Optional<BaseDatosConfigTO> op = Optional.of(db);
		
		return op;
		
	}//elegirConfiguracionFiltro
	
	/**
	 * Metodo encargado de tomar otro archivo como el de credenciales (credenciales.properties por Default)
	 * @param credencialesarchivo: clase con la etiqueta PropiertiesConfig
	 * @return objeto CredencialesTO con la informacion respectiva
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public CredencialesTO aplicarArchivoCredenciales(Class<?> credencialesarchivo) throws PersistenciaException
	{
		if(resultado == null)
		{
			throw new PersistenciaException("Problemas cargando el archivo tiposdb.xml, porfavor intente de nuevo y verifique que la ruta se encuentre en estado valido");
		}
		Archivo archivo = new Archivo(credencialesarchivo);
		this.setCredencialesTO((CredencialesTO) archivo.getGetArchivoProperties());
		return this.credencialesTO;
	}//aplicarArchivoCredenciales
	
	/**
	 * Metodo encargado de tomar otro archivo ara las configuraciones de base de datos(tiposdb.xml)
	 * @param rutaxml: ruta absoluta donde se encuentra el archivo con la estructura tipo XML
	 * @return lista de objetos tipo BaseDatosConfigTO
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public List<BaseDatosConfigTO> configuracionesBaseDeDatos(String rutaxml) throws PersistenciaException
	{
		if(resultado == null)
		{
			throw new PersistenciaException("Problemas cargando el archivo tiposdb.xml, porfavor intente de nuevo y verifique que la ruta se encuentre en estado valido");
		}
		boolean archivocorrecto = false;
		StringBuilder items = null;
		try
		{
			Archivo archivConfiguracionDB = new Archivo(rutaxml);
			resultado = new ArrayList<BaseDatosConfigTO>();		
			StringBuilder contenido = archivConfiguracionDB.getInformacionArchivoTextoPlano();				
			
			Pattern patrona = Pattern.compile(ConstantesRegex.REGEX_ITEM);
			Matcher matchitems = patrona.matcher(contenido.toString());
			
			if(!matchitems.find())
			{
				throw new PersistenciaException("El archivo con nombre: "+ConstantesArchivos.ARCHIVO_TIPO_DB+" se encuentra en estado invalido o su estructura no corresponde a la esperada");
			}else
			{
				items = new StringBuilder(matchitems.group(2));
			}

			Pattern patron = Pattern.compile(ConstantesRegex.REGEX_ATRIBUTOS_REGEX);
			Matcher match = patron.matcher(items.toString());
		
			while(match.find())
			{
				BaseDatosConfigTO objeto = new BaseDatosConfigTO();
				String itemActual = match.group();
				Pattern patronitem = Pattern.compile(ConstantesRegex.REGEX_HIJOS_ITEM);
				Matcher matchItem = patronitem.matcher(itemActual);
			
				while(matchItem.find())
				{	
					archivocorrecto = true;
					String hijoactual = matchItem.group(1);
					String valorhijoactual = matchItem.group(2);
				
					for(Field campo: BaseDatosConfigTO.class.getDeclaredFields())
					{
						campo.setAccessible(true);
					
						if(campo.getName().equalsIgnoreCase(hijoactual))
						{
							Utilidades.set(objeto, campo.getName(), valorhijoactual);
							break;
						}
					}
																
				}
				if(objeto !=null)
				resultado.add(objeto);
			}
			
			if(!archivocorrecto)
			{
				throw new PersistenciaException("El archivo con nombre: "+ConstantesArchivos.ARCHIVO_TIPO_DB+" se encuentra en estado invalido o su estructura no corresponde a la esperada");
			}
			
			return resultado;
		}catch(Exception e)
		{
			throw new PersistenciaException(e);
		}
	}//configuracionesBaseDeDatos
	
	/**
	 * Metodo encargado de entregarnos las credenciales por defecto 
	 * @return objeto de tipo CredencialesTO con las credenciales DEFAULT (Archivo credenciales.properties)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public CredencialesTO credencialesPorDefecto() throws PersistenciaException
	{	
		Archivo archivo = null;
		if(resultado == null)
		{
			throw new PersistenciaException("Problemas cargando el archivo tiposdb.xml, porfavor intente de nuevo y verifique que la ruta se encuentre en estado valido");
		}
		if(this.espathmanual)
		{
			archivo = new Archivo(this.pathmanualcredenciales, CredencialesTO.class);
		}else
		{
			archivo = new Archivo(CredencialesTO.class);
		}
		this.setCredencialesTO((CredencialesTO) archivo.getGetArchivoProperties());
		return this.getCredencialesTO();
	}//credencialesPorDefecto
	
	/**
	 * Metodo encargado de entregarnos las credenciales por defecto 
	 * @param credenciales: Objeto con las credenciales contextualizadas
	 * @return objeto de tipo CredencialesTO con las credenciales DEFAULT (Archivo credenciales.properties)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public CredencialesTO credencialesPorDefecto(CredencialesTO credenciales) throws PersistenciaException
	{	
		Archivo archivo = null;
		if(resultado == null)
		{
			throw new PersistenciaException("Problemas cargando el archivo tiposdb.xml, porfavor intente de nuevo y verifique que la ruta se encuentre en estado valido");
		}
		if(this.espathmanual)
		{
			archivo = new Archivo(this.pathmanualcredenciales, CredencialesTO.class);
		}else
		{
			archivo = new Archivo(credenciales);
		}
		this.setCredencialesTO((CredencialesTO) archivo.getGetArchivoProperties());
		return this.getCredencialesTO();
	}//credencialesPorDefecto
		
	
	/**
	 * Metodo encargado de tomar del archivo DEFAULTS.xml los valores por default que se pueden manejar en la aplicacion
	 * @param path para contextualizar el archivo default.xml
	 * @return Mapa con los defaults del archivo DEFAULTS.xml
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public HashMap<TipoElementoDB, String> defaults(String...path) throws PersistenciaException
	{		
		this.esDefault = true;
		String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		HashMap<TipoElementoDB, String> resultado = new HashMap<TipoElementoDB, String>();
		String tipodatoactual = new String();
		String valor = new String();
		Archivo archivConfiguracionDB = null;
		try
		{
			//Log
			this.agregarLog(getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
			//Creamos el archivo XML
			if(path != null && path.length > 1)
			{
				throw new PersistenciaException("Solo debe recibir un parametro como minimo y maximo (un solo path), porfavor intente de nuevo");
			}else if(path != null && path.length == 1)
			{
				archivConfiguracionDB =  new Archivo(path[0]);
			}else
			{
				archivConfiguracionDB = new Archivo(ConstantesArchivos.ARCHIVO_DEFAULTS);
			}			
			StringBuilder contenido = archivConfiguracionDB.getInformacionArchivoTextoPlano();	
			Pattern patron = Pattern.compile(ConstantesRegex.REGEX_DEFAULTS, Pattern.DOTALL);
			Matcher match = patron.matcher(contenido.toString());
			
			if(match.find())
			{
				String contenidodefaults = match.group(1);
				Pattern patrondefault = Pattern.compile(ConstantesRegex.REGEX_DEFAULT, Pattern.DOTALL);
				Matcher matchdefault = patrondefault.matcher(contenidodefaults);
				
				//Default
				while(matchdefault.find())
				{
					String contenidodefault = matchdefault.group(1);
					Pattern patronhijos = Pattern.compile(ConstantesRegex.REGEX_TIPO_DATO_DEFAULT);
					Matcher matchtipodato = patronhijos.matcher(contenidodefault);
					
					//Tipo dato
					while(matchtipodato.find())
					{
						tipodatoactual = matchtipodato.group(1);
						
					}
					patronhijos = Pattern.compile(ConstantesRegex.REGEX_VALOR_DEFAULT);
					Matcher matchvalor = patronhijos.matcher(contenidodefault);
					
					//Valor
					while(matchvalor.find())
					{
						valor = matchvalor.group(1);
						
					}
					
					TipoElemento elemento = TipoElemento.valueOf(tipodatoactual);
					
					if(elemento == null)
					{
						throw new PersistenciaException("La etiqueta default con el tipo de dato = "+tipodatoactual+" Es invalida como TipoElemento (enum), porfavor valide que exista: "+tipodatoactual+" Como un enum de tipo (TipoElemento)");
					}
					
					HashMap<TipoElementoDB,String> hashportipodato = this.getElementoPorHijo(tipodatoactual, valor);
					
					resultado.putAll(hashportipodato);
					
				}
				
			}
					
		this.setDefaults(resultado);
		//Log
		this.agregarLog(getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
		
		return resultado;
		
		}catch(Exception ex)
		{
			//Log
			this.agregarLog(getMensaje(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO, false, nombremetodo, ex.getMessage()));
			throw new PersistenciaException("Error al cargar el mapa de los DEFAULTS: "+ex.getMessage());	
		}
		
	}//defaults
	
	/**
	 * Agrega lineas al archivo log_programatico.txt
	 * @param mensaje mensaje a agregar en el log estatico
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public synchronized void agregarLog(MensajeTO mensaje) throws PersistenciaException
	{	
		if(this.getConfiguracionframework() == null)
		{
			throw new PersistenciaException("Contextualicemos el singleton Configuracion primero");
		}
		if(this.getConfiguracionframework().esLogBoo())
		{
			//Fecha				
			LocalDateTime ldt = LocalDateTime.now();			
			
			getLogprogramatico().append(ldt.toString());
			getLogprogramatico().append("-------");
			getLogprogramatico().append(mensaje.getMensaje());
			getLogprogramatico().append(Constantes.ENTER);
			
			if(this.getConfiguracionframework().tieneLimiteLog())//Si existe la palabra
			{				
				if(getContadorlog() ==  this.getConfiguracionframework().getLogLimiteInt())
				{
					Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG, getLogprogramatico().toString(), true);
					archivo.escribirArchivo(true);
					setContadorlog(0);
				}else
				{					
					this.setContadorlog(getContadorlog() + 1);
				}
			}else
			{
				Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG, getLogprogramatico().toString(), true);
				archivo.escribirArchivo(true);
			}
		}
		
	}//agregarLog
	
	/**
	 * Encargado de agregar lineas al archivo log_programatico.txt
	 * @param mensaje literal a agregar
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public synchronized void agregarLog(String mensaje) throws PersistenciaException
	{
		if(this.getConfiguracionframework().esLogBoo())
		{
			//Fecha				
			LocalDateTime ldt = LocalDateTime.now();			
			
			getLogprogramatico().append(ldt.toString());
			getLogprogramatico().append("-------");
			getLogprogramatico().append(mensaje);
			getLogprogramatico().append(Constantes.ENTER);
			
			if(this.getConfiguracionframework().tieneLimiteLog())//Si existe la palabra
			{				
				if(getContadorlog() ==  this.getConfiguracionframework().getLogLimiteInt())
				{
					Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG, getLogprogramatico().toString());
					archivo.escribirArchivo();
					setContadorlog(0);
				}else
				{					
					this.setContadorlog(getContadorlog() + 1);
				}
			}else
			{
				Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG, getLogprogramatico().toString());
				archivo.escribirArchivo();
			}
		}
	}//agregarLogSimple
	
	/**
	 * Encargado de escribir el log manualmente
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public void escribirLogManualmente() throws PersistenciaException
	{
		Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG, getLogprogramatico().toString());
		archivo.escribirArchivo();		
		
	}//escribirLogManualmente
	
	/**
	 * Encargado de borrar el log manualmente
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public void borrarLogManualmente() throws PersistenciaException
	{
		Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG);
		archivo.borrarContenidoArchivo();
		
	}//borrarLogManualmente
	
	/**
	 * Encargado de retornar la informacion del log (log_programatico.txt) en un literal (StringBuilder)
	 * @return StirngBuilder con la informacion plana del log
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public StringBuilder getLiteralLog() throws PersistenciaException
	{

		Archivo archivo = new Archivo(ConstantesArchivos.ARCHIVO_LOG);
		return archivo.getInformacionArchivoTextoPlano();		
	}//getLiteralLog
	
	/**
	 * Metodo encargado de obtener el elemento (TipoElementoDB) apartir de TiopoElemento
	 * @param hijo: Hijo a tratar
	 * @param defaultvalor: valor a poner siempre al objeto hijo anidado
	 * @return mapa con elementos por hijo del archivo Defaults.xml
	 */
	private HashMap<TipoElementoDB,String> getElementoPorHijo(String hijo, String defaultvalor)
	{
		List<Field> instanciaspropias = Arrays.asList(TipoElementoDB.class.getDeclaredFields()).stream().filter(c -> c.getType().getName().equalsIgnoreCase(TipoElementoDB.class.getName())).collect(Collectors.toList());
		TipoElementoDB elemento = null;
		HashMap<TipoElementoDB,String> resultado = new HashMap<TipoElementoDB,String>();
		
		for(Field campo: instanciaspropias)
		{
			campo.setAccessible(true);
			for(Field campoaninado: campo.getType().getDeclaredFields())
			{
				campoaninado.setAccessible(true);
				if(campoaninado.getType().getName().equalsIgnoreCase(TipoElemento.class.getName()) && campo.getName().equalsIgnoreCase(hijo))
				{
					elemento = TipoElementoDB.valueOf(campo.getName());
					resultado.put(elemento, defaultvalor);
	
				}
			}
		}
		
		return resultado;
	}//getElementoPorHijo
	
	/**
	 * 
	 * 
	 * GETTERS AND SETTERS
	 * 
	 * 
	 * 
	 */
	/**
	 * 
	 * @return atributo elegida
	 */
	public BaseDatosConfigTO getElegida() 
	{
		return configuracion_base_datos_elegida;
	}

	/**
	 * Setter
	 * @param elegida: elegida a asignar
	 */
	public void setElegida(BaseDatosConfigTO elegida) 
	{
		this.configuracion_base_datos_elegida = elegida;
	}

	/**
	 * Getter
	 * @return: retorna la configuracion de las bases de datos
	 */
	public List<BaseDatosConfigTO> getResultado() 
	{
		return resultado;
	}

	/**
	 * Setter
	 * @param resultado: asigna configuracion de las bases de datos
	 */
	public void setResultado(List<BaseDatosConfigTO> resultado) 
	{
		this.resultado = resultado;
	}

	/**
	 * Getter
	 * @return: retorna credenciales
	 */
	public CredencialesTO getCredencialesTO() 
	{
		return credencialesTO;
	}

	/**
	 * Setter
	 * @param credencialesTO: credenciales a asignar
	 */
	public void setCredencialesTO(CredencialesTO credencialesTO) 
	{
		this.credencialesTO = credencialesTO;
	}

	/**
	 * Getter
	 * @return: conexion actual de TO
	 */
	public Optional<Connection> getConexion() 
	{
		return conexion;
	}

	/**
	 * Setter
	 * @param conexion: conexion a asignar
	 */
	public void setConexion(Optional<Connection> conexion) 
	{
		this.conexion = conexion;
	}

	/**
	 * Getter
	 * @return: log programatico de la aplicacion
	 */
	public StringBuilder getLogprogramatico() 
	{
		return logprogramatico;
	}

	/**
	 * Setter
	 * @param logprogramatico: log programatico a asignar
	 */
	public void setLogprogramatico(StringBuilder logprogramatico) 
	{
		this.logprogramatico = logprogramatico;
	}

	/**
	 * Getter
	 * @return: defaults del framework
	 */
	public HashMap<TipoElementoDB, String> getDefaults() 
	{
		return defaults;
	}

	/**
	 * Setter
	 * @param defaults: defaults a asignar
	 */
	public void setDefaults(HashMap<TipoElementoDB, String> defaults) 
	{
		this.defaults = defaults;
	}

	/**
	 * Getter
	 * @return: si manejamos o no defaults en el framework
	 */
	public boolean isEsDefault() 
	{
		return esDefault;
	}

	/**
	 * Setter
	 * @param esDefault: esdefault a asignar
	 */
	public void setEsDefault(boolean esDefault) 
	{
		this.esDefault = esDefault;
	}

	/**
	 * Getter
	 * @return: configuracion del framework
	 */
	public ConfiguracionTO getConfiguracionframework() 
	{
		return configuracionframework;
	}

	/**
	 * Setter
	 * @param configuracionframework: configuracion del framework a asignar
	 */
	public void setConfiguracionframework(ConfiguracionTO configuracionframework) 
	{
		this.configuracionframework = configuracionframework;
	}

	/**
	 * Getter
	 * @return: lista de mensajes del framework
	 */
	public List<MensajeTO> getMensajes() 
	{
		return mensajes;
	}

	/**
	 * Setter
	 * @param mensajes: mensajes a asignar
	 */
	public void setMensajes(List<MensajeTO> mensajes) 
	{
		this.mensajes = mensajes;
	}

	/**
	 * Getter
	 * @return: contador si el log tiene un limite
	 */
	public int getContadorlog() 
	{
		return contadorlog;
	}

	/**
	 * Setter
	 * @param contadorlog: contadorlog a asignar
	 */
	public void setContadorlog(int contadorlog) 
	{
		this.contadorlog = contadorlog;
	}		
			
}//No borrar
