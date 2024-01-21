<script lang="ts">
    import { GenderTab } from '$lib/data/HomePage';
	import KeyRingIstan from '$lib/components/pages/key-ring-istan.svelte';
	import TheFashionJewellery from '$lib/components/pages/the-fashion-jewellery.svelte';
	import * as Tabs from '$lib/components/ui/tabs';
	import GenderTabSection from '$lib/components/sections/gender-tab-section.svelte';

	let currentTab: GenderTab = GenderTab.Women;
	const onClickGenderTab = (homePageTab: GenderTab) => {
		currentTab = homePageTab;
	};
</script>

<div class="mx-auto px-6 py-6 overflow-x-hidden">
	<Tabs.Root value={currentTab}>
		
        <GenderTabSection {currentTab} {onClickGenderTab}  />

		<div class= "text-grey-900 my-4">
			<Tabs.Content value={currentTab}>
				{#if currentTab === GenderTab.Women}
					<TheFashionJewellery />
				{:else}
					<KeyRingIstan />
				{/if}
			</Tabs.Content>
		</div>
	</Tabs.Root>
</div>
