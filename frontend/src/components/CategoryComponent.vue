<template>
    <div class="category-list">
        <h2>Categories</h2>
        <button :class="{ active: selectedCategory === '' }" @click="filterProducts('')">All Categories</button>
        <button
            v-for="category in formattedCategories"
            :key="category.originalName"
            :class="{ active: selectedCategory === category.originalName }"
            @click="filterProducts(category.originalName)"
        >
            {{ category.formattedName }}
        </button>
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
    computed: {
        formattedCategories() {
            return this.categories.map(category => {
                return {
                    originalName: category.name,
                    formattedName: category.name.charAt(0) + category.name.slice(1).toLowerCase()
                };
            });
        }
    },
    methods: {
        filterProducts(categoryName) {
            this.selectedCategory = categoryName;
            this.$emit('filter-category', categoryName);
        }
    }
};
</script>

<style scoped>
.category-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 8px;
    font-family: 'Oxanium', sans-serif;
    margin: 20px;
}

.category-list h2 {
    margin-bottom: 10px;
    font-size: 1.2em;
    color: #333;
}

.category-list button {
    background-color: #ffffff;
    border: 1px solid #ddd;
    padding: 10px;
    cursor: pointer;
    text-align: left;
    width: 100%;
    border-radius: 5px;
    transition: background-color 0.3s ease, color 0.3s ease;
    box-sizing: border-box;
    font-family: 'Oxanium', sans-serif;
    font-weight: 600;
    font-size: 1em;
    white-space: nowrap;
    letter-spacing: 2px;
}

.category-list button:hover {
    background-color: #007bff;
    color: white;
}

.category-list button.active {
    background-color: #007bff;
    color: white;
    white-space: nowrap;
}
</style>
