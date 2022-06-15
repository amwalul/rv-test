package com.example.rvtest.domain.model

data class Zone(
    val id: Int,
    val name: String
) {
    companion object {
        fun getList() = listOf(
            Zone(276, "Jakarta"),
            Zone(307, "Bandung"),
            Zone(220, "Aceh")
        )
    }
}
