# Práctica 02 · DNS: del nombre a la dirección IP

**RINXI · Rhynus Interactive Works** · Nivel inicial · Español

**[Probar la terminal interactiva](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/dns/)**

## Objetivo

Entender cómo una consulta DNS encuentra datos asociados a un dominio, reconocer qué devuelve un registro `A` y comparar el resultado con una consulta `AAAA`.

## Reto

Consulta `aula.rinxi.test`. Identifica la dirección IPv4 de la respuesta `A`, lee el TTL y comprueba si el ejemplo tiene registro `AAAA`.

**Resultado preparado:** `A → 192.0.2.42`; no se ha definido un registro AAAA. No es una consulta real.

## Conceptos esenciales

- El **resolver recursivo** recibe la consulta del equipo. Puede responder desde su caché o buscar los datos siguiendo referencias dentro de la jerarquía DNS.
- Un servidor **autoritativo** publica información de una zona.
- **A** relaciona un nombre con IPv4; **AAAA**, con IPv6.
- **TTL** indica durante cuántos segundos puede mantenerse una respuesta en caché.
- El recorrido de la página es una introducción simplificada; DNS real tiene más pasos y variaciones.

## Comandos aceptados

| Comando | Qué muestra |
|---|---|
| `help` | Comandos disponibles |
| `dig aula.rinxi.test A` | Respuesta A/IPv4 ficticia |
| `dig aula.rinxi.test AAAA` | Consulta sin respuesta AAAA |
| `nslookup aula.rinxi.test` | Nombre y dirección IPv4 ficticios |
| `pista` | Una pista |
| `clear` | Limpia la salida |

## Imágenes

![Ilustración RINXI del recorrido de una consulta DNS por servidores organizados en jerarquía](assets/rinxi-dns-resolucion.webp)

![Ilustración RINXI del intercambio DNS entre un equipo, un resolver y un servidor](assets/rinxi-dns-a-ip.webp)

Los originales PNG de alta resolución se descargan desde la galería del [laboratorio](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/dns/#gallery-title).

## Simulación y uso responsable

El terminal interpreta únicamente los comandos de la tabla y muestra resultados preparados. No ejecuta `dig`, `nslookup` o una shell; tampoco realiza consultas DNS ni envía paquetes.

El sufijo `.test` está reservado para pruebas. La IP `192.0.2.42` pertenece al bloque TEST-NET-1 reservado para documentación. Consulta [RFC 6761](https://www.rfc-editor.org/rfc/rfc6761.html) y [RFC 5737](https://www.rfc-editor.org/rfc/rfc5737.html). Para ampliar sobre DNS: [RFC 1034](https://www.rfc-editor.org/rfc/rfc1034.html) y [RFC 1035](https://www.rfc-editor.org/rfc/rfc1035.html).

## Archivos

- `index.html`: artículo y terminal independiente.
- `assets/`: identidad RINXI y versiones WebP de las ilustraciones.
- `LINKEDIN.md`: borrador del anuncio.
