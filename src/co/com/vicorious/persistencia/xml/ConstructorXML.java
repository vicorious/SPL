package co.com.vicorious.persistencia.xml;

import java.util.ArrayList;
import java.util.List;

import co.com.vicorious.persistencia.to.XmlTO;
import co.com.vicorious.persistencia.utilidades.Constantes;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesArchivos;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesMensajes;

/**
 *  <p>XML</p>	
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
 * Clase encargada de construir los archivos XML de SPL
 * 
 *
 */
public abstract class ConstructorXML 
{
	/**
	 * Metodo encargado de generar los XML de configuracion
	 * @return to con la informacion de todos los archivos
	 */
	public static XmlTO generarXMLTO()
	{
		XmlTO xmlresultado = new XmlTO();
		//Configuracion
		ConfiguracionXML configuracion = new ConfiguracionXML();
		configuracion.setEsdefault("true");
		configuracion.setLog("true");
		configuracion.setLoglimite("false");
		configuracion.setAutocommit("true");
		configuracion.setSobreescribearchivo("false");
		
		//Mensajes
		MensajesXML mensajes = new MensajesXML();
		List<MensajeXML> mensajelista = new ArrayList<MensajeXML>();
		
		MensajeXML primermensaje = new MensajeXML();
		primermensaje.setCodigo("primermensaje");
		primermensaje.setValor_raiz(ConstantesMensajes.MENSAJE_INICIAL_VALOR);
		
		MensajeXML iniciometodo = new MensajeXML();
		iniciometodo.setCodigo("iniciometodo");
		iniciometodo.setValor_raiz(ConstantesMensajes.MENSAJE_INICIO_METODO_VALOR);
		
		MensajeXML finmetodo = new MensajeXML();
		finmetodo.setCodigo("finmetodo");
		finmetodo.setValor_raiz(ConstantesMensajes.MENSAJE_FINAL_METODO_VALOR);
		
		MensajeXML findeclase = new MensajeXML();
		findeclase.setCodigo("findeclase");
		findeclase.setValor_raiz(ConstantesMensajes.MENSAJE_FIN_CLASE_VALOR);
		
		MensajeXML finclaseerroneo = new MensajeXML();
		finclaseerroneo.setCodigo("finclaseerroneo");
		finclaseerroneo.setValor_raiz(ConstantesMensajes.MENSAJE_CLASE_ERRONEO_VALOR);
		
		MensajeXML finmetodoerroneo = new MensajeXML();
		finmetodoerroneo.setCodigo("finmetodoerroneo");
		finmetodoerroneo.setValor_raiz(ConstantesMensajes.MENSAJE_FINAL_ERRONEO_METODO_VALOR);
		
		MensajeXML sql = new MensajeXML();
		sql.setCodigo("sql");
		sql.setValor_raiz(ConstantesMensajes.MENSAJE_SQL_VALOR);
		
		MensajeXML cadenajdbc = new MensajeXML();
		cadenajdbc.setCodigo("cadenajdbc");
		cadenajdbc.setValor_raiz(ConstantesMensajes.MENSAJE_JDBC_VALOR);
		
		MensajeXML callablejdbc = new MensajeXML();
		callablejdbc.setCodigo("callablejdbc");
		callablejdbc.setValor_raiz(ConstantesMensajes.MENSAJE_CALLABLE_VALOR);
		
		mensajelista.add(primermensaje);
		mensajelista.add(iniciometodo);
		mensajelista.add(finmetodo);
		mensajelista.add(findeclase);
		mensajelista.add(finclaseerroneo);
		mensajelista.add(finmetodoerroneo);
		mensajelista.add(sql);
		mensajelista.add(cadenajdbc);
		mensajelista.add(callablejdbc);
		
		mensajes.setMensaje(mensajelista);
		
		//Defaults		
		DefaultsXML defaults = new DefaultsXML();
		
		List<DefaultXML> elementosdefault = new ArrayList<DefaultXML>();
		
		DefaultXML integer = new DefaultXML();
		integer.setName("Integer");
		integer.setTipodato(Constantes.DEFAULT_INTEGER);
		integer.setValor(Constantes.DEFAULT_INTEGER_VALOR);
		
		DefaultXML doubledefault = new DefaultXML();
		doubledefault.setName("Double");
		doubledefault.setTipodato(Constantes.DEFAULT_DOUBLE);
		doubledefault.setValor(Constantes.DEFAULT_DOUBLE_VALOR);
		
		elementosdefault.add(integer);
		elementosdefault.add(doubledefault);
		
		defaults.setDefault_element(elementosdefault);
		
		//TiposDB
		
		TiposDBXML tipos = new TiposDBXML();
		List<TipoDBXML> tiposelementos = new ArrayList<TipoDBXML>();
		
		TipoDBXML postgre93 = new TipoDBXML();
		postgre93.setNombre(ConstantesArchivos.TIPOSDB_POSTGRE93_NOMBRE);
		postgre93.setVersion(ConstantesArchivos.TIPOSDB_POSTGRE93_VERSION);
		postgre93.setDriver(ConstantesArchivos.TIPOSDB_POSTGRE93_DRIVER);
		postgre93.setJdbc(ConstantesArchivos.TIPOSDB_POSTGRE93_JDBC);
		
		TipoDBXML postgre96 = new TipoDBXML();
		postgre96.setNombre(ConstantesArchivos.TIPOSDB_POSTGRE96_NOMBRE);
		postgre96.setVersion(ConstantesArchivos.TIPOSDB_POSTGRE96_VERSION);
		postgre96.setDriver(ConstantesArchivos.TIPOSDB_POSTGRE96_DRIVER);
		postgre96.setJdbc(ConstantesArchivos.TIPOSDB_POSTGRE96_JDBC);
		
		TipoDBXML mysql51 = new TipoDBXML();
		mysql51.setNombre(ConstantesArchivos.TIPOSDB_MYSQL51_NOMBRE);
		mysql51.setVersion(ConstantesArchivos.TIPOSDB_MYSQL51_VERSION);
		mysql51.setDriver(ConstantesArchivos.TIPOSDB_MYSQL51_DRIVER);
		mysql51.setJdbc(ConstantesArchivos.TIPOSDB_MYSQL51_JDBC);
		
		TipoDBXML mysql57 = new TipoDBXML();
		mysql57.setNombre(ConstantesArchivos.TIPOSDB_MYSQL57_NOMBRE);
		mysql57.setVersion(ConstantesArchivos.TIPOSDB_MYSQL57_VERSION);
		mysql57.setDriver(ConstantesArchivos.TIPOSDB_MYSQL57_DRIVER);
		mysql57.setJdbc(ConstantesArchivos.TIPOSDB_MYSQL57_JDBC);
		
		TipoDBXML oracle = new TipoDBXML();
		oracle.setNombre(ConstantesArchivos.TIPOSDB_ORACLE12C_NOMBRE);
		oracle.setVersion(ConstantesArchivos.TIPOSDB_ORACLE12C_VERSION);
		oracle.setDriver(ConstantesArchivos.TIPOSDB_ORACLE12C_DRIVER);
		oracle.setJdbc(ConstantesArchivos.TIPOSDB_ORACLE12C_JDBC);
		
		TipoDBXML sqlserver2000 = new TipoDBXML();
		sqlserver2000.setNombre(ConstantesArchivos.TIPOSDB_SQLSERVER2000_NOMBRE);
		sqlserver2000.setVersion(ConstantesArchivos.TIPOSDB_SQLSERVER2000_VERSION);
		sqlserver2000.setDriver(ConstantesArchivos.TIPOSDB_SQLSERVER2000_DRIVER);
		sqlserver2000.setJdbc(ConstantesArchivos.TIPOSDB_SQLSERVER2000_JDBC);
		
		TipoDBXML mariadb12 = new TipoDBXML();
		mariadb12.setNombre(ConstantesArchivos.TIPOSDB_MARIADB_NOMBRE);
		mariadb12.setVersion(ConstantesArchivos.TIPOSDB_MARIADB_VERSION);
		mariadb12.setDriver(ConstantesArchivos.TIPOSDB_MARIADB_DRIVER);
		mariadb12.setJdbc(ConstantesArchivos.TIPOSDB_MARIADB_JDBC);
		
		tiposelementos.add(postgre96);
		tiposelementos.add(postgre93);
		tiposelementos.add(mysql51);
		tiposelementos.add(mysql57);
		tiposelementos.add(oracle);
		tiposelementos.add(sqlserver2000);
		tiposelementos.add(mariadb12);
		
		tipos.setItem(tiposelementos);
		
		//Credenciales
		
		
		String credenciales_properties = 
				"usuario = usuario\n" 			+
				"contrasena = contrasena\n" 	+
				"basededatos = basededatos\n" 	+
				"puerto = 0000\n" 				+
				"host = localhost\n";
		
		//XML
		
		xmlresultado.setConfiguracion_xml(configuracion);
		xmlresultado.setDefaults_xml(defaults);
		xmlresultado.setMensajes_xml(mensajes);
		xmlresultado.setTiposdb_xml(tipos);
		xmlresultado.setCredenciales_properties(credenciales_properties);
		
		return xmlresultado;
		
	}//generarXMLTO
}//NoBorrar
