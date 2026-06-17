package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import io.github.Main.SpaceNavigation;

public class PantallaPausa implements Screen {

    private SpaceNavigation game;
    private PantallaJuego pantallaJuego;
    private OrthographicCamera camera;

    public PantallaPausa(SpaceNavigation game, PantallaJuego pantallaJuego) {
        this.game = game;
        this.pantallaJuego = pantallaJuego;
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
    }

    @Override
    public void render(float delta) {
        // 1. Limpiamos la pantalla con un fondo oscuro
        Gdx.gl.glClearColor(0, 0, 0.1f, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // 2. Opcional: Dibujamos el juego de fondo para que se vea pausado atrás
        pantallaJuego.dibujarPantalla();
        
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        // 3. Dibujamos el texto de la pausa
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "--- JUEGO EN PAUSA ---", 450, 450);
        game.getFont().draw(game.getBatch(), "Presiona [P] o [ESC] para Continuar", 380, 380);
        game.getFont().draw(game.getBatch(), "Presiona [Q] para salir al Menú", 430, 310);
        game.getBatch().end();

        // 4. Captura de entradas para reanudar o salir
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(pantallaJuego); // Volvemos a la pantalla de juego guardada
            dispose();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            game.setScreen(new PantallaMenu(game)); // Volvemos al menú principal
            dispose();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) { camera.setToOrtho(false, width, height); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}