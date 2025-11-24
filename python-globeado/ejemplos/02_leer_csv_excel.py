"""
Ejemplo 2: Leer CSV y Excel con pandas
======================================

Este script enseña a leer y procesar archivos CSV y Excel usando pandas.

Temas cubiertos:
- Leer CSV y Excel
- Explorar DataFrames
- Filtrar y seleccionar datos
- Validar datos

Ejecutar: python ejemplos/02_leer_csv_excel.py
"""

import pandas as pd
import os

print("=" * 60)
print("LEER CSV Y EXCEL CON PANDAS")
print("=" * 60)

# ============================================================================
# 1. ¿QUÉ ES PANDAS?
# ============================================================================
print("\n1. ¿QUÉ ES PANDAS?")
print("-" * 60)
print("""
pandas es LA librería de Python para trabajar con datos tabulares.
Piensa en pandas como "Excel en Python".

Conceptos clave:
- DataFrame: tabla de datos (como una hoja de Excel)
- Series: una columna de un DataFrame
- Index: índice de filas
""")

# ============================================================================
# 2. CREAR DATOS DE EJEMPLO
# ============================================================================
print("\n2. CREAR DATOS DE EJEMPLO")
print("-" * 60)

# Crear DataFrame desde un diccionario
datos_reservas = {
    "codigo": ["R001", "R002", "R003", "R004", "R005"],
    "barco": ["Crucero", "Velero", "Yate", "Crucero", "Lancha"],
    "plazas": [4, 2, 6, 3, 8],
    "precio": [25.50, 30.00, 45.00, 25.50, 20.00],
    "fecha": ["2025-12-01", "2025-12-02", "2025-12-01", "2025-12-03", "2025-12-02"]
}

df_reservas = pd.DataFrame(datos_reservas)
print("DataFrame creado:")
print(df_reservas)

# ============================================================================
# 3. EXPLORAR DATAFRAME
# ============================================================================
print("\n3. EXPLORAR DATAFRAME")
print("-" * 60)

print(f"\nDimensiones (filas, columnas): {df_reservas.shape}")
print(f"Número de filas: {len(df_reservas)}")
print(f"Columnas: {df_reservas.columns.tolist()}")

print("\nPrimeras 3 filas:")
print(df_reservas.head(3))

print("\nÚltimas 2 filas:")
print(df_reservas.tail(2))

print("\nInformación del DataFrame:")
print(df_reservas.info())

print("\nEstadísticas descriptivas:")
print(df_reservas.describe())

# ============================================================================
# 4. ACCEDER A DATOS
# ============================================================================
print("\n4. ACCEDER A DATOS")
print("-" * 60)

# Acceder a una columna (devuelve Series)
print("\nColumna 'barco':")
print(df_reservas["barco"])

# Acceder a múltiples columnas
print("\nColumnas 'codigo' y 'plazas':")
print(df_reservas[["codigo", "plazas"]])

# Acceder a una fila por índice
print("\nFila en índice 2:")
print(df_reservas.iloc[2])

# Acceder a un valor específico
codigo_primera_fila = df_reservas.loc[0, "codigo"]
print(f"\nCódigo de la primera fila: {codigo_primera_fila}")

# ============================================================================
# 5. FILTRAR DATOS
# ============================================================================
print("\n5. FILTRAR DATOS")
print("-" * 60)

# Filtrar por condición
reservas_grandes = df_reservas[df_reservas["plazas"] > 4]
print("\nReservas con más de 4 plazas:")
print(reservas_grandes)

# Filtrar por múltiples condiciones
reservas_crucero = df_reservas[
    (df_reservas["barco"] == "Crucero") & 
    (df_reservas["plazas"] >= 3)
]
print("\nReservas de Crucero con 3+ plazas:")
print(reservas_crucero)

# Filtrar por lista de valores
barcos_interes = ["Crucero", "Yate"]
reservas_filtradas = df_reservas[df_reservas["barco"].isin(barcos_interes)]
print("\nReservas de Crucero o Yate:")
print(reservas_filtradas)

# ============================================================================
# 6. ITERAR SOBRE FILAS
# ============================================================================
print("\n6. ITERAR SOBRE FILAS")
print("-" * 60)

print("\nIterando sobre filas:")
for indice, fila in df_reservas.iterrows():
    codigo = fila["codigo"]
    barco = fila["barco"]
    plazas = fila["plazas"]
    print(f"  {codigo}: {plazas} plazas en {barco}")

# ============================================================================
# 7. OPERACIONES COMUNES
# ============================================================================
print("\n7. OPERACIONES COMUNES")
print("-" * 60)

# Calcular total
total_plazas = df_reservas["plazas"].sum()
print(f"\nTotal de plazas reservadas: {total_plazas}")

# Calcular media
precio_medio = df_reservas["precio"].mean()
print(f"Precio medio: {precio_medio:.2f} €")

# Contar valores únicos
barcos_unicos = df_reservas["barco"].nunique()
print(f"Número de barcos diferentes: {barcos_unicos}")

# Agrupar y contar
print("\nReservas por barco:")
print(df_reservas["barco"].value_counts())

# Agrupar y sumar
print("\nPlazas totales por barco:")
print(df_reservas.groupby("barco")["plazas"].sum())

# ============================================================================
# 8. AÑADIR/MODIFICAR COLUMNAS
# ============================================================================
print("\n8. AÑADIR/MODIFICAR COLUMNAS")
print("-" * 60)

# Añadir columna calculada
df_reservas["total"] = df_reservas["plazas"] * df_reservas["precio"]
print("\nDataFrame con columna 'total':")
print(df_reservas)

# Modificar columna existente
df_reservas["precio"] = df_reservas["precio"] * 1.10  # Subir precios 10%
print("\nPrecios actualizados (+10%):")
print(df_reservas[["codigo", "precio"]])

# ============================================================================
# 9. GUARDAR DATOS
# ============================================================================
print("\n9. GUARDAR DATOS")
print("-" * 60)

# Crear carpeta data si no existe
data_dir = os.path.join(os.path.dirname(__file__), "..", "data")
os.makedirs(data_dir, exist_ok=True)

# Guardar CSV
ruta_csv = os.path.join(data_dir, "reservas_ejemplo.csv")
df_reservas.to_csv(ruta_csv, index=False, encoding="utf-8")
print(f"\n✓ CSV guardado en: {ruta_csv}")

# Guardar Excel
ruta_excel = os.path.join(data_dir, "reservas_ejemplo.xlsx")
df_reservas.to_excel(ruta_excel, index=False, engine="openpyxl")
print(f"✓ Excel guardado en: {ruta_excel}")

# ============================================================================
# 10. LEER DATOS
# ============================================================================
print("\n10. LEER DATOS")
print("-" * 60)

# Leer CSV
df_desde_csv = pd.read_csv(ruta_csv)
print("\nDataFrame leído desde CSV:")
print(df_desde_csv.head())

# Leer Excel
df_desde_excel = pd.read_excel(ruta_excel, engine="openpyxl")
print("\nDataFrame leído desde Excel:")
print(df_desde_excel.head())

# ============================================================================
# 11. VALIDAR DATOS
# ============================================================================
print("\n11. VALIDAR DATOS")
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
    columnas_requeridas = ["codigo", "barco", "plazas", "precio"]
    for col in columnas_requeridas:
        if col not in df.columns:
            errores.append(f"Falta columna requerida: {col}")
    
    # Verificar tipos de datos
    if "plazas" in df.columns:
        if not pd.api.types.is_numeric_dtype(df["plazas"]):
            errores.append("La columna 'plazas' debe ser numérica")
    
    if "precio" in df.columns:
        if not pd.api.types.is_numeric_dtype(df["precio"]):
            errores.append("La columna 'precio' debe ser numérica")
    
    # Verificar valores nulos
    if df[columnas_requeridas].isnull().any().any():
        errores.append("Hay valores nulos en columnas requeridas")
    
    # Verificar valores negativos
    if "plazas" in df.columns and (df["plazas"] < 0).any():
        errores.append("La columna 'plazas' tiene valores negativos")
    
    if "precio" in df.columns and (df["precio"] < 0).any():
        errores.append("La columna 'precio' tiene valores negativos")
    
    valido = len(errores) == 0
    return valido, errores


# Validar
valido, errores = validar_datos(df_desde_csv)
if valido:
    print("✓ Datos válidos")
else:
    print("✗ Datos inválidos:")
    for error in errores:
        print(f"  - {error}")

# ============================================================================
# 12. DATOS PARA GLOBEADO
# ============================================================================
print("\n12. DATOS PARA GLOBEADO")
print("-" * 60)

# Crear datos de ejemplo para globeado de PDFs
datos_globeado = {
    "id": [1, 2, 3, 4, 5],
    "elemento": ["Mesa", "Silla", "Armario", "Estantería", "Puerta"],
    "x": [100, 150, 300, 250, 50],
    "y": [200, 220, 400, 350, 100],
    "texto": ["Mesa 1", "Silla A", "Archivador", "Libros", "Entrada"],
    "color": ["rojo", "azul", "verde", "amarillo", "negro"]
}

df_globeado = pd.DataFrame(datos_globeado)
print("DataFrame para globeado:")
print(df_globeado)

# Guardar
ruta_globeado_csv = os.path.join(data_dir, "globeado_ejemplo.csv")
ruta_globeado_excel = os.path.join(data_dir, "globeado_ejemplo.xlsx")

df_globeado.to_csv(ruta_globeado_csv, index=False, encoding="utf-8")
df_globeado.to_excel(ruta_globeado_excel, index=False, engine="openpyxl")

print(f"\n✓ Datos de globeado guardados:")
print(f"  - CSV: {ruta_globeado_csv}")
print(f"  - Excel: {ruta_globeado_excel}")

# ============================================================================
# RESUMEN
# ============================================================================
print("\n" + "=" * 60)
print("RESUMEN")
print("=" * 60)
print("""
✓ pandas: librería para datos tabulares (Excel en Python)
✓ DataFrame: tabla de datos (filas y columnas)
✓ Leer: pd.read_csv(), pd.read_excel()
✓ Guardar: df.to_csv(), df.to_excel()
✓ Explorar: df.head(), df.info(), df.describe()
✓ Filtrar: df[df["columna"] > valor]
✓ Agrupar: df.groupby("columna").sum()
✓ Iterar: for index, fila in df.iterrows()
✓ Validar: verificar columnas, tipos, valores nulos/negativos

Datos de ejemplo creados en: {data_dir}
""")

print("\nAhora ejecuta: python ejemplos/03_manipular_pdf.py")
