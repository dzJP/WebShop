<template>
    <div>
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
            <div class="form-group">d
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
            <button type="submit">Update Product</button>
        </form>
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

onMounted(async () => {
    await fetchAllProducts();
});

const fetchAllProducts = async () => {
    try {
        productList.value = await productStore.getAllProducts();
    } catch (error) {
        console.error('Error fetching products:', error);
    }
};

const fetchProductDetails = async () => {
    try {
        if (!selectedProductId.value) return;
        const product = await productStore.getProductById(selectedProductId.value);
        productName.value = product.name;
        price.value = product.price;
        category.value = product.category;
    } catch (error) {
        console.error('Error fetching product details:', error);
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
    } catch (error) {
        console.error('Error updating product:', error);
    }
};
</script>