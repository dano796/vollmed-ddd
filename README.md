# Voll Med API

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue)

## 📖 Descripción del Negocio

**Voll Med** es una API REST para la gestión de una clínica médica que permite:

- **Gestión de Médicos**: Registro, actualización y consulta de médicos por especialidad
- **Gestión de Pacientes**: Registro, actualización y consulta de pacientes
- **Sistema de Consultas**: Reserva y cancelación de citas médicas con validaciones de negocio
- **Autenticación**: Sistema de login seguro con JWT tokens
- **Notificaciones**: Sistema de eventos para notificaciones automáticas

### Reglas de Negocio Principales

1. **Reserva de Consultas**:
   - Solo médicos activos pueden atender consultas
   - Solo pacientes activos pueden reservar consultas
   - No se permiten consultas en horarios no laborales
   - Médicos no pueden tener consultas simultáneas
   - Pacientes no pueden tener múltiples consultas el mismo día
   - Reservas con mínimo 30 minutos de anticipación

2. **Cancelación de Consultas**:
   - Cancelación con al menos 24 horas de anticipación
   - Motivos válidos: paciente desistió, médico canceló, otros

## 🏗️ Arquitectura

Este proyecto implementa **Domain-Driven Design (DDD)** con **Arquitectura Hexagonal**, organizando el código en capas bien definidas:

```
src/main/java/med/voll/api/
├── api/                    # Capa de Presentación
├── application/            # Capa de Aplicación  
├── domain/                 # Capa de Dominio (Core)
└── infrastructure/         # Capa de Infraestructura
```

### 🎯 Principios Arquitectónicos

- **Separation of Concerns**: Cada capa tiene responsabilidades específicas
- **Dependency Inversion**: Las dependencias apuntan hacia el dominio
- **Domain Events**: Comunicación desacoplada mediante eventos
- **Aggregate Pattern**: Consistencia transaccional y encapsulación
- **Value Objects**: Objetos inmutables para conceptos de dominio

## 📁 Estructura del Proyecto

### 🌐 API Layer (`api/`)

**Responsabilidad**: Exponer endpoints REST y manejar requests/responses HTTP.

#### `controller/`
- **`AutenticacionController.java`**: Login de usuarios, generación de JWT tokens
- **`ConsultaController.java`**: Endpoints para reservar/cancelar consultas
- **`MedicoController.java`**: CRUD de médicos, listado por especialidad
- **`PacienteController.java`**: CRUD de pacientes

### 🔄 Application Layer (`application/`)

**Responsabilidad**: Coordinar casos de uso y orquestar el dominio.

#### `command/`
Comandos para operaciones de escritura (patrón CQRS preparado)

#### `dto/`
**Data Transfer Objects** para transferencia de datos entre capas:

##### `request/`
- **`DatosAutenticacionUsuario.java`**: Credenciales de login
- **`DatosReservaConsulta.java`**: Datos para reservar consulta
- **`DatosCancelamientoConsulta.java`**: Datos para cancelar consulta
- **`DatosRegistroMedico.java`**: Registro de nuevo médico
- **`DatosRegistroPaciente.java`**: Registro de nuevo paciente
- **`DatosActualizarMedico.java`**: Actualización de médico
- **`DatosActualizacionPaciente.java`**: Actualización de paciente
- **`DatosDireccion.java`**: Información de dirección

##### `response/`
- **`DatosJWTToken.java`**: Token de autenticación
- **`DatosDetalleConsulta.java`**: Detalles de consulta
- **`DatosListadoMedico.java`**: Lista de médicos
- **`DatosListaPaciente.java`**: Lista de pacientes
- **`DatosRespuestaMedico.java`**: Respuesta de médico

#### `query/`
Consultas para operaciones de lectura (patrón CQRS preparado)

#### `service/`
**Application Services** que coordinan casos de uso:
- **`GestionConsultaService.java`**: Orquesta operaciones de consultas
- **`GestionMedicoService.java`**: Orquesta operaciones de médicos
- **`GestionPacienteService.java`**: Orquesta operaciones de pacientes

### 🎯 Domain Layer (`domain/`)

**Responsabilidad**: Contiene la lógica de negocio central y las reglas del dominio.

#### `aggregates/`
**Aggregate Roots** que garantizan consistencia:
- **`Consulta.java`**: Agregado principal para consultas médicas
  - Maneja reserva y cancelación
  - Genera eventos de dominio automáticamente
  - Mantiene invariantes de negocio

#### `entities/`
**Entidades de dominio**:
- **`Medico.java`**: Entidad médico con especialidades y estado activo/inactivo
- **`Paciente.java`**: Entidad paciente con datos personales
- **`Usuario.java`**: Entidad para autenticación

#### `event/`
**Domain Events** para comunicación desacoplada:
- **`ConsultaReservadaEvent.java`**: Se dispara al reservar una consulta
- **`ConsultaCanceladaEvent.java`**: Se dispara al cancelar una consulta

#### `interfaces/`
**Repository Interfaces** (puertos hacia infraestructura):
- **`IConsultaRepository.java`**: Persistencia de consultas
- **`IMedicoRepository.java`**: Persistencia de médicos
- **`IPacienteRepository.java`**: Persistencia de pacientes
- **`IUsuarioRepository.java`**: Persistencia de usuarios

#### `service/`
**Domain Services** con lógica de negocio compleja:

##### Servicios Principales:
- **`ReservaConsultaService.java`**: Lógica de reserva con validaciones
- **`CancelacionConsultaService.java`**: Lógica de cancelación

##### Validadores (Chain of Responsibility):
- **`ValidadorReservaConsulta.java`**: Interfaz base para validaciones
- **`ValidadorMedicoActivo.java`**: Valida que el médico esté activo
- **`ValidadorPacienteActivo.java`**: Valida que el paciente esté activo
- **`ValidadorHorarioFuncionamiento.java`**: Valida horario laboral
- **`ValidadorHorarioAntecedencia.java`**: Valida anticipación mínima
- **`ValidadorMedicoConOtraConsulta.java`**: Evita consultas simultáneas
- **`ValidadorPacienteSinConsulta.java`**: Evita múltiples consultas por día
- **`ValidadorConsultaConAnticipacion.java`**: Valida anticipación para cancelar
- **`ValidadorCancelacionConsulta.java`**: Interfaz base para cancelaciones

#### `shared/`
**Elementos compartidos del dominio**:
- **`AggregateRoot.java`**: Clase base para agregados con eventos
- **`DomainEvent.java`**: Interfaz base para eventos de dominio
- **`DomainException.java`**: Excepción específica del dominio
- **`ResourceNotFoundException.java`**: Excepción para recursos no encontrados

#### `value_objects/`
**Value Objects** inmutables:
- **`Direccion.java`**: Dirección con validaciones
- **`Documento.java`**: Documento de identidad
- **`Email.java`**: Email con validación de formato
- **`Especialidad.java`**: Enum de especialidades médicas
- **`FechaConsulta.java`**: Fecha de consulta con validaciones
- **`MotivoCancelamiento.java`**: Enum de motivos de cancelación
- **`Telefono.java`**: Teléfono con validaciones

### 🔧 Infrastructure Layer (`infrastructure/`)

**Responsabilidad**: Implementar detalles técnicos y conectar con sistemas externos.

#### `configuration/`
**Configuraciones de Spring**:
- **`CorsConfig.java`**: Configuración de CORS para el frontend
- **`DomainServiceConfiguration.java`**: Configuración de beans de dominio
- **`GlobalExceptionHandler.java`**: Manejo global de excepciones
- **`JacksonConfig.java`**: Configuración de serialización JSON
- **`SecurityConfigurations.java`**: Configuración de Spring Security
- **`SecurityFilter.java`**: Filtro JWT para autenticación

#### `messaging/`
**Manejo de eventos**:
- **`ConsultaEventHandler.java`**: Procesa eventos de consultas (notificaciones, logs)

#### `service/`
**Servicios de infraestructura**:
- **`AutenticacionService.java`**: Implementación de autenticación
- **`DomainEventPublisher.java`**: Publicador de eventos de dominio
- **`TokenService.java`**: Generación y validación de JWT tokens

## 🚀 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **Spring Boot 3.4.1**: Framework principal
- **Spring Security**: Autenticación y autorización
- **Spring Data JPA**: Persistencia de datos
- **PostgreSQL**: Base de datos
- **JWT**: Tokens de autenticación
- **Lombok**: Reducción de boilerplate
- **Jackson**: Serialización JSON
- **Maven**: Gestión de dependencias

## 📊 Flujos Principales

### 🔐 Autenticación
1. Usuario envía credenciales → `AutenticacionController`
2. `AutenticacionService` valida credenciales
3. `TokenService` genera JWT token
4. Token retornado para requests subsecuentes

### 📅 Reserva de Consulta
1. Request → `ConsultaController.reservar()`
2. `GestionConsultaService` coordina el proceso
3. `ReservaConsultaService` ejecuta validaciones:
   - Médico activo (`ValidadorMedicoActivo`)
   - Paciente activo (`ValidadorPacienteActivo`)
   - Horario válido (`ValidadorHorarioFuncionamiento`)
   - Sin conflictos (`ValidadorMedicoConOtraConsulta`)
   - Otras validaciones...
4. Se crea `Consulta` (aggregate root)
5. `Consulta` genera `ConsultaReservadaEvent`
6. `ConsultaEventHandler` procesa evento (notificaciones)

### ❌ Cancelación de Consulta
1. Request → `ConsultaController.cancelar()`
2. `GestionConsultaService` coordina
3. `CancelacionConsultaService` valida cancelación
4. `Consulta.cancelar()` actualiza estado
5. `ConsultaCanceladaEvent` es generado
6. `ConsultaEventHandler` procesa evento

## 🏃‍♂️ Cómo Ejecutar

```bash
# Clonar repositorio
git clone <repository-url>

# Navegar al directorio
cd vollmed-api-master

# Configurar base de datos PostgreSQL
# Ejecutar script: vollmed-postgresql-schema.sql

# Configurar application.properties con datos de BD

# Ejecutar aplicación
./mvnw spring-boot:run

# O en Windows
mvnw.cmd spring-boot:run
```

## 📁 Archivos de Configuración

- **`pom.xml`**: Dependencias Maven
- **`application.properties`**: Configuración de desarrollo
- **`application-prod.properties`**: Configuración de producción
- **`vollmed-postgresql-schema.sql`**: Script de base de datos
- **`VollMed-API-Postman-Collection-Updated.json`**: Colección Postman para testing

## 🎯 Ventajas de esta Arquitectura

1. **Mantenibilidad**: Código organizado y fácil de entender
2. **Testabilidad**: Dominio independiente, fácil de testear
3. **Escalabilidad**: Preparado para crecimiento
4. **Flexibilidad**: Fácil cambiar implementaciones técnicas
5. **Reutilización**: Lógica de dominio reutilizable
6. **Consistencia**: Eventos garantizan sincronización
7. **Performance**: Lazy loading y optimizaciones JPA

---

## 📞 Contacto

**Desarrollado para Voll Med - Sistema de Gestión de Clínica Médica**
