package metier;

import java.lang.reflect.Method;

public class MappingProperty {
    private String property;
    private String colName;
    private Class javaType;
    private Boolean idProperty;

    public MappingProperty(String property, String colName, Class javaType) {
        this.property = property;
        this.colName = colName;
        this.javaType = javaType;
        this.idProperty = false;
    }

    public Boolean getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(Boolean idProperty) {
        this.idProperty = idProperty;
    }

    public String getProperty() {
        return property;
    }

    public String getColName() {
        return colName;
    }

    public Class getJavaType() {
        return javaType;
    }

    public Method methodSetProperty(Class c) throws NoSuchMethodException {
        return c.getMethod("set" + nameGetProperty(property), javaType);
    }

    public Method methodGetProperty(Class clazz) throws NoSuchMethodException {
        return clazz.getMethod("get" + nameGetProperty(property));
    }

    public String nameGetProperty(String property){//1ere lettre en MAJ
        String lettre = property.substring(0,1);
        return lettre.toUpperCase() + property.substring(1);

    }

}
