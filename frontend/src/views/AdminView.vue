<template>
    <WelcomeAdmin />
    <CreateCategory />
    <CreateProduct />
</template>

<script>
import WelcomeAdmin from '@/components/WelcomeAdmin.vue';
import CreateCategory from '@/components/CreateCategory.vue';
import CreateProduct from '@/components/CreateProduct.vue';

export default {
    components: {
        WelcomeAdmin,
        CreateCategory,
        CreateProduct,
    }
}
</script>