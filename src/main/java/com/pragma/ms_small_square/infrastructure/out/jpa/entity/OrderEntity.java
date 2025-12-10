package com.pragma.ms_small_square.infrastructure.out.jpa.entity;

import com.pragma.ms_small_square.domain.model.enums.OrderStateEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStateEnum state;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private UserEntity user; //cliente del pedido

    private String deliveryCode;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }

    @OneToMany(
            mappedBy = "order", // 'order' es el nombre del campo en OrderDetailEntity
            cascade = CascadeType.ALL, // Propaga todas las operaciones (guardar, borrar, etc.)
            orphanRemoval = true, // Si un detalle se quita de la lista, se borra de la BD
            fetch = FetchType.LAZY
    )
    private List<OrderDetailsEntity> dishes;

    // Metodo helper para sincronizar la relación bidireccional
    public void addOrderDetail(OrderDetailsEntity orderDetail) {
        dishes.add(orderDetail);
        orderDetail.setOrder(this);
    }

}
