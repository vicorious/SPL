package prueba;


import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.enums.TipoElementoDB;

@Tabla(nombre = "APP")
public class App 
{
	@ID(secuencia = "secuencia_id_app")
	@Columna(nombre = "ID", tipoDato = TipoElementoDB.INTEGER)
	private int id;
	
	@Columna(nombre = "CODIGO", tipoDato = TipoElementoDB.TEXT)
	private String codigo;
	
	
	public App(){};
			
	public App(int id, String codigo) 
	{
		super();
		this.id = id;
		this.codigo = codigo;
		
	}//Constructor

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getCodigo() 
	{
		return codigo;
	}

	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

					
}//NoBorrar
