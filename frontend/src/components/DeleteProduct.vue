<template>
    <div class="form-container">
        <label for="product-select">Select Product:</label>
        <select id="product-select" v-model="selectedProduct">
            <option value="">Select a product</option>
            <option v-for="product in productList" :key="product.id" :value="product.id">{{ product.name }}</option>
        </select>
        <button @click="deleteProduct" class="delete-button">Delete Product</button>
        <div v-if="isDeleted" class="confirmation-message">
            Product deleted successfully!
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';

const productStore = useProductStore();
const productList = ref([]);
const selectedProduct = ref(null);
const isDeleted = ref(false);

onMounted(async () => {
    await getProductList();
});

const getProductList = async () => {
    try {
        await productStore.getAllProducts();
        productList.value = productStore.productList;
    } catch (error) {
        console.error('Error fetching products:', error);
    }
};

const deleteProduct = async () => {
    try {
        if (selectedProduct.value) {
            await productStore.deleteProduct(selectedProduct.value);
            await getProductList();
            selectedProduct.value = null;

            isDeleted.value = true;
        }
    } catch (error) {
        console.error('Error deleting product:', error);
    }
};
</script>

<style scoped>
.form-container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.form-group {
    margin-bottom: 15px;
}
.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
}
.form-group select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
.delete-button {
    padding: 10px 15px;
    background-color: #dc3545;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
.delete-button:hover {
    background-color: #c82333;
}
</style>
