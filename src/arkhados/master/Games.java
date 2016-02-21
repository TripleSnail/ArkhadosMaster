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
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.util.HashMap;
import java.util.Map;

public class Games extends Listener {

    private final Map<Connection, Game> games = new HashMap<>();

    @Override
    public void disconnected(Connection connection) {
        super.disconnected(connection);
        handleUnregister(connection);
    }

    @Override
    public void received(Connection source, Object m) {
        if (m instanceof ReqGameList) {
            handleGameList(source);
        } else if (m instanceof ReqRegisterGame) {
            handleRegister(source, (ReqRegisterGame) m);
        } else if (m instanceof ReqUnregisterGame) {
            handleUnregister(source);
        }
    }

    private void handleGameList(Connection src) {
        src.sendTCP(new RepGameList(games.values()));
    }

    private void handleRegister(Connection src, ReqRegisterGame req) {
        System.out.println("New game registered: " + req.toString() + " at "
                + src.toString());
        games.put(src, new Game(req.gameMode, src.getRemoteAddressTCP()
                .getAddress().getHostAddress(), req.port));
    }

    private void handleUnregister(Connection src) {
        Game removed = games.remove(src);
        if (removed != null) {
            System.out.println("Removed game: " + removed.toString()
                    + " at " + src);
        }
    }
}
