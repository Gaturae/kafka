package com.hd.service;

import net.sf.json.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="reducer")
public interface BaseService {

    @RequestMapping("reducePerson")
    public String reduce(@RequestBody JSONObject data);

}
