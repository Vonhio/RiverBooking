# RiverBooking - Proyecto Actual

## ¿Qué es RiverBooking?

RiverBooking es una aplicación web para gestionar reservas de paseos en barco por el río Guadalquivir en Sevilla.

## Arquitectura del Proyecto

### Backend - Java + Spring Boot
**Ubicación**: `/Backend`
**Tecnologías**:
- Java 21
- Spring Boot 3.4.5
- Spring Security (autenticación JWT)
- Spring Data JPA (persistencia)
- MySQL (base de datos)
- Maven (gestión de dependencias)

**Estructura**:
```
Backend/
├── src/main/java/com/riverBooking/
│   ├── RiverBookingApplication.java  (punto de entrada)
│   ├── entity/                       (entidades JPA - modelos de BD)
│   │   ├── BarcoEntity.java
│   │   ├── ReservaEntity.java
│   │   └── UsuarioEntity.java
│   ├── entityDTO/                    (DTOs - datos para API)
│   │   ├── BarcoEntityDTO.java
│   │   ├── ReservaEntityDTO.java
│   │   └── InformacionReservasDTO.java
│   ├── service/                      (lógica de negocio)
│   │   ├── BarcoService.java
│   │   ├── ReservaService.java
│   │   └── MailService.java
│   ├── exception/                    (manejo de errores)
│   │   ├── GlobalExceptionHandler.java
│   │   ├── ReservaNoEncontradaException.java
│   │   └── PlazasInsuficientesException.java
│   └── security/                     (autenticación y autorización)
│       ├── config/SecurityConfig.java
│       ├── jwt/JwtUtil.java
│       └── filter/JwtAuthFilter.java
└── pom.xml                           (configuración Maven)
```

### Frontend - Angular
**Ubicación**: `/Frontend`
**Tecnologías**:
- Angular 19.2.4
- TypeScript
- HTML/CSS

**Comandos principales**:
```bash
ng serve         # Ejecutar en desarrollo (http://localhost:4200)
ng build         # Compilar para producción
ng test          # Ejecutar tests
```

## Funcionalidades Principales

Según el backlog (`backlog.md.code-workspace`), el sistema tiene:

### 1. **FU-1: Reservar barco** (Must)
- Cliente anónimo puede reservar: barco → fecha → hora → nº personas
- Validación de plazas disponibles
- API: `POST /reservas` → HTTP 201

### 2. **FU-2: Gestionar reservas** (Must)
- Admin logueado puede listar/filtrar/editar/eliminar reservas
- Filtros: código, barco, fecha, hora
- Envío automático de correos

### 3. **FU-3: Iniciar sesión** (Must)
- Admin se autentica con JWT (válido 24h)
- Token JWT en cada petición privada

### 4. **FU-4: Confirmar reserva** (Must)
- Correo de confirmación con código de reserva
- Plantillas diferentes para cliente y admin

### 5. **FU-5: Cancelar reserva** (Must)
- Admin cancela → libera plazas → notifica al cliente
- API: `DELETE /reservas/{id}` → HTTP 204

## Requisitos No Funcionales

- **NF-1**: Rendimiento < 500ms (p95) con 30 usuarios
- **NF-2**: Seguridad JWT + BCrypt + OWASP ZAP
- **NF-3**: Accesibilidad WCAG 2.1 AA

## ¿Por qué Java y no Python?

Este proyecto usa **Java con Spring Boot** porque:
1. Es robusto para aplicaciones empresariales con alta concurrencia
2. Spring Boot simplifica la creación de APIs REST
3. Spring Security facilita autenticación/autorización
4. JPA abstrae el acceso a base de datos
5. Ecosistema maduro con muchas herramientas

**Python**, en cambio, se usaría para:
- Scripts de automatización
- Procesamiento de datos (pandas)
- Machine Learning
- Manipulación de archivos (PDFs, Excel, CSV)
- Prototipado rápido

## Relación con "Globeado Punto a Punto"

El proyecto **RiverBooking** (Java/Angular) y el **script de globeado Python** son **proyectos diferentes**:

- **RiverBooking**: App web completa para reservas
- **Globeado Python**: Script para añadir etiquetas/globos en PDFs según datos de Excel/CSV

**No se reemplazan**, son herramientas complementarias para diferentes propósitos.

---

## Próximos Pasos

1. ✅ Has entendido qué es RiverBooking (Java/Spring Boot)
2. 📖 Lee `PYTHON_BASICO.md` para aprender conceptos Python viniendo de Java/TS
3. 🎯 Lee `GLOBEADO_PUNTO_A_PUNTO.md` para entender el proyecto de globeado PDF
