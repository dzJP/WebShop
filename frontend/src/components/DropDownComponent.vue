<template>
    <div class="dropdown">
        <button class="dropdown-button" @click="toggleDropdown">{{ selectedOption }}</button>
        <ul v-if="isOpen" class="dropdown-list">
            <li v-for="option in options" :key="option" @click="handleSelection(option)">
                {{ option }}
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const options = ["Create Product", "Load Products", "Edit Products", "Delete Products"];
const selectedOption = ref('Select an option');
const isOpen = ref(false);

const emit = defineEmits(['option-selected']);

const toggleDropdown = () => {
    isOpen.value = !isOpen.value;
};

const handleSelection = (option) => {
    selectedOption.value = option;
    isOpen.value = false;
    emit('option-selected', option);
};
</script>

<style>
.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s ease;
}

.dropdown-button:hover {
    background-color: #0056b3;
}

.dropdown-list {
    position: absolute;
    top: 100%;
    left: 0;
    background-color: white;
    border: 1px solid #ddd;
    border-radius: 4px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-top: 5px;
    width: 100%;
    z-index: 1000;
    list-style: none;
    padding: 0;
    margin: 0;
}

.dropdown-list li {
    padding: 10px 20px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.dropdown-list li:hover {
    background-color: #f0f0f0;
}
</style>
