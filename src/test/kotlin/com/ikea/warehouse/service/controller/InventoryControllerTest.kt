package com.ikea.warehouse.service.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith
@AutoConfigureMockMvc
@ComponentScan
@SpringBootTest
internal class InventoryControllerTest() {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `GET available-products should return 200`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/available-products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
    }

    @Test
    fun `POST purchase-product should return 400 when missing body`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/purchase-product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `POST purchase-product should return 200 when with a valid body`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/purchase-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productName\":\"Bed\", \"quantity\":1}"))
                .andExpect(status().isOk)
    }

    @Test
    fun `POST purchase-product should return 400 when missing fields in the body`() {
        mockMvc.perform(MockMvcRequestBuilders.post("/purchase-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productName\":\"Bed\"}"))
                .andExpect(status().isBadRequest)
    }
}
