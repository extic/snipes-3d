package org.extremely.snipes3d.engine.rendering;

import org.extremely.snipes3d.engine.core.EngineSettings;
import org.extremely.snipes3d.engine.core.SceneGraph;
import org.extremely.snipes3d.engine.core.SceneObject;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine {
    private Window window;
    private Shader simpleShader;
    private Map<String, Integer> samplerMap;

    public void init(EngineSettings settings) {
        window = new Window();
        window.init(settings);

        samplerMap = new HashMap<>();
        samplerMap.put("diffuse", 0);

        simpleShader = new Shader("shader");

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
//        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
    }

    public void render(SceneGraph sceneGraph) {
        render(sceneGraph.getRoot());
        window.update();
    }

    public void render(SceneObject object) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        object.render(simpleShader, this);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

//        for(BaseLight light : m_lights)
//        {
//            m_activeLight = light;
//            object.RenderAll(light.GetShader(), this);
//        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public boolean isCloseRequested() {
        return window.isCloseRequested();
    }

    public int getSamplerSlot(String samplerName)
    {
        return samplerMap.get(samplerName);
    }


    public void dispose() {
        window.dispose();
    }

    public Window getWindow() {
        return window;
    }
}
