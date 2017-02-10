package me.zero.example.mod.mods;

import me.zero.client.api.event.EventHandler;
import me.zero.client.api.event.defaults.Render2DEvent;
import me.zero.client.api.module.Mod;
import me.zero.client.api.module.Module;
import me.zero.example.ExampleClient;
import me.zero.example.mod.category.IRender;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.util.Comparator;

/**
 * Created by Brady on 2/8/2017.
 */
@Mod(name = "HUD", description = "Displays an In-Game HUD")
public class Hud extends Module implements IRender {

    private int y;

    public Hud() {
        this.toggle();
    }

    @EventHandler
    public void onRender2D(Render2DEvent event) {
        ScaledResolution sr = new ScaledResolution(mc);
        FontRenderer fr = mc.fontRendererObj;

        fr.drawStringWithShadow("ExampleClient", 2, 2, -1);

        y = sr.getScaledHeight() - 12;
        int x = sr.getScaledWidth() - 2;
        ExampleClient.getInstance().getModuleManager().getData().stream().filter(Module::getState).sorted(Comparator.comparingInt(m -> -fr.getStringWidth(m.getName()))).forEach(module -> {
            fr.drawString(module.getName(), x - fr.getStringWidth(module.getName()), y, -1);
            y -= 10;
        });
    }
}
