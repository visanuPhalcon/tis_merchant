package com.promptnow.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class UtilReflection {

    public static ArrayList<Field> getAllFieldsMember(Class<?> cl){
        ArrayList<Field> fieldArrayList = new ArrayList<Field>();

        for(Field field:cl.getDeclaredFields()){
            fieldArrayList.add(field);
        }

        return getAllFieldsMemberOfSuperClass(cl.getSuperclass(),fieldArrayList);
    }

    public static ArrayList<Field> getAllFieldsMemberOfSuperClass(Class<?> cl,ArrayList<Field> fieldArrayList ){

        if(cl.getName().equals(Object.class.getName())){
            return fieldArrayList;
        }

        for(Field field:cl.getDeclaredFields()){
            if(Modifier.isProtected(field.getModifiers())||Modifier.isPublic(field.getModifiers())) {
                fieldArrayList.add(field);
            }
        }

        return getAllFieldsMemberOfSuperClass(cl.getSuperclass(),fieldArrayList);
    }

    public static boolean isInheriteFrom(Class<?> target,Class<?> cl){
        if(target.getName().equals(Object.class.getName())){
            return false;
        }else{
            if(target.getName().equals(cl.getName())){
                return true;
            }else{
                return isInheriteFrom(target.getSuperclass(),cl);
            }
        }
    }

}
