package me.xiba.composing

/**
 * @Author:liukun
 * @Date: 2020-09-10 17:45
 * @Description:
 */
class Constants {
    companion object{
        val IS_DEPENDENCY_BY_LOCAL = "isDependencyByLocal"  // 项目是否本地依赖
        val LOCAL_PROJECT_PATH = "localProjectPath"     // 项目目录的相对路径
        val PROJECT_LOCAL_URI = "projectLocalUri"       // 项目本地依赖的地址
        val PROJECT_MAVEN_URI = "projectMavenUri"       // 项目Maven依赖的地址
    }
}