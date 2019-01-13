package io.github.thang86.serviceimpl;

import io.github.thang86.repository.StoreRepository;
import io.github.thang86.repository.UserRepository;
import io.github.thang86.entities.*;
import io.github.thang86.enums.Role;
import io.github.thang86.enums.StoreHistoryType;
import io.github.thang86.enums.StoreStatus;
import io.github.thang86.forms.AddStoreCollaboratorForm;
import io.github.thang86.forms.AddStoreForm;
import io.github.thang86.forms.AddStoreProductForm;
import io.github.thang86.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
*  StoreServiceImpl.java
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
public class StoreServiceImpl implements StoreService {
	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserService userService;

    @Autowired
    private StoreHistoryService storeHistoryService;

	@Autowired
	private StoreProductService storeProductService;

    @Override
	public Optional<Store> getStoreById(Long id) {
		return Optional.ofNullable(storeRepository.findOne(id));
	}

    @Override
    public Optional<Store> getStoreByName(String name) {
        return storeRepository.findByName(name);
    }

    @Override
	public Store acceptStore(Long storeId) {
		Optional<Store> store = Optional.ofNullable(storeRepository.findOne(storeId));
		if(store.isPresent()) {
			Store store1 = store.get();
			store1.setStatus(StoreStatus.ACCEPTED);
			return storeRepository.save(store1);
		}
		return null;
	}

	@Override
	public Store rejectStore(Long storeId) {
		Optional<Store> store = Optional.ofNullable(storeRepository.findOne(storeId));
		if(store.isPresent()) {
			Store store1 = store.get();
			store1.setStatus(StoreStatus.REJECTED);
			return storeRepository.save(store1);
		}
		return null;
	}

	@Override
	public Collection<Store> getAllStores() {
		return storeRepository.findAll();
 	}

	@Override
	public Collection<Store> getAllAppliedStores() {
		return storeRepository.findAllByStatus(StoreStatus.PENDING);
	}

	@Override
	public Collection<Store> getAllUserAndCollabStores(Long storeOwnerId) {
		return storeRepository.findAllByStoreOwner_IdOrCollaborators_User_IdAndStatus(storeOwnerId, storeOwnerId, StoreStatus.ACCEPTED);
	}

	@Override
	public 	Collection<Store> getAllAcceptedUserStores(Long storeOwnerId){
		return storeRepository.findByStoreOwner_IdAndStatus(storeOwnerId, StoreStatus.ACCEPTED);
	}

	@Override
	public 	Collection<Store> getAllPendingUserStores(Long storeOwnerId){
		return storeRepository.findByStoreOwner_IdAndStatus(storeOwnerId, StoreStatus.PENDING);
	}

	@Override
	public 	Collection<Store> getAllNotAcceptedUserStores(Long storeOwnerId){
		return storeRepository.findByStoreOwner_IdAndStatus(storeOwnerId, StoreStatus.REJECTED);
	}

	@Override
	public 	Collection<Store> getAllCollaboratedUserStores(Long storeOwnerId){
		return userRepository.findOne(storeOwnerId).getStoreOwner().getCollaboratedStores();
	}

	@Override
	public Store add(AddStoreForm form, User sessionUser) {
		Store store;
		if(form.getPhysical())
		{
			PhysicalStore physicalStore = new PhysicalStore();
			physicalStore.setAddress(form.getAddress());
			store = physicalStore;
		}
		else
			store = new VirtualStore();

		store.setStatus(StoreStatus.PENDING);
		store.setName(form.getName());

		User user = userRepository.findOne(sessionUser.getId());

		if(!user.getRoles().contains(Role.STORE_OWNER))
			user.addRole(Role.STORE_OWNER);

		if(user.getStoreOwner() == null){
			user.setStoreOwner(new StoreOwner());
			user.getStoreOwner().setUser(user);
		}

		user = userRepository.save(user);

		store.setStoreOwner(user.getStoreOwner());

		return storeRepository.save(store);
	}

	@Override
	public StoreProduct addProductToStore(AddStoreProductForm form, User user) {
		Optional<Product> productOptional = productService.getProductById(form.getProductId());
		Optional<Store>   storeOptional   = this.getStoreById(form.getStoreId());

		Product product = productOptional.get();
		Store store = storeOptional.get();

		StoreProduct storeProduct = new StoreProduct();
		storeProduct.setName(form.getName());
		storeProduct.setDescription(form.getDescription());
		storeProduct.setPrice(form.getPrice());
		storeProduct.setProduct(product);
		storeProduct.setStore(store);

		store.addStoreProduct(storeProduct);

		Store save = storeRepository.save(store);

		StoreProductHistory storeProductHistory = new StoreProductHistory(
				user,
				store,
				storeProduct.getName() + " đã được thêm.",
				new Date(),
				StoreHistoryType.ADD,
				storeProduct.getStore().getId(),
				storeProduct.getProduct().getId(),
				storeProduct.getDescription(),
				storeProduct.getPrice(),
				storeProduct.getName(),
				storeProduct.getId()
		);

		storeHistoryService.add(storeProductHistory);

		return storeProduct;
	}

	@Override
	public Boolean removeProductFromStore(Long storeProductId, User user) {

		Optional<StoreProduct> optionalStoreProduct = storeProductService.getProductById(storeProductId);

		if(!optionalStoreProduct.isPresent())
			return false;

		//remove it
		StoreProduct storeProduct = optionalStoreProduct.get();

		storeProductService.remove(storeProduct);

		StoreProductHistory storeProductHistory = new StoreProductHistory(
				user,
				storeProduct.getStore(),
				storeProduct.getName() + " đã bị xóa khỏi của hàng.",
				new Date(),
				StoreHistoryType.DELETE,
				storeProduct.getStore().getId(),
				storeProduct.getProduct().getId(),
				storeProduct.getDescription(),
				storeProduct.getPrice(),
				storeProduct.getName(),
				storeProduct.getId()
		);

		storeHistoryService.add(storeProductHistory);

		return true;
	}

	@Override
	public StoreOwner addCollaboratorToStore(AddStoreCollaboratorForm form, Long userId){
		Optional<Store> storeOptional   = this.getStoreById(form.getStoreId());
		Optional<User> userOptional	= userService.getUserByUsername(form.getUsername());
		Store store = storeOptional.get();
		User collaborator = userOptional.get();


		if(!collaborator.getRoles().contains(Role.STORE_OWNER))
			collaborator.addRole(Role.STORE_OWNER);

		if(collaborator.getStoreOwner() == null){
			collaborator.setStoreOwner(new StoreOwner());
			collaborator.getStoreOwner().setUser(collaborator);
		}

		collaborator.getStoreOwner().addStCollaberatedStore(store);
		collaborator = userRepository.save(collaborator);
		store.addCollaborator(collaborator.getStoreOwner());


		store = storeRepository.save(store);

		StoreCollabHistory storeCollabHistory = new StoreCollabHistory(
				new User(userId),
				store,
				"Cộng tác viên : " + collaborator.getName() + " đã được thêm!",
				new Date(),
				StoreHistoryType.ADD,
				collaborator
		);

		storeHistoryService.add(storeCollabHistory);

		return collaborator.getStoreOwner();
	}

	@Override
	public void removeCollaboratorToStore(AddStoreCollaboratorForm form, Long userId){
		Optional<Store> storeOptional   = this.getStoreById(form.getStoreId());
		Optional<User> userOptional	= userService.getUserByUsername(form.getUsername());
		Store store = storeOptional.get();
		User collaborator = userOptional.get();
		collaborator.getStoreOwner().removeStCollaberatedStore(store);


		if(collaborator.getStoreOwner().getCollaboratedStores().isEmpty()&&collaborator.getStoreOwner().getStores().isEmpty())
			collaborator.removeRole(Role.STORE_OWNER);

		store.removeCollaborator(collaborator.getStoreOwner());

		if(collaborator.getStoreOwner() != null)
			collaborator.setStoreOwner(null);

		collaborator = userRepository.save(collaborator);
		store = storeRepository.save(store);

	
		StoreCollabHistory storeCollabHistory = new StoreCollabHistory(
				new User(userId),
				store,
				"Cộng tác viên : " + collaborator.getName() + " đã được xóa vai trò!",
				new Date(),
				StoreHistoryType.DELETE,
				collaborator
		);

		storeHistoryService.add(storeCollabHistory);

	}
}

