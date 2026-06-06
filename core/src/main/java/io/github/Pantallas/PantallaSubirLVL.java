package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.Main.SpaceNavigation;
import io.github.Personaje.Jugador;
import io.github.Strat.*;

public class PantallaSubirLVL implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Jugador pjJugador;
	private PantallaJuego pantallaJuego;

	public PantallaSubirLVL(SpaceNavigation game , PantallaJuego pantallaJuego , Jugador pjJugador) {
		this.game = game;
		this.pantallaJuego = pantallaJuego;
		this.pjJugador = pjJugador;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
	}

	@Override
    public void render(float delta) {
        
        pantallaJuego.render(0f); 
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        
        game.getFont().getData().setScale(2f); 
        game.getFont().draw(game.getBatch(), "¡NIVEL AUMENTADO!", 400, 600);
        
        game.getFont().getData().setScale(1.5f);
        game.getFont().draw(game.getBatch(), "Elige una mejora para tu nave:", 350, 500);

        game.getFont().draw(game.getBatch(), "[1] Aumentar Daño (+2)", 400, 400);
        game.getFont().draw(game.getBatch(), "[2] Aumentar Vida Máxima (+2)", 400, 350);
        game.getFont().draw(game.getBatch(), "[3] Aumentar Velocidad (+0.25)", 400, 300);
        game.getFont().draw(game.getBatch(), "[4] Aumentar Vel. de Ataque (-0.05s)", 400, 250);
        if (pjJugador.getLvl() % 5 == 0) {
            game.getFont().draw(game.getBatch(), "[5] Mejorar Arma: ESCOPETA", 400, 200);
        }
        
        game.getBatch().end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            
            pjJugador.setDañoAtaque(pjJugador.getDañoAtaque() + 5);
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
	    
			// límite para que no baje de 0.1 segundos (para que no dispare infinito y rompa el juego)
			if (nuevaCadencia < 0.1f) {
				nuevaCadencia = 0.1f;
			}
	    
			pjJugador.setCadenciaAtaque(nuevaCadencia);
			volverAlJuego();
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
		    // Validamos que realmente sea un nivel múltiplo de 5 para evitar trampas
		    if (pjJugador.getLvl() % 5 == 0) {
		        pjJugador.setEstrategiaDisparo(new DisparoEscopeta());
		        volverAlJuego();
		    }
		}
    }

    // Método auxiliar para no repetir código
    private void volverAlJuego() {
        // Devolvemos la pantalla original (la que estaba en pausa), NO creamos una nueva
        game.setScreen(pantallaJuego); 
        dispose();
    }
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
   
}