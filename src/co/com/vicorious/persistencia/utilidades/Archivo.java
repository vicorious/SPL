package co.com.vicorious.persistencia.utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import co.com.vicorious.persistencia.anotaciones.PropertiesConfig;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.to.ArchivoTO;

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
 *   
 * Manager encargado de gestionar el comportamiento de los archivos de SPL (Deberias usar esta clase para manejar las instancias de tus archivos)
 * 
 * 
 *
 */
public class Archivo extends Logueable
{
	private File 		archivo;
	private String 		rutaArchivo;	
	private Object 		getArchivoProperties;
	private FileWriter 	log;	
	private ArchivoTO 	archivoTo;
	
	public Archivo(String ruta)
	{
		this.rutaArchivo = ruta;
		this.archivoTo = new ArchivoTO(ruta);
		
	}//Constructor
	
	/**
	 * Constructor utilizado para crear un archivo logica properties
	 * @param rutaProperties: path del archivo
	 * @param claseMapeo: clase a la que se debe mapear
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */ 
	@SuppressWarnings("unused")
	public Archivo(String rutaProperties, Class<?> claseMapeo) throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		this.rutaArchivo = rutaProperties;
		this.archivo = new File(rutaProperties);
		this.archivoTo = new ArchivoTO(rutaProperties);
		
		Properties archivo = new Properties();
		
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream(rutaProperties);

		
		try
		{
			
			if(inputStream == null)
			{
				inputStream = new FileInputStream(rutaProperties);
			}
		
			if(inputStream != null)
			{
				archivo.load(inputStream);
				
				Object objeto = claseMapeo.newInstance();
				
				for(Field campo: claseMapeo.getDeclaredFields())
				{
					campo.setAccessible(true);
					
					if(!campo.isAnnotationPresent(co.com.vicorious.persistencia.anotaciones.Properties.class))
					{
						continue;
					}
					
					co.com.vicorious.persistencia.anotaciones.Properties properties = campo.getAnnotation(co.com.vicorious.persistencia.anotaciones.Properties.class);
					
					Object valor = archivo.getProperty(properties.llave());
					
					Utilidades.set(objeto, campo.getName(), valor);					 
				}
				
				setGetArchivoProperties(objeto);
				
			}else
			{
				throw new PersistenciaException("No existe el archivo: "+rutaProperties);
			}
			
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex.getMessage());
		}
	}//Archivo	
	
	/**
	 * Encargado de contextualizar en un objeto un properties 
	 * @param claseProperties clase que encapsula la informacion del properties
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	@SuppressWarnings("unused")
	public Archivo(Class<?> claseProperties) throws PersistenciaException
	{
		//Log
		/*String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));*/
		
		PropertiesConfig rutaProperties = claseProperties.getAnnotation(PropertiesConfig.class);
		
		if(rutaProperties == null)
		{
			throw new PersistenciaException("La clase debe contener la etiqueta PropertiesNombre con la ruta del archivo Properties");
		}
		
		this.archivo = new File(rutaProperties.ruta());		
		this.archivoTo = new ArchivoTO(rutaProperties.ruta());
		Properties archivo = new Properties();		
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream(rutaProperties.ruta());
		
		try
		{
			if(inputStream == null)
			{
				inputStream = new FileInputStream(rutaProperties.ruta());
			}
		
			if(inputStream != null)
			{
				archivo.load(inputStream);
				
				Object objeto = claseProperties.newInstance();
				
				for(Field campo: claseProperties.getDeclaredFields())
				{
					campo.setAccessible(true);
					
					if(!campo.isAnnotationPresent(co.com.vicorious.persistencia.anotaciones.Properties.class))
					{
						continue;
					}
					
					co.com.vicorious.persistencia.anotaciones.Properties properties = campo.getAnnotation(co.com.vicorious.persistencia.anotaciones.Properties.class);
					
					Utilidades.set(objeto, campo.getName(), archivo.get(properties.llave()));					 
				}
				
				setGetArchivoProperties(objeto);
				
			}else
			{
				throw new PersistenciaException("No existe el archivo: "+rutaProperties.ruta());
			}
			
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex.getMessage());
		}
		
	}//Archivo
	
	/**
	 * Metodo encargado de generar el archivo CREDENCIALES.properties con los valores que contenga el parametro (objetoproerties)
	 * @param objetoproperties: Objeto con los parametro definidos para el archivo credenciales.properties
	 * @throws PersistenciaException error de persistencia general
	 */
	@SuppressWarnings("unused")
	public Archivo(Object objetoproperties) throws PersistenciaException
	{		
		Class<?> claseProperties = objetoproperties.getClass();
		
		FileOutputStream fr = null;
		
		PropertiesConfig rutaProperties = claseProperties.getAnnotation(PropertiesConfig.class);
		
		if(rutaProperties == null)
		{
			throw new PersistenciaException("La clase debe contener la etiqueta PropertiesNombre con la ruta del archivo Properties");
		}
		
		this.archivo = new File(rutaProperties.ruta());		
		this.archivoTo = new ArchivoTO(rutaProperties.ruta());
		Properties archivo = new Properties();		
		InputStream inputStream =  getClass().getClassLoader().getResourceAsStream(rutaProperties.ruta());
		
		try
		{
			if(inputStream == null)
			{
				inputStream = new FileInputStream(rutaProperties.ruta());
			}
		
			if(inputStream != null)
			{
				archivo.load(inputStream);
				
				Object objeto = claseProperties.newInstance();
				
				for(Field campo: claseProperties.getDeclaredFields())
				{
					campo.setAccessible(true);
					
					if(!campo.isAnnotationPresent(co.com.vicorious.persistencia.anotaciones.Properties.class))
					{
						continue;
					}
					
					co.com.vicorious.persistencia.anotaciones.Properties properties = campo.getAnnotation(co.com.vicorious.persistencia.anotaciones.Properties.class);
					
					archivo.put(campo.getName(), Utilidades.get(objetoproperties, campo.getName()));
														
				}
				
				File archivosobreescrito = new File(rutaProperties.ruta());
				fr=new FileOutputStream(archivosobreescrito);
				
				archivo.store(fr, "credenciales.properties");
				fr.close();
				
				setGetArchivoProperties(objetoproperties);
				
			}else
			{
				throw new PersistenciaException("No existe el archivo: "+rutaProperties.ruta());
			}
			
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex.getMessage());
		}finally
		{
			try
			{
				if(fr != null)
					fr.close();
			}catch(Exception ex)
			{
				throw new PersistenciaException("Error al cerrar el archivo para sobreescribir el properties: ERROR: "+ex.getMessage());
			}
			
		}
		
	}//Archivo
	
	/**
	 * Encargado de agregarle a un archivo informacion extra
	 * @param ruta: path del archivo
	 * @param informacion: contenido informacion a agregar
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Archivo(String ruta, String informacion) throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		try
		{
			if(ruta == null)
			{
				throw new PersistenciaException("La ruta del archivo es nula o invalida, intente de nuevo por favor ");
			}
			
			if(informacion == null)
			{
				throw new PersistenciaException("La informacion a escribir en el archivo: "+ruta+" Es nula o invalida, porfavor intente con informacion correcta he intente de nuevo ");
			}
			this.archivoTo = new ArchivoTO(ruta, informacion);
			this.rutaArchivo = ruta;	
			
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{			
			throw new PersistenciaException(ex);
		}
	}//Archivo
	
	/**
	 * Encargado de agregarle a un archivo informacion extra(Log)
	 * @param ruta: Path del archivo
	 * @param informacion: contenido informacion a agregar
	 * @param eslog: Si el archivo corresponde a el log o no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Archivo(String ruta, String informacion, boolean eslog) throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		try
		{
			if(ruta == null)
			{
				throw new PersistenciaException("La ruta del archivo es nula o invalida, intente de nuevo por favor ");
			}
			
			if(informacion == null)
			{
				throw new PersistenciaException("La informacion a escribir en el archivo: "+ruta+" Es nula o invalida, porfavor intente con informacion correcta he intente de nuevo ");
			}
			this.archivoTo = new ArchivoTO(ruta, informacion);
			this.rutaArchivo = ruta;
			if(eslog)
			{
				this.log = new FileWriter(ruta, true);
			}
			
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{			
			throw new PersistenciaException(ex);
		}
	}//Archivo
	

	/**
	 * Encargado de escribir un archivo en su equivalencia
	 * @param eslog: describe si el archivo a escribir es el log
	 * @return true si pudo, false si no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean escribirArchivo(boolean eslog) throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		try
		{
			if(eslog && this.log == null)
			{
				throw new PersistenciaException("El objeto log se encuentra en un estado invalido, porfavor verificar la previa creacion del objeto archivo, he intente de nuevo (Use el contructor  Archivo(String ruta, String informacion))");
			}
			
			if(this.getArchivoTo() == null)
			{
				throw new PersistenciaException("El objeto archivoTO se encuentra en un estado invalido, porfavor verificar la previa creacion del objeto archivo, he intente de nuevo (Use el contructor  Archivo(String ruta, String informacion))");
			}
			
			if(eslog)
			{	
				this.log.write(this.getArchivoTo().getContenido());
				this.log.close();				
			}else
			{
				FileWriter escritor = new FileWriter(this.getArchivoTo().getRuta());
				escritor.write("");
				escritor.close();
			}
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			return true;
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex);
		}
	}//escribirArchivo
	
	/**
	 * Encargado de escribir el archivo actual
	 * @return true si pudo, false si no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean escribirArchivo() throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		try
		{
			if(this.getArchivoTo() == null)
			{
				throw new PersistenciaException("El objeto archivoTO se encuentra en un estado invalido, porfavor verificar la previa creacion del objeto archivo, he intente de nuevo (Use el contructor  Archivo(String ruta, String informacion))");
			}
			
			FileWriter escritor = new FileWriter(this.getArchivoTo().getRuta());
			escritor.write(this.getArchivoTo().getContenido());
			escritor.close();
			
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
			return true;
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex);
		}
	}//escribirArchivo
	
	/**
	 * Encargado borrar el contenido del archivo en la ruta
	 * @param ruta: path del archivo
	 * @return true si pudo, false si no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean borrarContenidoArchivo(String ruta) throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		try
		{
			FileWriter writer = new FileWriter(ruta);
			writer.write(Constantes.VACIO);
			writer.close();
			
			return true;
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex);
		}
	}
	
	/**
	 * Borra el contenido del archivo actual
	 * @return true si pudo, false si no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean borrarContenidoArchivo() throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		try
		{
			if(this.getArchivoTo() == null)
			{
				throw new PersistenciaException("El objeto archivoTO se encuentra en un estado invalido, porfavor verificar la previa creacion del objeto archivo, he intente de nuevo (Use el contructor  Archivo(String ruta, String informacion))");
			}
			FileWriter writer = new FileWriter(this.getArchivoTo().getRuta());
			writer.write(Constantes.VACIO);
			writer.close();
			
			return true;
			//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(Exception ex)
		{
			throw new PersistenciaException(ex);
		}
	}
	
	/**
	 * Metodo encargado de retornar la informacion del archivo (texto plano).
	 * @return Archivo en texto plano (StringBuilder)
	 * @throws PersistenciaException  encapsula cualquier error generado por JAVA
	 */
	public StringBuilder getInformacionArchivoTextoPlano() throws PersistenciaException
	{			
		//Log
		/*String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));*/
		
		StringBuilder contenido = new StringBuilder();
		
		try
		{			
			if(this.getArchivoTo() == null)
			{
				throw new PersistenciaException("El objeto archivoTO se encuentra en un estado invalido, porfavor verificar la previa creacion del objeto archivo, he intente de nuevo (Use el contructor  Archivo(String ruta, String informacion))");
			}
			this.archivo = new File(this.rutaArchivo);
			try(BufferedReader bufferLectura = new BufferedReader(new FileReader(this.rutaArchivo)))
			{
				String linea = bufferLectura.readLine();
				while(linea != null)
				{
					contenido.append(linea);
					linea = bufferLectura.readLine();
				}
			}
		
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(IOException ex)
		{
			throw new PersistenciaException(ex);
		}
		
		return contenido;
		
	}//getInformacionArchivo
	
	/**
	 * Metodo encargado de retornar la informacion del archivo (texto plano).
	 * @param ruta: ruta del archivo a obtener su literal
	 * @return Literal con la informacion plana del archivo (StringBuilder)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA 
	 */
	public StringBuilder getInformacionArchivoTextoPlano(String ruta) throws PersistenciaException
	{
		//Log
		//String nombremetodo = new Object(){}.getClass().getEnclosingMethod().getName();	
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_INICIO_METODO, false, nombremetodo));
		
		StringBuilder contenido = new StringBuilder();
		
		try
		{		
			this.archivo = new File(ruta);			
			try(BufferedReader bufferLectura = new BufferedReader(new FileReader(ruta)))
			{
				String linea = bufferLectura.readLine();
				while(linea != null)
				{
					contenido.append(linea);
					linea = bufferLectura.readLine();
				}
			}
			
		//super.loguear(super.getMensaje(ConstantesMensajes.MENSAJE_FINAL_METODO, false, nombremetodo));
			
		}catch(IOException ex)
		{
			throw new PersistenciaException(ex);
		}
		
		return contenido;
		
	}//getInformacionArchivo
	

	/**
	 * Getter
	 * @return: log del archivo
	 */
	public FileWriter getLog() 
	{
		return log;
	}

	/**
	 * Setter
	 * @param log: log a asignar
	 */
	public void setLog(FileWriter log) 
	{
		this.log = log;
	}

	/**
	 * Getter
	 * @return archivoTo del TO
	 */
	public ArchivoTO getArchivoTo() 
	{
		return archivoTo;
	}

	/**
	 * Setter
	 * @param archivoTo: archivoTO a asignar
	 */
	public void setArchivoTo(ArchivoTO archivoTo) 
	{
		this.archivoTo = archivoTo;
	}	
		
	/**
	 * Getter
	 * @return: ruta del archivo del TO
	 */
	public String getRutaArchivo() 
	{
		return rutaArchivo;
	}

	/**
	 * Setter
	 * @param rutaArchivo: ruta del archivo a asignar
	 */
	public void setRutaArchivo(String rutaArchivo) 
	{
		this.rutaArchivo = rutaArchivo;
	}

	/**
	 * Getter
	 * @return: archivo del TO
	 */
	public File getArchivo() 
	{
		return archivo;
	}

	/**
	 * Setter
	 * @param archivo: archivo a asignar
	 */
	public void setArchivo(File archivo) 
	{
		this.archivo = archivo;
	}
			
	/**
	 * Getter
	 * @return: archivo de propiedades del TO 
	 */
	public Object getGetArchivoProperties() 
	{
		return getArchivoProperties;
	}

	/**
	 * Setter
	 * @param getArchivoProperties: archivo properties a asignar
	 */
	public void setGetArchivoProperties(Object getArchivoProperties) 
	{
		this.getArchivoProperties = getArchivoProperties;
	}
		
}//No borrar
