<template>
    <div>
        <h1>Shop</h1>
        <div v-if="loading">Loading products...</div>
        <div v-else>
            <div v-if="error" class="error">{{ error }}</div>
            <div v-if="products.length === 0">No products available</div>
            <div v-else class="product-list">
                <div v-for="product in products" :key="product.id" class="product">
                    <h2>{{ product.name }}</h2>
                    <p>{{ product.description }}</p>
                    <p>${{ product.price }}</p>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';

export default defineComponent({
    name: 'ShopComponent',
    setup() {
        const productStore = useProductStore();
        const loading = ref(true);
        const error = ref(null);

        const fetchProducts = async () => {
            try {
                const response = await productStore.getAllProducts();
                console.log('Products fetched:', response.data); // Add this line to log response data
            } catch (err) {
                error.value = 'Failed to load products: ' + err.message;
            } finally {
                loading.value = false;
            }
        };

        onMounted(() => {
            fetchProducts();
        });

        return {
            products: productStore.productList,
            loading,
            error,
        };
    },
});
</script>

<style scoped>
.product-list {
    display: flex;
    flex-wrap: wrap;
}

.product {
    border: 1px solid #ccc;
    padding: 16px;
    margin: 16px;
    width: 200px;
}

.error {
    color: red;
}
</style>