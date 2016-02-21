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
package arkhados.master.messages;

import arkhados.master.Game;
import arkhados.master.messages.RepGameList;
import arkhados.master.messages.ReqGameList;
import arkhados.master.messages.ReqRegisterGame;
import arkhados.master.messages.ReqUnregisterGame;
import com.esotericsoftware.kryo.Kryo;
import java.util.ArrayList;

public class KryoMessages {

    public static void register(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(Game.class);
        kryo.register(RepGameList.class);
        kryo.register(ReqGameList.class);
        kryo.register(ReqRegisterGame.class);
        kryo.register(ReqUnregisterGame.class);
    }
}
