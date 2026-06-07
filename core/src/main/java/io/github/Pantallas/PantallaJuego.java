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

import io.github.ConstructoresEnemigos.BuilderDistancia;
import io.github.ConstructoresEnemigos.BuilderEnemigo;
import io.github.ConstructoresEnemigos.BuilderMelee;
import io.github.Main.SpaceNavigation;
import io.github.Personaje.Bullet;
import io.github.Personaje.Enemigo;
import io.github.Personaje.Jugador;
import io.github.Template.*;

public class PantallaJuego implements Screen {
	
	//Recursos para el loop game
	private boolean isDeveloperMode;
	private SpaceNavigation game;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Music gameMusic;
	private int ronda;
	
	//Recursos Jugador
	private Jugador jugadorPersonaje;
	private  ArrayList<Bullet> balas = new ArrayList<>();
	private Texture txJugador;
	private Texture txBalaJugador;
	
	// Recursos para la horda
	private Texture txEnemigoMelee;
    private Sound sonidoDañoEnemigoMelee;
    
    private Texture txtEnemigoDistancia;
    private Texture txtBalaEnemiga;
    private ArrayList<Bullet> balasEnemigas = new ArrayList();
    
	private  ArrayList<BuilderEnemigo> listaConstructoresEnemigos = new ArrayList<>();
	private  ArrayList<Enemigo> hordaEnemigos = new ArrayList<>();

	// Temporizador de hordas
	private float tiempoParaSpawn = 0;
    private float intervaloSpawn = 10.0f;


	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, boolean isDeveloperMode) {

		this.game = game;
		this.ronda = ronda;
		this.isDeveloperMode = isDeveloperMode;
		
		//inicializar recursos jugador
		txJugador = new Texture(Gdx.files.internal("MainShip3.png"));
		txBalaJugador = new Texture(Gdx.files.internal("Rocket2.png"));

		jugadorPersonaje = new Jugador(
		        Gdx.graphics.getWidth() / 2 - 50,                     
		        30,                                                   
		        txJugador,      
		        txBalaJugador,  
		        Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),   
		        Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))
		);
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.5f);
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("piano-loops.wav"));
		
		//Guardamos los Sprite y Sonidos de los enemigos
		txEnemigoMelee = new Texture(Gdx.files.internal("MeleeEnemy.png"));       
		sonidoDañoEnemigoMelee =Gdx.audio.newSound(Gdx.files.internal("MeleeEnemy-DamageSound.mp3"));
		
		txtEnemigoDistancia = new Texture(Gdx.files.internal("EnemigoDistancia.png"));
		txtBalaEnemiga = new Texture(Gdx.files.internal("BulletEnemiga.png"));
		this.listaConstructoresEnemigos.add(new BuilderMelee(txEnemigoMelee, sonidoDañoEnemigoMelee ));
		this.listaConstructoresEnemigos.add(new BuilderDistancia(txtEnemigoDistancia, txtBalaEnemiga, Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))));
		gameMusic.setVolume(0.5f);
		gameMusic.play();
		
	    // cargar imagen de la nave, 64x64   
		jugadorPersonaje = new Jugador(
				Gdx.graphics.getWidth() / 2 - 50,                     // 1. int x
			    30,                                                   // 2. int y
			    new Texture(Gdx.files.internal("MainShip3.png")),     // 3. Texture tx
			    new Texture(Gdx.files.internal("Rocket2.png")),       // 4. Texture txBala
			    Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),   // 5. Sound sonidoHerido
			    Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")) // 6. Sound soundBala
		);
		generarSiguienteHorda();
	}    
	
	public void dibujaEncabezado() {
        game.getFont().getData().setScale(2f);        
        
        int altoPantalla = Gdx.graphics.getHeight();
        int anchoPantalla = Gdx.graphics.getWidth();
        
        int margenSuperiorY = altoPantalla - 20; 

        CharSequence strVida = "Vida: " + jugadorPersonaje.getVidaActual() + " | Ronda: " + ronda;
        game.getFont().draw(batch, strVida, 20, margenSuperiorY);
        
        CharSequence strExp = "Exp: " + jugadorPersonaje.getExp() + " / " + jugadorPersonaje.getLvlCap();
        game.getFont().draw(batch, strExp, (anchoPantalla / 2) - 80, margenSuperiorY);

        CharSequence strLvl = "Nivel: " + jugadorPersonaje.getLvl();
        game.getFont().draw(batch, strLvl, anchoPantalla - 150, margenSuperiorY);
        
    }
	
	@Override
    public void render(float delta) {

        // devmode
        if (isDeveloperMode) {
            // subir de nivel
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                jugadorPersonaje.forzarSubirNivel();
                game.setScreen(new PantallaSubirLVL(game, this, jugadorPersonaje));
            }
            // god mode
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
 	 
	// --- MÉTODOS HELPER PRIVADOS ---

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
            		game.setScreen(new PantallaSubirLVL(game, this, jugadorPersonaje));                	}
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(); 
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        dibujaEncabezado();
        jugadorPersonaje.draw(batch);
        
        for (Enemigo enemigo : hordaEnemigos) {
            enemigo.draw(batch);
        }
        for (Bullet bala : balas) {
            bala.draw(batch);
        }
        
        for (Bullet bala : balasEnemigas) {
            bala.draw(batch);
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
	
    /*
	private void generarEnemigoAleatorio() {
        Random r = new Random();
        int lado = r.nextInt(4); // 0: Arriba, 1: Abajo, 2: Izquierda, 3: Derecha
        
        int x = 0;
        int y = 0;
        int anchoPantalla = Gdx.graphics.getWidth();
        int altoPantalla = Gdx.graphics.getHeight();
        int margen = 50; // Distancia extra fuera de la pantalla
        
        switch(lado) {
            case 0: // Arriba
                x = r.nextInt(anchoPantalla);
                y = altoPantalla + margen;
                break;
            case 1: // Abajo
                x = r.nextInt(anchoPantalla);
                y = -margen;
                break;
            case 2: // Izquierda
                x = -margen;
                y = r.nextInt(altoPantalla);
                break;
            case 3: // Derecha
                x = anchoPantalla + margen;
                y = r.nextInt(altoPantalla);
                break;
        }
        
        int indiceAleatorio = r.nextInt(listaConstructoresEnemigos.size());
        BuilderEnemigo constructorElegido = listaConstructoresEnemigos.get(indiceAleatorio);
        
        Enemigo nuevoEnemigo = constructorElegido.setPosicion(x, y).build();       
        hordaEnemigos.add(nuevoEnemigo);

    }
	*/
    
    private void generarSiguienteHorda() {
        ronda++; 
        TemplateHorda hordaAct;

        if (ronda % 10 == 0) {
            hordaAct = new HordaJefe1(); 
        } else {
            Random r = new Random();
            if (r.nextBoolean()) {
                hordaAct = new Horda1();
            } else {
                hordaAct = new Horda2();
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
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
	}

	@Override
	public void resize(int width, int height) {
	    camera.setToOrtho(false, width, height);
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
		this.explosionSound.dispose();
		this.gameMusic.dispose();
		this.txJugador.dispose();     
	    this.txBalaJugador.dispose(); 
	}
   
}
