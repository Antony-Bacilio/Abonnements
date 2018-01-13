package dao.normalisation;

import dao.interfaces.DAO;
import metier.EntityBase;

public abstract class Validation {
    public abstract boolean estValide(EntityBase entityBase);

    protected abstract boolean NormaliserEntity();

    protected abstract boolean existeDeja();

    protected boolean isEmpty(String s) {
        if (s == null || s.length() == 0) return true;
        return false;
    }


}
