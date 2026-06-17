package io.github.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Pantallas.PantallaMenu;

public class SpaceNavigation extends Game {
    private String nombreJuego = "Space Navigation";
    private SpriteBatch batch;
    private BitmapFont font;
    private int highScore;    

    // Instancia única estática
    private static SpaceNavigation instancia;

    private SpaceNavigation() {
        // Constructor vacío
    }

    // Punto de acceso global 
    public static SpaceNavigation getInstancia() {
        if (instancia == null) {
            instancia = new SpaceNavigation();
        }
        return instancia;
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
        instancia = null; 
    }

    public SpriteBatch getBatch() { return batch; }
    public BitmapFont getFont() { return font; }
    public int getHighScore() { return highScore; }
    public void setHighScore(int highScore) { this.highScore = highScore; }
}