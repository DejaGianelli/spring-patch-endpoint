package com.example.demo;

import jakarta.validation.Path;
import jakarta.validation.TraversableResolver;

import java.lang.annotation.ElementType;

public class CustomTraversableResolver implements TraversableResolver {

    @Override
    public boolean isReachable(
            Object traversableObject,
            Path.Node traversableProperty,
            Class<?> rootBeanType,
            Path pathToTraversableObject,
            ElementType elementType) {

        if (traversableObject instanceof PatchRequest patchRequest) {
            return patchRequest.isInformed(traversableProperty.getName());
        }

        return true;
    }

    @Override
    public boolean isCascadable(
            Object traversableObject,
            Path.Node traversableProperty,
            Class<?> rootBeanType,
            Path pathToTraversableObject,
            ElementType elementType) {

        if (traversableObject instanceof PatchRequest patchRequest) {
            return patchRequest.isInformed(traversableProperty.getName());
        }

        return true;
    }
}