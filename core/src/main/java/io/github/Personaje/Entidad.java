package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.github.Pantallas.PantallaJuego;

public abstract class Entidad {
	
    protected float vidaMax;
    protected float vidaActual;
    protected float velocidadMax;
    protected float dañoAtaque;
    protected Sprite spr;
    protected boolean muerto;
    
    public Entidad(float vidaMax, float velocidadMax, int dañoAtaque, Sprite spr) {
        this.vidaMax = vidaMax;
        this.vidaActual = vidaMax; 
        this.velocidadMax = velocidadMax;
        this.dañoAtaque = dañoAtaque;
        this.spr = spr;
        this.muerto = false; 
    }
    
    public abstract void update(PantallaJuego juego);
    public void draw(SpriteBatch batch) {spr.draw(batch);}
    
    public void recibirDaño(float dañoRecibido) {
        if (muerto) return;

        vidaActual -= dañoRecibido;

        if (vidaActual <= 0) {
            vidaActual = 0;
            muerto = true;
        }
    }
    
    public Rectangle getArea() {return this.spr.getBoundingRectangle();}
    public float getDañoAtaque() {return this.dañoAtaque;}
    public boolean isMuerto() { return this.muerto; }
    public Sprite getSprite() { return this.spr; }
}
