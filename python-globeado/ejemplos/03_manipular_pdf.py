"""
Ejemplo 3: Manipular PDFs con PyPDF2 y reportlab
================================================

Este script enseña a crear y manipular archivos PDF.

Temas cubiertos:
- Crear PDFs desde cero con reportlab
- Leer PDFs con PyPDF2
- Dibujar formas (círculos, rectángulos, líneas)
- Añadir texto
- Sistema de coordenadas

Ejecutar: python ejemplos/03_manipular_pdf.py
"""

from reportlab.pdfgen import canvas
from reportlab.lib.pagesizes import A4
from reportlab.lib.units import cm, mm
from reportlab.lib.colors import red, blue, green, black
from PyPDF2 import PdfReader
import os

print("=" * 60)
print("MANIPULAR PDFs CON REPORTLAB Y PyPDF2")
print("=" * 60)

# Crear carpetas necesarias
data_dir = os.path.join(os.path.dirname(__file__), "..", "data")
output_dir = os.path.join(os.path.dirname(__file__), "..", "output")
os.makedirs(data_dir, exist_ok=True)
os.makedirs(output_dir, exist_ok=True)

# ============================================================================
# 1. SISTEMA DE COORDENADAS
# ============================================================================
print("\n1. SISTEMA DE COORDENADAS EN PDF")
print("-" * 60)
print("""
Los PDFs usan coordenadas cartesianas:
- Origen (0, 0): esquina inferior izquierda
- X: aumenta hacia la derecha →
- Y: aumenta hacia arriba ↑ (⚠️ diferente a pantallas)

Unidades:
- Por defecto: puntos (1 punto = 1/72 pulgadas)
- A4: 595 × 842 puntos (ancho × alto)
- 1 cm ≈ 28.35 puntos
- 1 mm ≈ 2.835 puntos

Ejemplo:
    Y (842)
    ↑
    │         ● (300, 400)
    │
    │    ● (150, 220)
    │  ● (100, 200)
    │
    └──────────────────→ X (595)
  (0, 0)
""")

# ============================================================================
# 2. CREAR PDF SIMPLE
# ============================================================================
print("\n2. CREAR PDF SIMPLE")
print("-" * 60)

ruta_pdf_simple = os.path.join(output_dir, "01_pdf_simple.pdf")

# Crear canvas (lienzo)
c = canvas.Canvas(ruta_pdf_simple, pagesize=A4)
ancho, alto = A4
print(f"Tamaño A4: {ancho:.2f} × {alto:.2f} puntos")

# Añadir texto
c.setFont("Helvetica", 16)
c.drawString(100, 800, "Mi Primer PDF con Python")

c.setFont("Helvetica", 12)
c.drawString(100, 750, "Este PDF fue generado con reportlab")

# Guardar
c.save()
print(f"✓ PDF creado: {ruta_pdf_simple}")

# ============================================================================
# 3. DIBUJAR FORMAS BÁSICAS
# ============================================================================
print("\n3. DIBUJAR FORMAS BÁSICAS")
print("-" * 60)

ruta_pdf_formas = os.path.join(output_dir, "02_formas_basicas.pdf")

c = canvas.Canvas(ruta_pdf_formas, pagesize=A4)

# Título
c.setFont("Helvetica-Bold", 16)
c.drawString(50, 800, "Formas Básicas")

# Línea
c.setStrokeColorRGB(0, 0, 0)  # Negro
c.line(50, 750, 300, 750)
c.setFont("Helvetica", 10)
c.drawString(320, 745, "Línea")

# Rectángulo (sin relleno)
c.setStrokeColorRGB(1, 0, 0)  # Rojo
c.rect(50, 650, 100, 50, stroke=1, fill=0)
c.setFillColorRGB(0, 0, 0)  # Negro para texto
c.drawString(160, 670, "Rectángulo (sin relleno)")

# Rectángulo (con relleno)
c.setStrokeColorRGB(0, 0, 1)  # Azul
c.setFillColorRGB(0.8, 0.8, 1)  # Azul claro
c.rect(50, 550, 100, 50, stroke=1, fill=1)
c.setFillColorRGB(0, 0, 0)
c.drawString(160, 570, "Rectángulo (con relleno)")

# Círculo (sin relleno)
c.setStrokeColorRGB(0, 1, 0)  # Verde
c.circle(100, 450, 30, stroke=1, fill=0)
c.setFillColorRGB(0, 0, 0)
c.drawString(140, 445, "Círculo (sin relleno)")

# Círculo (con relleno)
c.setStrokeColorRGB(1, 0.5, 0)  # Naranja
c.setFillColorRGB(1, 0.9, 0.7)  # Naranja claro
c.circle(100, 350, 30, stroke=1, fill=1)
c.setFillColorRGB(0, 0, 0)
c.drawString(140, 345, "Círculo (con relleno)")

# Guardar
c.save()
print(f"✓ PDF con formas creado: {ruta_pdf_formas}")

# ============================================================================
# 4. USAR UNIDADES (CM, MM)
# ============================================================================
print("\n4. USAR UNIDADES (CM, MM)")
print("-" * 60)

ruta_pdf_unidades = os.path.join(output_dir, "03_unidades.pdf")

c = canvas.Canvas(ruta_pdf_unidades, pagesize=A4)

# Título
c.setFont("Helvetica-Bold", 16)
c.drawString(2*cm, 28*cm, "Usando Unidades (cm y mm)")

# Usando centímetros
c.setStrokeColorRGB(1, 0, 0)
c.circle(5*cm, 20*cm, 1*cm, stroke=1, fill=0)
c.setFillColorRGB(0, 0, 0)
c.drawString(7*cm, 20*cm, "Círculo de 1 cm de radio")

# Usando milímetros
c.setStrokeColorRGB(0, 0, 1)
c.circle(5*cm, 15*cm, 15*mm, stroke=1, fill=0)
c.drawString(7*cm, 15*cm, "Círculo de 15 mm de radio")

# Guardar
c.save()
print(f"✓ PDF con unidades creado: {ruta_pdf_unidades}")

# ============================================================================
# 5. DIBUJAR ETIQUETAS (GLOBOS)
# ============================================================================
print("\n5. DIBUJAR ETIQUETAS (GLOBOS)")
print("-" * 60)

ruta_pdf_globos = os.path.join(output_dir, "04_globos_etiquetas.pdf")

c = canvas.Canvas(ruta_pdf_globos, pagesize=A4)

# Título
c.setFont("Helvetica-Bold", 16)
c.drawString(2*cm, 28*cm, "Globos y Etiquetas")


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
    canvas_obj.circle(x, y, radio, stroke=1, fill=1)
    
    # Dibujar texto
    canvas_obj.setFillColorRGB(0, 0, 0)  # Negro
    canvas_obj.setFont("Helvetica", 10)
    canvas_obj.drawString(x + radio + 5, y - 5, texto)


# Dibujar varios globos
elementos = [
    {"x": 100, "y": 700, "texto": "Mesa 1", "color": (1, 0, 0)},     # Rojo
    {"x": 150, "y": 650, "texto": "Silla A", "color": (0, 0, 1)},    # Azul
    {"x": 300, "y": 600, "texto": "Archivador", "color": (0, 1, 0)}, # Verde
    {"x": 250, "y": 550, "texto": "Estantería", "color": (1, 0.5, 0)}, # Naranja
    {"x": 100, "y": 500, "texto": "Puerta", "color": (0.5, 0, 0.5)}, # Morado
]

for elemento in elementos:
    dibujar_globo(
        c,
        elemento["x"],
        elemento["y"],
        elemento["texto"],
        elemento["color"]
    )

# Guardar
c.save()
print(f"✓ PDF con globos creado: {ruta_pdf_globos}")

# ============================================================================
# 6. LEER PDF CON PyPDF2
# ============================================================================
print("\n6. LEER PDF CON PyPDF2")
print("-" * 60)

# Leer el último PDF creado
with open(ruta_pdf_globos, "rb") as archivo:
    lector = PdfReader(archivo)
    
    num_paginas = len(lector.pages)
    print(f"Número de páginas: {num_paginas}")
    
    # Obtener información de la primera página
    pagina = lector.pages[0]
    mediabox = pagina.mediabox
    ancho = float(mediabox.width)
    alto = float(mediabox.height)
    
    print(f"Dimensiones página 1: {ancho:.2f} × {alto:.2f} puntos")
    print(f"Dimensiones página 1: {ancho/cm:.2f} × {alto/cm:.2f} cm")

# ============================================================================
# 7. CREAR PLANO DE EJEMPLO
# ============================================================================
print("\n7. CREAR PLANO DE EJEMPLO")
print("-" * 60)

ruta_plano = os.path.join(data_dir, "plano_ejemplo.pdf")

c = canvas.Canvas(ruta_plano, pagesize=A4)
ancho, alto = A4

# Título
c.setFont("Helvetica-Bold", 18)
c.drawString(2*cm, 28*cm, "Plano de Oficina")

# Dibujar cuadrícula de referencia
c.setStrokeColorRGB(0.8, 0.8, 0.8)  # Gris claro
c.setFont("Helvetica", 8)

# Líneas verticales cada 2 cm
for i in range(0, int(ancho), int(2*cm)):
    c.line(i, 0, i, alto)
    if i % int(4*cm) == 0:
        c.drawString(i + 2, 5, f"{i}")

# Líneas horizontales cada 2 cm
for i in range(0, int(alto), int(2*cm)):
    c.line(0, i, ancho, i)
    if i % int(4*cm) == 0:
        c.drawString(5, i + 2, f"{i}")

# Dibujar contorno de la oficina
c.setStrokeColorRGB(0, 0, 0)  # Negro
c.setLineWidth(2)
c.rect(3*cm, 5*cm, 15*cm, 20*cm)

# Etiqueta
c.setFont("Helvetica", 10)
c.setFillColorRGB(0, 0, 0)
c.drawString(3*cm, 3*cm, "Plano base para añadir globos")

# Guardar
c.save()
print(f"✓ Plano de ejemplo creado: {ruta_plano}")
print("  Este plano se usará en el siguiente ejemplo para añadir globos")

# ============================================================================
# RESUMEN
# ============================================================================
print("\n" + "=" * 60)
print("RESUMEN")
print("=" * 60)
print(f"""
✓ reportlab: crear y dibujar en PDFs
✓ PyPDF2: leer PDFs existentes
✓ Coordenadas: origen (0,0) abajo-izquierda, Y hacia arriba
✓ Unidades: puntos (default), cm, mm con reportlab.lib.units
✓ Formas: line(), rect(), circle()
✓ Texto: drawString(x, y, texto)
✓ Colores: setStrokeColorRGB(), setFillColorRGB()

Archivos creados:
  {output_dir}/
    - 01_pdf_simple.pdf
    - 02_formas_basicas.pdf
    - 03_unidades.pdf
    - 04_globos_etiquetas.pdf
  {data_dir}/
    - plano_ejemplo.pdf (para usar en el siguiente ejemplo)
""")

print("\nAhora ejecuta: python ejemplos/04_globeado_simple.py")
