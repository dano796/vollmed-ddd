-- ================================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS PARA VOLL MED API
-- Base de datos: PostgreSQL
-- Versión: Compatible con PostgreSQL 12+
-- ================================================================

-- Crear la base de datos (ejecutar como superusuario)
-- CREATE DATABASE vollmed;

-- Conectarse a la base de datos vollmed antes de ejecutar el resto
-- \c vollmed;

-- ================================================================
-- TABLA: usuarios
-- Descripción: Almacena usuarios del sistema para autenticación
-- ================================================================
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    CONSTRAINT uk_usuarios_login UNIQUE (login)
);

-- ================================================================
-- TABLA: medicos
-- Descripción: Información de médicos registrados en el sistema
-- ================================================================
CREATE TABLE medicos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    documento VARCHAR(14) NOT NULL UNIQUE,
    especialidad VARCHAR(20) NOT NULL CHECK (especialidad IN ('ORTOPEDIA', 'CARDIOLOGIA', 'GINECOLOGIA', 'DERMATOLOGIA')),
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    -- Campos embebidos de Direccion (Value Object)
    calle VARCHAR(100),
    distrito VARCHAR(100),
    ciudad VARCHAR(100),
    numero VARCHAR(20),
    complemento VARCHAR(100),

    CONSTRAINT uk_medicos_email UNIQUE (email),
    CONSTRAINT uk_medicos_documento UNIQUE (documento)
);

-- ================================================================
-- TABLA: pacientes
-- Descripción: Información de pacientes registrados en el sistema
-- ================================================================
CREATE TABLE pacientes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    documento VARCHAR(14) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    -- Campos embebidos de Direccion (Value Object)
    calle VARCHAR(100),
    distrito VARCHAR(100),
    ciudad VARCHAR(100),
    numero VARCHAR(20),
    complemento VARCHAR(100),

    CONSTRAINT uk_pacientes_email UNIQUE (email),
    CONSTRAINT uk_pacientes_documento UNIQUE (documento)
);

-- ================================================================
-- TABLA: consultas
-- Descripción: Consultas médicas programadas y su estado
-- ================================================================
CREATE TABLE consultas (
    id BIGSERIAL PRIMARY KEY,
    medico_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    fecha TIMESTAMP NOT NULL,
    motivo_cancelamiento VARCHAR(20) CHECK (motivo_cancelamiento IN ('PACIENTE_DESISTIO', 'MEDICO_CANCELO', 'OTROS')),

    CONSTRAINT fk_consultas_medico FOREIGN KEY (medico_id) REFERENCES medicos(id),
    CONSTRAINT fk_consultas_paciente FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);

-- ================================================================
-- ÍNDICES PARA OPTIMIZAR CONSULTAS
-- ================================================================

-- Índices para tabla medicos
CREATE INDEX idx_medicos_especialidad ON medicos(especialidad);
CREATE INDEX idx_medicos_activo ON medicos(activo);
CREATE INDEX idx_medicos_especialidad_activo ON medicos(especialidad, activo);

-- Índices para tabla pacientes
CREATE INDEX idx_pacientes_activo ON pacientes(activo);

-- Índices para tabla consultas
CREATE INDEX idx_consultas_medico_id ON consultas(medico_id);
CREATE INDEX idx_consultas_paciente_id ON consultas(paciente_id);
CREATE INDEX idx_consultas_fecha ON consultas(fecha);
CREATE INDEX idx_consultas_medico_fecha ON consultas(medico_id, fecha);
CREATE INDEX idx_consultas_paciente_fecha ON consultas(paciente_id, fecha);
CREATE INDEX idx_consultas_activas ON consultas(fecha) WHERE motivo_cancelamiento IS NULL;

-- ================================================================
-- DATOS DE PRUEBA (OPCIONAL)
-- ================================================================

-- Usuario de prueba para login
INSERT INTO usuarios (login, clave) VALUES
('admin@vollmed.com', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.'); -- password: 123456

-- Médicos de prueba
INSERT INTO medicos (nombre, email, telefono, documento, especialidad, activo, calle, distrito, ciudad, numero, complemento) VALUES
('Dr. Juan Pérez', 'juan.perez@vollmed.com', '11999887766', '12345678901', 'CARDIOLOGIA', true, 'Av. Paulista', 'Bela Vista', 'São Paulo', '1000', 'Conj. 101'),
('Dra. Maria Silva', 'maria.silva@vollmed.com', '11988776655', '98765432109', 'DERMATOLOGIA', true, 'Rua Augusta', 'Consolação', 'São Paulo', '500', 'Sala 205'),
('Dr. Carlos Santos', 'carlos.santos@vollmed.com', '11977665544', '11122233344', 'ORTOPEDIA', true, 'Av. Brigadeiro', 'Jardins', 'São Paulo', '2000', 'Andar 3'),
('Dra. Ana Costa', 'ana.costa@vollmed.com', '11966554433', '55566677788', 'GINECOLOGIA', true, 'Rua Oscar Freire', 'Jardins', 'São Paulo', '1500', 'Consultório 8');

-- Pacientes de prueba
INSERT INTO pacientes (nombre, email, documento, telefono, activo, calle, distrito, ciudad, numero, complemento) VALUES
('João Silva', 'joao.silva@email.com', '11111111111', '11888777666', true, 'Rua das Palmeiras', 'Vila Madalena', 'São Paulo', '123', 'Apt 45'),
('Maria Oliveira', 'maria.oliveira@email.com', '22222222222', '11777666555', true, 'Av. Rebouças', 'Pinheiros', 'São Paulo', '456', 'Bloco B'),
('Pedro Santos', 'pedro.santos@email.com', '33333333333', '11666555444', true, 'Rua Consolação', 'Centro', 'São Paulo', '789', ''),
('Ana Rodrigues', 'ana.rodrigues@email.com', '44444444444', '11555444333', true, 'Av. Ipiranga', 'República', 'São Paulo', '321', 'Apt 12');

-- ================================================================
-- COMANDOS ÚTILES PARA VERIFICACIÓN
-- ================================================================

-- Verificar tablas creadas
-- \dt

-- Verificar estructura de una tabla específica
-- \d medicos

-- Contar registros en cada tabla
-- SELECT 'usuarios' as tabla, COUNT(*) as total FROM usuarios
-- UNION ALL
-- SELECT 'medicos', COUNT(*) FROM medicos
-- UNION ALL
-- SELECT 'pacientes', COUNT(*) FROM pacientes
-- UNION ALL
-- SELECT 'consultas', COUNT(*) FROM consultas;

-- ================================================================
-- NOTAS IMPORTANTES:
-- 1. El password del usuario de prueba está encriptado con BCrypt
-- 2. Las especialidades están restringidas por CHECK constraint
-- 3. Los motivos de cancelamiento también están restringidos
-- 4. Se crearon índices para optimizar las consultas más comunes
-- 5. Las direcciones están embebidas como campos separados en las tablas
-- ================================================================
