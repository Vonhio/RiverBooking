# Globeado Punto a Punto en PDFs

## ¿Qué es el "Globeado Punto a Punto"?

**Globeado** significa añadir **etiquetas, globos o anotaciones** (círculos, rectángulos, texto) en un PDF en posiciones específicas.

**Punto a punto** significa que cada etiqueta se coloca en una **coordenada exacta** (X, Y) definida previamente.

### Caso de Uso Típico

Imagina que tienes:
1. Un **PDF con un plano** (edificio, mapa, diagrama técnico)
2. Un **Excel/CSV con datos**:
   - Nombre del elemento
   - Coordenadas X, Y donde colocarlo
   - Texto a mostrar en el globo

El script debe:
1. Leer los datos del Excel/CSV
2. Abrir el PDF
3. Dibujar globos/etiquetas en las coordenadas especificadas
4. Guardar el PDF modificado

---

## Ejemplo Visual

### PDF Original (plano de oficina)
```
┌─────────────────────────────┐
│                             │
│                             │
│                             │
│                             │
│                             │
└─────────────────────────────┘
```

### Excel con datos
| ID | Elemento | X | Y | Texto |
|----|----------|---|---|-------|
| 1 | Escritorio | 100 | 200 | Mesa 1 |
| 2 | Silla | 150 | 220 | Silla A |
| 3 | Armario | 300 | 400 | Archivador |

### PDF con globos añadidos
```
┌─────────────────────────────┐
│                             │
│    ⭕ Mesa 1                │
│      ⭕ Silla A             │
│                             │
│                      ⭕ Archivador
└─────────────────────────────┘
```

---

## Arquitectura del Sistema

```
┌──────────────────────────────────────────────────┐
│ 1. ENTRADA DE DATOS                             │
├──────────────────────────────────────────────────┤
│ - Excel (.xlsx) o CSV (.csv)                     │
│ - Columnas: ID, Elemento, X, Y, Texto, Color, etc. │
└──────────────────────────────────────────────────┘
                    ↓
┌──────────────────────────────────────────────────┐
│ 2. SCRIPT PYTHON                                 │
├──────────────────────────────────────────────────┤
│ Módulo: lector_datos.py                          │
│ - Lee Excel/CSV con pandas                       │
│ - Valida datos (coordenadas, textos)             │
│                                                  │
│ Módulo: procesador_pdf.py                        │
│ - Abre PDF con PyPDF2/reportlab                  │
│ - Calcula posiciones según cuadrícula            │
│ - Dibuja globos/etiquetas                        │
│                                                  │
│ Módulo: generador_salida.py                      │
│ - Guarda PDF modificado                          │
│ - Genera log de operaciones                      │
└──────────────────────────────────────────────────┘
                    ↓
┌──────────────────────────────────────────────────┐
│ 3. SALIDA                                        │
├──────────────────────────────────────────────────┤
│ - PDF con globos/etiquetas                       │
│ - Log de operaciones (qué se añadió, errores)    │
└──────────────────────────────────────────────────┘
```

---

## Librerías Necesarias

### 1. **pandas** - Leer/procesar datos
```python
import pandas as pd

# Leer Excel
df = pd.read_excel("datos.xlsx")

# Leer CSV
df = pd.read_csv("datos.csv")

# Acceder a datos
for index, fila in df.iterrows():
    print(fila["Elemento"], fila["X"], fila["Y"])
```

**Por qué pandas**:
- Lee Excel/CSV fácilmente
- Valida y limpia datos
- Maneja datos faltantes
- Filtra y transforma datos

### 2. **PyPDF2** - Leer PDFs existentes
```python
import PyPDF2

with open("plano.pdf", "rb") as archivo:
    lector = PyPDF2.PdfReader(archivo)
    pagina = lector.pages[0]
    ancho = pagina.mediabox.width
    alto = pagina.mediabox.height
```

**Por qué PyPDF2**:
- Lee PDFs existentes
- Extrae información de páginas
- Fusiona PDFs

### 3. **reportlab** - Crear/modificar PDFs
```python
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4

# Crear PDF
c = canvas.Canvas("salida.pdf", pagesize=A4)

# Dibujar círculo (globo)
c.circle(100, 200, 15, stroke=1, fill=0)

# Añadir texto
c.drawString(120, 200, "Mesa 1")

c.save()
```

**Por qué reportlab**:
- Crea PDFs desde cero
- Dibuja formas geométricas
- Añade texto en posiciones exactas
- Combina con PDFs existentes

### 4. **openpyxl** (alternativa a pandas para Excel)
```python
import openpyxl

libro = openpyxl.load_workbook("datos.xlsx")
hoja = libro.active

for fila in hoja.iter_rows(min_row=2, values_only=True):
    elemento, x, y, texto = fila
    print(elemento, x, y, texto)
```

**Cuándo usar**:
- Si solo necesitas leer Excel (más ligero que pandas)
- Si necesitas escribir Excel con formato

---

## Flujo del Script

### Paso 1: Leer datos

```python
import pandas as pd

def leer_datos(archivo):
    """
    Lee datos de Excel/CSV.
    
    Args:
        archivo (str): Ruta al archivo
    
    Returns:
        pandas.DataFrame: Datos leídos
    """
    if archivo.endswith(".xlsx"):
        df = pd.read_excel(archivo)
    elif archivo.endswith(".csv"):
        df = pd.read_csv(archivo)
    else:
        raise ValueError("Formato no soportado")
    
    # Validar columnas requeridas
    columnas_requeridas = ["Elemento", "X", "Y", "Texto"]
    for col in columnas_requeridas:
        if col not in df.columns:
            raise ValueError(f"Falta columna: {col}")
    
    return df
```

### Paso 2: Abrir PDF y preparar canvas

```python
from PyPDF2 import PdfReader, PdfWriter
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
import io

def preparar_pdf(pdf_entrada):
    """
    Prepara el PDF para añadir anotaciones.
    
    Args:
        pdf_entrada (str): Ruta al PDF original
    
    Returns:
        tuple: (lector, pagina_original)
    """
    lector = PdfReader(pdf_entrada)
    pagina = lector.pages[0]  # Primera página
    
    return lector, pagina
```

### Paso 3: Dibujar globos/etiquetas

```python
def dibujar_globos(datos, pdf_salida):
    """
    Dibuja globos en el PDF según los datos.
    
    Args:
        datos (pandas.DataFrame): Datos con coordenadas
        pdf_salida (str): Ruta al PDF de salida
    """
    # Crear canvas en memoria
    packet = io.BytesIO()
    can = canvas.Canvas(packet, pagesize=A4)
    
    for index, fila in datos.iterrows():
        x = float(fila["X"])
        y = float(fila["Y"])
        texto = str(fila["Texto"])
        
        # Dibujar círculo (globo)
        can.setStrokeColorRGB(1, 0, 0)  # Rojo
        can.setFillColorRGB(1, 1, 1)    # Blanco
        can.circle(x, y, 15, stroke=1, fill=1)
        
        # Dibujar texto
        can.setFillColorRGB(0, 0, 0)    # Negro
        can.drawString(x + 20, y - 5, texto)
    
    can.save()
    
    # Mover al inicio del buffer
    packet.seek(0)
    
    return packet
```

### Paso 4: Combinar con PDF original

```python
def combinar_pdfs(pdf_original, anotaciones, pdf_salida):
    """
    Combina el PDF original con las anotaciones.
    
    Args:
        pdf_original (str): Ruta al PDF original
        anotaciones (BytesIO): Buffer con anotaciones
        pdf_salida (str): Ruta al PDF de salida
    """
    # Leer PDF original
    lector_original = PdfReader(pdf_original)
    pagina_original = lector_original.pages[0]
    
    # Leer anotaciones
    lector_anotaciones = PdfReader(anotaciones)
    pagina_anotaciones = lector_anotaciones.pages[0]
    
    # Combinar (merge)
    pagina_original.merge_page(pagina_anotaciones)
    
    # Escribir resultado
    escritor = PdfWriter()
    escritor.add_page(pagina_original)
    
    with open(pdf_salida, "wb") as archivo_salida:
        escritor.write(archivo_salida)
```

### Paso 5: Script principal

```python
def main():
    """Función principal."""
    # Configuración
    archivo_datos = "data/datos.xlsx"
    pdf_entrada = "data/plano.pdf"
    pdf_salida = "output/plano_con_globos.pdf"
    
    print("1. Leyendo datos...")
    datos = leer_datos(archivo_datos)
    print(f"   ✓ {len(datos)} elementos encontrados")
    
    print("2. Preparando PDF...")
    lector, pagina = preparar_pdf(pdf_entrada)
    print(f"   ✓ PDF cargado")
    
    print("3. Dibujando globos...")
    anotaciones = dibujar_globos(datos, pdf_salida)
    print(f"   ✓ Globos dibujados")
    
    print("4. Generando PDF final...")
    combinar_pdfs(pdf_entrada, anotaciones, pdf_salida)
    print(f"   ✓ PDF guardado en: {pdf_salida}")

if __name__ == "__main__":
    main()
```

---

## Conceptos Clave para Entender

### 1. Sistema de Coordenadas en PDFs

Los PDFs usan coordenadas cartesianas:
- **Origen (0, 0)**: Esquina inferior izquierda
- **X**: Aumenta hacia la derecha
- **Y**: Aumenta hacia arriba (⚠️ diferente a pantallas)

```
Y
↑
│         ● (300, 400)
│
│    ● (150, 220)
│  ● (100, 200)
│
└──────────────────→ X
(0, 0)
```

### 2. Unidades en reportlab

Por defecto, reportlab usa **puntos** (1 punto = 1/72 pulgadas).

- **A4**: 595 × 842 puntos (ancho × alto)
- 1 cm ≈ 28.35 puntos
- 1 pulgada = 72 puntos

```python
from reportlab.lib.units import cm, mm

x = 5 * cm   # 5 centímetros
y = 100 * mm # 100 milímetros
```

### 3. Merge de PDFs

Cuando quieres añadir contenido a un PDF existente:
1. Creas un PDF "transparente" con las anotaciones
2. Lo fusionas con el PDF original usando `merge_page()`

---

## Estructura del Proyecto Python

```
python-globeado/
├── README.md              # Documentación del proyecto
├── requirements.txt       # Dependencias
├── .gitignore            # Excluir venv, __pycache__, etc.
│
├── src/                   # Código fuente
│   ├── __init__.py
│   ├── main.py           # Script principal
│   ├── lector_datos.py   # Lectura de Excel/CSV
│   ├── procesador_pdf.py # Manipulación de PDFs
│   └── config.py         # Configuración (rutas, colores, etc.)
│
├── data/                  # Datos de entrada
│   ├── ejemplo.xlsx
│   ├── ejemplo.csv
│   └── plano_ejemplo.pdf
│
├── output/                # PDFs generados
│   └── .gitkeep
│
├── tests/                 # Tests unitarios
│   ├── __init__.py
│   └── test_lector_datos.py
│
└── ejemplos/              # Scripts educativos paso a paso
    ├── 01_conceptos_basicos.py
    ├── 02_leer_csv_excel.py
    ├── 03_manipular_pdf.py
    └── 04_globeado_simple.py
```

---

## requirements.txt

```txt
pandas==2.2.0
openpyxl==3.1.2
PyPDF2==3.0.1
reportlab==4.0.9
```

**Instalar**:
```bash
pip install -r requirements.txt
```

---

## Ejemplo de Datos (Excel/CSV)

### datos.xlsx / datos.csv

| ID | Elemento | X | Y | Texto | Color | TamañoGlobo |
|----|----------|---|---|-------|-------|-------------|
| 1 | Mesa | 100 | 200 | Mesa 1 | rojo | 15 |
| 2 | Silla | 150 | 220 | Silla A | azul | 12 |
| 3 | Armario | 300 | 400 | Archivador | verde | 20 |

---

## Validaciones Importantes

### 1. Validar coordenadas

```python
def validar_coordenadas(x, y, ancho_pagina, alto_pagina):
    """
    Verifica que las coordenadas estén dentro del PDF.
    
    Args:
        x (float): Coordenada X
        y (float): Coordenada Y
        ancho_pagina (float): Ancho del PDF
        alto_pagina (float): Alto del PDF
    
    Returns:
        bool: True si son válidas
    """
    if x < 0 or x > ancho_pagina:
        return False
    if y < 0 or y > alto_pagina:
        return False
    return True
```

### 2. Validar datos de entrada

```python
def validar_datos(df):
    """
    Valida el DataFrame con los datos.
    
    Args:
        df (pandas.DataFrame): Datos a validar
    
    Raises:
        ValueError: Si hay datos inválidos
    """
    # Verificar columnas
    columnas_requeridas = ["Elemento", "X", "Y", "Texto"]
    for col in columnas_requeridas:
        if col not in df.columns:
            raise ValueError(f"Falta columna requerida: {col}")
    
    # Verificar tipos
    if not pd.api.types.is_numeric_dtype(df["X"]):
        raise ValueError("La columna X debe ser numérica")
    
    if not pd.api.types.is_numeric_dtype(df["Y"]):
        raise ValueError("La columna Y debe ser numérica")
    
    # Verificar valores nulos
    if df[columnas_requeridas].isnull().any().any():
        raise ValueError("Hay valores nulos en columnas requeridas")
```

---

## Control de Errores

```python
import logging

# Configurar logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(levelname)s - %(message)s",
    handlers=[
        logging.FileHandler("globeado.log"),
        logging.StreamHandler()
    ]
)

logger = logging.getLogger(__name__)

def procesar_con_errores():
    """Procesa con manejo de errores."""
    try:
        logger.info("Iniciando proceso...")
        datos = leer_datos("datos.xlsx")
        logger.info(f"Datos leídos: {len(datos)} filas")
        
        # ... procesamiento ...
        
        logger.info("Proceso completado exitosamente")
    except FileNotFoundError as e:
        logger.error(f"Archivo no encontrado: {e}")
    except ValueError as e:
        logger.error(f"Datos inválidos: {e}")
    except Exception as e:
        logger.error(f"Error inesperado: {e}")
```

---

## Pruebas Básicas

```python
import unittest
import pandas as pd

class TestLectorDatos(unittest.TestCase):
    """Tests para lector_datos.py"""
    
    def test_leer_csv(self):
        """Verifica que se lea un CSV correctamente."""
        df = leer_datos("data/ejemplo.csv")
        self.assertIsInstance(df, pd.DataFrame)
        self.assertIn("X", df.columns)
        self.assertIn("Y", df.columns)
    
    def test_validar_datos_correctos(self):
        """Verifica validación con datos correctos."""
        df = pd.DataFrame({
            "Elemento": ["Mesa"],
            "X": [100],
            "Y": [200],
            "Texto": ["Mesa 1"]
        })
        validar_datos(df)  # No debe lanzar error
    
    def test_validar_datos_incorrectos(self):
        """Verifica validación con datos incorrectos."""
        df = pd.DataFrame({
            "Elemento": ["Mesa"],
            "X": ["texto"],  # Debe ser número
            "Y": [200],
            "Texto": ["Mesa 1"]
        })
        with self.assertRaises(ValueError):
            validar_datos(df)

if __name__ == "__main__":
    unittest.main()
```

---

## Plan de Implementación (2 Semanas)

### Semana 1: Fundamentos
- **Día 1-2**: Instalar Python, entorno virtual, librerías
- **Día 3-4**: Practicar con ejemplos básicos (leer CSV, manipular PDFs)
- **Día 5-6**: Implementar lectura de datos con validación
- **Día 7**: Implementar dibujo de formas simples en PDFs

### Semana 2: Integración y Refinamiento
- **Día 8-9**: Integrar lectura + dibujo en un script completo
- **Día 10-11**: Añadir configuración, logging, manejo de errores
- **Día 12**: Tests básicos
- **Día 13**: Refinamiento y optimización
- **Día 14**: Demo y documentación

---

## Próximos Pasos

1. ✅ Has entendido qué es el globeado punto a punto
2. 💻 Revisa los ejemplos en `/python-globeado/ejemplos/`
3. 🔧 Practica con datos de prueba
4. 🚀 Implementa el script paso a paso
