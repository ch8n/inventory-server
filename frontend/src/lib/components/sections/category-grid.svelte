<script lang="ts">
	import type { Category } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';

	let categories: Category[] = [];

	onMount(async () => {
		try {
			const response = await fetch('http://0.0.0.0:8080/v1/categories');
			if (response.ok) {
				let _response: any = await response.json();
				categories = _response.data.map((it: any) => {
					let item: Category = {
						categoryId: it.categoryId,
						categoryName: it.name
					};
					return item;
				});
				console.log(categories);
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	});
</script>

<div class="">
	<div class="py-4 grid gap-4 grid-cols-2 grid-rows-2">
		{#if categories.length === 0}
			<div class="border-2 border-sky-500 rounded-sm w-96 h-96">
				<div class="text-md">Loading...</div>
			</div>
		{:else}
			{#each categories as category}
				<button
					class="py-2 border-2 border-sky-500 rounded-sm w-11/12 text-wrap"
					on:click={(e) => {
						goto(`/category/${category.categoryId}`);
					}}
				>
					<h3>{category.categoryId}</h3>
					<p>{category.categoryName}</p>
				</button>
			{/each}
		{/if}
	</div>
</div>
