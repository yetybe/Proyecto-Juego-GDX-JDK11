package io.github.Personaje;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.Pantallas.PantallaJuego;

public abstract class Enemigo extends Entidad {
    
    protected int dropXp;
    protected Sound sonidoAtaque;

    public Enemigo(int vidaMax, float velocidadMax, int dañoAtaque, Sprite spr , Sound sonidoAtaque, int dropXp) {
        super(vidaMax, velocidadMax, dañoAtaque, spr);
        this.dropXp = dropXp;
        this.sonidoAtaque = sonidoAtaque;
    }
    
    public abstract void atacar(PantallaJuego juego);
    public int getDropXp() { return dropXp; }
    public Sound getSonidoAtq() { return sonidoAtaque; }

    public int getVidaMax() { 
        return this.vidaMax; 
    }
    
    public void setVidaMax(int nuevaVidaMax) { 
        this.vidaMax = nuevaVidaMax; 
    }
    
    public void setVidaActual(int nuevaVida) { 
        this.vidaActual = nuevaVida; 
    }
}

