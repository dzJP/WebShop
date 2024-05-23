// order.js
import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from './auth'; // Import the auth store

export const useOrderStore = defineStore({
    id: 'order',
    state: () => ({
        orders: [], // Array to store order data
    }),
    actions: {
        async placeOrder(orderData) {
            try {
                const authStore = useAuthStore(); // Get the auth store
                const token = authStore.token; // Retrieve the token from the auth store

                const response = await axios.post('http://localhost:8080/api/v1/orders/checkout', orderData, {
                    headers: {
                        Authorization: `Bearer ${token}`, // Include the token in the request headers
                    },
                });

                return response;
            } catch (error) {
                console.error('Error placing order:', error);
                throw error;
            }
        },
        async fetchUserOrders(email) {
            try {
                const authStore = useAuthStore(); // Get the auth store
                const token = authStore.token; // Retrieve the token from the auth store

                const response = await axios.get(`http://localhost:8080/api/v1/orders/user/${email}`, {
                    headers: {
                        Authorization: `Bearer ${token}`, // Include the token in the request headers
                    },
                });

                this.orders = response.data;
                return response;
            } catch (error) {
                console.error('Error fetching user orders:', error);
                throw error;
            }
        },
    },
});
