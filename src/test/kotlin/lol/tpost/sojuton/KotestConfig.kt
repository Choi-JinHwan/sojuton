package lol.tpost.sojuton

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.junitxml.JunitXmlReporter

class KotestConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(
        JunitXmlReporter(
            includeContainers = false, // don't write out status for all tests
            useTestPathAsName = true, // use the full test path (ie, includes parent test names)
            outputDir = "reports/xml/" // include to set output dir for maven
        )
    )
}
