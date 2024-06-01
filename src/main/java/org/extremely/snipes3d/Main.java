package org.extremely.snipes3d;

import org.extremely.snipes3d.engine.core.Engine;
import org.extremely.snipes3d.game.Snipes3DGame;

public class Main {
    public static void main(String[] args) {
        var engine = Engine.getInstance();
        engine.init(new Snipes3DGame());
        engine.start();
    }
}