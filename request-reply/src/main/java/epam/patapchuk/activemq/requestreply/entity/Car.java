package epam.patapchuk.activemq.requestreply.entity;

import java.io.Serializable;

public record Car(Integer id, String name) implements Serializable {
}

