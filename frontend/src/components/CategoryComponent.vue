<template>
    <div class="category-filter">
        <h2>Filter by Category</h2>
        <select v-model="selectedCategory" @change="filterProducts">
            <option value="">All Categories</option>
            <option v-for="category in categories" :key="category.name" :value="category.name">{{ category.name }}</option>
        </select>
    </div>
</template>

<script>
export default {
    name: 'CategoryComponent',
    props: {
        categories: {
            type: Array,
            required: true
        }
    },
    data() {
        return {
            selectedCategory: ''
        };
    },
    methods: {
        filterProducts() {
            this.$emit('filter-category', this.selectedCategory);
        }
    }
};
</script>

<style scoped>
.category-filter {
    margin-bottom: 20px;
}

select {
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ddd;
}
</style>
