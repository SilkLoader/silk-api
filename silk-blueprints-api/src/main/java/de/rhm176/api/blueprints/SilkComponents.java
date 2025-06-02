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

import audio.Sound;
import audio.SoundEffect;
import birdHunt.BirdHuntCompBlueprint;
import breeding.BreedingCompBlueprint;
import carnivorePlant.TongueShootCompBlueprint;
import classification.Classification;
import componentArchitecture.Requirement;
import components.InformationComponent;
import de.rhm176.api.blueprints.mixin.FlingingCompBlueprintAccessor;
import de.rhm176.api.blueprints.mixin.component.*;
import de.rhm176.api.blueprints.mixin.component.death.*;
import death.*;
import effects.Effect;
import environment.EnviroCompBlueprint;
import environment.EnviroFactorBlueprint;
import equipping.ItemCompBlueprint;
import fighting.FightCompBlueprint;
import fishHunt.FishHuntCompBlueprint;
import flinging.FlingingCompBlueprint;
import flying.BirdMoveBlueprint;
import food.FoodCompBlueprint;
import food.FoodSectionBlueprint;
import growth.GrowthCompBlueprint;
import healer.HealerCompBlueprint;
import health.LifeCompBlueprint;
import hiveComponents.HiveCompBlueprint;
import hunting.HuntCompBlueprint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import loot.DropCompBlueprint;
import meerkats.TimeOutCompBlueprint;
import org.lwjgl.util.vector.Vector4f;
import particles.ParticleSystem;
import perching.PerchCompBlueprint;
import sleeping.SleepCompBlueprint;
import sound.RandomSounderBlueprint;
import sound.SoundCompBlueprint;
import stinging.StingingCompBlueprint;
import treeCharging.TreeChargeCompBlueprint;

public class SilkComponents {
    public static TongueShootCompBlueprint createTongueShoot() {
        // return new TongueShootCompBlueprint();
        return null;
    }

    public static RandomSounderBlueprint createRandomSounder(
            float waitTime, float randomExtra, List<SoundEffect> sounds, int stageReq) {
        return RandomSounderBlueprintAccessor.create(waitTime, randomExtra, sounds, stageReq);
    }

    public static FoodCompBlueprint createFood(Effect effect, FoodSectionBlueprint... sections) {
        return FoodCompBlueprintAccessor.create(sections, effect);
    }

    public static HuntCompBlueprint createHunt(
            int huntingRange, boolean huntsYoung, boolean huntsOld, Classification... prey) {
        return HuntCompBlueprintAccessor.create(huntingRange, prey, huntsYoung, huntsOld);
    }

    public static GrowthCompBlueprint.StaticGrowthCompBlueprint createStaticGrowth(
            float growthTime, int modelStages, int subStages) {
        return StaticGrowthCompBlueprintAccessor.create(growthTime, modelStages, subStages);
    }

    public static GrowthCompBlueprint.DynamicGrowthCompBlueprint createDynamicGrowth(
            float growthTime, int modelStages) {
        return DynamicGrowthCompBlueprintAccessor.create(growthTime, modelStages);
    }

    public static TreeChargeCompBlueprint createTreeCharge() {
        return TreeChargeCompBlueprintAccessor.create();
    }

    public static TimeOutCompBlueprint createTimeout(float decayTime) {
        return TimeOutCompBlueprintAccessor.create(decayTime);
    }

    public static DropCompBlueprint createDrop(int dropBlueprintId) {
        return DropCompBlueprintAccessor.create(dropBlueprintId);
    }

    public static BirdMoveBlueprint createBirdMove(float glideDown) {
        return BirdMoveBlueprintAccessor.create(glideDown);
    }

    public static FlingingCompBlueprint createFlinging(float minTime, float maxTime) {
        return FlingingCompBlueprintAccessor.create(minTime, maxTime);
    }

    public static PerchCompBlueprint createPerch(Vector4f... perchPositions) {
        return PerchCompBlueprintAccessor.create(perchPositions);
    }

    public static ItemCompBlueprint createItem(float decayTime) {
        return ItemCompBlueprintAccessor.create(decayTime);
    }

    public static SleepCompBlueprint createSleep(float startMin, float startMax, float endMin, float endMax) {
        return SleepCompBlueprintAccessor.create(startMin, startMax, endMin, endMax);
    }

    public static SoundCompBlueprint createSound() {
        return SoundCompBlueprintAccessor.create();
    }

    public static StingingCompBlueprint createStinging() {
        return StingingCompBlueprintAccessor.create();
    }

    public static FishHuntCompBlueprint createHuntFish() {
        return FishHuntCompBlueprintAccessor.create();
    }

    public static BirdHuntCompBlueprint createHuntBird() {
        return BirdHuntCompBlueprintAccessor.create();
    }

    public static FightCompBlueprint createFight(int damage, boolean takesRevenge, float biteRange, float pause) {
        return createFight(damage, takesRevenge, biteRange, pause, 0);
    }

    public static FightCompBlueprint createFight(
            int damage, boolean takesRevenge, float biteRange, float pause, int animationId) {
        return FightCompBlueprintAccessor.create(damage, takesRevenge, animationId, biteRange, pause);
    }

    public static HiveCompBlueprint createHive(int maxHoney, int startStage, int stageCount) {
        return HiveCompBlueprintAccessor.create(maxHoney, startStage, stageCount);
    }

    public static HealerCompBlueprint createHealer(DeathAiBlueprint death) {
        return HealerCompBlueprintAccessor.create(death);
    }

    public static LifeCompBlueprint createLife(
            float averagePopulation,
            float averageLifeLength,
            int defencePoints,
            float[] popFactors,
            DeathAiBlueprint deathAi,
            BreedingCompBlueprint breedBlueprint,
            EnviroCompBlueprint enviroBlueprint,
            boolean isAnimal) {
        return LifeCompBlueprintAccessor.create(
                averagePopulation,
                averageLifeLength,
                defencePoints,
                popFactors,
                deathAi,
                breedBlueprint,
                enviroBlueprint,
                isAnimal);
    }

    public static BreedingCompBlueprint createBreeding(
            int breedingCount,
            List<Requirement> requirements,
            float breedMaturity,
            float breedTimeAverage,
            boolean secret) {
        return createBreeding(-1, breedingCount, requirements, breedMaturity, breedTimeAverage, secret);
    }

    public static BreedingCompBlueprint createBreeding(
            int breedingCount, List<Requirement> requirements, float breedMaturity, float breedTimeAverage) {
        return createBreeding(-1, breedingCount, requirements, breedMaturity, breedTimeAverage, false);
    }

    public static BreedingCompBlueprint createBreeding(
            int parentId,
            int breedingCount,
            List<Requirement> requirements,
            float breedMaturity,
            float breedTimeAverage) {
        return createBreeding(parentId, breedingCount, requirements, breedMaturity, breedTimeAverage, false);
    }

    public static BreedingCompBlueprint createBreeding(
            int parentId,
            int breedingCount,
            List<Requirement> requirements,
            float breedMaturity,
            float breedTimeAverage,
            boolean secret) {
        return BreedingCompBlueprintAccessor.create(
                parentId, breedingCount, requirements, breedMaturity, breedTimeAverage, secret);
    }

    public static EnviroCompBlueprint createEnvironment(EnviroFactorBlueprint... blueprints) {
        // game requires a mutable list 'cause it sorts it for some reason
        return new EnviroCompBlueprint(new ArrayList<>(Arrays.asList(blueprints)));
    }

    public static InformationComponent.InformationCompBlueprint createInfo(
            String name,
            String description,
            boolean flipTexture,
            int dpCost,
            int baseDpPerMin,
            int roamingRange,
            Sound placementSound) {
        InformationComponent.InformationCompBlueprint blueprint = new InformationComponent.InformationCompBlueprint();
        InformationCompBlueprintAccessor accessor = (InformationCompBlueprintAccessor) blueprint;
        accessor.setName(name);
        accessor.setDescription(description);
        accessor.setFlipTexture(flipTexture);
        accessor.setDpCost(dpCost);
        accessor.setBaseDpPerMin(baseDpPerMin);
        accessor.setRoamingRange(roamingRange);
        accessor.setPlacementSound(placementSound);

        return blueprint;
    }

    @SuppressWarnings("DataFlowIssue")
    public static UpDownDeathBlueprint createUpDownDeath(float speed, ParticleSystem particles) {
        UpDownDeathBlueprint blueprint = new UpDownDeathBlueprint();
        UpDownDeathBlueprintAccessor accessor = ((UpDownDeathBlueprintAccessor) blueprint);
        accessor.setSpeed(speed);
        accessor.setParticles(particles);

        return blueprint;
    }

    public static ParticleDeathBlueprint createParticleDeath(ParticleSystem particles) {
        ParticleDeathBlueprint blueprint = new ParticleDeathBlueprint();
        ((ParticleDeathBlueprintAccessor) blueprint).setSystem(particles);

        return blueprint;
    }

    public static FallDeathBlueprint createFallDeath(float fallTime, float totalTime, float fallAngle) {
        FallDeathBlueprint blueprint = new FallDeathBlueprint();
        FallDeathBlueprintAccessor accessor = ((FallDeathBlueprintAccessor) blueprint);
        accessor.setFallTime(fallTime);
        accessor.setTotalTime(totalTime);
        accessor.setFallenAngle(fallAngle);

        return blueprint;
    }

    public static FallDeathBlueprint createFallDeath(
            float fallTime,
            float totalTime,
            float fallAngle,
            float explodeTime,
            boolean useEntityColor,
            ParticleSystem system,
            Set<Integer> modelStages) {
        FallDeathBlueprint blueprint = createFallDeath(fallTime, totalTime, fallAngle);
        FallDeathBlueprintAccessor accessor = ((FallDeathBlueprintAccessor) blueprint);
        accessor.setHasParticleEffect(true);
        accessor.setExplodeTime(explodeTime);
        accessor.setUseEntityColour(useEntityColor);
        accessor.setSystem(system);
        accessor.setParticleModelStages(modelStages);

        return blueprint;
    }

    public static FloaterDeathBlueprint createFloaterDeath(float deadRot) {
        FloaterDeathBlueprint blueprint = new FloaterDeathBlueprint();
        ((FloaterDeathBlueprintAccessor) blueprint).setDeadRot(deadRot);

        return blueprint;
    }

    public static FadeDeathBlueprint createFadeDeath(float fadeTime) {
        FadeDeathBlueprint blueprint = new FadeDeathBlueprint();
        ((FadeDeathBlueprintAccessor) blueprint).setFadeTime(fadeTime);

        return blueprint;
    }

    public static SpawnDeathBlueprint createSpawnDeath(
            int entityId, int minCount, int maxCount, boolean onlyFullyGrown) {
        SpawnDeathBlueprint blueprint = new SpawnDeathBlueprint();
        SpawnDeathBlueprintAccessor accessor = ((SpawnDeathBlueprintAccessor) blueprint);
        accessor.setEntityId(entityId);
        accessor.setMinCount(minCount);
        accessor.setMaxCount(maxCount);
        accessor.setOnlyFullGrown(onlyFullyGrown);

        return blueprint;
    }
}
