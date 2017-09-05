package prueba;

import java.io.Serializable;
import java.time.LocalDate;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.MuchosAUno;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.enums.TipoElementoDB;

/**
 *  <p>entidades</p>	
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
 *    Esta clase nos encapsula la tabla "PEDIDO" de la base de datos
 *    
 *   */
@Tabla(nombre = "PEDIDO")
public class PedidoTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ID
	@Columna(nombre = "ID", tipoDato = TipoElementoDB.INTEGER)
	private int id;
	
	@Columna(nombre = "CODIGO", tipoDato = TipoElementoDB.TEXT)
	private String codigo;
	
	@Columna(nombre = "C_PRECIO_TOTAL", tipoDato = TipoElementoDB.INTEGER)
	private int c_precio_total;
	
	@Columna(nombre = "FECHA_INICIAL_PEDIDO", tipoDato = TipoElementoDB.DATE, formato = "yyyy-mm-dd  HH:mm:ss")
	private LocalDate fecha_inicial_pedido;
	
	@Columna(nombre = "FECHA_FINAL_PEDIDO", tipoDato = TipoElementoDB.DATE, formato = "yyyy-mm-dd  HH:mm:ss")
	private LocalDate fecha_final_pedido;
	
	@MuchosAUno
	@Columna(nombre = "FORMA_PAGO_ID", tipoDato = TipoElementoDB.INTEGER)
	private FormaPagoTO forma_pago;

	/**
	 * Getter
	 * @return id de la entidad
	 */
	public int getId() 
	{
		return id;
	}

	/**
	 * Setter
	 * @param id: id a asignar
	 */
	public void setId(int id) 
	{
		this.id = id;
	}

	/**
	 * Getter
	 * @return codigo de la entidad
	 */
	public String getCodigo() 
	{
		return codigo;
	}

	/**
	 * Setter
	 * @param codigo: codigo a asignar
	 */
	public void setCodigo(String codigo) 
	{
		this.codigo = codigo;
	}

	/**
	 * Getter
	 * @return: precio total de la entidad
	 */
	public int getC_precio_total() 
	{
		return c_precio_total;
	}

	/**
	 * Setter
	 * @param c_precio_total: c_precio_total a asignar 
	 */
	public void setC_precio_total(int c_precio_total) 
	{
		this.c_precio_total = c_precio_total;
	}

	/**
	 * Getter
	 * @return: fecha inicial pedido de la entidad
	 */
	public LocalDate getFecha_inicial_pedido() 
	{
		return fecha_inicial_pedido;
	}

	/**
	 * Setter
	 * @param fecha_inicial_pedido: fecha_inicial_pedido a asignar 
	 */
	public void setFecha_inicial_pedido(LocalDate fecha_inicial_pedido) 
	{
		this.fecha_inicial_pedido = fecha_inicial_pedido;
	}

	/**
	 * Getter
	 * @return fecha final pedido de la entidad
	 */
	public LocalDate getFecha_final_pedido() 
	{
		return fecha_final_pedido;
	}

	/**
	 * Setter
	 * @param fecha_final_pedido: fecha_final_pedido a asignar
	 */
	public void setFecha_final_pedido(LocalDate fecha_final_pedido) 
	{
		this.fecha_final_pedido = fecha_final_pedido;
	}

	/**
	 * Getter
	 * @return forma de pago de la entidad
	 */
	public FormaPagoTO getForma_pago() 
	{
		return forma_pago;
	}

	/**
	 * Setter
	 * @param forma_pago: forma_pago a asignar
	 */
	public void setForma_pago(FormaPagoTO forma_pago) 
	{
		this.forma_pago = forma_pago;
	}
	
}//NoBorrar
