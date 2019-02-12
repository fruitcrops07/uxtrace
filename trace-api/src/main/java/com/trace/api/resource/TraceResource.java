package com.trace.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trace.api.model.Trace;
import com.trace.api.model.TraceInfo;
import com.trace.api.service.TraceService;

@RestController
@RequestMapping("/traces")
public class TraceResource {

    @Autowired
    private TraceService traceService;

    @RequestMapping(value = "/createByTraceInfo", method = RequestMethod.POST)
    public Trace create(@RequestBody TraceInfo trace) {
        return traceService.create(trace.getTraceInfo());
    }

    @RequestMapping(value = "/createByTrace", method = RequestMethod.POST)
    public Trace create(@RequestBody Trace trace) {
        return traceService.create(trace);
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public List<Trace> getAll() {
        return traceService.findAll();
    }

    @RequestMapping(value = "/json/search", method = RequestMethod.GET)
    public Trace findByGTIN(@RequestParam(value = "code", required = true) String code) {
        return traceService.findByCode(code);
    }

    @RequestMapping(value = "/xml/search", method = RequestMethod.GET, produces = "application/xml")
    public Trace findByGTINInXml(@RequestParam(value = "code", required = true) String code) {
        return traceService.findByCode(code);
    }

}
