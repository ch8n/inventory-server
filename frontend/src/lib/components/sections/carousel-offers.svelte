<script lang="ts">
	import * as Carousel from '$lib/components/ui/carousel/index.js';
	import Autoplay from 'embla-carousel-autoplay';
	const plugin = Autoplay({ delay: 2000, stopOnInteraction: true });
	export let offerUrls: string[] = [];
	export const onOfferClicked = (offerUrl: string) => {};
</script>

<div class="my-4">
	{#if offerUrls.length != 0}
		<Carousel.Root
			plugins={[plugin]}
			class="w-full max-w-xs"
			on:mousenter={plugin.stop}
			on:mouseleave={plugin.reset}
		>
			<Carousel.Content>
				{#each offerUrls as url}
					<Carousel.Item>
						<div class="p-1 h-64 border border-gray-400 rounded-lg">
							<button
								on:click={(e) => {
									onOfferClicked(url);
								}}
							>
								<img class="h-full w-full object-cover" src={url} />
							</button>
						</div>
					</Carousel.Item>
				{/each}
			</Carousel.Content>
			<Carousel.Previous class="mx-8" />
			<Carousel.Next class="mx-8" />
		</Carousel.Root>
	{/if}
</div>
