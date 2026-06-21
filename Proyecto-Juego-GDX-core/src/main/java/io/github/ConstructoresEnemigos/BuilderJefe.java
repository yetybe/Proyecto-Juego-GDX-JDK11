package io.github.ConstructoresEnemigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import io.github.Personaje.Enemigo;
import io.github.Personaje.EnemigoJefe;

public class BuilderJefe implements BuilderEnemigo {
    private int x, y;
    private Texture texturaJefe; 
    private Texture texturaBala;
    private Sound sonidoAtq;

    public BuilderJefe(Texture tx, Texture txBala, Sound snd) {
        this.texturaJefe = tx;
        this.texturaBala = txBala; 
        this.sonidoAtq = snd;
    }

    @Override
    public BuilderEnemigo setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
        return this; 
    }

    @Override
    public Enemigo build() {
        int vidaBase = 500; 
        float velBase = 1.0f; 
        int dañoBase = 2;
        int dropXp = 50;
        
        EnemigoJefe jefe = new EnemigoJefe(vidaBase, velBase, dañoBase, dropXp, texturaJefe, texturaBala, sonidoAtq, x, y);
        
        // Cambiamos el tamaño directamente desde su sprite interno heredado
        jefe.getSprite().setSize(260, 280); 
        
        this.x = 0;
        this.y = 0;
        
        return jefe;    
    }
}