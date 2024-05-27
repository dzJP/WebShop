<template>
    <div class="form-container">
        <label for="product-select">Select Product:</label>
        <select id="product-select" v-model="selectedProductId" @change="fetchProductDetails">
            <option value="">Select a product</option>
            <option v-for="product in productList" :key="product.id" :value="product.id">{{ product.name }}</option>
        </select>

        <form v-if="selectedProductId" @submit.prevent="updateProduct">
            <div class="form-group">
                <label for="product-name">Product Name</label>
                <input id="product-name" v-model="productName" type="text" required>
            </div>
            <div class="form-group">
                <label for="product-price">Price</label>
                <input id="product-price" v-model="price" type="text" required>
            </div>
            <div class="form-group">
                <label for="product-category">Category</label>
                <select id="product-category" v-model="category" required>
                    <option value="FRUIT">Fruit</option>
                    <option value="VEGETABLE">Vegetable</option>
                    <option value="MEAT">Meat</option>
                    <option value="FISH">Fish</option>
                    <option value="BEVERAGE">Beverage</option>
                </select>
            </div>
            <button type="submit" class="submit-button">Update Product</button>
        </form>
        <div v-if="isUpdated" class="confirmation-message">
            Product updated successfully!
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';

const productStore = useProductStore();
const selectedProductId = ref(null);
const productList = ref([]);
const productName = ref('');
const price = ref('');
const category = ref('');
const isUpdated = ref(false);

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

const updateProduct = async () => {
    try {
        const productData = {
            id: selectedProductId.value,
            name: productName.value,
            price: price.value,
            category: category.value
        };
        await productStore.editProduct(productData);
        await getProductList();
        isUpdated.value = true;
    } catch (error) {
        console.error('Error updating product:', error);
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
.form-group input,
.form-group select {
    width: 100%;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}
.submit-button {
    padding: 10px 15px;
    background-color: #28a745;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
.submit-button:hover {
    background-color: #218838;
}
</style>