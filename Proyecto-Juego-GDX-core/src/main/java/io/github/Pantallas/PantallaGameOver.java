package io.github.Pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.Main.SpaceNavigation;

public class PantallaGameOver implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Texture txGameOver;
	private GlyphLayout layout;

	public PantallaGameOver(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);

		this.txGameOver = new Texture(Gdx.files.internal("game_over.png"));
		this.layout = new GlyphLayout();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();

		float anchoPantalla = camera.viewportWidth;
		float altoPantalla = camera.viewportHeight;

		float anchoImg = txGameOver.getWidth();
		float altoImg = txGameOver.getHeight();
		float xImg = (anchoPantalla - anchoImg) / 2;
		float yImg = (altoPantalla - altoImg) / 2 + 50;

		game.getBatch().draw(txGameOver, xImg, yImg, anchoImg, altoImg);

		String textoInstruccion = "Pincha en cualquier lado para reiniciar ...";
		layout.setText(game.getFont(), textoInstruccion);
		float xTexto = (anchoPantalla - layout.width) / 2;
		float yTexto = yImg - 50;

		game.getFont().draw(game.getBatch(), textoInstruccion, xTexto, yTexto);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game, 1, 10, false);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
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
		if (txGameOver != null) {
			txGameOver.dispose();
		}
	}
}