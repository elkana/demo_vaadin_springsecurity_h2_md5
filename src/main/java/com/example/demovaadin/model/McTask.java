package com.example.demovaadin.model;

import java.util.Date;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.example.demovaadin.common.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class McTask {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private TaskEnum flag;
    private Boolean done;

    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date dueDate;

    private String createdBy;
    @CreatedDate
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date createdDate;

    @Transient
    public boolean isDone() {
        return done != null && done.booleanValue();
    }
}
