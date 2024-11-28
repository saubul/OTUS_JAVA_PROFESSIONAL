package testrunner;

import annotation.AfterSuite;
import annotation.BeforeSuite;
import annotation.Disabled;
import annotation.Test;
import exception.OtusException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {

    public static void run(Class<?> testSuiteClass) throws InvocationTargetException, IllegalAccessException {
        if (testSuiteClass.isAnnotationPresent(Disabled.class)) {
            System.out.println("Класс " + testSuiteClass.getSimpleName() + " отключен по причине: " + testSuiteClass.getAnnotation(Disabled.class).reason());
            return;
        }
        // Приоритеты от 1 до 10. 1-10 - @Test
        Map<Integer, List<Method>> priorityToMethodListMap = new HashMap<>();
        for (int i = 1; i < 11; i++) {
            priorityToMethodListMap.put(i, new ArrayList<>());
        }
        // Не getDeclaredMethods, поскольку нам не нужны методы родителя
        Method[] classMethods = testSuiteClass.getDeclaredMethods();
        int classMethodsWithTestAnnotationCount = classMethods.length;
        int successTestMethodsCount = 0;
        int failTestMethodsCount = 0;
        int disabledTestMethodsCount = 0;

        Method beforeSuiteMethod = null;
        Method afterSuiteMethod = null;
        for (Method classMethod : classMethods) {
            int hasTestAnnotation = classMethod.isAnnotationPresent(Test.class) ? 1 : 0;
            int hasBeforeSuiteAnnotation = classMethod.isAnnotationPresent(BeforeSuite.class) ? 1 : 0;
            int hasAfterSuiteAnnotation = classMethod.isAnnotationPresent(AfterSuite.class) ? 1 : 0;

            // Проверяем все "НАШИ" аннотации, поскольку у метода могут быть и "ЧУЖИЕ" аннотации (не только Test, BeforeSuite, AfterSuite)
            if (hasTestAnnotation + hasBeforeSuiteAnnotation + hasAfterSuiteAnnotation > 1) {
                throw new OtusException("Ошибка: метод " + classMethod.getName() + " имеет более одной тестовой аннотации");
            }

            if (hasTestAnnotation == 1) {
                int classMethodPriority = classMethod.getAnnotation(Test.class).priority();
                if (classMethodPriority < 1 || classMethodPriority > 10) {
                    throw new OtusException("Ошибка: метод " + classMethod.getName() + " имеет неверный приоритет. " +
                            "Приоритет метода не может быть меньше 1 или больше 10");
                } else {
                    priorityToMethodListMap.get(classMethodPriority).add(classMethod);
                }
            }

            if (classMethod.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeSuiteMethod != null) {
                    throw new OtusException("У класса может быть не более одного метода, помеченного аннотацией @BeforeSuite");
                }
                beforeSuiteMethod = classMethod;
                classMethodsWithTestAnnotationCount--;
            }

            if (classMethod.isAnnotationPresent(AfterSuite.class)) {
                if (afterSuiteMethod != null) {
                    throw new OtusException("У класса может быть не более одного метода, помеченного аннотацией @AfterSuite");
                }
                afterSuiteMethod = classMethod;
                classMethodsWithTestAnnotationCount--;
            }

            if (classMethod.isAnnotationPresent(Disabled.class)) {
                disabledTestMethodsCount++;
                System.out.println("Тест метода " + classMethod.getName() + " будет пропущен. Причина: " + classMethod.getAnnotation(Disabled.class).reason());
            }
        }

        System.out.println("\nВЫПОЛНЕНИЕ ТЕСТОВ: НАЧАЛО\n");
        if (beforeSuiteMethod != null) {
            beforeSuiteMethod.invoke(null);
        }
        for (int i = 10; i >= 1; i--) {
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
        if (afterSuiteMethod != null) {
            afterSuiteMethod.invoke(null);
        }
        System.out.println("\nВЫПОЛНЕНИЕ ТЕСТОВ: КОНЕЦ\n");

        System.out.println("Количество методов с аннотацией @Test класса " + testSuiteClass.getSimpleName() + ": " + classMethodsWithTestAnnotationCount);
        System.out.println("Количество методов, успешно прошедших тесты: " + successTestMethodsCount);
        System.out.println("Количество методов, упавших во время тестов: " + failTestMethodsCount);
        System.out.println("Количество методов, для которых отключен тест: " + disabledTestMethodsCount);

    }

}

