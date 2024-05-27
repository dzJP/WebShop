<template>
    <div class="create-product-container">
        <div class="create-product">
            <form @submit.prevent="createProduct">
                <div class="form-group">
                    <label for="product-name">Product</label>
                    <input id="product-name" v-model="newProduct.name" type="text" required>
                </div>
                <div class="form-group">
                    <label for="product-price">Price</label>
                    <input id="product-price" v-model="newProduct.price" type="number" required>
                </div>
                <div class="form-group">
                    <label for="product-category">Category</label>
                    <select id="product-category" v-model="newProduct.category" required>
                        <option value="" disabled>Select a category</option>
                        <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
                    </select>
                </div>
                <button type="submit" class="create-product-button submit-button">Create Product</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useProductStore } from '@/stores/product';

const productStore = useProductStore();
const newProduct = ref({ name: '', price: 0, category: '' });

const { emit } = getCurrentInstance();
const authStore = useAuthStore();
const userRole = ref(authStore.role);

const categories = ['FRUIT', 'VEGETABLE', 'DAIRY', 'MEAT', 'BEVERAGE', 'OTHER'];

watch(() => authStore.role, (newRole) => {
    userRole.value = newRole;
});

const createProduct = async () => {
    try {
        const createdProduct = await productStore.createProduct(newProduct.value);
        console.log('Created product:', createdProduct.data);

        emit('product-created');
        newProduct.value = { name: '', price: 0, category: '' };
    } catch (error) {
        console.error('Error creating product:', error);
    }
};
</script>

<style>
/* Add relevant styles here */
</style>
