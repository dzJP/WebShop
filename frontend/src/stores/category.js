import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from './auth';

export const useCategoryStore = defineStore({
    id: 'category',
    state: () => ({
        categoryList: [],
    }),
    actions: {
        async getAllCategories() {
            try {
                const authStore = useAuthStore();
                const token = authStore.token;

                const response = await axios.get('http://localhost:8080/api/v1/get-categories', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                this.categoryList = response.data;
                return response.data;
            } catch (error) {
                console.error('Error fetching categories:', error);
                throw error;
            }
        },
    },
});
