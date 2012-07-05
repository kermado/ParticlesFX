package particlesfx;

import gameengine.Sprite;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;

/**
 * Represents a single particle.
 * 
 * Renders a particle and implements methods for position updating along with
 * collision detection.
 * 
 * @author Omar Kermad
 * @version 1.0
 */
public class Particle extends Sprite {
    
    /**
     * Particle constructor.
     * 
     * @param radius particle radius
     */
    public Particle(double radius) {
        Circle sphere = CircleBuilder.create()
                .strokeWidth(1)
                .centerX(radius)
                .centerY(radius)
                .radius(radius)
                .cache(true)
                .build();
        
        RadialGradient gradient = RadialGradientBuilder.create()
                .centerX(sphere.getCenterX())
                .centerY(sphere.getCenterY())
                .radius(radius)
                .proportional(false)
                .stops(new Stop(0, Color.rgb(215, 0, 0)), new Stop(1, Color.rgb(175, 0, 0)))
                .build();
        
        sphere.setFill(gradient);
        
        setNode(sphere);
    }

    /**
     * Update particle.
     * 
     * Updates the particle's position in space according to its current
     * position and velocity.
     */
    @Override
    public void update() {
        Node node = getNode();
        node.setTranslateX(node.getTranslateX() + getVelocityX());
        node.setTranslateY(node.getTranslateY() + getVelocityY());
    }

    /**
     * Sprite collision detection.
     * 
     * Detects whether a collision has occurred with another sprite.
     * 
     * @param other other sprite
     * @return true if sprites have collided, false otherwise
     */
    @Override
    public boolean collide(Sprite other) {
        if (other instanceof Particle) {
            return collide((Particle) other);
        }
        
        return false;
    }
    
    /**
     * Particle collision detection.
     * 
     * Detects whether a collision between two particles have occurred.
     * 
     * @param other other particle
     * @return true if particles have collided, false otherwise
     */
    public boolean collide(Particle other) {        
        Circle thisSphere = (Circle)getNode();
        Circle otherSphere = (Circle)other.getNode();
        
        // Find the centroid coordinates for both particles
        double thisCentroidX = thisSphere.getTranslateX() + thisSphere.getCenterX();
        double thisCentroidY = thisSphere.getTranslateY() + thisSphere.getCenterY();
        double otherCentroidX = otherSphere.getTranslateX() + otherSphere.getCenterX();
        double otherCentroidY = otherSphere.getTranslateY() + otherSphere.getCenterY();
        
        // We compare the square of the distances to avoid using computationally
        // expensive square root.
        double dx = thisCentroidX - otherCentroidX;
        double dy = thisCentroidY - otherCentroidY;
        double distanceSquared = dx*dx + dy*dy;
        double radiiSquared = Math.pow(thisSphere.getRadius() + otherSphere.getRadius(), 2);
        
        // Particles have collided if the square of the distance between the
        // the paricles is smaller than the square of the sum of their radii.
        return (distanceSquared < radiiSquared);
    }
    
    /**
     * Set particle position.
     * 
     * Sets the position of the particle in space.
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void setPosition(double x, double y) {
        Node node = getNode();
        node.setTranslateX(x);
        node.setTranslateY(y);
    }
    
}
