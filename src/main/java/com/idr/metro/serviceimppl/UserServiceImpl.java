package com.example.UserService.Service;

import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void updateUser(Long id, User updatedUser) throws IllegalArgumentException {
        // Validate inputs
        if (id == null || updatedUser == null) {
            throw new IllegalArgumentException("User id and updated user cannot be null");
        }

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        // Update only if the fields are not null or empty
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            user.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            user.setEmail(updatedUser.getEmail());
        }

        // Save the updated user
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    // date function

    // Assume usersList is a list containing all users
    private List<User> usersList;

    // Constructor to initialize the usersList
    public UserService(List<User> usersList) {
        this.usersList = usersList;
    }

    public List<User> getUsersByCreationDateRange(Date startDate, Date endDate) {
        List<User> usersInRange = new ArrayList<>();

        // Iterate through all users
        for (User user : usersList) {
            // Check if user's creation date falls within the specified range
            if (user.isCreationDateWithinRange(startDate, endDate)) {
                // Add the user to the list
                usersInRange.add(user);
            }
        }

        // Return the list of users within the specified date range
        return usersInRange;
    }
}
