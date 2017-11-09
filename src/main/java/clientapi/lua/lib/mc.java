package clientapi.lua.lib;

import clientapi.lua.LuaLibrary;
import clientapi.lua.extension.FourArgFunction;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

import java.util.Map;

import static clientapi.util.interfaces.Helper.*;

/**
 * @author Brady
 * @since 11/8/2017 12:26 PM
 */
public final class mc extends LuaLibrary {

    public mc() {
        super("mc");
    }

    @Override
    public final void load(Map<String, LuaValue> table) {
        table.put("message", new message());
        table.put("drawString", new drawString());
        table.put("drawStringWithShadow", new drawStringWithShadow());
    }

    /**
     * @see EntityPlayerSP#sendChatMessage(String)
     */
    static final class message extends OneArgFunction {

        @Override
        public final LuaValue call(LuaValue message) {
            if (message.isstring() && mc.player != null) {
                mc.player.sendChatMessage(message.tojstring());
                return TRUE;
            }
            return FALSE;
        }
    }

    /**
     * @see FontRenderer#drawString(String, float, float, int, boolean)
     */
    static final class drawString extends FourArgFunction {

        @Override
        public final LuaValue call(LuaValue string, LuaValue x, LuaValue y, LuaValue color) {
            if (!string.isstring() || !x.isnumber() || !y.isnumber() || !color.isnumber())
                return NIL;

            return LuaInteger.valueOf(mc.fontRenderer.drawString(string.tojstring(), x.tofloat(), y.tofloat(), color.toint(), false));
        }
    }

    /**
     * @see FontRenderer#drawString(String, float, float, int, boolean)
     */
    static final class drawStringWithShadow extends FourArgFunction {

        @Override
        public final LuaValue call(LuaValue string, LuaValue x, LuaValue y, LuaValue color) {
            if (!string.isstring() || !x.isnumber() || !y.isnumber() || !color.isnumber())
                return NIL;

            return LuaInteger.valueOf(mc.fontRenderer.drawString(string.tojstring(), x.tofloat(), y.tofloat(), color.toint(), true));
        }
    }
}
