<template>
    <div>
        <button class="create-category-button" @click="toggleFormVisibility">Create Category</button>
        <div v-if="isFormVisible" class="create-category">
            <i class="toggle-categories bi bi-x" @click="toggleFormVisibility"></i>
            <form @submit.prevent="createCategory">
                <input type="text" v-model="newCategory.categoryName" placeholder="Category Name" required>
                <button type="submit" class="create-category-button">Create Category</button>
            </form>
        </div>
    </div>
</template>

<script setup>
import { ref, getCurrentInstance, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useCategoryStore } from '@/stores/category';

const categoryStore = useCategoryStore();
const newCategory = ref({ categoryName: '' });
const isFormVisible = ref(false);


const { emit } = getCurrentInstance();
const authStore = useAuthStore();
const userRole = ref(authStore.role);

watch(() => authStore.role, (newRole) => {
    userRole.value = newRole;
});

const toggleFormVisibility = () => {
    isFormVisible.value = !isFormVisible.value;
};

const createCategory = async () => {
    try {
        const createdCategory = await categoryStore.createCategory(newCategory.value);
        console.log('Created category:', createdCategory.data);

        await categoryStore.getAllCategories();

        emit('category-created');
        newCategory.value = { categoryName: '' };
    } catch (error) {
        console.error('Error creating category:', error);
    }
    return {
        newCategory,
        createCategory,
        userRole,
        isFormVisible,
        toggleFormVisibility,
    };
};

</script>

<style>
.create-category {
    margin-top: 10px;
}

.create-category form {
    display: flex;
    align-items: center;
}

.create-category input[type="text"] {
    padding: 8px;
    margin-right: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.create-category button[type="submit"] {
    padding: 8px 16px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.create-category-button {
    margin-bottom: 10px;
}
</style>