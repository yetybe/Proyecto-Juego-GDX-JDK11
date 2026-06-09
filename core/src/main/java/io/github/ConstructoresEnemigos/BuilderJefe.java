package io.github.ConstructoresEnemigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import io.github.Personaje.Enemigo;
import io.github.Personaje.EnemigoJefe;

public class BuilderJefe implements BuilderEnemigo {
    private int x, y;
    private Texture texturaJefe; // Ahora recibe Texture
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
        int vidaBase = 200; 
        float velBase = 0.5f; // Lento pero imponente
        int dañoBase = 2;
        int dropXp = 50;
        
        // Creamos el Sprite internamente y lo enviamos al constructor
        Sprite spriteJefe = new Sprite(texturaJefe);
        EnemigoJefe jefe = new EnemigoJefe(vidaBase, velBase, dañoBase, dropXp, spriteJefe, sonidoAtq, texturaBala);
        
        // Asignamos tamaño y posición (Tu decisión #2)
        jefe.getSprite().setSize(260, 280); 
        jefe.getSprite().setPosition(x, y);
        
        // Reiniciamos para el siguiente spawn
        this.x = 0;
        this.y = 0;
        
        return jefe;    
    }
}
