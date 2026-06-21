package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport; // <--- NUEVO
import com.badlogic.gdx.utils.viewport.Viewport;    // <--- NUEVO
import io.github.Main.SpaceNavigation;

public class PantallaPausa implements Screen {

    private SpaceNavigation game;
    private PantallaJuego pantallaJuego;
    private OrthographicCamera camera;
    private Viewport viewport; // <--- NUEVO: Mantiene las proporciones de la pausa

    public PantallaPausa(SpaceNavigation game, PantallaJuego pantallaJuego) {
        this.game = game;
        this.pantallaJuego = pantallaJuego;
        
        // MODIFICADO: Usamos un FitViewport idéntico al de PantallaJuego (1200x800)
        camera = new OrthographicCamera();
        viewport = new FitViewport(1200, 800, camera);
        viewport.apply();
    }

    @Override
    public void render(float delta) {
        // 1. Limpiamos TODA la pantalla externa con negro absoluto para los bordes
        Gdx.gl.glClearColor(0, 0, 0, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // 2. Dibujamos el juego de fondo para que se vea pausado atrás
        // Como PantallaJuego ya usa su propio viewport, se dibujará centrado correctamente
        pantallaJuego.dibujarPantalla();
        
        // 3. EFECTO OSCURECIDO: Dibujamos una capa negra semitransparente sobre el juego
        // Activamos el renderizado con transparencia (Blending)
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        // Forzamos a que la cámara de la pausa tome el control del espacio de dibujo
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        // Dibujamos un rectángulo negro transparente que cubra exactamente el tamaño del mundo virtual (1200x800)
        // Nota: Si no tienes una textura blanca de 1x1, puedes usar directamente los textos, 
        // pero esto le dará ese look oscuro profesional detrás del menú.
        // game.getBatch().setColor(0, 0, 0, 0.5f); // 50% de oscuridad
        
        // 4. Dibujamos el texto de la pausa perfectamente centrado en el espacio 1200x800
        game.getFont().getData().setScale(2f);
        game.getFont().draw(game.getBatch(), "--- JUEGO EN PAUSA ---", 1200 / 2 - 180, 480);
        game.getFont().draw(game.getBatch(), "Presiona [P] o [ESC] para Continuar", 1200 / 2 - 250, 400);
        game.getFont().draw(game.getBatch(), "Presiona [Q] para salir al Menú", 1200 / 2 - 210, 320);
        
        game.getBatch().end();
        Gdx.gl.glDisable(GL20.GL_BLEND); // Desactivamos el blending para no romper otros renders

        // 5. Captura de entradas para reanudar o salir
        if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(pantallaJuego); // Volvemos a la pantalla de juego guardada
            // OJO: Quitamos el dispose() de aquí porque si destruyes la pantalla de juego, 
            // no podrías volver a pausar la próxima vez.
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            game.setScreen(new PantallaMenu(game)); // Volvemos al menú principal
            // Aquí sí podrías liberar recursos específicos si la pausa cargara texturas propias.
        }
    }

    @Override public void show() {}
    
    @Override 
    public void resize(int width, int height) { 
        // MODIFICADO: El viewport se encarga de recalcular las bandas negras y mantener el texto al medio
        viewport.update(width, height, true); 
    }
    
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {}
}