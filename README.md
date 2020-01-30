<h2>API REST para gestión de autos.</h2>

Se inicia mediante Spring Boot mediante el comando:

  mvn spring-boot:run
  
Levanta en el puerto 8081
  
Endpoints disponibles
  http://localhost:8081/cars
  http://localhost:8081/swagger-ui.html (en esta se encuentran los endpoints del Controller)

Base de Datos H2

  docker run -d -p 1521:1521 -p 81:81 -e H2_OPTIONS='-ifNotExists' -v data_h2:/opt/h2-data --name=h2 oscarfonts/h2:alpine
