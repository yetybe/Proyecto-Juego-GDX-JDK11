package io.github.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx; // <--- NUEVA IMPORTACIÓN
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music; // <--- NUEVA IMPORTACIÓN
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Pantallas.PantallaMenu;

public class SpaceNavigation extends Game {
    private String nombreJuego = "a Dungeon Crawler but confined in a tiny room";
    private SpriteBatch batch;
    private BitmapFont font;
    private int highScore;    
    private Music musicaPartida; 
    // Instancia única estática
    private static SpaceNavigation instancia;


    public SpaceNavigation() {
        // Constructor
    }

    // Punto de acceso global 
    public static SpaceNavigation getInstancia() {
        if (instancia == null) {
            instancia = new SpaceNavigation();
        }
        return instancia;
    }


    public void iniciarMusicaJuego() {

        if (musicaPartida != null) {
            musicaPartida.dispose();
        }
        
        // Cargamos el archivo de sonido desde los assets internos
        musicaPartida = Gdx.audio.newMusic(Gdx.files.internal("game_music.mp3"));
        musicaPartida.setLooping(true); // Hace que la música vuelva a empezar en bucle
        musicaPartida.setVolume(0.5f);  // Volumen balanceado
        musicaPartida.play();
    }


    public Music getMusicaPartida() {
        return this.musicaPartida;
    }

    @Override
    public void create() {
        highScore = 0;
        batch = new SpriteBatch();
        font = new BitmapFont(); 
        font.getData().setScale(2f);
        
        Screen ss = new PantallaMenu(this);
        this.setScreen(ss);
    }

    @Override
    public void render() {
        super.render(); 
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        

        if (musicaPartida != null) {
            musicaPartida.dispose();
        }
        
        instancia = null; 
    }

    public SpriteBatch getBatch() { return batch; }
    public BitmapFont getFont() { return font; }
    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }
}