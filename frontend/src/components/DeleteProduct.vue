<template>
    <div>
        <label for="product-select">Select Product:</label>
        <select id="product-select" v-model="selectedProduct">
            <option v-for="product in productList" :key="product.id" :value="product.id">{{ product.name }}</option>
        </select>
        <button @click="deleteProduct">Delete Product</button>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';

const productStore = useProductStore();
const productList = ref([]);
const selectedProduct = ref(null);

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
    if (selectedProduct.value) {
        await productStore.deleteProduct(selectedProduct.value);
        getProductList();
    }
};
</script>
