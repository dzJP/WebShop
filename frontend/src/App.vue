<template>
    <router-view />
</template>

<script>
import { RouterView } from 'vue-router';
import { useAuthStore } from './stores/auth';
import { computed } from 'vue';

export default {
	name: 'App',
	components: {
		RouterView,
	},
	setup() {
        const authStore = useAuthStore();
        const isAuthenticated = computed(() => !!authStore.token);

        return {
            isAuthenticated,
        };
    }
};
</script>

