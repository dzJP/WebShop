import { defineStore } from 'pinia';
import axios from 'axios';
import { useAuthStore } from '@/stores/auth';

export const useCategoryStore = defineStore({
    id: 'category',
    state: () => ({
        categoryList: [],
    }),
    actions: {
        async createCategory(categoryData) {
            try {
                const authStore = useAuthStore();
                const token = authStore.token;

                const response = await axios.post('http://localhost:8080/api/v1/create-category', categoryData, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                return response;
            } catch (error) {
                console.log('Error creating category:', error);
                throw error;
            }
        },
    }
})