package com.brandyodhiambo.mynote.feature_auth.domain.usecase.validation

data class ValidationResults(
    val isSuccessful: Boolean,
    val errorMessage: String? = null

)