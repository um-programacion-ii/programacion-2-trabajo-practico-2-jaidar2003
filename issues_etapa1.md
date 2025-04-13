# 📋 Issues - Etapa 1: Diseño Base y Principios SOLID

---

## ✅ Issue 1: Crear clase `modelo`

**Descripción**  
Diseñar e implementar la clase `modelo` siguiendo el principio de responsabilidad única (SRP). La clase debe encargarse únicamente de representar un usuario del sistema.

**Criterios de aceptación**  
- [ ] Atributos: `nombre`, `id`, `email`  
- [ ] Métodos getters/setters adecuados  
- [ ] Constructor con parámetros  
- [ ] Sobrescribir `toString()` para visualización  
- [ ] Clase documentada  

**Etiquetas**: `enhancement`, `etapa1`, `SRP`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 2: Crear clase abstracta `RecursoDigital`

**Descripción**  
Crear una clase abstracta `RecursoDigital` que represente un recurso genérico. Debe implementar atributos y métodos comunes a todos los recursos y permitir su extensión sin modificar la clase base (OCP).

**Criterios de aceptación**  
- [ ] Atributos: `id`, `titulo`, `estado`, `categoria`  
- [ ] Métodos comunes: `getIdentificador()`, `getEstado()`, `actualizarEstado()`  
- [ ] Implementación base para `Prestable`  
- [ ] Código documentado y extensible  

**Etiquetas**: `enhancement`, `etapa1`, `OCP`, `SRP`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 3: Implementar subclases `Libro`, `Revista`, `Audiolibro`

**Descripción**  
Implementar subclases de `RecursoDigital`, asegurando que cumplan con el Principio de Sustitución de Liskov (LSP). Deben poder ser usadas donde se espera un `RecursoDigital`.

**Criterios de aceptación**  
- [ ] Clase `Libro` con atributos específicos (ej: `autor`, `isbn`)  
- [ ] Clase `Revista` con atributos específicos (ej: `numeroEdicion`)  
- [ ] Clase `Audiolibro` con atributos específicos (ej: `duracion`, `narrador`)  
- [ ] Métodos sobrescritos de forma coherente  
- [ ] Funcionan correctamente como `RecursoDigital`  

**Etiquetas**: `enhancement`, `etapa1`, `LSP`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 4: Crear clase `GestorUsuarios`

**Descripción**  
Crear una clase dedicada a la gestión de usuarios. Esta clase no debe encargarse de la lógica de recursos ni de notificaciones, cumpliendo con el SRP.

**Criterios de aceptación**  
- [ ] Método para registrar usuario  
- [ ] Método para buscar usuario por ID/email  
- [ ] Método para listar usuarios  
- [ ] Pruebas simples de su funcionamiento  

**Etiquetas**: `enhancement`, `etapa1`, `SRP`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 5: Crear clase `Consola` para interfaz de usuario

**Descripción**  
Crear una clase que maneje exclusivamente la interacción con el usuario a través de consola. Debe presentar menús, capturar entradas y mostrar salidas.

**Criterios de aceptación**  
- [ ] Menú principal con opciones  
- [ ] Métodos de visualización reutilizables  
- [ ] No debe contener lógica del negocio  

**Etiquetas**: `enhancement`, `etapa1`, `SRP`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 6: Crear interfaz `Prestable` y `Renovable`

**Descripción**  
Crear interfaces separadas que representen funcionalidades específicas según el principio de segregación de interfaces (ISP). Solo deben implementarlas las clases que realmente lo necesiten.

**Criterios de aceptación**  
- [ ] Interfaz `Prestable` con métodos: `estaDisponible()`, `prestar()`, `getFechaDevolucion()`  
- [ ] Interfaz `Renovable` con método `renovar()`  
- [ ] Aplicar solo en clases necesarias  
- [ ] Documentación de interfaces  

**Etiquetas**: `enhancement`, `etapa1`, `ISP`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 7: Crear interfaz `ServicioNotificaciones` + implementaciones

**Descripción**  
Crear la interfaz `ServicioNotificaciones` y las clases que la implementen: `ServicioNotificacionesEmail` y `ServicioNotificacionesSMS`. Inyectar estas dependencias donde se requiera notificación, siguiendo el DIP.

**Criterios de aceptación**  
- [ ] Interfaz `ServicioNotificaciones` con método `enviarNotificacion(String mensaje)`  
- [ ] Implementación para Email y SMS  
- [ ] Uso de inyección de dependencias en clases que usen notificaciones  
- [ ] Prueba en consola del envío de notificación  

**Etiquetas**: `enhancement`, `etapa1`, `DIP`, `notificaciones`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 8: Crear interfaz `RecursoDigital`

**Descripción**  
Diseñar una interfaz `RecursoDigital` que defina los métodos comunes para todos los recursos digitales. Esta interfaz debe representar el contrato principal que usarán las clases concretas y facilitar el cumplimiento del Principio de Abierto/Cerrado (OCP).

**Criterios de aceptación**  
- [ ] Definir métodos: `getIdentificador()`, `getEstado()`, `actualizarEstado(EstadoRecurso)`  
- [ ] Debe ser implementada por `RecursoBase`  
- [ ] Interfaz documentada correctamente  

**Etiquetas**: `enhancement`, `etapa1`, `OCP`, `abstracción`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 9: Crear clase abstracta `RecursoBase`

**Descripción**  
Implementar la clase `RecursoBase` que extienda la interfaz `RecursoDigital` y sirva como clase base para los recursos como `Libro`, `Revista`, etc. Debe encapsular la lógica común compartida entre los tipos de recursos, cumpliendo SRP y OCP.

**Criterios de aceptación**  
- [ ] Implementa `RecursoDigital` y `Prestable`  
- [ ] Define comportamiento común (estado, identificación)  
- [ ] Puede ser extendida sin modificaciones  
- [ ] Código probado y documentado  

**Etiquetas**: `enhancement`, `etapa1`, `SRP`, `herencia`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 10: Implementar visualización de recursos en consola

**Descripción**  
Desarrollar métodos para mostrar en consola los detalles de cualquier recurso digital, utilizando polimorfismo y cumpliendo con LSP. Esta visualización debe integrarse con el menú general.

**Criterios de aceptación**  
- [ ] Visualización de recursos (`Libro`, `Revista`, `Audiolibro`) con sus datos específicos  
- [ ] Método en clase `Consola` reutilizable  
- [ ] Uso de `instanceof` o `getClass()` minimizado  
- [ ] Salida legible y ordenada  

**Etiquetas**: `enhancement`, `etapa1`, `LSP`, `consola`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 11: Implementar menú extensible de consola

**Descripción**  
Diseñar un menú principal en consola que permita seleccionar opciones según el tipo de operación (gestión de recursos, gestión de usuarios, préstamos, etc.), preparado para crecer sin modificaciones en las estructuras actuales (OCP).

**Criterios de aceptación**  
- [ ] Implementar menú con opciones numeradas  
- [ ] Delegar cada opción a métodos separados  
- [ ] Facilitar la adición de nuevas opciones sin modificar el switch principal  
- [ ] Documentación del flujo de ejecución  

**Etiquetas**: `enhancement`, `etapa1`, `OCP`, `interfaz-usuario`  
**Milestone**: Etapa 1: Diseño Base

---

## ✅ Issue 12: Integrar dependencias con inyección manual

**Descripción**  
Aplicar el principio de inversión de dependencias (DIP) mediante la inyección de dependencias manual. Las clases que necesiten notificaciones deben recibir una implementación de `ServicioNotificaciones`.

**Criterios de aceptación**  
- [ ] Crear constructor o método `setServicioNotificaciones(...)` en las clases consumidoras  
- [ ] Inyectar `ServicioNotificacionesEmail` o `ServicioNotificacionesSMS`  
- [ ] Eliminar instanciación directa dentro de la clase  
- [ ] Comprobar que el sistema notifica correctamente  

**Etiquetas**: `enhancement`, `etapa1`, `DIP`, `inyeccion`  
**Milestone**: Etapa 1: Diseño Base
