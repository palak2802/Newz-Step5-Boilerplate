package com.stackroute.userprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.userprofile.model.UserProfile;
import com.stackroute.userprofile.repository.UserProfileRepository;
import com.stackroute.userprofile.util.exception.UserProfileAlreadyExistsException;
import com.stackroute.userprofile.util.exception.UserProfileNotFoundException;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class UserProfileServiceImpl implements UserProfileService {

	/*
	 * Autowiring should be implemented for the UserProfileRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private UserProfileRepository userProfileRepo;
	@Autowired
	public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
		this.userProfileRepo = userProfileRepository;
	}
	
	/*
	 * This method should be used to save a new userprofile. Call the corresponding method
	 * of Repository interface.
	 */

    public UserProfile registerUser(UserProfile user) throws UserProfileAlreadyExistsException {
		Boolean userProfileById = userProfileRepo.existsById(user.getUserId());
    	if(userProfileById == false) {
    		UserProfile userProfile = userProfileRepo.insert(user);
    		if(userProfile != null)
    			return userProfile;
    	}
        throw new UserProfileAlreadyExistsException("User Profile with user ID: "+user.getUserId()+ " already exists in DB.");
    }

	/*
	 * This method should be used to update a existing userprofile.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public UserProfile updateUser(String userId, UserProfile user) throws UserProfileNotFoundException {
    	UserProfile userProfileById = userProfileRepo.findById(userId).get();
    	if(userProfileById != null) {
    		userProfileById.setFirstName(user.getFirstName());
    		userProfileById.setLastName(user.getLastName());
    		userProfileById.setContact(user.getContact());
    		userProfileById.setEmail(user.getEmail());
    		userProfileRepo.save(userProfileById);
    		UserProfile userProfile = userProfileRepo.findById(userId).get();
    		return userProfile;
    	}
        throw new UserProfileNotFoundException("User Profile with user ID: "+userId+ " does not found in DB.");
    }

	/*
	 * This method should be used to delete an existing user. Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public boolean deleteUser(String userId) throws UserProfileNotFoundException {
    	UserProfile userProfileById = userProfileRepo.findById(userId).get();
    	if(userProfileById != null) {
    		userProfileRepo.deleteById(userId);
    		return true;
    	}
        throw new UserProfileNotFoundException("User Profile with user ID: "+userId+ " does not found in DB.");
    }
    
	/*
	 * This method should be used to get userprofile by userId.Call the corresponding
	 * method of Respository interface.
	 */

    @Override
    public UserProfile getUserById(String userId) throws UserProfileNotFoundException {
    	UserProfile userProfileById = userProfileRepo.findById(userId).get();
    	if(userProfileById != null) {
    		return userProfileById;
    	}
        throw new UserProfileNotFoundException("User Profile with user ID: "+userId+ " does not found in DB.");
    }
}
