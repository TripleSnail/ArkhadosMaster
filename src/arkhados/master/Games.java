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

import arkhados.master.messages.RepGameList;
import arkhados.master.messages.ReqGameList;
import arkhados.master.messages.ReqRegisterGame;
import arkhados.master.messages.ReqUnregisterGame;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.HashMap;
import java.util.Map;

public class Games implements ConnectionListener,
        MessageListener<HostedConnection> {

    private final Map<HostedConnection, Game> games = new HashMap<>();

    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
    }

    @Override
    public void messageReceived(HostedConnection source, Message m) {
        if (m instanceof ReqGameList) {
            handleGameList(source);
        } else if (m instanceof ReqRegisterGame) {
            handleRegister(source, (ReqRegisterGame) m);
        } else if (m instanceof ReqUnregisterGame) {
            handleUnregister(source);
        }
    }

    private void handleGameList(HostedConnection src) {
        src.send(new RepGameList(games.values()));
    }
    
    private void handleRegister(HostedConnection src, ReqRegisterGame req) {
        games.put(src, new Game(req.gameMode, src.getAddress()));
    }

    private void handleUnregister(HostedConnection src) {
        games.remove(src);
    }
}
