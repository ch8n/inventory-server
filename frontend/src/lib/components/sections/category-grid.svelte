<script lang="ts">
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';
	import { categories, type Category } from '$lib/stores/categoryStore';

	const getIcon = (categoryId: string) => {
		return 'https://picsum.photos/200/300?random=1';
	};

	onMount(async () => {
		try {
			const response = await fetch('http://0.0.0.0:8080/v1/categories');
			if (response.ok) {
				let _response: any = await response.json();
				let _categories = _response.data.map((it: any) => {
					let item: Category = {
						categoryId: it.categoryId,
						categoryName: it.name
					};
					return item;
				});
				categories.set(_categories);
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
		{#if $categories.length !== 0}
			{#each $categories as category}
				<button
					class="border-2 border-sky-500 rounded-sm w-11/12 h-36 text-wrap"
					on:click={(e) => {
						goto(`/category/${category.categoryId}`);
					}}
				>
					<div
						class="bg-cover bg-center w-full h-5/6 overflow-clip"
						style={`background-image: url(${getIcon(category.categoryId)})`}
					/>
					<p class="truncate text-center">{category.categoryName}</p>
				</button>
			{/each}
		{/if}
	</div>
</div>
