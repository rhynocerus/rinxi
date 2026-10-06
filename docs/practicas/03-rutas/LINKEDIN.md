¿Cómo decide un equipo por dónde enviar un paquete cuando conoce la dirección IP de destino?

La respuesta está en su tabla de rutas. En la práctica 03 de RINXI explicamos, con un ejemplo sencillo, cómo leer una dirección IPv4 con prefijo CIDR y cómo encontrar el siguiente salto.

En el laboratorio puedes:

• identificar la dirección local y su red con `ip addr`
• interpretar una tabla con una ruta local, una ruta específica y una ruta por defecto
• comprobar por qué `10.20.0.0/16` gana frente a `0.0.0.0/0` para el destino del reto
• observar la puerta de enlace y la interfaz elegidas

Prueba la práctica:
https://rinxi-laboratorio-puertos.rhynus.chatgpt.site/rutas/

Todo ocurre en una terminal simulada: no se ejecuta `ip`, no se inspecciona tu equipo ni se envía tráfico a una red. Las direcciones y resultados están preparados para aprender.

RINXI · Rhynus Interactive Works
Aprender haciendo, con laboratorios pequeños y seguros.

Si la pruebas, cuéntame: ¿qué parte de una tabla de rutas te resultó más clara después del ejercicio?

#Redes #Linux #AprenderHaciendo #Ciberseguridad #RINXI
