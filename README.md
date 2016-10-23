# Pasae
Backend Java de proyecto para aprobar la materia PASAE.

Requerimientos
--------------
Es necesario para poder ejecutar la aplicación las siguientes herramientas:
* Java 8
* [Maven](https://maven.apache.org/)

Ejecutar
--------
Para ejecutar el proyecto se debe descaragar el repositorio:
```bash
git clone git@github.com:fedeotaran/pasae.git 
```
Ahora dentro del proyecto ejecutamos el comando para descargar las dependencias:
```bash
cd pasae && mvn package
```
Terminado esto podemos ejecuar el proyecto:
```bash
mvn spring-boot:run
```

Autenticación
-------------
Por el momento está configurada una autenticación básica por oauth2. El modo de
autenticación al servidor es `client_credentials` y las credenciales son:
```
client_id = foo
client_secret = bar
```
Con dichas credenciales se debe pedir el token a <http://localhost:8080/oauth/token>
La respuesta debería ser algo de este tipo:
```
{
  "access_token": "my_token",
  "token_type": "bearer",
  "expires_in": 43199
}
```

Finalmente, y haciendo uso del token, puedes entrar a <http://localhost:8080/greeting>

Ejemplo en curl de la cosulta:
```bash
curl -X GET -H "Authorization: Bearer my_token" -H "Cache-Control: no-cache" "http://localhost:8080/greeting"
```
