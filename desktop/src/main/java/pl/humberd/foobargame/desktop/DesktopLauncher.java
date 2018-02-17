package pl.humberd.foobargame.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.czyzby.autumn.fcs.scanner.DesktopClassScanner;
import com.github.czyzby.autumn.mvc.application.AutumnApplication;
import pl.humberd.foobargame.Core;
import pl.humberd.foobargame.configuration.Constants;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
    public static void main(String[] args) {
        createApplication();
    }

    private static LwjglApplication createApplication() {
        return new LwjglApplication(new AutumnApplication(new DesktopClassScanner(), Core.class),
                getDefaultConfiguration());
    }

    private static LwjglApplicationConfiguration getDefaultConfiguration() {
        LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
        configuration.title = "foobargame";
        configuration.width = Constants.WIDTH;
        configuration.height = Constants.HEIGHT;
        for (int size : new int[] { 128, 64, 32, 16 }) {
            configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
        }
        return configuration;
    }
}