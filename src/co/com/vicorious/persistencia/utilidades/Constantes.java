package co.com.vicorious.persistencia.utilidades;


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
 * Encargado de encapsular todas las instancias no mutables(Constantes) en SPL
 * 
 * 
 * 
 *
 */
public abstract class Constantes 
{
	public static final String TYPE = 					 "TYPE";
	public static final String NEGACION = 				 "NO";
	public static final String CLASE_ORDEN = 			 "java.lang.Integer";
	public static final int VECES_LIMITE_ESCRIBE_LOG = 	 10;
	public static final String ENTER = 					 "\n";
	public static final String ESPACIO = 				 " ";
	public static final String VACIO = 					 "";
	public static final String SEPARADOR_ARCHIVOS =		 "/";
	public static final String FORMATO_SECUENCIA = 		"_secuencia_";
	public static final String FLAG_SECUENCIA_NORMAL = 	"NORMAL";
	
	//Defaults
	public static final String DEFAULT_INTEGER = "INTEGER";
	public static final String DEFAULT_INTEGER_VALOR = "0";
	
	public static final String DEFAULT_DOUBLE = "DECIMAL";
	public static final String DEFAULT_DOUBLE_VALOR = "0.0";
	/**
	 * 
	 * 
	 *
	 */
	public static class ConstantesArchivos
	{
		public static final String CARPETA_CONFIGURACION =			"/ArchivosSPL";
		public static final String ARCHIVO_TIPO_DB = 				ConstantesArchivos.CARPETA_CONFIGURACION+Constantes.SEPARADOR_ARCHIVOS+"tiposdb.xml";
		public static final String ARCHIVO_CREDENCIALES_PROPERTIES =ConstantesArchivos.CARPETA_CONFIGURACION+Constantes.SEPARADOR_ARCHIVOS+"credenciales.properties";
		public static final String ARCHIVO_DEFAULTS = 				ConstantesArchivos.CARPETA_CONFIGURACION+Constantes.SEPARADOR_ARCHIVOS+"defaults.xml";
		public static final String ARCHIVO_CONFIGURACION = 			ConstantesArchivos.CARPETA_CONFIGURACION+Constantes.SEPARADOR_ARCHIVOS+"configuraciones.xml";
		public static final String ARCHIVO_MENSAJE = 				ConstantesArchivos.CARPETA_CONFIGURACION+Constantes.SEPARADOR_ARCHIVOS+"mensajes.xml";
		public static final String ARCHIVO_LOG = 					ConstantesArchivos.CARPETA_CONFIGURACION+Constantes.SEPARADOR_ARCHIVOS+"log_programatico.txt";		
		
		public static final String TIPOSDB_POSTGRE93_NOMBRE = 		"POSTGRESQL";
		public static final String TIPOSDB_POSTGRE93_VERSION = 		"9.3.5";
		public static final String TIPOSDB_POSTGRE93_DRIVER = 		"org.postgresql.Driver";
		public static final String TIPOSDB_POSTGRE93_JDBC  =		"postgresql";
		
		public static final String TIPOSDB_POSTGRE96_NOMBRE = 		"POSTGRESQL";
		public static final String TIPOSDB_POSTGRE96_VERSION = 		"9.6";
		public static final String TIPOSDB_POSTGRE96_DRIVER = 		"org.postgresql.Driver";
		public static final String TIPOSDB_POSTGRE96_JDBC  =		"postgresql";
		
		public static final String TIPOSDB_MYSQL51_NOMBRE = 		"MYSQL";
		public static final String TIPOSDB_MYSQL51_VERSION = 		"5.1";
		public static final String TIPOSDB_MYSQL51_DRIVER = 		"com.mysql.jdbc.Driver";
		public static final String TIPOSDB_MYSQL51_JDBC  =			"mysql";
		
		public static final String TIPOSDB_MYSQL57_NOMBRE = 		"MYSQL";
		public static final String TIPOSDB_MYSQL57_VERSION = 		"5.7";
		public static final String TIPOSDB_MYSQL57_DRIVER = 		"com.mysql.jdbc.Driver";
		public static final String TIPOSDB_MYSQL57_JDBC  =			"mysql";
		
		public static final String TIPOSDB_ORACLE12C_NOMBRE = 		"ORACLE";
		public static final String TIPOSDB_ORACLE12C_VERSION = 		"12C";
		public static final String TIPOSDB_ORACLE12C_DRIVER = 		"oracle.jdbc.driver.OracleDriver";
		public static final String TIPOSDB_ORACLE12C_JDBC  =		"oracle";
		
		public static final String TIPOSDB_SQLSERVER2000_NOMBRE = 	"SQLSERVER";
		public static final String TIPOSDB_SQLSERVER2000_VERSION = 	"2000";
		public static final String TIPOSDB_SQLSERVER2000_DRIVER = 	"com.microsoft.sqlserver.jdbc.SQLServerDriver";
		public static final String TIPOSDB_SQLSERVER2000_JDBC  =	"sqlserver";
		
		public static final String TIPOSDB_MARIADB_NOMBRE = 		"MARIADB";
		public static final String TIPOSDB_MARIADB_VERSION = 		"10.2.7";
		public static final String TIPOSDB_MARIADB_DRIVER = 		"org.mariadb.jdbc.Driver";
		public static final String TIPOSDB_MARIADB_JDBC = 			"mariadb";
		
		
	}//ConstantesArchivos	 
	
	public static class ConstantesMensajes
	{
		public static final String MENSAJE_INICIAL = 				"primermensaje";
		public static final String MENSAJE_FINAL_METODO = 			"finmetodo";
		public static final String MENSAJE_FINAL_ERRONEO_METODO = 	"finmetodoerroneo";
		public static final String MENSAJE_FIN_CLASE = 				"findeclase";
		public static final String MENSAJE_CLASE_ERRONEO = 			"finclaseerroneo";
		public static final String MENSAJE_INICIO_METODO = 			"iniciometodo";
		public static final String MENSAJE_SQL =					"sql";
		public static final String MENSAJE_JDBC = 					"cadenajdbc";
		public static final String MENSAJE_CALLABLE = 				"callablejdbc";
		
		//Mensajes
		
		public static final String MENSAJE_INICIAL_VALOR = 				"Log programatico SPL";
		public static final String MENSAJE_FINAL_METODO_VALOR =			"Termino el metodo ?0 correctamente";
		public static final String MENSAJE_FINAL_ERRONEO_METODO_VALOR = "Termino el metodo /error0/ incorrectamente: Error: /error1/";
		public static final String MENSAJE_FIN_CLASE_VALOR 	= 			"Termino la clase ?0 correctamente";
		public static final String MENSAJE_CLASE_ERRONEO_VALOR = 		"Termino la clase /error0/ incorrectamente: Error /error1/";
		public static final String MENSAJE_INICIO_METODO_VALOR = 		"Comenzo el metodo ?0 correctamente";
		public static final String MENSAJE_SQL_VALOR = 					"SQL Enviar: ?0";
		public static final String MENSAJE_JDBC_VALOR = 				"JDBC: ?0";
		public static final String MENSAJE_CALLABLE_VALOR = 			"Callable: ?0";
		
	}//ConstantesMensajes
	
	/**
	 * 
	 * @author alejo
	 *
	 */
	public static class ConstantesRegex
	{
		public static final String REGEX_ITEM = 					"(.+?)(<item>(.+)</item>)";
		public static final String REGEX_ATRIBUTOS_REGEX = 			">(.+?)</item>";
		public static final String REGEX_HIJOS_ITEM = 				"<(.+?)>(.+?)<";
		public static final String REGEX_DEFAULTS = 				"<defaults>(.+)</defaults>";
		public static final String REGEX_DEFAULT = 					">(.+?)</default>";
		public static final String REGEX_TIPO_DATO_DEFAULT = 		"<tipodato>(.+?)<";
		public static final String REGEX_VALOR_DEFAULT = 			"<valor>(.+)?<";
		public static final String REGEX_MAIN = 					"<main>(.+)</main>";
		public static final String REGEX_LOG = 						"<log>(.+)?<\\/log>";
		public static final String REGEX_CONFIGURACION = 			"<configuracion>(.+)<\\/configuracion>";
		public static final String REGEX_NUMERO_ETIQUETAS = 		"^\\<";
		public static final String REGEX_CONTENIDO_ARCHIVO_CONF = 	"<(.+?)>(.+?)<";
		public static final String REGEX_MENSAJE = 					"<mensaje\\scodigo\\=\\\"(.+?)\\\"\\>(.+?)\\<";
		
	}//ConstantesRegex
	
	/**
	 * 
	 * @author alejo
	 *
	 */
	public static class ConstantesSQL
	{
		public static final String SQL_SELECT = 			"SELECT ";
		public static final String SQL_FROM = 				" FROM ";
		public static final String SQL_WHERE = 				" WHERE ";
		public static final String SQL_ORDER_BY = 			" ORDER BY ";
		public static final String SQL_AND = 				" AND ";
		public static final String SQL_DELETE = 			"DELETE ";	
		public static final String SQL_INSERT_INTO = 		"INSERT INTO ";
		public static final String SQL_VALUES = 			"VALUES ";	
		public static final String SQL_UPDATE = 			"UPDATE ";
		public static final String SQL_SET = 				" SET ";
		public static final String SQL_IS = 				" IS ";
		public static final String SQL_NEXT_VAL	=			".NEXTVAL";
		
	}//ConstantesSQL

}//Constantes
