<template>
    <div class="shop-container">
        <CategoryComponent :categories="categories" @filter-category="filterByCategory" />
        <div class="shop">
            <h1 class="heading">
                <span>Shop</span>
            </h1>
            <div v-if="loading" class="loading">Loading products...</div>
            <div v-else>
                <div v-if="error" class="error">{{ error }}</div>
                <div v-if="filteredProducts.length === 0" class="empty">No products available</div>
                <div class="product-container">
                    <div v-for="product in filteredProducts" :key="product.id" class="product">
                        <img :src="getProductImageUrl(product.image)" alt="Product Image" class="product-image">
                        <h2>{{ product.name }}</h2>
                        <p class="category">{{ product.category }}</p>
                        <p class="price">${{ product.price }}</p>
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

        const getProductImageUrl = (imageName) => {
            const backendUrl = 'http://localhost:8080';
            return `${backendUrl}/${imageName}`;
        };

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
            getProductImageUrl,
        };
    },
});
</script>

<style scoped>
.heading {
    text-align: center;
    margin: 20px;
    color: #333;
}

.shop-container {
    display: flex;
    gap: 20px;
    padding: 20px;
    background-color: #f1f1f1;
}

.shop {
    flex-grow: 1;
    display: flex;
    flex-direction: column;
}

.product-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: flex-start;
}

.product {
    flex: 0 0 calc(25% - 20px);
    border: 1px solid #ddd;
    border-radius: 8px;
    padding: 20px;
    background-color: #ffffff;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.product-image {
    width: 100%;
    height: 250px;
    object-fit: cover;
    border-radius: 8px;
    margin-bottom: 10px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.product h2 {
    font-size: 1.2em;
    margin: 10px 0;
    text-align: center;
}

.price {
    font-size: 1.1em;
    color: green;
    font-weight: bold;
    margin: 5px 0;
}

.category {
    font-size: 0.9em;
    color: #555;
    margin: 5px 0;
}

.add-to-cart {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 6px 10px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    text-align: center;
    font-family: 'Oxanium', sans-serif;
    font-weight: 600;
    letter-spacing: 1px;
}

.add-to-cart:hover {
    background-color: #0056b3;
}
</style>
