package com.redscooter.util.DynamicQueryBuilder.DynamicSortBuilder.annotations;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

// this class is used to keep track of the fields declared as SortableField
@Getter
public class SortableFieldDetails implements Cloneable {
    private String fieldName;
    private String apiName;
    private String uniqueAPIName;
    private final List<String> persistencePaths = new ArrayList<>();
    private String persistencePath;

    private String joinTable;

    public SortableFieldDetails(String fieldName, String apiName, List<String> persistencePaths) {
        this(fieldName, apiName, persistencePaths, null);
    }

    public SortableFieldDetails(String fieldName, String apiName, String... persistencePaths) {
        this(fieldName, apiName, Arrays.stream(persistencePaths).toList(), null);
    }

    public SortableFieldDetails(String fieldName, String apiName, List<String> persistencePaths, String joinTable) {
        setFieldName(fieldName);
        setApiName(fieldName);
        setPersistencePaths(persistencePaths);
        this.joinTable = joinTable;
    }

    public SortableFieldDetails() {
    }

    protected void setFieldName(String name) {
//        if (name == null || name.trim().length() == 0)
//            throw new DynamicQuerySortException("fieldName cannot be empty or null!");
        this.fieldName = name;
    }

    protected void setApiName(String apiName) {
//        if (apiName == null || apiName.trim().length() == 0)
//            throw new DynamicQuerySortException("apiName cannot be empty or null!");
        this.apiName = apiName;
        this.uniqueAPIName = apiName;
    }

    public void setPersistencePaths(List<String> persistencePaths) {
        if (persistencePaths != null) {
            this.persistencePaths.clear();
            this.persistencePaths.addAll(persistencePaths);
            buildPersistencePathCache();
        }
    }

    public void addPersistencePath(String persistencePath) {
        addPersistencePath(persistencePath, null);
    }

    public void addPersistencePath(String persistencePath, String joinTableClass) {
        persistencePaths.add(persistencePath);
        if (joinTableClass != null)
            this.joinTable = joinTableClass;
        buildPersistencePathCache();
    }

    public void popPersistencePath() {
        persistencePaths.remove(persistencePaths.size() - 1);
        buildPersistencePathCache();
    }

    public void buildPersistencePathCache() {
        persistencePath = persistencePaths.stream().reduce((s1, s2) -> {
            return s1 + "." + s2;
        }).orElse("");
    }

    public String getPersistencePathAsString() {
        return persistencePath;
    }

    public String buildSortableFieldDetails(String path, SortableField sortableFieldAnnotation, Hashtable<String, SortableFieldDetails> sortableFields) {
        setFieldName(path);
        setApiName(path);
        if (sortableFieldAnnotation.persistenceName() != null && sortableFieldAnnotation.persistenceName().trim().length() != 0)
            addPersistencePath(sortableFieldAnnotation.persistenceName());
        else
            addPersistencePath(path);
        if (sortableFieldAnnotation.apiName() != null && sortableFieldAnnotation.apiName().trim().length() != 0)
            setApiName(sortableFieldAnnotation.apiName());
        return evaluateUniqueAPIName(sortableFields);
    }

    @Override
    public SortableFieldDetails clone() {
        SortableFieldDetails sortableFieldDetails = new SortableFieldDetails();
        sortableFieldDetails.setPersistencePaths(getPersistencePaths());
        sortableFieldDetails.setFieldName(getFieldName());
        sortableFieldDetails.setApiName(getApiName());
        sortableFieldDetails.buildPersistencePathCache();
        sortableFieldDetails.uniqueAPIName = uniqueAPIName;
        sortableFieldDetails.joinTable = joinTable;
        return sortableFieldDetails;
    }

    public String evaluateUniqueAPIName(Hashtable<String, SortableFieldDetails> sortableFieldDetails) {
        return evaluateUniqueAPIName(sortableFieldDetails, getApiName(), 0);
    }

    protected String evaluateUniqueAPIName(Hashtable<String, SortableFieldDetails> sortableFieldDetails, String apiName_, int persistencePathsIndex) {
        if (sortableFieldDetails.containsKey(apiName_)) {
            if (persistencePathsIndex < persistencePaths.size() - 1) {
                List<String> parts = new ArrayList<>(persistencePaths.subList(0, persistencePathsIndex + 1));
                parts.add(apiName_);
                apiName_ = parts.stream().reduce(((p1, p2) -> {
                    return p1 + "." + p2;
                })).orElse(apiName_);
                persistencePathsIndex++;
            } else {
                apiName_ = apiName_ + "_"; // if somehow we end up with duplicate persistencePaths, add underscores until resolved
            }
            return evaluateUniqueAPIName(sortableFieldDetails, apiName_, persistencePathsIndex);
        }
        this.uniqueAPIName = apiName_;
        return apiName_;
    }

    public boolean isJoin() {
        return joinTable != null;
    }
}
