<template>
    <div>
        <button class="create-product-button" @click="toggleFormVisibility">Create Product</button>
        <div v-if="isFormVisible" class="create-product">
            <i class="toggle-products bi bi-x" @click="toggleFormVisibility"></i>
            <form @submit.prevent="createProduct">
                <label>Product</label>
                <input v-model="newProduct.name" type="text" required>
                <!-- Change "product" to "productName" -->
                <label>Price</label>
                <input v-model="newProduct.price" type="text" required>
                <button type="submit" class="create-product-button">Create Product</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useProductStore } from '@/stores/product';

const productStore = useProductStore();
const newProduct = ref({ productName: '', price: '' }); // Change "product" to "productName"
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

        // await productStore.getAllCategories();

        emit('product-created');
        newProduct.value = { productName: '', price: '' }; // Reset the form fields
    } catch (error) {
        console.error('Error creating product:', error);
    }
    return {
        newProduct,
        createProduct,
        userRole,
        isFormVisible,
        toggleFormVisibility,
    }
};


</script>

<style>
.create-product {
    margin-top: 10px;
}

.create-product form {
    display: flex;
    align-items: center;
}

.create-product input[type="text"] {
    padding: 8px;
    margin-right: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.create-product button[type="submit"] {
    padding: 8px 16px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.create-product-button {
    margin-bottom: 10px;
}
</style>