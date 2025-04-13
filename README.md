# sistema-educativo-microservicios-AndreyR-Andres

Este proyecto implementa un sistema educativo utilizando una arquitectura de microservicios con Spring Boot y Spring Cloud.

## Visión General del Sistema
El sistema permite la gestión de estudiantes, docentes, asignaturas y matrículas en un entorno educativo distribuido.

## Estructura del Repositorio
- **usuarios-servicio**: Gestión de estudiantes y docentes
- **asignaturas-servicio**: CRUD de materias y su información
- **matriculas-servicio**: Registro de estudiantes en materias
- **eureka-server**: Servidor de descubrimiento de servicios
- **config-server**: Servidor de configuración centralizada

## Tecnologías Utilizadas
- Spring Boot 3.2.4
- Spring Cloud 2023.0.0
- Spring Security con JWT
- Spring Data JPA
- MySQL
- Docker y Docker Compose
- Feign Client
- Eureka Discovery Server
- Spring Cloud Config

## Microservicios
1. **usuarios-servicio**: Puerto 8081
2. **asignaturas-servicio**: Puerto 8082
3. **matriculas-servicio**: Puerto 8083
4. **config-server**: Puerto 8888
5. **eureka-server**: Puerto 8761

## Ejecución del Proyecto
Instrucciones para ejecutar el proyecto usando Docker Compose:
```bash
docker-compose up -d
```

## Autor
[Andrey Ramirez]