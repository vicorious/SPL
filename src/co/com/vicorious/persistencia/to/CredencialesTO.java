package co.com.vicorious.persistencia.to;

import co.com.vicorious.persistencia.anotaciones.Properties;
import co.com.vicorious.persistencia.anotaciones.PropertiesConfig;
import co.com.vicorious.persistencia.utilidades.Constantes.ConstantesArchivos;

/**
 *  <p>TO</p>	
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
 * Transfer Object (TO) encargado de encapsular la configuracion de las credenciales (credenciales.properties)
 * 
 * usuario = elusuario
 * contrasena = lacontrasena
 * basededatos = labasededatos(instancia)
 * puerto = elpuerto
 * host = laip
 * 
 * 
 *
 */
@PropertiesConfig(ruta = ConstantesArchivos.ARCHIVO_CREDENCIALES_PROPERTIES)
public class CredencialesTO 
{
	@Properties(llave = "usuario")
	private String usuario;
	@Properties(llave = "contrasena")
	private String contrasena;
	@Properties(llave = "basededatos")
	private String basededatos;
	@Properties(llave = "puerto")
	private String puerto;
	@Properties(llave = "host")
	private String host;
	private String tipoBaseDeDatos;
	
	public CredencialesTO()
	{
		
	}
	
	/**
	 <p>TO</p>	
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
	 * <h3>Descripcion </h3>
	 * 
	 * Clase encargada de construir de manera simple las Credenciales
	 */
	public static class Builder
	{
		private String usuario;
		private String contrasena;
		private String basededatos;
		private String puerto;
		private String host;
		private String tipoBaseDeDatos;		
		
		/**
		 * Setter
		 * @param usuario: usuario a asignar a las credenciales
		 * @return Builder semiconstruido
		 */
		public CredencialesTO.Builder setUsuario(String usuario)
		{
			this.usuario = usuario;
			return this;
		}
		
		/**
		 * Setter
		 * @param contrasena: contrasena a asignar a las credenciales
		 * @return Builder semiconstruido
		 */ 
		public CredencialesTO.Builder setContrasena(String contrasena)
		{
			this.contrasena = contrasena;
			return this;
		}
		
		/**
		 * Setter
		 * @param basedatos: basedatos a asignar
		 * @return Builder semiconstruido
		 */
		public CredencialesTO.Builder setBaseDatos(String basedatos)
		{
			this.basededatos = basedatos;
			return this;
		}
		
		/**
		 * Setter
		 * @param puerto: puerto a asignar
		 * @return Builder semiconstruido
		 */
		public CredencialesTO.Builder setPuerto(String puerto)
		{
			this.puerto = puerto;
			return this;
		}
		
		/**
		 * Setter
		 * @param host: host a asignar
		 * @return Builder semiconstruido
		 */
		public CredencialesTO.Builder setHost(String host)
		{
			this.host = host;
			return this;
		}
		
		/**
		 * Getter
		 * @return: usuario del TO
		 */
		public String getUsuario() 
		{
			return usuario;
		}

		/**
		 * Getter
		 * @return: contrasena del TO
		 */
		public String getContrasena() 
		{
			return contrasena;
		}

		/**
		 * Getter
		 * @return: basededatos del TO
		 */
		public String getBasededatos() 
		{
			return basededatos;
		}

		/**
		 * Getter
		 * @return: puerto del TO
		 */
		public String getPuerto() 
		{
			return puerto;
		}

		/**
		 * Getter
		 * @return: host del TO
		 */
		public String getHost() 
		{
			return host;
		}
					
		/**
		 * Getter
		 * @return: tipo base de datos del TO
		 */
		public String getTipoBaseDeDatos() 
		{
			return tipoBaseDeDatos;
		}

		/**
		 * Setter
		 * @param tipoBaseDeDatos: tipobasedatos a asignar
		 */
		public void setTipoBaseDeDatos(String tipoBaseDeDatos) 
		{
			this.tipoBaseDeDatos = tipoBaseDeDatos;
		}

		/**
		 * Metodo encargado de tangenciar lo hecho por el Builder
		 * @return credenciales complemente construidas
		 */
		public CredencialesTO contruirCredenciales()
		{
			return new CredencialesTO(this);
			
		}//contruirCredenciales
		
						
	}//No borrar Builder
	
	/**
	 * Contructor Builder
	 * @param builder: builder a asignar
	 */
	private CredencialesTO(CredencialesTO.Builder builder)
	{
		this.usuario  = builder.getUsuario();
		this.contrasena = builder.getContrasena();
		this.host = builder.getHost();
		this.basededatos = builder.getBasededatos();
		this.puerto = builder.getPuerto();
		this.tipoBaseDeDatos = builder.getTipoBaseDeDatos();
		
	}//Constructor
	
	
	
	/**
	 * 
	 * 
	 * GETTERS AND SETTERS
	 * 
	 * 
	 */
	
	/**
	 * Getter
	 * @return Atributo usuario
	 */
	public String getUsuario() 
	{
		return usuario;
	}
	
	/**
	 * Setter
	 * @param usuario: usuario a asignar
	 */
	public void setUsuario(String usuario) 
	{
		this.usuario = usuario;
	}
	
	/**
	 * Getter
	 * @return Atributo contrasena
	 */
	public String getContrasena() 
	{
		return contrasena;
	}
	
	/**
	 * Setter
	 * @param contrasena: contrasena a asignar
	 */
	public void setContrasena(String contrasena) 
	{
		this.contrasena = contrasena;
	}
	
	/**
	 * Getter
	 * @return Atributo base de datos
	 */
	public String getBasededatos() 
	{
		return basededatos;
	}
	
	/**
	 * Setter
	 * @param basededatos: basededatos a asignar
	 */
	public void setBasededatos(String basededatos) 
	{
		this.basededatos = basededatos;
	}
	/**
	 * Getter
	 * @return Atributo Puerto
	 */
	public String getPuerto() 
	{
		return puerto;
	}
	
	/**
	 * Setter
	 * @param puerto: puerto a asignar
	 */
	public void setPuerto(String puerto) 
	{
		this.puerto = puerto;
	}
	
	/**
	 * Getter
	 * @return El atributo Host
	 */
	public String getHost() 
	{
		return host;
	}
	
	/**
	 * Setter
	 * @param host: host a asignar
	 */
	public void setHost(String host) 
	{
		this.host = host;
	}	
	
	/**
	 * Getter 
	 * @return Atributo tipo de base de datos
	 */
	public String getTipoBaseDeDatos() 
	{
		return tipoBaseDeDatos;
	}

	/**
	 * Setter
	 * @param tipoBaseDeDatos: TiposBaseDatos a asignar
	 */
	public void setTipoBaseDeDatos(String tipoBaseDeDatos) 
	{
		this.tipoBaseDeDatos = tipoBaseDeDatos;
	}
	
	
}//No borrar
