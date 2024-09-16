package testRunner;

import annotation.AfterSuite;
import annotation.BeforeSuite;
import annotation.Disabled;
import annotation.Test;
import exception.OtusException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {

    public static void run(Class<?> testSuiteClass) {
        if (testSuiteClass.isAnnotationPresent(Disabled.class)) {
            System.out.println("Класс " + testSuiteClass.getSimpleName() + " отключен по причине: " + testSuiteClass.getAnnotation(Disabled.class).reason());
            return;
        }
        // Приоритеты от 0 до 11. 11 - @BeforeSuite, 0 - @AfterSuite, 1-10 - @Test
        Map<Integer, List<Method>> priorityToMethodListMap = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            priorityToMethodListMap.put(i, new ArrayList<>());
        }
        // Не getDeclaredMethods, поскольку нам не нужны методы родителя
        Method[] classMethods = testSuiteClass.getDeclaredMethods();
        int classMethodsCount = classMethods.length;
        int successTestMethodsCount = 0;
        int failTestMethodsCount = 0;
        int disabledTestMethodsCount = 0;

        int beforeSuiteMethodsCount = 0;
        int afterSuiteMethodsCount = 0;
        for (Method classMethod : classMethods) {
            if (classMethod.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethodsCount == 1) {
                    throw new OtusException("У класса может быть не более одного метода, помеченного аннотацией @BeforeSuite");
                }
                beforeSuiteMethodsCount++;
            }
            if (classMethod.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethodsCount == 1) {
                    throw new OtusException("У класса может быть не более одного метода, помеченного аннотацией @AfterSuite");
                }
                afterSuiteMethodsCount++;
            }
        }

        for (Method classMethod : classMethods) {
            // Проверяем все "НАШИ" аннотации, поскольку у метода могут быть и "ЧУЖИЕ" аннотации (не только Test, BeforeSuite, AfterSuite)
            int hasTestAnnotation = classMethod.isAnnotationPresent(Test.class) ? 1 : 0;
            int hasBeforeSuiteAnnotation = classMethod.isAnnotationPresent(BeforeSuite.class) ? 1 : 0;
            int hasAfterSuiteAnnotation = classMethod.isAnnotationPresent(AfterSuite.class) ? 1 : 0;
            if (hasTestAnnotation + hasBeforeSuiteAnnotation + hasAfterSuiteAnnotation > 1) {
                System.out.println("Тест метода " + classMethod.getName() + " будет пропущен: у метода  найдено более одной тестовой аннотации");
            } else {
                if (classMethod.isAnnotationPresent(Disabled.class)) {
                    disabledTestMethodsCount++;
                    System.out.println("Тест метода " + classMethod.getName() + " будет пропущен. Причина: " + classMethod.getAnnotation(Disabled.class).reason());
                }
                if (hasBeforeSuiteAnnotation == 1) {
                    priorityToMethodListMap.get(11).add(classMethod);
                } else if (hasAfterSuiteAnnotation == 1) {
                    priorityToMethodListMap.get(0).add(classMethod);
                } else if (hasTestAnnotation == 1){
                    int classMethodPriority = classMethod.getAnnotation(Test.class).priority();
                    if (classMethodPriority < 1 || classMethodPriority > 10) {
                        System.out.println("Тест метода " + classMethod.getName() + " будет пропущен. Приоритет метода не может быть меньше 1 или больше 10");
                    } else {
                        priorityToMethodListMap.get(classMethodPriority).add(classMethod);
                    }
                }
            }
        }

        System.out.println("\nВЫПОЛНЕНИЕ ТЕСТОВ: НАЧАЛО\n");
        for (int i = 11; i >= 0; i--) {
            List<Method> testMethods = priorityToMethodListMap.get(i);
            for (Method testMethod : testMethods) {
                try {
                    testMethod.invoke(null);
                    successTestMethodsCount++;
                } catch (Exception e) {
                    failTestMethodsCount++;
                }
            }
        }
        System.out.println("\nВЫПОЛНЕНИЕ ТЕСТОВ: КОНЕЦ\n");

        System.out.println("Количество методов класса " + testSuiteClass.getSimpleName() + ": " + classMethodsCount);
        System.out.println("Количество методов, успешно прошедших тесты: " + successTestMethodsCount);
        System.out.println("Количество методов, упавших во время тестов: " + failTestMethodsCount);;
        System.out.println("Количество методов, для которых отключен тест: " + disabledTestMethodsCount);

    }

}

