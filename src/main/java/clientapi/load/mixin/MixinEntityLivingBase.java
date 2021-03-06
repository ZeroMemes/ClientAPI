/*
 * Copyright 2018 ImpactDevelopment
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package clientapi.load.mixin;

import clientapi.ClientAPI;
import clientapi.event.defaults.game.entity.EntityDeathEvent;
import clientapi.event.defaults.game.entity.EntityJumpEvent;
import clientapi.event.defaults.game.entity.EntityTravelEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Brady
 * @since 4/27/2017
 */
@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends MixinEntity {

    @Inject(
            method = "onDeath",
            at = @At("HEAD")
    )
    private void onDeath(DamageSource cause, CallbackInfo ci) {
        EntityLivingBase _this = (EntityLivingBase) (Object) this;
        if (!(_this instanceof EntityPlayer))
            ClientAPI.EVENT_BUS.post(new EntityDeathEvent(_this, cause));
    }

    @Redirect(
            method = "onLivingUpdate",
            at = @At(
                    value = "INVOKE",
                    target = "net/minecraft/entity/EntityLivingBase.travel(FFF)V"
            )
    )
    private void onLivingUpdate$travel(EntityLivingBase entity, float strafe, float vertical, float forward) {
        EntityTravelEvent event = new EntityTravelEvent(EventState.PRE, entity, strafe, vertical, forward);
        ClientAPI.EVENT_BUS.post(event);
        if (!event.isCancelled())
            entity.travel(event.getStrafe(), event.getVertical(), event.getForward());

        ClientAPI.EVENT_BUS.post(new EntityTravelEvent(EventState.POST, entity, strafe, vertical, forward));
    }

    @Inject(
            method = "jump",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preJump(CallbackInfo ci) {
        EntityJumpEvent event = new EntityJumpEvent(EventState.PRE, (EntityLivingBase) (Object) this);
        ClientAPI.EVENT_BUS.post(event);
        if (event.isCancelled())
            ci.cancel();
    }

    @Inject(
            method = "jump",
            at = @At("RETURN")
    )
    private void postJump(CallbackInfo ci) {
        ClientAPI.EVENT_BUS.post(new EntityJumpEvent(EventState.POST, (EntityLivingBase) (Object) this));
    }
}
