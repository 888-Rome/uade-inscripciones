# Sistema de Gestión Inteligente de Inscripciones · UADE

📢 Procesos de Desarrollo de Software

Una versión repensada del sistema de inscripciones de la Universidad Argentina de la Empresa, centrada en resolver los problemas reales que enfrentan los estudiantes cada cuatrimestre.

---

## 👤 Grupo N°13
1️⃣ Ximena Romero · `888-Rome`

---

## ❶ Propuesta
Ver el documento de Google para contexto, problema detectado, alcance, solución propuesta y justificaciones.

[Google Document](https://docs.google.com/document/d/1i27qvzpOujo_rDp85v9kBzWrCtNVhmV9NfYaES3tROw/edit?tab=t.cj9dv1xbblhn).

---

## ❷ Stack
- Java 17 · Spring Boot · Spring Data JPA
- Spring Security + JWT
- Bean Validation
- Lombok · Maven
- H2 (en memoria, seedeada al iniciar vía `DataSeeder`)
- Frontend HTML · CSS · React · Redux

---

## ❸ Patrones

Facade · Strategy · MVC · Observer · Repository

Detalle y justificación en [Google Document](https://docs.google.com/document/d/1i27qvzpOujo_rDp85v9kBzWrCtNVhmV9NfYaES3tROw/edit?tab=t.cj9dv1xbblhn).

---

## ❹ Estructura del proyecto
Frontend NO detallado.

```
backend/
└─ src/
   ├─ main/
   │  ├─ java/uade/inscripciones/
   │  │  ├─ InscripcionesApplication.java
   │  │  ├─ api/                ← capa de presentación (MVC)
   │  │  │  ├─ controller/      ← endpoints REST
   │  │  │  ├─ dto/             ← objetos de transferencia
   │  │  │  │  ├─ request/
   │  │  │  │  └─ response/
   │  │  │  └─ mapper/          ← entidad ↔ DTO
   │  │  ├─ base/
   │  │  │  ├─ config/          ← DataSeeder, OpenApi
   │  │  │  ├─ enums/           ← Sede, Turno, Modalidad, Rol, etc.
   │  │  │  ├─ exception/       ← BusinessException + handler global
   │  │  │  └─ model/           ← entidades JPA
   │  │  ├─ logic/              ← lógica de negocio
   │  │  │  ├─ service/         ← servicios
   │  │  │  ├─ facade/          ← patrón Facade
   │  │  │  ├─ strategy/        ← criterios intercambiables
   │  │  │  └─ observer/        ← sistema de notificaciones
   │  │  ├─ repository/         ← interfaces Spring Data
   │  │  └─ security/           ← JWT, filtros, SecurityConfig
   │  └─ resources/
   │     └─ application.properties
   └─ test/
```

---

## ❺ ¿Cómo correrlo?

👉 **Prerrequisitos**
- Java 17 o superior
- Maven 3.8 o superior (o usar el wrapper incluído `./mvnw`)

📌 **Pasos**

```bash
git clone https://github.com/888-Rome/uade-inscripciones.git
cd uade-inscripciones/backend
.mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`

- Consola H2 en `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:inscripciones`
  - Usuario: `sa` · sin contraseña
- Documentación de la APU (OpenAPI/Swagger): `http://localhost:8080/swagger-ui.html`

---

*TP Final · Procesos de Desarrollo de Software · UADE · 2026*
