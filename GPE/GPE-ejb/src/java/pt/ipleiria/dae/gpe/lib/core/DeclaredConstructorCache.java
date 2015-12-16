/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.dae.gpe.lib.core;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author joeld
 */
public class DeclaredConstructorCache {

    public static final DeclaredConstructorCache CACHE = new DeclaredConstructorCache();

    private final Map<Class<? extends AbstractDTO>, Constructor<? extends AbstractDTO>> declaredConstructors;

    private DeclaredConstructorCache() {
        this.declaredConstructors = new HashMap<>();
    }

    public Constructor<? extends AbstractDTO> getDeclaredConstructor(Class<? extends AbstractDTO> dtoClass, Class<? extends AbstractEntity> entityClass) throws NoSuchMethodException {
        Constructor<? extends AbstractDTO> declaredConstructor = declaredConstructors.get(dtoClass);
        if (declaredConstructor == null) {
            declaredConstructor = dtoClass.getDeclaredConstructor(entityClass);
            declaredConstructors.put(dtoClass, declaredConstructor);
        }
        return declaredConstructor;
    }
}
