import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { CommonModule } from "@angular/common";
import { HttpClientModule } from "@angular/common/http";
import {RouterLink} from "@angular/router";
import {StripeService} from "../stripe.service";

@Component({
  selector: 'app-checkout',
  template: `
    <img src="../../assets/images/cart/cartImg.svg" class="cartIcon" [style.display]="'none'">

    <ul *ngIf="isCartVisible" class="productInCart">

      <button class="back" [routerLink]=" ['']" class="back"> BACK</button>
      <li *ngFor="let product of cartProducts">
        Nome: {{ product.name }} -
        Quantità: {{ product.quantity }} -
        Categoria: {{ product.category }}
      </li>

      <!-- STRIPE REDIRECT  -->
      <button class="stripeRedirect" (click)="stripeCheckout()" class="goToStripe"> CONFIRM</button>

    </ul>
  `,
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink],
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  cartProducts: any[] = [];
  isCartVisible = true;

  constructor(
    private cartService: CartService,
    private stripeService: StripeService,
  ) {}

  ngOnInit(): void {
    this.viewCart();
  }

  viewCart(): void {
    this.cartService.viewCart().subscribe(
      cart => {
        this.cartProducts = cart.cartProducts;
      },
      error => console.error('Si è verificato un errore durante il recupero del carrello:', error)
    );
  }

  stripeCheckout(): void {
    this.stripeService.redirectToStripe(this.cartProducts).subscribe(
      (response) => {
        console.log('redirect', response);
        window.location.href = response;
      },
      error => console.error('Errore nel redirect', error)
    );
  }




}
