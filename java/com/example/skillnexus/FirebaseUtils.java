package com.example.skillnexus;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtils {

    // Firebase Authentication
    public static FirebaseAuth getAuth() {
        return FirebaseAuth.getInstance();
    }

    // Current logged-in user
    public static FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    // Firebase Firestore instance
    public static FirebaseFirestore getFirestore() {
        return FirebaseFirestore.getInstance();
    }

    // Get current user's UID
    public static String getCurrentUserId() {
        FirebaseUser user = getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }

    // Reference to "Users" collection
    public static CollectionReference allUsersCollection() {
        return getFirestore().collection("Users");
    }

    // Reference to a specific user document
    public static DocumentReference getCurrentUserDetails() {
        return allUsersCollection().document(getCurrentUserId());
    }

    // Reference to a user's specific profile
    public static DocumentReference getUserProfileById(String userId) {
        return allUsersCollection().document(userId);
    }

    // Reference to a chat between two users
    public static CollectionReference getChatMessages(String receiverId) {
        String currentUserId = getCurrentUserId();
        String chatId = currentUserId.compareTo(receiverId) < 0 ?
                currentUserId + "_" + receiverId : receiverId + "_" + currentUserId;
        return getFirestore().collection("chats").document(chatId).collection("messages");
    }

    // Reference to Requests collection
    public static CollectionReference getRequestsCollection() {
        return getFirestore().collection("Requests");
    }

    // Send a "Request to Learn"
    public static void sendLearningRequest(String receiverId, String skill) {
        getRequestsCollection().add(new LearningRequest(getCurrentUserId(), receiverId, skill));
    }

    // Model for Learning Request (inner class)
    public static class LearningRequest {
        private String senderId;
        private String receiverId;
        private String skill;

        public LearningRequest() {} // Firestore requires empty constructor

        public LearningRequest(String senderId, String receiverId, String skill) {
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.skill = skill;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public String getSkill() {
            return skill;
        }
    }
}
