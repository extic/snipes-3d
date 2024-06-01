package org.extremely.snipes3d.engine.core.components;

import org.extremely.snipes3d.engine.core.SceneComponent;
import org.extremely.snipes3d.engine.rendering.Material;
import org.extremely.snipes3d.engine.rendering.Mesh;
import org.extremely.snipes3d.engine.rendering.RenderingEngine;
import org.extremely.snipes3d.engine.rendering.Shader;

public class MeshRenderer extends SceneComponent {
    private final Mesh mesh;
    private final Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine) {
        shader.bind();
        shader.updateUniforms(getTransform().getTransformMatrix(), material, renderingEngine);
//        shader.updateUniforms(material, renderingEngine);
        mesh.draw();
    }
}
