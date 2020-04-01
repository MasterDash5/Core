package dashnetwork.core.global.listeners;

import dashnetwork.core.survival.Survival;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState() && !event.getWorld().getName().equals(Survival.getWorld().getName()))
            event.setCancelled(true);
    }

}
