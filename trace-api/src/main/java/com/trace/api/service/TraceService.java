package com.trace.api.service;

import java.util.List;

import com.trace.api.model.Trace;

public interface TraceService {

    List<Trace> findAll();

    Trace create(Trace trace);

    Trace findByCode(String code);
}
