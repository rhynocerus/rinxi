# Informe de implementación: laboratorio web RINXI sobre puertos

**Fecha:** 6 de octubre de 2026  
**Proyecto:** RINXI · Rhynus Interactive Works  
**Público:** hispanohablantes que empiezan a estudiar redes  
**Estado:** publicado

## Resumen

Se creó un artículo interactivo en español para aprender a distinguir puertos abiertos, cerrados y filtrados. La página combina una explicación breve con una terminal educativa que acepta un conjunto pequeño de comandos y presenta resultados preparados para una máquina ficticia.

**Enlace público:** https://rinxi-laboratorio-puertos.rhynus.chatgpt.site

## Objetivo didáctico

Al terminar el ejercicio, la persona debería poder:

1. Explicar qué papel tiene un puerto en la comunicación con un servicio.
2. Distinguir los estados `open`, `closed` y `filtered` en una explicación introductoria.
3. Leer las columnas `PORT`, `STATE` y `SERVICE` de una salida tipo Nmap.
4. Identificar que, en el resultado de ejemplo, los puertos 22 y 80 aparecen abiertos, el 23 aparece cerrado y el servicio asociado al 22 es SSH.

## Contenido de la página

- Definición intuitiva de puerto y relación con servicios de red.
- Explicación introductoria de los estados abierto, cerrado y filtrado.
- Ejemplos comunes de SSH (22), HTTP (80) y HTTPS (443), aclarando que el número no confirma por sí solo qué programa está detrás.
- Instrucción guiada para probar `nmap -sV demo.local`.
- Laboratorio RINXI con botones para ejecutar ejemplos, solicitar ayuda, pedir una pista y limpiar la salida.
- Recordatorio de utilizar escaneos reales únicamente sobre sistemas propios o con autorización expresa.
- Galería con dos ilustraciones originales para acompañar el artículo y la publicación en redes.

## Funcionamiento de la terminal

La terminal está implementada en JavaScript dentro de la página. Reconoce únicamente órdenes previstas para el ejercicio, entre ellas:

- `help`
- `nmap -sV demo.local`
- `nmap -p 22,23,80 demo.local`
- `ping demo.local`
- `pista`
- `clear`

Para la consulta didáctica, la salida fija muestra:

| Puerto | Estado simulado | Servicio de ejemplo |
|---|---|---|
| 22/tcp | abierto | SSH / OpenSSH 9.2 |
| 23/tcp | cerrado | Telnet |
| 80/tcp | abierto | HTTP / nginx 1.24 |

El resultado es contenido predefinido para enseñar a leer una tabla. No depende de una conexión a una máquina real.

## Recursos visuales

Las imágenes están disponibles en la página y se pueden descargar en PNG para reutilizarlas en publicaciones:

1. **Red y puertos:** https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/assets/rinxi-post-red-puertos.png
2. **Puertos abiertos y cerrados:** https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/assets/rinxi-post-puertos-estados.png

Las ilustraciones emplean la identidad visual RINXI: fondo oscuro, acentos verde menta y referencias visuales a redes y puertos.

## Límites y seguridad

- La página no ejecuta Nmap, una shell ni comandos del sistema.
- No envía paquetes ni escanea la red local o Internet.
- `demo.local` y la salida mostrada pertenecen al escenario didáctico.
- El simulador no representa todas las opciones de Nmap ni todos los resultados posibles.
- Aunque el artículo explica `filtered`, el reto interactivo actual solo modela puertos abiertos y cerrados.
- No se deben interpretar los resultados simulados como observaciones de una red real.

## Relación con la app Android

La aplicación Android y este laboratorio web son superficies separadas que comparten identidad RINXI. El laboratorio web no está integrado dentro del APK. La versión Android conserva su consola local; la web presenta una actividad educativa autocontenida en el navegador.

## Validación realizada

- El documento HTML se analizó con el parser de Python sin errores de estructura.
- Se revisaron las rutas de la imagen de marca, el favicon y las dos ilustraciones de la galería.
- Se comprobó la sintaxis JavaScript con `node --check`.
- El empaquetado del Site se validó y la versión 3 se publicó correctamente.
- La salida de la terminal y el estado de finalización se inspeccionaron en el código.

No se realizó una prueba automatizada de navegador en distintos dispositivos. Conviene revisar visualmente el sitio en escritorio y móvil y solicitar a varias personas que completen el reto antes de ampliar el catálogo.

## Próximos pasos posibles

1. Recoger comentarios de quienes prueben la página: claridad del texto, comodidad en móvil y comprensión de los estados.
2. Corregir lo que resulte confuso antes de diseñar el siguiente ejercicio.
3. Reutilizar la estructura de artículo + terminal simulada para futuras prácticas pequeñas, cada una con comandos limitados y resultados ficticios claramente etiquetados.
4. Si se incorpora una terminal ejecutable en el futuro, diseñarla como un laboratorio aislado y tratarla como un proyecto técnico distinto de este simulador.

## Decisión de documentación

El artículo queda como publicación independiente para que pueda compartirse directamente. El README del repositorio presenta el laboratorio y enlaza este informe, manteniendo visible que la aplicación Android y la práctica web son componentes relacionados, pero distintos.

## Código y recursos en GitHub

El repositorio incluye una copia autocontenida del artículo y de la terminal interactiva en `docs/laboratorio-puertos/index.html`, junto con el símbolo RINXI, el favicon y versiones WebP optimizadas de las dos ilustraciones. El README enlaza la práctica publicada y muestra las imágenes directamente en GitHub.

GitHub presenta el archivo HTML como código en la vista del repositorio; la terminal ejecutable se abre desde el enlace público al laboratorio. También se puede descargar el repositorio y abrir el HTML localmente en un navegador. Los originales PNG de alta resolución continúan descargables desde el Site.
