<script lang="ts">	
	import * as Carousel from '$lib/components/ui/carousel/index.js';
	import Autoplay from 'embla-carousel-autoplay';
	const plugin = Autoplay({ delay: 2000, stopOnInteraction: true });
</script>

<Carousel.Root
	plugins={[plugin]}
	class="w-full max-w-xs"
	on:mousenter={plugin.stop}
	on:mouseleave={plugin.reset}
>
	<Carousel.Content>
		{#each Array(5) as _, i (i)}
			<Carousel.Item>
				<div class="p-1">
					<p>
						<span class="text-4xl font-semibold">{i + 1}</span>
					</p>
				</div>
			</Carousel.Item>
		{/each}
	</Carousel.Content>
	<Carousel.Previous />
	<Carousel.Next />
</Carousel.Root>
