package me.zero.client.api.module;

import me.zero.client.api.event.EventManager;
import me.zero.client.api.util.keybind.Keybind;

/**
 * A type of module intended for use as a sub-module
 *
 * @since 1.0
 *
 * Created by Brady on 2/24/2017.
 */
public class ModuleMode<T extends Module> implements IModule {

    /**
     * Parent Module
     */
    protected final T parent;

    /**
     * Name for the mode
     */
    private final String name;

    /**
     * The state of the mode
     */
    private boolean state;

    public ModuleMode(T parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    @Override
    public void toggle() {}

    @Override
    public void setState(boolean state) {
        this.state = state;
        if (state) {
            if (parent.getState()) {
                this.onEnable();
                EventManager.subscribe(this);
            }
        } else {
            this.onDisable();
            EventManager.unsubscribe(this);
        }
    }

    /**
     * @since 1.0
     *
     * @return The name of the mode
     */
    public final String getName() {
        return this.name;
    }

    /**
     * @since 1.0
     *
     * @return The parent module
     */
    public final Module getParent() {
        return this.parent;
    }

    @Override
    public final boolean getState() {
        return state;
    }

    /* Methods below are irrelevant to Modes */

    @Override
    public final Keybind getBind() {
        return null;
    }

    @Override
    public final Class<?> getType() {
        return null;
    }
}
