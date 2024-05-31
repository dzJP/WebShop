<template>
    <div class="header-container">
        <h1>Welcome, {{ userStore.firstName }}</h1>
        <div class="shopping-cart-container" @click="openCartPopup">
            <img src="@/assets/shoppingcart.png" alt="Shopping Cart" class="shopping-cart-image">
            <div class="cart-badge">{{ totalItems }}</div>
        </div>
        <div v-if="showCartPopup" class="cart-popup" @click="closeCartPopup">
            <div class="cart-items" @click.stop>
                <template v-if="cartItems.length > 0">
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
                </template>
                <template v-else>
                    <p class="empty-cart-message">You have no items in your cart.</p>
                </template>
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
    border-bottom: solid 1px var(--orange);
    padding: 15px 30px;
}

.header-container h1 {
    font-size: 2.3em;
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

.empty-cart-message {
    color: var(--black);
}

.cart-popup {
    display: flex;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.7);
    z-index: 999;
    justify-content: center;
    align-items: center;
}

.cart-items {
    display: flex;
    flex-direction: column;
    text-align: left;
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
    width: 300px;
    max-height: 400px;
    overflow-y: auto;
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
    color: #333;
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
    padding: 8px 12px;
    border: none;
    border-radius: 5px;
    background-color: #4caf50;
    color: var(--white);
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
    color: var(--black);
}

.quantity-controls button {
    font-size: 14px;
    font-weight: bold;
}
</style>