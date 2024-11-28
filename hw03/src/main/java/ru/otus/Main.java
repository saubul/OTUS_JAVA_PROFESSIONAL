package ru.otus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<Task> taskList = Stream.generate(TaskGenerator::generateTask).limit(10).toList();

        System.out.println("Сгенерированный список задач");
        taskList.forEach(System.out::println);
        System.out.println();

        System.out.println("Список задач, отфильтрованных по статусу CREATED (Создан)");
        findTasksByStatus(taskList, Status.CREATED).forEach(System.out::println);
        System.out.println();

        System.out.println("Проверка существования задач с идентификатором 1: " + checkTaskExistById(taskList, 1L));
        System.out.println("Проверка существования задач с идентификатором 100: " + checkTaskExistById(taskList, 100L));
        System.out.println();

        System.out.println("Список задач, отсортированный по статусу");
        sortTasksByStatus(taskList).forEach(System.out::println);
        System.out.println();

        System.out.println("Количество задач со статусом COMPLETED (Исполнены): " + countTasksByStatus(taskList, Status.COMPLETED));
    }

    private static List<Task> findTasksByStatus(List<Task> tasks, Status status) {
        return tasks.stream().filter(task -> task.getStatus() == status).toList();
    }

    private static List<Task> sortTasksByStatus(List<Task> tasks) {
        return tasks.stream().sorted(Comparator.comparingInt(task -> task.getStatus().getValue())).toList();
    }

    private static boolean checkTaskExistById(List<Task> tasks, Long id) {
        return tasks.stream().anyMatch(task -> task.getId().equals(id));
    }

    private static Long countTasksByStatus(List<Task> tasks, Status status) {
        return tasks.stream().filter(task -> task.getStatus() == status).count();
    }

}
