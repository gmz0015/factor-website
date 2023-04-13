package org.noah.service;


import feign.RequestLine;
import feign.Response;
import org.noah.entity.ModelRequestEntity;
import org.noah.entity.ModelResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface FactorModelService {
    @RequestLine("POST /get_score")
    ModelResponseEntity getScore(@RequestBody ModelRequestEntity modelRequestEntity);

}
