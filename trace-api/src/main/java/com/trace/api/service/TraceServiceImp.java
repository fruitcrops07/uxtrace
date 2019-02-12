package com.trace.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trace.api.model.Trace;
import com.trace.api.service.util.DataSourceHelper;

@Service
public class TraceServiceImp implements TraceService {

    private static final Logger logger = LoggerFactory.getLogger(TraceServiceImp.class);

    @Autowired
    private DataSourceHelper dsHelper;

    @Override
    public List<Trace> findAll() {
        logger.info("action=findAll, message=find_all_trace");
        return dsHelper.findAll();
    }

    @Override
    public Trace create(Trace trace) {
        logger.info("action=create, message=creating_new_trace_record");
        return dsHelper.create(trace);
    }

    @Override
    public Trace findByCode(String code) {
        logger.info("action=findByCode, message=finding_trace_record_by_code, code={}", code);
        return dsHelper.findByCode(code);
    }

}
