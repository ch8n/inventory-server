<script lang="ts">
	import { Home } from 'lucide-svelte';
	import { CircleUserRound } from 'lucide-svelte';
	import { ShoppingBag } from 'lucide-svelte';
	import { Button } from '$lib/components/ui/button';
</script>

<div
	class="fixed bottom-0 left-0 w-full place-items-center bg-gray-800 text-white p-4 grid grid-cols-3 grid-rows-1"
>
	<Button variant="outline" size="icon" class="center">
		<Home className="h-4 w-4" color="#000000" />
	</Button>

	<Button variant="outline" size="icon">
		<CircleUserRound className="h-4 w-4" color="#000000" />
	</Button>

	<Button variant="outline" size="icon">
		<ShoppingBag className="h-4 w-4" color="#000000" />
	</Button>
</div>
