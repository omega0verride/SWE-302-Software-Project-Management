package com.redscooter.util.DynamicQueryBuilder.DynamicSortBuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// fields annotated with this interface will be exposed as SortableField if the service considers it
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SortableField {
    String apiName() default "";

    String persistenceName() default "";
}
