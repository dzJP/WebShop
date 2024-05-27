<template>
    <div class="shop">
        <h1>Shop</h1>
        <div v-if="loading" class="loading">Loading products...</div>
        <div v-else>
            <div v-if="error" class="error">{{ error }}</div>
            <div v-if="products.length === 0" class="empty">No products available</div>
            <div v-else class="product-list">
                <div v-for="product in products" :key="product.id" class="product">
                    <h2>{{ product.name }}</h2>
                    <p>{{ product.description }}</p>
                    <p class="price">${{ product.price }}</p>
                    <p class="category">{{ product.category }}</p>
                    <button @click="addToCart(product)" class="add-to-cart">Add to Cart</button>
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
.shop {
    max-width: 800px;
    margin: 0 auto;
}

.loading {
    margin-top: 20px;
    font-style: italic;
}

.error {
    margin-top: 20px;
    color: red;
}

.empty {
    margin-top: 20px;
    font-style: italic;
}

.product-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
}

.product {
    border: 1px solid #ddd;
    padding: 20px;
    border-radius: 5px;
}

.product h2 {
    margin-top: 0;
}

.price {
    font-weight: bold;
}

.category {
    font-style: italic;
}

.add-to-cart {
    margin-top: 10px;
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
}

.cart-summary {
    margin-top: 20px;
    background-color: #007bff;
}

</style>