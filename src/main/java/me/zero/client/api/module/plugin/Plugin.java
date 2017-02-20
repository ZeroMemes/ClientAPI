package me.zero.client.api.module.plugin;

import me.zero.client.api.module.Module;

import java.util.ArrayList;
import java.util.List;

/**
 * Base for Plugin Main classes
 *
 * @since 1.0
 *
 * Created by Brady on 1/27/2017.
 */
public abstract class Plugin {

    /**
     * The list of Modules associated with this Plugin
     */
    private List<Module> modules = new ArrayList<>();

    /**
     * The info for this plugin
     */
    private PluginInfo info;

    /**
     * Plugin Init method, Not used for anything essential.
     * Just a way for plugin developers to know
     * when the Client {@code postInit()} has finished.
     *
     * @since 1.0
     */
    public abstract void onInit();

    /**
     * Sets the info for this plugin, the info
     * will only be set if it has not already
     * been set.
     *
     * @param info The info being set
     */
    void setInfo(PluginInfo info) {
        if (this.info != null) return;
        this.info = info;
    }

    /**
     * @since 1.0
     *
     * @return The Info of this plugin
     */
    public PluginInfo getInfo() {
        return this.info;
    }

    /**
     * Loads a Module into the Module list of this plugin
     *
     * @since 1.0
     *
     * @param module Module being loaded
     */
    void loadModule(Module module) {
        this.modules.add(module);
    }

    /**
     * @since 1.0
     *
     * @return The module list associated with this plugin
     */
    public List<Module> getModules() {
        return new ArrayList<>(modules);
    }
}
