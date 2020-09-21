package me.xiba.composing

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @Author:liukun
 * @Date: 2020-09-21 10:56
 * @Description: ComposingProjectPlugin的配置参数，在build.gradle中使用
 */
open class DependencyProjectConfigExtension {

    // 动态配置，接收依赖项目的名称
    var dependencyProjectConfig: NamedDomainObjectContainer<DependencyProjectConfig>

    constructor(project: Project){

        // 通过project创建NamedDomainObjectContainer对象
        dependencyProjectConfig = project.container(DependencyProjectConfig::class.java)
    }
}