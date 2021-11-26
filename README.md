# Tecnicatura Universitaria en Desarrollo de Aplicaciones Informaticas
## Arquitectura Tp4 - 2021

## Trabajo Practico Integrador n°5 - Informe Final

### Alumnos: 
- Anselmo, Geronimo. 
- Notti, Agustina. 
- Scaminaci, Luciano.
- Perrotta, Rosario.


---
### Introduccion
<p style='text-align: justify;'> El presente informe se presenta en el marco de la cátedra Arquitecturas Web de la carrera Tecnicatura Universitaria en Desarrollo de Aplicaciones Informáticas (TUDAI) y tiene como objetivo realizar una descripción del diseño de la aplicación desarrollada para la gestión de una despensa. 

Asimismo, se presentan algunos diagramas que permiten representar el diseño de la solución de manera gráfica. 


### Desarrollo
<p style='text-align: justify;'>Para la implementación del siguiente trabajo final, se creó un proyecto utilizando como gestor de dependencias Maven. Esta herramienta genera un Project Object Model <strong>(POM)</strong> donde se configuran las dependencias que el proyecto tendrá.

<p style='text-align: justify;'>A su vez, se utilizó el framework de Spring Boot, la cual es una infraestructura ligera que simplifica el trabajo de configurar las aplicaciones basadas en Spring. Principalmente, nos provee del uso de notaciones, tales como Repository, Service, RestController, RequestMapping,  Autowired, entre otras que nos permiten crear nuestras propias API y desplegar nuestros servicios REST para que podamos interactuar con otros servicios. 

<p style='text-align: justify;'>En primer lugar, tenemos el paquete de entidades, donde se desarrollaron las clases que representan las entidades de Cliente, Producto y Venta. Una entidad representa una tabla almacenada en una base de datos, la cual posee datos que pueden persistirse. Cuya instancia representa una fila en la tabla. Una entidad es representada por medio de la notación @Entity.

<p style='text-align: justify;'>Por otra parte, implementamos los repositorios de cada entidad por medio de la notación <span style='font-style: italic;'>@Repository</span>, esto nos indica que la clase define un repositorio de datos que está destinado a interactuar con la fuente de datos ya que es la interfaz encargada de las acciones de acceso a la base de datos y su persistencia. 

<p style='text-align: justify;'>Luego, se implementaron los servicios por medio de la notación <span style='font-style: italic;'>@Service</span> para cada uno de los repositorios. La anotación <span style='font-style: italic;'>@Service</span> registra esta clase en el marco de Spring para que otras clases puedan inyectarse con ella. El servicio actúa como intermediario entre el controlador y el repositorio, y cumple la función de llevar a cabo la lógica de negocio y procesamiento de los datos. El servicio se comunica con un repositorio, encargado de llevar a cabo las operaciones de lectura/escritura sobre la base de datos. 

<p style='text-align: justify;'> 
Por último, se implementaron los controladores con la anotación <span style='font-style: italic;'>@RestController</span>, la cual le dice a la aplicación Spring Boot que las solicitudes HTTP serán manejadas por esta clase. El controlador se ocupa de capturar los request que entran en la aplicación y derivan la consulta al servicio correspondiente. También se utilizó para el mapeo de la API <span style='font-style: italic;'>@RequestMapping</span> la cual asigna una ruta de solicitud o patrón específico al controlador. Por ejemplo,  para el caso de Ventas todas las solicitudes a los puntos finales REST se antepondrán con “ /ventas” de la misma forma para los distintos endpoints de la aplicación. 

<p style='text-align: justify;'> Para la resolución de los servicios de reporte solicitados en relación a  los clientes y el monto total de sus compras y las ventas por día. Se implementó un patrón DTO - Data Transfer Object - el cual es una entidad de transferencia datos para devolver solo los campos de la entidad relevantes a la consulta. El patrón DTO tiene como finalidad crear un objeto plano (POJO) con una serie de atributos que puedan ser enviados o recuperados del servidor en una sola invocación, de tal forma que un DTO puede contener información de múltiples fuentes o tablas y concentrarlas en una única clase simple. 


---

- [Documentacion en Swagger de los endpoint de la API REST](https://tudai-arqui-tp5.herokuapp.com/swagger-ui/)


### Diagrama vista de Modulos
![Vista_modulos](https://user-images.githubusercontent.com/52261739/143509340-34745fa5-e024-4590-b4d5-881edd1b63e6.jpg)

### Diagrama Vista Componentes y Conectores
![Vista_C C](https://user-images.githubusercontent.com/52261739/143509401-46da8421-1f00-4816-9393-28e6453dfb25.jpeg)
  
### Diagrama Entidad-Relacion
![DER](https://user-images.githubusercontent.com/52261739/143509414-37163bd3-643a-43f9-9c13-9935c02224ef.jpg)


