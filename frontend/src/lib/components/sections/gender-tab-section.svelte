<script lang="ts">
	import * as Tabs from '$lib/components/ui/tabs';
	import { GenderTab } from '$lib/data/HomePage';

	let genderTabs: GenderTab[] = [GenderTab.Women, GenderTab.Men];
	export let currentTab: GenderTab = genderTabs[0];
	export const onClickGenderTab = (homePageTab: GenderTab) => {
		currentTab = homePageTab;
	};
</script>

<div class="text-gray-900 flex justify-center">
	<Tabs.List class="border border-gray-300 rounded-md">
		{#each genderTabs as tab}
			<Tabs.Trigger
				class="px-2"
				value={tab}
				on:click={(e) => {
					onClickGenderTab(tab);
				}}>{tab}</Tabs.Trigger
			>
		{/each}
	</Tabs.List>
</div>
