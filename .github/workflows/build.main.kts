#!/usr/bin/env kotlin
@file:DependsOn("io.github.typesafegithub:github-workflows-kt:1.5.0")
@file:Import("generated/actions/checkout.kt")
@file:Import("generated/actions/setup-java.kt")
@file:Import("generated/gradle/gradle-build-action.kt")

import io.github.typesafegithub.workflows.domain.RunnerType.UbuntuLatest
import io.github.typesafegithub.workflows.domain.triggers.PullRequest
import io.github.typesafegithub.workflows.domain.triggers.Push
import io.github.typesafegithub.workflows.dsl.workflow
import io.github.typesafegithub.workflows.yaml.writeToFile

workflow(
    name = "Build",
    on = listOf(
        Push(branches = listOf("main")),
        PullRequest(),
    ),
    sourceFile = __FILE__.toPath(),
) {
    job(
        id = "build",
        runsOn = UbuntuLatest,
    ) {
        uses(action = Checkout())
        uses(action = SetupJava(
            distribution = SetupJava.Distribution.Adopt,
            javaVersion = "21",
        ))
        uses(
            name = "Build",
            action = GradleBuildAction(
                arguments = "build",
            )
        )
    }
}.writeToFile(generateActionBindings = true)
