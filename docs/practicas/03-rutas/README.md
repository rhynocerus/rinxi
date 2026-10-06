# Práctica 03 · Rutas: leer una dirección IP y una ruta de red

**RINXI · Rhynus Interactive Works** · Nivel inicial · Español

**[Probar la terminal interactiva](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/rutas/)**

## Objetivo

Reconocer la dirección IPv4 y su prefijo CIDR, leer los destinos de una tabla de rutas e identificar la ruta que el equipo elegiría para llegar a una IP.

## Reto

1. Ejecuta `ip addr` e identifica la dirección local y su prefijo.
2. Ejecuta `ip route` y compara la ruta específica con la ruta por defecto.
3. Consulta `ip route get 10.20.4.25`. ¿Qué ruta, puerta de enlace e interfaz se seleccionan?
4. Consulta `ip route get 203.0.113.10`. ¿Se usa una ruta específica o la ruta por defecto?

**Resultado preparado:** `10.20.4.25` coincide con `10.20.0.0/16`, que gana frente a `0.0.0.0/0`, y se envía vía `192.168.8.1` por `eth0`. Para `203.0.113.10` se utiliza la ruta por defecto.

## Conceptos esenciales

- En `192.168.8.42/24`, `192.168.8.42` es la dirección de la interfaz y `/24` es la longitud del prefijo de red. La red correspondiente es `192.168.8.0/24`.
- Una ruta asocia una red de destino con una interfaz directa o un siguiente salto, como una puerta de enlace.
- `0.0.0.0/0` es la ruta por defecto IPv4: coincide con cualquier destino que no tenga una ruta más específica.
- Si varias rutas coinciden, el reenvío IP suele preferir la coincidencia de prefijo más largo (la más específica).
- `via` muestra el siguiente salto; `dev` indica la interfaz de salida.

## Comandos aceptados

| Comando | Qué muestra |
|---|---|
| `help` | Comandos disponibles |
| `ip addr` | Interfaz `eth0` y dirección ficticia `192.168.8.42/24` |
| `ip route` | Tres rutas preparadas |
| `ip route get 10.20.4.25` | Selección de la ruta `10.20.0.0/16` |
| `ip route get 203.0.113.10` | Selección de la ruta por defecto |
| `pista` | Pista sobre prefijos, siguiente salto e interfaz |
| `clear` | Limpia la salida |

## Imágenes

![Ilustración RINXI de una dirección separada en prefijo de red y parte de equipo](assets/rinxi-rutas-ipv4.webp)

![Ilustración RINXI sobre rutas, puerta de enlace y selección del siguiente salto](assets/rinxi-rutas-tabla.webp)

Los originales PNG de alta resolución se pueden descargar desde la galería del [laboratorio](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/rutas/#gallery-title).

## Simulación y uso responsable

El terminal acepta únicamente los comandos enumerados y presenta salidas preparadas en JavaScript. No ejecuta `ip`, no inspecciona el dispositivo y no envía paquetes a ninguna red. Las direcciones pertenecen a bloques de ejemplo privados o reservados para documentación.

Referencias: [RFC 4632 · CIDR y reenvío basado en prefijos](https://www.rfc-editor.org/rfc/rfc4632.html), [RFC 1918 · direcciones privadas](https://www.rfc-editor.org/rfc/rfc1918.html) y [RFC 5737 · bloques de documentación](https://www.rfc-editor.org/rfc/rfc5737.html).

## Archivos

- `index.html`: artículo y terminal independiente.
- `assets/`: identidad RINXI y versiones WebP de los gráficos.
- `LINKEDIN.md`: borrador del anuncio.
