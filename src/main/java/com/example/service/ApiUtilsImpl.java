package com.example.service;

import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * Created by ryan on 3/31/17.
 */
@Service
public class ApiUtilsImpl implements ApiUtils {

    @Override
    public void merge(Object obj, Object update) {
        if(!obj.getClass().isAssignableFrom(update.getClass())){
            return;
        }

        Method[] methods = obj.getClass().getMethods();

        for(Method fromMethod: methods){
            if(fromMethod.getDeclaringClass().equals(obj.getClass())
                    && fromMethod.getName().startsWith("get")){

                String fromName = fromMethod.getName();
                String toName = fromName.replace("get", "set");

                try {
                    Method toMetod = obj.getClass().getMethod(toName, fromMethod.getReturnType());
                    Object value = fromMethod.invoke(update, (Object[])null);
                    if(value != null){
                        toMetod.invoke(obj, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


}


