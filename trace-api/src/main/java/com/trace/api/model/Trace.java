package com.trace.api.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "traceInfo")
public class Trace {

    @NotNull(message = "{trace.gtin.notNull}")
    @Size(min = 13, max = 13, message = "{trace.gtin.size}")
    private String gtin;

    @NotNull(message = "{trace.batchOrLot.notNull}")
    @Size(min = 20, max = 20, message = "{trace.batchOrLot.size}")
    private String batchOrLot;

    @NotNull(message = "{trace.species.notNull}")
    @Size(min = 3, max = 3, message = "{trace.species.size}")
    private String species;

    @NotNull(message = "{trace.production.notNull}")
    @Size(min = 2, max = 2, message = "{trace.production.size}")
    private String production;

    @NotNull(message = "trace.byCatch.notNull")
    private boolean byCatch;

}
