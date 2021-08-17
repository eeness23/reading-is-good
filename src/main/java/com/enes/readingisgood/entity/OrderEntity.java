package com.enes.readingisgood.entity;

import com.enes.readingisgood.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private UserEntity customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private BookEntity book;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.PURCHASED;

    private BigDecimal calculateTotalPrice() {
        BigDecimal bookPrice = book.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(this.quantity);
        return bookPrice.multiply(quantity);
    }

    public boolean isCancelableByUser(UserEntity currentUser) {
        return currentUser.getId().equals(this.getId())
                || currentUser.getRoles().stream().anyMatch(roleEntity -> roleEntity.getName().equals("ROLE_ADMIN"));
    }

    @PrePersist
    public void prePersist() {
        this.totalPrice = calculateTotalPrice();
    }

    @PreUpdate
    public void preUpdate() {
        this.totalPrice = calculateTotalPrice();
    }
}
