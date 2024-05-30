<template>
    <div class="shop-container">
        <div class="shop">
            <h1 class="heading"><span>Shop</span></h1>
            <CategoryComponent :categories="categories" @filter-category="filterByCategory" />
            <div v-if="loading" class="loading">Loading products...</div>
            <div v-else>
                <div v-if="error" class="error">{{ error }}</div>
                <div v-if="filteredProducts.length === 0" class="empty">No products available</div>
                <div class="product-container">
                    <div v-for="product in filteredProducts" :key="product.id" class="product">
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
import { defineComponent, ref, computed, onMounted } from 'vue';
import { useProductStore } from '@/stores/product';
import { useCategoryStore } from '@/stores/category';
import { useCartStore } from '@/stores/cart';
import CheckoutComponent from '@/components/CheckoutComponent.vue';
import CategoryComponent from '@/components/CategoryComponent.vue';

export default defineComponent({
    name: 'ShopComponent',
    components: {
        CheckoutComponent,
        CategoryComponent,
    },
    setup() {
        const productStore = useProductStore();
        const categoryStore = useCategoryStore();
        const cart = useCartStore();
        const loading = ref(true);
        const error = ref(null);
        const selectedCategory = ref('');

        const fetchProducts = async () => {
            try {
                await productStore.getAllProducts();
            } catch (err) {
                error.value = 'Failed to load products: ' + err.message;
            } finally {
                loading.value = false;
            }
        };

        const fetchCategories = async () => {
            try {
                await categoryStore.getAllCategories();
                console.log('Categories:', categoryStore.categoryList);
            } catch (err) {
                console.error('Failed to load categories: ', err);
            }
        };

        const addToCart = (product) => {
            cart.addToCart(product);
        };

        const filterByCategory = (categoryName) => {
            selectedCategory.value = categoryName;
        };

        const filteredProducts = computed(() => {
            if (!selectedCategory.value) {
                return productStore.productList;
            } else {
                return productStore.productList.filter(product => product.category === selectedCategory.value);
            }
        });

        onMounted(() => {
            fetchProducts();
            fetchCategories();
        });

        return {
            products: productStore.productList,
            categories: categoryStore.categoryList,
            loading,
            error,
            cart,
            addToCart,
            filterByCategory,
            filteredProducts,
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
