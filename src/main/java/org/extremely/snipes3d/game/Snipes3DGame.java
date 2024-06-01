/*
 * Copyright (C) 2014 Benny Bobaganoosh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.extremely.snipes3d.game;

import org.extremely.snipes3d.engine.core.*;
import org.extremely.snipes3d.engine.core.components.Camera;
import org.extremely.snipes3d.engine.core.components.MeshRenderer;
import org.extremely.snipes3d.engine.rendering.Material;
import org.extremely.snipes3d.engine.rendering.Mesh;
import org.extremely.snipes3d.engine.rendering.RenderingEngine;
import org.extremely.snipes3d.engine.rendering.Texture;
import org.joml.Math;
import org.joml.Vector3f;

import java.io.File;
import java.util.Arrays;

import static java.util.Objects.requireNonNull;

public class Snipes3DGame implements Game {
//    private InputServer inputServer;

    @Override
    public EngineSettings getSettings() {
        return new EngineSettings(1600, 900, "Snipes 3D", 60, true);
    }

    @Override
    public void init() {
        var sceneGraph = Engine.getInstance().getSceneGraph();

        var light = new Light(new Vector3f(10, 20, 20), new Vector3f(1, 1, 1));
        sceneGraph.setLight(light);
        sceneGraph.getRoot().add(createCamera());
    }

    @Override
    public void input() {
        Engine.getInstance().getSceneGraph().getRoot().input();
    }

    @Override
    public void update(float frameTime) {
        Engine.getInstance().getSceneGraph().getRoot().update(frameTime);
    }

    @Override
    public void render(RenderingEngine renderingEngine) {

    }

    private Camera createCamera() {
        Vector3f position = new Vector3f(0f, 10f, 4f);
        Vector3f center = new Vector3f(0f, 0f, 0f);
        Vector3f up = new Vector3f(0f, 1f, 0f);

        return new Camera(70.0f, 0.01f, 1000f, position, center, up);
    }
}
