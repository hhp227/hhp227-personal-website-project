package kr.hhp227.Webapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.view.JstlView
import org.springframework.web.servlet.view.UrlBasedViewResolver
import org.springframework.web.servlet.view.tiles3.TilesConfigurer
import org.springframework.web.servlet.view.tiles3.TilesView
import org.springframework.web.servlet.view.tiles3.TilesViewResolver

@Configuration
class TilesConfiguration {
    @Bean
    fun urlBasedViewResolver() = UrlBasedViewResolver()
        .apply {
            order = 2

            setViewClass(JstlView::class.java)
            setPrefix("/WEB-INF/jsp/")
            setSuffix(".jsp")
        }

    @Bean
    fun viewResolver() = UrlBasedViewResolver()
        .apply {
            order = 1

            setViewClass(TilesView::class.java)
        }

    @Bean
    fun tilesViewResolver() = TilesViewResolver()

    @Bean
    fun tilesConfigurer() = TilesConfigurer().apply {
        setDefinitions("WEB-INF/tiles/tiles.xml")
        setCheckRefresh(true)
    }
}