package co.com.vicorious.persistencia.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  <p>Anotacion</p>	
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
 * <p>Encargado de encapsular la ruta de un properties un una clase</p><br>
 * 
 * 
 * <p>Ubicacion de mi properties = C:/mi/file/system/myproperties.properties<p><br>
 * 
 * <code>
 * Ejemplo:
 * <br>
 * MYPROPERTIES.java<br>
 * 
 * {@literal @}PropertiesConfig(ruta = "C:/mi/file/system/myproperties.properties")<br>
 * public class MyProperties{...}
 * 
 * </code>
 * 
 * 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PropertiesConfig
{
	String ruta();

}//No borrar
