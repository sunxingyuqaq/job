package com.boot.job.db.monitor.controller;


import com.boot.job.db.common.entity.FebsResponse;
import com.boot.job.db.common.exception.FebsException;
import com.boot.job.db.common.utils.DateUtil;
import com.boot.job.db.monitor.endpoint.FebsHttpTraceEndpoint;
import com.boot.job.db.monitor.entity.FebsHttpTrace;
import com.boot.job.db.monitor.helper.FebsActuatorHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author MrBird
 */
@Slf4j
@RestController
@RequestMapping("febs/actuator")
public class FebsActuatorController {

    @Autowired
    private FebsHttpTraceEndpoint httpTraceEndpoint;
    @Autowired
    private FebsActuatorHelper actuatorHelper;

    @GetMapping("httptrace")
    @RequiresPermissions("httptrace:view")
    public FebsResponse httpTraces(String method, String url) throws FebsException {
        try {
            FebsHttpTraceEndpoint.FebsHttpTraceDescriptor traces = httpTraceEndpoint.traces();
            List<HttpTrace> httpTraceList = traces.getTraces();
            List<FebsHttpTrace> febsHttpTraces = new ArrayList<>();
            httpTraceList.forEach(t -> {
                FebsHttpTrace febsHttpTrace = new FebsHttpTrace();
                febsHttpTrace.setRequestTime(DateUtil.formatInstant(t.getTimestamp(), DateUtil.FULL_TIME_SPLIT_PATTERN));
                febsHttpTrace.setMethod(t.getRequest().getMethod());
                febsHttpTrace.setUrl(t.getRequest().getUri());
                febsHttpTrace.setStatus(t.getResponse().getStatus());
                febsHttpTrace.setTimeTaken(t.getTimeTaken());
                if (StringUtils.isNotBlank(method) && StringUtils.isNotBlank(url)) {
                    if (StringUtils.equalsIgnoreCase(method, febsHttpTrace.getMethod())
                            && StringUtils.containsIgnoreCase(febsHttpTrace.getUrl().toString(), url))
                        febsHttpTraces.add(febsHttpTrace);
                } else if (StringUtils.isNotBlank(method)) {
                    if (StringUtils.equalsIgnoreCase(method, febsHttpTrace.getMethod()))
                        febsHttpTraces.add(febsHttpTrace);
                } else if (StringUtils.isNotBlank(url)) {
                    if (StringUtils.containsIgnoreCase(febsHttpTrace.getUrl().toString(), url))
                        febsHttpTraces.add(febsHttpTrace);
                } else {
                    febsHttpTraces.add(febsHttpTrace);
                }
            });
            Map<String, Object> data = new HashMap<>();
            data.put("rows", febsHttpTraces);
            data.put("total", febsHttpTraces.size());
            return new FebsResponse().success().data(data);
        } catch (Exception e) {
            String message = "请求追踪失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
}
