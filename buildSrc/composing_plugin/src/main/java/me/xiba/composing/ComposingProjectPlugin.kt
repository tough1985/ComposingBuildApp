package me.xiba.composing

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.util.*

/**
 * @Author:liukun
 * @Date: 2020-09-10 16:23
 * @Description: 用于在项目的build.gradle中动态添加dependencies
 */
class ComposingProjectPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("ComposingProjectPlugin: ---------------------")

        var composingProjectExtension = project.extensions.create("ComposingDependency", DependencyProjectConfigExtension::class.java, project)

        // 创建一个Properties
        var composingProperty = Properties()
        // 获取项目根目录下的composing.properties文件
        var propertiesFile = File(project.rootDir.path + "/composing.properties")
        // 如果文件存在 读取文件内容到composingProperties
        if (propertiesFile.exists()){
            composingProperty.load(propertiesFile.inputStream())
        } else {
            println("You need create composing.properties file in your root dir!")
        }

        project.afterEvaluate {

            var appExtension = project.extensions.getByType(AppExtension::class.java)
            println("appExtension = $appExtension" )

            // 获取当前项目配置的依赖项目名称
            composingProjectExtension.dependencyProjectConfig.all {
                println("ComposingProjectPlugin: name:" + it.name)

                var dependencyProjectName = it.name

                var isDependencyByLocal = composingProperty.getProperty("$dependencyProjectName.${Constants.IS_DEPENDENCY_BY_LOCAL}")

                println("ComposingProjectPlugin: 是否本地依赖: isDependencyByLocal=$isDependencyByLocal")

                var projectUri: String

                if (isDependencyByLocal.toBoolean()){   // 如果是本地依赖

                    projectUri = composingProperty.getProperty("$dependencyProjectName.${Constants.PROJECT_LOCAL_URI}")

                    println("ComposingProjectPlugin: 本地依赖: projectUri=$projectUri")

                } else {    // 如果是maven依赖

                    projectUri = composingProperty.getProperty("$dependencyProjectName.${Constants.PROJECT_MAVEN_URI}")

                    println("ComposingProjectPlugin: maven依赖: projectUri=$projectUri")

                }
                project.dependencies.add("implementation", project.dependencies.create(projectUri))
            }

        }
    }
}