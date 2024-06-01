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

package org.extremely.snipes3d.engine.rendering;

import org.extremely.snipes3d.engine.rendering.resources.TextureResource;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Texture {
    private static Map<String, TextureResource> loadedTextures = new HashMap<>();

    private TextureResource resource;
    private String fileName;

    public Texture(String fileName) {
        this.fileName = fileName;
        TextureResource oldResource = loadedTextures.get(fileName);

        if (oldResource != null) {
            resource = oldResource;
//            resource.AddReference();
        } else {
            resource = loadTexture(fileName);
            loadedTextures.put(fileName, resource);
        }
    }
//
//    @Override
//    protected void finalize() {
//        if (resource.RemoveReference() && !fileName.isEmpty()) {
//            loadedTextures.remove(fileName);
//        }
//    }

    public void bind() {
        bind(0);
    }

    public void bind(int samplerSlot) {
        glActiveTexture(GL_TEXTURE0 + samplerSlot);
        glBindTexture(GL_TEXTURE_2D, resource.getId());
    }

    public int getID() {
        return resource.getId();
    }

    private static TextureResource loadTexture(String fileName) {
        try (var it = MemoryStack.stackPush()) {
            var width = it.mallocInt(1);
            var height = it.mallocInt(1);
            var channels = it.mallocInt(1);

            var buffer = STBImage.stbi_load("./res/textures/" + fileName, width, height, channels, 4);
            if (buffer == null) {
                throw new RuntimeException("Image file " + fileName + " not loaded " + STBImage.stbi_failure_reason());
            }

            TextureResource resource = new TextureResource();
            GL11.glBindTexture(GL_TEXTURE_2D, resource.getId());

            GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
            GL11.glTexImage2D(
                    GL11.GL_TEXTURE_2D,
                    0,
                    GL11.GL_RGBA,
                    width.get(),
                    height.get(),
                    0,
                    GL11.GL_RGBA,
                    GL11.GL_UNSIGNED_BYTE,
                    buffer
            );
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
            GL11.glTexParameterf(GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
            float amount = Math.min(4f, GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT));
            GL11.glTexParameterf(GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
            STBImage.stbi_image_free(buffer);

            return resource;
        }
    }
}
