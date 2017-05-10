package me.zero.client.api.util.render.camera.defaults;

import me.zero.client.api.util.math.Vec2;
import me.zero.client.api.util.render.camera.Camera;
import me.zero.client.api.util.render.camera.CameraHandle;
import me.zero.client.wrapper.IEntity;

/**
 * An implementation of Camera that renders what is directly behind the player.
 *
 * @since 1.0
 *
 * @author Brady
 * @since 2/4/2017 12:00PM
 */
public final class RearViewCamera extends Camera {

    public RearViewCamera(CameraHandle handle) {
        super(handle);
    }

    @Override
    public void updateFramebuffer(float partialTicks) {
        IEntity entity = (IEntity) mc.player;
        Vec2 rotations = entity.getRotations();
        this.position = entity.getPos();
        this.rotation = new Vec2(rotations.getX() + 180, rotations.getY() * -1);
        super.updateFramebuffer(partialTicks);
    }
}
