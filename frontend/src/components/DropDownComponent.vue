<template>
    <div class="container">
        <label for="component-select" class="label">Select Component:</label>
        <select id="component-select" v-model="selectedComponent" class="dropdown">
            <option v-for="component in availableComponents" :key="component.name" :value="component.name">{{ component.displayName }}</option>
        </select>
        <div class="component-container">
            <component :is="selectedComponentComponent" v-if="selectedComponentComponent"></component>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import CreateProduct from './CreateProduct.vue';
import EditProduct from './EditProduct.vue';
import DeleteProduct from './DeleteProduct.vue';

const availableComponents = [
    { name: 'CreateProduct', displayName: 'Create Product', component: CreateProduct },
    { name: 'EditProduct', displayName: 'Edit Product', component: EditProduct },
    { name: 'DeleteProduct', displayName: 'Delete Product', component: DeleteProduct },
];

const selectedComponent = ref(null);

const selectedComponentComponent = computed(() => {
    const component = availableComponents.find(c => c.name === selectedComponent.value);
    return component ? component.component : null;
});
</script>

<style scoped>
.container {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #f9f9f9;
    border: 1px solid #ddd;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.label {
    display: block;
    font-weight: bold;
    margin-bottom: 10px;
}

.dropdown {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    border: 1px solid #ccc;
    border-radius: 4px;
    font-size: 16px;
    background-color: #fff;
}

.component-container {
    margin-top: 20px;
}
</style>
