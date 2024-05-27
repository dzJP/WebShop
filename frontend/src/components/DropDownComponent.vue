<template>
    <div>
        <label for="component-select">Select Component:</label>
        <select id="component-select" v-model="selectedComponent">
            <option v-for="component in availableComponents" :key="component.name" :value="component.name">{{ component.displayName }}</option>
        </select>
        <component :is="selectedComponentComponent" v-if="selectedComponentComponent"></component>
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
