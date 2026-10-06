# RINXI

RINXI es un proyecto de Rhynus Interactive Works para explorar herramientas y conceptos de sistema con una interfaz inspirada en la terminal.

## Aplicación Android

La aplicación Android incluye utilidades locales de diagnóstico y una consola educativa limitada. La consola no ofrece una shell del sistema. Consulta el código y las instrucciones del proyecto antes de usar sus funciones.

## Laboratorio web: puertos abiertos y cerrados

RINXI también presenta una nota educativa en español con una terminal interactiva simulada. El ejercicio enseña a leer estados de puerto y servicios en un escenario ficticio.

- **Probar el laboratorio:** [RINXI · Laboratorio de puertos](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site)
- **Informe de implementación y alcance:** [docs/INFORME-LABORATORIO-WEB-01.md](docs/INFORME-LABORATORIO-WEB-01.md)

Los comandos y resultados del laboratorio web están predefinidos: no ejecuta Nmap ni envía tráfico a una red. Para escaneos reales, utiliza Nmap únicamente sobre equipos propios o con autorización expresa.

## Desarrollo

Abre el proyecto en Android Studio y ejecuta **Build > Make Project** para compilar la aplicación Android. La web es independiente y se puede probar desde el enlace anterior.

## Idioma

La documentación del laboratorio está escrita en español para personas hispanohablantes.

## Colección de prácticas interactivas

[Explorar el índice de laboratorios RINXI](docs/practicas/README.md)

- **Práctica 01 · Puertos:** [probar](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site) · [código](docs/laboratorio-puertos/index.html)
- **Práctica 02 · DNS:** [probar](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/dns/) · [guía, imágenes y código](docs/practicas/02-dns/README.md)
- **Práctica 03 · Rutas:** [probar](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/rutas/) · [guía, imágenes y código](docs/practicas/03-rutas/README.md)
- **Práctica 04 · HTTP:** [probar](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/http/) · [guía, imágenes y código](docs/practicas/04-http/README.md)
- **Plantilla para nuevas prácticas:** [guion, terminal, seguridad y publicación](docs/practicas/PLANTILLA.md)

## Práctica interactiva: puertos de red

**[Abrir la práctica con terminal interactiva](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site)**

La terminal del ejercicio está disponible aquí como página web independiente:

- [Ver el código completo de la práctica](docs/laboratorio-puertos/index.html)
- [Consultar el informe didáctico y técnico](docs/INFORME-LABORATORIO-WEB-01.md)

La página funciona en el navegador al abrirla desde el enlace publicado. El HTML del repositorio permite revisar y reutilizar el ejercicio; GitHub muestra ese archivo como código, no lo ejecuta dentro del README. Para probarlo en local, descarga el repositorio y abre `docs/laboratorio-puertos/index.html` en un navegador.

### Imágenes RINXI

![Ilustración RINXI: red de dispositivos y puertos](docs/laboratorio-puertos/assets/rinxi-post-red-puertos.webp)

![Ilustración RINXI: puertos abiertos y cerrados](docs/laboratorio-puertos/assets/rinxi-post-puertos-estados.webp)

Las imágenes WebP optimizadas están guardadas en este repositorio. Los originales PNG de alta resolución se pueden [abrir y descargar desde el laboratorio](https://rinxi-laboratorio-puertos.rhynus.chatgpt.site#gallery-title).

> **Nota de seguridad:** la terminal es una simulación didáctica. No ejecuta Nmap ni realiza conexiones o escaneos reales.
