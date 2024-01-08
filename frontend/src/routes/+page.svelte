<script lang="ts">
	import { Icon, Bars4 } from 'svelte-hero-icons';
	import * as Tabs from '$lib/components/ui/tabs';
	import { HomePageTab } from '$lib/data/HomePage';
	import TheFashionJewellery from '$lib/components/pages/the-fashion-jewellery.svelte';
	import KeyRingIstan from '$lib/components/pages/key-ring-istan.svelte';

	let currentTab: HomePageTab = HomePageTab.Women;
	let homePageTabs: HomePageTab[] = [HomePageTab.Women, HomePageTab.Men];
	const onClickHomePageTab = (homePageTab: HomePageTab) => {
		currentTab = homePageTab;
	};
</script>

<div class="mx-auto px-6 py-6">
	<Tabs.Root value={currentTab}>
		<div class="text-gray-900 grid grid-cols-3">
			<Icon src={Bars4} size="32" class="border border-gray-300 rounded-md p-1" />

			<Tabs.List class="border border-gray-300 rounded-md m-auto">
				{#each homePageTabs as tab}
					<Tabs.Trigger
						class="px-2"
						value={tab}
						on:click={(e) => {
							onClickHomePageTab(tab);
						}}>{tab}</Tabs.Trigger
					>
				{/each}
			</Tabs.List>
		</div>

		<div class="text-grey-900 my-4">
			<Tabs.Content value={currentTab}>
				{#if currentTab === HomePageTab.Women}
					<TheFashionJewellery />
				{:else}
					<KeyRingIstan />
				{/if}
			</Tabs.Content>
		</div>
	</Tabs.Root>
</div>
