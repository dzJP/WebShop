import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from './auth'; // Import the auth store

export const useProductStore = defineStore({
    id: 'product',
    state: () => ({
        productList: [],
    }),
    actions: {
        async createProduct(productData) {
            try {
                const authStore = useAuthStore(); // Get the auth store
                const token = authStore.token; // Retrieve the token from the auth store

                const response = await axios.post('http://localhost:8080/api/v1/create-product', productData, {
                    headers: {
                        Authorization: `Bearer ${token}`, // Include the token in the request headers
                    },
                });

                return response;
            } catch (error) {
                console.log('Error creating product:', error);
                throw error;
            }
        },
        async getAllProducts() {
            try {
                const authStore = useAuthStore(); // Get the auth store
                const token = authStore.token; // Retrieve the token from the auth store

                const response = await axios.get('http://localhost:8080/api/v1/get-products', {
                    headers: {
                        Authorization: `Bearer ${token}`, // Include the token in the request headers
                    },
                });

                this.productList = response.data;
                return response;
            } catch (error) {
                console.error('Error fetching products:', error);
                throw error;
            }
        }
    }
});
