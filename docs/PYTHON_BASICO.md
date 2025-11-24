# Python Básico para Desarrolladores Java/TypeScript

## Introducción

Si vienes de **Java** o **TypeScript**, Python te parecerá familiar pero con diferencias clave.

---

## Comparativa Rápida

| Aspecto | Java | TypeScript | Python |
|---------|------|------------|--------|
| **Tipado** | Estático (compilado) | Estático opcional | Dinámico (con hints opcionales) |
| **Punto y coma** | Obligatorio | Opcional | No se usa |
| **Bloques** | `{ }` | `{ }` | Indentación (4 espacios) |
| **Variables** | `int x = 5;` | `let x = 5;` | `x = 5` |
| **Strings** | `"text"` o `'text'` | `"text"` o `'text'` o `` `text` `` | `"text"` o `'text'` (equivalentes) |
| **Arrays/Listas** | `String[] arr = new String[3];` | `const arr: string[] = [];` | `arr = []` o `arr = list()` |
| **Funciones** | `public int suma(int a, int b) { return a + b; }` | `function suma(a: number, b: number): number { return a + b; }` | `def suma(a, b): return a + b` |
| **Clases** | Sí (obligatorias para todo) | Sí (opcionales) | Sí (opcionales, todo puede ser función) |
| **null** | `null` | `null` / `undefined` | `None` |
| **Booleanos** | `true` / `false` | `true` / `false` | `True` / `False` |
| **Comentarios** | `//` y `/* */` | `//` y `/* */` | `#` y `""" """` |

---

## 1. Variables y Tipos de Datos

### Java
```java
String nombre = "Antonio";
int edad = 25;
double precio = 19.99;
boolean activo = true;
```

### TypeScript
```typescript
const nombre: string = "Antonio";
const edad: number = 25;
const precio: number = 19.99;
const activo: boolean = true;
```

### Python
```python
nombre = "Antonio"        # str (cadena de texto)
edad = 25                 # int (entero)
precio = 19.99            # float (decimal)
activo = True             # bool (booleano, con mayúscula)

# Python infiere el tipo automáticamente
# Puedes usar "type hints" opcionales (Python 3.5+)
nombre: str = "Antonio"
edad: int = 25
```

**Puntos clave**:
- **No hay declaración de tipo obligatoria** (dinámico)
- `True`/`False` con mayúscula (no `true`/`false`)
- `None` equivale a `null`/`undefined`

---

## 2. Estructuras de Datos

### Listas (como arrays dinámicos)

**Java**:
```java
List<String> barcos = new ArrayList<>();
barcos.add("Crucero");
barcos.add("Velero");
String primero = barcos.get(0);
```

**Python**:
```python
barcos = []                  # Lista vacía
barcos = ["Crucero", "Velero"]  # Lista con elementos
barcos.append("Yate")        # Añadir elemento
primero = barcos[0]          # Acceder por índice (0-based)
ultimo = barcos[-1]          # Índice negativo = desde el final
barcos[1] = "Lancha"         # Modificar elemento
```

### Diccionarios (como HashMap o JSON)

**Java**:
```java
Map<String, String> reserva = new HashMap<>();
reserva.put("codigo", "R001");
reserva.put("barco", "Crucero");
String codigo = reserva.get("codigo");
```

**Python**:
```python
reserva = {}                    # Diccionario vacío
reserva = {                     # Diccionario con valores
    "codigo": "R001",
    "barco": "Crucero",
    "plazas": 4
}
codigo = reserva["codigo"]      # Acceso por clave
codigo = reserva.get("codigo")  # Acceso seguro (devuelve None si no existe)
reserva["fecha"] = "2025-12-01" # Añadir/modificar clave
```

### Tuplas (inmutables)

```python
coordenadas = (10, 20)   # Tupla (no se puede modificar)
x, y = coordenadas       # Desempaquetado
```

---

## 3. Control de Flujo

### If-Else

**Java**:
```java
if (edad >= 18) {
    System.out.println("Mayor de edad");
} else if (edad >= 13) {
    System.out.println("Adolescente");
} else {
    System.out.println("Menor");
}
```

**Python** (sin paréntesis ni llaves, usa **indentación**):
```python
if edad >= 18:
    print("Mayor de edad")
elif edad >= 13:           # elif, no else if
    print("Adolescente")
else:
    print("Menor")
```

**⚠️ CRÍTICO**: La indentación define bloques. Usa **4 espacios** (no tabs).

### Bucles

**For (iterar lista)**:
```python
barcos = ["Crucero", "Velero", "Yate"]

for barco in barcos:
    print(barco)

# Con índice
for i, barco in enumerate(barcos):
    print(f"{i}: {barco}")   # f-string para interpolación
```

**For (rango numérico)**:
```python
for i in range(5):        # 0, 1, 2, 3, 4
    print(i)

for i in range(2, 10, 2): # 2, 4, 6, 8 (inicio, fin, paso)
    print(i)
```

**While**:
```python
contador = 0
while contador < 5:
    print(contador)
    contador += 1
```

---

## 4. Funciones

**Java**:
```java
public static int calcularTotal(int plazas, double precioPorPlaza) {
    return (int) (plazas * precioPorPlaza);
}

int total = calcularTotal(4, 25.5);
```

**Python**:
```python
def calcular_total(plazas, precio_por_plaza):
    """
    Calcula el precio total.
    
    Args:
        plazas (int): Número de plazas reservadas
        precio_por_plaza (float): Precio por plaza
    
    Returns:
        float: Precio total
    """
    return plazas * precio_por_plaza

total = calcular_total(4, 25.5)
```

**Puntos clave**:
- `def` para definir funciones
- Nombres en `snake_case` (no `camelCase`)
- Docstrings `"""..."""` para documentar (opcional pero buena práctica)
- Parámetros opcionales con valores por defecto:

```python
def saludar(nombre, saludo="Hola"):
    return f"{saludo}, {nombre}!"

saludar("Antonio")              # "Hola, Antonio!"
saludar("Antonio", "Buenos días")  # "Buenos días, Antonio!"
```

---

## 5. Clases y Objetos

**Java**:
```java
public class Barco {
    private String nombre;
    private int capacidad;
    
    public Barco(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    
    public String getNombre() {
        return nombre;
    }
}

Barco barco = new Barco("Crucero", 50);
```

**Python**:
```python
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
    
    def info(self):
        """Devuelve información del barco."""
        return f"{self.nombre} (capacidad: {self.capacidad})"

barco = Barco("Crucero", 50)   # No se usa 'new'
print(barco.nombre)             # Acceso directo (no hay getters obligatorios)
print(barco.info())
```

**Puntos clave**:
- `__init__` es el constructor
- `self` es obligatorio como primer parámetro (equivale a `this`)
- Por defecto, atributos son públicos (convención: `_atributo` para "privado")

---

## 6. Manejo de Archivos

### Leer archivo de texto

**Java**:
```java
BufferedReader reader = new BufferedReader(new FileReader("datos.txt"));
String linea;
while ((linea = reader.readLine()) != null) {
    System.out.println(linea);
}
reader.close();
```

**Python** (más simple con `with`):
```python
with open("datos.txt", "r", encoding="utf-8") as archivo:
    for linea in archivo:
        print(linea.strip())  # strip() elimina \n al final

# No necesitas cerrar explícitamente (with lo hace)
```

### Escribir archivo

```python
with open("salida.txt", "w", encoding="utf-8") as archivo:
    archivo.write("Hola mundo\n")
    archivo.write("Segunda línea\n")
```

---

## 7. Librerías Esenciales

Python tiene una filosofía de "baterías incluidas" con muchas librerías estándar:

### Librería Estándar (no necesitan instalación)

```python
import os           # Sistema operativo (rutas, archivos)
import sys          # Sistema (argumentos, salida)
import datetime     # Fechas y tiempos
import json         # Leer/escribir JSON
import csv          # Leer/escribir CSV
import re           # Expresiones regulares
import pathlib      # Manipulación de rutas moderna
```

### Librerías Externas (necesitan instalación con `pip`)

```python
import pandas as pd         # Procesamiento de datos (Excel, CSV)
import numpy as np          # Operaciones numéricas
import PyPDF2               # Manipulación de PDFs
import reportlab            # Creación de PDFs
import requests             # Peticiones HTTP
```

**Instalar librerías**:
```bash
pip install pandas
pip install PyPDF2
pip install reportlab
```

---

## 8. Imports y Módulos

### Importar todo el módulo

```python
import math
resultado = math.sqrt(16)   # 4.0
```

### Importar funciones específicas

```python
from math import sqrt, pi
resultado = sqrt(16)
print(pi)  # 3.14159...
```

### Alias

```python
import pandas as pd   # Convención común
df = pd.DataFrame()
```

---

## 9. Excepciones

**Java**:
```java
try {
    int resultado = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Error: " + e.getMessage());
} finally {
    System.out.println("Siempre se ejecuta");
}
```

**Python**:
```python
try:
    resultado = 10 / 0
except ZeroDivisionError as e:
    print(f"Error: {e}")
except Exception as e:          # Captura cualquier excepción
    print(f"Error inesperado: {e}")
finally:
    print("Siempre se ejecuta")
```

**Lanzar excepciones**:
```python
def dividir(a, b):
    if b == 0:
        raise ValueError("No se puede dividir por cero")
    return a / b
```

---

## 10. List Comprehensions (avanzado pero muy útil)

Crear listas de forma concisa.

**Java/TS (tradicional)**:
```java
List<Integer> cuadrados = new ArrayList<>();
for (int i = 0; i < 10; i++) {
    cuadrados.add(i * i);
}
```

**Python (list comprehension)**:
```python
cuadrados = [i * i for i in range(10)]
# [0, 1, 4, 9, 16, 25, 36, 49, 64, 81]

# Con filtro
pares = [i for i in range(10) if i % 2 == 0]
# [0, 2, 4, 6, 8]
```

---

## 11. F-strings (Interpolación de Strings)

**Java**:
```java
String mensaje = "Hola " + nombre + ", tienes " + edad + " años";
String mensaje = String.format("Hola %s, tienes %d años", nombre, edad);
```

**TypeScript**:
```typescript
const mensaje = `Hola ${nombre}, tienes ${edad} años`;
```

**Python (f-strings, Python 3.6+)**:
```python
mensaje = f"Hola {nombre}, tienes {edad} años"

# Con expresiones
precio = 25.5
mensaje = f"Total: {precio * 1.21:.2f} €"  # Total: 30.86 €
```

---

## 12. Convenios de Nomenclatura (PEP 8)

Python tiene un estándar de estilo llamado **PEP 8**.

| Elemento | Java | Python (PEP 8) |
|----------|------|----------------|
| Variables | `nombreCompleto` | `nombre_completo` |
| Funciones | `calcularTotal()` | `calcular_total()` |
| Clases | `BarcoEntity` | `BarcoEntity` (igual) |
| Constantes | `MAX_VALUE` | `MAX_VALUE` (igual) |
| Módulos/archivos | `BarcoService.java` | `barco_service.py` |

**Regla de oro**: `snake_case` para todo excepto clases (`PascalCase`).

---

## 13. Entornos Virtuales (Importante)

En Python, las dependencias se instalan **globalmente** por defecto. Esto causa conflictos entre proyectos.

**Solución**: Entornos virtuales (venv).

```bash
# Crear entorno virtual
python -m venv venv

# Activar (Linux/Mac)
source venv/bin/activate

# Activar (Windows)
venv\Scripts\activate

# Instalar dependencias
pip install pandas

# Guardar dependencias
pip freeze > requirements.txt

# Instalar desde requirements.txt
pip install -r requirements.txt

# Desactivar
deactivate
```

**Buena práctica**: Un entorno virtual por proyecto.

---

## 14. Ejemplo Completo: Leer CSV y Procesar

```python
import csv

def procesar_reservas(archivo_csv):
    """
    Lee un CSV de reservas y calcula el total de plazas.
    
    Args:
        archivo_csv (str): Ruta al archivo CSV
    
    Returns:
        int: Total de plazas reservadas
    """
    total_plazas = 0
    
    with open(archivo_csv, "r", encoding="utf-8") as archivo:
        lector = csv.DictReader(archivo)  # Lee CSV como diccionarios
        
        for fila in lector:
            plazas = int(fila["plazas"])
            total_plazas += plazas
            print(f"Reserva {fila['codigo']}: {plazas} plazas")
    
    return total_plazas

# Uso
total = procesar_reservas("reservas.csv")
print(f"Total de plazas: {total}")
```

**CSV de ejemplo** (`reservas.csv`):
```csv
codigo,barco,plazas
R001,Crucero,4
R002,Velero,2
R003,Yate,6
```

---

## 15. ¿Por Qué Python para Automatización?

| Ventaja | Explicación |
|---------|-------------|
| **Sintaxis simple** | Menos código para hacer lo mismo |
| **Librerías potentes** | pandas, PyPDF2, openpyxl, etc. |
| **Scripting rápido** | No necesitas compilar, ejecutas directamente |
| **Comunidad grande** | Muchos ejemplos y soluciones online |
| **Multiplataforma** | Funciona en Windows, Linux, Mac sin cambios |

---

## Próximos Pasos

1. ✅ Has visto las diferencias clave entre Java/TS y Python
2. 🎯 Lee `GLOBEADO_PUNTO_A_PUNTO.md` para aplicar esto al proyecto real
3. 💻 Practica con los ejemplos en `/python-globeado/ejemplos/`
