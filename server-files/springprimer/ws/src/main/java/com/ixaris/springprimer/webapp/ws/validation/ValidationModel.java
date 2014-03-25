package com.ixaris.springprimer.webapp.ws.validation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 *
 * @author <a href="mailto:kyle.pullicino@ixaris.com">Kyle</a>
 */
public class ValidationModel {
    
    public static ValidationModel prepare(final Errors errors) {
        
        final List<ErrorModel> globalErrorsList = new LinkedList<>();
        for (final ObjectError error : errors.getGlobalErrors()) {
            globalErrorsList.add(new ErrorModel(ErrorModel.ErrorType.GLOBAL, null, error.getCode()));
        }
        final Map<String, List<ErrorModel>> fieldErrorsMap = new HashMap<>();
        for (final FieldError error : errors.getFieldErrors()) {
            if (!fieldErrorsMap.containsKey(error.getField())) {
                fieldErrorsMap.put(error.getField(), new LinkedList<ErrorModel>());
            }
            fieldErrorsMap.get(error.getField()).add(new ErrorModel(ErrorModel.ErrorType.FIELD, error.getField(), error.getCode()));
        }
        
        return new ValidationModel(fieldErrorsMap, globalErrorsList);
    }
 
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    private Map<String, List<ErrorModel>> field;
    
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    private List<ErrorModel> global;

    public ValidationModel(final Map<String, List<ErrorModel>> field, 
                           final List<ErrorModel> global) {
        
        this.field = field;
        this.global = global;
    }
    
    public Map<String, List<ErrorModel>> getField() {
        return field;
    }

    public void setField(Map<String, List<ErrorModel>> field) {
        this.field = field;
    }

    public List<ErrorModel> getGlobal() {
        return global;
    }

    public void setGlobal(List<ErrorModel> global) {
        this.global = global;
    }
    
}
