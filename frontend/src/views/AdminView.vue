<template>
    <WelcomeAdmin />
    <CreateProduct />
    <DropDownComponent />
</template>

<script>
import WelcomeAdmin from '@/components/WelcomeAdmin.vue';
import CreateProduct from '@/components/CreateProduct.vue';
import DropDownComponent from '@/components/DropDownComponent.vue';

export default {
    components: {
        WelcomeAdmin,
        CreateProduct,
        DropDownComponent,
    }
}
</script>