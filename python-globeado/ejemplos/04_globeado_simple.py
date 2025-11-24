"""
Ejemplo 4: Globeado Simple - Integración Completa
=================================================

Este script integra todo lo aprendido: lee datos de Excel/CSV y añade globos a un PDF.

Temas cubiertos:
- Leer datos de Excel/CSV con pandas
- Validar datos
- Combinar PDF base con anotaciones
- Script completo de globeado

Ejecutar: python ejemplos/04_globeado_simple.py
"""

import pandas as pd
from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
from PyPDF2 import PdfReader, PdfWriter
import io
import os

print("=" * 60)
print("GLOBEADO SIMPLE - INTEGRACIÓN COMPLETA")
print("=" * 60)

# ============================================================================
# CONFIGURACIÓN
# ============================================================================

# Rutas
base_dir = os.path.dirname(__file__)
data_dir = os.path.join(base_dir, "..", "data")
output_dir = os.path.join(base_dir, "..", "output")

ARCHIVO_DATOS = os.path.join(data_dir, "globeado_ejemplo.csv")
PDF_BASE = os.path.join(data_dir, "plano_ejemplo.pdf")
PDF_SALIDA = os.path.join(output_dir, "plano_con_globos.pdf")

# Crear carpetas si no existen
os.makedirs(output_dir, exist_ok=True)

# ============================================================================
# 1. LEER DATOS
# ============================================================================
print("\n1. LEYENDO DATOS")
print("-" * 60)


def leer_datos(archivo):
    """
    Lee datos de Excel o CSV.
    
    Args:
        archivo (str): Ruta al archivo
    
    Returns:
        pandas.DataFrame: Datos leídos
    
    Raises:
        ValueError: Si el formato no es soportado
        FileNotFoundError: Si el archivo no existe
    """
    if not os.path.exists(archivo):
        raise FileNotFoundError(f"Archivo no encontrado: {archivo}")
    
    if archivo.endswith(".xlsx"):
        df = pd.read_excel(archivo, engine="openpyxl")
    elif archivo.endswith(".csv"):
        df = pd.read_csv(archivo)
    else:
        raise ValueError("Formato no soportado. Usa .xlsx o .csv")
    
    return df


# Verificar que los archivos de ejemplo existen
if not os.path.exists(ARCHIVO_DATOS):
    print(f"⚠️  Archivo de datos no encontrado: {ARCHIVO_DATOS}")
    print("   Ejecuta primero: python ejemplos/02_leer_csv_excel.py")
    exit(1)

if not os.path.exists(PDF_BASE):
    print(f"⚠️  PDF base no encontrado: {PDF_BASE}")
    print("   Ejecuta primero: python ejemplos/03_manipular_pdf.py")
    exit(1)

# Leer datos
try:
    df = leer_datos(ARCHIVO_DATOS)
    print(f"✓ Datos leídos: {len(df)} elementos")
    print(f"  Columnas: {df.columns.tolist()}")
    print(f"\nPrimeras filas:")
    print(df.head())
except Exception as e:
    print(f"✗ Error al leer datos: {e}")
    exit(1)

# ============================================================================
# 2. VALIDAR DATOS
# ============================================================================
print("\n2. VALIDANDO DATOS")
print("-" * 60)


def validar_datos(df):
    """
    Valida que el DataFrame tenga la estructura correcta.
    
    Args:
        df (pandas.DataFrame): DataFrame a validar
    
    Returns:
        tuple: (valido, errores)
    """
    errores = []
    
    # Verificar columnas requeridas
    columnas_requeridas = ["x", "y", "texto"]
    for col in columnas_requeridas:
        if col not in df.columns:
            errores.append(f"Falta columna requerida: '{col}'")
    
    if errores:
        return False, errores
    
    # Verificar tipos numéricos
    if not pd.api.types.is_numeric_dtype(df["x"]):
        errores.append("La columna 'x' debe ser numérica")
    
    if not pd.api.types.is_numeric_dtype(df["y"]):
        errores.append("La columna 'y' debe ser numérica")
    
    # Verificar valores nulos
    if df[columnas_requeridas].isnull().any().any():
        errores.append("Hay valores nulos en columnas requeridas")
    
    # Verificar coordenadas positivas
    if "x" in df.columns and (df["x"] < 0).any():
        errores.append("La columna 'x' tiene valores negativos")
    
    if "y" in df.columns and (df["y"] < 0).any():
        errores.append("La columna 'y' tiene valores negativos")
    
    valido = len(errores) == 0
    return valido, errores


# Validar
valido, errores = validar_datos(df)
if valido:
    print("✓ Datos válidos")
else:
    print("✗ Datos inválidos:")
    for error in errores:
        print(f"  - {error}")
    exit(1)

# ============================================================================
# 3. MAPEO DE COLORES
# ============================================================================
print("\n3. PREPARANDO CONFIGURACIÓN")
print("-" * 60)

# Mapeo de nombres de colores a RGB
COLORES = {
    "rojo": (1, 0, 0),
    "azul": (0, 0, 1),
    "verde": (0, 1, 0),
    "amarillo": (1, 1, 0),
    "naranja": (1, 0.5, 0),
    "morado": (0.5, 0, 0.5),
    "negro": (0, 0, 0),
    "gris": (0.5, 0.5, 0.5),
}


def obtener_color_rgb(nombre_color):
    """
    Convierte nombre de color a RGB.
    
    Args:
        nombre_color (str): Nombre del color
    
    Returns:
        tuple: RGB (valores 0-1)
    """
    return COLORES.get(nombre_color.lower(), (1, 0, 0))  # Default: rojo


print(f"✓ {len(COLORES)} colores disponibles: {', '.join(COLORES.keys())}")

# ============================================================================
# 4. FUNCIÓN PARA DIBUJAR GLOBOS
# ============================================================================
print("\n4. CONFIGURANDO FUNCIONES DE DIBUJO")
print("-" * 60)


def dibujar_globo(canvas_obj, x, y, texto, color_rgb=(1, 0, 0), radio=15):
    """
    Dibuja un globo (círculo) con texto al lado.
    
    Args:
        canvas_obj: Objeto canvas de reportlab
        x (float): Coordenada X
        y (float): Coordenada Y
        texto (str): Texto a mostrar
        color_rgb (tuple): Color RGB (valores 0-1)
        radio (float): Radio del círculo
    """
    # Dibujar círculo
    canvas_obj.setStrokeColorRGB(*color_rgb)
    canvas_obj.setFillColorRGB(1, 1, 1)  # Blanco
    canvas_obj.setLineWidth(2)
    canvas_obj.circle(x, y, radio, stroke=1, fill=1)
    
    # Dibujar texto
    canvas_obj.setFillColorRGB(0, 0, 0)  # Negro
    canvas_obj.setFont("Helvetica", 10)
    canvas_obj.drawString(x + radio + 5, y - 5, texto)


print("✓ Función de dibujo configurada")

# ============================================================================
# 5. CREAR CAPA DE ANOTACIONES
# ============================================================================
print("\n5. CREANDO CAPA DE ANOTACIONES")
print("-" * 60)


def crear_capa_anotaciones(datos):
    """
    Crea un PDF en memoria con las anotaciones (globos).
    
    Args:
        datos (pandas.DataFrame): Datos con coordenadas
    
    Returns:
        BytesIO: Buffer con el PDF de anotaciones
    """
    # Crear canvas en memoria
    packet = io.BytesIO()
    c = canvas.Canvas(packet, pagesize=A4)
    
    contador = 0
    for index, fila in datos.iterrows():
        x = float(fila["x"])
        y = float(fila["y"])
        texto = str(fila["texto"])
        
        # Obtener color si existe
        color_rgb = (1, 0, 0)  # Default: rojo
        if "color" in fila and pd.notna(fila["color"]):
            color_rgb = obtener_color_rgb(fila["color"])
        
        # Dibujar globo
        dibujar_globo(c, x, y, texto, color_rgb)
        contador += 1
    
    # Guardar y mover al inicio
    c.save()
    packet.seek(0)
    
    print(f"✓ {contador} globos añadidos a la capa de anotaciones")
    return packet


# Crear capa
capa_anotaciones = crear_capa_anotaciones(df)

# ============================================================================
# 6. COMBINAR CON PDF BASE
# ============================================================================
print("\n6. COMBINANDO CON PDF BASE")
print("-" * 60)


def combinar_pdfs(pdf_base, capa_anotaciones, pdf_salida):
    """
    Combina el PDF base con la capa de anotaciones.
    
    Args:
        pdf_base (str): Ruta al PDF base
        capa_anotaciones (BytesIO): Buffer con anotaciones
        pdf_salida (str): Ruta al PDF de salida
    """
    # Leer PDF base
    lector_base = PdfReader(pdf_base)
    pagina_base = lector_base.pages[0]
    
    # Leer capa de anotaciones
    lector_anotaciones = PdfReader(capa_anotaciones)
    pagina_anotaciones = lector_anotaciones.pages[0]
    
    # Combinar (merge)
    pagina_base.merge_page(pagina_anotaciones)
    
    # Escribir resultado
    escritor = PdfWriter()
    escritor.add_page(pagina_base)
    
    with open(pdf_salida, "wb") as archivo_salida:
        escritor.write(archivo_salida)


# Combinar
try:
    combinar_pdfs(PDF_BASE, capa_anotaciones, PDF_SALIDA)
    print(f"✓ PDF combinado guardado en: {PDF_SALIDA}")
except Exception as e:
    print(f"✗ Error al combinar PDFs: {e}")
    exit(1)

# ============================================================================
# 7. VERIFICAR RESULTADO
# ============================================================================
print("\n7. VERIFICANDO RESULTADO")
print("-" * 60)

try:
    with open(PDF_SALIDA, "rb") as archivo:
        lector = PdfReader(archivo)
        num_paginas = len(lector.pages)
        print(f"✓ PDF generado correctamente")
        print(f"  - Número de páginas: {num_paginas}")
        print(f"  - Tamaño: {os.path.getsize(PDF_SALIDA)} bytes")
except Exception as e:
    print(f"✗ Error al verificar PDF: {e}")

# ============================================================================
# RESUMEN
# ============================================================================
print("\n" + "=" * 60)
print("RESUMEN DEL PROCESO")
print("=" * 60)
print(f"""
✓ Datos leídos desde: {ARCHIVO_DATOS}
✓ PDF base: {PDF_BASE}
✓ Globos añadidos: {len(df)}
✓ PDF final generado: {PDF_SALIDA}

Proceso completado exitosamente. Abre el PDF para ver el resultado.

PASOS REALIZADOS:
1. Leer datos de CSV/Excel con pandas
2. Validar estructura y tipos de datos
3. Crear capa de anotaciones en memoria
4. Dibujar globos según coordenadas y colores
5. Combinar capa con PDF base usando PyPDF2
6. Guardar PDF final

PRÓXIMOS PASOS:
- Implementa el script completo en /src/main.py
- Añade configuración en /src/config.py
- Añade logging para depuración
- Crea tests en /tests/
""")

print("\n" + "=" * 60)
print("¡FELICIDADES! Has completado todos los ejemplos.")
print("=" * 60)
print("""
Ahora tienes las bases para:
1. Leer datos de Excel/CSV
2. Manipular PDFs
3. Integrar ambos en un script completo

Siguiente paso: Implementar el sistema completo en /src/
""")
