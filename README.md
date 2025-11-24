# RiverBooking - Proyecto Completo

Este repositorio contiene múltiples proyectos relacionados con sistemas de gestión y automatización.

## 📁 Estructura del Repositorio

### 🚢 [`Backend/`](Backend/) - Sistema de Reservas RiverBooking
Aplicación web completa para gestionar reservas de paseos en barco.

**Tecnologías**: Java 21, Spring Boot 3.4.5, Spring Security, MySQL  
**Documentación**: Ver [`Backend/README.md`](Backend/README.md)

### 🎨 [`Frontend/`](Frontend/) - Interfaz Web
Aplicación frontend para el sistema de reservas.

**Tecnologías**: Angular 19.2.4, TypeScript  
**Documentación**: Ver [`Frontend/README.md`](Frontend/README.md)

### 🐍 [`python-globeado/`](python-globeado/) - Sistema de Globeado PDF
Script Python educativo para añadir globos/etiquetas a PDFs según datos de Excel/CSV.

**Tecnologías**: Python 3.8+, pandas, PyPDF2, reportlab  
**Documentación**: Ver [`python-globeado/README.md`](python-globeado/README.md)

### 📚 [`docs/`](docs/) - Documentación Completa
Guías detalladas para comprender todos los proyectos.

**Contenido**:
- [`docs/README.md`](docs/README.md) - **EMPIEZA AQUÍ** - Guía principal
- [`docs/PROYECTO_ACTUAL.md`](docs/PROYECTO_ACTUAL.md) - Explicación del proyecto RiverBooking
- [`docs/PYTHON_BASICO.md`](docs/PYTHON_BASICO.md) - Python para desarrolladores Java/TypeScript
- [`docs/GLOBEADO_PUNTO_A_PUNTO.md`](docs/GLOBEADO_PUNTO_A_PUNTO.md) - Sistema de globeado detallado

---

## 🚀 Inicio Rápido

### Para el Proyecto Web (RiverBooking)

#### Backend
```bash
cd Backend
./mvnw spring-boot:run
```

#### Frontend
```bash
cd Frontend
npm install
ng serve
```

### Para el Proyecto Python (Globeado)

```bash
cd python-globeado

# Crear entorno virtual
python -m venv venv
source venv/bin/activate  # Linux/Mac
# venv\Scripts\activate   # Windows

# Instalar dependencias
pip install -r requirements.txt

# Ejecutar ejemplos
python ejemplos/01_conceptos_basicos.py
```

---

## 📖 Documentación por Perfil

### 👨‍💻 Soy desarrollador y quiero entender el proyecto web
1. Lee [`docs/PROYECTO_ACTUAL.md`](docs/PROYECTO_ACTUAL.md)
2. Revisa el código en `Backend/` y `Frontend/`
3. Consulta el backlog en `backlog.md.code-workspace`

### 🐍 Quiero aprender Python (vengo de Java/TypeScript)
1. **EMPIEZA AQUÍ**: [`docs/README.md`](docs/README.md)
2. Lee [`docs/PYTHON_BASICO.md`](docs/PYTHON_BASICO.md)
3. Practica con los ejemplos en `python-globeado/ejemplos/`

### 🎯 Quiero implementar el sistema de globeado PDF
1. Lee [`docs/GLOBEADO_PUNTO_A_PUNTO.md`](docs/GLOBEADO_PUNTO_A_PUNTO.md)
2. Sigue la guía en [`python-globeado/README.md`](python-globeado/README.md)
3. Ejecuta los ejemplos en orden (01 → 02 → 03 → 04)

---

## 🎓 Objetivos de Aprendizaje

Este repositorio es también educativo. Los proyectos Python están diseñados para enseñar:

- ✅ Fundamentos de Python para desarrolladores de otros lenguajes
- ✅ Procesamiento de datos con pandas (Excel, CSV)
- ✅ Manipulación de PDFs (lectura, creación, anotaciones)
- ✅ Buenas prácticas: entornos virtuales, estructura de proyectos, documentación
- ✅ Desarrollo paso a paso: de conceptos básicos a integración completa

---

## 🛠️ Requisitos

### Para el Proyecto Web
- Java 21
- Node.js 18+
- MySQL
- Maven

### Para el Proyecto Python
- Python 3.8+ (recomendado 3.11+)
- pip
- Entorno virtual (venv)

---

## 📞 Soporte

- **Backend/Frontend**: Revisa el código y la documentación interna
- **Python/Globeado**: Lee la documentación en [`docs/`](docs/) y ejecuta los ejemplos

---

## 📄 Estructura Completa

```
RiverBooking/
├── Backend/              # API REST con Spring Boot
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── Frontend/             # App Angular
│   ├── src/
│   ├── angular.json
│   └── README.md
├── python-globeado/      # Scripts Python educativos
│   ├── ejemplos/        # 4 ejemplos progresivos
│   ├── src/             # Código del proyecto final
│   ├── data/            # Datos de ejemplo
│   ├── output/          # PDFs generados
│   └── README.md
├── docs/                 # Documentación completa
│   ├── README.md        # 👈 EMPIEZA AQUÍ
│   ├── PROYECTO_ACTUAL.md
│   ├── PYTHON_BASICO.md
│   └── GLOBEADO_PUNTO_A_PUNTO.md
├── backlog.md.code-workspace  # Backlog del proyecto web
└── README.md            # Este archivo
```

---

## 🌟 Características Destacadas

### Proyecto Web (Java + Angular)
- ✅ Autenticación JWT
- ✅ API REST completa
- ✅ Gestión de reservas con validación de plazas
- ✅ Sistema de notificaciones por email
- ✅ Panel de administración

### Proyecto Python (Globeado)
- ✅ Lectura de Excel/CSV con pandas
- ✅ Validación robusta de datos
- ✅ Generación de PDFs con reportlab
- ✅ Fusión de capas PDF con PyPDF2
- ✅ Ejemplos educativos paso a paso
- ✅ Documentación completa en español

---

**¡Explora, aprende y construye!** 🚀
