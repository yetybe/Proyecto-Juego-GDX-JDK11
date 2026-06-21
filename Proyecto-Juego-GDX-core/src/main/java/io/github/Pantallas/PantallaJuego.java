package io.github.Pantallas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.ConstructoresEnemigos.BuilderDistancia;
import io.github.ConstructoresEnemigos.BuilderEnemigo;
import io.github.ConstructoresEnemigos.BuilderMelee;
import io.github.Main.SpaceNavigation;
import io.github.Personaje.Bullet;
import io.github.Personaje.Enemigo;
import io.github.Personaje.Jugador;
import io.github.Template.*;

public class PantallaJuego implements Screen {
	
	// Recursos para el loop game
	private boolean isDeveloperMode;
	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private Viewport viewport; 
	private SpriteBatch batch;
	private Sound explosionSound;
	// ELIMINADO: private Music gameMusic; ya no manejamos la música localmente aquí
	private int ronda;
	private Texture txFondoJuego;
	
	// Recursos Jugador
	private Jugador jugadorPersonaje;
	private ArrayList<Bullet> balas = new ArrayList<>();
	private Texture txJugador;
	private Texture txBalaJugador;
	
	// Recursos para la horda
	private Texture txEnemigoMelee;
	private Sound sonidoDañoEnemigoMelee;
    
	private Texture txtEnemigoDistancia;
	private Texture txtBalaEnemiga;
	private ArrayList<Bullet> balasEnemigas = new ArrayList<>();
    
	private ArrayList<BuilderEnemigo> listaConstructoresEnemigos = new ArrayList<>();
	private ArrayList<Enemigo> hordaEnemigos = new ArrayList<>();

	// Temporizador de hordas
	private float tiempoParaSpawn = 0;
	private float intervaloSpawn = 10.0f;

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, boolean isDeveloperMode) {
		this.game = game;
		this.ronda = ronda;
		this.isDeveloperMode = isDeveloperMode;
		
		// Inicializar dimensiones virtuales fijas
		camera = new OrthographicCamera();	
		viewport = new FitViewport(1200, 800, camera); 
		viewport.apply();
		
		batch = game.getBatch();
		
		// Inicializar recursos jugador utilizando las coordenadas del viewport virtual
		txJugador = new Texture(Gdx.files.internal("MainShip3.png"));
		txBalaJugador = new Texture(Gdx.files.internal("Rocket2.png"));

		jugadorPersonaje = new Jugador(
		        1200 / 2 - 50,                     
		        30,                                                   
		        txJugador,      
		        txBalaJugador,  
		        Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),   
		        Gdx.audio.newSound(Gdx.files.internal("pop-sound.ogg"))
		);
		
		// Inicializar assets locales (efectos de sonido y texturas)
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1, 0.5f);
		
		// MODIFICADO: Llamamos al método central para iniciar la música global desde cero
		game.iniciarMusicaJuego();
		
		// Guardamos los Sprite y Sonidos de los enemigos
		txEnemigoMelee = new Texture(Gdx.files.internal("MeleeEnemy.png"));       
		sonidoDañoEnemigoMelee = Gdx.audio.newSound(Gdx.files.internal("MeleeEnemy-DamageSound.mp3"));
		
		txtEnemigoDistancia = new Texture(Gdx.files.internal("EnemigoDistancia.png"));
		txtBalaEnemiga = new Texture(Gdx.files.internal("BulletEnemiga.png"));
		
		this.listaConstructoresEnemigos.add(new BuilderMelee(txEnemigoMelee, sonidoDañoEnemigoMelee));
		this.listaConstructoresEnemigos.add(new BuilderDistancia(txtEnemigoDistancia, txtBalaEnemiga, Gdx.audio.newSound(Gdx.files.internal("pop-sound.ogg"))));
		
		// Textura de fondo
		this.txFondoJuego = new Texture(Gdx.files.internal("fondo_juego.jpg"));
		
		generarSiguienteHorda();
	}    
	
	public void dibujaEncabezado() {
		game.getFont().getData().setScale(2f);        
        
		float altoPantalla = viewport.getWorldHeight();
		float anchoPantalla = viewport.getWorldWidth();
        
		float margenSuperiorY = altoPantalla - 20; 

		CharSequence strVida = "Vida: " + jugadorPersonaje.getVidaActual() + " | Ronda: " + ronda;
		game.getFont().draw(batch, strVida, 20, margenSuperiorY);
        
		CharSequence strExp = "Exp: " + jugadorPersonaje.getExp() + " / " + jugadorPersonaje.getLvlCap();
		game.getFont().draw(batch, strExp, (anchoPantalla / 2) - 80, margenSuperiorY);

		CharSequence strLvl = "Nivel: " + jugadorPersonaje.getLvl();
		game.getFont().draw(batch, strLvl, anchoPantalla - 150, margenSuperiorY);
	}
	
	@Override
	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.P) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.setScreen(new PantallaPausa(game, this));
			return; 
		}
		if (isDeveloperMode) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
				jugadorPersonaje.forzarSubirNivel();
				game.setScreen(new PantallaSubirLVL(game, this, jugadorPersonaje));
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
				jugadorPersonaje.toggleGodMode();
			}
		}
		if (delta == 0) return;
		actualizarMundo(delta);
		gestionarColisionesYLimpieza();
		verificarGameOver();
		dibujarPantalla(); 
	}
 	 
	private void actualizarMundo(float delta) {
		tiempoParaSpawn += delta;
		if (tiempoParaSpawn >= intervaloSpawn) {
			generarSiguienteHorda();
			tiempoParaSpawn = 0;
		}
        
		jugadorPersonaje.update(this);
        
		for (Bullet bala : balas) { bala.update(delta); }	
		for (Bullet bala : balasEnemigas) { bala.update(delta); }
		for (Enemigo enemigo : hordaEnemigos) { enemigo.update(this); }
	}

	private void gestionarColisionesYLimpieza() {
		balaColsionEnemigo(balas, hordaEnemigos);
		balaEnemigaColisionJugador();
        
		Iterator<Enemigo> iterEnemigos = hordaEnemigos.iterator();
		while (iterEnemigos.hasNext()) {
			Enemigo e = iterEnemigos.next();
			if (e.isMuerto()) {
				if (jugadorPersonaje.ganarXp(e.getDropXp())) {
					game.setScreen(new PantallaSubirLVL(game, this, jugadorPersonaje));
				}
				iterEnemigos.remove(); 
			}
		}
        
		Iterator<Bullet> iterBalas = balas.iterator();
		while(iterBalas.hasNext()){
			if(iterBalas.next().isDestroyed()) {
				iterBalas.remove();
			}
		}   
        
		Iterator<Bullet> iterEnemigasBullet = balasEnemigas.iterator();
		while(iterEnemigasBullet.hasNext()){
			if(iterEnemigasBullet.next().isDestroyed()) {
				iterEnemigasBullet.remove();
			}
		}  
	}

	public void dibujarPantalla() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update(); 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        
		batch.draw(txFondoJuego, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        
		dibujaEncabezado();
		jugadorPersonaje.draw(batch);
        
		for (Enemigo enemigo : hordaEnemigos) {
			enemigo.draw(batch);
		}
		for (Bullet bala : balas) {
			bala.draw(batch);
		}
		for (Bullet bala : balasEnemigas) {
			batch.draw(txtBalaEnemiga, bala.getArea().x, bala.getArea().y, bala.getArea().width, bala.getArea().height);
		}
        
		if (isDeveloperMode) {
			game.getFont().draw(batch, "1: +1 nivel // 2: godmode", 20, 30);
			if (jugadorPersonaje.isGodMode()) {
				game.getFont().draw(batch, "GOD MODE: ACTIVADO", 20, 55);
			}
		}
		batch.end();
	}

	private void verificarGameOver() {
		if (jugadorPersonaje.isMuerto()) {          
			game.setScreen(new PantallaGameOver(game));
			dispose(); 
		}
	}
	 
	private void generarSiguienteHorda() {
		ronda++; 
		TemplateHorda hordaAct;

		if (ronda % 10 == 0) {
			hordaAct = new HordaJefe1(); 
		} else {
			Random r = new Random();
			int seleccion = r.nextInt(4);
            
			switch (seleccion) {
				case 0: hordaAct = new Horda1(); break;
				case 1: hordaAct = new Horda2(); break;
				case 2: hordaAct = new Horda3(); break;
				case 3: hordaAct = new Horda4(); break;
				default: hordaAct = new Horda1(); break;
			}
		}

		hordaAct.armarHorda(this);
	}
    
	public void balaColsionEnemigo(ArrayList<Bullet> balas , ArrayList<Enemigo> enemigos  ){
		for (Bullet bala : balas) {
			for (Enemigo enemigo : hordaEnemigos) { 
				if (bala.getArea().overlaps(enemigo.getArea())) {  
					enemigo.recibirDaño(jugadorPersonaje.getDañoAtaque()); 
					explosionSound.play();
					bala.setDestroyed(true);
				}
			}
		}	
	}
	
	private void balaEnemigaColisionJugador() {
		for (Bullet bala : balasEnemigas) {
			if (bala.getArea().overlaps(jugadorPersonaje.getArea())) {
				jugadorPersonaje.recibirDaño(2); 	            
				bala.setDestroyed(true);
			}
		}
	}
	
	public void agregarEnemigo(Enemigo enemigoCreado) {
		this.hordaEnemigos.add(enemigoCreado);
	}
	
	public boolean agregarBala(Bullet bb) {
		return balas.add(bb);
	}
    
	public boolean agregarBalaEnemiga(Bullet bb) {
		return balasEnemigas.add(bb);
	}
    
	public OrthographicCamera getCamera() {
		return this.camera;
	}
    
	public Jugador getJugador() {
		return this.jugadorPersonaje;
	}
    
	public ArrayList<BuilderEnemigo> getConstructores() {
		return this.listaConstructoresEnemigos;
	}
	
	// --- CONTROL DETALLADO DEL FLUJO DE AUDIO ---

	@Override
	public void show() {
		// MODIFICADO: Al regresar de la pausa, si la música existe y está detenida, la reanudamos sin reiniciar
		Music musica = game.getMusicaPartida();
		if (musica != null && !musica.isPlaying()) {
			musica.play();
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {
		// MODIFICADO: Si la aplicación pierde foco por completo en el sistema operativo
		Music musica = game.getMusicaPartida();
		if (musica != null && musica.isPlaying()) {
			musica.pause();
		}
	}

	@Override
	public void resume() {
		Music musica = game.getMusicaPartida();
		if (musica != null && !musica.isPlaying()) {
			musica.play();
		}
	}

	@Override
	public void hide() {
		// MODIFICADO: Al cambiar a otra pantalla (como PantallaPausa), pausamos el hilo de audio global
		Music musica = game.getMusicaPartida();
		if (musica != null && musica.isPlaying()) {
			musica.pause();
		}
	}

	@Override
	public void dispose() {
		this.explosionSound.dispose();
		this.txJugador.dispose();     
		this.txBalaJugador.dispose();
		this.txEnemigoMelee.dispose();
		this.sonidoDañoEnemigoMelee.dispose();
		this.txtEnemigoDistancia.dispose();
		this.txtBalaEnemiga.dispose();
		txFondoJuego.dispose();
		// ELIMINADO: gameMusic.dispose(); ya no se limpia aquí pues le pertenece a la clase base global
	}
}