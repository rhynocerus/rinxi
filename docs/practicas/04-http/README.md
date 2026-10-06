# Práctica 04 · HTTP: solicitar una página e interpretar la respuesta

**RINXI · Rhynus Interactive Works** · Nivel inicial · Español

**[Probar la terminal interactiva](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/http/)**

## Objetivo

Seguir una solicitud `GET` sencilla y reconocer en su respuesta la línea de estado, las cabeceras y el contenido. Comparar una respuesta `200 OK` con una `404 Not Found`.

## Reto

1. Ejecuta `curl -i http://aula.rinxi.test/`.
2. Identifica el método, la ruta y la cabecera `Host` que envía el cliente.
3. En la respuesta, localiza el código de estado y `Content-Type`.
4. Lee el contenido HTML que aparece después de la línea vacía.
5. Solicita `/no-existe` y explica qué informa `404 Not Found`.

**Resultado preparado:** la portada responde `200 OK` con una representación `text/html`; la ruta `/no-existe` responde `404 Not Found`. En ambos casos se muestra una respuesta HTTP ficticia.

## Conceptos esenciales

- Una solicitud HTTP incluye un método (aquí `GET`), un destino y cabeceras.
- En HTTP/1.1, la línea de solicitud incluye método, destino y versión; `Host` identifica el servidor solicitado.
- Una respuesta HTTP/1.1 comienza con una línea de estado, seguida de cabeceras, una línea vacía y, si corresponde, el contenido.
- `200 OK` indica éxito. `404 Not Found` informa que el servidor no encontró el recurso pedido.
- `Content-Type` indica el tipo de representación que acompaña el mensaje; en el ejemplo es HTML.
- `curl -i` es el comando que practicaríamos en un sistema real para mostrar cabeceras y contenido. En esta página es texto reconocido por un simulador y no ejecuta curl.
- La práctica se enfoca en HTTP. Un sitio HTTPS añade una capa de seguridad TLS a la conexión; DNS y transporte son temas relacionados, pero no se simulan aquí.

## Comandos aceptados

| Comando | Qué muestra |
|---|---|
| `help` | Comandos disponibles |
| `curl -i http://aula.rinxi.test/` | Solicitud GET simulada y respuesta `200 OK` |
| `curl -i http://aula.rinxi.test/no-existe` | Solicitud GET simulada y respuesta `404 Not Found` |
| `pista` | Pista para identificar estado, cabeceras y contenido |
| `clear` | Limpia la salida |

## Imágenes

![Ilustración RINXI del intercambio de solicitud entre navegador y servidor web](assets/rinxi-http-intercambio.webp)

![Ilustración RINXI de una respuesta HTTP que contiene una página HTML](assets/rinxi-http-respuesta.webp)

Los originales PNG de alta resolución se pueden descargar desde la galería del [laboratorio](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/http/#gallery-title).

## Simulación y uso responsable

El terminal solo responde a los comandos indicados y muestra resultados fijos en JavaScript. No ejecuta `curl`, no resuelve nombres, no abre conexiones y no descarga páginas. El nombre termina en `.test`, reservado para pruebas según RFC 6761.

Referencias: [RFC 9112 · HTTP/1.1](https://www.rfc-editor.org/rfc/rfc9112.html), [RFC 9110 · HTTP Semantics](https://www.rfc-editor.org/rfc/rfc9110.html) y [RFC 6761 · nombres reservados](https://www.rfc-editor.org/rfc/rfc6761.html).

## Archivos

- `index.html`: artículo y terminal independiente.
- `assets/`: identidad RINXI y versiones WebP de las ilustraciones.
- `LINKEDIN.md`: borrador del anuncio.
