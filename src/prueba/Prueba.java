package prueba;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import co.com.vicorious.persistencia.conexion.Conexion;
import co.com.vicorious.persistencia.enums.TiposBaseDatos;
import co.com.vicorious.persistencia.excepciones.PersistenciaException;
import co.com.vicorious.persistencia.to.BaseDatosConfigTO;
import co.com.vicorious.persistencia.to.CredencialesTO;
import co.com.vicorious.persistencia.unidadpersistencia.UnidadPersistencia;
import co.com.vicorious.persistencia.unidadpersistencia.UnidadPersistenciaInterface;
import co.com.vicorious.persistencia.utilidades.Configuracion;
import co.com.vicorious.persistencia.utilidades.Logueable;


/**
 *   <p>Prueba</p>	
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
 *   Clase Main que nos permite ejecutar nuestras pruebas integrales
 *   
 **/
public class Prueba extends Logueable
{
	//Unidad de persistencia capaz de gestionar iteraciones con la base de datos.
	private static UnidadPersistenciaInterface unidad = null;

	/**
	 * Main
	 * @param args: argumentos desde la linea de comandos
	 * @throws ClassNotFoundException problemas con referencias de clases
	 * @throws IllegalArgumentException argumentos invalido
	 * @throws IllegalAccessException no se puede acceder algun miembro privado reflexivamente
	 * @throws NoSuchFieldException no se encuentra un atributo de alguna clase
	 * @throws SecurityException Violacion de seguridad
	 */
	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException 
	{
		try
		{					

			/******************************************************************************************************/
								   // METODO QUE NO DEBEMOS BORRAR
			/*METODO QUE NO DEBEMOS BORRAR*/conexiones();//Metodo que NO DEBEMOS BORRAR
									//METODO QUE NO DEBEMOS BORRAR
			
			/*****************************************************************************************************/
			
			
			
			/**
			 * 
			 * Apartir de aqui ya puedes llamar algunos de los metodos 
			 * existentes o si creaste alguno tuyo
			 * 
			 * 
			 */
			
			/*Matematica mate = (x,y) -> x*y;
			System.out.println("El resultado: "+mate.operacion(2, 3));*/

			//conexionesManuales();
			//Get entidades plana
			//entidadMuchosAUno();
		
			//entidadUnoAMuchos();
			//Entidad plana where
			//entidadPlanaObjectoWhere();
			
			//insertarUsuarioPostgreSQL();
			//funcion();
			
			//procedimiento();
			//entidadWherePostgreSQL();
			//actualizarUsuarioPostgreSQL();
			//eliminarUsuarioPostgreSQL();
			//EntidadID
			//createSQL();
			
			//funcionPostgreSQL();
			
			//muchosAUnoPostgreSQL();
			
			//unoAMuchosPostgreSQL();
			//entidadPlanaObjectoWhere();
			//createDML();
			//getMapa();
			
			//getPredicado();
			
			//unoAMuchosPostgreSQL();
			
			//insertarMuchosAuno();
			
			//xml();
			
			//entidadID();	
			
			//getEntidad2TimestampColumn();
			
			//getEstablecimientos();
			
			//fixOpcional();
			
			//actualizarUsuarioPostgreSQL();
			
			actualizarAPP();
			
			/***************************************************************************************/
			
									//METODO QUE NO DEBEMOS BORRAR
			/*METODO QUE NO DEBEMOS BORRAR*/cerrarConexion();//METODO QUE NO DEBEMOS BORRAR
									//METODO QUE NO DEBEMOS BORRAR
			
			
			/***************************************************************************************/
			
			System.exit(0);
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		

	}
	/**
	 * Obtenemos la conexion correspondiente
	 * @throws PersistenciaException cuando no se crea la conexion de manera correcta
	 */
	public static void conexiones() throws PersistenciaException
	{
		CredencialesTO credenciales = new CredencialesTO();
		credenciales.setUsuario("postgres");
		credenciales.setBasededatos("postgres");
		credenciales.setContrasena("*postgre123*");
		credenciales.setPuerto("5432");
		credenciales.setHost("localhost");
		
		credenciales = Configuracion.getInstancia().credencialesPorDefecto(credenciales);
		
		Optional<BaseDatosConfigTO> opconfiguracion = Configuracion.getInstancia().
			elegirConfiguracionFiltro(
					p -> p.getNombre().equalsIgnoreCase(TiposBaseDatos.POSTGRES96.getNombre()) 
					&& 
					p.getVersion().equalsIgnoreCase(TiposBaseDatos.POSTGRES96.getVersion()));
		
		
		//Optional<BaseDatosConfigTO> opconfiguracion = Configuracion.getInstancia().elegirConfiguracionFiltro(p -> p.getNombre().equalsIgnoreCase(TiposBaseDatos.MARIADB.getNombre()) && p.getVersion().equalsIgnoreCase(TiposBaseDatos.MARIADB.getVersion()));
		if(!opconfiguracion.isPresent())
		{
			throw new PersistenciaException("No es posible cargar la configuracion de la base de datos: porfavor intenta con una configurable");
		}
		Optional<Connection> conexionBaseCrede = Conexion.crearConexion(opconfiguracion.get(), credenciales);
		if(!conexionBaseCrede.isPresent())
		{
			throw new PersistenciaException("Error al crear conexion con la configuracion de la base de datos seleccionada, porfavor intente de nuevo ");
		}
		unidad = new UnidadPersistencia(conexionBaseCrede);
		
		
	}//conexiones
	
	/**
	 * Obtenemos las conexiones manuales
	 * @throws PersistenciaException no se crea correctamente la conexion
	 */
	public static void conexionesManuales() throws PersistenciaException
	{
		String path = "C:\\Archivos\\";
		String pathcredenciales = path+"credenciales.properties";
		String pathconfiguraciones = path+"configuraciones.xml";		
		String pathdefaults = path+"defaults.xml";
		String pathtipodb = path+"tiposdb.xml";
		String pathmensajes = path+"mensajes.xml";
		CredencialesTO credenciales;
		
		credenciales = Configuracion.getInstancia(pathcredenciales, pathconfiguraciones, pathmensajes, pathdefaults, pathtipodb).credencialesPorDefecto();//SOLO es necesario pasarle los parametros de configuracion UNA SOLA VEZ, despues puede llamarse el singleton de manera limpia (Configuracion.getInstance().loquesea()
		Optional<BaseDatosConfigTO> opconfiguracion = Configuracion.getInstancia().elegirConfiguracionFiltro(p -> p.getNombre().equalsIgnoreCase(TiposBaseDatos.POSTGRES96.getNombre()) && p.getVersion().equalsIgnoreCase(TiposBaseDatos.POSTGRES96.getVersion()));
		if(!opconfiguracion.isPresent())
		{
			throw new PersistenciaException("No es posible cargar la configuracion de la base de datos: porfavor intenta con una configurable");
		}
		Optional<Connection> conexionBaseCrede = Conexion.crearConexion(opconfiguracion.get(), credenciales);
		if(!conexionBaseCrede.isPresent())
		{
			throw new PersistenciaException("Error al crear conexion con la configuracion de la base de datos seleccionada, porfavor intente de nuevo ");
		}
		unidad = new UnidadPersistencia(conexionBaseCrede);
	}//conexionesManuales
	
	/**
	 * Encargada de cerrar la conexion de la unidad
	 * @throws PersistenciaException error la cerrar la conexion
	 */
	public static void cerrarConexion() throws PersistenciaException
	{
		unidad.cerrarConexion();
		
	}//cerrarConexion
	
	/**
	 * CASO PRUEBA 1: Entidad con muchos a 1
	 */
	@SuppressWarnings("unchecked")
	public static void entidadMuchosAUno()
	{
		try 
		{
			Optional<List<Entidad>> entidades = (Optional<List<Entidad>>) unidad.get(Entidad.class);
			List<Entidad> resultado = null;
			
			if(entidades.isPresent())
			{
				resultado =  entidades.get();
				resultado.forEach(p -> System.out.println(p.getAtributovarchar()));
				
			}else
			{
				//tafac
			}
			
		} catch (PersistenciaException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//entidadMuchosAUno
	/**
	 * CASO ESTUDIO 2: Funcion MYSQL
	 */
	public static void funcion()
	{
		FuncionPrueba resultadoo = null;
		try
		{
			FuncionPrueba funcion =  new FuncionPrueba();
			funcion.setParametroentrada(5);
			Optional<?> resultado = unidad.funcion(funcion);
			if(resultado.isPresent())
			{
				resultadoo = (FuncionPrueba) resultado.get();
				System.out.println("Resultado funcion: "+resultadoo.getParametrosalida());
			}
		}catch(PersistenciaException ex)
		{
			ex.printStackTrace();
		}
		
	}//funcion
	
	/**
	 * CASO ESTUDIO 3: Procedimiento MYSQL
	 */
	public static void procedimiento()
	{
		Procedimiento procedimiento = null;
		try
		{
			procedimiento = new Procedimiento();
			procedimiento.setEntrada("PRUEBA-SPL");
			Optional<?> resultado = unidad.procedimiento(procedimiento);
			if(resultado.isPresent())
			{
				procedimiento = (Procedimiento) resultado.get();
				System.out.println("Resultado Procedimiento: "+procedimiento.getSalida());
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}//procedimiento
	
	
	/**
	 * CASO ESTUDIO 4: entidad objeto where
	 */
	@SuppressWarnings("unchecked")
	public static void entidadPlanaObjectoWhere()
	{
		try
		{
			Entidad entidadwhere = new Entidad();
			entidadwhere.setAtributovarchar("LITERAL");
			Optional<List<Entidad>> entidades = (Optional<List<Entidad>>) unidad.get(entidadwhere,"ATRIBUTO_DATE");
			List<Entidad> resultado = null;
			
			if(entidades.isPresent())
			{				
				resultado =  entidades.get();
				System.out.println("Si encontro resultados: Cuentas? "+resultado.size());
				resultado.stream().map(Entidad::getAtributovarchar).forEach(System.out::println);
				
			}else
			{
				//tafac
			}
			
		}catch(PersistenciaException ex)
		{
			ex.printStackTrace();
		}
		
	}//entidadPlanaObjectoWhere
			
	/**
	 * CASO ESTUDIO 5: Entidad por ID
	 */
	public static void entidadID()
	{
		try
		{
			Entidad2 resultado = null;
			Entidad2 entidadwhere = new Entidad2();
			entidadwhere.setAtributovarchar("ASD");
			entidadwhere.setIdentidad(1);
			
			@SuppressWarnings("unchecked")
			Optional<Entidad2> entidades = (Optional<Entidad2>) unidad.getForID(entidadwhere);
			
			if(entidades.isPresent())
			{
				resultado =  entidades.get();
				System.out.println(resultado.getIdentidad());
				
			}else
			{
				//tafac
			}
			
		}catch(PersistenciaException ex)
		{
			ex.printStackTrace();
		}
		
	}//entidadID
	
	/**
	 * CASO ESTUDIO 6: Uno a muchos
	 */
	@SuppressWarnings("unchecked")
	public static void entidadUnoAMuchos()
	{
		try 
		{
			Optional<List<Entidad2>> entidades = (Optional<List<Entidad2>>) unidad.get(Entidad2.class);
			List<Entidad2> resultado = null;
			
			if(entidades.isPresent())
			{
				resultado =  entidades.get();
				resultado.forEach(p -> System.out.println(p.getAtributovarchar()));
				
			}else
			{
				//tafac
			}
			
		} catch (PersistenciaException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//entidadUnoAMuchos
	
	/**
	 * CASO ESTUDIO 7: entidad plana POSTGRESQL
	 */
	@SuppressWarnings("unchecked")
	public static void entidadPostgreSQL()
	{
		try
		{				
			Optional<List<Usuario>> entidades = (Optional<List<Usuario>>) unidad.get(Usuario.class);
			List<Usuario> resultado = null;
			
			if(entidades.isPresent())
			{
				resultado =  entidades.get();
				resultado.forEach(p -> System.out.println(p.getCorreo()));
				
			}else
			{
				//tafac
			}
			
		}catch(PersistenciaException ex)
		{
			ex.printStackTrace();
		}
		
	}//entidadPostgreSQL
	
	/**
	 * CASO ESTUDIO 8: entidad plana objeto where POSTGRESQL
	 */
	@SuppressWarnings("unchecked")
	public static void entidadWherePostgreSQL()
	{
		try
		{				
			Usuario usuario = new Usuario();
			usuario.setNombre("Alejandro");
			//("alejo.lindarte@outlook.com");
			Optional<List<Usuario>> entidades = (Optional<List<Usuario>>) unidad.get(usuario,"APELLIDOS","CORREO" ,"CONTRASENA");
			List<Usuario> resultado = null;
			
			if(entidades.isPresent())
			{
				resultado =  entidades.get();
				resultado.forEach(p -> System.out.println(p.getCorreo()));
				
			}else
			{
				//tafac
			}
			
		}catch(PersistenciaException ex)
		{
			ex.printStackTrace();
		}
		
	}//entidadWherePostgreSQL
	
	/**
	 * CASO ESTUDIO 9: insertar entidad postgre
	 */	
	public static void insertarUsuarioPostgreSQL()
	{
		try
		{
			Usuario usuario = new Usuario();	
			usuario.setId(5);
			usuario.setNombre("Prueba");
			usuario.setApellidos("Prueba apellidos");
			usuario.setCorreo("alejo.lindartenq2ew");
			usuario.setContrasena("123123123");
			
			boolean resultado = unidad.insertar(usuario);
			System.out.println("Se inserto correctamente? "+(resultado ? "Si": "No"));
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}//insertarUsuarioPostgreSQL
	
	/**
	 * CASO ESTUDIO 10: actualizar entidad postgre
	 */
	public static void actualizarUsuarioPostgreSQL()
	{
		try
		{
			Usuario usuario = new Usuario();
			usuario.setId(3);
			usuario.setNombre("Prueba");
			usuario.setApellidos("Prueba apellidos");
			usuario.setCorreo("alejo.lindartenq2ew");
			usuario.setContrasena("123123123");
			
			boolean resultado = unidad.actualizar(usuario);
			
			System.out.println("Se inserto correctamente? "+(resultado ? "Si": "No"));
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}//actualizarUsuarioPostgreSQL
	
	/**
	 * CASO ESTUDIO 11: eliminar entidad postgre
	 */
	public static void eliminarUsuarioPostgreSQL()
	{
		try
		{
			Usuario usuario = new Usuario();
			usuario.setId(3);
			usuario.setNombre("Prueba");
			usuario.setApellidos("Prueba apellidos");
			usuario.setCorreo("alejo.lindartenq2ew");
			usuario.setContrasena("123123123");
			
			boolean resultado = unidad.borrar(usuario);
			
			System.out.println("Se inserto correctamente? "+(resultado ? "Si": "No"));
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//eliminarUusuarioPostgreSQL
	
	/**
	 * CASO ESTUDIO 12: Crear DML (Data modeling Language) dinamicamente sin discriminacion alguna
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public static void createDML()
	{
		try
		{
			//String sql = "SELECT ID, NOMBRE FROM Usuario";
			String sqlinsert = "INSERT INTO Usuario (ID, NOMBRE, APELLIDOS, CORREO, CONTRASENA) VALUES (4,'Alejandro','Castro','alejo.lindarte','123123')";
			String sql = "SELECT EXTRACT(EPOCH FROM NOW()::timestamp - FECHA_CREACION)/60 as resultado FROM TOKENIZACION WHERE TICKET = 'TICKET_PRUEBA'";
			String sqlmapeo = "SELECT ID, LLAVE, VALOR FROM PARAMETRO";
			Object resultado = unidad.createDML(sqlmapeo, Parametro.class); // SELECT CON MAPEO
			//Object resultado = unidad.createDML(sql); // SELECT SIMPLE
			//Object resultado = unidad.createDML(sqlinsert); //Operacion (INSERT, UPDATE O DELETE)
		
			if(Boolean.class.isInstance(resultado))
			{
				System.out.println("INSERT, DELETE O UPDATE");
				boolean resultadoboo = Boolean.parseBoolean(resultado.toString());
				System.out.println("El SQL se ejecuto correctamente? "+((boolean) resultadoboo ? "Si": "No"));
			}else if(List.class.isInstance(resultado))
			{
				System.out.println("SELECT CON OBJETO DE MAPEO");
				List<Parametro> resultadolista = (List<Parametro>) resultado;
				resultadolista.stream().map(Parametro::getNombre).forEach(System.out::println);
			}			
			else
			{
				System.out.println("SELECT");
				Object[] resultadoselect = (Object[]) resultado;
				for(Object resultadoi: resultadoselect)
				{
					System.out.println(resultadoi);
					System.out.println("=>");
				}
			}
				
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//createSQL
	
	/**
	 * CASO ESTUDIO 13: llamar funcion PostgreSQL
	 */
	public static void funcionPostgreSQL()
	{
		try
		{
			FuncionPostgreSQL funcion = new FuncionPostgreSQL();
			funcion.setParametroentrada(1);
			funcion.setParametroentrada2(2);
			
			Optional<?> resultado = unidad.funcion(funcion);
			if(resultado.isPresent())
			{
				FuncionPostgreSQL resultadoo = (FuncionPostgreSQL) resultado.get();
				System.out.println(resultadoo.getParametrosalida());
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//funcionPostgreSQL
	
	/**
	 * CASO ESTUDIO 14: llamar entidad muchos a uno POSTGRESQL 
	 */
	@SuppressWarnings("unchecked")
	public static void muchosAUnoPostgreSQL()
	{
		try
		{
			Optional<?> resultado = unidad.get(Usuario.class);
			if(resultado.isPresent())
			{
				List<Usuario> productos = (List<Usuario>) resultado.get();
				productos.forEach( p -> System.out.println(p.getNombre()));
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//MuchosAUnoPostgreSQL
	
	/**
	 * CASO ESTUDIO 15: llamar entidad uno a muchos PostgreSQL
	 */
	@SuppressWarnings("unchecked")
	public static  void unoAMuchosPostgreSQL()
	{
		try
		{
			Optional<?> resultado = unidad.get(Usuario.class);
			if(resultado.isPresent())
			{
				List<Optional<?>> establecimientos = (List<Optional<?>>) resultado.get();
				establecimientos.forEach(e -> {System.out.println(((Usuario)(e.get())).getNombre());});
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}//unoAMuchosPostgreSQL
	
	/**
	 * CASO ESTUDIO 16: Insert muchos a uno
	 */
	public static void insertarMuchosAuno()
	{
		try
		{
			PedidoTO pedido = new PedidoTO();
			pedido.setCodigo("PEDIDO1");
			pedido.setFecha_final_pedido(LocalDate.now());
			pedido.setFecha_inicial_pedido(LocalDate.now());
			pedido.setId(1);
			
			FormaPagoTO pago = new FormaPagoTO();
			pago.setId(1);
			pago.setCodigo("CODIGO");
			
			pedido.setForma_pago(pago);
			
			boolean resultado = unidad.insertar(pedido);
			System.out.println("Se inserto correctamente? "+(resultado ? "Si": "No"));
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}//insertarMuchosAuno
	
	/**
	 * CASO ESTUDIO 17: Select MAPA
	 */
	@SuppressWarnings("unchecked")
	public static void getMapa()
	{
		try
		{
			LinkedHashMap<String, Object> mapa = new LinkedHashMap<String, Object>();
			mapa.put("ATRIBUTO_VARCHAR", "LITERAL");
			Optional<List<Entidad>> resultado = (Optional<List<Entidad>>) unidad.get(Entidad.class, mapa);
			if(resultado.isPresent())
			{
				List<Entidad> establecimientos =  resultado.get();
				establecimientos.stream().map(Entidad::getAtributovarchar).forEach(System.out::println);
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//getMapa
	
	/**
	 * CASO ESTUDIO 18. Get con predicado
	 */
	@SuppressWarnings("unchecked")
	public static void getPredicado()
	{
		try
		{			
			Predicate<Object> predicado = p -> p.equals("LITERAL");			
			Optional<List<Entidad>> resultado = (Optional<List<Entidad>>) unidad.get(Entidad.class, predicado);
			if(resultado.isPresent())
			{
				List<Entidad> establecimientos =  resultado.get();
				establecimientos.stream().map(Entidad::getAtributovarchar).forEach(System.out::println);
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}//getPredicado		
	
	/**
	 * CASO ESTUDIO 19. Get columna timestamp
	 */
	@SuppressWarnings("unchecked")
	public static void getEntidad2TimestampColumn()
	{
		try
		{
			Optional<List<Entidad2>> entidades = (Optional<List<Entidad2>>) unidad.get(Entidad2.class);
			if(entidades.isPresent())
			{
				List<Entidad2> entidades2 = entidades.get();
				entidades2.stream().map(Entidad2::getAtributo_timestamp).forEach(System.out::println);
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//getEntidad2TimestampColumn
	
	/**
	 * CASO ESTUDIO 20. Lazy
	 */
	@SuppressWarnings("unchecked")
	public static void getEstablecimientos()
	{
		try
		{
			Optional<List<Establecimiento>> entidades = (Optional<List<Establecimiento>>) unidad.get(Establecimiento.class);
			if(entidades.isPresent())
			{
				List<Establecimiento> entidades2 = entidades.get();
				entidades2.stream().map(Establecimiento::getCodigo).forEach(System.out::println);
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}//getEstablecimientos
	
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	public static void fixOpcional()
	{
		try
		{
			App appe = new App();
			appe.setCodigo("vegan");
			Optional<?> resultado = unidad.get(appe);
						
			if(resultado.isPresent())
			{
				Object a = resultado.get();				
			}else
			{
				
			}
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void actualizarAPP()
	{
		try
		{
			Tokenizacion token = new Tokenizacion();
			token.setActivo("A");
			token.setTicket("b97s3cqldb5bsj9o43o9nimqtb");
			token.setApp(new App(0,"VEGAN"));
			Optional<?> tokeconsultado = unidad.get(token,"FECHA_CREACION");
			if(tokeconsultado.isPresent())
			{
				token = ((Tokenizacion)((List<?>)tokeconsultado.get()).get(0));
				token.setActivo("I");//Si existe, vamos a volverlo inactivo
			}
			unidad.actualizar(token,"FECHA_CREACION","APP");
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private static void consumer()
	{
		Consumer consumer = Prueba::printNamesConsumer;		 		 
		consumer.accept("Jeremy");
		consumer.accept("Paul");
		consumer.accept("Richard");
		
	}//consumer
	
	private static void printNamesConsumer(Object name) 
	{
        System.out.println(name);
    }
	
	public static void parametrosDinamicos(String...strings)
	{
		for(String valor: strings)
		{
			System.out.println(valor);
		}
	}

}//No borrar
