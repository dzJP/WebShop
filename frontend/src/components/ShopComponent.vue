<template>
    <div class="shop-container">
        <div class="shop">
            <h1 class="heading">
                <span>Shop</span>
            </h1>
            <div v-if="loading" class="loading">Loading products...</div>
            <div v-else>
                <div v-if="error" class="error">{{ error }}</div>
                <div v-if="products.length === 0" class="empty">No products available</div>
                <div class="product-container">
                    <div v-for="product in products" :key="product.id" class="product">
                        <h2>{{ product.name }}</h2>
                        <p class="price">${{ product.price }}</p>
                        <p class="category">{{ product.category }}</p>
                        <button @click="addToCart(product)" class="add-to-cart">Add to Cart</button>
                    </div>
                </div>
            </div>
        </div>
        <CheckoutComponent />
    </div>
</template>

<script>
import { defineComponent, ref, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';
import { useCartStore } from '@/stores/cart';
import CheckoutComponent from '@/components/CheckoutComponent.vue';

export default defineComponent({
    name: 'ShopComponent',
    components: {
        CheckoutComponent,
    },
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
.heading {
    text-align: center;
    margin: 20px;
    color: var(--white);
}
.shop-container {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
}

.shop {
    flex-grow: 1;
}

.product-container {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 10px;
}

.product {
    flex: 0 0 calc(25% - 100px);
    border: 2px solid var(--lightest-grayest);
    padding: 20px;
    border-radius: 5px;
    text-align: center;
    background-color: var(--light-gray);
    color: var(--white);
}

.product h2 {
    margin-top: 0;
    font-size: 1.7em;
}

.price {
    font-weight: bold;
    margin-bottom: 10px;
}

.category {
    font-style: italic;
    font-weight: 700;
    margin-bottom: 10px;
    color: var(--purple-variance);
}

.add-to-cart {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.add-to-cart:hover {
    background-color: #0056b3;
}

.checkout-container button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}

.checkout-container button:hover:not(:disabled) {
    background-color: #e04a57;
}
</style>
