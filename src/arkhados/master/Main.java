/*    This file is part of Arkhados.

 Arkhados is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Arkhados is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Arkhados.  If not, see <http://www.gnu.org/licenses/>. */
package arkhados.master;

import arkhados.master.messages.KryoMessages;
import com.esotericsoftware.kryonet.Server;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends SimpleApplication {

    private final Games games = new Games();

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(10);

        Main app = new Main();
        app.setShowSettings(false);
        app.setSettings(settings);
        app.setPauseOnLostFocus(false);
        app.start(JmeContext.Type.Headless);
    }

    private Server server;

    @Override
    public void simpleInitApp() {
        server = new Server();        
        registerMasterMessages();

        try {
            server.bind(12346, 12346);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            stop();
            return;
        }

        server.addListener(games);
        server.start();
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    @Override
    public void destroy() {
        super.destroy();
        if (server != null) {
            server.close();
        }
    }       

    private void registerMasterMessages() {
        KryoMessages.register(server.getKryo());
    }
}
