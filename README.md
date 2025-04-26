# Api de contacto

Una API RESTful desarrollada con **Java 21** y **Spring Boot 3.4.5**, que permite a los usuarios enviar formularios de contacto desde una página web. Esta API fue creada como parte de una prueba técnica para modernizar el sistema actual de "Consultores Estratégicos Ltda.", el cual solo enviaba correos sin guardar registros.

Este nuevo enfoque permite no solo enviar notificaciones por correo, sino también guardar cada mensaje en la base de datos y consultar estadísticas básicas desde la misma API.

---

## Contexto del Problema

Actualmente, los formularios enviados desde el sitio web solo generan un correo electrónico sin dejar rastro alguno para futuras consultas o métricas. Esto limita la capacidad de análisis y seguimiento por parte del equipo administrativo.

### Necesidades que resolvemos
- Almacenamos cada envío del formulario en una base de datos con su fecha de creación.
- Notificamos al usuario (confirmación) y al administrador (alerta de nuevo contacto) por correo electrónico.
- Permitimos consultar la cantidad de formularios recibidos por día directamente desde la API.

---

## Objetivo de esta API

Crear un pequeño backend que reciba formularios de contacto desde la web, los valide, almacene, envíe notificaciones por correo y brinde métricas simples del uso del formulario.

---

## Configuración

### Variables de entorno necesarias

Estas deben colocarse en el archivo `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/prueba_metrix
    username: usuario_sql
    password: contraseña_sql
  jpa:
    hibernate:
      ddl-auto: update

mail:
  host: smtp.gmail.com
  port: 587
  username: correo@gmail.com
  password: contraseña_aplication  
  properties:
    mail:
      smtp:
        auth: true
        starttls:
          enable: true

contact:
  admin-email: admin@gmail.com
```

---

## Instalación de dependencias

Este proyecto utiliza **Maven** para la gestión de dependencias. Asegúrate de tener **Java 21** y Maven instalados.

En la raíz del proyecto, ejecuta:

```bash
mvn clean install
```

### Principales dependencias utilizadas

- `spring-boot-starter-web`: Exposición de endpoints REST.
- `spring-boot-starter-data-jpa`: Persistencia de datos con JPA.
- `spring-boot-starter-mail`: Envío de correos electrónicos.
- `spring-boot-starter-validation`: Validaciones automáticas en los datos recibidos.
- `springdoc-openapi-starter-webmvc-ui`: Generación automática de documentación Swagger.
- `mysql-connector-j`: Driver JDBC para conectarse a MySQL.
- `lombok`: Menos código repetitivo (getters, setters, etc.).
- `spring-boot-devtools` (opcional): Recarga automática durante el desarrollo.


---

## Cómo ejecutar la aplicación

### Opción 1: Ejecutar localmente

Si prefieres ejecutar la aplicación de manera local, puedes usar Maven. Solo ejecuta el siguiente comando:

```bash
mvn spring-boot:run
```

Una vez corriendo, la API estará disponible en:

```
http://localhost:8080
```

### Opción 2: Ejecutar con Docker

Si deseas ejecutar la aplicación usando Docker, sigue estos pasos:

1. **Asegúrate de tener Docker.**

2. **Construye las imágenes de Docker**:

   ```bash
   docker-compose build
   ```

3. **Levanta los contenedores de Docker**:

   ```bash
   docker-compose up
   ```

Esto iniciará tanto el contenedor de MySQL como el de la aplicación de Spring Boot.

4. **La API estará disponible en:**

   ```
   http://localhost:8081
   ```

   - El contenedor de MySQL estará corriendo en el puerto `3307`.
   - La aplicación Spring Boot estará disponible en el puerto `8081` por defecto, como está configurado en el `docker-compose.yml`.

---

## Ejemplos para probar la API

Puedes probar los endpoints manualmente con herramientas como [Postman](https://www.postman.com/) o directamente desde Swagger.

### Enviar formulario de contacto

```
POST /api/contact-submissions
```

Cuerpo esperado:
```json
{
  "full_name": "Juan Pérez",
  "email": "juan.perez@example.com",
  "country": "Colombia",
  "phone": "123456789",
  "message": "Quisiera más información sobre sus servicios."
}
```

Respuesta esperada:
```json
{
  "code": 201,
  "message": "Formulario enviado exitosamente",
  "data": null,
  "error_code": null
}
```

---

### Consultar envíos del día actual

```
GET /api/metrics/daily-submissions
```

Respuesta esperada:
```json
{
  "code": 200,
  "message": "Consulta de formularios diarios exitosa",
  "data": 8,
  "error_code": null
}
```

---

### Consultar formularios enviados por país

```
GET /api/metrics/submissions-by-country
```

Respuesta esperada:
```json
{
  "code": 200,
  "message": "Consulta de formularios por país exitosa",
  "data": {
    "Colombia": 5,
    "México": 3,
    "Perú": 2
  },
  "error_code": null
}
```

---

### Consultar formularios enviados en un rango de fechas

```
GET /api/metrics/submissions-by-date-range?start=2025-04-25&end=2025-04-26
```

Respuesta esperada:
```json
{
  "code": 200,
  "message": "Consulta de formularios por rango de fechas exitosa",
  "data": 15,
  "error_code": null
}
```

---

## Documentación interactiva (Swagger)

Puedes acceder a la documentación completa de la API (incluye descripciones, y pruebas) aquí:

```
http://localhost:8081/swagger-ui/index.html
```

---
## Preguntas de Razonamiento 

### 1. **Arquitectura y Diseño:**

**¿Por qué elegiste Spring Boot?**

Elegí **Spring Boot** debido a su capacidad de crear aplicaciones robustas y escalables de manera rápida y sencilla. Es un marco ampliamente adoptado en el ecosistema Java, lo que garantiza una gran comunidad de soporte, documentación extensa y compatibilidad con muchas herramientas y servicios. Además, Spring Boot facilita la integración con otras tecnologías como bases de datos, correo electrónico, validación, etc., de forma muy eficiente, lo que acelera el desarrollo. Además, cuenta con una comunidad bastante amplia, por lo que si algo falla o se implementa algo nuevo, encontraremos documentación y ejemplos de cómo integrarlo en nuestro sistema.

**Estructura del Proyecto:**

La estructura del proyecto sigue el patrón **Clean Architecture** y está organizada en capas bien definidas:

- **Controller**: Encargado de gestionar las solicitudes HTTP, en este caso, para recibir y responder los formularios de contacto.
- **UseCase**: Contiene la lógica de negocio, como la creación y envío de correos electrónicos.
- **Repository**: Interactúa directamente con la base de datos para guardar y consultar los formularios.
- **Domain**: Las entidades representan el modelo de datos y se mapean directamente a la base de datos (por ejemplo, `ContactEntity`).
- **IService**: Interfaz de servicio que nos ayuda a inyectar los métodos de un lado a otro.
- **Service**: Interactúa con el repository permitiendo las operaciones para la base de datos.

**Esquema de la Base de Datos:**

El diseño de la base de datos es sencillo pero eficiente. La tabla **ContactEntity** almacena los formularios con los campos necesarios: nombre, correo electrónico, país, teléfono, mensaje y una marca de tiempo de cuándo fue creado.

La base de datos se ha diseñado de manera que pueda escalar sin demasiados problemas, usando un campo de fecha para realizar consultas rápidas sobre los formularios enviados. La relación con los correos electrónicos y otros procesos de notificación no requiere tablas adicionales, lo que simplifica la estructura y mejora la eficiencia.

### 2. **Escalabilidad y Mejoras:**

**Cuellos de botella y estrategias de escalabilidad:**

Si la API debe manejar miles de envíos por hora, los principales cuellos de botella se podrían dar en:

- **Base de Datos**: Los registros masivos de datos y las consultas frecuentes pueden sobrecargar el rendimiento. Se podría mitigar con técnicas como la **indexación** en el campo de fecha y **sharding** (división de bases de datos) si la escala crece aún más.
- **Correo electrónico**: El envío de correos puede ser una operación costosa en términos de tiempo, especialmente si se debe enviar a un gran volumen de usuarios. Para escalar, implementaría una **cola de mensajes** (como **RabbitMQ** o **Kafka**) para manejar las notificaciones de correo en segundo plano y evitar bloquear el proceso de recepción del formulario. Cabe aclarar que se está usando una dependencia gratuita, pero si tenemos un servicio de envío de correos, se hará de una manera mucho más eficiente.

**Estrategias para escalar:**

1. **Optimización de la Base de Datos**: Asegurarse de que las consultas estén optimizadas, añadiendo índices a las columnas de búsqueda más frecuentes (por ejemplo, la fecha de envío).
2. **Colas de Mensajes para Notificaciones**: Como mencioné antes, las notificaciones por correo se podrían hacer asincrónicas usando un sistema de colas, lo que mejora la eficiencia.

**Funcionalidades adicionales para mejorar la API:**

1. **Autenticación y Autorización**: Implementar un sistema de autenticación (OAuth 2.0, JWT) para asegurar que solo los usuarios autorizados puedan acceder a ciertos endpoints, como consultar métricas o realizar configuraciones.
2. **Logging y Monitoreo**: Incorporar una solución de monitoreo (como **Prometheus**) para obtener información en tiempo real sobre el rendimiento y los errores de la API.

### 3. **Alternativas y Trade-offs:**

**¿Consideraste otras librerías o enfoques técnicos?**

- **Validación**: Aunque Spring Boot tiene un soporte robusto para validación usando anotaciones como `@NotBlank` y `@Email`, podría haberse considerado el uso de una librería adicional como **Hibernate Validator** para validaciones más complejas, pero Spring Boot ya incluye Hibernate Validator, por lo que no era necesario.
  
- **Acceso a la Base de Datos**: Otra alternativa para acceder a la base de datos podría haber sido usar **MyBatis** en lugar de **JPA**. MyBatis permite un control más fino sobre las consultas SQL, pero para esta prueba técnica, la simplicidad de JPA fue una elección más rápida y eficiente.

- **Envío de Correos**: Se podría haber utilizado una librería como **Apache Commons Email** en lugar de la integración nativa de Spring Boot con el **JavaMailSender**. Sin embargo, Spring Boot ya proporciona una integración limpia y fácil de configurar con correo, por lo que opté por esta opción para reducir la complejidad.

**Trade-offs**:

Hubo algunos compromisos debido al tiempo, como la implementación de una base de datos SQL más sencilla (usando solo una tabla para almacenar los datos del formulario) en lugar de un diseño más complejo que podría ser necesario si la aplicación se expande en el futuro.

### 4. **Endpoint de Métricas:**

**¿Cómo implementaste la lógica para `GET /api/metrics/daily-submissions`?**

La lógica para este endpoint es simple pero efectiva. Utilicé una consulta en la base de datos para contar la cantidad de registros de formulario enviados en el día actual, basándome en el campo `createdAt`, que se actualiza automáticamente al crear un nuevo formulario.

**Eficiencia**:

La consulta que se realiza es eficiente, ya que solo se consulta una tabla para contar los registros del día actual.

**Diseño de un endpoint para métricas por país o rango de fechas**:

Para mejorar el diseño de este endpoint y permitir la consulta de métricas por país o por un rango de fechas, lo ideal sería permitir a los usuarios pasar parámetros adicionales en la solicitud, como `country` o un rango de fechas (`startDate` y `endDate`). La consulta SQL podría ser extendida para filtrar por estos parámetros, lo que agregaría flexibilidad en la consulta de métricas.

---


## Resumen rápido de endpoints

| Método | Endpoint                          | Descripción                                |
|--------|-----------------------------------|--------------------------------------------|
| POST   | /api/contact-submissions          | Envía el formulario de contacto             |
| GET    | /api/metrics/daily-submissions    | Consulta cuántos formularios se enviaron hoy |
| GET    | /api/metrics/submissions-by-country | Consulta la cantidad de formularios enviados por país |
| GET    | /api/metrics/submissions-by-date-range | Consulta la cantidad de formularios enviados en un rango de fechas |

---

## Contacto

Este proyecto fue desarrollado como parte de una prueba técnica. Si deseas dejar algún comentario o sugerencia, puedes escribir a:

📩 `marlontorresmedina@gmail.com`