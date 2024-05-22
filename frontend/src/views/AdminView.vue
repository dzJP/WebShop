<template>
    <WelcomeAdmin />
    <CreateProduct />
</template>

<script>
import WelcomeAdmin from '@/components/WelcomeAdmin.vue';
import CreateProduct from '@/components/CreateProduct.vue';

export default {
    components: {
        WelcomeAdmin,
        CreateProduct,
    }
}
</script>