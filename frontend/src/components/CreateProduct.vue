<template>
    <div>
        <div class="create-product-container">
            <div class="button-wrapper">
                <button class="create-product-button" @click="toggleFormVisibility">Create Product</button>
            </div>
            <div v-if="isFormVisible" class="create-product">
                <i class="toggle-products" @click="toggleFormVisibility"></i>
                <form @submit.prevent="createProduct">
                    <div class="form-group">
                        <label for="product-name">Product</label>
                        <input id="product-name" v-model="newProduct.productName" type="text" required>
                    </div>
                    <div class="form-group">
                        <label for="product-price">Price</label>
                        <input id="product-price" v-model="newProduct.price" type="text" required>
                    </div>
                    <button type="submit" class="create-product-button submit-button">Create Product</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useProductStore } from '@/stores/product';

const productStore = useProductStore();
const newProduct = ref({ productName: '', price: '' });
const isFormVisible = ref(false);

const { emit } = getCurrentInstance();
const authStore = useAuthStore();
const userRole = ref(authStore.role);

watch(() => authStore.role, (newRole) => {
    userRole.value = newRole;
});

const toggleFormVisibility = () => {
    isFormVisible.value = !isFormVisible.value;
};

const createProduct = async () => {
    try {
        const createdProduct = await productStore.createProduct(newProduct.value);
        console.log('Created product:', createdProduct.data);

        emit('product-created');
        newProduct.value = { productName: '', price: '' };
    } catch (error) {
        console.error('Error creating product:', error);
    }
};
</script>

<style>

</style>
