package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport; 
import com.badlogic.gdx.utils.viewport.Viewport;    
import io.github.Main.SpaceNavigation;

public class PantallaPausa implements Screen {

    private SpaceNavigation game;
    private PantallaJuego pantallaJuego;
    private OrthographicCamera camera;
    private Viewport viewport; 

    public PantallaPausa(SpaceNavigation game, PantallaJuego pantallaJuego) {
        this.game = game;
        this.pantallaJuego = pantallaJuego;
        

        camera = new OrthographicCamera();
        viewport = new FitViewport(1200, 800, camera);
        viewport.apply();
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        pantallaJuego.dibujarPantalla();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        game.getFont().getData().setScale(2f);
        game.getFont().draw(game.getBatch(), "--- JUEGO EN PAUSA ---", 1200 / 2 - 180, 480);
        game.getFont().draw(game.getBatch(), "Presiona [P] o [ESC] para Continuar", 1200 / 2 - 250, 400);
        game.getFont().draw(game.getBatch(), "Presiona [Q] para salir al Menú", 1200 / 2 - 210, 320);
        
        game.getBatch().end();
        Gdx.gl.glDisable(GL20.GL_BLEND); 


        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(pantallaJuego); 
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            game.setScreen(new PantallaMenu(game)); 
        }
    }

    @Override public void show() {}
    
    @Override 
    public void resize(int width, int height) { 
        viewport.update(width, height, true); 
    }
    
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}