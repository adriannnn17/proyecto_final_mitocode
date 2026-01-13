# proyecto_final_mitocode

Breve descripción: Proyecto backend basado en Quarkus que gestiona reservas (entidades como Cliente, Profesional, HorarioDisponible, Reserva), con generación de APIs desde OpenAPI y persistencia en base de datos relacional.

Mayor info en la WIKI del proyecto en GitHub.

---

Instrucciones para ejecutar


- Una vez clonado el repositorio, para ejecutar la aplicación localmente:
- Cree la base de datos y las tablas.
- Debe ejecutar "mvn clean compile"
- Luego ejecutar el proyecto con "mvn quarkus:dev", pero para que funcione la conexion (si se usa azure sql) puede que deba ejecutarse 2 veces por tema de latencia.
- Si necesita informacion de los endpoints, acceder a http://localhost:8080/q/swagger-ui
- Se probo ejecutar quarkus en linux
- Se adjunto un archivo postman para probar los endpoints "Proyecto_Mitocode.postman_collection.json"


---

Decisiones técnicas

- Se uso AzureSQL como base de datos relacional, no reactiva, es por ello que se uso Panache ORM y se trabajo de forma transaccionar para trabajar con una conexion bloqueante.
- Se utilizo contract first en el proyecto, generando el codigo de los schemas y los endpoints usando el generador de Quarkus y APIDog para la documentacion del yml openapi de input
- Se utilizo Liquibase para versionar la BD, trabaja con la conexion no reactiva de quarkus
- Se utilizo MapStruct para los mapeos entre DTOs y entidades
- Se utilizo Lombok para reducir codigo boilerplate en los modelos y constructores
- Se utilizo Mutiny para programacion reactiva en las capas de servicio y controladores
- No se implementó JWT para no complicar la prueba y ejecucion del proyecto

---

✨ Características

- API REST generada a partir de OpenAPI (configurado en `application.properties`).
- Arquitectura con capas: domain, application, infraestructure, interfaces (API/requests/responses).
- Persistencia con Hibernate ORM / Panache y scripts de migración con Liquibase.
- Programación reactiva parcial mediante Mutiny (dependencia `quarkus-mutiny`).
- Mapeos entre DTOs y entidades con MapStruct; uso de Lombok para modelos/constructores.

---

🛠️ Tecnologías

- Java 17 (configurado en `pom.xml` - property `maven.compiler.release` = 17)
- Quarkus platform 3.30.5 (propiedad `quarkus.platform.version` en `pom.xml`)
- Mutiny (reactividad) — `quarkus-mutiny`
- Hibernate ORM / Panache — `quarkus-hibernate-orm`, `quarkus-hibernate-orm-panache`
- Liquibase — `quarkus-liquibase`
- MapStruct 1.6.3
- Lombok 1.18.42
- Quarkus OpenAPI generator (configurado en `application.properties`)
- Conector JDBC para Microsoft SQL Server (`quarkus-jdbc-mssql`)

---

📋 Prerrequisitos

- JDK 17 (el proyecto está configurado para Java 17 en `pom.xml`).
- Maven (el proyecto utiliza `pom.xml` para build y dependencias).
- Base de datos configurada según `src/main/resources/application.properties` (tipo: mssql).

---

📦 Dependencias principales (extraídas de `pom.xml`)

| Dependencia | Versión (si está en pom) | Licencia | Propósito |
|---|---:|---|---|
| io.quarkus:quarkus-mutiny | (propia del BOM Quarkus) | No especificada en pom.xml | Programación reactiva / Mutiny |
| io.quarkus:quarkus-rest | (propia del BOM Quarkus) | No especificada en pom.xml | REST endpoints |
| io.quarkiverse.openapi.generator:quarkus-openapi-generator-server | 2.13.0 | No especificada en pom.xml | Generación de código a partir de OpenAPI |
| io.quarkus:quarkus-hibernate-validator | (propia del BOM Quarkus) | No especificada en pom.xml | Validación de beans (Bean Validation) |
| io.quarkiverse.openapi.generator:quarkus-openapi-generator-oidc | 2.10.0 | No especificada en pom.xml | Soporte OIDC para el generador OpenAPI |
| org.mapstruct:mapstruct | 1.6.3 | No especificada en pom.xml | Mapeo DTO ↔ entidad (MapStruct) |
| io.quarkus:quarkus-rest-client-oidc-filter | ${quarkus.platform.version} (3.30.5) | No especificada en pom.xml | Cliente REST con filtro OIDC |
| io.quarkus:quarkus-jdbc-mssql | (propia del BOM Quarkus) | No especificada en pom.xml | Driver JDBC para MS SQL Server |
| io.quarkus:quarkus-rest-jackson | (propia del BOM Quarkus) | No especificada en pom.xml | Serialización JSON con Jackson |
| io.quarkus:quarkus-hibernate-orm-panache | (propia del BOM Quarkus) | No especificada en pom.xml | Repositorios Panache (ORM simplificado) |
| io.quarkus:quarkus-liquibase | (propia del BOM Quarkus) | No especificada en pom.xml | Migraciones de BD con Liquibase |
| io.quarkus:quarkus-arc | (propia del BOM Quarkus) | No especificada en pom.xml | Inyección CDI / ArC |
| io.quarkus:quarkus-hibernate-orm | (propia del BOM Quarkus) | No especificada en pom.xml | Hibernate ORM |
| io.quarkus:quarkus-smallrye-openapi | (propia del BOM Quarkus) | No especificada en pom.xml | Documentación OpenAPI runtime |
| org.projectlombok:lombok | 1.18.42 | No especificada en pom.xml | Generación de getters/setters/constructores (scope: provided) |
| io.quarkus:quarkus-junit5 | (propia del BOM Quarkus) | No especificada en pom.xml | Framework de testing (scope: test) |
| io.rest-assured:rest-assured | (no está versionada explícitamente) | No especificada en pom.xml | Pruebas de integración HTTP (scope: test) |

> Nota: las versiones marcadas como "(propia del BOM Quarkus)" se gestionan por el BOM importado en `dependencyManagement` del `pom.xml`. La columna "Licencia" muestra "No especificada en pom.xml" cuando no aparece explícitamente en el `pom.xml`.

---

📁 Estructura del Proyecto (árbol resumido, extraído del workspace)

```
mvnw
mvnw.cmd
pom.xml
README.md
src/
  main/
    docker/
      Dockerfile.jvm
      Dockerfile.legacy-jar
      Dockerfile.native
      Dockerfile.native-micro
    java/
      org/
        acme/
          application/
            mappers/
            services/
            utils/
          domain/
            cases/
            model/
            repository/
            services/
          infraestructure/
            db/
            dtos/
            mappers/
            repository/
          interfaces/
            apis/
            cases/
            requests/
            controllers/
    resources/
      application.properties
      db/
        changelog-master.yaml
      openapi/
        openapi.yml
test/
  java/
    org/
      acme/
        GreetingResourceIT.java
        GreetingResourceTest.java
target/ (build output)
```

(El árbol está resumido; archivos y paquetes concretos aparecen en `src/main/java/org/acme/...`.)

---

📞 Contacto (datos extraídos del repositorio)

- Cliente: No se encontró información explícita en los headers o comentarios del código.
- Service Provider: `org.acme` (groupId en `pom.xml`).
- Desarrollador: identificado en archivos del repositorio como `adrian` (aparece como `author: adrian` en `src/main/resources/db/changelog-master.yaml` y como `quarkus.datasource.username=adrian` en `application.properties`).
- Repositorio/GitHub (usuario identificado en archivo de workspace): `adriannnn17` (aparece en `.idea/workspace.xml` dentro del repositorio local).

---

📚 **Para documentación extendida y guías detalladas, consulta la Wiki del proyecto en GitHub.**

