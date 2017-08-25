package co.com.vicorious.persistencia.anotaciones;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import co.com.vicorious.persistencia.enums.TipoElementoDB;
import co.com.vicorious.persistencia.enums.TipoParametro;

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
 * <p>Encargado de encapsular cualquier tipo de parametro (Nos sirve para marcar un campo como parametro para una funcion o un procedimiento)</p><br>
 * 
 * <code>
 * Ejemplo:
 * <br>
 * {@literal @}Parametro(TipoParametro = TipoParametro.IN, TipoElementoDB = TipoElementoDB.INTEGER, orden = 1)<br>
 * private String parametroentrada;
 * </code>
 * 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Parametro 
{
	TipoParametro tipoparametro() default TipoParametro.IN;
	TipoElementoDB tipodato();
	int orden() default 0;
}
