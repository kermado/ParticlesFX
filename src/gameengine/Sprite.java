package gameengine;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

/**
 * The Sprite class represents a image or node to be displayed.
 * 
 * In a 2D game a sprite will contain a velocity for the image to
 * move across the scene area. The game loop will call the update()
 * and collide() method at every interval of a key frame. A list of
 * animations can be used during different situations in the game
 * such as rocket thrusters, walking, jumping, etc.
 *
 * @author cdea
 * @author Omar Kermad
 */
public abstract class Sprite {

    /**
     * Current display node
     */
    private Node node;

    /**
     * velocity vector x direction
     */
    private double vX = 0;

    /**
     * velocity vector y direction
     */
    private double vY = 0;

    /**
     * dead?
     */
    private boolean isDead = false;

    /**
     * collision shape
     */
    private Circle collisionBounds;

    /**
     * Updates this sprite object's velocity, or animations.
     */
    public abstract void update();

    /**
     * Did this sprite collide into the other sprite?
     *
     * @param other - The other sprite.
     * @return boolean - Whether this or the other sprite collided, otherwise false.
     */
    public boolean collide(Sprite other) {

        if (collisionBounds == null || other.collisionBounds == null) {
            return false;
        }

        // determine it's size
        Circle otherSphere = other.collisionBounds;
        Circle thisSphere = collisionBounds;
        Point2D otherCenter = otherSphere.localToScene(otherSphere.getCenterX(), otherSphere.getCenterY());
        Point2D thisCenter = thisSphere.localToScene(thisSphere.getCenterX(), thisSphere.getCenterY());
        double dx = otherCenter.getX() - thisCenter.getX();
        double dy = otherCenter.getY() - thisCenter.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double minDist = otherSphere.getRadius() + thisSphere.getRadius();

        return (distance < minDist);
    }

    /**
     * Handle the death of sprites.
     * 
     * @param gameWorld 
     */
    public void handleDeath(GameWorld gameWorld) {
        gameWorld.getSpriteManager().addSpritesToBeRemoved(this);
    }
    
    /**
     * Returns the sprite's node.
     * 
     * @return sprite node
     */
    public Node getNode() {
        return node;
    }
    
    /**
     * Sets the sprite's node.
     * 
     * @param node sprite node
     */
    public void setNode(Node node) {
        this.node = node;
    }
    
    /**
     * Markes a sprite as dead.
     * 
     * Dead sprites are removed from the game world.
     * 
     * @param isDead true to mark the sprite as dead
     */
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
    
    /**
     * Returns the x-component of velocity.
     * 
     * @return x-component of velocity
     */
    public double getVelocityX() {
        return vX;
    }
    
    /**
     * Sets the x-component of velocity.
     * 
     * @param vX x-component of velocity
     */
    public void setVelocityX(double vX) {
        this.vX = vX;
    }
    
    /**
     * Returns the y-component of velocity.
     * 
     * @return y-component of velocity
     */
    public double getVelocityY() {
        return vY;
    }
    
    /**
     * Sets the y-component of velocity.
     * 
     * @param vY y-component of velocity
     */
    public void setVelocityY(double vY) {
        this.vY = vY;
    }
    
}