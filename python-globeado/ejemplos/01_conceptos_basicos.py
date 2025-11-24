"""
Ejemplo 1: Conceptos Básicos de Python
======================================

Este script enseña los fundamentos de Python para desarrolladores Java/TypeScript.

Temas cubiertos:
- Variables y tipos de datos
- Estructuras de datos (listas, diccionarios)
- Control de flujo (if, for, while)
- Funciones
- Clases básicas

Ejecutar: python ejemplos/01_conceptos_basicos.py
"""

print("=" * 60)
print("CONCEPTOS BÁSICOS DE PYTHON")
print("=" * 60)

# ============================================================================
# 1. VARIABLES Y TIPOS DE DATOS
# ============================================================================
print("\n1. VARIABLES Y TIPOS DE DATOS")
print("-" * 60)

# En Python no necesitas declarar el tipo (tipado dinámico)
nombre = "Antonio"              # str (string/cadena)
edad = 25                       # int (entero)
precio = 19.99                  # float (decimal)
activo = True                   # bool (booleano, con mayúscula)
nulo = None                     # None (equivale a null en Java/TS)

print(f"Nombre: {nombre} (tipo: {type(nombre).__name__})")
print(f"Edad: {edad} (tipo: {type(edad).__name__})")
print(f"Precio: {precio} (tipo: {type(precio).__name__})")
print(f"Activo: {activo} (tipo: {type(activo).__name__})")
print(f"Nulo: {nulo} (tipo: {type(nulo).__name__})")

# F-strings (Python 3.6+) para formatear strings
mensaje = f"Hola {nombre}, tienes {edad} años"
print(f"\nMensaje formateado: {mensaje}")

# ============================================================================
# 2. ESTRUCTURAS DE DATOS
# ============================================================================
print("\n2. ESTRUCTURAS DE DATOS")
print("-" * 60)

# LISTAS (como ArrayList en Java)
barcos = ["Crucero", "Velero", "Yate"]
print(f"\nLista de barcos: {barcos}")
print(f"Primer barco: {barcos[0]}")
print(f"Último barco: {barcos[-1]}")  # Índice negativo = desde el final

barcos.append("Lancha")  # Añadir elemento
print(f"Después de append: {barcos}")

barcos[1] = "Catamarán"  # Modificar elemento
print(f"Después de modificar: {barcos}")

# DICCIONARIOS (como HashMap en Java o objetos en JS)
reserva = {
    "codigo": "R001",
    "barco": "Crucero",
    "plazas": 4,
    "precio": 25.50
}
print(f"\nDiccionario reserva: {reserva}")
print(f"Código: {reserva['codigo']}")
print(f"Precio: {reserva.get('precio')} €")

# Añadir nueva clave
reserva["fecha"] = "2025-12-01"
print(f"Después de añadir fecha: {reserva}")

# TUPLAS (inmutables)
coordenadas = (100, 200)
print(f"\nTupla coordenadas: {coordenadas}")
x, y = coordenadas  # Desempaquetado
print(f"X: {x}, Y: {y}")

# ============================================================================
# 3. CONTROL DE FLUJO
# ============================================================================
print("\n3. CONTROL DE FLUJO")
print("-" * 60)

# IF-ELIF-ELSE (sin paréntesis, con indentación)
print("\nIF-ELIF-ELSE:")
edad_ejemplo = 16
if edad_ejemplo >= 18:
    print("  Mayor de edad")
elif edad_ejemplo >= 13:
    print("  Adolescente")
else:
    print("  Menor")

# FOR (iterar sobre lista)
print("\nFOR sobre lista:")
for barco in barcos:
    print(f"  - {barco}")

# FOR con enumerate (índice + valor)
print("\nFOR con enumerate:")
for i, barco in enumerate(barcos):
    print(f"  {i}: {barco}")

# FOR con range (rango numérico)
print("\nFOR con range:")
for i in range(5):  # 0, 1, 2, 3, 4
    print(f"  Número: {i}")

# WHILE
print("\nWHILE:")
contador = 0
while contador < 3:
    print(f"  Contador: {contador}")
    contador += 1

# ============================================================================
# 4. FUNCIONES
# ============================================================================
print("\n4. FUNCIONES")
print("-" * 60)


def calcular_total(plazas, precio_por_plaza):
    """
    Calcula el precio total de una reserva.
    
    Args:
        plazas (int): Número de plazas
        precio_por_plaza (float): Precio por plaza
    
    Returns:
        float: Precio total
    """
    return plazas * precio_por_plaza


# Llamar a la función
total = calcular_total(4, 25.50)
print(f"Total calculado: {total} €")


# Función con parámetros por defecto
def saludar(nombre, saludo="Hola"):
    """Saluda a una persona."""
    return f"{saludo}, {nombre}!"


print(f"Saludo 1: {saludar('Antonio')}")
print(f"Saludo 2: {saludar('Antonio', 'Buenos días')}")


# Función con múltiples retornos
def analizar_reserva(plazas, capacidad):
    """
    Analiza una reserva.
    
    Returns:
        tuple: (disponible, plazas_libres)
    """
    plazas_libres = capacidad - plazas
    disponible = plazas_libres >= 0
    return disponible, plazas_libres


disponible, libres = analizar_reserva(4, 10)
print(f"¿Disponible?: {disponible}, Plazas libres: {libres}")

# ============================================================================
# 5. CLASES
# ============================================================================
print("\n5. CLASES")
print("-" * 60)


class Barco:
    """Representa un barco."""
    
    def __init__(self, nombre, capacidad):
        """
        Constructor.
        
        Args:
            nombre (str): Nombre del barco
            capacidad (int): Capacidad máxima
        """
        self.nombre = nombre        # self = this en Java
        self.capacidad = capacidad
        self.plazas_ocupadas = 0
    
    def reservar(self, plazas):
        """
        Reserva plazas en el barco.
        
        Args:
            plazas (int): Número de plazas a reservar
        
        Returns:
            bool: True si se pudo reservar
        """
        if self.plazas_ocupadas + plazas <= self.capacidad:
            self.plazas_ocupadas += plazas
            return True
        return False
    
    def plazas_libres(self):
        """Devuelve las plazas libres."""
        return self.capacidad - self.plazas_ocupadas
    
    def info(self):
        """Devuelve información del barco."""
        return f"{self.nombre} ({self.plazas_ocupadas}/{self.capacidad})"


# Crear instancia (no se usa 'new')
barco1 = Barco("Crucero", 50)
print(f"Barco creado: {barco1.info()}")

# Llamar métodos
exito = barco1.reservar(10)
print(f"Reserva de 10 plazas: {'Éxito' if exito else 'Fallo'}")
print(f"Estado: {barco1.info()}")
print(f"Plazas libres: {barco1.plazas_libres()}")

# ============================================================================
# 6. LIST COMPREHENSIONS (avanzado)
# ============================================================================
print("\n6. LIST COMPREHENSIONS")
print("-" * 60)

# Forma tradicional
cuadrados_tradicional = []
for i in range(5):
    cuadrados_tradicional.append(i * i)
print(f"Cuadrados (tradicional): {cuadrados_tradicional}")

# List comprehension (más compacto)
cuadrados = [i * i for i in range(5)]
print(f"Cuadrados (comprehension): {cuadrados}")

# Con filtro
pares = [i for i in range(10) if i % 2 == 0]
print(f"Números pares: {pares}")

# ============================================================================
# 7. MANEJO DE EXCEPCIONES
# ============================================================================
print("\n7. MANEJO DE EXCEPCIONES")
print("-" * 60)


def dividir(a, b):
    """Divide dos números."""
    try:
        resultado = a / b
        return resultado
    except ZeroDivisionError:
        print("  Error: No se puede dividir por cero")
        return None
    except TypeError:
        print("  Error: Los argumentos deben ser números")
        return None


print(f"10 / 2 = {dividir(10, 2)}")
print(f"10 / 0 = {dividir(10, 0)}")

# ============================================================================
# RESUMEN
# ============================================================================
print("\n" + "=" * 60)
print("RESUMEN")
print("=" * 60)
print("""
✓ Variables: tipado dinámico, sin declaración de tipo
✓ Listas: [1, 2, 3] - dinámicas, mutables
✓ Diccionarios: {"clave": "valor"} - como HashMap/objetos JS
✓ Control: if/elif/else, for, while (sin paréntesis, con indentación)
✓ Funciones: def nombre(args): ... - docstrings opcionales
✓ Clases: class Nombre: ... - __init__ es el constructor, self = this
✓ Excepciones: try/except/finally
✓ Convenciones: snake_case (variables/funciones), PascalCase (clases)
""")

print("Ahora ejecuta: python ejemplos/02_leer_csv_excel.py")
