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

En nuestro repositorio **GIT** [SPL GIT](https://github.com/vicorious/SPL/)  encontramos una carpeta con el nombre DIST
> ![Dist Github](/raw/images/dist.PNG)

encontraremos en nuestra carpeta un archivo con el nombre **SPL.jar**

> ![SPL jar](/raw/images/SPL_jar.PNG)

Este archivo es el que **Debemos incluir en nuestro proyecto para poder utilizar a SPL**

## SPL Cliente:
Si observamos nuestro jar de prueba **SPL_CLIENTE**. [GIT SPL Cliente](https://github.com/vicorious/SPL_CLIENTE)
> ![SPL Pruebas](/raw/images/SPL_CLIENTE.PNG)

 Observamos que existe una carpeta llamada **lib**
> ![Lib SPL Pruebas](/raw/images/lib.PNG)

 y dentro de esta carpeta encontramos el archivo con nombre **SPL.jar**
> ![SPL Jar en SPL Cliente](/raw/images/SPL_JAR_CLIENTE.PNG)

Estas dos versiones de los archivos **SPL.jar** deben ser iguales. para verificar el correcto funcionamiento de nuestro cliente, debemos descargar nuestro proyecto.

> ![Cliente descargado](/raw/images/SPL_CLIENTE_DESCARGAR.PNG)

Una vez descomprimido, vamos a abrir nuestro **IDE Eclipse**, 
La version utilizada para probar el cliente y la construccion de SPL es:
> ![Version eclipse](/raw/images/version_eclipse.PNG)

Cuando lo tengamos abierto (Si necesitas descargar el IDE utilizado aqui lo expongo)
 [URL Descargar Eclipse](https://1drv.ms/u/s!Ap97F-3qa8F6xnkpnoYSKXxyCvj5)  

Vamos a la opcion **File -> Import**

> ![Importar eclipse](/raw/images/importar_proyecto.PNG)

Elegimos la opcion **Projects from Folder or Archive** Vamos a la opcion **Siguiente**

> ![Importar eclipse](/raw/images/importar_proyecto2.PNG)

Ahora como vemos en la imagen, en la opcion **_Import Source_** el boton **_Directory..._** abrira una ventana como la siguiente:

> ![Importar eclipse](/raw/images/importar_proyecto3.PNG)

Elegimos el proyecto que descargamos y vamos a **Aceptar**
encontraremos una ventana como la siguiente:


> ![Importar eclipse](/raw/images/importar_proyecto4.PNG)

Luego vamos a darle al boton **Finish**

y Nuestro proyecto esta contextualizado en eclipse:

> ![Importar eclipse](/raw/images/importar_proyecto5.PNG)

Para verificar que nuestro proyecto esta correctamente configurado, vamos a **dar click derecho** sobre nuestro proyecto **SPL_PRUEBAS** y vamos a la ultima opcion(**properties**).

> ![Importar eclipse](/raw/images/importar_proyecto6.PNG)

Aqui en la opcion **Java Build Path** encontramos que tenemos como dependencias en nuestro jar **2 jar nuevos**
se encuentra el jar para la conexion a **PostgreSQL 9.6**
se encuentra el jar para la conexion mediante **SPL**


#Disfruta!!!
 


