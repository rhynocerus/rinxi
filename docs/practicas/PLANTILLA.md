# Plantilla para nuevas prácticas RINXI

Usa esta guía para preparar una nueva nota con laboratorio interactivo. La serie mantiene una identidad común, pero cada entrega puede variar su recorrido, sus ejemplos, su reto y su lenguaje visual.

## Cómo mantener variedad

Conserva las piezas que hacen reconocible una práctica RINXI: explicación breve, objetivo, laboratorio seguro, reto resoluble, recursos visuales y materiales para compartir. Después elige una forma distinta de enseñar el tema:

- **Seguir un recorrido:** observar cómo una petición o dato avanza por varias etapas.
- **Leer una salida:** encontrar campos importantes en una respuesta o registro.
- **Comparar casos:** contrastar dos resultados parecidos y explicar la diferencia.
- **Resolver un diagnóstico:** probar una hipótesis a partir de pistas.
- **Ordenar pasos:** reconstruir el flujo correcto de un proceso.
- **Tomar una decisión:** escoger entre opciones y justificar el resultado.

No es necesario usar todos los apartados de abajo ni repetir siempre el mismo orden. Elige entre tres y cinco secciones conceptuales que ayuden a cumplir el objetivo. Alterna el tipo de reto, el primer comando sugerido, la disposición de los ejemplos y el enfoque de las imágenes.

## Ficha de la práctica

Completa antes de redactar:

- **Número y título:** `Práctica NN · [tema y pregunta concreta]`
- **Público y nivel:** persona principiante; define cualquier palabra técnica.
- **Objetivo observable:** al terminar, la persona podrá…
- **Idea clave:** una frase que debe recordar.
- **Escenario ficticio:** nombres, datos y resultados que utilizará la práctica.
- **Reto:** una acción concreta y un resultado verificable.
- **Comandos permitidos:** lista cerrada de entradas aceptadas.
- **Solución razonada:** explica cómo se obtiene, paso a paso.
- **Límites:** qué simula la terminal y qué no hace.
- **Fuentes primarias:** normas, documentación oficial o manuales pertinentes.
- **Idea visual 1 y 2:** dos conceptos distintos que ayuden a entender el tema.
- **Variación de esta entrega:** qué cambia respecto a las prácticas anteriores.

## Guion de la nota educativa

Copia solo las secciones que sirvan para el tema y adapta sus títulos:

```markdown
# Práctica NN · [Título]

**RINXI · Rhynus Interactive Works** · Nivel inicial · Español

**[Probar la terminal interactiva](URL_DEL_LABORATORIO)**

## Objetivo

[Explica con un verbo qué aprenderá o sabrá hacer la persona.]

## Antes de empezar

[Introduce la situación y una analogía concreta. Define los términos imprescindibles.]

## [Concepto esencial 1]

[Explicación breve, ejemplo trabajado y cómo leerlo.]

## [Concepto esencial 2]

[Incluye una tabla o pasos solo si hacen más clara la idea.]

## Reto

[Describe qué debe averiguar, comparar, clasificar o decidir. No reveles la respuesta antes de que se pueda probar.]

## Cómo probarlo

[Comando o acción inicial, qué salida mirar y qué comandos alternativos hay.]

## Solución razonada

[Respuesta esperada y explicación breve de cada pista relevante. Puede estar en la guía del repositorio aunque no se muestre antes del reto en la página.]

## Simulación y límites

[Indica qué entradas se aceptan, si los resultados son fijos y confirma explícitamente que no se ejecutan comandos ni se contacta con sistemas reales.]

## Fuentes

[Enlaces a normas y documentación primaria; indica qué afirmación respalda cada fuente.]

## Recursos

[Enlaces a imágenes descargables, terminal independiente y materiales del repositorio.]
```

### Sugerencias de presentación

- Abre con la pregunta o situación que despierta curiosidad, no con un glosario largo.
- Alterna párrafos, pasos, tablas pequeñas y ejemplos de terminal según el contenido.
- Explica cada campo de la salida con palabras sencillas y después presenta el nombre técnico.
- Deja clara la relación entre el reto y el objetivo: resolverlo debe demostrar lo aprendido.
- Incluye advertencias de uso real solo cuando sean pertinentes; nunca sugieras probar sistemas ajenos.

## Diseño del laboratorio interactivo

El terminal web es una simulación. Antes de programar, define una tabla de entradas permitidas y resultados preparados. No ejecutes la cadena escrita por el visitante mediante shell, `eval`, `exec` ni llamadas de red.

| Entrada permitida | Salida preparada | Aprendizaje o avance del reto |
|---|---|---|
| `help` | Lista exacta de entradas | Descubrir las opciones |
| `[comando de ejemplo A]` | `[salida fija A]` | `[qué observa]` |
| `[comando de ejemplo B]` | `[salida fija B]` | `[qué compara]` |
| `pista` | `[pista gradual, sin regalar la solución]` | Volver a mirar un dato |
| `clear` | Limpia la salida visible | Reiniciar la lectura |

Adapta los controles al ejercicio: botones de comando, selección, pasos guiados u otra interacción pequeña. Si incorporas variación, mantén visible el texto introducido y explica por qué una respuesta es correcta. El estado de progreso debe poder reiniciarse al volver a cargar la página.

### Límites técnicos y de seguridad

- Acepta solo comandos y parámetros de una lista cerrada; rechaza objetivos no previstos.
- Genera salidas con APIs de texto seguras, como `textContent`.
- No hagas `fetch`, consultas DNS, peticiones HTTP, conexiones TCP/UDP ni escaneos.
- No recojas datos personales y no afirmes que se ha inspeccionado el dispositivo.
- Etiqueta los datos como ficticios y distingue la práctica simulada de una herramienta real.
- Usa dominios o rangos reservados para ejemplos siempre que aplique; enlaza la norma que justifica la elección.
- Comprueba teclado, foco visible, etiquetas de controles, contraste y lectura en móvil.

## Gráficos

Genera dos imágenes con objetivos visuales diferentes, no dos variaciones de la misma composición:

1. **Imagen de contexto:** relación entre actores, dispositivos o etapas del proceso.
2. **Imagen de detalle:** metáfora o estructura que haga visible el concepto difícil del reto.

Mantén la paleta RINXI (carbón, verde menta y blanco), estilo tecnológico limpio, composición apaisada y sin texto generado dentro de la imagen. En la página incluye texto alternativo descriptivo y enlaces para descargar PNG; el repositorio puede guardar WebP optimizado.

## Estructura recomendada en GitHub

```text
docs/practicas/NN-tema/
├── README.md       # guía, objetivo, solución y límites
├── index.html      # nota y laboratorio autónomo
├── LINKEDIN.md     # borrador con párrafos y viñetas
└── assets/
    ├── favicon.svg
    ├── rinxi-symbol.png
    ├── rinxi-tema-01.webp
    └── rinxi-tema-02.webp
```

Añade la práctica a `docs/practicas/README.md` y a la lista de colección del `README.md` raíz. Integra enlaces desde la página anterior y desde la nueva práctica para que la colección se pueda recorrer en ambos sentidos.

## Plantilla para LinkedIn

Mantén líneas en blanco entre párrafos para que LinkedIn no lo convierta en un bloque. Ajusta la cantidad de viñetas al ejercicio y no uses negritas Unicode como única señal de jerarquía.

```text
[Pregunta breve que despierte curiosidad]

[Dos frases: qué enseña esta práctica y qué puede probar la persona.]

En el laboratorio puedes:

• [acción o concepto 1]
• [acción o concepto 2]
• [reto o comparación]

Prueba la práctica:
URL_PUBLICA

[Explica con claridad que la terminal es simulada y qué no hace.]

RINXI · Rhynus Interactive Works
Aprender haciendo, con laboratorios pequeños y seguros.

[Invitación breve a probarla o compartir un descubrimiento]

#Tema #Redes #AprenderHaciendo #RINXI
```

## Lista de revisión antes de cerrar la práctica

- [ ] El objetivo se puede demostrar con el reto.
- [ ] La explicación diferencia conceptos que suelen confundirse.
- [ ] La solución se puede reproducir a partir de las salidas de la terminal.
- [ ] La lista de comandos está cerrada y los destinos inesperados se rechazan.
- [ ] La página declara que la simulación no ejecuta comandos ni genera tráfico.
- [ ] Las fuentes respaldan las explicaciones técnicas y abren correctamente.
- [ ] Los dos gráficos son distintos, tienen texto alternativo y están enlazados para descargar.
- [ ] La página y sus controles funcionan con teclado y en pantallas estrechas.
- [ ] El README de la práctica, el índice general y el README raíz apuntan al lugar correcto.
- [ ] El post de LinkedIn conserva saltos de línea y una llamada clara a probar el ejercicio.
- [ ] Se ejecuta la prueba del JavaScript, se revisan enlaces/archivos y `git diff --check` pasa.

## Flujo de trabajo

Una práctica por rama: preparar contenido y recursos, probar, hacer commit, subir una rama, abrir PR, integrar y cerrar el frente antes de empezar el siguiente.
