package io.github.thang86.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
*  StoreProduct.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-12    ThangTX     Create
*/


@Entity
@Indexed
public class StoreProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	private float price;

	@Field
	private String name;

	@Field
	@Column(length = 1000)
	private String description;

	@ManyToOne
	@IndexedEmbedded
	@JsonBackReference
	private Product product;

	@ManyToOne
	@IndexedEmbedded
	@JsonBackReference
	private Store store;

	@JsonBackReference
	@OneToMany(mappedBy = "storeProduct", cascade = CascadeType.ALL)
	private List<Order> orders;


	@Column(name = "views", nullable = false, unique = false)
	private int storeViews;

	public StoreProduct() {
		storeViews=0;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public int getStoreViews() {
		return storeViews;
	}

	public void setStoreViews(int storeViews) {
		this.storeViews = storeViews;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
		public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;
				StoreProduct that = (StoreProduct) o;
				return Objects.equals(id, that.id);
			}
}
