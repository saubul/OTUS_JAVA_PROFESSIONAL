package ru.otus;

public class Task {

    private Long id;

    private String label;

    private Status status;

    public Task() {
    }

    public Task(Long id, String label, Status status) {
        this.id = id;
        this.label = label;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ru.otus.Task{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", status=" + status +
                '}';
    }
}
