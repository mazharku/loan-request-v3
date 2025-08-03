package com.bracits.shared.domain.aggregate;

import com.bracits.shared.domain.event.DomainEvent;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Getter
public abstract class AggregateRoot {
    
    @Id
    protected String id;
    
    @Version
    protected Long version;
    
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected String createdBy;
    protected String updatedBy;

    @Transient
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    
    protected AggregateRoot() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    protected void registerEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }
    
    public List<DomainEvent> getUncommittedEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
    
    public void markEventsAsCommitted() {
        domainEvents.clear();
    }
    
    protected void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}