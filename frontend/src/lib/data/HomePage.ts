export enum GenderTab {
	Women = 'Women',
	Men = 'Men'
}


export type OfferPagerItem = {
	offerId: string;
	bannerUrl: string;
};

export type Product = {
	productId: number;
	name: string;
	description: string;
	price: number;
	stockQuantity: number;
	imageUrls: string[];
	categoryId: number;
	variants: number[];
};
