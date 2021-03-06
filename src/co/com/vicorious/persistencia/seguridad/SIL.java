package co.com.vicorious.persistencia.seguridad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import co.com.vicorious.persistencia.excepciones.PersistenciaException;

/**
 *  <p>Seguridad</p>	
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
 * Este es mi mayor orgullo, puede que no sea el mejor, pero significa algo para mi. Investiguenlo
 * 
 * 
 *
 */
public abstract class SIL 
{
		    
    private static ArrayList<String> letras;
    private static ArrayList<String> numeros;
   /**
    * Constructor
    */
    public SIL() 
    {
       SIL.letras = new ArrayList<String>();
       SIL.numeros = new ArrayList<String>();
       fullNumeros();
       fullLetras();
    }//constructor
    /**
     * Encripta una cadena SIL
     * @param cadena a escriptar
     * @return Retorna la cadena encriptada
     */
    public static String encriptado(String cadena) 
    {
        // - , +
        // true, empeiza sumando, false empieza restando
        //& numeros negativos
        // / numeros de 2 cifras
        verificarCadena(cadena);
        ArrayList<String> diccionario = new ArrayList<String>();
        diccionario = letras;
        int contador = 1;
        char[] cadenasfinal = null;
        int base = 0;
        Map<String, Object> baseletra = new HashMap<String, Object>();
        Map<String, Object> mapa = new HashMap<String, Object>();
        String finalpass = "";
        StringBuilder constructorcadenas = new StringBuilder();
        boolean tipooperacion = true;

        for (int j = 0; j < diccionario.size(); j++) 
        {

            mapa.put(diccionario.get(j), contador);
            contador++;
        }
        cadenasfinal = cadena.toCharArray();
        if (isNumeric(String.valueOf(cadenasfinal[0]))) 
        {
            base = Integer.parseInt(String.valueOf(cadenasfinal[0]));
        } else 
        {
            for (Map.Entry<String, Object> elemento2 : mapa.entrySet()) 
            {

                if (elemento2.getKey().equals(String.valueOf(cadenasfinal[0]))) 
                {
                    base = Integer.parseInt(elemento2.getValue().toString());
                    baseletra.put(cadenasfinal[0] + "", base);
                    break;
                }
            }
        }
        if (baseletra.size() > 0) 
        {
            for (Map.Entry<String, Object> elemento2 : baseletra.entrySet()) 
            {

                finalpass = elemento2.getKey();
            }
        } else 
        {
            finalpass = String.valueOf(base);
        }
        for (int i = 0; i < cadenasfinal.length; i++) 
        {

            for (Map.Entry<String, Object> elemento2 : mapa.entrySet()) 
            {
                if (isNumeric(finalpass)) 
                {
                    if (i == 0) 
                    {
                        break;
                    } else 
                    {
                        if (isNumeric(String.valueOf(cadenasfinal[i]))) 
                        {
                            if (tipooperacion) 
                            {
                                if ((Integer.parseInt(String.valueOf(cadenasfinal[i])) + base) >= 10) {
                                    constructorcadenas.append("/");
                                }
                                constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) + base);
                                tipooperacion = false;

                                break;
                            } else 
                            {
                                if ((Integer.parseInt(String.valueOf(cadenasfinal[i])) - base) <= -10) 
                                {
                                    constructorcadenas.append("/");
                                }
                                constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) - base);
                                tipooperacion = true;
                                break;
                            }
                        } else 
                        {
                            if (elemento2.getKey().equals(String.valueOf(cadenasfinal[i]))) 
                            {
                                int temporal = Integer.parseInt(elemento2.getValue().toString());
                                if (tipooperacion) 
                                {
                                    temporal = temporal + Integer.parseInt(finalpass.toString());
                                    if (temporal > mapa.size()) 
                                    {
                                        constructorcadenas.append("��" + elemento2.getValue().toString());
                                        tipooperacion = false;
                                        break;
                                    }
                                    for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                    {
                                        if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                        {
                                            constructorcadenas.append(elemento3.getKey());
                                            tipooperacion = false;
                                        }

                                    }

                                } else 
                                {
                                    temporal = temporal - Integer.parseInt(finalpass.toString());
                                    if (temporal > 0) 
                                    {
                                        for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                        {
                                            if (Integer.parseInt(elemento3.getValue().toString()) == temporal) {

                                                constructorcadenas.append(elemento3.getKey());
                                                tipooperacion = true;
                                            }

                                        }
                                    } else 
                                    {
                                        constructorcadenas.append("&�" + elemento2.getKey());
                                        tipooperacion = true;
                                    }
                                }
                            }
                        }
                    }
                } else 
                {
                    //if(elemento2.getKey().equals(finalpass)) {
                    if (i == 0) 
                    {
                        break;
                    } else 
                    {
                        if (isNumeric(String.valueOf(cadenasfinal[i]))) 
                        {
                            if (tipooperacion) 
                            {
                                if ((Integer.parseInt(String.valueOf(cadenasfinal[i])) + base) >= 10) 
                                {
                                    constructorcadenas.append("/");
                                }
                                constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) + base);
                                tipooperacion = false;
                                break;
                            } else 
                            {
                                if ((Integer.parseInt(String.valueOf(cadenasfinal[i])) - base) <= -10) 
                                {
                                    constructorcadenas.append("/");
                                }
                                constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) - base);
                                tipooperacion = true;
                                break;
                            }
                        } else 
                        {
                            if (elemento2.getKey().equals(String.valueOf(cadenasfinal[i]))) 
                            {
                                int temporal = Integer.parseInt(elemento2.getValue().toString());
                                if (tipooperacion) {
                                    temporal = temporal + Integer.parseInt(baseletra.get(finalpass).toString());
                                    if (temporal > mapa.size()) 
                                    {
                                        constructorcadenas.append("��" + elemento2.getValue().toString());
                                        tipooperacion = false;
                                        break;
                                    }
                                    for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                    {
                                        if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                        {
                                            constructorcadenas.append(elemento3.getKey());
                                            tipooperacion = false;
                                            break;
                                        }

                                    }

                                } else 
                                {
                                    temporal = temporal - Integer.parseInt(baseletra.get(finalpass).toString());
                                    if (temporal > 0) 
                                    {
                                        for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                        
                                        {
                                            if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                            {
                                                constructorcadenas.append(elemento3.getKey());
                                                tipooperacion = true;
                                                break;
                                            }

                                        }
                                    } else 
                                    {
                                        constructorcadenas.append("&�" + elemento2.getKey());
                                        tipooperacion = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        finalpass += constructorcadenas.toString();
        String hash = getHashNumber(finalpass);
        finalpass = addHash(finalpass, hash);
        return finalpass;
        
    }//encriptado
    
    
     /**
      * Encargado de desencriptar una cadena
      * @param cadena: literal encriptado
      * @return literal en su estado original
      * @throws PersistenciaException genera cuando hay desbordamiento a la derecha de una coleccion fija
      */
     public static String desencriptar(String cadena) throws PersistenciaException
     {
         // - , +
         // true, empieza sumando, false empieza restando
         // valor de depreciacion  )
         // despreciacion absoluta �
         int contador = 1;
         char[] cadenas = null;
         String[] cadenasfinal = null;
         ArrayList<String> diccionario = new ArrayList<String>();
         diccionario = letras;
         cadena = reverseHash(cadena);
         int base = 0;
         Map<String, Object> baseletra = new HashMap<String, Object>();
         Map<String, Object> mapa = new TreeMap<String, Object>();
         String finalpass = "";
         StringBuilder constructorcadenas = new StringBuilder();
         boolean tipooperacion = false;

         try 
         {
             for (int j = 0; j < diccionario.size(); j++) 
             {

                 mapa.put(diccionario.get(j), contador);
                 contador++;

             }

             cadenas = cadena.toCharArray();
             cadenasfinal = new String[cadenas.length];
             for (int h = 0; h < cadenas.length; h++) 
             {
                 cadenasfinal[h] = String.valueOf(cadenas[h]);
             }
             if (isNumeric(String.valueOf(cadenasfinal[0]))) 
             {
                 base = Integer.parseInt(String.valueOf(cadenasfinal[0]));
             } else 
             {
                 for (Map.Entry<String, Object> elemento2 : mapa.entrySet()) 
                 {

                     if (elemento2.getKey().equals(String.valueOf(cadenasfinal[0]))) 
                     {
                         base = Integer.parseInt(elemento2.getValue().toString());
                         baseletra.put(cadenasfinal[0] + "", base);
                         break;
                     }
                 }
             }
             if (baseletra.size() > 0) 
             {
                 for (Map.Entry<String, Object> elemento2 : baseletra.entrySet()) 
                 {

                     finalpass = elemento2.getKey();
                 }
             } else 
             {
                 finalpass = String.valueOf(base);
             }
             for (int i = 0; i < cadenasfinal.length; i++) 
             {

                 for (Map.Entry<String, Object> elemento2 : mapa.entrySet()) 
                 {
                     if (isNumeric(finalpass)) 
                     {
                         if (i == 0) 
                         {
                             break;
                         } else 
                         {
                             if (isNumeric(String.valueOf(cadenasfinal[i]))) 
                             {
                                 if (tipooperacion) 
                                 {
                                     constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) +
                                                               base);
                                     tipooperacion = false;
                                     break;
                                 } else 
                                 {

                                     constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) -
                                                               base);
                                     tipooperacion = true;
                                     break;
                                 }
                             } else 
                             {
                                 if (elemento2.getKey().equals(String.valueOf(cadenasfinal[i]))) 
                                 {
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {
                                     if (cadenasfinal[i].equals("/") && isNumeric(cadenasfinal[i + 1])) 
                                     {
                                         StringBuilder suma = new StringBuilder();
                                         suma.append(cadenasfinal[i + 1]);
                                         suma.append(cadenasfinal[i + 2]);
                                         String temp = "";
                                         for (int m = 0; m < suma.length(); m++) {
                                             temp += suma.charAt(m);
                                         }
                                         cadenasfinal[i] = temp;
                                         cadenasfinal[i + 1] = ")";
                                         cadenasfinal[i + 2] = ")";
                                         i--;
                                         break;
                                     }else if(cadenasfinal[i].equals("/") && cadenasfinal[i + 1].equals("-") && isNumeric(cadenasfinal[i + 2])) 
                                     {
                                         StringBuilder suma = new StringBuilder();
                                         suma.append(cadenasfinal[i + 1]);
                                         suma.append(cadenasfinal[i + 2]);
                                         suma.append(cadenasfinal[i + 3]);
                                         String temp = "";
                                         for (int m = 0; m < suma.length(); m++) {
                                             temp += suma.charAt(m);
                                         }
                                         cadenasfinal[i] = temp;
                                         cadenasfinal[i + 1] = ")";
                                         cadenasfinal[i + 2] = ")";
                                         cadenasfinal[i + 3] = ")";
                                         i--;
                                         break;
                                     }
                                     }
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {
                                     if (String.valueOf(cadenasfinal[i]).equals("-") && isNumeric(cadenasfinal[i + 1])) 
                                     {
                                         if (cadenasfinal[i + 1] == cadenasfinal[cadenasfinal.length - 1]) 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";

                                             i--;
                                             break;

                                         }else if(cadenasfinal[i + 1] != cadenasfinal[cadenasfinal.length - 1]) 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";

                                             i--;
                                             break;
                                         }
                                         if (isNumeric(cadenasfinal[i + 2])) 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             suma.append(cadenasfinal[i + 2]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";
                                             cadenasfinal[i + 2] = ")";
                                             i--;
                                             break;


                                         } else 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";

                                             i--;
                                             break;

                                         }
                                     }
                                     }
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {
                                     if (String.valueOf(cadenasfinal[i]).equals("&") && cadenasfinal[i + 1].equals("�")) 
                                     {
                                         if (tipooperacion) 
                                         {
                                             constructorcadenas.append(String.valueOf(cadenasfinal[i + 2]));
                                             tipooperacion = false;
                                             cadenasfinal[i + 1] = ")";
                                             i = i + 2;
                                             break;
                                         } else 
                                         {
                                             constructorcadenas.append(String.valueOf(cadenasfinal[i + 2]));
                                             tipooperacion = true;
                                             cadenasfinal[i + 1] = ")";
                                             i = i + 2;
                                             break;
                                         }

                                     }
                                     }
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {

                                     if (cadenasfinal[i].equals("�") && cadenasfinal[i + 1].equals("�") && isNumeric(cadenasfinal[i + 2])) 
                                     {
                                         StringBuilder suma = new StringBuilder();
                                         String prueba = "";
                                         if (cadenasfinal[i + 2] == cadenasfinal[cadenasfinal.length - 1] ) 
                                         {
                                             suma.append(cadenasfinal[i + 2]);
                                             prueba = suma.toString();
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {

                                                
                                                 if (elemento3.getValue().toString().equals(suma)) 
                                                 {
                                                     if (tipooperacion) 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = false;
                                                         i = i + 2;
                                                         break;
                                                     } else 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = true;
                                                         i = i + 2;
                                                         break;
                                                     }
                                                 }
                                             }
                                         }
                                        
                                         

                                         if (isNumeric(cadenasfinal[i + 3])) 
                                         {
                                             suma.append(cadenasfinal[i + 2]);
                                             suma.append(cadenasfinal[i + 3]);
                                             prueba = suma.toString();
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {
                                                 if (elemento3.getValue().toString().equals(prueba)) 
                                                 {
                                                     if (tipooperacion) 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = false;
                                                         i = i + 3;;
                                                         break;
                                                     } else 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = true;
                                                         i = i + 3;
                                                         break;
                                                     }

                                                 }
                                             }
                                         } else 
                                         {
                                             suma.append(cadenasfinal[i + 2]);
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {                                            
                                                 if (elemento3.getValue().toString().equals(suma.toString())) 
                                                 {
                                                     if (tipooperacion) 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = false;
                                                         i = i + 2;
                                                         break;
                                                     } else 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = true;
                                                         i = i + 2;
                                                         break;
                                                     }
                                                 }
                                             }

                                         }
                                         break;
                                     }
                                     }
                                     int temporal = Integer.parseInt(elemento2.getValue().toString());
                                     if (tipooperacion) 
                                     {
                                         temporal = temporal + Integer.parseInt(finalpass.toString());

                                         for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                         {
                                             if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                             {
                                                 constructorcadenas.append(elemento3.getKey());
                                                 tipooperacion = false;
                                                 break;
                                             }

                                         }

                                     } else 
                                     {
                                         temporal = temporal - Integer.parseInt(finalpass.toString());
                                         if (temporal > 0) {
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {
                                                 if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                                 {

                                                     constructorcadenas.append(elemento3.getKey());
                                                     tipooperacion = true;
                                                     break;
                                                 }

                                             }
                                             break;
                                         } else 
                                         {
                                             constructorcadenas.append(String.valueOf(cadenasfinal[i + 1]));
                                             tipooperacion = true;
                                             i++;
                                         }
                                     }
                                 } else 
                                 {

                                 }
                             }
                         }
                     } else 
                     {
                         //if(elemento2.getKey().equals(finalpass)) {
                         if (i == 0) 
                         {
                             break;
                         } else 
                         {
                             if (isNumeric(String.valueOf(cadenasfinal[i]))) 
                             {
                                 if (tipooperacion) 
                                 {
                                     constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) +
                                                               base);
                                     tipooperacion = false;
                                     break;
                                 } else 
                                 {

                                     constructorcadenas.append(Integer.parseInt(String.valueOf(cadenasfinal[i])) -
                                                               base);
                                     tipooperacion = true;
                                     break;
                                 }
                             } else 
                             {
                                 if (elemento2.getKey().equals(String.valueOf(cadenasfinal[i]))) 
                                 {
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {
                                     if (cadenasfinal[i].equals("/") && isNumeric(cadenasfinal[i + 1])) 
                                     {
                                         StringBuilder suma = new StringBuilder();
                                         suma.append(cadenasfinal[i + 1]);
                                         suma.append(cadenasfinal[i + 2]);
                                         String temp = "";
                                         for (int m = 0; m < suma.length(); m++) 
                                         {
                                             temp += suma.charAt(m);
                                         }
                                         cadenasfinal[i] = temp;
                                         cadenasfinal[i + 1] = ")";
                                         cadenasfinal[i + 2] = ")";
                                         i--;
                                         break;
                                     }else if(cadenasfinal[i].equals("/") && cadenasfinal[i + 1].equals("-") && isNumeric(cadenasfinal[i + 2])) 
                                     {
                                         StringBuilder suma = new StringBuilder();
                                         suma.append(cadenasfinal[i + 1]);
                                         suma.append(cadenasfinal[i + 2]);
                                         suma.append(cadenasfinal[i + 3]);
                                         String temp = "";
                                         for (int m = 0; m < suma.length(); m++) 
                                         {
                                             temp += suma.charAt(m);
                                         }
                                         cadenasfinal[i] = temp;
                                         cadenasfinal[i + 1] = ")";
                                         cadenasfinal[i + 2] = ")";
                                         cadenasfinal[i + 3] = ")";
                                         i--;
                                         break;
                                     }
                                 }
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {
                                     if (String.valueOf(cadenasfinal[i]).equals("-") && isNumeric(cadenasfinal[i + 1])) 
                                     {
                                         if (cadenasfinal[i + 1] == cadenasfinal[cadenasfinal.length - 1]) 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";

                                             i--;
                                             break;

                                         }else if(cadenasfinal[i + 1] != cadenasfinal[cadenasfinal.length - 1]) 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";

                                             i--;
                                             break;
                                         }
                                         
                                         if (isNumeric(cadenasfinal[i + 2])) 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             suma.append(cadenasfinal[i + 2]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";
                                             cadenasfinal[i + 2] = ")";
                                             i--;
                                             break;


                                         } else 
                                         {
                                             StringBuilder suma = new StringBuilder();
                                             suma.append(cadenasfinal[i]);
                                             suma.append(cadenasfinal[i + 1]);
                                             for (int m = 0; m < suma.length(); m++) 
                                             {
                                                 if (m == 0) 
                                                 {

                                                 } else 
                                                 {
                                                     cadenasfinal[i] += suma.charAt(m);
                                                 }
                                             }
                                             cadenasfinal[i + 1] = ")";

                                             i--;
                                             break;

                                         }
                                     }
                                     }
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {
                                     if (String.valueOf(cadenasfinal[i]).equals("&") && cadenasfinal[i + 1].equals("�")) 
                                     {
                                         if (tipooperacion) 
                                         {
                                             constructorcadenas.append(String.valueOf(cadenasfinal[i + 2]));
                                             tipooperacion = false;
                                             cadenasfinal[i + 1] = ")";
                                             i = i + 2;
                                             break;
                                         } else 
                                         {
                                             constructorcadenas.append(String.valueOf(cadenasfinal[i + 2]));
                                             tipooperacion = true;
                                             cadenasfinal[i + 1] = ")";
                                             i = i + 2;
                                             break;
                                         }

                                     }
                                     }
                                     if(cadenasfinal[i] == cadenasfinal[cadenasfinal.length - 1] ) 
                                     {
                                         
                                     }else
                                     {

                                     if (cadenasfinal[i].equals("�") && cadenasfinal[i + 1].equals("�") && isNumeric(cadenasfinal[i + 2])) 
                                     {
                                         StringBuilder suma = new StringBuilder();
                                         String prueba = "";
                                         if (cadenasfinal[i + 2] == cadenasfinal[cadenasfinal.length - 1] ) 
                                         {
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {

                                                 suma.append(cadenasfinal[i + 2]);
                                                 prueba = suma.toString();
                                                 if (elemento3.getValue().toString().equals(suma)) 
                                                 {
                                                     if (tipooperacion) 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = false;
                                                         i++;
                                                         break;
                                                     } else 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = true;
                                                         i++;
                                                         break;
                                                     }
                                                 }
                                             }
                                         }
                                        
                                         

                                         if (isNumeric(cadenasfinal[i + 3])) 
                                         {
                                             suma.append(cadenasfinal[i + 2]);
                                             suma.append(cadenasfinal[i + 3]);
                                             prueba = suma.toString();
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {
                                                 if (elemento3.getValue().toString().equals(prueba)) 
                                                 {
                                                     if (tipooperacion) {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = false;
                                                         i = i + 3;;
                                                         break;
                                                     } else 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = true;
                                                         i = i + 3;
                                                         break;
                                                     }

                                                 }
                                             }
                                         } else 
                                         {
                                             suma.append(cadenasfinal[i + 2]);
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {

                                                 
                                                 if (elemento3.getValue().toString().equals(suma.toString())) 
                                                 {
                                                     if (tipooperacion) 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = false;
                                                         i = i + 2;
                                                         break;
                                                     } else 
                                                     {
                                                         constructorcadenas.append(elemento3.getKey());
                                                         tipooperacion = true;
                                                         i = i + 2;
                                                         break;
                                                     }
                                                 }
                                             }

                                         }
                                         break;
                                     }
                                     }
                                     int temporal = Integer.parseInt(elemento2.getValue().toString());
                                     if (tipooperacion) 
                                     {
                                         temporal = temporal + Integer.parseInt(baseletra.get(finalpass).toString());
                                         for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                         {
                                             if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                             {
                                                 constructorcadenas.append(elemento3.getKey());
                                                 tipooperacion = false;
                                                 break;
                                             }

                                         }

                                     } else 
                                     {
                                         temporal = temporal - Integer.parseInt(baseletra.get(finalpass).toString());
                                         if (temporal > 0) 
                                         {
                                             for (Map.Entry<String, Object> elemento3 : mapa.entrySet()) 
                                             {
                                                 if (Integer.parseInt(elemento3.getValue().toString()) == temporal) 
                                                 {

                                                     constructorcadenas.append(elemento3.getKey());
                                                     tipooperacion = true;
                                                     break;
                                                 }

                                             }
                                             break;
                                         } else 
                                         {
                                             constructorcadenas.append(String.valueOf(cadenasfinal[i + 1]));
                                             tipooperacion = true;
                                             i++;
                                         }
                                     }
                                 }
                             }
                         }

                     }
                 }
             }
             finalpass += constructorcadenas.toString();

         } catch (ArrayIndexOutOfBoundsException ex) 
         {
        	 throw new PersistenciaException(ex.getMessage());
                 
         }

         return finalpass;
     }//desencriptar
    /**
     * Verfica si una cadena es un numero o no
     * @param cadena a verificar
     * @return true si es numerico, false si no
     */
    private static boolean isNumeric(String cadena) 
    {
        try 
        {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) 
        {
            return false;
        }
    }//isNumeric
    /**
     * Verifica una cadena y agrega a la base de datos los caracteres desconocidos en ella
     * @param cadena: literal a evaluar
     */
    @SuppressWarnings("unused")
	private static void verificarCadena(String cadena) 
    {
        String caracteresdesconocidos = new String();
        int contadora = 0;
        int contadoraNumeros = 0;
        for(int i = 0; i < cadena.length(); i++) {
            contadora = 0;
            for(String elemento: letras)
            {
                if(String.valueOf(cadena.charAt(i)).equals(elemento))
                {
                    contadora++;
                    break;
                }
                
            }
            
            //numeros
            
            for(String elemento: numeros)
            {
                contadoraNumeros = 0;
                if(String.valueOf(cadena.charAt(i)).equals(elemento))
                {
                    contadoraNumeros++;
                    break;
                }
                
            }
            
            
            if(contadora == 0 && contadoraNumeros == 0) 
            {
                caracteresdesconocidos += cadena.charAt(i);
                letras.add(String.valueOf(cadena.charAt(i)));
            }
            
        }        
    }//verificarCadena
    
    /**
     * Nos da el hash de una cadena encriptada
     * @param cadena: literal a generar el Hash
     * @return Hash completo
     */
    @SuppressWarnings("unused")
    private static String getHashNumber(String cadena) 
    {
        int contador = 0;
        int contadora = 0;
        String cadenanumerica = "";
        // False si empieza restando
        boolean flag = false;
        HashMap<String, Integer> mapa = new HashMap<String, Integer>();
        for (int j = 0; j < letras.size(); j++) 
        {
            mapa.put(letras.get(j), contador);
            contador++;
        }
            for(int i = 0; i < cadena.length(); i++) 
            {
                contadora = 0;
                for(Map.Entry<String, Integer> elemento: mapa.entrySet())
                {
                    if(String.valueOf(cadena.charAt(i)).equals(elemento.getKey()))
                    {
                        cadenanumerica += String.valueOf(elemento.getValue());
                        break;
                    }
                    
                }
                
            }
            //Operamos numeros
            int numeroactual = 0;
            int numerofinal = 0;
            for(int i = 0; i < cadenanumerica.length(); i++) 
            {   
                try
                {
                if(flag && String.valueOf(cadenanumerica.charAt(i + 1)) != null) 
                
                {
                    if(i == 0)
                    {
                    numeroactual = Integer.parseInt(String.valueOf(cadenanumerica.charAt(i)));
                    numerofinal = numeroactual + Integer.parseInt(String.valueOf(cadenanumerica.charAt(i + 1)));
                    flag = false;
                    i++;
                    }else 
                    {
                        numeroactual = Integer.parseInt(String.valueOf(cadenanumerica.charAt(i)));
                        numerofinal = numerofinal + Integer.parseInt(String.valueOf(cadenanumerica.charAt(i)));  
                        flag = false;
                    }
    
                }else if(!flag && String.valueOf(cadenanumerica.charAt(i + 1)) != null) 
                {
                    if(i == 0)
                    {
                    numeroactual = Integer.parseInt(String.valueOf(cadenanumerica.charAt(i))); 
                    numerofinal = numeroactual - Integer.parseInt(String.valueOf(cadenanumerica.charAt(i + 1)));
                    flag = true;
                    i++;
                    }else 
                    {
                        numeroactual = Integer.parseInt(String.valueOf(cadenanumerica.charAt(i))); 
                        numerofinal = numerofinal - Integer.parseInt(String.valueOf(cadenanumerica.charAt(i)));
                        flag = true;
                    }
                }
                }catch(Exception e) 
                {
                    if(flag) 
                    {
                        numerofinal = numerofinal + Integer.parseInt(String.valueOf(cadenanumerica.charAt(i)));
                    }else 
                    {
                        numerofinal = numerofinal - Integer.parseInt(String.valueOf(cadenanumerica.charAt(i)));
                    }
                }
            }
            return String.valueOf(numerofinal);
    }//getHashNumber
    /**
     * Agrega el hash a la primera posicion y envia la base a la ultima
     * @param cadena: literal
     * @param hash: hash generado al literal
     * @return
     */
    private static String addHash(String cadena, String hash) 
    {
        String cadenafinal = hash + "�";
        for(int i = 1; i < cadena.length(); i++) {
             cadenafinal += cadena.charAt(i);
        }
        cadenafinal += cadena.charAt(0);
        return cadenafinal;
    }//addHash
    /**
     * Agrega la base a la primera y elimina el Hash
     * @param cadena: literal a reversar
     * @return literal reversado
     */
     public static String reverseHash(String cadena) 
     {
         String cadenafinal = "";
         int index = 1;
         
         for(int i = 0; i < cadena.length() - 1; i++) {
            if(cadena.charAt(i) != '�') 
                index++;
                else 
                break;
         }
         for(int i = index; i < cadena.length() - 1; i++) 
         {
             
              cadenafinal += cadena.charAt(i);
         }
         cadenafinal  = cadena.charAt(cadena.length() - 1) + cadenafinal  ;
         return cadenafinal;
     }//reverseHash
    /**
     * Nos devuelve los caracteres de la base de datos
     * @return coleccion con las letras
     */
    public static ArrayList<String> getLetras() 
    {
        return letras;
    }//getLetras
    
    /**
     * 
     */
    private void fullNumeros() 
    {
        numeros.add("1");
        numeros.add("2");
        numeros.add("3");
        numeros.add("4");
        numeros.add("5");
        numeros.add("6");
        numeros.add("7");
        numeros.add("8");
        numeros.add("9");
        numeros.add("0");
    }//fullNumeros
    /**
     * 
     */
    private void fullLetras() 
    {
        letras.add("a");
        letras.add("b");
        letras.add("c");
        letras.add("d");
        letras.add("e");
        letras.add("f");
        letras.add("g");
        letras.add("h");
        letras.add("i");
        letras.add("j");
        letras.add("k");
        letras.add("l");
        letras.add("m");
        letras.add("n");
        letras.add("�");
        letras.add("o");
        letras.add("p");
        letras.add("q");
        letras.add("r");
        letras.add("s");
        letras.add("t");
        letras.add("u");
        letras.add("v");
        letras.add("w");
        letras.add("x");
        letras.add("y");
        letras.add("z");
        letras.add(".");
        letras.add("-");
        letras.add("?");
        letras.add("*");
        letras.add("�");
        letras.add("&");
        letras.add("/");
        letras.add("@");
        letras.add("+");
        letras.add(",");
        letras.add("_");
        letras.add("_");
        letras.add("�");
        letras.add("(");
    }//fullLetras 

}//No borrar
