package org.noah.config;

import feign.Contract;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.noah.service.FactorModelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述: feign远程调用
 *
 */
@Configuration
public class OpenFeignConfig {
    @Value("${model.host}")
    private String modelHost;

    @Value("${model.port}")
    private String modelPort;

    @Bean
    public Contract useFeignAnnotations(){
        return new Contract.Default();
    }

    @Bean
    FactorModelService getFeignService(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .options(new Request.Options(2000,5000))
                .retryer(new Retryer.Default(5000,5000,3))
                .target(FactorModelService.class,"http://" + modelHost + ":" + modelPort + "/");
    }

}
