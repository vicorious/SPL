package co.com.vicorious.persistencia.unidadpersistencia;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.function.Predicate;

import co.com.vicorious.persistencia.excepciones.PersistenciaException;

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
 * Interfaz que describe el comportamiento de la unidad de persistencia
 *
 */
public interface UnidadPersistenciaInterface 
{
	/**
	 * Encargado de retornar todos los resultados para la entidad 
	 * @param entidad: Clase que debe tener las etiqueta TABLA
	 * @return opcional con el resultado dicho
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<?> get(Class<?> entidad) throws PersistenciaException;
	
	/**
	 * Encargado de retornar la entidad por ID
	 * @param objectowhere: objeto con el ID a buscar
	 * @return opcional con el resultado
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<?> getForID(Object objectowhere) throws PersistenciaException;
	/**
	 * Encargado de retornar todos los resultados para la entidad filtrados por un HashMap
	 * @param entidad: clase que debe tener la etiqueta TABLA
	 * @param parametros: mapa con los parametros
	 * @return opcional con el resultado
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<?> get(Class<?> entidad, LinkedHashMap<String, Object> parametros) throws PersistenciaException;
	/**
	 * Encargado de retornar todos los resultados para la entidad filtrados por un predicado
	 * @param entidad: Clase que debe tener la etiqueta TABLA
	 * @param predicado: Expresion lambda predicado para filtrar
	 * @return opcional con el resultado dicho
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<?> get(Class<?> entidad, Predicate<Object> predicado) throws PersistenciaException;
	
	/**
	 * Encargado de retornar todos los resultados para la entidad filtrados por un objeto del mismo (objeto where)
	 * @param columnanowhere: van los nombres de las columnas que no deben ir en el where del objeto where
	 * @param objetoWhere: Objeto del tipo entidad (entidad.getClass())
	 * con este objeto podemos asignar dinamicamente los filtros de la consulta
	 * a nivel logico. EJM.
	 * Entidad Persona. Atributos: nombre, apellido
	 * 
	 * Supongamos que en nuestra base de datos tenemos 2 registros en la tabla Persona
	 * 
	 * SELECT * FROM PERSONA
	 * 
	 * Nombre      |Apellido
	 * Alejandro    Lindarte
	 * Victoria		Lindarte
	 * .
	 * La manera de encontrar a Victoria en la base de datos atraves del metodo, es la siguiente:
	 * objetoWhere: 
	 * Persona persona = new Persona();
	 * persona.setNombre("Victoria");
	 * Optional-?- victoria = getSelectEntidad(persona);
	 * Persona victoriaP = victoria != null ? (Persona) victoria.get() : null;
	 * System.out.println(victoriaP.getApellido());
	 * 
	 * Out:
	 * Lindarte
	 * 
	 * @return: Optional con el resultado, puede que sea un objeto o una lista (Si hay mas de un match)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<?> get(Object objetoWhere, String... columnanowhere) throws PersistenciaException;
	
	/**
	 * Metodo encargado de eliminar a entidad utilizando las propiedades del objetoWhere
	 * @param objectowhere: objeto con los campos necesarios para eliminar los objetos de su mismo tipo
	 * @param columnanowhere: Columnas que no van en el WHERE
	 * @return true si puedo eliminar, false si no
	 * 
	 * Ejemplo
	 * Entidad Persona. Atributos: nombre, apellido
	 * 
	 * Supongamos que en nuestra base de datos tenemos 2 registros en la tabla Persona
	 * 
	 * SELECT * FROM PERSONA
	 * 
	 * Nombre      |Apellido
	 * Alejandro    Lindarte
	 * Victoria		Lindarte
	 * .
	 * La manera de eliminar a Victoria en la base de datos atraves del metodo, es la siguiente:
	 * objetoWhere: 
	 * Persona persona = new Persona();
	 * persona.setNombre("Victoria");
	 * boolean borrado = eliminarEntidad(persona);
	 * System.out.println(borrado == true ? "correcto": "Problemas");
	 * 
	 * Out:
	 * correcto
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean borrar(Object objectowhere, String... columnanowhere) throws PersistenciaException;
	
	/**
	 * Metodo encargado de eliminar a entidad utilizando las propiedades del objetoWhere
	 * @param objectowhere: objeto con los campos necesarios para eliminar los objetos de su mismo tipo
	 * @return true si puedo eliminar, false si no
	 * 
	 * Ejemplo
	 * Entidad Persona. Atributos: nombre, apellido
	 * 
	 * Supongamos que en nuestra base de datos tenemos 2 registros en la tabla Persona
	 * 
	 * SELECT * FROM PERSONA
	 * 
	 * Nombre      |Apellido
	 * Alejandro    Lindarte
	 * Victoria		Lindarte
	 * .
	 * La manera de eliminar a Victoria en la base de datos atraves del metodo, es la siguiente:
	 * objetoWhere: 
	 * Persona persona = new Persona();
	 * persona.setNombre("Victoria");
	 * boolean borrado = eliminarEntidad(persona);
	 * System.out.println(borrado == true ? "correcto": "Problemas");
	 * 
	 * Out:
	 * correcto
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean borrar(Object objectowhere) throws PersistenciaException;
	/**
	 * Metodo encargado de insertar un objeto tipo entidad(Que contenga la etiqueta TABLA)
	 * @param entidad: objeto con los atributos a insertar
	 * @return: true si inserto, false si no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean insertar(Object entidad) throws PersistenciaException;
	
	/**
	 * Metodo encargado de actualizar un objeto tipo entidad (Que contenga la etiqueta TABLA)
	 * @param entidad: objeto con los atributos a insertar
	 * @param columnanowhere: Aqui van las columnas que no deben ir en el WHERE de la sentencia
	 * @return: true si actualizo: false si no
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public boolean actualizar(Object entidad, String... columnanowhere) throws PersistenciaException;
	
	/**
	 * Metodo encargado de llamar un procedimiento tipo Procedimiento (Que contenga la etiqueta Procedimiento)
	 * @param procedimiento: Objeto procedimiento
	 * @param <T> clase a la que corresponde el procedimiento
	 * @return opcional con los parametros OUT(si los contiene)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public <T> Optional<?> procedimiento(Object procedimiento) throws PersistenciaException;
	
	/**
	 * Metodo encargado de llamar una funcion tipo Funcion(Que contenga la etiqueta Funcion)
	 * @param funcion: Objeto funcion
	 * @return opcional con el parametro de salida(RECORDEMOS QUE UNA FUNCION DEVUELVE UN SOLO VALOR)
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Optional<?> funcion(Object funcion) throws PersistenciaException;
	
	/**
	 * Metodo encargado de crear un SQL nativo
	 * @param sql Sql para generar la transaccion
	 * @param mapeo: Clase a la que se deben mapear los resultados
	 * @return true o false si es un INSERT, UPDATE O DELETE o Arreglo de objetos (Object[]) con los resultados del SELECT
	 * @throws PersistenciaException encapsula cualquier error generado por JAVA
	 */
	public Object createDML(String sql,Class<?>... mapeo) throws PersistenciaException;
	
	/**
	 * Encargado de cerrar la conexion de la unidad de persistencia actual
	 * @throws PersistenciaException error al cerrar la conexion
	 */
	public void cerrarConexion() throws PersistenciaException;	
		
}//No borrar
