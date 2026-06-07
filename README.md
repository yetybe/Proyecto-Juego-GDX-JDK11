#Space Navigation# (WIP)
Un videojuego arcade de supervivencia espacial desarrollado en Java 11 con LibGDX. 
El jugador debe esquivar y destruir oleadas de enemigos para subir de nivel y mejorar sus estadísticas.

Antes de ejecutar!:
- Cambiar el Text File Encoding a UTF-8
- window -> preferences -> general -> workspace-> text file encoding -> other -> UTF-8

FAQ: si sale error por missing assets, hay que moverlos desde la carpeta de Proyecto-Juego-GDX-parent -> assets, a la carpeta Proyecto-Juego-GDX-lwjgl3 -> assets.

Ejecutar: 
- Click derecho en Proyecto-Juego-GDX-lwjgl3 
- Run As -> Java Application (si aparece una ventana llamada "select java aplication", dar click en ok)

Controles y Mecánicas

Movimiento: Teclas W, A, S, D. (arriba, izquierda, abajo, derecha respectivamente)

Disparar: es automatico, sigue a tu cursor.

Mejoras: Al subir de nivel, presiona las teclas numéricas (1 a 5) para escoger una mejora.

Dev Mode: en la pantalla de inicio apreta 2, esto desbloquea facilidades para probar el juego.

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
