<script lang="ts">
	import type { Category } from '$lib/data/HomePage';
	import { onMount } from 'svelte';

	let categories: Category[] = [];

	onMount(async () => {
		try {
			const response = await fetch('http://0.0.0.0:8080/v1/categories');
			if (response.ok) {
				let _response = await response.json();
				console.log(_response);
				
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	});
</script>

<div class="py-4">
	<div class="text-xl bold">The Fashion Jewellery</div>

	<div class="border border-gray-300 h-48 my-2 p-4 rounded-xl">Place holder for banner</div>
</div>
