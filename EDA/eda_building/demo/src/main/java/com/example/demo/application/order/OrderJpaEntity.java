package com.example.demo.application.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "goods_name")
    private String goodsName;

}
