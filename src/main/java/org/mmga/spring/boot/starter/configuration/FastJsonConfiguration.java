package org.mmga.spring.boot.starter.configuration;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import lombok.RequiredArgsConstructor;
import org.mmga.spring.boot.starter.properties.AuthorizationConfigurationProperties;
import org.mmga.spring.boot.starter.properties.Env;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FastJsonConfiguration implements WebMvcConfigurer {
    private final AuthorizationConfigurationProperties properties;

    @Override
    public void configureMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
        FastJsonConfig fastJsonConfig = getFastJsonConfig();
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        ArrayList<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setFastJsonConfig(fastJsonConfig);
        converters.add(0, converter);
    }

    private FastJsonConfig getFastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(StandardCharsets.UTF_8);
        JSONWriter.Feature[] writerFeatures = {JSONWriter.Feature.IgnoreNonFieldGetter, JSONWriter.Feature.PrettyFormat};
        if (properties.getEnv().equals(Env.PROD)) {
            writerFeatures = new JSONWriter.Feature[]{JSONWriter.Feature.IgnoreNonFieldGetter};
        }
        fastJsonConfig.setWriterFeatures(writerFeatures);
        return fastJsonConfig;
    }
}