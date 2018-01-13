package metier;

public interface EntityBase {

    Object getId();

    void setId(Object o);

    void cloneEntity();

    void restoreEntity();
}
