import { writable, type Writable } from 'svelte/store';

export type CartItem = {
	productId: number;
	quantity: number;
};

export const cart: Writable<CartItem[]> = writable([]);
