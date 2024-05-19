import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

export const useProductStore = defineStore({
    id: 'product',
    state: () => ({
        productList: [],
    }),
    actions: {
        async createProduct(productData) {
            try {
                const authStore = useAuthStore();
                const token = authStore.token;

                const response = await axios.post('http://localhost:8080/api/v1/create-product', productData, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                return response;
            } catch (error) {
                console.log('Error creating product:', error);
                throw error;
            }
        },
    }
})