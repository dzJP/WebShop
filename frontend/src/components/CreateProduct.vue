<template>
    <div class="form-container">
        <div class="form">
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
                <button type="submit" class="submit-button">Create Product</button>
            </form>
        </div>
        <div v-if="isCreated" class="confirmation-message">
            Product created successfully!
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
const isCreated = ref(false);

const categories = ['FRUIT', 'VEGETABLE', 'MEAT', 'FISH', 'BEVERAGE'];

watch(() => authStore.role, (newRole) => {
    userRole.value = newRole;
});

const createProduct = async () => {
    try {
        const createdProduct = await productStore.createProduct(newProduct.value);
        console.log('Created product:', createdProduct.data);

        isCreated.value = true;

        emit('product-created');
        newProduct.value = { name: '', price: 0, category: '' };
    } catch (error) {
        console.error('Error creating product:', error);
    }
};
</script>

<style>
.form-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.form {
    display: flex;
    flex-direction: column;
}
.form-group {
    margin-bottom: 15px;
}
.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}
.form-group input,
.form-group select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
.submit-button {
    padding: 10px 15px;
    background-color: #007bff;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
.submit-button:hover {
    background-color: #0056b3;
}
</style>
