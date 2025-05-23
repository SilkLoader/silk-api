package de.rhm176.api.task;

import de.rhm176.api.base.Identifier;
import tasks.Reward;
import tasks.Task;
import tasks.TaskRequirement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskBuilder {
    private boolean repeatable = false;
    private final List<TaskRequirement> requirements;
    private final List<Reward> rewards;

    private final String name;
    private final String description;

    private final Identifier id;

    public TaskBuilder(Identifier id, String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;

        this.requirements = new ArrayList<>();
        this.rewards = new ArrayList<>();
    }

    public TaskBuilder repeatable() {
        repeatable = true;
        return this;
    }

    public TaskBuilder requirement(TaskRequirement requirement) {
        requirements.add(requirement);
        return this;
    }

    public TaskBuilder requirements(TaskRequirement... requirements) {
        Collections.addAll(this.requirements, requirements);
        return this;
    }

    public TaskBuilder reward(Reward reward) {
        rewards.add(reward);
        return this;
    }

    public TaskBuilder rewards(Reward... rewards) {
        Collections.addAll(this.rewards, rewards);
        return this;
    }

    public Task register() {
        return SilkTask.REGISTRY.register(id, build());
    }

    public Task build() {
        return new Task(0, repeatable, name, description, requirements, rewards);
    }
}
