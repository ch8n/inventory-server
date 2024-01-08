<script lang="ts">
	import type { Category } from '$lib/data/HomePage';
	import { onMount } from 'svelte';
	import * as Tabs from '$lib/components/ui/tabs';

	let categories: Category[] = [];
	let currentCategory: Category | null = null;

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
				if (categories.length != 0) {
					currentCategory = categories[0];
				}
				console.log(categories);
			} else {
				console.error('Failed to fetch data:', response.status, response.statusText);
			}
		} catch (error) {
			console.error('Error during fetch:', error.message);
		}
	});

	const updateCurrentCategory = (category: Category | null) => {
		currentCategory = category;
	};
</script>

<div class="py-4">
	<div class="text-xl bold">The Fashion Jewellery</div>

	<div class="border border-gray-300 h-48 my-2 p-4 rounded-xl">Place holder for banner</div>

	<div class="py-4">
		{#if categories.length === 0}
			<div class="text-md">Loading...</div>
		{:else}
			<Tabs.Root value={currentCategory?.categoryId || ''}>
				<Tabs.List class="border border-gray-300 rounded-md m-auto">
					{#each categories as catgoryTab}
						<Tabs.Trigger class="px-2" value={catgoryTab?.categoryId || ''} on:click={(e) => {
							updateCurrentCategory(catgoryTab)
						}}
							>{catgoryTab?.categoryName || ''}
						</Tabs.Trigger>
					{/each}
				</Tabs.List>

				<div class="text-grey-900 my-4">
					<Tabs.Content value={currentCategory?.categoryId || ''}>
						{currentCategory?.categoryName || ''}
					</Tabs.Content>
				</div>
			</Tabs.Root>
		{/if}
	</div>
</div>
