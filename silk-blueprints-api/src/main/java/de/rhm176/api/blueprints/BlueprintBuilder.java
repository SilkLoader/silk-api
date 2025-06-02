/*
 * Copyright 2025 Silk Loader
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rhm176.api.blueprints;

import blueprints.Blueprint;
import blueprints.SubBlueprint;
import classification.Classifier;
import componentArchitecture.ComponentBlueprint;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.base.util.ObjLoader;
import de.rhm176.api.blueprints.mixin.BlueprintAccessor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BlueprintBuilder {
    private boolean randomizeModel = false;
    private boolean isAlwaysVisible = false;

    private final String classification;

    private final List<ComponentBlueprint> components = new ArrayList<>();

    private final Identifier id;
    private final List<Identifier> models = new ArrayList<>();

    private boolean canBeUnderwater = false;
    private boolean canBeOverwater = true;
    private float waterOffset = 0;

    public BlueprintBuilder(Identifier id, String classification) {
        this.id = id;
        this.classification = classification;
    }

    public BlueprintBuilder randomizeModel() {
        randomizeModel = true;
        return this;
    }

    public BlueprintBuilder alwaysVisible() {
        isAlwaysVisible = true;
        return this;
    }

    public BlueprintBuilder component(ComponentBlueprint component) {
        components.add(component);
        return this;
    }

    public BlueprintBuilder components(ComponentBlueprint... components) {
        Collections.addAll(this.components, components);
        return this;
    }

    public BlueprintBuilder model(Identifier modelPath) {
        models.add(modelPath);
        return this;
    }

    public BlueprintBuilder models(Identifier... modelPaths) {
        Collections.addAll(models, modelPaths);
        return this;
    }

    public BlueprintBuilder waterRequirement(boolean canBeUnderwater, boolean canBeOverwater) {
        return waterRequirement(canBeUnderwater, canBeOverwater, 0);
    }

    public BlueprintBuilder waterRequirement(boolean canBeUnderwater, boolean canBeOverwater, float offset) {
        this.canBeUnderwater = canBeUnderwater;
        this.canBeOverwater = canBeOverwater;
        this.waterOffset = offset;
        return this;
    }

    public Blueprint register() {
        return SilkBlueprint.REGISTRY.register(id, build());
    }

    public Blueprint build() {
        Blueprint blueprint = BlueprintAccessor.create(0);
        blueprint.setRandomizeModelStages(randomizeModel);
        blueprint.alwaysVisible = isAlwaysVisible;
        ((BlueprintAccessor) blueprint).callSetClassification(Classifier.getClassification(classification));
        ((BlueprintAccessor) blueprint).callSetWaterRequirements(canBeUnderwater, canBeOverwater, waterOffset);

        List<SubBlueprint> subBlueprints = new ArrayList<>();
        List<Identifier> modelPaths = models.isEmpty() ? List.of(id) : models;

        for (Identifier modelPath : modelPaths) {
            Optional<Path> objFile = BlueprintsMain.CONTAINER.findPath(
                    "assets/" + modelPath.getNamespace() + "/models/" + modelPath.getPath() + ".obj");
            Optional<Path> mtlFile = BlueprintsMain.CONTAINER.findPath(
                    "assets/" + modelPath.getNamespace() + "/models/" + modelPath.getPath() + ".mtl");

            if (objFile.isEmpty()) {
                throw new RuntimeException(new FileNotFoundException("Blueprint \"" + id + "\" has no \".obj\" file."));
            }
            if (mtlFile.isEmpty()) {
                throw new RuntimeException(new FileNotFoundException("Blueprint \"" + id + "\" has no \".mtl\" file."));
            }

            try {
                subBlueprints.add(ObjLoader.convertToSubBlueprint(
                        Files.newInputStream(objFile.get()),
                        objFile.get().getFileName().toString(),
                        Files.newInputStream(mtlFile.get()),
                        mtlFile.get().getFileName().toString(),
                        1,
                        1));
            } catch (IOException e) {
                throw new RuntimeException("Could not load model for blueprint \"" + id + "\".", e);
            }
        }

        for (int i = 0; i < subBlueprints.size(); i++) {
            SubBlueprint nextStage = i < subBlueprints.size() - 1 ? subBlueprints.get(i + 1) : null;
            subBlueprints.get(i).calculateGrowths(i == 0, nextStage);
        }

        components.forEach(blueprint::addComponent);

        ((BlueprintAccessor) blueprint).callSetSubBlueprints(subBlueprints);
        ((BlueprintAccessor) blueprint).callIndicateLoaded();

        return blueprint;
    }
}
