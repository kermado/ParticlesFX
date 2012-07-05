package particlesfx;

import gameengine.GameWorld;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * ParticlesFX application.
 * 
 * Initializes the game.
 * 
 * @author Omar Kermad
 * @version 1.0
 */
public class ParticlesFX extends Application {

    GameWorld gameworld = new ParticlesFXGame(60, "Particles");
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        gameworld.initialize(primaryStage);
        gameworld.beginGameLoop();
        primaryStage.show();
    }
    
}
