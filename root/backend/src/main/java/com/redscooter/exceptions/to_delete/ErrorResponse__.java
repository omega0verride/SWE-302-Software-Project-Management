package com.redscooter.exceptions.to_delete;


import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class ErrorResponse__ {
    private String message;

    private String detailed_message;
    private HashMap<String, Object> details;
    private ArrayList<String> details_list;

    private boolean success = false;

    public ErrorResponse__(String message) {
        this(message, null, new ArrayList<>(), new HashMap<>());
    }

    public ErrorResponse__(String message, String detailed_message) {
        this(message, detailed_message, new ArrayList<>(), new HashMap<>());
    }

    public ErrorResponse__(String message, HashMap<String, Object> details) {
        this(message, null, new ArrayList<>(), details);
    }

    public ErrorResponse__(String message, String detailed_message, HashMap<String, Object> details) {
        this(message, detailed_message, new ArrayList<>(), details);
    }

    public ErrorResponse__(String message, ArrayList<String> details_list) {
        this(message, null, details_list, new HashMap<>());
    }

    public ErrorResponse__(String message, String detailed_message, ArrayList<String> details_list) {
        this(message, detailed_message, details_list, new HashMap<>());
    }

    public ErrorResponse__(String message, String detailed_message, ArrayList<String> details_list, HashMap<String, Object> details) {
        this.message = message;
        this.detailed_message = detailed_message;
        this.details = details;
        this.details_list = details_list;
    }

    public void addDetail(String detail) {
        details_list.add(detail);
    }

    public void putDetail(String key, Object value) {
        details.put(key, value);
    }
}