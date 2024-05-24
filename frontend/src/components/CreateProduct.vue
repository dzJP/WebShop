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
.create-product-container {
    display: flex;
    flex-direction:column;
    align-items: center;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    background-color: #fafafa;
    max-width: 400px;
    margin: auto;
}

.button-wrapper {
    width: 100%;
    display: flex;
    justify-content: center;
    margin-bottom: 20px;
}

.create-product-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s ease;
}

.create-product-button:hover {
    background-color: #0056b3;
}

.create-product {
    width: 100%;
}

.create-product form {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.form-group {
    display: flex;
    flex-direction: column;
}

.form-group label {
    margin-bottom: 5px;
    font-weight: bold;
    color: #333;
}

.form-group input[type="text"] {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    transition: border-color 0.3s ease;
}

.form-group input[type="text"]:focus {
    border-color: #007bff;
    outline: none;
}

.submit-button {
    background-color: #28a745;
}

.submit-button:hover {
    background-color: #218838;
}
</style>
