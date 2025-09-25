import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import { RouterLink } from "@angular/router";
import { CartQuantityService } from "../cart-quantity.service";

@Component({
  selector: 'app-cart',
  template: `
    <img (click)="toggleCart($event)"
         src="../../assets/images/cart/cartImg.svg" class="cartIcon"
         [style.display]="showCartIcon ? 'block' : 'none'">
    <ul *ngIf="isCartVisible && cartProducts.length > 0" class="productInCart">
      <li *ngFor="let product of cartProducts">
        Nome: {{ product.name }} -
        Quantità: {{ product.quantity }} -
        Categoria: {{ product.category }}
        <!-- immagine eventualmente -->
      </li>
      <button class="goToCheckout" [routerLink]="['/checkout']"> CHECKOUT</button>
      <button (click)="deleteCart(cartProducts)" class="deleteCart">delete cart</button>
    </ul>
  `,
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink],
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartProducts: any[] = [];
  isCartVisible = false;
  showCartIcon = false;

  constructor(
    private cartService: CartService,
    private cartQuantityService: CartQuantityService
  ) {}

  ngOnInit(): void {
    this.viewCart();
    this.cartService.cartVisibility$.subscribe(isVisible => {
      this.showCartIcon = isVisible;
    });
  }

  viewCart(): void {
    this.cartService.viewCart().subscribe(
      cart => {
        this.cartProducts = cart.cartProducts;
        this.showCartIcon = this.cartProducts.length > 0;
      },
      error => console.error('Si è verificato un errore durante il recupero del carrello:', error)
    );
  }

  toggleCart(data: MouseEvent): void {
    this.isCartVisible = !this.isCartVisible;
    if (this.isCartVisible) {
      this.viewCart();
    }
  }

  deleteCart(cart: any): void {
    this.cartService.deleteCart(cart.id).subscribe(
      () => {
        console.log('Carrello rimosso');
        this.cartProducts = []; // Svuota l'array dei prodotti del carrello
        this.cartService.updateCartVisibility(false); // Nasconde l'icona del carrello
        this.cartQuantityService.updateTrackQuantity(0); // Aggiorna trackQuantity a 0
      },
      error => console.error('Errore durante la rimozione del carrello:', error)
    );
  }
}
