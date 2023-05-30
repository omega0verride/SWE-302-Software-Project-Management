package com.redscooter.api;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestStreamJoin {
    public static void main(String[] args){
        String[] arr = {};
        String res = "{" + Arrays.stream(arr).map(s -> "\"" + s + "\"")
                .collect(Collectors.joining(", ")) + "}";
        System.out.println(res);
    }
}
