import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ProductService } from '../product.service';
import { CartService } from '../cart.service';
import { HttpClientModule } from "@angular/common/http";
import { CommonModule } from "@angular/common";
import { Observable } from "rxjs";
import { CartComponent } from '../cart/cart.component';
import { CartQuantityService } from "../cart-quantity.service";

@Component({
  selector: 'app-product-list',
  template: `
    <ul class="wrapAll">
      <p class="trackQuantity" *ngIf="trackQuantity > 0">
        {{ trackQuantity }} </p>
      <li *ngFor="let product of products$ | async" class="liItem">
        ID: {{ product.id ? product.id : 'N/A' }} -
        <p class="item">  Nome: {{ product.name ? product.name : 'N/A' }} -
          Categoria: {{ product.category ? product.category : 'N/A' }} -
          Prezzo: {{ product.price ? product.price : 'N/A' }} </p>
        <div class="cardContainer">
          <img [src]="product.img ? '../../assets/images/'
          + product.img : '../../assets/images/default.jpg'" alt="{{ product.name }}"
               class="cardImg">
        </div>
        <div class="buttons">
          <button (click)="addProduct(product)" class="addBtn">Add Product</button>
          <button (click)="removeProduct(product)" class="lessBtn">Less</button>
        </div>
      </li>
    </ul>
    <app-cart></app-cart>
  `,
  standalone: true,
  styleUrls: ['./product-list.component.css'],
  imports: [CommonModule, HttpClientModule, CartComponent]
})
export class ProductListComponent implements OnInit {
  products$!: Observable<any[]>;
  trackQuantity: number = 0;
  isCartVisible: boolean = false;
  showCartIcon: boolean = false;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private cartQuantityService: CartQuantityService
  ) {}

  ngOnInit(): void {
    this.products$ = this.productService.getProducts();
    this.updateCart();

  }

  addProduct(product: any): void {
    this.cartService.getUserCart().subscribe(
      cart => {
        if (this.exclusiveInCart(cart) && product.category === "EXCLUSIVE") {
          console.log("Exclusive già nel carrello");
          return;
        }
        this.cartService.addProductToCart(product, 1).subscribe(
          () => {
            console.log('Prodotto aggiunto con successo al carrello.');
            console.log(product)
            this.cartService.cartVisibility$.subscribe(isVisible => {
              this.showCartIcon = isVisible;
              this.isCartVisible = isVisible;
            });
            this.updateCart();
          },
          error => console.error('Errore durante l\'aggiunta del prodotto al carrello:', error)
        );
      },
      error => {
        if (error.status === 404) {
          this.cartService.addProductToCart(product, 1).subscribe(
            () => {
              console.log('Prodotto aggiunto con successo al carrello.');
              this.updateCart();
            },
            error => console.error('Errore durante l\'aggiunta del prodotto al carrello:', error)
          );
        } else {
          console.error('Errore durante il recupero del carrello:', error);
        }
      }
    );
  }

  removeProduct (product: any): void {
    this.cartService.removeProductFromCart(product.id, -1).subscribe(
      () => {
        console.log('Prodotto rimosso con successo dal carrello.');
        this.updateCart();
      },
      error => console.error('Errore durante la rimozione del prodotto dal carrello:', error)
    );
  }

  private updateCart(): void {
    this.cartQuantityService.trackQuantity$.subscribe(quantity => {
      this.trackQuantity = quantity;
    });
    this.cartService.getUserCart().subscribe(
      cart => {
        const totalQuantity = this.cartService.calculateTotalQuantity(cart.cartProducts);
        this.trackQuantity = totalQuantity;
        console.log('Quantità totale aggiornata:', totalQuantity);
        if (totalQuantity === 0) {
          this.cartService.updateCartVisibility(false);
        } else {
          this.cartService.updateCartVisibility(true);
        }
      },
      error => console.error('Errore durante l\'aggiornamento del carrello:', error)
    );
  }

  private exclusiveInCart(cart: any): boolean {
    const exclusiveProduct = cart.cartProducts.filter((product: any) => product.category === "EXCLUSIVE");
    return exclusiveProduct.length > 0 && exclusiveProduct[0].quantity === 1;
  }
}
