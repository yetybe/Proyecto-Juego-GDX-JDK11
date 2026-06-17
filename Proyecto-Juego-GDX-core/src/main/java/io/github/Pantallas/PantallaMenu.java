package io.github.Pantallas;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;

import io.github.Main.SpaceNavigation;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Texture txFondoMenu; // NUEVO

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
		// NUEVO: Carga la imagen de fondo (guárdala en tu carpeta assets)
		this.txFondoMenu = new Texture(Gdx.files.internal("fondo_menu.png"));
	}

	@Override
	public void render(float delta) {
	    ScreenUtils.clear(0, 0, 0.2f, 1);

	    camera.update();
	    game.getBatch().setProjectionMatrix(camera.combined);

	    game.getBatch().begin();
	 // NUEVO: Dibujar el fondo en la coordenada (0,0) estirado al tamaño de la pantalla (1200x800)
	    game.getBatch().draw(txFondoMenu, 0, 0, 1200, 800);
	    game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation !", 140, 450);
	    game.getFont().draw(game.getBatch(), "Presiona [1] para Modo Normal", 100, 350);
	    game.getFont().draw(game.getBatch(), "Presiona [2] para Modo Desarrollador", 100, 290);
	    game.getFont().draw(game.getBatch(), "Presiona [F] para Pantalla Completa (ON/OFF)", 100, 230); // <--- Texto añadido
	    game.getFont().draw(game.getBatch(), "Presiona [ESC] para Salir del Juego", 100, 170);
	    game.getBatch().end();

	    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
	        iniciarJuego(false); 
	    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
	        iniciarJuego(true);  
	    } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
	        // Ejecutamos el cambio de pantalla completa
	        boolean esPantallaCompleta = Gdx.graphics.isFullscreen();
	        if (esPantallaCompleta) {
	            Gdx.graphics.setWindowedMode(1200, 800);
	        } else {
	            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	        } }
	        else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	            Gdx.app.exit();
	    }
	}

	private void iniciarJuego(boolean isDeveloper) {
	    Screen ss = new PantallaJuego(game, 1, 10, isDeveloper); 
	    ss.resize(1200, 800);
	    game.setScreen(ss);
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

		}		
	} 