package io.github.ConstructoresEnemigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import io.github.Personaje.Enemigo;
import io.github.Personaje.EnemigoDistancia;

public class BuilderDistancia implements BuilderEnemigo {
        private int x, y;
        private Texture textura;
        private Texture texturaBala; // NUEVO: Atributo para guardar la imagen del proyectil
        private Sound sonidoAtq;

        // Actualizamos el constructor para recibir ambas texturas (enemigo y bala)
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
            // Ensambla el EnemigoDistancia con sus stats fijos por defecto
            int vidaBase = 4;
            float velBase = 1.2f; 
            int dañoBase = 2;
            int dropXp = 1;
            
            // Pasamos la texturaBala al constructor de EnemigoDistancia respetando el orden
            EnemigoDistancia enemigo = new EnemigoDistancia(vidaBase, velBase, dañoBase, dropXp, textura, texturaBala, sonidoAtq, x, y);
            
            // Resetea las coordenadas para el próximo enemigo
            this.x = 0;
            this.y = 0;
            
            return enemigo;    
        }
}