<script lang="ts">
	import { Icon, Bars4 } from 'svelte-hero-icons';
	import * as Tabs from '$lib/components/ui/tabs';
	import { HomePageTab } from '$lib/data/HomePage';

	let currentTab: HomePageTab = HomePageTab.Women;
	let homePageTabs: HomePageTab[] = [HomePageTab.Women, HomePageTab.Men];
</script>

<div class="mx-auto px-6 py-6">
	
    <div class="text-gray-900 border-red-500">
		<Icon src={Bars4} size="32" />

		<Tabs.Root value={`${currentTab}`} class="w-[400px]">
			<Tabs.List>
				{#each homePageTabs as tab}
					<Tabs.Trigger value={tab}>{tab}</Tabs.Trigger>
				{/each}
			</Tabs.List>

			{#each homePageTabs as tab}
				<Tabs.Content value={tab}>
					<div class="text-red">{tab}</div>
				</Tabs.Content>
			{/each}
		</Tabs.Root>
	</div>
</div>
