import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import { useAuthStore } from '@/stores/auth'

const routes = [
	{
		path: '/',
		name: 'home',
		component: HomeView
	},
	{
		path: '/login',
		name: 'login',
		component: LoginView
	},
	{
		path: '/admin',
		name: 'admin',
		component: () => import('../views/AdminView.vue')
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})
router.beforeEach(async (to, from, next) => {
	const publicPages = ['/login'];
	const authRequired = !publicPages.includes(to.path);

	const auth = useAuthStore();

	if (authRequired && !auth.user) {
		auth.returnUrl = to.fullPath;
		next('/login');
	} else if (to.path === '/admin' && auth.role !== 'ROLE_ADMIN') {
		console.error('User does not have access to the admin panel.');
		next('/');
	} else {
		next();
	}
});

export default router
