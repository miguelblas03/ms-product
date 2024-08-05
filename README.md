# ms-product

Microservicio para administración de productos

## Configuración local

Para ejecutar la aplicación localmente, se necesita crear la bd y configurar variables de entorno, las tablas se crearán automáticamente al ejecutar la aplicación

### Prerrequisitos

Crear la base de datos en mysql server con el nombre `db_project`

### Variables de Entorno

- **`DB_URL`**: La URL de conexión a la base de datos.
  - `localhost:3306`
- **`DB_NAME`**: El nombre de la base de datos.
  - `db_project`
- **`DB_USERNAME`**: El usuario para acceder a la base de datos.
  - `root`
- **`DB_PASSWORD`**: La contraseña del usuario para acceder la base de datos.
  - `rootroot`
- **`USERNAME`**: El usuario que se usara para la autenticación.
  - `user`
- **`USER_PASSWORD`**: La contraseña del usuario que tendrá autorización.
  - `pass`
- **`BOOTSTRAP_SERVERS`**: Servidor donde se encuentra el topico.
  - `Se proporciona el dato por interno`
- **`CLUSTER_API_KEY`**: Api key del cluster.
  - `Se proporciona el dato por interno`
- **`CLUSTER_API_SECRET`**: Api secret del cluster.
  - `Se proporciona el dato por interno`

### Configuracion para descargar dependencias

Para poder producir y consumir eventos se creo una biblioteca con los datos del evento en común que luego sera usado desde el consumidor.

Agregar la siguiente configuración en `settings.xml`:

```
<servers>
  <server>
    <id>github</id>
    <username>GITHUB_TOKEN_MVN </username>
    <password>${token}</password>
  </server>
</servers>
```

Nota: reemplazar `${token}` por el dato proporcionado

La biblioteca se encuentra en: `https://github.com/miguelblas03/common-event-lib`

### Pruebas

Utilizar el postman adjunto

Nota: Crear una categoría para poder crear productos

### Servicio externo

Para realizar la actualización de las estadísticas de manera asíncrona, se creo un servicio consumidor del evento:

El servicio se encuentra en: `https://github.com/miguelblas03/async-statistic`

### **Nota: Para que las estadisticas se actualicen de manera correcta, se requiere que el servicio asincrono se encuentre funcionando**