package top.lanscarlos.vulpecula.bacikal

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.configuration.ConfigNode
import taboolib.module.kether.ScriptActionParser
import top.lanscarlos.vulpecula.bacikal.parser.BacikalContext
import top.lanscarlos.vulpecula.bacikal.parser.BacikalFruit
import top.lanscarlos.vulpecula.bacikal.parser.DefaultContext
import top.lanscarlos.vulpecula.bacikal.quest.*
import top.lanscarlos.vulpecula.config.DynamicConfig
import java.io.File
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

/**
 * Vulpecula
 * top.lanscarlos.vulpecula.bacikal
 *
 * @author Lanscarlos
 * @since 2023-08-20 21:29
 */
object Bacikal {

    @ConfigNode("bacikal.analysis-symbol-closure")
    var analysisSymbolClosure: Boolean = true

    lateinit var service: BacikalService

    @Awake(LifeCycle.LOAD)
    private fun init() {
        service = DefaultBacikalService
    }

    /**
     * 注册语句解析器
     * */
    fun <T> registerParser(func: BacikalContext.() -> BacikalFruit<T>): ScriptActionParser<T> {
        return ScriptActionParser {
            val context = DefaultContext(this)
            func(context)
        }
    }

    fun buildQuest(name: String, func: Consumer<BacikalQuestBuilder>): BacikalQuest {
        return service.buildQuest(name, func)
    }

    fun buildSimpleQuest(name: String, func: Consumer<BacikalBlockBuilder>): BacikalQuest {
        return service.buildSimpleQuest(name, func)
    }

    fun executeQuest(quest: BacikalQuest): CompletableFuture<*> {
        return service.executeQuest(quest)
    }

    fun terminateQuest(quest: BacikalQuest) {
        service.terminateQuest(quest)
    }

    /**
     * 构建任务
     *
     * @param name 任务名
     * */
    fun String.toBacikalQuest(name: String): BacikalQuest {
        return buildSimpleQuest(name) {
            it.appendContent(this@toBacikalQuest)
        }
    }

    /**
     * 获取任务
     *
     * @param path 节点路径
     * @param name 任务名
     * */
    fun DynamicConfig.getBacikalQuest(
        path: String,
        name: String = this.path.toString().replace(File.separatorChar, '.')
    ): BacikalQuest {
        val builder = DefaultQuestBuilder(name)
        builder.appendBlock {
            appendContent(this@getBacikalQuest.getString(path, "null"))
        }
        builder.compiler = AnalysisQuestCompiler(this.path, this.indexOf(path))
        return builder.build()
    }
}