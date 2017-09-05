package prueba;

import java.util.List;

import co.com.vicorious.persistencia.anotaciones.Columna;
import co.com.vicorious.persistencia.anotaciones.ID;
import co.com.vicorious.persistencia.anotaciones.Tabla;
import co.com.vicorious.persistencia.anotaciones.UnoAMuchos;
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
 *    Esta clase nos encapsula la tabla "Establecimiento" de la base de datos
 *    
 *   */
@Tabla(nombre = "Establecimiento")
public class Establecimiento 
{
	@ID
	@Columna(nombre = "ID", tipoDato = TipoElementoDB.INTEGER)
	private int id;
	
	@Columna(nombre = "CODIGO", tipoDato = TipoElementoDB.TEXT)
	private String codigo;
	
	@Columna(nombre = "DIRECCION", tipoDato = TipoElementoDB.TEXT)
	private String direccion;
	
	@Columna(nombre = "TELEFONO", tipoDato = TipoElementoDB.TEXT)
	private String telefono;
	
	@Columna(nombre = "PRECIO_ENVIO", tipoDato = TipoElementoDB.INTEGER)
	private int precioenvio;
	
	@Columna(nombre = "IMAGEN", tipoDato = TipoElementoDB.TEXT)
	private String imagen;
	
	@UnoAMuchos(lazy = true)
	@Columna
	private List<Producto> productos;

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
	 * @return: codigo de la entidad
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
	 * @return: direccion de la entidad
	 */
	public String getDireccion() 
	{
		return direccion;
	}

	/**
	 * Setter
	 * @param direccion: direccion a asignar
	 */
	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}

	/**
	 * Getter
	 * @return telefono de la entidad
	 */
	public String getTelefono() 
	{
		return telefono;
	}

	/**
	 * Setter
	 * @param telefono: telefono a asignar
	 */
	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	/**
	 * Getter
	 * @return: precioenvio de la entidad
	 */
	public int getPrecioenvio() 
	{
		return precioenvio;
	}

	/**
	 * Setter
	 * @param precioenvio: precioenvio a asignar
	 */
	public void setPrecioenvio(int precioenvio) 
	{
		this.precioenvio = precioenvio;
	}

	/**
	 * Getter
	 * @return: imagen de la entidad
	 */
	public String getImagen() 
	{
		return imagen;
	}

	/**
	 * Setter
	 * @param imagen: imagen a asignar
	 */
	public void setImagen(String imagen) 
	{
		this.imagen = imagen;
	}

	/**
	 * Getter
	 * @return: productos de la entidad
	 */
	public List<Producto> getProductos() 
	{
		return productos;
	}

	/**
	 * Setter
	 * @param productos: productos a asignar
	 */
	public void setProductos(List<Producto> productos) 
	{
		this.productos = productos;
	}
			
}//NoBorrar
