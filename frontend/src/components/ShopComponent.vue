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
                    <button @click="addToCart(product)">Add to Cart</button>
                </div>
            </div>
            <div class="cart-summary">
                <p>Number of items in cart: {{ cart.getTotalItems() }}</p>
                <p>Total cost: ${{ cart.getTotalPrice() }}</p>
            </div>
        </div>
    </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';
import { useCartStore } from '@/stores/cart';

export default defineComponent({
    name: 'ShopComponent',
    setup() {
        const productStore = useProductStore();
        const loading = ref(true);
        const error = ref(null);
        const cart = useCartStore();

        const fetchProducts = async () => {
            try {
                const response = await productStore.getAllProducts();
                console.log('Products fetched:', response.data);
            } catch (err) {
                error.value = 'Failed to load products: ' + err.message;
            } finally {
                loading.value = false;
            }
        };

        const addToCart = (product) => {
            cart.addToCart(product);
        };

        onMounted(() => {
            fetchProducts();
        });

        return {
            products: productStore.productList,
            loading,
            error,
            cart,
            addToCart,
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

.cart-summary {
    margin-top: 20px;
    border-top: 2px solid #ccc;
    padding-top: 10px;
}
</style>
