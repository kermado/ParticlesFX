package particlesfx;

import gameengine.GameWorld;
import gameengine.Sprite;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ParticleFX Game.
 * 
 * Sets the scene and game surface. Generates the particles and handles the
 * behavior of particle-particle and particle-wall collisions.
 * 
 * @author Omar Kermad
 * @version 1.0
 */
public class ParticlesFXGame extends GameWorld {
    
    /**
     * ParticleFX constructor.
     * 
     * @param fps target frames per second
     * @param title game title
     */
    public ParticlesFXGame(int fps, String title) {
        super(fps, title);
    }

    /**
     * Initializes the game.
     * 
     * Sets the game surface and runs the generateParticles method.
     * 
     * @param primaryStage 
     */
    @Override
    public void initialize(Stage primaryStage) {
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 600, 600));
        primaryStage.setScene(getGameSurface());
        primaryStage.setTitle(getWindowTitle());
        
        generateParticles(100, 10, 4);
    }
    
    /**
     * Generates particles.
     * 
     * Creates the specified number of particles and initializes them to random
     * positions with random velocities.  These particles are then added to
     * the game scene.
     * 
     * @param numberParticles number of particles to generate
     * @param radius radius for each particle
     * @param maxInitialVelocity maximum particle velocity
     */
    public void generateParticles(int numberParticles, double radius, double maxInitialVelocity) {
        Scene gameSurface = getGameSurface();
        Random rnd = new Random();
        
        for (int i = 0; i < numberParticles; i++) {
            int x = rnd.nextInt((int)(gameSurface.getWidth() - radius*2));
            int y = rnd.nextInt((int)(gameSurface.getHeight() - radius*2));
            
            Particle p = new Particle(radius);
            p.setPosition(x, y);
            p.setVelocityX(rnd.nextInt((int)maxInitialVelocity*2) - maxInitialVelocity);
            p.setVelocityY(rnd.nextInt((int)maxInitialVelocity*2) - maxInitialVelocity);
            
            getSpriteManager().addSprites(p);
            getSceneNodes().getChildren().add(p.getNode());
        }
    }

    /**
     * Sprite frame updating.
     * 
     * Detects collisions with the game surface's bounds and updates particles'
     * velocities accordingly.
     * 
     * @param sprite sprite to update
     */
    @Override
    protected void handleUpdate(Sprite sprite) {
        if (sprite instanceof Particle) {
            sprite.update();
            
            double particleRight = sprite.getNode().getTranslateX() + sprite.getNode().getBoundsInParent().getWidth();
            double particleTop = sprite.getNode().getTranslateY() + sprite.getNode().getBoundsInParent().getHeight();
            
            if ((particleRight > getGameSurface().getWidth() && sprite.getVelocityX() > 0) ||
                (sprite.getNode().getTranslateX() < 0 && sprite.getVelocityX() < 0)) {
            
                sprite.setVelocityX(-1 * sprite.getVelocityX());
            }
                
            if ((particleTop > getGameSurface().getHeight() && sprite.getVelocityY() > 0) ||
                (sprite.getNode().getTranslateY() < 0 && sprite.getVelocityY() < 0)) {
                
                sprite.setVelocityY(-1 * sprite.getVelocityY());
            }
        }
    }

    /**
     * Handle sprite collisions.
     * 
     * Provides behavior for particle-particle collisions.
     * 
     * @param spriteA sprite A
     * @param spriteB sprite B
     * @return true if the two sprites have collided, false otherwise
     */
    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        if (spriteA instanceof Particle &&
            spriteB instanceof Particle &&
            spriteA.collide(spriteB)) {
            
            double ar = spriteA.getNode().getBoundsInParent().getWidth();
            double br = spriteB.getNode().getBoundsInParent().getWidth();
            
            double ax = spriteA.getNode().getTranslateX() + ar;
            double ay = spriteA.getNode().getTranslateY() + ar;
            double bx = spriteB.getNode().getTranslateX() + br;
            double by = spriteB.getNode().getTranslateY() + br;
            
            double avx = spriteA.getVelocityX();
            double avy = spriteA.getVelocityY();
            double bvx = spriteB.getVelocityX();
            double bvy = spriteB.getVelocityY();
            
            double dx = ax - bx;
            double dy = ay - by;
            
            double dvx = bvx - avx;
            double dvy = bvy - avy;
            
            double dotproduct = dx*dvx + dy*dvy;
            
            // We only want to change the particles' velocity when the particles
            // are moving towards each other. Otherwise, we end up changing
            // their velocities when the paricles have already collided and are
            // moving away from each other (due to overlapping bounds).
            if (dotproduct > 0) {
                spriteA.setVelocityX(bvx);
                spriteA.setVelocityY(bvy);
                spriteB.setVelocityX(avx);
                spriteB.setVelocityY(avy);
                return true;
            }
        }
        
        return false;
    }
    
}
