package kr.hhp227.Webapp.config

import org.mybatis.spring.annotation.MapperScan
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("kr.hhp227.Webapp.mapper")
class MyBatisConfiguration {
    @Bean
    fun configurationCustomizer() = ConfigurationCustomizer { configuration ->
        configuration.isMapUnderscoreToCamelCase = true
    }
}