# SPL
Framework de persistencia
## Caracteristicas:
- Consultar Tablas **Ejemplo:**  *@Tabla* 
- Capacidad de secuencias por ID.  **Ejemplo:** *@ID(Secuencia = 'lasecuencia')*.
- Consultar Tablas (_Relaciones uno a muchos_)**Ejemplo:**  *@UnoAMuchos* .
- Consultar Tablas (_Relaciones muchos a uno_) **Ejemplo:** *@MuchosAUno* .
- Insertar en tablas.
- Actualizar en tablas.
- Elimina en tablas.
- Consumo de procedimientos almacenados**Ejemplo:** *@Procedimiento* .
- Consumo de funciones **Ejemplo:** *@Funcion* .
- Consumo de archivos properties **Ejemplo:** *@PropertiesConfig, @Properties* .
- Ejecutar SQL Compuestos (_JOIN´S_) .
- Traza log (_log_programatico.txt_).
- Archivos de configuracion automaticos (_Se crean y se gestionan todos_).
- Manejo de algoritmos de tokenizacion (_UUID , lineal_).
- Consultar tablas anidadas (_Relacion a si mismas_).
- Implementacion algoritmo de seguridad (_SIL_).

## Implementacion:

En nuestro repositorio **GIT** [SPL Git](https://github.com/vicorious/SPL/)  encontramos una carpeta con el nombre DIST
> ![Dist Github](/raw/images/dist.PNG)
<br />
> encontraremos en nuestra carpeta un archivo con el nombre **SPL.jar**
> ![SPL jar](/raw/images/SPL_jar.PNG)
Este archivo es el que **Debemos incluir en nuestro proyecto para poder utilizar a SPL**

Si observamos nuestro jar de prueba **SPL_CLIENTE**. [GIT SPL Cliente](https://github.com/vicorious/SPL_CLIENTE)
> ![SPL Pruebas](/raw/images/SPL_CLIENTE.PNG)
> Observamos que existe una carpeta llamada **lib**
> ![Lib SPL Pruebas](/raw/images/lib.PNG)
> y dentro de esta carpeta encontramos el archivo con nombre **SPL.jar**
> ![SPL Jar en SPL Cliente](/raw/images/SPL_JAR_CLIENTE.PNG)



