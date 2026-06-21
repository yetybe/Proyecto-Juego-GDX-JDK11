package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout; 
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport; 
import com.badlogic.gdx.utils.viewport.Viewport;    

import io.github.Main.SpaceNavigation;

public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Viewport viewport; 
	private Texture txFondoMenu; 
	private GlyphLayout layout; 

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		
		viewport = new StretchViewport(1200, 800, camera);
		viewport.apply();

		this.txFondoMenu = new Texture(Gdx.files.internal("fondo_menu.png"));
		this.layout = new GlyphLayout();
	}

	@Override
	public void render(float delta) {
	    ScreenUtils.clear(0, 0, 0, 1);
	    
	    float anchoPantalla = viewport.getWorldWidth();
	    float altoPantalla = viewport.getWorldHeight();

	    camera.update();
	    game.getBatch().setProjectionMatrix(camera.combined);

	    game.getBatch().begin();

	    game.getBatch().draw(txFondoMenu, 0, 0, anchoPantalla, altoPantalla);
	    

	    String titulo = "Tiny Room Roguelike";
	    layout.setText(game.getFont(), titulo);
	    float xTitulo = (anchoPantalla - layout.width) / 2;
	    float yTitulo = altoPantalla - 80; 

	    game.getFont().draw(game.getBatch(), titulo, xTitulo, yTitulo);
	    
	    game.getFont().draw(game.getBatch(), "Presiona [1] para Modo Normal", 100, 350);
	    game.getFont().draw(game.getBatch(), "Presiona [2] para Modo Desarrollador", 100, 290);
	    game.getFont().draw(game.getBatch(), "Presiona [F] para Pantalla Completa (ON/OFF)", 100, 230);
	    game.getFont().draw(game.getBatch(), "Presiona [ESC] para Salir del Juego", 100, 170);
	    
	    game.getBatch().end();

	    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
	        iniciarJuego(false); 
	    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
	        iniciarJuego(true);  
	    } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
	        boolean esPantallaCompleta = Gdx.graphics.isFullscreen();
	        if (esPantallaCompleta) {
	            Gdx.graphics.setWindowedMode(1200, 800);
	        } else {
	            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
	        } 
	    } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	        Gdx.app.exit();
	    }
	}

	private void iniciarJuego(boolean isDeveloper) {
	    Screen ss = new PantallaJuego(game, 1, 10, isDeveloper); 
	    game.setScreen(ss);
	    dispose();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void hide() {}

	@Override
	public void dispose() {
		if (txFondoMenu != null) {
			txFondoMenu.dispose(); 
		}
	}		
}