package io.github.ConstructoresEnemigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import io.github.Personaje.Enemigo;
import io.github.Personaje.EnemigoMelee;

public class BuilderMelee implements BuilderEnemigo {
        private int x, y;
        private Texture textura;
        private Sound sonidoHerido;
        private Sound sonidoAtq;

        // 1. El constructor ahora recibe ambos sonidos
        public BuilderMelee(Texture tx, Sound sndAtaque) {
            this.textura = tx;
            this.sonidoAtq = sndAtaque;
        }

        @Override
        public BuilderEnemigo setPosicion(int x, int y) {
            this.x = x;
            this.y = y;
            return this; 
        }

        @Override
        public Enemigo build() {
            // Ensambla el EnemigoMelee con sus stats fijos por defecto
            int vidaBase = 7;
            float velBase = 1f;
            int dañoBase = 1;
            int dropXp = 1;
            
            // 2. Se pasan ambos sonidos (sonidoHerido y sonidoAtq) al constructor
            EnemigoMelee enemigo = new EnemigoMelee(vidaBase, velBase, dañoBase, dropXp, textura, sonidoAtq, x, y);
            
            this.x = 0;
            this.y = 0;
            
            return enemigo;    
        }
}