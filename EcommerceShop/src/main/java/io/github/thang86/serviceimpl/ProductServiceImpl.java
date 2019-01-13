package io.github.thang86.serviceimpl;


import io.github.thang86.repository.BrandRepository;
import io.github.thang86.repository.CompanyRepository;
import io.github.thang86.repository.ProductRepository;
import io.github.thang86.entities.PhysicalProduct;
import io.github.thang86.entities.Product;
import io.github.thang86.entities.VirtualProduct;
import io.github.thang86.forms.AddProductForm;
import io.github.thang86.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
*  ProductServiceImpl.java
* 
*  Version 1.0
*
*  Copyright
*
*  Modification Logs:
*  DATE		     AUTHOR		 DESCRIPTION
*  -------------------------------------
*  2018-12-20    ThangTX     Create
*/

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository){this.productRepository=productRepository;}

	@Override
	public Optional<Product> getProductById(Long id) {
		return Optional.ofNullable(productRepository.findOne(id));
	}

	@Override
	public Optional<Product> getProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public Optional<Product> getPriceBetween(Float start,Float end)
	{
		return productRepository.findByAveragePriceBetween(start,end);
	}

	@Override
	public Collection<Product>getAllProducts()
	{
		return productRepository.findAll();
	}

	@Override
	public Product addProduct(AddProductForm productForm) {
		Product product;
		if(productForm.getPhysicalProduct())
		{
			PhysicalProduct physicalProduct = new PhysicalProduct();
			physicalProduct.setLength(productForm.getLength());
			physicalProduct.setWidth (productForm.getWidth() );
			physicalProduct.setHeight(productForm.getHeight());
			physicalProduct.setWeight(productForm.getWeight());
			product = physicalProduct;
		}
		else {
			VirtualProduct virtualProduct = new VirtualProduct();
			virtualProduct.setSerial(productForm.getSerial());
			product = virtualProduct;
		}

		product.setBrand(brandRepository.findOneById(productForm.getBrandId()).get());
		product.setCompany(companyRepository.findOneById(productForm.getCompanyId()).get());
		product.setName(productForm.getName());
		product.setAveragePrice(productForm.getAveragePrice());
		product.setDateTime(new Date());

		return productRepository.save(product);
	}
	public void incrementViews(Product product) {
			product.setView(product.getView()+1);
			productRepository.save(product);
	}
}
