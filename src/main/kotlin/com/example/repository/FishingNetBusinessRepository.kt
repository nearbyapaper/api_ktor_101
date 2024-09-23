package com.example.repository

import com.example.model.FishingNetBusiness

object FishingNetBusinessRepository {
    private val businesses = mutableListOf<FishingNetBusiness>()

    // Add a new business to the list
    fun addNewBusiness(business: FishingNetBusiness) {
        businesses.add(business)
    }

    // Remove a business by name
    fun removeTask(businessName: String): Boolean {
        // Find the business by name
        val business = businesses.find { it.name == businessName }
        return if (business != null) {
            // Remove the found business and return true
            businesses.remove(business)
            true
        } else {
            // If not found, return false
            false
        }
    }

    // Optionally: Get all businesses
    fun getAllBusinesses(): List<FishingNetBusiness> {
        // mock data
        if(businesses.isEmpty()){
            return listOf(
                FishingNetBusiness(
                    "KFC","Fried Chicken","Yes","Yes","Wingzap","Yes"
                ),
                FishingNetBusiness(
                    "7-11","Convenience Store","Yes","Yes","Easy to find them","Yes"
                )
            )
        }else{
            return businesses
        }
    }
}
