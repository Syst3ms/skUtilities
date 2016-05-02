package uk.tim740.skUtilities.files;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import uk.tim740.skUtilities.Utils;
import uk.tim740.skUtilities.skUtilities;

/**
 * Created by tim740
 */
public class EffRunApp extends Effect{
	private Expression<String> path;

	@Override
	protected void execute(Event arg0) {
        File pth = new File(Utils.getDefaultPath() + path.getSingle(arg0));
        if (Desktop.isDesktopSupported()){
            try{
                EvtRunApp era = new EvtRunApp(pth);
                Bukkit.getServer().getPluginManager().callEvent(era);
                if (!era.isCancelled()) {
                    if(!pth.exists()){
                        throw new IOException();
                    }else{
                        Desktop.getDesktop().open(pth);
                    }
                }
            }catch (IOException e){
                skUtilities.prSys("'" + pth + "' isn't a valid path!", getClass().getSimpleName(), 0);
            }
        }else{
            skUtilities.prSys("Sorry this OS isn't supported!", getClass().getSimpleName(), 0);
            skUtilities.prSysi("");
            skUtilities.prSysi("If you using a Linix based system you could try installing");
            skUtilities.prSysi("libgnome: 'sudo apt-get install libgnome2-0'");
            skUtilities.prSysi("and then restart the system!");
            skUtilities.prSysi("");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
        path = (Expression<String>) arg0[0];
        return true;
    }
    @Override
    public String toString(@Nullable Event arg0, boolean arg1) {
        return getClass().getName();
    }
}