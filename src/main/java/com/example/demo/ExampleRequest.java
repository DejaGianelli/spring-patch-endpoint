package com.example.demo;

import jakarta.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

public class ExampleRequest implements PatchRequest {

    private final Set<String> informedFields = new HashSet<>();

    @NotEmpty
    private String foo;

    @NotEmpty
    private String bar;

    public void setFoo(String foo) {
        this.informedFields.add("foo");
        this.foo = foo;
    }

    public void setBar(String bar) {
        this.informedFields.add("bar");
        this.bar = bar;
    }

    @Override
    public boolean isInformed(String field) {
        return informedFields.contains(field);
    }

    public String getFoo() {
        return foo;
    }

    public String getBar() {
        return bar;
    }
}
