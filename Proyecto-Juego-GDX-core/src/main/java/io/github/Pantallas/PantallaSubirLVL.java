package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.Main.SpaceNavigation;
import io.github.Personaje.Jugador;
import io.github.Strategy.*;

public class PantallaSubirLVL implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Jugador pjJugador;
	private PantallaJuego pantallaJuego;
	private GlyphLayout layout;

	public PantallaSubirLVL(SpaceNavigation game , PantallaJuego pantallaJuego , Jugador pjJugador) {
		this.game = game;
		this.pantallaJuego = pantallaJuego;
		this.pjJugador = pjJugador;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
		
		this.layout = new GlyphLayout();
	}

	@Override
    public void render(float delta) {	
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); 
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        pantallaJuego.dibujarPantalla();
        
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        float anchoPantalla = camera.viewportWidth;
        
        game.getFont().getData().setScale(2f);
        String textoLvlUp = "¡LVL UP!";
        layout.setText(game.getFont(), textoLvlUp);
        float xLvlUp = (anchoPantalla - layout.width) / 2;
        game.getFont().draw(game.getBatch(), textoLvlUp, xLvlUp, 600);
        
        game.getFont().getData().setScale(1.5f);
        String textoPoder = "Escoge tu poder:";
        layout.setText(game.getFont(), textoPoder);
        float xPoder = (anchoPantalla - layout.width) / 2;
        game.getFont().draw(game.getBatch(), textoPoder, xPoder, 500);

        game.getFont().getData().setScale(1.5f);
        game.getFont().draw(game.getBatch(), "[1] Aumentar Daño (+1)", 400, 400);
        game.getFont().draw(game.getBatch(), "[2] Aumentar Vida Máxima (+2)", 400, 350);
        game.getFont().draw(game.getBatch(), "[3] Aumentar Velocidad (+0.25)", 400, 300);
        game.getFont().draw(game.getBatch(), "[4] Aumentar Vel. de Ataque (-0.05s)", 400, 250);
        if (pjJugador.getLvl() % 5 == 0 && pjJugador.getArma() instanceof DisparoNormal) {
            game.getFont().draw(game.getBatch(), "[5] Hechizo Nuevo: Cast Multiple", 400, 200);
        }
        
        game.getBatch().end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            pjJugador.setDañoAtaque(pjJugador.getDañoAtaque() + 3);
            volverAlJuego();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
        	pjJugador.setVidaMax(pjJugador.getVidaMax() + 2);
            pjJugador.setVidaActual(pjJugador.getVidaMax()); 
            volverAlJuego();
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
            pjJugador.setVelocidadMax(pjJugador.getVelMax() + 0.5f);
            volverAlJuego();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
			float nuevaCadencia = pjJugador.getCadenciaAtaque() - 0.05f;
			if (nuevaCadencia < 0.1f) {
				nuevaCadencia = 0.1f;
			}
			pjJugador.setCadenciaAtaque(nuevaCadencia);
			volverAlJuego();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
		    if (pjJugador.getLvl() % 5 == 0 && pjJugador.getArma() instanceof DisparoNormal) {
		        pjJugador.setEstrategiaDisparo(new DisparoEscopeta());
		        volverAlJuego();
		    }
		}
    }

    private void volverAlJuego() {
        game.setScreen(pantallaJuego); 
        dispose();
    }
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}