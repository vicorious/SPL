package prueba;
import java.io.Serializable;
import java.time.LocalDateTime;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.MuchosAUno;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.enums.TipoElementoDB;

/**
 * 
 * 
 *
 */
@Tabla(nombre = "TOKENIZACION")
public class Tokenizacion implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ID(secuencia = "secuencia_id_token")
	@Columna(nombre = "ID", tipoDato = TipoElementoDB.INTEGER)
	private int id;
	
	@Columna(nombre = "TICKET", tipoDato = TipoElementoDB.TEXT)
	private String ticket;
	
	@Columna(nombre = "ACTIVO", tipoDato = TipoElementoDB.TEXT)
	private String activo;
	
	@Columna(nombre = "FECHA_CREACION", tipoDato = TipoElementoDB.TIMESTAMP, formato = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime fecha_creacion;

	@MuchosAUno(lazy = true)
	@Columna(nombre = "APP_ID", tipoDato = TipoElementoDB.INTEGER)
	private App app;
	
	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}
	
	public String getTicket() 
	{
		return ticket;
	}

	public void setTicket(String ticket) 
	{
		this.ticket = ticket;
	}

	public String getActivo() 
	{
		return activo;
	}

	public void setActivo(String activo) 
	{
		this.activo = activo;
	}

	public LocalDateTime getFecha_creacion() 
	{
		return fecha_creacion;
	}

	public void setFecha_creacion(LocalDateTime fecha_creacion) 
	{
		this.fecha_creacion = fecha_creacion;
	}

	public App getApp() 
	{
		return app;
	}

	public void setApp(App app) 
	{
		this.app = app;
	}
	
				
}//NoBorrar
