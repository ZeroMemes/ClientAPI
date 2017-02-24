package me.zero.client.load.transformer.wrapper.defaults;

import me.zero.client.api.wrapper.IMinecraft;
import me.zero.client.load.transformer.wrapper.ClassWrapper;

import static me.zero.client.load.transformer.reference.obfuscation.MCMappings.*;

/**
 * Wrapper for IMinecraft
 *
 * @since 1.0
 *
 * Created by Brady on 2/23/2017.
 */
public class WMinecraft extends ClassWrapper {

    public WMinecraft() {
        super(Minecraft, IMinecraft.class);
    }

    @Override
    protected void loadImplementations() {
        this.implementR("getTimer", Timer.getCtClass(), timer);
        this.implementS("setSession", Session.getCtClass(), session);
    }
}
