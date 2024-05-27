<template>
    <div>
        <WelcomeAdmin />
        <DropDownComponent />
    </div>
</template>

<script>
import WelcomeAdmin from '@/components/WelcomeAdmin.vue';
import DropDownComponent from '@/components/DropDownComponent.vue';

export default {
    components: {
        WelcomeAdmin,
        DropDownComponent,
    },
};
</script>