package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Pantallas.PantallaJuego;

public abstract class Enemigo extends Entidad {
    
    protected int dropXp;
    protected Sound sonidoAtaque;

    public Enemigo(float vidaMax, float velocidadMax, int dañoAtaque, Sprite spr , Sound sonidoAtaque, int dropXp) {
        super(vidaMax, velocidadMax, dañoAtaque, spr);
        this.dropXp = dropXp;
        this.sonidoAtaque = sonidoAtaque;
    }
    
    public abstract void atacar(PantallaJuego juego);
    public int getDropXp() { return dropXp; }
    public Sound getSonidoAtq() { return sonidoAtaque; }

    public float getVidaMax() { 
        return this.vidaMax; 
    }
    
    public void setVidaMax(float nuevaVidaMax) { 
        this.vidaMax = nuevaVidaMax; 
    }
    
    public void setVidaActual(float nuevaVida) { 
        this.vidaActual = nuevaVida; 
    }
}

