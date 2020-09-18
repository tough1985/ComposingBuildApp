package me.xiba.composing

import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import java.io.File
import java.util.*


/**
 * @Author:liukun
 * @Date: 2020-09-09 18:19
 * @Description:  用于在项目的setting.gradle中添加includeBuild
 */
class ComposingSettingPlugin : Plugin<Settings> {

    override fun apply(settings: Settings) {

        println("ComposingSettingPlugin: ---------------------")

        settings.gradle.settingsEvaluated {
            // 创建一个Properties
            var composingProperty = Properties()
            // 获取项目根目录下的composing.properties文件
            var propertiesFile = File(settings.rootDir.path + "/composing.properties")
            // 如果文件存在 读取文件内容到composingProperties
            if (propertiesFile.exists()){
                composingProperty.load(propertiesFile.inputStream())
            } else {
                println("---------------------You need create composing.properties file in your root dir!---------------------")
            }
            // 遍历composingProperty的key
            composingProperty.keys.forEach {
                // 如果是开关属性
                if (it.toString().contains(Constants.IS_DEPENDENCY_BY_LOCAL)){

                    // 读取配置判断是否是本地依赖
                    var isDependencyByLocal = composingProperty.getProperty(it.toString()).toBoolean()
                    println("ComposingPlugin: 是否本地依赖: $it=$isDependencyByLocal")

                    if (isDependencyByLocal){   // 如果是本地依赖

                        // 获得项目名称
                        var projectName = it.toString().split(".")[0]
                        // 根据项目名称来获取项目目录的相对路径
                        var localProjectPath = composingProperty.getProperty("$projectName.${Constants.LOCAL_PROJECT_PATH}")

                        println("ComposingPlugin: 添加includeBuild: localProjectPath=$localProjectPath")

                        // 在setting配置中听见includeBuild
                        settings.includeBuild(localProjectPath)
                    }
                }

                println("ComposingPlugin: keys:$it and value:${composingProperty.getProperty(it.toString())}")

            }
        }
    }
}