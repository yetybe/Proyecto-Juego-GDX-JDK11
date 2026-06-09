package io.github.ConstructoresEnemigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import io.github.Personaje.Enemigo;
import io.github.Personaje.EnemigoDistancia;

public class BuilderDistancia implements BuilderEnemigo {
        private int x, y;
        private Texture textura;
        private Texture texturaBala;
        private Sound sonidoAtq;

        public BuilderDistancia(Texture tx, Texture txBala, Sound snd) {
            this.textura = tx;
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
            int vidaBase = 4;
            float velBase = 1.2f; 
            int dañoBase = 1;
            int dropXp = 1;
            
            EnemigoDistancia enemigo = new EnemigoDistancia(vidaBase, velBase, dañoBase, dropXp, textura, texturaBala, sonidoAtq, x, y);

            this.x = 0;
            this.y = 0;
            
            return enemigo;    
        }
}