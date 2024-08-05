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

### Pruebas desde local

Utilizar el postman adjunto (Se agregaron 2 carpetas donde contiene los endpoins para probar desde local y los endpoints para probar apuntando al api desplegado en Render)

Nota: Crear una categoría para poder crear productos

### Servicio externo

Para realizar la actualización de las estadísticas de manera asíncrona, se creo un servicio consumidor del evento:

El servicio se encuentra en: `https://github.com/miguelblas03/async-statistic`

### **Nota: Para que las estadisticas se actualicen de manera correcta, se requiere que el servicio asincrono se encuentre funcionando**

### Despliegue
La configuracion de Github actions y el uso de variables de entorno esta funcionando. 
Se guardar en AWS ECR y se realiza el despliegue en AWS ECS. Sin embargo, los microservicios no se pudieron desplegar por problemas de configuracion con la facturacion que pide AWS

AWS ECR
<img width="1838" alt="image" src="https://github.com/user-attachments/assets/58104f7e-b2f0-4301-886a-ca7ebcedf305">

AWS ECS
<img width="1186" alt="image" src="https://github.com/user-attachments/assets/c8743d3c-ee79-417f-8422-241a28238ad6">

## Para tener una instancia cloud de los contenedores, se realizo un despliegue de ambos microservicios mencionados previamente (**ms-product** y **async-statistic**) en Render:

`https://ms-product-n6ru.onrender.com`
`https://async-statistic.onrender.com`

# RENDER
<img width="1549" alt="image" src="https://github.com/user-attachments/assets/15b8f882-6174-4574-810d-4498d37c488c">

# **Pruebas al ms desplegado
<img width="1313" alt="image" src="https://github.com/user-attachments/assets/e07b84bb-cefc-4d82-a0aa-e8856fe2bcb7">

# **Eventos producidos desde ms-product
<img width="1433" alt="image" src="https://github.com/user-attachments/assets/e4e5120c-b7a6-4b9e-8a63-a44e5f69aa1b">


