package dev.community.afkcinematics;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class SettingsScreen extends Screen {
    public SettingsScreen() { super(Text.literal("AFK Cinematics Reimagined")); }
    @Override protected void init() {
        int cx=width/2, y=height/4;
        addDrawableChild(ButtonWidget.builder(toggleLabel(), b -> {
            AfkCinematicsClient.CONFIG.enabled=!AfkCinematicsClient.CONFIG.enabled;
            AfkCinematicsClient.CONFIG.save(); b.setMessage(toggleLabel());
        }).dimensions(cx-100,y,200,20).build());
        addDrawableChild(ButtonWidget.builder(timeLabel(), b -> {
            int[] choices={15,30,60,90,120,180,300};
            int next=0; for(int i=0;i<choices.length;i++) if(choices[i]==AfkCinematicsClient.CONFIG.afkSeconds) next=(i+1)%choices.length;
            AfkCinematicsClient.CONFIG.afkSeconds=choices[next]; AfkCinematicsClient.CONFIG.save(); b.setMessage(timeLabel());
        }).dimensions(cx-100,y+28,200,20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> close()).dimensions(cx-100,y+64,200,20).build());
    }
    private Text toggleLabel(){return Text.literal("Cinematics: "+(AfkCinematicsClient.CONFIG.enabled?"ON":"OFF"));}
    private Text timeLabel(){return Text.literal("AFK delay: "+AfkCinematicsClient.CONFIG.afkSeconds+" seconds");}
    @Override public void close(){ if(client!=null) client.setScreen(null); }
}
