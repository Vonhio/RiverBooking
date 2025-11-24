# Python Globeado - Proyecto de Aprendizaje

## Descripción

Este es un proyecto educativo para aprender Python desarrollando un sistema que añade globos/etiquetas a PDFs según datos de Excel/CSV.

## Objetivo

En 2 semanas, crear un script que:
1. Lee datos formateados de Excel/CSV
2. Abre PDFs
3. Coloca globos/etiquetas según coordenadas y texto nominal

## Estructura

```
python-globeado/
├── README.md              # Este archivo
├── requirements.txt       # Dependencias del proyecto
├── ejemplos/              # Scripts educativos paso a paso
│   ├── 01_conceptos_basicos.py
│   ├── 02_leer_csv_excel.py
│   ├── 03_manipular_pdf.py
│   └── 04_globeado_simple.py
├── src/                   # Código del proyecto final
│   ├── main.py
│   ├── lector_datos.py
│   ├── procesador_pdf.py
│   └── config.py
├── data/                  # Datos de ejemplo
│   ├── ejemplo.csv
│   └── ejemplo.xlsx
├── output/                # PDFs generados (no se suben a git)
└── tests/                 # Tests unitarios
```

## Instalación

### 1. Crear entorno virtual

```bash
# En la carpeta python-globeado/
python -m venv venv

# Activar (Linux/Mac)
source venv/bin/activate

# Activar (Windows)
venv\Scripts\activate
```

### 2. Instalar dependencias

```bash
pip install -r requirements.txt
```

## Uso

### Ejemplos educativos

```bash
# Ejecutar ejemplos en orden
python ejemplos/01_conceptos_basicos.py
python ejemplos/02_leer_csv_excel.py
python ejemplos/03_manipular_pdf.py
python ejemplos/04_globeado_simple.py
```

### Script principal (cuando esté implementado)

```bash
python src/main.py --datos data/ejemplo.xlsx --pdf data/plano.pdf --salida output/resultado.pdf
```

## Documentación

Lee los documentos en `/docs/`:
1. `PROYECTO_ACTUAL.md` - Entiende el proyecto RiverBooking
2. `PYTHON_BASICO.md` - Aprende Python viniendo de Java/TypeScript
3. `GLOBEADO_PUNTO_A_PUNTO.md` - Comprende el sistema de globeado

## Plan de Aprendizaje (2 semanas)

### Semana 1: Fundamentos
- [x] Día 1-2: Instalar Python, entorno virtual, librerías
- [ ] Día 3-4: Practicar ejemplos básicos
- [ ] Día 5-6: Implementar lectura de datos
- [ ] Día 7: Dibujo básico en PDFs

### Semana 2: Integración
- [ ] Día 8-9: Script completo
- [ ] Día 10-11: Refinamiento (config, logging, errores)
- [ ] Día 12: Tests
- [ ] Día 13-14: Demo final

## Convenciones de Código

- **Nombres**: `snake_case` (funciones, variables) y `PascalCase` (clases)
- **Indentación**: 4 espacios (no tabs)
- **Documentación**: Docstrings en todas las funciones públicas
- **Imports**: Orden: estándar → externos → propios

## Recursos

- [Documentación Python](https://docs.python.org/es/3/)
- [pandas](https://pandas.pydata.org/docs/)
- [reportlab](https://docs.reportlab.com/)
- [PyPDF2](https://pypdf2.readthedocs.io/)

## Autor

Desarrollador junior aprendiendo Python para automatización de procesos.
