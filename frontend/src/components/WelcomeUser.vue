<template>
    <div class="header-container">
        <h1>Welcome, {{ userStore.firstName }}</h1>
        <div class="shopping-cart-container" @click="openCartPopup">
            <img src="@/assets/shoppingcart.png" alt="Shopping Cart" class="shopping-cart-image">
            <div class="cart-badge">{{ totalItems }}</div>
        </div>
        <div v-if="showCartPopup" class="cart-popup" @click="closeCartPopup">
            <div class="cart-items" @click.stop>
                <div v-for="item in cartItems" :key="item.id" class="cart-item">
                    <div class="cart-item-details">
                        <p class="item-name">{{ item.name }}</p>
                        <p class="item-price">Price: ${{ item.price }}</p>
                        <div class="quantity-controls">
                            <button @click.stop="decreaseQuantity(item)" class="quantity-button">-</button>
                            <span class="item-quantity">{{ item.quantity }}</span>
                            <button @click.stop="increaseQuantity(item)" class="quantity-button">+</button>
                            <button @click.stop="removeFromCart(item)" class="remove-button">Remove</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useUserStore } from '../stores/user';
import { useCartStore } from '@/stores/cart';

const userStore = useUserStore();
const cart = useCartStore();

const cartItems = computed(() => cart.items);
const totalItems = computed(() => cart.getTotalItems());
const showCartPopup = ref(false);

const openCartPopup = () => {
    showCartPopup.value = true;
};

const closeCartPopup = () => {
    showCartPopup.value = false;
};

const removeFromCart = (item) => {
    const index = cart.items.indexOf(item);
    if (index > -1) {
        cart.removeFromCart(index);
    }
};

const increaseQuantity = (item) => {
    item.quantity++;
};

const decreaseQuantity = (item) => {
    if (item.quantity > 1) {
        item.quantity--;
    }
};

onMounted(async () => {
    await userStore.getUserDetails();
});

</script>

<style scoped>
.header-container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: var(--white);
    background-color: var(--lighter-gray);
    padding: 10px 20px;
}

.header-container h1 {
    font-size: 2.5em;
    font-weight: bold;
}

.shopping-cart-container {
    display: flex;
    align-items: center;
    position: relative;
    cursor: pointer;
}

.shopping-cart-image {
    width: 40px;
    transform: scaleX(-1);
    filter: invert(100%);
}

.cart-badge {
    position: absolute;
    top: -8px;
    right: -8px;
    background-color: #ff5f6d;
    color: white;
    border-radius: 50%;
    padding: 4px 8px;
    font-size: 12px;
}

.cart-popup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 999;
    display: flex;
    justify-content: center;
    align-items: center;
}

.cart-items {
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
    max-width: 400px;
}

.cart-item {
    margin-bottom: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.cart-item:last-child {
    margin-bottom: 0;
    padding-bottom: 0;
    border-bottom: none;
}

.cart-item-details {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.item-name {
    font-weight: bold;
    font-size: 16px;
}

.item-price {
    color: #888;
    font-size: 14px;
}

.quantity-controls {
    display: flex;
    align-items: center;
    gap: 10px;
}

.quantity-button {
    padding: 8px;
    border: none;
    border-radius: 5px;
    background-color: #4caf50;
    color: white;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.quantity-button:hover {
    background-color: #388e3c;
}

.remove-button {
    background-color: #f44336;
    color: white;
    padding: 8px 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.remove-button:hover {
    background-color: #d32f2f;
}

.item-quantity {
    font-size: 16px;
    font-weight: bold;
}
</style>