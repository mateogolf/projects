package com.mattr.projects.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.mattr.projects.models.Pack;
import com.mattr.projects.models.Subscription;
import com.mattr.projects.models.User;
import com.mattr.projects.repositories.PackRepo;
import com.mattr.projects.repositories.SubRepo;
//import com.mattr.projects.repositories.UserRepository;

@Service
public class SubService {
	private PackRepo packRepo;
	private SubRepo subRepo;
//    private UserRepository userRepository;
	public SubService(PackRepo packRepo, SubRepo subRepo) {//, UserRepository userRepository) {
		this.packRepo = packRepo;
		this.subRepo = subRepo;
//		this.userRepository = userRepository;
	}
    //View User and Subscription (User profile)
	
	//View User/Sub/Pack (Admin dash)
	
	//View all package and count of subs (admin dash)
	public ArrayList<Pack> allPack(){
		return (ArrayList<Pack>) packRepo.findAll();
	}

	//View all packages (new sub dropdown)
	public ArrayList<Pack> allActivePack(){
		return (ArrayList<Pack>) packRepo.findByAvailabilityTrue();
	}
	
	
	//add new package
	public Pack addPackage(Pack pack) {
		return packRepo.save(pack);
	}
	
	//add new sub
	public Subscription addSub(Subscription sub) {
		return subRepo.save(sub);
	}
	
	//find sub by user
	public Subscription userSub(User user) {
		return subRepo.findByUser(user);
	}
    
	//Activate Package
	public void activate(Long id) {
		Pack pack = packRepo.findOne(id);
		if(!pack.isAvailability()) {
			pack.setAvailability(true);
			packRepo.save(pack);
		}
		
	}
	//Deactivate Package
	public void deactivate(Long id) {
		Pack pack = packRepo.findOne(id);
		if(pack.isAvailability()) {
			pack.setAvailability(false);
			packRepo.save(pack);
		}
		
	}
	public void deletePack(Long id) {
		packRepo.delete(id);
	}


}
