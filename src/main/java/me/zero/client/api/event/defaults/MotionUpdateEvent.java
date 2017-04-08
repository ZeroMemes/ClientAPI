package me.zero.client.api.event.defaults;

import me.zero.client.api.event.type.EventState;
import me.zero.client.api.util.interfaces.Helper;
import me.zero.client.api.util.math.Vec2;
import me.zero.client.api.util.math.Vec3;
import me.zero.client.wrapper.IEntity;

/**
 * Called before and after packets are sent to
 * the server to update your location and position
 *
 * @since 1.0
 *
 * Created by Brady on 2/12/2017.
 */
public final class MotionUpdateEvent implements Helper {

    /**
     * Original and New positions
     */
    private static Vec3 oPos, nPos;

    /**
     * Original and New rotations
     */
    private static Vec2 oRotation, nRotation;

    /**
     * OnGround state
     */
    private static boolean oGround, nGround;

    /**
     * State of this event
     */
    private EventState type;

    public MotionUpdateEvent(EventState type) {
        this.type = type;
        if (type == EventState.POST) return;

        IEntity util = (IEntity) mc.player;
        oPos      = util.getPos();
        oRotation = util.getRotations();
        oGround   = mc.player.onGround;
        nPos      = util.getPos();
        nRotation = util.getRotations();
        nGround   = mc.player.onGround;
    }

    /**
     * Sets the X position
     *
     * @since 1.0
     *
     * @param x The new X position
     * @return This event
     */
    public MotionUpdateEvent x(double x) {
        nPos.x(x);
        return this;
    }

    /**
     * Sets the Y position
     *
     * @since 1.0
     *
     * @param y The new Y position
     * @return This event
     */
    public MotionUpdateEvent y(double y) {
        nPos.y(y);
        return this;
    }

    /**
     * Sets the Z position
     *
     * @since 1.0
     *
     * @param z The new Z position
     * @return This event
     */
    public MotionUpdateEvent z(double z) {
        nPos.z(z);
        return this;
    }

    /**
     * Sets the Yaw rotation
     *
     * @since 1.0
     *
     * @param yaw The new yaw rotation
     * @return This event
     */
    public MotionUpdateEvent yaw(float yaw) {
        nRotation.x(yaw);
        return this;
    }

    /**
     * Sets the Pitch rotation
     *
     * @since 1.0
     *
     * @param pitch The new pitch rotation
     * @return This event
     */
    public MotionUpdateEvent pitch(float pitch) {
        nRotation.y(pitch);
        return this;
    }

    /**
     * Sets the onGround state
     *
     * @since 1.0
     *
     * @param onGround The new onGround state
     * @return This event
     */
    public MotionUpdateEvent onGround(boolean onGround) {
        nGround = onGround;
        return this;
    }

    /**
     * @since 1.0
     *
     * @return The X position
     */
    public double getX() {
        return nPos.getX();
    }

    /**
     * @since 1.0
     *
     * @return The Y position
     */
    public double getY() {
        return nPos.getY();
    }

    /**
     * @since 1.0
     *
     * @return The Z position
     */
    public double getZ() {
        return nPos.getZ();
    }

    /**
     * @since 1.0
     *
     * @return The Yaw rotation
     */
    public float getYaw() {
        return nRotation.getX();
    }

    /**
     * @since 1.0
     *
     * @return The Pitch rotation
     */
    public float getPitch() {
        return nRotation.getY();
    }

    /**
     * @since 1.0
     *
     * @return The OnGround state
     */
    public boolean isOnGround() {
        return nGround;
    }

    /**
     * @since 1.0
     *
     * @return The type of event
     */
    public EventState getType() {
        return this.type;
    }

    /**
     * Applies the new position/rotations
     */
    public static void apply() {
        IEntity util = (IEntity) mc.player;
        util.setPos(nPos);
        util.setRotations(nRotation);
        mc.player.onGround = nGround;
    }

    /**
     * Resets the original position/rotations
     */
    public static void reset() {
        IEntity util = (IEntity) mc.player;
        util.setPos(oPos);
        util.setRotations(oRotation);
        mc.player.onGround = oGround;
    }
}
