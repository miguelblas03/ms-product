# ms-product

Microservicio para administración de productos

## Configuración local

Para ejecutar la aplicación localmente, se necesita crear la bd y configurar variables de entorno, las tablas se crearán automáticamente al ejecutar la aplicación

### Pre requisitos

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

### Pruebas

Utilizar el postman adjunto

Nota: Crear una categoria para poder crear productos