import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from './auth';

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
        async getAllProducts() {
            try {
                const authStore = useAuthStore();
                const token = authStore.token;

                const response = await axios.get('http://localhost:8080/api/v1/get-products', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                this.productList = response.data;
                return response.data;
            } catch (error) {
                console.error('Error fetching products:', error);
                throw error;
            }
        },
        async editProduct(productData) {
            try {
                const authStore = useAuthStore();
                const token = authStore.token;

                const response = await axios.put(`http://localhost:8080/api/v1/edit-product/${productData.id}`, productData, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                return response.data;
            } catch (error) {
                console.log('Error updating product:', error);
                throw error;
            }
        },
        setEditingProduct(productItem) {
            this.editingProduct = productItem;
        }
    },
    async deleteProduct(productId) {
        try {
            if (!productId) {
                console.error('Invalid productId:', productId);
                return;
            }

            const authStore = useAuthStore();
            const token = authStore.token;

            const response = await axios.delete(`http://localhost:8080/api/v1/delete-product`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                params: {
                    id: productId
                },
            });

            await this.getAllProducts();

            console.log('Delete product response:', response);

            return response;
        } catch (error) {
            console.error('Error deleting product:', error);
            throw error;
        }
    },
});

