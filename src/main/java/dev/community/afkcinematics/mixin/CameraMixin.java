package dev.community.afkcinematics.mixin;

import dev.community.afkcinematics.AfkCinematicsClient;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow protected abstract void setRotation(float yaw, float pitch);
    @Inject(method="update", at=@At("TAIL"))
    private void afkcinematics$applyCinematicRotation(CallbackInfo ci) {
        var director=AfkCinematicsClient.DIRECTOR;
        if (director.isActive()) {
            Camera self=(Camera)(Object)this;
            // Add a slow, smooth camera drift to the current view.
            setRotation(self.getYaw()+director.yawOffset(), self.getPitch()+director.pitchOffset());
        }
    }
}
