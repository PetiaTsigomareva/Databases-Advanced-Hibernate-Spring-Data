package app.ccb.util;

import javax.validation.ConstraintViolation;
import java.util.Set;

public interface ValidationUtil {

    <E> boolean isValid(E entity);
    <O> Set<ConstraintViolation<O>> violations(O object);
}
