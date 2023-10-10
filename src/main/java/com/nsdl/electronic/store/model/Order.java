package com.nsdl.electronic.store.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="orders")
public class Order {
	@Id
	private String orderId;
	private String orderStatus;
    private String paymentStatus;
    private int orderAmount;
    @Column(length=1000)
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate ;
    private Date deliveredDate;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @OneToMany(mappedBy= "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    List<OrderItem> orderItems =new ArrayList<>();
}
