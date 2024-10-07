package com.example.devxsyncx.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "modification_tokens", nullable = false)
    private int modificationTokens;

    @Column(name = "deletion_tokens", nullable = false)
    private int deletionTokens;

    @Column(name = "last_reset", nullable = false)
    private LocalDateTime lastReset;

    public Token() {
        this.modificationTokens = 2;
        this.deletionTokens = 1;
        this.lastReset = LocalDateTime.now();
    }

    public Token(User user, int modificationTokens, int deletionTokens, LocalDateTime lastReset) {
        this.user = user;
        this.modificationTokens = modificationTokens;
        this.deletionTokens = deletionTokens;
        this.lastReset = lastReset;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getModificationTokens() {
        return modificationTokens;
    }

    public void setModificationTokens(int modificationTokens) {
        this.modificationTokens = modificationTokens;
    }

    public int getDeletionTokens() {
        return deletionTokens;
    }

    public void setDeletionTokens(int deletionTokens) {
        this.deletionTokens = deletionTokens;
    }

    public LocalDateTime getLastReset() {
        return lastReset;
    }

    public void setLastReset(LocalDateTime lastReset) {
        this.lastReset = lastReset;
    }

    public void resetTokens() {
        this.modificationTokens = 2;
        this.deletionTokens = 1;
        this.lastReset = LocalDateTime.now();
    }
}
