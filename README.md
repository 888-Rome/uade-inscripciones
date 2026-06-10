# Sistema de Gestión de Inscripciones Inteligente

---
📑 𝗧𝗣 𝗙𝗶𝗻𝗮𝗹 · Procesos de Desarrollo de Software

Una versión repensada del sistema de inscripciones de la Universidad Argentina de la Empresa, centrada en resolver los problemas reales que enfrentan los estudiantes cada cuatrimestre.

---

## ❶ Propuesta
Ver el documento de Google para contexto, problema detectado, alcance, solución propuesta y justificaciones. \
[Google Document](https://docs.google.com/document/d/1Bgh0efhnsvZm-DZ7xvgswQhYsSMqMpHTTmuWCZWbOQ0/edit?usp=drive_link).
> ⚠️ **Estado del proyecto** \
> Este repositorio refleja el estado del proyecto al momento de la entrega. Algunas funcionalidades previstas para la versión final se encuentran parcialmente implementadas o pendientes de integración. El objetivo principal de esta entrega es presentar la arquitectura propuesta, las decisiones de diseño y la implementación de los componentes principales del sistema.

---

## ❷ Stack
- Java 17 · Spring Boot · Spring Data JPA
- Spring Security + JWT
- Lombok · Maven
- H2 (en memoria, seedeada al iniciar)
- Frontend React · Vite · HTML · CSS

---

## ❸ Patrones
Strategy · Facade · Observer · Template Method · Adapter · Repository \
Detalle y justificación en el [Google Document](https://docs.google.com/document/d/1Bgh0efhnsvZm-DZ7xvgswQhYsSMqMpHTTmuWCZWbOQ0/edit?usp=drive_link).

---

## ❹ Estructura del proyecto
```
src/
├─ main/
│  ├─ java/uade/inscripciones/
│  │  ├─ api/
│  │  │  ├─ controller/      ← endpoints REST (auth, inscripciones)
│  │  │  ├─ dto/             ← requests y responses
│  │  │  └─ mapper/          ← conversión entre entidades y DTOs
│  │  │
│  │  ├─ base/
│  │  │  ├─ config/          ← seeds y configuración OpenAPI
│  │  │  ├─ enums/           ← enumeraciones del dominio
│  │  │  ├─ exception/       ← manejo global de errores
│  │  │  ├─ external/        ← integración con servicios externos
│  │  │  └─ model/           ← entidades JPA
│  │  │
│  │  ├─ logic/
│  │  │  ├─ service/         ← lógica de negocio
│  │  │  ├─ strategy/        ← patrón Strategy
│  │  │  ├─ facade/          ← patrón Facade
│  │  │  ├─ observer/        ← patrón Observer
│  │  │  └─ templatemethod/  ← patrón Template Method
│  │  │
│  │  ├─ repository/         ← acceso a datos (Spring Data JPA)
│  │  ├─ security/           ← autenticación JWT y Spring Security
│  │  └─ InscripcionesApplication.java
│  │
│  └─ resources/
│     └─ application.properties
│
└─ test/
   └─ java/
```

---

## ❺ ¿Cómo correrlo?
👉 **Prerrequisitos**
- Java 17
- Maven 3.8 o superior
- Node.js + npm


📌 Backend: http://localhost:8080\
`mvn spring-boot:run`

📌 Frontend: http://localhost:5173 \
`npm install`\
`npm run dev`

📌 Base de datos H2: http://localhost:8080/h2-console

--- 

👩🏻‍💼 𝗥𝗼𝗺𝗲𝗿𝗼, 𝗫𝗶𝗺𝗲𝗻𝗮 𝗦𝘁𝗲𝗹𝗹𝗮