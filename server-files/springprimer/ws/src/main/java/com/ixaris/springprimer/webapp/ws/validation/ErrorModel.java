package com.ixaris.springprimer.webapp.ws.validation;

import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 *
 * @author kyle.pullicino
 */
public class ErrorModel {
    
    public static enum ErrorType {
        FIELD,
        GLOBAL
    }
    
    private ErrorType type;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String field;
    
    private String code;
    
    public ErrorModel(final ErrorType type, final String field, final String code) {
        this.type = type;
        this.field = field;
        this.code = code;
    }

    public ErrorModel() { }
    
    public ErrorType getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getCode() {
        return code;
    }

}
