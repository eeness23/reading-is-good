package com.enes.readingisgood.entity;

import com.enes.readingisgood.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<OrderEntity> orders;

    @Column(name = "status", nullable = false)
    private Status status = Status.ACTIVE;

    public void decreaseStock(Integer count) {
        this.stock -= count;
    }

    public void increaseStock(Integer count) {
        this.stock += count;
    }
}
