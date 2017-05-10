package me.zero.client.api.event.defaults;

import me.zero.client.api.event.type.Cancellable;
import net.minecraft.entity.Entity;

/**
 * Called when 2 entities collide with one another.
 * If cancelled, the collision doesn't occur.
 *
 * @since 1.0
 *
 * @author Brady
 * @since 4/8/2017 12:00PM
 */
public final class EntityCollisionEvent extends Cancellable {

    /**
     * Entity being collided into
     */
    private final Entity entity;

    /**
     * Entity colliding into other entity
     */
    private final Entity collidingEntity;

    public EntityCollisionEvent(Entity entity, Entity collidingEntity) {
        this.entity = entity;
        this.collidingEntity = collidingEntity;
    }

    /**
     * @since 1.0
     *
     * @return The entity being collided into
     */
    public final Entity getEntity() {
        return this.entity;
    }

    /**
     * @since 1.0
     *
     * @return Entity colliding into other entity
     */
    public final Entity getCollidingEntity() {
        return this.collidingEntity;
    }
}
