# Guía de Comprensión: Proyecto Globeado Punto a Punto

## 👋 Bienvenido

Esta documentación te guiará paso a paso para comprender el proyecto de "globeado punto a punto" en Python, especialmente diseñada para desarrolladores que vienen de **Java** o **TypeScript** y están aprendiendo Python.

---

## 📚 ¿Por Dónde Empezar?

### 1️⃣ Entender el Contexto Actual
**Lee primero**: [`docs/PROYECTO_ACTUAL.md`](docs/PROYECTO_ACTUAL.md)

Este documento explica:
- Qué es RiverBooking (el proyecto Java/Angular existente)
- Por qué este repositorio tiene Java y no Python (por ahora)
- Diferencia entre el proyecto web y el script de globeado

### 2️⃣ Aprender Python Básico
**Lee después**: [`docs/PYTHON_BASICO.md`](docs/PYTHON_BASICO.md)

Comparativa completa entre Java/TypeScript y Python:
- Variables y tipos de datos
- Estructuras de datos (listas, diccionarios)
- Control de flujo (if, for, while)
- Funciones y clases
- Convenciones de nomenclatura
- Manejo de archivos
- Librerías esenciales

### 3️⃣ Comprender el Proyecto de Globeado
**Lee finalmente**: [`docs/GLOBEADO_PUNTO_A_PUNTO.md`](docs/GLOBEADO_PUNTO_A_PUNTO.md)

Explicación detallada del sistema:
- Qué es el "globeado punto a punto"
- Arquitectura del sistema
- Librerías necesarias (pandas, PyPDF2, reportlab)
- Flujo completo del script
- Sistema de coordenadas en PDFs
- Validaciones y control de errores

---

## 💻 Proyecto Práctico

En la carpeta [`python-globeado/`](python-globeado/) encontrarás un proyecto completo de aprendizaje:

### Estructura del Proyecto

```
python-globeado/
├── README.md              # Documentación del proyecto
├── requirements.txt       # Dependencias Python
├── .gitignore            # Archivos a ignorar en git
│
├── ejemplos/              # Scripts educativos paso a paso
│   ├── 01_conceptos_basicos.py      # Fundamentos de Python
│   ├── 02_leer_csv_excel.py         # pandas para datos
│   ├── 03_manipular_pdf.py          # reportlab y PyPDF2
│   └── 04_globeado_simple.py        # Integración completa
│
├── src/                   # Código del proyecto final (a implementar)
│   ├── __init__.py
│   ├── main.py           # Script principal
│   ├── lector_datos.py   # Lectura de Excel/CSV
│   ├── procesador_pdf.py # Manipulación de PDFs
│   └── config.py         # Configuración
│
├── data/                  # Datos de ejemplo (generados)
├── output/                # PDFs generados (git-ignored)
└── tests/                 # Tests unitarios (a implementar)
```

### Pasos para Empezar

#### 1. Instalar Python

Si no tienes Python instalado:
- **Windows/Mac**: Descarga desde [python.org](https://www.python.org/downloads/)
- **Linux**: Ya viene instalado, verifica con `python3 --version`

Requiere **Python 3.8+** (recomendado 3.11+)

#### 2. Crear Entorno Virtual

```bash
# Navega a la carpeta del proyecto
cd python-globeado/

# Crea el entorno virtual
python -m venv venv

# Activa el entorno
# En Linux/Mac:
source venv/bin/activate

# En Windows:
venv\Scripts\activate
```

**¿Por qué entorno virtual?**
- Aísla dependencias del proyecto
- Evita conflictos entre proyectos
- Buena práctica profesional

#### 3. Instalar Dependencias

```bash
# Con el entorno activado:
pip install -r requirements.txt
```

Esto instalará:
- `pandas`: Procesamiento de datos (Excel, CSV)
- `openpyxl`: Soporte para archivos Excel
- `PyPDF2`: Lectura de PDFs
- `reportlab`: Creación y dibujo en PDFs

#### 4. Ejecutar Ejemplos en Orden

```bash
# Ejemplo 1: Conceptos básicos de Python
python ejemplos/01_conceptos_basicos.py

# Ejemplo 2: Leer CSV y Excel (genera archivos en data/)
python ejemplos/02_leer_csv_excel.py

# Ejemplo 3: Manipular PDFs (genera archivos en output/ y data/)
python ejemplos/03_manipular_pdf.py

# Ejemplo 4: Globeado completo (integración final)
python ejemplos/04_globeado_simple.py
```

**Cada ejemplo**:
- Explica conceptos paso a paso
- Incluye comentarios detallados
- Genera archivos de salida para verificar
- Te prepara para el siguiente nivel

---

## 🎯 Plan de Aprendizaje (2 Semanas)

### Semana 1: Fundamentos

| Días | Objetivo | Recursos |
|------|----------|----------|
| **Día 1-2** | Instalar Python, entorno virtual, ejecutar ejemplos 1-2 | `01_conceptos_basicos.py`, `02_leer_csv_excel.py` |
| **Día 3-4** | Practicar con pandas: leer, filtrar, validar datos | `PYTHON_BASICO.md`, modificar ejemplo 2 |
| **Día 5-6** | Manipulación de PDFs: dibujar formas, texto, globos | `03_manipular_pdf.py`, `GLOBEADO_PUNTO_A_PUNTO.md` |
| **Día 7** | Integración: script completo de globeado | `04_globeado_simple.py` |

### Semana 2: Proyecto Final

| Días | Objetivo | Tareas |
|------|----------|--------|
| **Día 8-9** | Implementar script profesional en `src/` | `main.py`, `lector_datos.py`, `procesador_pdf.py` |
| **Día 10** | Añadir configuración y logging | `config.py`, manejo de errores |
| **Día 11** | Crear tests básicos | `tests/test_lector_datos.py` |
| **Día 12** | Refinamiento: validaciones, documentación | Docstrings, README |
| **Día 13** | Optimización y casos edge | Manejo de errores avanzado |
| **Día 14** | Demo final y presentación | Script funcional completo |

---

## 📖 Recursos Adicionales

### Documentación Oficial
- [Python en Español](https://docs.python.org/es/3/)
- [pandas](https://pandas.pydata.org/docs/)
- [reportlab](https://docs.reportlab.com/)
- [PyPDF2](https://pypdf2.readthedocs.io/)

### Conceptos Clave para Recordar

| Concepto | Java/TS | Python |
|----------|---------|--------|
| Nombrado variables | `camelCase` | `snake_case` |
| Nombrado clases | `PascalCase` | `PascalCase` |
| Bloques de código | `{ }` | Indentación (4 espacios) |
| Null | `null` | `None` |
| Booleanos | `true`/`false` | `True`/`False` |
| Arrays | `[]` / `new Type[]` | `[]` (listas) |
| Mapas | `HashMap<K,V>` / `Record<K,V>` | `{}` (diccionarios) |

---

## 🚀 Próximos Pasos

1. ✅ **Has leído esta guía**
2. 📖 **Lee los documentos en orden**:
   - `docs/PROYECTO_ACTUAL.md`
   - `docs/PYTHON_BASICO.md`
   - `docs/GLOBEADO_PUNTO_A_PUNTO.md`
3. 💻 **Practica con los ejemplos**:
   - Ejecuta cada script en orden
   - Modifica y experimenta
   - Rompe el código para entender errores
4. 🔧 **Implementa tu versión**:
   - Crea tu propio script en `src/`
   - Añade funcionalidades personalizadas
   - Documenta tu código

---

## 🆘 Solución de Problemas

### Error: `ModuleNotFoundError: No module named 'pandas'`
**Solución**: Instala las dependencias con `pip install -r requirements.txt`

### Error: `python: command not found`
**Solución**: Usa `python3` en lugar de `python` en Linux/Mac

### Error: Archivos no encontrados al ejecutar ejemplos
**Solución**: Ejecuta los ejemplos en orden. El ejemplo 2 genera datos para el 4.

### El entorno virtual no se activa
**Solución**: 
- Windows: Ejecuta `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser` en PowerShell
- Linux/Mac: Verifica que usaste `source venv/bin/activate`

---

## 📞 Contacto y Contribuciones

Este es un proyecto educativo. Si encuentras errores o tienes sugerencias:
1. Revisa la documentación existente
2. Experimenta con los ejemplos
3. Documenta tus aprendizajes

---

## 📄 Licencia

Proyecto educativo de código abierto para aprendizaje de Python.

---

**¡Buena suerte en tu viaje de aprendizaje de Python!** 🐍✨
