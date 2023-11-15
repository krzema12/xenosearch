#!/usr/bin/env kotlin
@file:DependsOn("io.github.typesafegithub:github-workflows-kt:1.5.0")
@file:Import("generated/actions/checkout.kt")
@file:Import("generated/actions/setup-java.kt")
@file:Import("generated/gradle/gradle-build-action.kt")
@file:Import("generated/JamesIves/github-pages-deploy-action.kt")

import io.github.typesafegithub.workflows.domain.RunnerType.UbuntuLatest
import io.github.typesafegithub.workflows.domain.triggers.PullRequest
import io.github.typesafegithub.workflows.domain.triggers.Push
import io.github.typesafegithub.workflows.dsl.expressions.expr
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
    val build = job(
        id = "build",
        runsOn = UbuntuLatest,
    ) {
        uses(action = Checkout())
        uses(action = SetupJava(
            distribution = SetupJava.Distribution.Adopt,
            javaVersion = "17",
        ))
        uses(
            name = "Build",
            action = GradleBuildAction(
                arguments = "build",
            )
        )
    }

    job(
        id = "deploy",
        runsOn = UbuntuLatest,
        needs = listOf(build),
        `if` = expr {"${github.ref} == 'refs/heads/main'"}
    ) {
        uses(action = Checkout())
        uses(action = SetupJava(
            distribution = SetupJava.Distribution.Adopt,
            javaVersion = "17",
        ))
        uses(
            name = "Prepare distribution",
            action = GradleBuildAction(
                arguments = "jsBrowserDistribution",
            )
        )
        uses(
            name = "Deploy to GitHub Pages",
            action = GithubPagesDeployAction(
                folder = "build/dist/js/productionExecutable",
            ),
        )
    }
}.writeToFile(generateActionBindings = true)
