# API de Marcas y Modelos

Esta API permite gestionar marcas y modelos de productos. A continuación, se describen los endpoints disponibles, los objetos de solicitud y respuesta, y el manejo de excepciones.

---

## Endpoints de Marcas (`BrandController`)

### Obtener todas las marcas
- **Método:** `GET`
- **Ruta:** `/brands`
- **Descripción:** Devuelve una lista de todas las marcas disponibles.
- **Respuesta:**
    - Tipo: `List<BrandResponse>`
    - Estructura de `BrandResponse`:
      ```json
      {
          "id": 1,
          "nombre": "Marca A",
          "average_price": 150000.0
      }
      ```
    - Ejemplo de respuesta:
      ```json
      [
          {
              "id": 1,
              "nombre": "Marca A",
              "average_price": 150000.0
          },
          {
              "id": 2,
              "nombre": "Marca B",
              "average_price": 200000.0
          }
      ]
      ```

### Obtener modelos de una marca específica
- **Método:** `GET`
- **Ruta:** `/brands/{id}/models`
- **Descripción:** Devuelve una lista de modelos asociados a una marca específica. Se pueden aplicar filtros para obtener modelos con un precio mayor o menor que un valor dado.
- **Parámetros de la URL:**
    - `id`: ID de la marca (debe ser un número mayor que 0).
- **Parámetros de consulta (opcionales):**
    - `greater`: Filtra modelos con un precio mayor que el valor proporcionado.
    - `lower`: Filtra modelos con un precio menor que el valor proporcionado.
- **Respuesta:**
    - Tipo: `List<ModelResponse>`
    - Estructura de `ModelResponse`:
      ```json
      {
          "id": 1,
          "name": "Modelo X",
          "average_price": 120000.0
      }
      ```
    - Ejemplo de respuesta:
      ```json
      [
          {
              "id": 1,
              "name": "Modelo X",
              "average_price": 120000.0
          },
          {
              "id": 2,
              "name": "Modelo Y",
              "average_price": 180000.0
          }
      ]
      ```

### Crear una nueva marca
- **Método:** `POST`
- **Ruta:** `/brands`
- **Descripción:** Crea una nueva marca.
- **Cuerpo de la solicitud:**
    - Tipo: `NewBrandRequest`
    - Estructura de `NewBrandRequest`:
      ```json
      {
          "nombre": "Marca C"
      }
      ```
    - Validaciones:
        - `nombre`: No puede estar vacío y solo puede contener letras y espacios.
- **Respuesta:**
    - Tipo: `BrandResponse`
    - Ejemplo de respuesta:
      ```json
      {
          "id": 3,
          "nombre": "Marca C",
          "average_price": null
      }
      ```

### Crear un nuevo modelo para una marca específica
- **Método:** `POST`
- **Ruta:** `/brands/{id}/models`
- **Descripción:** Crea un nuevo modelo asociado a una marca específica.
- **Parámetros de la URL:**
    - `id`: ID de la marca (debe ser un número mayor que 0).
- **Cuerpo de la solicitud:**
    - Tipo: `NewModelRequest`
    - Estructura de `NewModelRequest`:
      ```json
      {
          "name": "Modelo Z",
          "average_price": 250000.0
      }
      ```
    - Validaciones:
        - `name`: No puede estar vacío y solo puede contener letras, números y algunos caracteres especiales (`+`, `-`, `/`, `*`).
        - `average_price`: No puede ser nulo y debe ser mayor a 100,000.
- **Respuesta:**
    - Tipo: `ModelResponse`
    - Ejemplo de respuesta:
      ```json
      {
          "id": 3,
          "name": "Modelo Z",
          "average_price": 250000.0
      }
      ```

---

## Endpoints de Modelos (`ModelController`)

### Actualizar un modelo existente
- **Método:** `PUT`
- **Ruta:** `/models/{id}`
- **Descripción:** Actualiza la información de un modelo existente.
- **Parámetros de la URL:**
    - `id`: ID del modelo (debe ser un número mayor que 0).
- **Cuerpo de la solicitud:**
    - Tipo: `UpdateModelRequest`
    - Estructura de `UpdateModelRequest`:
      ```json
      {
          "average_price": 300000.0
      }
      ```
    - Validaciones:
        - `average_price`: No puede ser nulo y debe ser mayor a 100,000.
- **Respuesta:**
    - Tipo: `ModelResponse`
    - Ejemplo de respuesta:
      ```json
      {
          "id": 3,
          "name": "Modelo Z",
          "average_price": 300000.0
      }
      ```

### Obtener todos los modelos
- **Método:** `GET`
- **Ruta:** `/models`
- **Descripción:** Devuelve una lista de todos los modelos disponibles. Se pueden aplicar filtros para obtener modelos con un precio mayor o menor que un valor dado.
- **Parámetros de consulta (opcionales):**
    - `greater`: Filtra modelos con un precio mayor que el valor proporcionado.
    - `lower`: Filtra modelos con un precio menor que el valor proporcionado.
- **Respuesta:**
    - Tipo: `List<ModelResponse>`
    - Ejemplo de respuesta:
      ```json
      [
          {
              "id": 1,
              "name": "Modelo X",
              "average_price": 120000.0
          },
          {
              "id": 2,
              "name": "Modelo Y",
              "average_price": 180000.0
          }
      ]
      ```
      
---

## Manejo de Excepciones

En caso de que ocurra una excepción, la API responderá con un objeto `GlobalExceptionResponse` que contiene un mensaje de error o un mapa de mensajes de error.

### Estructura de `GlobalExceptionResponse`:
```json
{
  "message": "Error específico"
}
```
### Estructura de `GlobalExceptionResponse` para multiples errores:
```json
{
  "messages": {
        "campo1": "Error en campo1",
        "campo2": "Error en campo2"
  }
}
```
