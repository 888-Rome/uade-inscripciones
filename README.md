# Sistema de Gestión Inteligente de Inscripciones · UADE

📢 Procesos de Desarrollo de Software

Una versión repensada del sistema de inscripciones de la Universidad Argentina de la Empresa, centrada en resolver los problemas reales que enfrentan los estudiantes cada cuatrimestre.

—

## 👤 Grupo N°13
- ⤷ Ximena Romero · `888-Rome`
- ⤷ 
- ⤷ 
- ⤷ 
- ⤷ 
- ⤷ 
- ⤷ 

—
## ❶ Propuesta
Ver el documento de Google para contexto, problema detectado, alcance, solución propuesta y justificaciones.

[Google Document](https://docs.google.com/document/d/1Bgh0efhnsvZm-DZ7xvgswQhYsSMqMpHTTmuWCZWbOQ0/edit?usp=drive_link)

—

## ❷ Stack
⤷ Java 17 · Spring Boot · Spring Data JPA
⤷ Spring Security + JWT
⤷ Lombok · Maven
⤷ H2 (en memoria, seedeada al iniciar)
⤷ Frontend HTML · CSS · React

—

## ❸ Patrones
Facade · Strategy · MVC · Observer · Repository
> Detalle y justificación en `docs/propuesta.md`

—
## ❹ Estructura del proyecto

```
src/
├─ main/
│  ├─ java/uade/inscripciones/
│  │  ├─ config/           ← seguridad, JWT, seeds
│  │  ├─ controller/       ← endpoints REST
│  │  ├─ model/            ← entidades JPA
│  │  ├─ repository/       ← interfaces Spring Data
│  │  ├─ service/          ← lógica de negocio
│  │  ├─ facade/           ← patrón Facade
│  │  ├─ strategy/         ← criterios de prioridad
│  │  ├─ observer/         ← sistema de notificaciones
│  │  └─ dto/              ← objetos de transferencia
│  └─ resources/
│     ├─ application.yml
│     └─ data.sql
└─ test/
```

—

## ❺ ¿Cómo correrlo?

👉 **Prerrequisitos**
⤷ Java 17 o superior
⤷ Maven 3.8 o superior

📌 **Pasos**

```bash
git clone https://github.com/[usuario]/uade-inscripciones.git
cd uade-inscripciones
mvn spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`
Consola H2 en `http://localhost:8080/h2-console`

—

*TP Final · Procesos de Desarrollo de Software · UADE · 2026*
